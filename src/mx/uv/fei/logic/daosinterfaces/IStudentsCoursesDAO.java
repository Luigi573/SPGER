package mx.uv.fei.logic.daosinterfaces;

import java.util.ArrayList;

import mx.uv.fei.logic.exceptions.DataInsertionException;
import mx.uv.fei.logic.exceptions.DataRetrievalException;

public interface IStudentsCoursesDAO {
    public int addStudentCourse(String studentMatricle, String courseNRC) throws DataInsertionException;
    public ArrayList<String> getStudentsMatriclesByCourseNRC(String courseNRC) throws DataRetrievalException;
    public void removeStudentCourse(String studentMatricle, String courseNRC) throws DataInsertionException;
}
