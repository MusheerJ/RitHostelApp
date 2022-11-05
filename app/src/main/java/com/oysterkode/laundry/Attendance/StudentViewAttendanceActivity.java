package com.oysterkode.laundry.Attendance;

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
import com.oysterkode.laundry.Admin.Attendace.Attendance;
import com.oysterkode.laundry.Admin.Attendace.ViewAttendanceAdapter;
import com.oysterkode.laundry.Paths;
import com.oysterkode.laundry.databinding.ActivityStudentViewAttendanceBinding;

import java.util.ArrayList;

public class StudentViewAttendanceActivity extends AppCompatActivity {

    private ActivityStudentViewAttendanceBinding binding;
    private FirebaseDatabase database;
    private ArrayList<Attendance> attendances;
    private ViewAttendanceAdapter adapter;
    private ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityStudentViewAttendanceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        getSupportActionBar().hide();


        database = FirebaseDatabase.getInstance();
        dialog = new ProgressDialog(this);
        attendances = new ArrayList<>();

        adapter = new ViewAttendanceAdapter(this, attendances);


        binding.studentAttendanceAdapter.setLayoutManager(new LinearLayoutManager(this));
        binding.studentAttendanceAdapter.setAdapter(adapter);

        dialog.show();

        database.getReference()
                .child(Paths.ATTENDANCE_STUDENT)
                .child(getCurrStudentPRN())
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


    private String getCurrStudentPRN() {
        return getSharedPreferences("CurrUser", MODE_PRIVATE).getString("currStudId", "");
    }
}