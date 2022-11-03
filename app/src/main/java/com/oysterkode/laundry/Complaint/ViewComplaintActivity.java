package com.oysterkode.laundry.Complaint;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.oysterkode.laundry.databinding.ActivityViewComplaintBinding;

import java.util.Objects;

public class ViewComplaintActivity extends AppCompatActivity {

    private ActivityViewComplaintBinding binding;
    private Complaint selectedComplaint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewComplaintBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();

        selectedComplaint = (Complaint) getIntent().getSerializableExtra("selected_complaint");
        setComplaint();

    }

    private void setComplaint() {
        binding.viewComplaintHostelName.setText(selectedComplaint.getHostel());
        binding.viewComplaintHostelName.setEnabled(false);

        binding.viewComplaintRoomNo.setText(selectedComplaint.getHostelRoomNumber());
        binding.viewComplaintRoomNo.setEnabled(false);

        binding.viewComplaintCategory.setText(selectedComplaint.getCategory());
        binding.viewComplaintCategory.setEnabled(false);

        binding.viewComplaintStatus.setText(selectedComplaint.getStatus());
        binding.viewComplaintStatus.setEnabled(false);

        binding.viewComplaintDes.setText(selectedComplaint.getDesc());
        binding.viewComplaintDes.setEnabled(false);

    }
}