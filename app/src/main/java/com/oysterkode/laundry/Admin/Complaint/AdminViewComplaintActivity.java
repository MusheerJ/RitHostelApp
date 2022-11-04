package com.oysterkode.laundry.Admin.Complaint;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.FirebaseDatabase;
import com.oysterkode.laundry.Complaint.Complaint;
import com.oysterkode.laundry.Paths;
import com.oysterkode.laundry.R;
import com.oysterkode.laundry.databinding.ActivityAdminViewComplaintBinding;

public class AdminViewComplaintActivity extends AppCompatActivity {

    private ActivityAdminViewComplaintBinding binding;
    private FirebaseDatabase database;
    private ProgressDialog dialog;
    private Complaint selectedComplaint;
    private String complaintStatus[] = {
            Complaint.Status.PENDING,
            Complaint.Status.IN_PROGRESS,
            Complaint.Status.RESOLVED
    };
    private ArrayAdapter statusAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAdminViewComplaintBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        database = FirebaseDatabase.getInstance();
        dialog = new ProgressDialog(this);
        selectedComplaint = (Complaint) getIntent().getSerializableExtra("selected_complaint");
        setComplaint();

        statusAdapter = new ArrayAdapter(getApplicationContext(), R.layout.home_list_item, complaintStatus);
        binding.viewComplaintStatus.setAdapter(statusAdapter);


        binding.updateComplaintBtn.setOnClickListener(view -> {
            selectedComplaint.setStatus(binding.viewComplaintStatus.getText().toString());
            database.getReference()
                    .child(Paths.COMPLAINT_STUDENTS)
                    .child(selectedComplaint.getStudentId())
                    .child(selectedComplaint.getComplaintId())
                    .setValue(selectedComplaint)
                    .addOnSuccessListener(unused -> {
                        database.getReference()
                                .child(Paths.COMPLAINT_ADMIN)
                                .child(selectedComplaint.getComplaintId())
                                .setValue(selectedComplaint)
                                .addOnSuccessListener(unused1 -> {
                                    dialog.dismiss();
                                    Toast.makeText(this, "Complaint Updated", Toast.LENGTH_SHORT).show();
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

    private void setComplaint() {
        binding.viewComplaintHostelName.setText(selectedComplaint.getHostel());
        binding.viewComplaintHostelName.setEnabled(false);

        binding.viewComplaintRoomNo.setText(selectedComplaint.getHostelRoomNumber());
        binding.viewComplaintRoomNo.setEnabled(false);

        binding.viewComplaintCategory.setText(selectedComplaint.getCategory());
        binding.viewComplaintCategory.setEnabled(false);

        binding.viewComplaintDes.setText(selectedComplaint.getDesc());
        binding.viewComplaintDes.setEnabled(false);

        binding.viewComplaintStatus.setText(selectedComplaint.getStatus());

    }
}