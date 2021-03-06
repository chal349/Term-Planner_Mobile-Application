package com.example.termplanner.UI;

import static com.example.termplanner.UI.CourseDetailsActivity.assessmentsInCourseCount;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.termplanner.Adapters.CourseAdapter;
import com.example.termplanner.Adapters.TermAdapter;
import com.example.termplanner.DAO.CourseDao;
import com.example.termplanner.Entities.Course;
import com.example.termplanner.Entities.Term;
import com.example.termplanner.R;
import com.example.termplanner.Repository.Repository;
import com.example.termplanner.Util.DateValidator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TermDetailsActivity extends AppCompatActivity{
    private Repository repository;
    static int tempId;
    static String tempTitle;
    static String tempStart;
    static String tempEnd;
    static int coursesInTermCount;
    EditText termName;
    EditText startDate;
    EditText endDate;
    String dateFormat = "MM/dd/yyyy";
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);

    Calendar calendarStart = Calendar.getInstance();
    Calendar calendarEnd = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener dateStartClick;
    DatePickerDialog.OnDateSetListener dateEndClick;
    Term selectedTerm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_term);
        getSupportActionBar().setTitle("Term Details");

        repository = new Repository(getApplication());
        termName = findViewById(R.id.termDetailsTitle);
        startDate = findViewById(R.id.termDetailsStartDate);
        endDate = findViewById(R.id.termdDetailsEndDate);

        // GET INTENTS
        int termID = getIntent().getIntExtra("termId", -1);
        tempId = termID;
        List<Term> allTerms = repository.getAllTerms();
        for (Term term : allTerms) {
            if (term.getTermId() == termID) {
                selectedTerm = term;
            }
        }
        String termTitle = getIntent().getStringExtra("termTitle");
        tempTitle = termTitle;
        String termStartDate = getIntent().getStringExtra("termStartDate");
        tempStart = termStartDate;
        String termEndDate = getIntent().getStringExtra("termEndDate");
        tempEnd = termEndDate;

        if (termID != -1) {
            termName.setText(tempTitle);
            startDate.setText(tempStart);
            endDate.setText(tempEnd);
        }

        List<Course> coursesInTerm = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.term_with_courses_recycler);

        final CourseAdapter courseAdapter = new CourseAdapter(this, coursesInTerm);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        for(Course course : repository.getAllCourses()){
            if(course.getTermId() == tempId){
                coursesInTerm.add(course);
            }
        }
        TextView coursesHeader = findViewById(R.id.courses);
        if(coursesInTerm.size() < 1){
            coursesHeader.setVisibility(View.INVISIBLE);
        }
        courseAdapter.setCourses(coursesInTerm);
        coursesInTermCount = coursesInTerm.size();


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

                new DatePickerDialog(TermDetailsActivity.this, dateStartClick, calendarStart
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

                new DatePickerDialog(TermDetailsActivity.this, dateEndClick, calendarEnd
                        .get(Calendar.YEAR), calendarEnd.get(Calendar.MONTH),
                        calendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    ////////////////////////////////////////////////////On Create End


    public void saveTermDetails(View view) {

        DateValidator validator = new DateValidator();
        String name = termName.getText().toString();
        String start = startDate.getText().toString();
        String end = endDate.getText().toString();
        Term updateTerm;

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

        } else if (!validator.isDateValid(start) || !validator.isDateValid(end)) {

            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Alert");
            alertDialog.setMessage("Date must be formatted mm/dd/yyyy!");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
            alertDialog.show();


        } else if (!validator.isDateOrderValid(start, end)) {

            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Alert");
            alertDialog.setMessage("Start date must be before End date!");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
            alertDialog.show();
        }
        else {

            updateTerm = new Term(tempId, name, start, end);
            repository.update(updateTerm);

            Intent intent = new Intent(TermDetailsActivity.this, TermActivity.class);
            intent.putExtra("termId", tempId);
            intent.putExtra("termTitle", tempTitle);
            intent.putExtra("termStartDate", tempStart);
            intent.putExtra("termEndDate", tempEnd);
            startActivity(intent);
        }

    }

    public void AddCourse(View view) {
        Intent intent = new Intent(TermDetailsActivity.this, CourseAddActivity.class);
        intent.putExtra("termId", tempId);
        intent.putExtra("termTitle", tempTitle);
        intent.putExtra("termStartDate", tempStart);
        intent.putExtra("termEndDate", tempEnd);
        startActivity(intent);
    }

    public void DeleteTerm(View view) {
        if (coursesInTermCount > 0) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Alert");
            alertDialog.setMessage("Term has Courses assigned to it.\nCourses must be deleted before deleting a Term!");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
            alertDialog.show();
        } else {
            repository.delete(selectedTerm);
            Context context = getApplicationContext();
            CharSequence text = termName.getText() + " has been deleted!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            Intent intent = new Intent(TermDetailsActivity.this, TermActivity.class);
            startActivity(intent);
        }
    }

}
