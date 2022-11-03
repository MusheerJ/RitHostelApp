package com.oysterkode.laundry.Admin;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.FirebaseDatabase;
import com.oysterkode.laundry.Admin.Complaint.ComplaintListActivity;
import com.oysterkode.laundry.Admin.Leave.LeaveListActivity;
import com.oysterkode.laundry.databinding.ActivityAdminDashBoardBinding;

public class AdminDashBoardActivity extends AppCompatActivity {


    private ActivityAdminDashBoardBinding binding;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAdminDashBoardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        getSupportActionBar().hide();


        binding.AdminLeave.setOnClickListener(view -> {
            Intent i = new Intent(this, LeaveListActivity.class);
            startActivity(i);
        });


        binding.AdminLaundry.setOnClickListener(view->{
            Intent i = new Intent(this, LaundryStudentList.class);
            startActivity(i);
        });

        binding.AdminComplaint.setOnClickListener(view -> {
            Intent i = new Intent(this, ComplaintListActivity.class);
            startActivity(i);
        });


    }
}