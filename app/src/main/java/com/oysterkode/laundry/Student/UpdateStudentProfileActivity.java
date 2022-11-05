package com.oysterkode.laundry.Student;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.FirebaseDatabase;
import com.oysterkode.laundry.databinding.ActivityUpdateStudentProfileBinding;

public class UpdateStudentProfileActivity extends AppCompatActivity {

    private ActivityUpdateStudentProfileBinding binding;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityUpdateStudentProfileBinding.inflate(getLayoutInflater());
        database = FirebaseDatabase.getInstance();
        setContentView(binding.getRoot());




    }
}