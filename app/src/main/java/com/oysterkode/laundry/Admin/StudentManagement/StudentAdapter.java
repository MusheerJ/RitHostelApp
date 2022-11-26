package com.oysterkode.laundry.Admin.StudentManagement;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.oysterkode.laundry.R;
import com.oysterkode.laundry.Student.Student;
import com.oysterkode.laundry.databinding.SampleStudentLayoutBinding;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {
    private final Context context;
    private ArrayList<Student> students;


    public StudentAdapter(Context context, ArrayList<Student> students) {
        this.context = context;
        this.students = students;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_student_layout, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {

        Student student = students.get(position);
        holder.binding.studentName.setText(student.getStudentName());
        holder.binding.studentPrn.setText(student.getStudentId());
        holder.binding.studentRoom.setText(student.getRoomNo());


        holder.binding.layoutClick.setOnClickListener(v -> {
            Intent i = new Intent(context, ViewStudentActivity.class);
            i.putExtra("selected_student", student);
            context.startActivity(i);
        });


    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public void filterList(ArrayList<Student> filteredList) {
        students = filteredList;
        notifyDataSetChanged();
    }

    public static class StudentViewHolder extends RecyclerView.ViewHolder {

        private final SampleStudentLayoutBinding binding;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = SampleStudentLayoutBinding.bind(itemView);
        }
    }
}
