package com.oysterkode.laundry.Admin.Leave;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

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


        binding.searchComplaint.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {

                    binding.titleText.setVisibility(View.INVISIBLE);
                    binding.searchComplaint.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;

                    binding.backButton.setVisibility(View.GONE);
                } else {
                    binding.titleText.setVisibility(View.VISIBLE);
                    binding.searchComplaint.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
                    binding.backButton.setVisibility(View.VISIBLE);
                }

            }
        });


        binding.searchComplaint.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });

        binding.backButton.setOnClickListener(v -> finish());


    }

    private void filter(String keyword) {
        ArrayList<Leave> filteredList = new ArrayList<>();
        if (keyword.isEmpty()) {
            filteredList.addAll(leaves);

        } else {
            for (Leave leave : leaves) {
                String from = leave.getFrom().toLowerCase();
                String to = leave.getTo().toLowerCase();
                String prn = leave.getStudentId().toLowerCase();
                String name = leave.getStudentName().toLowerCase();
                String destination = leave.getDestination().toLowerCase();
                String status = leave.getStatus().toLowerCase();
//                Log.d("TAG", "filter: " + hostel);
                if (from.contains(keyword) || prn.contains(keyword) || to.contains(keyword) || prn.contains(keyword) || name.contains(keyword)
                        || destination.contains(keyword) || status.contains(keyword)) {
                    filteredList.add(leave);
                }

            }
        }
        adapter.filterList(filteredList);

    }
}