package com.oysterkode.laundry.Admin.Attendace;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.oysterkode.laundry.Paths;
import com.oysterkode.laundry.Student.Student;
import com.oysterkode.laundry.databinding.ActivityAttendanceStudentListBinding;

import java.util.ArrayList;

public class AttendanceStudentListActivity extends AppCompatActivity {

    private ActivityAttendanceStudentListBinding binding;
    private StudentListAdapter adapter;
    private FirebaseDatabase database;
    private ProgressDialog dialog;
    private ArrayList<Student> students;
    private String date;
    private String hostel;
    public static int count;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAttendanceStudentListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        date = getIntent().getStringExtra("attendanceDate");
        hostel = getIntent().getStringExtra("attendanceHostel");


        count = 0;


        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading ... ");

        database = FirebaseDatabase.getInstance();
        students = new ArrayList<>();
        adapter = new StudentListAdapter(this, students, date, hostel);


        binding.attendanceRecylerView.setLayoutManager(new LinearLayoutManager(this));
        binding.attendanceRecylerView.setAdapter(adapter);


        dialog.show();
        database.getReference()
                .child(Paths.STUDENT_INFO)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        students.clear();
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            Student s = snapshot1.getValue(Student.class);
                            assert s != null;
                            if (s.getHostel().equals(hostel)) {
                                students.add(s);
                            }

                        }
                        dialog.dismiss();
                        adapter.notifyDataSetChanged();
                        adapter.setCount(students.size());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("TAG", "onClick: " + adapter.getCount());

                if (!adapter.isAllMarked()) {

                    Toast.makeText(AttendanceStudentListActivity.this, "Mark all attendance!", Toast.LENGTH_SHORT).show();
                    return;
                }

                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {

        if (!adapter.isAllMarked()) {
            Toast.makeText(AttendanceStudentListActivity.this, "Mark all attendance!", Toast.LENGTH_SHORT).show();
            return;
        }
        super.onBackPressed();
    }
}