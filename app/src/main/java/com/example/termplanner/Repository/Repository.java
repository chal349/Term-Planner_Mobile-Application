package com.example.termplanner.Repository;

import android.app.Application;

import com.example.termplanner.DAO.AssessmentDao;
import com.example.termplanner.DAO.CourseDao;
import com.example.termplanner.DAO.TermDao;
import com.example.termplanner.DAO.UserDao;
import com.example.termplanner.Database.TermDatabaseBuilder;
import com.example.termplanner.Entities.Assessment;
import com.example.termplanner.Entities.Course;
import com.example.termplanner.Entities.Term;
import com.example.termplanner.Entities.User;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {

    private AssessmentDao assessmentDao;
    private CourseDao courseDao;
    private TermDao termDao;
    private UserDao userDao;
    private List<Assessment> allAssessments;
    private List<Course> allCourses;
    private List<Term> allTerms;
    private List<User> allUsers;

    private static int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application) {
        TermDatabaseBuilder termDatabaseBuilder = TermDatabaseBuilder.getDatabase(application);
        assessmentDao = termDatabaseBuilder.assessmentDao();
        courseDao = termDatabaseBuilder.courseDao();
        termDao = termDatabaseBuilder.termDao();
        userDao = termDatabaseBuilder.userDao();
    }


    // Assessments

    public void insert(Assessment assessment) {
        databaseExecutor.execute(() -> assessmentDao.insert(assessment));
        try {
            Thread.sleep(1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Assessment assessment) {
        databaseExecutor.execute(() -> assessmentDao.update(assessment));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Assessment assessment) {
        databaseExecutor.execute(() -> assessmentDao.delete(assessment));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<Assessment> getAllAssessments() {
        databaseExecutor.execute(() -> allAssessments = assessmentDao.getAllAssessments());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return allAssessments;
    }


    // COURSES

    public void insert(Course course){
        databaseExecutor.execute(()-> courseDao.insert(course));
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void update(Course course){
        databaseExecutor.execute(()-> courseDao.update(course));
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void delete(Course course){
        databaseExecutor.execute(()-> courseDao.delete(course));
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public List<Course> getAllCourses() {
        databaseExecutor.execute(() -> allCourses = courseDao.getAllCourses());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return allCourses;
    }

    // TERMS

    public void insert(Term term){
        databaseExecutor.execute(()-> termDao.insert(term));
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void update(Term term){
        databaseExecutor.execute(()-> termDao.update(term));
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void delete(Term term){
        databaseExecutor.execute(()-> termDao.delete(term));
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public List<Term>getAllTerms() {
        databaseExecutor.execute(() -> {
            allTerms = termDao.getAllTerms();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return allTerms;
    }

    // USERS

    public void insert(User user){
        databaseExecutor.execute(()-> userDao.insert(user));
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void update(User user){
        databaseExecutor.execute(()-> userDao.update(user));
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public void delete(User user){
        databaseExecutor.execute(()-> userDao.delete(user));
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        databaseExecutor.execute(() -> allUsers = userDao.getAllUsers());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return allUsers;
    }
}