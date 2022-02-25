package com.example.termplanner.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.termplanner.R;

public class MainMenuActivity extends AppCompatActivity {
    public static int numAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Button termButton = findViewById(R.id.termsBtn);
        termButton.setText("TERMS");
        termButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainMenuActivity.this, TermActivity.class));

            }
        });

        Button courseButton = findViewById(R.id.coursesBtn);
        courseButton.setText("COURSES");
        courseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainMenuActivity.this, CourseActivity.class));

            }
        });

        Button assessmentButton = findViewById(R.id.assessmentsBtn);
        assessmentButton.setText("ASSESSMENTS");
        assessmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainMenuActivity.this, AssessmentActivity.class));

            }
        });


    }
}