package mx.uv.fei.logic.daosinterfaces;

import java.util.ArrayList;

import mx.uv.fei.logic.domain.Course;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.exceptions.DataInsertionException;

public interface ICourseDAO {
    public void addCourseToDatabase(Course course) throws DataInsertionException;
    public void modifyCourseDataFromDatabase(Course newCourseData, Course originalCourseData) throws DataInsertionException;
    public ArrayList<Course> getCoursesFromDatabase() throws DataRetrievalException;
    public ArrayList<Course> getSpecifiedCoursesFromDatabase(String courseName) throws DataRetrievalException;
    public Course getCourseFromDatabase(String courseName) throws DataRetrievalException;
}
