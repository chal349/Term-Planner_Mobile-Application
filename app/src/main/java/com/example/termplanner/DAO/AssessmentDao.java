package com.example.termplanner.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.termplanner.Entities.Assessment;

import java.util.List;

@Dao
public interface AssessmentDao {

    @Insert
    void insert(Assessment assessment);

    @Update
    void update(Assessment assessment);

    @Delete
    void delete(Assessment assessment);

    @Query("SELECT * FROM assessments_table ORDER BY assessmentId ASC")
    List<Assessment> getAllAssessments();

    @Query("DELETE FROM assessments_table")
    void deleteAllAssessments();
}
