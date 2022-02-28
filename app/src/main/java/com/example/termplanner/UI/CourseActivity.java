package com.example.termplanner.UI;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.termplanner.Adapters.CourseAdapter;
import com.example.termplanner.Adapters.TermAdapter;
import com.example.termplanner.Entities.Course;
import com.example.termplanner.Entities.Term;
import com.example.termplanner.R;
import com.example.termplanner.Repository.Repository;

import java.util.List;

public class CourseActivity extends AppCompatActivity {

    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        getSupportActionBar().setTitle("Courses");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        repository = new Repository(getApplication());
        List<Course> allCourses = repository.getAllCourses();

        RecyclerView recyclerView = findViewById(R.id.courses_recycler);

        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        courseAdapter.setCourses(allCourses);

        Context context = getApplicationContext();
        CharSequence text = "To add a course, first select or create a term on the Terms screen";
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}