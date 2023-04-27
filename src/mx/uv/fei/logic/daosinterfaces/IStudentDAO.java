package mx.uv.fei.logic.daosinterfaces;

import java.util.ArrayList;

import mx.uv.fei.logic.domain.Student;

public interface IStudentDAO {
    public void addStudentToDatabase(Student student);
    public ArrayList<Student> getStudentsFromDatabase();
    public ArrayList<Student> getSpecifiedStudentsFromDatabase(String studentName);
    public Student getStudentFromDatabase(String studentName);
}
