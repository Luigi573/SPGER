package mx.uv.fei.logic.daosinterfaces;

import java.util.ArrayList;

import mx.uv.fei.logic.domain.Course;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.exceptions.DataWritingException;

public interface ICourseDAO {
    public void addCourseToDatabase(Course course) throws DataWritingException;
    public void modifyCourseDataFromDatabase(Course newCourseData, Course originalCourseData) throws DataWritingException;
    public ArrayList<Course> getCoursesFromDatabase() throws DataRetrievalException;
    public ArrayList<Course> getSpecifiedCoursesFromDatabase(String courseName) throws DataRetrievalException;
    public Course getCourseFromDatabase(String courseName) throws DataRetrievalException;
}
