package mx.uv.fei.logic.daosinterfaces;

import java.util.ArrayList;

import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.exceptions.DataInsertionException;

public interface IStudentsCoursesDAO {
    public void addStudentCourse (String studentMatricle, String courseNRC) throws DataInsertionException;
    public ArrayList<String> getStudentsMatriclesByCourseNRC(String courseNRC) throws DataRetrievalException;
    public void removeStudentCourse(String studentMatricle, String courseNRC) throws DataInsertionException;
}
