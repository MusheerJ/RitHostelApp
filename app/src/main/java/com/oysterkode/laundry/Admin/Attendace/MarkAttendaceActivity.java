package com.oysterkode.laundry.Admin.Attendace;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.oysterkode.laundry.databinding.ActivityMarkAttendaceBinding;

public class MarkAttendaceActivity extends AppCompatActivity {

    private ActivityMarkAttendaceBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMarkAttendaceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        getSupportActionBar().hide();







    }
}