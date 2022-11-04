package com.oysterkode.laundry.Leave;

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
import com.oysterkode.laundry.databinding.ActivityLeaveHistoryBinding;

import java.util.ArrayList;
import java.util.Collections;

public class LeaveHistoryActivity extends AppCompatActivity {

    private ActivityLeaveHistoryBinding binding;
    private ArrayList<Leave> leaves;
    private LeaveAdapter adapter;
    private FirebaseDatabase database;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLeaveHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading ... ");
        database = FirebaseDatabase.getInstance();
        String path = Paths.LEAVE_STUDENTS + "/" + getSharedPreferences("CurrUser", MODE_PRIVATE).getString("currStudId", "");

        leaves = new ArrayList<>();
        adapter = new LeaveAdapter(this, leaves);
        binding.leaveHistory.setLayoutManager(new LinearLayoutManager(this));
        binding.leaveHistory.setAdapter(adapter);
        dialog.show();


        database.getReference()
                .child(path).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                leaves.clear();

                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Leave leave = snapshot1.getValue(Leave.class);
                    leaves.add(leave);
                }
                dialog.dismiss();

                Collections.reverse(leaves);
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