package mx.uv.fei.logic.daosinterfaces;

import java.util.ArrayList;

import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.exceptions.DataWritingException;

public interface IStudentsCoursesDAO {
    public void addStudentCourseToDatabase(String studentMatricle, String courseNRC) throws DataWritingException;
    public ArrayList<String> getStudentsMatriclesByCourseNRCFromDatabase(String courseNRC) throws DataRetrievalException;
    public void removeStudentCourseFromDatabase(String studentMatricle, String courseNRC) throws DataWritingException;
}
