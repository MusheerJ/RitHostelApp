package com.oysterkode.laundry.Admin;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.FirebaseDatabase;
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


    }
}