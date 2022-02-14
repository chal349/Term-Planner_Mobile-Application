package com.example.termplanner.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.example.termplanner.R;

public class AssessmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment);
        getSupportActionBar().setTitle("Assessments");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}