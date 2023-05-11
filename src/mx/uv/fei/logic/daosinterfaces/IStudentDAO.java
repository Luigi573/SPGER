package mx.uv.fei.logic.daosinterfaces;

import java.util.ArrayList;

import mx.uv.fei.logic.domain.Student;

public interface IStudentDAO {
    public void addStudentToDatabase(Student student);
    public void modifyStudentDataFromDatabase(Student newStudentData, Student originalStudentData);
    public ArrayList<Student> getStudentsFromDatabase();
    public ArrayList<Student> getSpecifiedStudentsFromDatabase(String studentName);
    public ArrayList<Student> getActiveStudentsFromDatabase();
    public ArrayList<Student> getSpecifiedActiveStudentsFromDatabase(String studentName);
    public Student getStudentFromDatabase(String matricle);
}
