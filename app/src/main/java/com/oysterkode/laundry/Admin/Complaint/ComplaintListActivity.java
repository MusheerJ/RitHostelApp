
package com.oysterkode.laundry.Admin.Complaint;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
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
import com.oysterkode.laundry.Complaint.Complaint;
import com.oysterkode.laundry.Paths;
import com.oysterkode.laundry.databinding.ActivityComplaintListBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class ComplaintListActivity extends AppCompatActivity {

    private ActivityComplaintListBinding binding;
    private FirebaseDatabase database;
    private AdminComplaintAdapter adapter;
    private ArrayList<Complaint> complaints;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityComplaintListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Objects.requireNonNull(getSupportActionBar()).hide();

        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading ... ");
        database = FirebaseDatabase.getInstance();

        complaints = new ArrayList<>();
        adapter = new AdminComplaintAdapter(this, complaints);
        binding.adminComplaintRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.adminComplaintRecycler.setAdapter(adapter);

        dialog.show();
        database.getReference()
                .child(Paths.COMPLAINT_ADMIN)
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
                filter(newText.toLowerCase());
                return false;
            }
        });

    }

    private void filter(String keyword) {
        ArrayList<Complaint> filteredList = new ArrayList<>();
        if (keyword.isEmpty()) {
            filteredList.addAll(complaints);

        } else {
            for (Complaint complaint : complaints) {
                String date = complaint.getDate().toLowerCase();
                String prn = complaint.getStudentId().toLowerCase();
                String hostel = complaint.getHostel().toLowerCase();
                String desc = complaint.getDesc().toLowerCase();
                String category = complaint.getCategory().toLowerCase();
                String room = complaint.getHostelRoomNumber();
                Log.d("TAG", "filter: " + hostel);
                if (date.contains(keyword) || prn.contains(keyword) || hostel.contains(keyword) || desc.contains(keyword) || category.contains(keyword)
                        || room.contains(keyword)) {
                    filteredList.add(complaint);
                }

            }
        }
        adapter.filterList(filteredList);
    }


}