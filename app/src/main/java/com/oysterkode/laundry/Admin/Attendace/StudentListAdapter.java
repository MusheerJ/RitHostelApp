package com.oysterkode.laundry.Admin.Attendace;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.oysterkode.laundry.Paths;
import com.oysterkode.laundry.R;
import com.oysterkode.laundry.Student.Student;
import com.oysterkode.laundry.databinding.AttendanceMarkBinding;
import com.oysterkode.laundry.databinding.StudentAttendanceLayoutBinding;

import java.util.ArrayList;

public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.StudentListViewHolder> {

    private Context context;
    private ArrayList<Student> students;
    private FirebaseDatabase database;
    private String date;
    private String hostel;
    public int count;

    public StudentListAdapter(Context context, ArrayList<Student> students, String date, String hostel) {
        this.context = context;
        this.students = students;
        this.date = date;
        this.hostel = hostel;
    }

    public void setCount(int c) {
        this.count = c;
    }

    @NonNull
    @Override
    public StudentListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.student_attendance_layout, parent, false);
        return new StudentListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentListViewHolder holder, int position) {
        Student currentStudent = students.get(position);
        View view = LayoutInflater.from(context).inflate(R.layout.attendance_mark, null);
        AttendanceMarkBinding markBinding = AttendanceMarkBinding.bind(view);
        AlertDialog markAttendance = new AlertDialog.Builder(context)
                .setTitle(date)
                .setView(markBinding.getRoot())
                .create();

        markBinding.attendeeNameMark.setText(currentStudent.getStudentName());
        holder.binding.studentName.setText(currentStudent.getStudentName());
        holder.binding.studentRoom.setText(currentStudent.getRoomNo());
        holder.binding.layoutClick.setOnClickListener(vi -> {
            markAttendance.show();

        });

        markBinding.CancelMark.setOnClickListener(v1 -> {
            markAttendance.dismiss();
        });

        markBinding.SaveMark.setOnClickListener(v2 -> {
            Attendance attendance = new Attendance();
            database = FirebaseDatabase.getInstance();
            attendance.setStudentName(currentStudent.getStudentName());
            attendance.setDate(date);
            attendance.setRoom(currentStudent.getRoomNo());
            attendance.setHostel(currentStudent.getHostel());

            holder.binding.circle.setVisibility(View.VISIBLE);

            if (markBinding.presentButton.isChecked()) {
                holder.binding.circle.setCardBackgroundColor(Color.parseColor("#11D853"));
                holder.binding.status.setText("P");
                attendance.setStatus(Attendance.Status.PRESENT);
            } else {
                holder.binding.circle.setCardBackgroundColor(Color.parseColor("#FB0000"));
                holder.binding.status.setText("A");
                attendance.setStatus(Attendance.Status.ABSENT);
            }

            database.getReference()
                    .child(Paths.ATTENDANCE_ADMIN)
                    .child(date)
                    .child(currentStudent.getStudentId())
                    .setValue(attendance)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            database.getReference()
                                    .child(Paths.ATTENDANCE_STUDENT)
                                    .child(currentStudent.getStudentId())
                                    .child(date)
                                    .setValue(attendance)
                                    .addOnSuccessListener(unused1 -> {
                                        Toast.makeText(context, "Attendance marked", Toast.LENGTH_SHORT).show();
//                                        AttendanceStudentListActivity.count--;
                                        count--;


                                    });
                        }
                    });


            markAttendance.dismiss();
        });


    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public boolean isAllMarked() {
        return count == 0;
    }

    public int getCount() {
        return count;
    }

    public static class StudentListViewHolder extends RecyclerView.ViewHolder {

        StudentAttendanceLayoutBinding binding;

        public StudentListViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = StudentAttendanceLayoutBinding.bind(itemView);
        }
    }


}
