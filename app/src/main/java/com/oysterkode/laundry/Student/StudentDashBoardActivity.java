package com.oysterkode.laundry.Student;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.oysterkode.laundry.Admin.AdminDashBoardActivity;
import com.oysterkode.laundry.Complaint.ComplaintRegistrationActivity;
import com.oysterkode.laundry.Complaint.ViewComplaintHistoryActivity;
import com.oysterkode.laundry.Laundry.LaundryDetailsActivity;
import com.oysterkode.laundry.Leave.ApplyLeaveActivity;
import com.oysterkode.laundry.Leave.LeaveHistoryActivity;
import com.oysterkode.laundry.R;
import com.oysterkode.laundry.Utils.LoginActivity;
import com.oysterkode.laundry.databinding.ActivityStudentDashBoardBinding;

public class StudentDashBoardActivity extends AppCompatActivity {

    private ActivityStudentDashBoardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStudentDashBoardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        getSupportActionBar().hide();
        binding.Laundry.setOnClickListener((view) -> {
            startActivity(new Intent(this, LaundryDetailsActivity.class));
        });


        binding.Leave.setOnClickListener((view) -> {
            startActivity(new Intent(this, ApplyLeaveActivity.class));
        });

        binding.Complaint.setOnClickListener((view) -> {
            startActivity(new Intent(this, ComplaintRegistrationActivity.class));
        });

        binding.teamMBA.setOnClickListener(view -> {
            startActivity(new Intent(this, AdminDashBoardActivity.class));
        });

        binding.menu.setOnClickListener(view -> {

            PopupMenu popupMenu = new PopupMenu(getApplicationContext(), binding.menu);
            popupMenu.getMenuInflater().inflate(R.menu.main_menu, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(menuItem -> {
                if (menuItem.getItemId() == R.id.leaveDetails) {
                    Intent intent = new Intent(getApplicationContext(), LeaveHistoryActivity.class);
                    startActivity(intent);
                    return true;
                }

                if (menuItem.getItemId() == R.id.complaintDetails) {

                    Intent intent = new Intent(getApplicationContext(), ViewComplaintHistoryActivity.class);
                    startActivity(intent);
                    return true;

                }

                if (menuItem.getItemId() == R.id.studentProfile) {
                    Intent intent = new Intent(getApplicationContext(), StudentProfileActivity.class);
                    startActivity(intent);
                    return true;
                }

                if (menuItem.getItemId() == R.id.studentLogout) {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(StudentDashBoardActivity.this, LoginActivity.class));
                    finishAffinity();
                    return true;
                }
                return false;
            });

            popupMenu.show();
        });


    }
}