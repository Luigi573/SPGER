package mx.uv.fei.logic.daosinterfaces;

import java.util.ArrayList;

import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.exceptions.DataInsertionException;

public interface IStudentsCoursesDAO {
    public void addStudentCourseToDatabase(String studentMatricle, String courseNRC) throws DataInsertionException;
    public ArrayList<String> getStudentsMatriclesByCourseNRCFromDatabase(String courseNRC) throws DataRetrievalException;
    public void removeStudentCourseFromDatabase(String studentMatricle, String courseNRC) throws DataInsertionException;
}
