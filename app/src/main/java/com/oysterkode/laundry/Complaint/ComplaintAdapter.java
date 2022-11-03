package com.oysterkode.laundry.Complaint;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.oysterkode.laundry.R;
import com.oysterkode.laundry.databinding.SampleLeaveLayoutBinding;

import java.util.ArrayList;

public class ComplaintAdapter extends RecyclerView.Adapter<ComplaintAdapter.ComplaintViewHolder> {
    private final Context context;
    private final ArrayList<Complaint> complaints;

    public ComplaintAdapter(Context context, ArrayList<Complaint> complaints) {
        this.context = context;
        this.complaints = complaints;
    }

    @NonNull
    @Override
    public ComplaintViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_leave_layout, parent, false);
        return new ComplaintViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComplaintViewHolder holder, int position) {
        Complaint complaint = complaints.get(position);
        String hostelAndRoom = complaint.getHostel() + " - " + complaint.getHostelRoomNumber();
        String dateAndTime = complaint.getTime() + " " + complaint.getDate();
        String categoryAndDesc = complaint.getCategory() + "\n" + complaint.getDesc();
        holder.binding.leaveItemDestination.setText(hostelAndRoom);
        holder.binding.leaveItemReason.setText(categoryAndDesc);
        holder.binding.leaveStatus.setText(complaint.getStatus());
        holder.binding.leaveItemDate.setText(dateAndTime);


        holder.binding.leaveItem.setOnClickListener(view -> {
            Intent i = new Intent(context, ViewComplaintActivity.class);
            i.putExtra("selected_complaint", complaint);
            context.startActivity(i);
        });

    }

    @Override
    public int getItemCount() {
        return complaints.size();
    }


    public static class ComplaintViewHolder extends RecyclerView.ViewHolder {

        private final SampleLeaveLayoutBinding binding;

        public ComplaintViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = SampleLeaveLayoutBinding.bind(itemView);
        }
    }


}
