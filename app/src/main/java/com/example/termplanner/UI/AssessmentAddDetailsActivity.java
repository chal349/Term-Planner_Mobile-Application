package com.example.termplanner.UI;

import static com.example.termplanner.UI.TermDetailsActivity.tempId;

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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.termplanner.Entities.Assessment;
import com.example.termplanner.Entities.Course;
import com.example.termplanner.R;
import com.example.termplanner.Repository.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
    static String tempAssessmentTitle;
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

//        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup, int radioId) {
//                if (performance.isChecked()) {
//                    radioSelection = performance.toString();
//                }
//                if (objective.isChecked()){
//                    radioSelection = objective.toString();
//                }
//            }
//        });
//



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
        tempAssessmentId = getIntent().getIntExtra("assessmentId", -1);
        List<Assessment> allAssessments = repository.getAllAssessments();
        for (Assessment assessment : allAssessments) {
            if (assessment.getAssessmentId() == tempAssessmentId) {
                selectedAssessment = assessment;
            }
        }


        tempAssessmentTitle = getIntent().getStringExtra("assessmentTitle");
        tempAssessmentStartDate = getIntent().getStringExtra("assessmentStartDate");
        tempAssessmentEndDate = getIntent().getStringExtra("assessmentEndDate");
        tempRadioSelection = getIntent().getStringExtra("assessmentType");

        assessmentTitle.setText(tempAssessmentTitle);
        assessmentStartDate.setText(tempAssessmentStartDate);
        assessmentEndDate.setText(tempAssessmentEndDate);
        if(selectedAssessment == null){
            performance.setChecked(true);
        }
            else if(selectedAssessment.getAssessmentType() == "Performance Assessment"){
                 performance.setChecked(true);
        }
            else if(selectedAssessment.getAssessmentType().equals("Objective Assessment") ){
                objective.setChecked(true);
        }


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

    /////////////////////////////////////////On Create End

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        MenuItem item = menu.findItem(R.id.shareNote);
        item.setVisible(false);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.notifyStartAlert:
                String startDateFromScreen = assessmentStartDate.getText().toString();
                Date myStartDate = null;
                try {
                    myStartDate = sdf.parse(startDateFromScreen);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long startTrigger = myStartDate.getTime();
                Intent intentStart = new Intent(AssessmentAddDetailsActivity.this, Receiver.class);
                intentStart.putExtra("key", assessmentTitle.getText().toString() + " starts today!");
                PendingIntent senderStart = PendingIntent.getBroadcast(AssessmentAddDetailsActivity.this, MainMenuActivity.numAlert++, intentStart, PendingIntent.FLAG_MUTABLE);
                AlarmManager alarmManagerStart = (AlarmManager)  getSystemService(Context.ALARM_SERVICE);
                alarmManagerStart.set(AlarmManager.RTC_WAKEUP, startTrigger, senderStart);
                return true;
            case R.id.notifyEndAlert:
                String endDateFromScreen = assessmentEndDate.getText().toString();
                Date myEndDate = null;
                try {
                    myEndDate = sdf.parse(endDateFromScreen);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Long endTrigger = myEndDate.getTime();
                Intent intentEnd = new Intent(AssessmentAddDetailsActivity.this, Receiver.class);
                intentEnd.putExtra("key", assessmentTitle.getText().toString() + " ends today!");
                PendingIntent senderEnd = PendingIntent.getBroadcast(AssessmentAddDetailsActivity.this, MainMenuActivity.numAlert++, intentEnd, PendingIntent.FLAG_MUTABLE);
                AlarmManager alarmManagerEnd = (AlarmManager)  getSystemService(Context.ALARM_SERVICE);
                alarmManagerEnd.set(AlarmManager.RTC_WAKEUP, endTrigger, senderEnd);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveAssessment(View view) {

        Assessment updateAssessment;
        Assessment newAssessment;
        String name = assessmentTitle.getText().toString();
        String start = assessmentStartDate.getText().toString();
        String end = assessmentEndDate.getText().toString();
        String selection = "Objective Assessment";
        if (performance.isChecked()) {
            selection = "Performance Assessment";
        }

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
        }

        if (tempAssessmentId != -1) {
            updateAssessment = new Assessment(tempAssessmentId, name, selection, start, end, tempCourseId, tempTermId);
            repository.update(updateAssessment);
        } else {
            newAssessment = new Assessment(assessmentId, name, selection, start, end, tempCourseId, tempTermId);
            repository.insert(newAssessment);
        }
        if (tempCourseId == -1 || tempCourseId != CourseDetailsActivity.tempCourseId) {
            Intent intent = new Intent(AssessmentAddDetailsActivity.this, AssessmentActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(AssessmentAddDetailsActivity.this, CourseDetailsActivity.class);
            intent.putExtra("courseId", tempCourseId);
            intent.putExtra("termId", tempTermId);
            intent.putExtra("termTitle", TermDetailsActivity.tempTitle);
            intent.putExtra("termStartDate", TermDetailsActivity.tempStart);
            intent.putExtra("termEndDate", TermDetailsActivity.tempEnd);
            intent.putExtra("courseTitle", CourseDetailsActivity.tempCourseTitle);
            intent.putExtra("courseStartDate", CourseDetailsActivity.tempCourseStart);
            intent.putExtra("courseEndDate", CourseDetailsActivity.tempCourseEnd);
            intent.putExtra("courseStatus", CourseDetailsActivity.tempSpinner);
            intent.putExtra("courseStatusSelection", CourseDetailsActivity.tempSpinnerSelection);
            intent.putExtra("courseNotes", CourseDetailsActivity.tempNotes);
            intent.putExtra("instructorName", CourseDetailsActivity.tempInstructorName);
            intent.putExtra("instructorEmail", CourseDetailsActivity.tempInstructorEmail);
            intent.putExtra("instructorPhone", CourseDetailsActivity.tempInstructorPhone);
            startActivity(intent);

        }
    }

    public void DeleteAssessment(View view) {
        repository.delete(selectedAssessment);
        Context context = getApplicationContext();
        CharSequence text = assessmentTitle.getText() + " has been deleted!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        if (tempCourseId == -1 || tempCourseId != CourseDetailsActivity.tempCourseId) {
            Intent intent = new Intent(AssessmentAddDetailsActivity.this, AssessmentActivity.class);
            startActivity(intent);
        } else {
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
