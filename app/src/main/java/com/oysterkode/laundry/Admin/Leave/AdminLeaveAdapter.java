package com.oysterkode.laundry.Admin.Leave;

import android.content.Context;
import android.content.Intent;
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
    private final ArrayList<Leave> leaves;

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

        holder.binding.leaveItemDestination.setText(leave.getStudentName());
        holder.binding.leaveItemReason.setText("Destination : " + leave.getDestination());
        holder.binding.leaveStatus.setText(leave.getStatus());
        holder.binding.leaveItemDate.setText(leave.getFrom());

        holder.binding.leaveItem.setOnClickListener(view -> {
            Intent i = new Intent(context, AdminViewLeaveActivity.class);
            i.putExtra("selected_leave", leave);
            context.startActivity(i);
        });

    }

    @Override
    public int getItemCount() {
        return leaves.size();
    }


    public static class AdminLeaveViewHolder extends RecyclerView.ViewHolder {

        private SampleLeaveLayoutBinding binding;

        public AdminLeaveViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = SampleLeaveLayoutBinding.bind(itemView);
        }
    }
}
