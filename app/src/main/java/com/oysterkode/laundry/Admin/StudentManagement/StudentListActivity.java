package com.oysterkode.laundry.Admin.StudentManagement;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.oysterkode.laundry.Paths;
import com.oysterkode.laundry.Student.Student;
import com.oysterkode.laundry.databinding.ActivityStudentListBinding;

import java.util.ArrayList;

public class StudentListActivity extends AppCompatActivity {

    private ActivityStudentListBinding binding;
    private FirebaseDatabase database;
    private ProgressDialog dialog;
    private StudentAdapter adapter;
    private ArrayList<Student> students;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityStudentListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading ... ");
        database = FirebaseDatabase.getInstance();
        students = new ArrayList<>();
        adapter = new StudentAdapter(this, students);


        binding.studentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.studentRecyclerView.setAdapter(adapter);

        dialog.show();
        database.getReference()
                .child(Paths.STUDENT_INFO)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            Student student = snapshot1.getValue(Student.class);
                            students.add(student);
                        }

                        adapter.notifyDataSetChanged();
                        dialog.dismiss();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        binding.searchStudent.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {

                    binding.titleText.setVisibility(View.INVISIBLE);
                    binding.searchStudent.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;

                    binding.backButton.setVisibility(View.GONE);
                } else {
                    binding.titleText.setVisibility(View.VISIBLE);
                    binding.searchStudent.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
                    binding.backButton.setVisibility(View.VISIBLE);
                }

            }
        });


        binding.searchStudent.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                filter(newText.toLowerCase());
                return false;
            }
        });


        binding.backButton.setOnClickListener(view -> {
            finish();
        });

    }

    private void filter(String keyword) {
        ArrayList<Student> filteredList = new ArrayList<>();
        if (keyword.isEmpty()) {
            filteredList.addAll(students);

        } else {
            for (Student student : students) {

                String prn = student.getStudentId().toLowerCase();
                String name = student.getStudentName().toLowerCase();
                String room = student.getRoomNo().toLowerCase();
                if (prn.contains(keyword) || name.contains(keyword)
                        || room.contains(keyword)) {
                    filteredList.add(student);
                }

            }
        }
        adapter.filterList(filteredList);

    }
}