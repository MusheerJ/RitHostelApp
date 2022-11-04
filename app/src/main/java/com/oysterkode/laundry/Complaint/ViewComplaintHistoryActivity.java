
package com.oysterkode.laundry.Complaint;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.oysterkode.laundry.Paths;
import com.oysterkode.laundry.databinding.ActivityViewComplaintHistoryBinding;

import java.util.ArrayList;
import java.util.Collections;

public class ViewComplaintHistoryActivity extends AppCompatActivity {

    private ActivityViewComplaintHistoryBinding binding;
    private FirebaseDatabase database;
    private ComplaintAdapter adapter;
    private ArrayList<Complaint> complaints;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityViewComplaintHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();


        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading ... ");
        database = FirebaseDatabase.getInstance();


        complaints = new ArrayList<>();
        adapter = new ComplaintAdapter(this, complaints);
        binding.complaintRecylerView.setLayoutManager(new LinearLayoutManager(this));
        binding.complaintRecylerView.setAdapter(adapter);

        dialog.show();


        database.getReference()
                .child(Paths.COMPLAINT_STUDENTS)
                .child(getSharedPreferences("CurrUser", MODE_PRIVATE).getString("currStudId", ""))
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        complaints.clear();
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            Complaint complaint = snapshot1.getValue(Complaint.class);
                            complaints.add(complaint);
                        }
                        dialog.dismiss();
                        Collections.reverse(complaints);
                        adapter.notifyDataSetChanged();


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}