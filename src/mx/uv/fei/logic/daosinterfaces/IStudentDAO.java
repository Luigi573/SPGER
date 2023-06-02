package mx.uv.fei.logic.daosinterfaces;

import java.util.ArrayList;
import mx.uv.fei.logic.domain.Student;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.exceptions.DataInsertionException;

public interface IStudentDAO {
    public void addStudent (Student student) throws DataInsertionException;
    public void modifyStudentData(Student newStudentData, Student originalStudentData) throws DataRetrievalException, DataInsertionException;
    public ArrayList<Student> getStudents() throws DataRetrievalException;
    public ArrayList<Student> getStudentList() throws DataRetrievalException;
    public ArrayList<Student> getAvailableStudents() throws DataRetrievalException;
    public ArrayList<Student> getSpecifiedAvailableStudents(String studentName) throws DataRetrievalException;
    public ArrayList<Student> getActiveStudents() throws DataRetrievalException;
    public ArrayList<Student> getSpecifiedActiveStudents(String studentName) throws DataRetrievalException;
    public ArrayList<Student> getSpecifiedStudents(String studentName) throws DataRetrievalException;
    public Student getStudent(String matricle) throws DataRetrievalException;
}
