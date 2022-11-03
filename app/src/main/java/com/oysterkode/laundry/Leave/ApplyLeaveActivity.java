package com.oysterkode.laundry.Leave;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.firebase.database.FirebaseDatabase;
import com.oysterkode.laundry.Paths;
import com.oysterkode.laundry.databinding.ActivityApplyLeaveBinding;

public class ApplyLeaveActivity extends AppCompatActivity {


    private ActivityApplyLeaveBinding binding;
    private Leave leave;
    private FirebaseDatabase database;
    private MaterialDatePicker.Builder materialDateBuilder;
    private MaterialDatePicker startDatePicker;
    private MaterialDatePicker dueDatePicker;

    private ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityApplyLeaveBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        database = FirebaseDatabase.getInstance();

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


    }

    private String getStudentId() {
        SharedPreferences sh = getSharedPreferences("CurrUser", MODE_PRIVATE);
        String s = sh.getString("currStudId", "");

        return s;

    }


}