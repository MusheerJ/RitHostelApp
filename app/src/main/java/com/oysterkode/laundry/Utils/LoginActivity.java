package com.oysterkode.laundry.Utils;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.oysterkode.laundry.Admin.AdminDashBoardActivity;
import com.oysterkode.laundry.Paths;
import com.oysterkode.laundry.R;
import com.oysterkode.laundry.Student.Student;
import com.oysterkode.laundry.Student.StudentDashBoardActivity;
import com.oysterkode.laundry.databinding.ActivityUser1Binding;

public class LoginActivity extends AppCompatActivity {
    private ActivityUser1Binding binding;
    private FirebaseAuth auth;
    private Animation topanimation;
    private FirebaseDatabase database;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUser1Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        auth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance();

        topanimation = AnimationUtils.loadAnimation(this, R.anim.topanimation);
//        binding.club.setAnimation(topanimation);
//
        if (auth.getCurrentUser() != null) {
            Intent i = new Intent(LoginActivity.this, StudentDashBoardActivity.class);
            startActivity(i);
            finish();
        }

        // Creating account Using Email ------>
        binding.CreateAC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.email.getText().toString().isEmpty()) {
                    binding.email.setError("Please enter a Email !!!");
                    return;

                }
                if (binding.password.getText().toString().isEmpty()) {
                    binding.password.setError("Please enter a Password !!!");
                    return;
                }
                auth.createUserWithEmailAndPassword(binding.email.getText().toString()
                        , binding.password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this,
                                    "Account Created ", Toast.LENGTH_SHORT).show();
                            binding.password.setText("");
                            binding.email.setText("");
                        }
                    }
                });

            }
        });


        // Sign In using Email ----->
        binding.signIn.setOnClickListener(v -> {

            String email = binding.email.getText().toString();
            String pass = binding.password.getText().toString();
            if (email.isEmpty()) {
                binding.email.setError("Please enter a Email !!!");
                return;

            }
            if (pass.isEmpty()) {
                binding.password.setError("Please enter a Password !!!");
                return;
            }
            auth.signInWithEmailAndPassword(email,
                    pass)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this,
                                    "Sign in Successful ", Toast.LENGTH_SHORT).show();

                            //Init Student
                            Student student = new Student();
                            student.setUserId(auth.getUid());
                            student.setEmail(email);
                            student.setStudentId(email.split("@")[0]);

                            SharedPreferences sharedPreferences = getSharedPreferences("CurrUser", MODE_PRIVATE);

                            SharedPreferences.Editor myEdit = sharedPreferences.edit();


                            myEdit.putString("currStudId", student.getStudentId());
                            myEdit.putString("currUserId", student.getUserId());
                            myEdit.commit();


                            database.getReference()
                                    .child(Paths.STUDENT_INFO)
                                    .child(auth.getUid())
                                    .addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if (!snapshot.exists()) {
                                                database.getReference().child(Paths.STUDENT_INFO)
                                                        .child(auth.getUid())
                                                        .setValue(student);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });


                            Intent i;
                            if (binding.isAdmin.isChecked()) {
                                i = new Intent(this, AdminDashBoardActivity.class);

                            } else {
                                i = new Intent(this, StudentDashBoardActivity.class);
                            }

                            i.putExtra("PRN", binding.password.getText().toString());
                            binding.password.setText("");
                            binding.email.setText("");
                            startActivity(i);
                            finish();


                        } else {
                            Toast.makeText(LoginActivity.this
                                    , task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                            binding.gifImageView.setVisibility(View.VISIBLE);
                            binding.gifImageView1.setVisibility(View.INVISIBLE);
                            binding.logp.setVisibility(View.INVISIBLE);

                        }

                    });

            if (binding.email.getText().toString().isEmpty()) {
                binding.logp.setVisibility(View.INVISIBLE);
                //  binding.gifImageView.setVisibility(View.INVISIBLE);
                //  binding.gifImageView1.setVisibility(View.VISIBLE);

            } else if (binding.password.getText().toString().isEmpty()) {
                binding.logp.setVisibility(View.INVISIBLE);
                //  binding.gifImageView.setVisibility(View.INVISIBLE);
                //  binding.gifImageView1.setVisibility(View.VISIBLE);

            } else {
                binding.logp.setVisibility(View.VISIBLE);
            }

            binding.gifImageView.setVisibility(View.INVISIBLE);
            binding.gifImageView1.setVisibility(View.VISIBLE);

        });

        String o = getIntent().getStringExtra("xyz");

        if (o == "1") {
            binding.gifImageView.setVisibility(View.VISIBLE);
            binding.gifImageView1.setVisibility(View.INVISIBLE);
            binding.logp.setVisibility(View.INVISIBLE);
        }


    }


//    boolean userExits() {
//
//    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}