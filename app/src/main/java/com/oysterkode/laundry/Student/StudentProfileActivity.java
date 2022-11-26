package com.oysterkode.laundry.Student;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.oysterkode.laundry.Paths;
import com.oysterkode.laundry.R;
import com.oysterkode.laundry.Utils.LoginActivity;
import com.oysterkode.laundry.databinding.ActivityStudentProfileBinding;

public class StudentProfileActivity extends AppCompatActivity {

    private ActivityStudentProfileBinding binding;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private Student currentStudent;
    private ProgressDialog dialog;
    private ArrayAdapter branchAdapter;
    private ArrayAdapter hostelAdapter;
    private ArrayAdapter yearAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStudentProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();


        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();


        dialog = new ProgressDialog(this);

        dialog.setMessage("Loading ...");
        dialog.setCancelable(false);

        dialog.show();
        database.getReference()
                .child(Paths.STUDENT_INFO)
                .child(auth.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        currentStudent = snapshot.getValue(Student.class);
                        setStudentInfo();
                        dialog.dismiss();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        binding.menu2.setOnClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(getApplicationContext(), binding.menu2);
            popupMenu.getMenuInflater().inflate(R.menu.student_profile_menu, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {

                    switch (item.getItemId()) {
                        case R.id.changePassword:
                            sendPassWordResetLink(currentStudent.getEmail());
                            break;
                        case R.id.updateProfile:
                            updateStudentProfile();
                            break;
                        case R.id.studentLogout:
                            auth.signOut();
                            startActivity(new Intent(StudentProfileActivity.this, LoginActivity.class));
                            finishAffinity();
                            break;

                    }
                    return false;
                }
            });

