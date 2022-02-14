package com.example.termplanner.UI;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.termplanner.R;
import com.example.termplanner.Repository.Repository;

public class CourseActivity extends AppCompatActivity {

    Repository repository;
    String title;
    String startDate;
    String endDate;
    String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        getSupportActionBar().setTitle("Courses");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}