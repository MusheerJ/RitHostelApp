package com.oysterkode.laundry.Admin.Attendace;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.oysterkode.laundry.Paths;
import com.oysterkode.laundry.databinding.ActivityViewAttendanceBinding;

import java.util.ArrayList;

public class ViewAttendanceActivity extends AppCompatActivity {

    private ActivityViewAttendanceBinding binding;
    private FirebaseDatabase database;
    private ViewAttendanceAdapter adapter;
    private ProgressDialog dialog;
    private ArrayList<Attendance> attendances;
    private String date;
    private String hostel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityViewAttendanceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        date = getIntent().getStringExtra("attendanceDate");
        hostel = getIntent().getStringExtra("attendanceHostel");

        database = FirebaseDatabase.getInstance();
        dialog = new ProgressDialog(this);

        attendances = new ArrayList<>();
        adapter = new ViewAttendanceAdapter(this, attendances, date, hostel);

        binding.viewAttendanceRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.viewAttendanceRecyclerView.setAdapter(adapter);

        dialog.show();
        database.getReference()
                .child(Paths.ATTENDANCE_ADMIN)
                .child(date)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        attendances.clear();

                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            Attendance attendance = snapshot1.getValue(Attendance.class);
                            attendances.add(attendance);
                        }
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}