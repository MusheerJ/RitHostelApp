package com.oysterkode.laundry.Admin.Attendace;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.oysterkode.laundry.R;
import com.oysterkode.laundry.databinding.StudentAttendanceLayoutBinding;

import java.util.ArrayList;

public class ViewAttendanceAdapter extends RecyclerView.Adapter<ViewAttendanceAdapter.ViewAttendanceViewHolder> {

    private Context context;
    private ArrayList<Attendance> attendances;
    private String date;
    private String hostel;

    public ViewAttendanceAdapter(Context context, ArrayList<Attendance> attendances, String date, String hostel) {
        this.context = context;
        this.attendances = attendances;
        this.date = date;
        this.hostel = hostel;
    }

    public ViewAttendanceAdapter(Context context, ArrayList<Attendance> attendances) {
        this.context = context;
        this.attendances = attendances;
    }

    @NonNull
    @Override
    public ViewAttendanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.student_attendance_layout, parent, false);
        return new ViewAttendanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewAttendanceViewHolder holder, int position) {
        Attendance attendance = attendances.get(position);
        String roomAndDate = attendance.getRoom() + "\n" + attendance.getDate();
        holder.binding.studentName.setText(attendance.getStudentName());
        holder.binding.studentRoom.setText(roomAndDate);

        holder.binding.circle.setVisibility(View.VISIBLE);
        if (attendance.getStatus().equals(Attendance.Status.PRESENT)) {
            holder.binding.circle.setCardBackgroundColor(Color.parseColor("#11D853"));
            holder.binding.status.setText("P");

        } else {
            holder.binding.circle.setCardBackgroundColor(Color.parseColor("#FB0000"));
            holder.binding.status.setText("A");
        }

    }

    @Override
    public int getItemCount() {
        return attendances.size();
    }

    public static class ViewAttendanceViewHolder extends RecyclerView.ViewHolder {

        private StudentAttendanceLayoutBinding binding;

        public ViewAttendanceViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = StudentAttendanceLayoutBinding.bind(itemView);
        }
    }
}
