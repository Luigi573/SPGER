package mx.uv.fei.logic.daosinterfaces;

import java.util.ArrayList;

public interface IStudentsCoursesDAO {
    public void addStudentCourseToDatabase(String studentMatricle, String courseNRC);
    public ArrayList<String> getStudentsMatriclesByCourseNRCFromDatabase(String courseNRC);
    public void removeStudentCourseFromDatabase(String studentMatricle, String courseNRC);
}
