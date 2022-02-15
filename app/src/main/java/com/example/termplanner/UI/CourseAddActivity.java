package com.example.termplanner.UI;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.termplanner.Entities.Course;
import com.example.termplanner.R;
import com.example.termplanner.Repository.Repository;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CourseAddActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Repository repository;
    static int tempId;
    int courseId;
    EditText courseName;
    EditText startDate;
    EditText endDate;
    Spinner courseStatus;
    CheckBox noteOption;
    EditText courseNotes;
    EditText instructorName;
    EditText instructorEmail;
    EditText instructorPhone;



    public String statusSelected;

    String dateFormat = "MM/dd/yyyy";
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);

    Calendar calendarStart = Calendar.getInstance();
    Calendar calendarEnd = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener dateStartClick;
    DatePickerDialog.OnDateSetListener dateEndClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        getSupportActionBar().setTitle("Add Course");

        repository = new Repository(getApplication());
        courseName = findViewById(R.id.courseName);
        startDate = findViewById(R.id.courseStartDate);
        endDate = findViewById(R.id.courseEndDate);
        //Spinner
        courseStatus = findViewById(R.id.statusSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.status, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseStatus.setAdapter(adapter);
        courseStatus.setOnItemSelectedListener(this);
        //CHECKBOX FOR NOTES
        noteOption = findViewById(R.id.optionalNote);
        noteOption.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (noteOption.isChecked()) {
                    courseNotes.isEnabled();
                } else {
                    courseNotes.setEnabled(false);
                }
            }
        });

        courseNotes = findViewById(R.id.courseNotes);
        instructorName = findViewById(R.id.instructorName);
        instructorEmail = findViewById(R.id.instructorEmail);
        instructorPhone = findViewById(R.id.instructorPhone);

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

                new DatePickerDialog(CourseAddActivity.this, dateStartClick, calendarStart
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

                new DatePickerDialog(CourseAddActivity.this, dateEndClick, calendarEnd
                        .get(Calendar.YEAR), calendarEnd.get(Calendar.MONTH),
                        calendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //CANCEL BUTTON
        Button cancelCourseButton = findViewById(R.id.cancel_course_button);
        cancelCourseButton.setText("CANCEL");
        cancelCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CourseAddActivity.this, TermDetailsActivity.class));

            }
        });
    }

    public void saveCourse(View view) {
//        String name = courseName.getText().toString();
//        String start = startDate.getText().toString();
//        String end = endDate.getText().toString();
//
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
//
//            List<Course> allCourses = repository.getAllCourses();
//            int coursesList = allCourses.size();
//
//           // newCourse = new Course(courseId, name, start, end,);
//
//        //    repository.insert(newTerm);
//
//       //     Intent intent = new Intent(TermAddActivity.this, TermActivity.class);
//        //    startActivity(intent);
//
//        }
    }

    //STATUS SPINNER SELECTION
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        statusSelected = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }




}
