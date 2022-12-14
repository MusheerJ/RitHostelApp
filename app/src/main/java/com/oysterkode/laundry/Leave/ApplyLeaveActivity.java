package com.oysterkode.laundry.Leave;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.oysterkode.laundry.Paths;
import com.oysterkode.laundry.Student.Student;
import com.oysterkode.laundry.databinding.ActivityApplyLeaveBinding;

public class ApplyLeaveActivity extends AppCompatActivity {


    private ActivityApplyLeaveBinding binding;
    private Leave leave;
    private FirebaseDatabase database;
    private MaterialDatePicker.Builder materialDateBuilder;
    private MaterialDatePicker startDatePicker;
    private MaterialDatePicker dueDatePicker;

    private ProgressDialog dialog;
    private FirebaseAuth auth;
    private Student currStudent = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityApplyLeaveBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        dialog = new ProgressDialog(this);
        dialog.setMessage("Applying for leave");

        materialDateBuilder = MaterialDatePicker.Builder.datePicker();
        materialDateBuilder.setTitleText("SELECT A DATE");

        startDatePicker = materialDateBuilder.build();
        dueDatePicker = materialDateBuilder.build();


        startDatePicker.addOnPositiveButtonClickListener(selection -> {
            binding.startDate.setText(startDatePicker.getHeaderText());
        });


        binding.startDate.setOnClickListener(view -> {
            startDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
        });

        dueDatePicker.addOnPositiveButtonClickListener(selection -> {
            binding.dueDate.setText(dueDatePicker.getHeaderText());
        });


        binding.dueDate.setOnClickListener(view -> {
            dueDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
        });


        //fetching current user data
        database.getReference().child(Paths.STUDENT_INFO).
                child(auth.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        currStudent = snapshot.getValue(Student.class);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        binding.applyLeaveBtn.setOnClickListener(view -> {


            if (binding.leaveStudentName.getText().toString().isEmpty()) {
                binding.leaveStudentName.setError("required !!");
                return;
            }
            if (binding.startDate.getText().toString().isEmpty()) {
                binding.startDate.setError("required !!");
                return;
            }
            if (binding.dueDate.getText().toString().isEmpty()) {
                binding.dueDate.setError("required !!");
                return;
            }
            if (binding.leaveDuration.getText().toString().isEmpty()) {
                binding.leaveDuration.setError("required !!");
                return;
            }
            if (binding.leaveDestination.getText().toString().isEmpty()) {
                binding.leaveDestination.setError("required !!");
                return;
            }

            if (binding.leaveParentContact.getText().toString().isEmpty()) {
                binding.leaveParentContact.setError("required !!");
                return;
            } else {
                String parentContact = binding.leaveParentContact.getText().toString();
                if (parentContact.length() != 10) {
                    binding.leaveParentContact.setError("enter a valid number");
                    return;
                }
            }

            if (binding.leaveReason.getText().toString().isEmpty()) {
                binding.leaveReason.setError("required !!");
                return;
            }


            dialog.show();
            leave = new Leave();
            leave.setStudentId(getStudentId());
            leave.setStudentName(binding.leaveStudentName.getText().toString());
            leave.setFrom(binding.startDate.getText().toString());
            leave.setTo(binding.dueDate.getText().toString());
            leave.setDuration(binding.leaveDuration.getText().toString());
            leave.setDestination(binding.leaveDestination.getText().toString());
            leave.setParentContact(binding.leaveParentContact.getText().toString());
            leave.setReason(binding.leaveReason.getText().toString());
            leave.setStatus(Leave.Status.PENDING);
            leave.setLeaveId(Leave.generateLeaveId());
            leave.setRoom(currStudent.getRoomNo());
            leave.setHostel(currStudent.getHostel());


            database.getReference()
                    .child(Paths.LEAVE_STUDENTS)
                    .child(leave.getStudentId())
                    .child(leave.getLeaveId())
                    .setValue(leave)
                    .addOnSuccessListener(unused -> database.getReference()
                            .child(Paths.LEAVE_ADMIN)
                            .child(leave.getLeaveId())
                            .setValue(leave)
                            .addOnSuccessListener(unused1 -> {
                                Toast.makeText(ApplyLeaveActivity.this, "Applied", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                binding.leaveStudentName.setText(null);
                                binding.startDate.setText(null);
                                binding.dueDate.setText(null);
                                binding.leaveDuration.setText(null);
                                binding.leaveDestination.setText(null);
                                binding.leaveParentContact.setText(null);
                                binding.leaveReason.setText(null);
                            }));


        });

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private String getStudentId() {
        SharedPreferences sh = getSharedPreferences("CurrUser", MODE_PRIVATE);
        String s = sh.getString("currStudId", "");

        return s;

    }


}