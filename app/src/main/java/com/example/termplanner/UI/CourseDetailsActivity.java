package com.example.termplanner.UI;

import static com.example.termplanner.UI.TermDetailsActivity.tempEnd;
import static com.example.termplanner.UI.TermDetailsActivity.tempId;
import static com.example.termplanner.UI.TermDetailsActivity.tempStart;
import static com.example.termplanner.UI.TermDetailsActivity.tempTitle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.termplanner.Adapters.AssessmentAdapter;
import com.example.termplanner.Entities.Assessment;
import com.example.termplanner.Entities.Course;
import com.example.termplanner.R;
import com.example.termplanner.Repository.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CourseDetailsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Repository repository;
    Course selectedCourse;
    static int tempCourseId;
    static int tempTermId;
    String tempTermTitle;
    String tempTermStart;
    String tempTermEnd;
    static String tempCourseTitle;
    static String tempCourseStart;
    static String tempCourseEnd;
    static String tempSpinner;
    static int tempSpinnerSelection;
    static String tempNotes;
    static String tempInstructorName;
    static String tempInstructorEmail;
    static String tempInstructorPhone;


    static int assessmentsInCourseCount;

    int courseId;
    int termId;
    String termTitle;
    String termStart;
    String termEnd;


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

        repository = new Repository(getApplication());


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
        courseId = getIntent().getIntExtra("courseId", -1);
        tempCourseId = courseId;
        List<Course> allCourses = repository.getAllCourses();
        for (Course course : allCourses) {
            if (course.getCourseId() == courseId) {
                selectedCourse = course;
            }
        }


        termId = getIntent().getIntExtra("termId", -1);
        tempTermId = termId;
        termTitle = getIntent().getStringExtra("termTitle");
        tempTermTitle = termTitle;
        termStart = getIntent().getStringExtra("termStartDate");
        tempTermStart = termStart;
        termEnd = getIntent().getStringExtra("termEndDate");
        tempTermEnd = termEnd;

