package com.oysterkode.laundry.Admin.StudentManagement;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.oysterkode.laundry.Admin.AdminDashBoardActivity;
import com.oysterkode.laundry.Paths;
import com.oysterkode.laundry.R;
import com.oysterkode.laundry.Student.Student;
import com.oysterkode.laundry.databinding.ActivityViewStudentBinding;

public class ViewStudentActivity extends AppCompatActivity {

    private ActivityViewStudentBinding binding;
    private Student selectedStudent;
    private ArrayAdapter branchAdapter;
    private ArrayAdapter hostelAdapter;
    private ArrayAdapter yearAdapter;
    private ProgressDialog dialog;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityViewStudentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        dialog = new ProgressDialog(this);
        dialog.setMessage("Updating Student ...");
        database = FirebaseDatabase.getInstance();
        selectedStudent = (Student) getIntent().getSerializableExtra("selected_student");
//        Toast.makeText(this, selectedStudent.getStudentName(), Toast.LENGTH_SHORT).show();

        setStudent();
        setAdapter();

        binding.updateStudentBtn.setOnClickListener(view -> {
            updateStudent();
        });
    }

    private void updateStudent() {
        dialog.show();
        selectedStudent.setStudentId(binding.viewStudentPRN.getText().toString());
        selectedStudent.setStudentName(binding.viewStudentName.getText().toString());
        selectedStudent.setHostel(binding.viewStudentHostel.getText().toString());
        selectedStudent.setRoomNo(binding.viewStudentRoom.getText().toString());
        selectedStudent.setStudentClass(binding.viewStudentYear.getText().toString());
        selectedStudent.setStudentBranch(binding.viewStudentBranch.getText().toString());
        selectedStudent.setContact(binding.viewStudentContact.getText().toString());

        database.getReference()
                .child(Paths.STUDENT_INFO)
                .child(selectedStudent.getUserId())
                .setValue(selectedStudent)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(ViewStudentActivity.this, "Student Updated", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        startActivity(new Intent(ViewStudentActivity.this, AdminDashBoardActivity.class));
                        finishAffinity();
                    }
                });

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

    private void setAdapter() {
        branchAdapter = new ArrayAdapter(getApplicationContext(), R.layout.home_list_item, Student.branches);
//        binding.viewStudentBranch.setText(null);
        binding.viewStudentBranch.setHint("Select Branch");
        binding.viewStudentBranch.setAdapter(branchAdapter);


        //Hostel ADAPTER
        hostelAdapter = new ArrayAdapter(getApplicationContext(), R.layout.home_list_item, Student.Hostel.hostels);
//        binding.viewStudentHostel.setText(null);
        binding.viewStudentHostel.setHint("Select Hostel");
        binding.viewStudentHostel.setAdapter(hostelAdapter);

        //YEAR ADAPTER
        yearAdapter = new ArrayAdapter(getApplicationContext(), R.layout.home_list_item, Student.year);
//        binding.viewStudentYear.setText(null);
        binding.viewStudentYear.setHint("Select Year");
        binding.viewStudentYear.setAdapter(yearAdapter);
    }
}