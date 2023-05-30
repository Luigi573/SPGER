package mx.uv.fei.logic.daosinterfaces;

import java.util.ArrayList;

import mx.uv.fei.logic.domain.Course;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.exceptions.DataInsertionException;

public interface ICourseDAO {
    public void addCourse (Course course) throws DataInsertionException;
    public void modifyCourseData(Course newCourseData, Course originalCourseData) throws DataInsertionException;
    public ArrayList<Course> getCourses() throws DataRetrievalException;
    public ArrayList<Course> getSpecifiedCourses(String courseName) throws DataRetrievalException;
    public Course getCourse(String courseName) throws DataRetrievalException;
}
