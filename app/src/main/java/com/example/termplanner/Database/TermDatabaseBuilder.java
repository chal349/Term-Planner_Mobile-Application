package com.example.termplanner.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.termplanner.DAO.AssessmentDao;
import com.example.termplanner.DAO.CourseDao;
import com.example.termplanner.DAO.TermDao;
import com.example.termplanner.DAO.UserDao;
import com.example.termplanner.Entities.Assessment;
import com.example.termplanner.Entities.Course;
import com.example.termplanner.Entities.Term;
import com.example.termplanner.Entities.User;

@Database(entities = {Assessment.class, Course.class, Term.class, User.class}, version = 18, exportSchema = false)
public abstract class TermDatabaseBuilder extends RoomDatabase {

    private static volatile TermDatabaseBuilder INSTANCE;
    public abstract AssessmentDao assessmentDao();
    public abstract CourseDao courseDao();
    public abstract TermDao termDao();
    public abstract UserDao userDao();

     public static TermDatabaseBuilder getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (TermDatabaseBuilder.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), TermDatabaseBuilder.class, "termDatabase.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
