package mx.uv.fei.logic.daosinterfaces;

import java.util.ArrayList;

import mx.uv.fei.logic.domain.Course;
import mx.uv.fei.logic.domain.Student;

public interface IGlobalReportDAO {
    public abstract ArrayList<Course> getCoursesFromDatabase (int nrc);
    public abstract ArrayList<Student> getStudentsFromDatabase (String matricle);
}
