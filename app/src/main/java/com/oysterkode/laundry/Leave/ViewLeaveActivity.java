package com.oysterkode.laundry.Leave;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.oysterkode.laundry.databinding.ActivityViewLeaveBinding;

public class ViewLeaveActivity extends AppCompatActivity {

    private ActivityViewLeaveBinding binding;
    private Leave selectedLeave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewLeaveBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        selectedLeave = (Leave) getIntent().getSerializableExtra("selected_leave");
        getSupportActionBar().hide();


        setLeave();


    }

    private void setLeave() {
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