            popupMenu.show();
        });


        binding.profileUpdateBtn.setOnClickListener(v -> {

            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
            if (binding.profileStudentName.getText().toString().isEmpty()) {
                binding.profileStudentName.setError("Required !!");
                return;
            }
            if (binding.profileStudentHostel.getText().toString().isEmpty()) {
                binding.profileStudentHostel.setError("Required !!");
                return;
            }
            if (binding.profileStudentRoom.getText().toString().isEmpty()) {
                binding.profileStudentRoom.setError("Required !!");
                return;
            }
            if (binding.profileStudentYear.getText().toString().isEmpty()) {
                binding.profileStudentYear.setError("Required !!");
                return;
            }

            if (binding.profileStudentBranch.getText().toString().isEmpty()) {
                binding.profileStudentBranch.setError("Required !!");
                return;
            }
            if (binding.profileStudentContact.getText().toString().isEmpty()) {
                binding.profileStudentContact.setError("Required !!");
                return;
            }

            currentStudent.setStudentName(binding.profileStudentName.getText().toString());
            currentStudent.setHostel(binding.profileStudentHostel.getText().toString());
            currentStudent.setRoomNo(binding.profileStudentRoom.getText().toString());
            currentStudent.setStudentClass(binding.profileStudentYear.getText().toString());
            currentStudent.setStudentBranch(binding.profileStudentBranch.getText().toString());
            currentStudent.setContact(binding.profileStudentContact.getText().toString());


            database.getReference()
                    .child(Paths.STUDENT_INFO)
                    .child(auth.getUid())
                    .setValue(currentStudent)
                    .addOnSuccessListener(unused -> {
                        Toast.makeText(this, "Profile Updated", Toast.LENGTH_SHORT).show();
                        binding.profileUpdateBtn.setVisibility(View.GONE);


                    });


        });


        //BRANCH ADAPTER
        branchAdapter = new ArrayAdapter(getApplicationContext(), R.layout.home_list_item, Student.branches);
        binding.profileStudentBranch.setAdapter(branchAdapter);


        //Hostel ADAPTER
        hostelAdapter = new ArrayAdapter(getApplicationContext(), R.layout.home_list_item, Student.Hostel.hostels);
        binding.profileStudentHostel.setAdapter(hostelAdapter);

        //YEAR ADAPTER
        yearAdapter = new ArrayAdapter(getApplicationContext(), R.layout.home_list_item, Student.year);
        binding.profileStudentYear.setAdapter(yearAdapter);


    }

    private void setAdapter() {
        branchAdapter = new ArrayAdapter(getApplicationContext(), R.layout.home_list_item, Student.branches);
        binding.profileStudentBranch.setAdapter(branchAdapter);


        //Hostel ADAPTER
        hostelAdapter = new ArrayAdapter(getApplicationContext(), R.layout.home_list_item, Student.Hostel.hostels);
        binding.profileStudentHostel.setAdapter(hostelAdapter);

        //YEAR ADAPTER
        yearAdapter = new ArrayAdapter(getApplicationContext(), R.layout.home_list_item, Student.year);
        binding.profileStudentYear.setAdapter(yearAdapter);
    }

    private void updateStudentProfile() {

        String noSet = "NOT SET";
        binding.profileUpdateBtn.setVisibility(View.VISIBLE);

        if (binding.profileStudentName.getText().toString().equals(noSet)) {
            binding.profileStudentName.setText(null);
            binding.profileStudentName.setEnabled(true);
        }

        if (binding.profileStudentHostel.getText().toString().equals(noSet)) {
            binding.profileStudentHostel.setHint("Select Year");
            binding.profileStudentHostel.setText(null);
            binding.profileStudentHostel.setEnabled(true);
        }

        if (binding.profileStudentRoom.getText().toString().equals(noSet)) {
            binding.profileStudentRoom.setText(null);
            binding.profileStudentRoom.setEnabled(true);
        }

        if (binding.profileStudentYear.getText().toString().equals(noSet)) {
            binding.profileStudentYear.setHint("Select Year");
            binding.profileStudentYear.setText(null);
            binding.profileStudentYear.setEnabled(true);
        }

        if (binding.profileStudentBranch.getText().toString().equals(noSet)) {
            binding.profileStudentBranch.setHint("Select Year");
            binding.profileStudentBranch.setText(null);
            binding.profileStudentBranch.setEnabled(true);
        }

        if (binding.profileStudentContact.getText().toString().equals(noSet)) {
            binding.profileStudentContact.setText(null);
        }


        binding.profileStudentContact.setEnabled(true);
        binding.profileStudentRoom.setEnabled(true);
    }

    private void setStudentInfo() {

        //set Student PRN
        binding.profileStudentPRN.setText(currentStudent.getStudentId());


        //set student name
        if (currentStudent.getStudentName() == null) {
            binding.profileStudentName.setText("NOT SET");
        } else {
            binding.profileStudentName.setText(currentStudent.getStudentName());
        }

        //set student hostel
        if (currentStudent.getHostel() == null) {
            binding.profileStudentHostel.setText("NOT SET");

        } else {
            binding.profileStudentHostel.setText(currentStudent.getHostel());
        }

        //set student room
        if (currentStudent.getRoomNo() == null) {
            binding.profileStudentRoom.setText("NOT SET");

        } else {
            binding.profileStudentRoom.setText(currentStudent.getRoomNo());
        }


        //set student class
        if (currentStudent.getStudentClass() == null) {
            binding.profileStudentYear.setText("NOT SET");
        } else {
            binding.profileStudentYear.setText(currentStudent.getStudentClass());
        }

        //set student contact
        if (currentStudent.getContact() == null) {
            binding.profileStudentContact.setText("NOT SET");
        } else {
            binding.profileStudentContact.setText(currentStudent.getContact());
        }

        //set student branch
        if (currentStudent.getStudentBranch() == null) {
            binding.profileStudentBranch.setText("NOT SET");
        } else {
            binding.profileStudentBranch.setText(currentStudent.getStudentBranch());
        }

        disableTheEditText();
    }

    private void disableTheEditText() {
        binding.profileStudentPRN.setEnabled(false);
        binding.profileStudentName.setEnabled(false);
        binding.profileStudentHostel.setEnabled(false);
        binding.profileStudentRoom.setEnabled(false);
        binding.profileStudentYear.setEnabled(false);
        binding.profileStudentBranch.setEnabled(false);
        binding.profileStudentContact.setEnabled(false);
    }

    private void sendPassWordResetLink(String email) {
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(StudentProfileActivity.this, "Reset link sent on email ", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}