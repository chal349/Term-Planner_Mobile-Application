package com.example.termplanner.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.termplanner.Util.DateValidator;

@Entity(tableName = "term_table")

public class Term {

    @PrimaryKey(autoGenerate = true)
    private int termId;

    private String title;
    private String startDate;
    private String endDate;

    public Term(int termId, String title, String startDate, String endDate) {
        this.termId = termId;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getTermId() {
        return termId;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) { this.endDate = endDate; }


    //FOR JUNIT TESTING
    public boolean validTermInput(String title, String startDate, String endDate){

        DateValidator validator = new DateValidator();
        int MAX_LENGTH = 14;

        if (title.isEmpty() || startDate.isEmpty() || endDate.isEmpty()){
            return false;
        }
        if (title.length() > MAX_LENGTH){
            return false;
        }
        if (!validator.isDateValid(startDate) && validator.isDateValid(endDate)){
            return false;
        }
        if (!validator.isDateOrderValid(startDate, endDate)){
            return false;
        }
        return true;
    }
}
