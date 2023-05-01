package mx.uv.fei.logic.daosinterfaces;

import java.util.ArrayList;

import mx.uv.fei.logic.domain.Course;

public interface ICourseDAO {
    public void addCourseToDatabase(Course course);
    public void modifyCourseDataFromDatabase(Course newCourseData, Course originalCourseData);
    public ArrayList<Course> getCoursesFromDatabase();
    public ArrayList<Course> getSpecifiedCoursesFromDatabase(String courseName);
    public Course getCourseFromDatabase(String courseName);
}
