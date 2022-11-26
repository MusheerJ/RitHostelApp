package com.oysterkode.laundry.Admin.Leave;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.oysterkode.laundry.Leave.Leave;
import com.oysterkode.laundry.R;
import com.oysterkode.laundry.databinding.SampleLeaveLayoutBinding;

import java.util.ArrayList;

public class AdminLeaveAdapter extends RecyclerView.Adapter<AdminLeaveAdapter.AdminLeaveViewHolder> {
    private final Context context;
    private ArrayList<Leave> leaves;

    public AdminLeaveAdapter(Context context, ArrayList<Leave> leaves) {
        this.context = context;
        this.leaves = leaves;
    }

    @NonNull
    @Override
    public AdminLeaveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.sample_leave_layout, parent, false);
        return new AdminLeaveViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminLeaveViewHolder holder, int position) {
        Leave leave = leaves.get(position);
        String status = leave.getStatus();
        String studentNameAndRoom = leave.getStudentName() + "\n" + leave.getRoom();
        holder.binding.leaveItemDestination.setText(studentNameAndRoom);
        holder.binding.leaveItemReason.setText("Destination : " + leave.getDestination());
        holder.binding.leaveStatus.setText(leave.getStatus());
        holder.binding.leaveItemDate.setText(leave.getFrom());

        holder.binding.leaveItem.setOnClickListener(view -> {
            Intent i = new Intent(context, AdminViewLeaveActivity.class);
            i.putExtra("selected_leave", leave);
            context.startActivity(i);
        });


        if (status.equals(Leave.Status.APPROVED)) {
            holder.binding.leaveItem.setCardBackgroundColor(context.getResources().getColor(R.color.low_green));
            holder.binding.leaveItemReason.setTextColor(context.getResources().getColor(R.color.desc_text));
            holder.binding.leaveItemDestination.setTextColor(context.getResources().getColor(R.color.black));
            holder.binding.leaveItemDate.setTextColor(context.getResources().getColor(R.color.dark_green));
            holder.binding.leaveStatus.setChipBackgroundColor(ColorStateList.valueOf(context.getResources().getColor(R.color.dark_green)));
            holder.binding.leaveStatus.setTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
        } else if (status.equals(Leave.Status.PENDING)) {
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

    }

    @Override
    public int getItemCount() {
        return leaves.size();
    }

    public void filterList(ArrayList<Leave> filteredList) {
        leaves = filteredList;
        notifyDataSetChanged();

    }


    public static class AdminLeaveViewHolder extends RecyclerView.ViewHolder {

        private SampleLeaveLayoutBinding binding;

        public AdminLeaveViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = SampleLeaveLayoutBinding.bind(itemView);
        }
    }
}
