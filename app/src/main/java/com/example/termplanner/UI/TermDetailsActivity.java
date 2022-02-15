package com.example.termplanner.UI;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.example.termplanner.Entities.Term;
import com.example.termplanner.R;
import com.example.termplanner.Repository.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TermDetailsActivity extends AppCompatActivity{
    private Repository repository;
    static int tempId;
    int termId;
    EditText termName;
    EditText startDate;
    EditText endDate;
    String dateFormat = "MM/dd/yyyy";
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);

    Calendar calendarStart = Calendar.getInstance();
    Calendar calendarEnd = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener dateStartClick;
    DatePickerDialog.OnDateSetListener dateEndClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_term);
        getSupportActionBar().setTitle("Term Details");

        repository = new Repository(getApplication());
        termName = findViewById(R.id.termDetailsTitle);
        startDate = findViewById(R.id.termDetailsStartDate);
        endDate = findViewById(R.id.termdDetailsEndDate);

        // GET INTENTS FROM PREVIOUS SCREEN
        int termID = getIntent().getIntExtra("termId", -1);
        String termTitle = getIntent().getStringExtra("title");
        String termStartDate = getIntent().getStringExtra("startDate");
        String termEndDate = getIntent().getStringExtra("endDate");

        if (termID != -1) {
            termName.setText(termTitle);
            startDate.setText(termStartDate);
            endDate.setText(termEndDate);
        }

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

        //CANCEL BUTTON
        Button cancelTermButton = findViewById(R.id.cancel_term_details_button);
        cancelTermButton.setText("CANCEL");
        cancelTermButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TermDetailsActivity.this, TermActivity.class));

            }
        });
    }

    public void saveTermDetails(View view) {
        int id = termId;
        String name = termName.getText().toString();
        String start = startDate.getText().toString();
        String end = endDate.getText().toString();
        //   int termID = termId;
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

        } else {
            List<Term> allTerms = repository.getAllTerms();
            id = allTerms.get(allTerms.size() - 1).getTermId();

            updateTerm = new Term(id, name, start, end);
            repository.update(updateTerm);
            Intent intent = new Intent(TermDetailsActivity.this, TermActivity.class);
            startActivity(intent);
        }

    }

    public void AddCourse(View view) {
        startActivity(new Intent(TermDetailsActivity.this, CourseAddActivity.class));
    }

}
