package com.oysterkode.laundry.Admin.Leave;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.FirebaseDatabase;
import com.oysterkode.laundry.Leave.Leave;
import com.oysterkode.laundry.Paths;
import com.oysterkode.laundry.databinding.ActivityAdminViewLeaveBinding;

public class AdminViewLeaveActivity extends AppCompatActivity {

    private ActivityAdminViewLeaveBinding binding;
    private Leave selectedLeave;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityAdminViewLeaveBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database = FirebaseDatabase.getInstance();

        getSupportActionBar().hide();
        selectedLeave = (Leave) getIntent().getSerializableExtra("selected_leave");
        setLeave();


        binding.approveLeave.setOnClickListener(view -> {
            selectedLeave.setStatus(Leave.Status.APPROVED);

            database.getReference()
                    .child(Paths.LEAVE_ADMIN)
                    .child(selectedLeave.getLeaveId())
                    .setValue(selectedLeave)
                    .addOnSuccessListener(unused -> database.getReference()
                            .child(Paths.LEAVE_STUDENTS)
                            .child(selectedLeave.getStudentId())
                            .child(selectedLeave.getLeaveId())
                            .setValue(selectedLeave)
                            .addOnSuccessListener(unused12 -> Toast.makeText(AdminViewLeaveActivity.this, "Leave Status Updated", Toast.LENGTH_SHORT).show()));
        });


        binding.rejectLeave.setOnClickListener(view -> {
            selectedLeave.setStatus(Leave.Status.REJECTED);

            database.getReference()
                    .child(Paths.LEAVE_ADMIN)
                    .child(selectedLeave.getLeaveId())
                    .setValue(selectedLeave)
                    .addOnSuccessListener(unused -> database.getReference()
                            .child(Paths.LEAVE_STUDENTS)
                            .child(selectedLeave.getStudentId())
                            .child(selectedLeave.getLeaveId())
                            .setValue(selectedLeave)
                            .addOnSuccessListener(unused1 -> Toast.makeText(AdminViewLeaveActivity.this, "Leave Status Updated", Toast.LENGTH_SHORT).show()));
        });

    }


    private void setLeave() {

        binding.leaveStudentPRN.setText(selectedLeave.getStudentId());
        binding.leaveStudentPRN.setEnabled(false);


        binding.leaveStudentName.setText(selectedLeave.getStudentName());
        binding.leaveStudentName.setEnabled(false);

        binding.leaveCurrentStatus.setText(selectedLeave.getStatus());
        binding.leaveCurrentStatus.setEnabled(false);


        binding.startDate.setText(selectedLeave.getFrom());


        binding.dueDate.setText(selectedLeave.getTo());

        binding.leaveDuration.setText(selectedLeave.getDuration());
        binding.leaveDuration.setEnabled(false);

        binding.leaveDestination.setText(selectedLeave.getDestination());
        binding.leaveDestination.setEnabled(false);

        binding.leaveParentContact.setText(selectedLeave.getParentContact());
        binding.leaveParentContact.setEnabled(false);

        binding.leaveReason.setText(selectedLeave.getReason());
        binding.leaveReason.setEnabled(false);


    }


}