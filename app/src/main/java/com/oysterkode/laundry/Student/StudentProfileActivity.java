package com.oysterkode.laundry.Student;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
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
            if (binding.profileStudentClassAndBranch.getText().toString().isEmpty()) {
                binding.profileStudentClassAndBranch.setError("Required !!");
                return;
            }
            if (binding.profileStudentContact.getText().toString().isEmpty()) {
                binding.profileStudentContact.setError("Required !!");
                return;
            }

            currentStudent.setStudentName(binding.profileStudentName.getText().toString());
            currentStudent.setHostel(binding.profileStudentHostel.getText().toString());
            currentStudent.setRoomNo(binding.profileStudentRoom.getText().toString());
            currentStudent.setClassAndBranch(binding.profileStudentClassAndBranch.getText().toString());
            currentStudent.setContact(binding.profileStudentContact.getText().toString());


            database.getReference()
                    .child(Paths.STUDENT_INFO)
                    .child(auth.getUid())
                    .setValue(currentStudent)
                    .addOnSuccessListener(unused -> {
                        Toast.makeText(this, "Profile Updated", Toast.LENGTH_SHORT).show();
                        binding.profileUpdateBtn.setVisibility(View.GONE);
                        binding.profileStudentName.setEnabled(false);
                        binding.profileStudentHostel.setEnabled(false);
                        binding.profileStudentRoom.setEnabled(false);
                        binding.profileStudentClassAndBranch.setEnabled(false);
                        binding.profileStudentContact.setEnabled(false);


                    });


        });


    }

    private void updateStudentProfile() {

        String noSet = "NOT SET";

        binding.profileUpdateBtn.setVisibility(View.VISIBLE);

        if (binding.profileStudentName.getText().toString().equals(noSet)) {
            binding.profileStudentName.setText(null);
            binding.profileStudentName.setEnabled(true);
        }

        if (binding.profileStudentHostel.getText().toString().equals(noSet)) {
            binding.profileStudentHostel.setText(null);
            binding.profileStudentHostel.setEnabled(true);
        }

        if (binding.profileStudentRoom.getText().toString().equals(noSet)) {
            binding.profileStudentRoom.setText(null);
            binding.profileStudentRoom.setEnabled(true);
        }

        if (binding.profileStudentClassAndBranch.getText().toString().equals(noSet)) {
            binding.profileStudentClassAndBranch.setText(null);
            binding.profileStudentClassAndBranch.setEnabled(true);
        }

        if (binding.profileStudentContact.getText().toString().equals(noSet)) {
            binding.profileStudentContact.setText(null);
        }


        binding.profileStudentContact.setEnabled(true);


    }

    private void setStudentInfo() {
        binding.profileStudentPRN.setText(currentStudent.getStudentId());
        binding.profileStudentPRN.setEnabled(false);


        //0
        if (currentStudent.getStudentName() == null) {
            binding.profileStudentName.setText("NOT SET");
        } else {

            binding.profileStudentName.setText(currentStudent.getStudentName());
            binding.profileStudentName.setEnabled(false);
        }

        //1
        if (currentStudent.getHostel() == null) {
            binding.profileStudentHostel.setText("NOT SET");

        } else {
            binding.profileStudentHostel.setText(currentStudent.getHostel());
        }

        //2
        if (currentStudent.getRoomNo() == null) {
            binding.profileStudentRoom.setText("NOT SET");

        } else {
            binding.profileStudentRoom.setText(currentStudent.getRoomNo());
        }


        //3
        if (currentStudent.getClassAndBranch() == null) {
            binding.profileStudentClassAndBranch.setText("NOT SET");
        } else {
            binding.profileStudentClassAndBranch.setText(currentStudent.getClassAndBranch());
        }

        //4
        if (currentStudent.getContact() == null) {
            binding.profileStudentContact.setText("NOT SET");
        } else {
            binding.profileStudentContact.setText(currentStudent.getContact());
        }

        binding.profileStudentName.setEnabled(false);
        binding.profileStudentContact.setEnabled(false);
        binding.profileStudentClassAndBranch.setEnabled(false);
        binding.profileStudentRoom.setEnabled(false);
        binding.profileStudentHostel.setEnabled(false);


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