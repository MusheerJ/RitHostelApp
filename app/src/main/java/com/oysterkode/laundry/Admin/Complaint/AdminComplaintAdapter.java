package com.oysterkode.laundry.Admin.Complaint;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.oysterkode.laundry.Complaint.Complaint;
import com.oysterkode.laundry.R;
import com.oysterkode.laundry.databinding.SampleLeaveLayoutBinding;

import java.util.ArrayList;

public class AdminComplaintAdapter extends RecyclerView.Adapter<AdminComplaintAdapter.AdminComplaintViewHolder> {
    private final Context context;
    private ArrayList<Complaint> complaints;

    public AdminComplaintAdapter(Context context, ArrayList<Complaint> complaints) {
        this.context = context;
        this.complaints = complaints;
    }

    @NonNull
    @Override
    public AdminComplaintViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_leave_layout, parent, false);
        return new AdminComplaintViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminComplaintViewHolder holder, int position) {
        Complaint complaint = complaints.get(position);
        String hostelAndRoom = complaint.getHostel() + " - " + complaint.getHostelRoomNumber();
        String dateAndTime = complaint.getTime() + " " + complaint.getDate();
        String categoryAndDesc = complaint.getCategory() + "\n" + complaint.getDesc();
        String status = complaint.getStatus();

        holder.binding.leaveItemDestination.setText(hostelAndRoom);
        holder.binding.leaveItemReason.setText(categoryAndDesc);
        holder.binding.leaveStatus.setText(complaint.getStatus());
        holder.binding.leaveItemDate.setText(dateAndTime);

        if (status.equals(Complaint.Status.RESOLVED)) {
            holder.binding.leaveItem.setCardBackgroundColor(context.getResources().getColor(R.color.low_green));
            holder.binding.leaveItemReason.setTextColor(context.getResources().getColor(R.color.desc_text));
            holder.binding.leaveItemDestination.setTextColor(context.getResources().getColor(R.color.black));
            holder.binding.leaveItemDate.setTextColor(context.getResources().getColor(R.color.dark_green));
            holder.binding.leaveStatus.setChipBackgroundColor(ColorStateList.valueOf(context.getResources().getColor(R.color.dark_green)));
            holder.binding.leaveStatus.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
        } else if (status.equals(Complaint.Status.IN_PROGRESS)) {
            holder.binding.leaveItem.setCardBackgroundColor(context.getResources().getColor(R.color.low_yellow));
            holder.binding.leaveItemDate.setTextColor(context.getResources().getColor(R.color.dark_yellow));
            holder.binding.leaveItemReason.setTextColor(context.getResources().getColor(R.color.desc_text));
            holder.binding.leaveItemDestination.setTextColor(context.getResources().getColor(R.color.black));
            holder.binding.leaveStatus.setChipBackgroundColor(ColorStateList.valueOf(context.getResources().getColor(R.color.dark_yellow)));
            holder.binding.leaveStatus.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));

        } else {
            holder.binding.leaveItem.setCardBackgroundColor(context.getResources().getColor(R.color.low_red));
            holder.binding.leaveItemDate.setTextColor(context.getResources().getColor(R.color.dark_red));
            holder.binding.leaveItemDestination.setTextColor(context.getResources().getColor(R.color.black));
            holder.binding.leaveItemReason.setTextColor(context.getResources().getColor(R.color.desc_text));
            holder.binding.leaveStatus.setChipBackgroundColor(ColorStateList.valueOf(context.getResources().getColor(R.color.dark_red)));
            holder.binding.leaveStatus.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));

        }


        holder.binding.leaveItem.setOnClickListener(view -> {
            Intent i = new Intent(context, AdminViewComplaintActivity.class);
            i.putExtra("selected_complaint", complaint);
            context.startActivity(i);
        });

    }

    @Override
    public int getItemCount() {
        return complaints.size();
    }

    public void filterList(ArrayList<Complaint> filteredList) {
        complaints = filteredList;
        notifyDataSetChanged();
    }

    public static class AdminComplaintViewHolder extends RecyclerView.ViewHolder {

        private final SampleLeaveLayoutBinding binding;

        public AdminComplaintViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = SampleLeaveLayoutBinding.bind(itemView);
        }
    }


}
