package com.oysterkode.laundry.Admin.Attendace;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.oysterkode.laundry.R;
import com.oysterkode.laundry.Student.Student;
import com.oysterkode.laundry.databinding.ActivityAttendanceParameterSelectionBinding;

public class AttendanceParameterSelectionActivity extends AppCompatActivity {

    private ActivityAttendanceParameterSelectionBinding binding;
    private MaterialDatePicker.Builder materialDateBuilder;
    private MaterialDatePicker startDatePicker;

    private ArrayAdapter hostelAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAttendanceParameterSelectionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();


        binding.attendanceHostelSelector.setHint("Select hostel");

        materialDateBuilder = MaterialDatePicker.Builder.datePicker();
        materialDateBuilder.setTitleText("SELECT A DATE");

        startDatePicker = materialDateBuilder.build();
        hostelAdapter = new ArrayAdapter(getApplicationContext(), R.layout.home_list_item, Student.Hostel.hostels);
        binding.attendanceHostelSelector.setAdapter(hostelAdapter);

        startDatePicker.addOnPositiveButtonClickListener(selection -> {
            binding.attendanceDate.setText(startDatePicker.getHeaderText());
        });

        binding.attendanceDate.setOnClickListener(view -> {
            startDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
        });


        binding.viewAttendanceBtn.setOnClickListener(view -> {
            String date = binding.attendanceDate.getText().toString();
            String hostel = binding.attendanceHostelSelector.getText().toString();

            if (date.isEmpty()) {
                binding.attendanceDate.setError("Required !!");
                return;
            }
            if (hostel.isEmpty()) {
                binding.attendanceHostelSelector.setError("Required !!");
                return;
            }

            Intent i = new Intent(this, ViewAttendanceActivity.class);
            i.putExtra("attendanceDate", date);
            i.putExtra("attendanceHostel", hostel);
            startActivity(i);


            binding.attendanceHostelSelector.setText(null);
            binding.attendanceDate.setText(null);
        });


        binding.attendanceProceedBtn.setOnClickListener(onclick -> {
            String date = binding.attendanceDate.getText().toString();
            String hostel = binding.attendanceHostelSelector.getText().toString();

            if (date.isEmpty()) {
                binding.attendanceDate.setError("Required !!");
                return;
            }
            if (hostel.isEmpty()) {
                binding.attendanceHostelSelector.setError("Required !!");
                return;
            }

            Intent i = new Intent(this, AttendanceStudentListActivity.class);
            i.putExtra("attendanceDate", date);
            i.putExtra("attendanceHostel", hostel);
            startActivity(i);


            binding.attendanceHostelSelector
                    .setText(null);
            binding.attendanceDate.setText(null);


        });


        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}