//        Button addAssessmentButton = findViewById(R.id.addAssessment);
//        if(TermDetailsActivity.tempId == -1 || tempId == 0 || tempId != tempTermId){
//            addAssessmentButton.setVisibility(View.INVISIBLE);
//        }

        String courseTitle = getIntent().getStringExtra("courseTitle");
        tempCourseTitle = courseTitle;
        String courseStartDate = getIntent().getStringExtra("courseStartDate");
        tempCourseStart = courseStartDate;
        String courseEndDate = getIntent().getStringExtra("courseEndDate");
        tempCourseEnd = courseEndDate;
        spinnerValue = getIntent().getStringExtra("courseStatus");
        tempSpinner = spinnerValue;
        spinnerSelectionPosition = getIntent().getIntExtra("courseStatusSelection", -1);
        tempSpinnerSelection = spinnerSelectionPosition;


        //Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.status, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseStatusSpinner.setAdapter(adapter);
        courseStatusSpinner.setOnItemSelectedListener(this);


        String notes = getIntent().getStringExtra("courseNotes");
        tempNotes = notes;

       // noteOption.setChecked(false);
        if (tempNotes == null || tempNotes.trim().isEmpty()) {
            courseNotes.setEnabled(false);
            noteOption.setChecked(false);
        } else {
            courseNotes.setEnabled(true);
            noteOption.setChecked(true);
        }

        noteOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (noteOption.isChecked()) {
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


        List<Assessment> assessmentsInCourse = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.course_with_assessments_recycler);

        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this, assessmentsInCourse);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        for (Assessment assessment : repository.getAllAssessments()) {
            if (assessment.getCourseId() == tempCourseId) {
                assessmentsInCourse.add(assessment);
            }
        }
        TextView assessmentsHeader = findViewById(R.id.assessments);
        if(assessmentsInCourse.size() < 1){
            assessmentsHeader.setVisibility(View.INVISIBLE);
        }
        assessmentAdapter.setAssessments(assessmentsInCourse);
        assessmentsInCourseCount = assessmentsInCourse.size();


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

    }

    ///////////////////////////////////On Create End


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.shareNote:
                Intent notesIntent = new Intent();
                notesIntent.setAction(Intent.ACTION_SEND);
                notesIntent.putExtra(Intent.EXTRA_TEXT, courseNotes.getText().toString());
                notesIntent.putExtra(Intent.EXTRA_TITLE, "Notes from - " + courseName.getText().toString());
                notesIntent.setType("text/plain");
                Intent noteIntentChooser = Intent.createChooser(notesIntent, null);
                startActivity(noteIntentChooser);
                return true;
            case R.id.notifyStartAlert:
                String startDateFromScreen = startDate.getText().toString();
                Date myStartDate = null;
                try {
                    myStartDate = sdf.parse(startDateFromScreen);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long startTrigger = myStartDate.getTime();
                Intent intentStart = new Intent(CourseDetailsActivity.this, Receiver.class);
                intentStart.putExtra("key", courseName.getText().toString() + " starts today!");
                PendingIntent senderStart = PendingIntent.getBroadcast(CourseDetailsActivity.this, MainMenuActivity.numAlert++, intentStart, PendingIntent.FLAG_MUTABLE);
                AlarmManager alarmManagerStart = (AlarmManager)  getSystemService(Context.ALARM_SERVICE);
                alarmManagerStart.set(AlarmManager.RTC_WAKEUP, startTrigger, senderStart);
                return true;
            case R.id.notifyEndAlert:
                String endDateFromScreen = endDate.getText().toString();
                Date myEndDate = null;
                try {
                    myEndDate = sdf.parse(endDateFromScreen);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long endTrigger = myEndDate.getTime();
                Intent intentEnd = new Intent(CourseDetailsActivity.this, Receiver.class);
                intentEnd.putExtra("key", courseName.getText().toString() + " ends today!");
                PendingIntent senderEnd = PendingIntent.getBroadcast(CourseDetailsActivity.this, MainMenuActivity.numAlert++, intentEnd, PendingIntent.FLAG_MUTABLE);
                AlarmManager alarmManagerEnd = (AlarmManager)  getSystemService(Context.ALARM_SERVICE);
                alarmManagerEnd.set(AlarmManager.RTC_WAKEUP, endTrigger, senderEnd);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        spinnerValue = parent.getItemAtPosition(position).toString();
        spinnerSelectionPosition = parent.getSelectedItemPosition();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    public void saveCourseDetails(View view) {

        Course updateCourse;
        String name = courseName.getText().toString();
        String start = startDate.getText().toString();
        String end = endDate.getText().toString();
        String instructor = instructorName.getText().toString();
        String phone = instructorPhone.getText().toString();
        String email = instructorEmail.getText().toString();
        String noteContent = courseNotes.getText().toString();


        if (name.trim().isEmpty() || start.trim().isEmpty() || end.trim().isEmpty() ||
                spinnerValue.isEmpty() || instructor.trim().isEmpty() || email.trim().isEmpty() ||
                phone.trim().isEmpty()) {

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
                updateCourse = new Course(courseId, name, start, end, spinnerValue, spinnerSelectionPosition, instructor, phone, email, noteContent, tempTermId);
                repository.update(updateCourse);

            if (tempId == -1 || tempId != tempTermId) {
                Intent intent = new Intent(CourseDetailsActivity.this, CourseActivity.class);
                startActivity(intent);
            } else {
            Intent intent = new Intent(CourseDetailsActivity.this, TermDetailsActivity.class);
                intent.putExtra("termId", tempId);
                intent.putExtra("termTitle", tempTitle);
                intent.putExtra("termStartDate", tempStart);
                intent.putExtra("termEndDate", tempEnd);
                startActivity(intent);
      }}

    }

    public void AddAssessment(View view) {

        Intent intent = new Intent(CourseDetailsActivity.this, AssessmentAddDetailsActivity.class);
        intent.putExtra("courseId", tempCourseId);
        intent.putExtra("termId", tempTermId);
        intent.putExtra("termTitle", tempTitle);
        intent.putExtra("termStartDate", tempStart);
        intent.putExtra("termEndDate", tempEnd);
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


    public void DeleteCourse(View view) {
        if (assessmentsInCourseCount > 0) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Alert");
            alertDialog.setMessage("Course has Assessments assigned to it.\nAssessments must be deleted before deleting a course!");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
            alertDialog.show();
        } else {
            repository.delete(selectedCourse);
            Context context = getApplicationContext();
            CharSequence text = courseName.getText() + " has been deleted!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            if (tempId == -1 || tempId != tempTermId) {
                Intent intent = new Intent(CourseDetailsActivity.this, CourseActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(CourseDetailsActivity.this, TermDetailsActivity.class);
                intent.putExtra("termId", tempId);
                intent.putExtra("termTitle", tempTitle);
                intent.putExtra("termStartDate", tempStart);
                intent.putExtra("termEndDate", tempEnd);
                startActivity(intent);
            }
        }
    }

}

