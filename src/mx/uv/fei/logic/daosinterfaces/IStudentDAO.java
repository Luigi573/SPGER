package mx.uv.fei.logic.daosinterfaces;

import java.util.ArrayList;
import mx.uv.fei.logic.domain.Student;
import mx.uv.fei.logic.exceptions.DataRetrievalException;

public interface IStudentDAO {
    public void addStudentToDatabase(Student student);
    public void modifyStudentDataFromDatabase(Student newStudentData, Student originalStudentData);
    public ArrayList<Student> getStudentsFromDatabase();
    public ArrayList<Student> getStudentList() throws DataRetrievalException;
    public ArrayList<Student> getSpecifiedStudentsFromDatabase(String studentName);
    public Student getStudentFromDatabase(String matricle);
}
