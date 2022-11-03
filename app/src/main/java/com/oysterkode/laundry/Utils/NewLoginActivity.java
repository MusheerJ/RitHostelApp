package com.oysterkode.laundry.Utils;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.oysterkode.laundry.databinding.ActivityNewLoginBinding;

public class NewLoginActivity extends AppCompatActivity {

    ActivityNewLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNewLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}