package com.oysterkode.laundry.Leave;

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

public class LeaveAdapter extends RecyclerView.Adapter<LeaveAdapter.LeaveViewHolder> {
    private final Context context;
    private final ArrayList<Leave> leaves;

    public LeaveAdapter(Context context, ArrayList<Leave> leaves) {
        this.context = context;
        this.leaves = leaves;
    }

    @NonNull
    @Override
    public LeaveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.sample_leave_layout, parent, false);
        return new LeaveViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaveViewHolder holder, int position) {
        Leave leave = leaves.get(position);

        holder.binding.leaveItemDestination.setText(leave.getDestination());
        holder.binding.leaveItemReason.setText(leave.getReason());
        holder.binding.leaveStatus.setText(leave.getStatus());
        holder.binding.leaveItemDate.setText(leave.getFrom());

        holder.binding.leaveItem.setOnClickListener(view -> {
            Intent i = new Intent(context, ViewLeaveActivity.class);
            i.putExtra("selected_leave", leave);
            context.startActivity(i);
        });


    }

    @Override
    public int getItemCount() {
        return leaves.size();
    }


    public static class LeaveViewHolder extends RecyclerView.ViewHolder {

        private final SampleLeaveLayoutBinding binding;

        public LeaveViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = SampleLeaveLayoutBinding.bind(itemView);
        }
    }
}
