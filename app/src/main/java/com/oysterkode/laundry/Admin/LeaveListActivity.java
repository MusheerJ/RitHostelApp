package com.oysterkode.laundry.Admin;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.oysterkode.laundry.Leave.Leave;
import com.oysterkode.laundry.Paths;
import com.oysterkode.laundry.databinding.ActivityLeaveListBinding;

import java.util.ArrayList;
import java.util.Collections;

public class LeaveListActivity extends AppCompatActivity {


    private ActivityLeaveListBinding binding;
    private FirebaseDatabase database;
    private ArrayList<Leave> leaves;
    private AdminLeaveAdapter adapter;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLeaveListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();


        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading ...");

        database = FirebaseDatabase.getInstance();

        leaves = new ArrayList<>();
        adapter = new AdminLeaveAdapter(this, leaves);


        binding.adminLeaveRecyler.setLayoutManager(new LinearLayoutManager(this));
        binding.adminLeaveRecyler.setAdapter(adapter);
        dialog.show();


        database.getReference()
                .child(Paths.LEAVE_ADMIN)
                .addValueEventListener(new ValueEventListener() {
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


    }
}