package com.oysterkode.laundry.Complaint;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.FirebaseDatabase;
import com.oysterkode.laundry.Paths;
import com.oysterkode.laundry.R;
import com.oysterkode.laundry.databinding.ActivityComplaintRegistrationBinding;

public class ComplaintRegistrationActivity extends AppCompatActivity {

    private ActivityComplaintRegistrationBinding binding;
    private final String complaintCategories[] = {Complaint.Category.ELECTRICITY,
            Complaint.Category.WATER,
            Complaint.Category.WI_FI};
    private ArrayAdapter complaintCategoryAdapter;
    private FirebaseDatabase database;

    private ProgressDialog dialog;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityComplaintRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        complaintCategoryAdapter = new ArrayAdapter(getApplicationContext(), R.layout.home_list_item, complaintCategories);
        binding.complaintCategory.setAdapter(complaintCategoryAdapter);

        database = FirebaseDatabase.getInstance();
        dialog = new ProgressDialog(this);

        dialog.setMessage("Registering complaint");


        binding.registerComplaintBtn.setOnClickListener(view -> {
            Complaint complaint = new Complaint();

            if (binding.complaintHostelName.getText().toString().isEmpty()) {
                binding.complaintHostelName.setError("Required !!");
                return;
            }
            if (binding.complaintRoomNo.getText().toString().isEmpty()) {
                binding.complaintRoomNo.setError("Required !!");
                return;
            }
            if (binding.complaintCategory.getText().toString().equals("Select Category")) {
                binding.complaintCategory.setError("Required !!");
                return;
            }
            binding.complaintCategory.setError(null);

            if (binding.complaintDes.getText().toString().isEmpty()) {
                binding.complaintDes.setError("Required !!");
                return;
            }

            dialog.show();


            complaint.setComplaintId(Complaint.generateComplaintId());
            complaint.setHostel(binding.complaintHostelName.getText().toString());
            complaint.setHostelRoomNumber(binding.complaintRoomNo.getText().toString());
            complaint.setCategory(binding.complaintCategory.getText().toString());
            complaint.setDesc(binding.complaintDes.getText().toString());
            complaint.setStatus(Complaint.Status.PENDING);
            complaint.setStudentId(getStudentId());

            String dateAndTime[] = Complaint.getTimeAndDate();
            complaint.setDate(dateAndTime[0]);
            complaint.setTime(dateAndTime[1]);


            database.getReference()
                    .child(Paths.COMPLAINT_STUDENTS)
                    .child(complaint.getStudentId())
                    .child(complaint.getComplaintId())
                    .setValue(complaint)
                    .addOnSuccessListener(unused -> {
                        database.getReference()
                                .child(Paths.COMPLAINT_ADMIN)
                                .child(complaint.getComplaintId())
                                .setValue(complaint)
                                .addOnSuccessListener(unused1 -> {
                                    resetValues();
                                    dialog.dismiss();
                                    Toast.makeText(this, "Complaint Registered", Toast.LENGTH_SHORT).show();
                                });
                    });


        });


        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void resetValues() {
        binding.complaintHostelName.setText(null);
        binding.complaintRoomNo.setText(null);
        binding.complaintCategory.setText("Select Category");
        binding.complaintDes.setText(null);
    }

    private String getStudentId() {
        SharedPreferences sh = getSharedPreferences("CurrUser", MODE_PRIVATE);
        String s = sh.getString("currStudId", "");

        return s;

    }
}