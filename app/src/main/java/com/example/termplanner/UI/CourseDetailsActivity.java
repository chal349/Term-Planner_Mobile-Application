package com.example.termplanner.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.termplanner.Adapters.CourseAdapter;
import com.example.termplanner.Entities.Assessment;
import com.example.termplanner.Entities.Course;
import com.example.termplanner.Entities.Term;
import com.example.termplanner.R;
import com.example.termplanner.Repository.Repository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CourseDetailsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Repository repository;
    static int tempId;
    static String tempTitle;
    static String tempStart;
    static String tempEnd;
    static String tempSpinner;
    static String tempNotes;
    static String tempInstructorName;
    static String tempInstructorEmail;
    static String tempInstructorPhone;


    static int assessmentsInCourseCount;

    int courseId;
    String spinnerValue;
    int spinnerSelectionPosition;
    public String statusSelected;

    EditText courseName;
    EditText startDate;
    EditText endDate;
    Spinner courseStatusSpinner;
    CheckBox noteOption;
    EditText courseNotes;
    EditText instructorName;
    EditText instructorEmail;
    EditText instructorPhone;


    String dateFormat = "MM/dd/yyyy";
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);

    Calendar calendarStart = Calendar.getInstance();
    Calendar calendarEnd = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener dateStartClick;
    DatePickerDialog.OnDateSetListener dateEndClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_course);
        getSupportActionBar().setTitle("Course Details");

        courseName = findViewById(R.id.courseDetailsName);
        startDate = findViewById(R.id.courseDetailsStartDate);
        endDate = findViewById(R.id.courseDetailsEndDate);
        courseStatusSpinner = findViewById(R.id.statusDetailsSpinner);
        noteOption = findViewById(R.id.optionalDetailsNoteCheckBox);
        courseNotes = findViewById(R.id.courseDetailsNotes);
        instructorName = findViewById(R.id.instructorNameCourseDetails);
        instructorEmail = findViewById(R.id.instructorEmailCourseDetails);
        instructorPhone = findViewById(R.id.instructorPhoneCourseDetails);

        // GET INTENTS AND CREATE TEMP DATA TO PASS TO ASSESSMENTS ACTIVITY IF NEEDED
        int courseId = getIntent().getIntExtra("courseId", -1);
        tempId = courseId;
        String courseTitle = getIntent().getStringExtra("title");
        tempTitle = courseTitle;
        String courseStartDate = getIntent().getStringExtra("startDate");
        tempStart = courseStartDate;
        String courseEndDate = getIntent().getStringExtra("endDate");
        tempEnd = courseEndDate;
        spinnerValue = getIntent().getStringExtra("status");
        spinnerSelectionPosition = getIntent().getIntExtra("courseStatusSelection", -1);
        tempSpinner = spinnerValue;

        //Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.status, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseStatusSpinner.setAdapter(adapter);
        courseStatusSpinner.setOnItemSelectedListener(this);


        String notes = getIntent().getStringExtra("notes");
        tempNotes = notes;
        courseNotes.setEnabled(false);
        noteOption.setChecked(true);
        if(notes.trim().isEmpty()){
            noteOption.setChecked(false);
            }else{
            courseNotes.setEnabled(true);
        }

        noteOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(noteOption.isChecked()){
                    courseNotes.setEnabled(true);
                } else {
                    courseNotes.setEnabled(false);
                    courseNotes.setText(" ");
                }

            }
        });

        String name = getIntent().getStringExtra("instructorName");
        tempInstructorName = name;
        String email = getIntent().getStringExtra("instructorEmail");
        tempInstructorEmail = email;
        String phone = getIntent().getStringExtra("instructorPhone");
        tempInstructorPhone = phone;


        courseName.setText(courseTitle);
        startDate.setText(courseStartDate);
        endDate.setText(courseEndDate);
        courseStatusSpinner.setSelection(spinnerSelectionPosition);
        courseNotes.setText(notes);
        instructorName.setText(name);
        instructorEmail.setText(email);
        instructorPhone.setText(phone);

//        repository = new Repository(getApplication());
//        List<Assessment> assessmentsInCourse = new ArrayList<>();
//
//        RecyclerView recyclerView = findViewById(R.id.);
//
//        final CourseAdapter courseAdapter = new CourseAdapter(this);
//        recyclerView.setAdapter(courseAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        for(Assessment assessment : repository.getAllAssessments()){
//            if(assessment.getId() == tempId){
//                assessmentsInCourse.add(assessment);
//            }
//        }
//        courseAdapter.setCourses(assessmentsInCourse);
//        coursesInTermCount = coursesInTerm.size();


        //START DATE PICKER
        dateStartClick = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendarStart.set(Calendar.YEAR, year);
                calendarStart.set(Calendar.MONTH, monthOfYear);
                calendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                startDate.setText(sdf.format(calendarStart.getTime()));

            }
        };

        //SHOW START CALENDAR ON CLICK
        startDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                new DatePickerDialog(CourseDetailsActivity.this, dateStartClick, calendarStart
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
                endDate.setText(sdf.format(calendarEnd.getTime()));

            }
        };

        //SHOW END CALENDAR ON CLICK
        endDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                new DatePickerDialog(CourseDetailsActivity.this, dateEndClick, calendarEnd
                        .get(Calendar.YEAR), calendarEnd.get(Calendar.MONTH),
                        calendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //CANCEL BUTTON
        Button cancelTermButton = findViewById(R.id.cancel_course_details_button);
        cancelTermButton.setText("CANCEL");
        cancelTermButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CourseDetailsActivity.this, TermActivity.class));

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        spinnerValue = parent.getItemAtPosition(position).toString();
        spinnerSelectionPosition = parent.getSelectedItemPosition();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }




//    public void saveCourseDetails(View view) {
//        int id;
//        String name = courseName.getText().toString();
//        String start = startDate.getText().toString();
//        String end = endDate.getText().toString();
//        //   int termID = termId;
//        Term updateTerm;
//
//        if (name.trim().isEmpty() || start.trim().isEmpty() || end.trim().isEmpty()) {
//
//            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
//            alertDialog.setTitle("Alert");
//            alertDialog.setMessage("All fields must be completed!");
//            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
//                    new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            dialogInterface.dismiss();
//                        }
//                    });
//            alertDialog.show();
//
//        } else {
//            List<Term> allTerms = repository.getAllTerms();
//            id = allTerms.get(allTerms.size() - 1).getTermId();
//
//            updateCourse = new Course(id, name, start, end);
//            repository.update(updateTerm);
//            Intent intent = new Intent(CourseDetailsActivity.this, TermActivity.class);
//            startActivity(intent);
//        }
//
//    }

    public void AddAssessment(View view) {
        Intent intent = new Intent(CourseDetailsActivity.this, CourseAddActivity.class);
        intent.putExtra("termId", tempId);
        intent.putExtra("title", tempTitle);
        intent.putExtra("startDate", tempStart);
        intent.putExtra("endDate", tempEnd);
        startActivity(intent);
    }


}

