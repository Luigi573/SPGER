package mx.uv.fei.logic.daosinterfaces;

import mx.uv.fei.logic.domain.Course;
import mx.uv.fei.logic.domain.Student;

public interface IStudentsCoursesDAO {
    public void addStudentCourseToDatabase(Student student, Course course);
    public void getStudentCourseToDatabase(Student student, Course course);
    public void removeStudentCourseFromDatabase(Student student, Course course);
}
