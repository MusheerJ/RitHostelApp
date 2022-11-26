package com.oysterkode.laundry.Admin.Attendace;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.oysterkode.laundry.Paths;
import com.oysterkode.laundry.R;
import com.oysterkode.laundry.Student.Student;
import com.oysterkode.laundry.databinding.ActivityAttendanceParameterSelectionBinding;

public class AttendanceParameterSelectionActivity extends AppCompatActivity {

    private ActivityAttendanceParameterSelectionBinding binding;
    private MaterialDatePicker.Builder materialDateBuilder;
    private MaterialDatePicker startDatePicker;
    private FirebaseDatabase database;

    private ArrayAdapter hostelAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAttendanceParameterSelectionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        database = FirebaseDatabase.getInstance();

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


            database.getReference()
                    .child(Paths.ATTENDANCE_ADMIN)
                    .child(date)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                Intent i = new Intent(AttendanceParameterSelectionActivity.this, ViewAttendanceActivity.class);
                                i.putExtra("attendanceDate", date);
                                i.putExtra("attendanceHostel", hostel);
                                startActivity(i);


                                binding.attendanceHostelSelector.setText(null);
                                binding.attendanceDate.setText(null);
                            } else {
                                Toast.makeText(AttendanceParameterSelectionActivity.this, "Attendance not available", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

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

            Intent i = new Intent(AttendanceParameterSelectionActivity.this, AttendanceStudentListActivity.class);
            i.putExtra("attendanceDate", date);
            i.putExtra("attendanceHostel", hostel);
            startActivity(i);


            binding.attendanceHostelSelector.setText(null);
            binding.attendanceDate.setText(null);


//            database.getReference()
//                    .child(Paths.ATTENDANCE_ADMIN)
//                    .child(date)
//                    .addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
////                            if (snapshot.exists() ) {
////                                Toast.makeText(AttendanceParameterSelectionActivity.this, "Attendance already added for this day", Toast.LENGTH_SHORT).show();
////                            } else {
//
////                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });


        });


        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}