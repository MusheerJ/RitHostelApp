package com.oysterkode.laundry.Admin.StudentManagement;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.oysterkode.laundry.Student.Student;
import com.oysterkode.laundry.databinding.ActivityViewStudentBinding;

public class ViewStudentActivity extends AppCompatActivity {

    private ActivityViewStudentBinding binding;
    private Student selectedStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityViewStudentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        selectedStudent = (Student) getIntent().getSerializableExtra("selected_student");
//        Toast.makeText(this, selectedStudent.getStudentName(), Toast.LENGTH_SHORT).show();

        setStudent();
    }

    private void setStudent() {

        binding.viewStudentPRN.setText(selectedStudent.getStudentId());
        binding.viewStudentName.setText(selectedStudent.getStudentName());
        binding.viewStudentHostel.setText(selectedStudent.getHostel());
        binding.viewStudentRoom.setText(selectedStudent.getRoomNo());
        binding.viewStudentYear.setText(selectedStudent.getStudentClass());
        binding.viewStudentBranch.setText(selectedStudent.getStudentBranch());
        binding.viewStudentContact.setText(selectedStudent.getContact());
    }
}