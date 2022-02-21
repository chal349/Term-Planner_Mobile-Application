package com.example.termplanner.UI;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.example.termplanner.Entities.Assessment;
import com.example.termplanner.Entities.Course;
import com.example.termplanner.R;
import com.example.termplanner.Repository.Repository;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AssessmentAddDetailsActivity extends AppCompatActivity {

    private Repository repository;
    Assessment selectedAssessment;
    int assessmentId;
    String radioSelection;
    EditText assessmentTitle;
    EditText assessmentStartDate;
    EditText assessmentEndDate;
    RadioGroup radioGroup;
    RadioButton performance;
    RadioButton objective;


    static int tempTermId;
    public static String tempTermTitle;
    public static String tempTermStart;
    public static String tempTermEnd;
    static int tempCourseId;
    static String tempCourseTitle;
    static String tempCourseStart;
    static String tempCourseEnd;
    static String tempSpinner;
    static int tempSpinnerSelection;
    static String tempNotes;
    static String tempInstructorName;
    static String tempInstructorEmail;
    static String tempInstructorPhone;
    static int tempAssessmentId;
    static String tempAssessmentStartDate;
    static String tempAssessmentEndDate;
    static String tempRadioSelection;

    String dateFormat = "MM/dd/yyyy";
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);

    Calendar calendarStart = Calendar.getInstance();
    Calendar calendarEnd = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener dateStartClick;
    DatePickerDialog.OnDateSetListener dateEndClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_add_assessment);
        getSupportActionBar().setTitle("Assessment Details");

        repository = new Repository(getApplication());
        assessmentTitle = findViewById(R.id.assessmentTitle);
        assessmentStartDate = findViewById(R.id.assessmentStartDate);
        assessmentEndDate = findViewById(R.id.assessmentEndDate);
        radioGroup = findViewById(R.id.radioGroup);
        performance = findViewById(R.id.performance);
        objective = findViewById(R.id.objective);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int radioId) {
                if (performance.isChecked()) {
                    radioSelection.equals("PA");
                } else {
                    radioSelection.equals("OA");
                }
            }
        });

        assessmentId = getIntent().getIntExtra("assessmentId", -1);
        tempAssessmentId = assessmentId;
        List<Assessment> allAssessments = repository.getAllAssessments();
        for (Assessment assessment : allAssessments) {
            if (assessment.getAssessmentId() == assessmentId) {
                selectedAssessment = assessment;
            }
        }

        tempTermId = getIntent().getIntExtra("termId", -1);
        tempTermTitle = getIntent().getStringExtra("termTitle");
        tempTermStart = getIntent().getStringExtra("termStartDate");
        tempTermEnd = getIntent().getStringExtra("termEndDate");
        tempCourseId = getIntent().getIntExtra("courseId", -1);
        tempCourseTitle = getIntent().getStringExtra("courseTitle");
        tempCourseStart = getIntent().getStringExtra("courseStartDate");
        tempCourseEnd = getIntent().getStringExtra("courseEndDate");
        tempSpinner = getIntent().getStringExtra("courseStatus");
        tempSpinnerSelection = getIntent().getIntExtra("courseStatusSelection", -1);
        tempNotes = getIntent().getStringExtra("courseNotes");
        tempInstructorName = getIntent().getStringExtra("instructorName");
        tempInstructorEmail = getIntent().getStringExtra("instructorEmail");
        tempInstructorPhone = getIntent().getStringExtra("instructorPhone");


        //START DATE PICKER
        dateStartClick = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendarStart.set(Calendar.YEAR, year);
                calendarStart.set(Calendar.MONTH, monthOfYear);
                calendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                assessmentStartDate.setText(sdf.format(calendarStart.getTime()));

            }
        };

        //SHOW START CALENDAR ON CLICK
        assessmentStartDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                new DatePickerDialog(AssessmentAddDetailsActivity.this, dateStartClick, calendarStart
                        .get(Calendar.YEAR), calendarStart.get(Calendar.MONTH),
                        calendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //END DATE PICKER
        dateEndClick = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendarEnd.set(Calendar.YEAR, year);
                calendarEnd.set(Calendar.MONTH, monthOfYear);
                calendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                assessmentEndDate.setText(sdf.format(calendarEnd.getTime()));

            }
        };

        //SHOW END CALENDAR ON CLICK
        assessmentEndDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                new DatePickerDialog(AssessmentAddDetailsActivity.this, dateEndClick, calendarEnd
                        .get(Calendar.YEAR), calendarEnd.get(Calendar.MONTH),
                        calendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    public void saveAssessment(View view) {

        Assessment assessment;
        String name = assessmentTitle.getText().toString();
        String selection = radioSelection;
        String start = assessmentStartDate.getText().toString();
        String end = assessmentEndDate.getText().toString();



        if (name.trim().isEmpty() || start.trim().isEmpty() || end.trim().isEmpty()) {

            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Alert");
            alertDialog.setMessage("All fields must be completed!");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
            alertDialog.show();

        } else {



            assessment = new Assessment(assessmentId, name, selection, start, end, tempCourseId, tempCourseTitle,
                    tempCourseStart, tempCourseEnd, tempSpinner, tempSpinnerSelection, tempInstructorName, tempInstructorEmail,
                    tempInstructorPhone, tempNotes, tempTermId, tempTermTitle, tempTermStart, tempTermEnd);

            repository.insert(assessment);

            Intent intent = new Intent(AssessmentAddDetailsActivity.this, CourseDetailsActivity.class);
            intent.putExtra("courseId", tempCourseId);
            intent.putExtra("termId", tempTermId);
            intent.putExtra("termTitle", tempTermTitle);
            intent.putExtra("termStartDate", tempTermStart);
            intent.putExtra("termEndDate", tempTermEnd);
            intent.putExtra("courseTitle", tempCourseTitle);
            intent.putExtra("courseStartDate", tempCourseStart);
            intent.putExtra("courseEndDate", tempCourseEnd);
            intent.putExtra("courseStatus", tempSpinner);
            intent.putExtra("courseStatusSelection", tempSpinnerSelection);
            intent.putExtra("courseNotes", tempNotes);
            intent.putExtra("instructorName", tempInstructorName);
            intent.putExtra("instructorEmail", tempInstructorEmail);
            intent.putExtra("instructorPhone", tempInstructorPhone);
            startActivity(intent);
        }
    }
}
