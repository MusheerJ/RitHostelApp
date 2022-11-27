package com.oysterkode.laundry.Admin.StudentManagement;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.oysterkode.laundry.Paths;
import com.oysterkode.laundry.R;
import com.oysterkode.laundry.Student.Student;
import com.oysterkode.laundry.databinding.ActivityAddStudent2Binding;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddStudentActivity extends AppCompatActivity {

    private ActivityAddStudent2Binding binding;
    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private FirebaseAuth mAuth2;
    private Student student;
    private ArrayAdapter branchAdapter;
    private ArrayAdapter hostelAdapter;
    private ArrayAdapter yearAdapter;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddStudent2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(this);
        dialog.setMessage("Adding student ...");


        setAdapter();
        secondAuth();


        binding.backButton.setOnClickListener(view -> {

            finish();
        });

        binding.addStudentBtn.setOnClickListener(view -> {

            if (binding.addStudentPRN.getText().toString().isEmpty()) {
                binding.addStudentPRN.setError("Required !!");
                return;
            }

            if (binding.addStudentPRN.getText().toString().length() != 7) {
                binding.addStudentPRN.setError("Invalid PRN");
                return;
            }
            if (binding.addStudentName.getText().toString().isEmpty()) {
                binding.addStudentName.setError("Required !!");
                return;
            }

            String email = binding.addStudentEmail.getText().toString();
            if (email.isEmpty()) {
                binding.addStudentEmail.setError("Required !!");
                return;
            }

            if (!isValid(email)) {
                binding.addStudentEmail.setError("Enter a valid mail !!");
                return;
            }

            if (binding.addStudentHostel.getText().toString().isEmpty()) {
                binding.addStudentHostel.setError("Required !!");
                return;
            }
            binding.addStudentHostel.setError(null);

            if (binding.addStudentRoom.getText().toString().isEmpty()) {
                binding.addStudentRoom.setError("Required !!");
                return;
            }
            if (binding.addStudentYear.getText().toString().isEmpty()) {
                binding.addStudentYear.setError("Required !!");
                return;
            }
            binding.addStudentYear.setError(null);

            if (binding.addStudentBranch.getText().toString().isEmpty()) {
                binding.addStudentBranch.setError("Required !!");
                return;
            }
            binding.addStudentBranch.setError(null);
            if (binding.addStudentContact.getText().toString().isEmpty()) {
                binding.addStudentContact.setError("Required !!");
                return;
            }
            if (!isValidNumber(binding.addStudentContact.getText().toString())) {
                binding.addStudentContact.setError("Enter a valid number !!");
                return;
            }


            dialog.show();
            student = new Student();
            student.setStudentId(binding.addStudentPRN.getText().toString());
            student.setStudentName(binding.addStudentName.getText().toString());
            student.setEmail(binding.addStudentEmail.getText().toString());
            student.setHostel(binding.addStudentHostel.getText().toString());
            student.setRoomNo(binding.addStudentRoom.getText().toString());
            student.setStudentClass(binding.addStudentYear.getText().toString());
            student.setStudentBranch(binding.addStudentBranch.getText().toString());
            student.setContact(binding.addStudentContact.getText().toString());


            createUser();


        });


    }

    private void createUser() {
        mAuth2.createUserWithEmailAndPassword(student.getEmail(), student.getStudentId())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            student.setUserId(mAuth2.getUid());
                            database.getReference()
                                    .child(Paths.STUDENT_INFO)
                                    .child(mAuth2.getUid())
                                    .setValue(student)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(AddStudentActivity.this, "Added User", Toast.LENGTH_SHORT).show();
                                            mAuth2.signOut();
                                            finish();
                                        }
                                    });
                        } else {
                            Log.d("TAG", "onComplete: " + task.getResult().toString());
                            Log.d("TAG", "onComplete: " + task.getException().getMessage());
                        }
                    }
                });
    }

    private void secondAuth() {
        FirebaseOptions firebaseOptions = new FirebaseOptions.Builder()
                .setDatabaseUrl(Paths.DB_URL)
                .setApiKey(Paths.WEB_API_KEY)
                .setApplicationId(Paths.PROJECT_ID).build();

        try {
            FirebaseApp myApp = FirebaseApp.initializeApp(getApplicationContext(), firebaseOptions, "AnyAppName");
            mAuth2 = FirebaseAuth.getInstance(myApp);
        } catch (IllegalStateException e) {
            mAuth2 = FirebaseAuth.getInstance(FirebaseApp.getInstance("AnyAppName"));
        }
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = auth.getCurrentUser();
//        if (currentUser != null) {
//            reload();
//        }
//    }

    public static boolean isValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    public static boolean isValidNumber(String s) {

        // The given argument to compile() method
        // is regular expression. With the help of
        // regular expression we can validate mobile
        // number.
        // The number should be of 10 digits.

        // Creating a Pattern class object
        Pattern p = Pattern.compile("^\\d{10}$");

        // Pattern class contains matcher() method
        // to find matching between given number
        // and regular expression for which
        // object of Matcher class is created
        Matcher m = p.matcher(s);

        // Returning boolean value
        return (m.matches());


    }

    private void setAdapter() {
        branchAdapter = new ArrayAdapter(getApplicationContext(), R.layout.home_list_item, Student.branches);
        binding.addStudentBranch.setText(null);
        binding.addStudentBranch.setHint("Select Branch");
        binding.addStudentBranch.setAdapter(branchAdapter);


        //Hostel ADAPTER
        hostelAdapter = new ArrayAdapter(getApplicationContext(), R.layout.home_list_item, Student.Hostel.hostels);
        binding.addStudentHostel.setText(null);
        binding.addStudentHostel.setHint("Select Hostel");
        binding.addStudentHostel.setAdapter(hostelAdapter);

        //YEAR ADAPTER
        yearAdapter = new ArrayAdapter(getApplicationContext(), R.layout.home_list_item, Student.year);
        binding.addStudentYear.setText(null);
        binding.addStudentYear.setHint("Select Year");
        binding.addStudentYear.setAdapter(yearAdapter);
    }


}