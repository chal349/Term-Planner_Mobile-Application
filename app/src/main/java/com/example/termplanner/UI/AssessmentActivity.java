package com.example.termplanner.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.termplanner.Adapters.AssessmentAdapter;
import com.example.termplanner.Adapters.CourseAdapter;
import com.example.termplanner.Entities.Assessment;
import com.example.termplanner.Entities.Course;
import com.example.termplanner.R;
import com.example.termplanner.Repository.Repository;

import java.util.List;

public class AssessmentActivity extends AppCompatActivity {

    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment);
        getSupportActionBar().setTitle("Assessments");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        repository = new Repository(getApplication());
        List<Assessment> allAssessments = repository.getAllAssessments();

        RecyclerView recyclerView = findViewById(R.id.assessments_recycler);

        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        assessmentAdapter.setAssessments(allAssessments);

    }
}