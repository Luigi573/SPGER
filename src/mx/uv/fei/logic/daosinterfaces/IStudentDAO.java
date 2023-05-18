package mx.uv.fei.logic.daosinterfaces;

import java.util.ArrayList;
import mx.uv.fei.logic.domain.Student;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.exceptions.DataWritingException;

public interface IStudentDAO {
    public void addStudentToDatabase(Student student) throws DataWritingException;
    public void modifyStudentDataFromDatabase(Student newStudentData, Student originalStudentData) throws DataWritingException;
    public ArrayList<Student> getStudentsFromDatabase() throws DataRetrievalException;
    public ArrayList<Student> getStudentList() throws DataRetrievalException;
    public ArrayList<Student> getActiveStudentsFromDatabase() throws DataRetrievalException;
    public ArrayList<Student> getSpecifiedActiveStudentsFromDatabase(String studentName) throws DataRetrievalException;
    public ArrayList<Student> getSpecifiedStudentsFromDatabase(String studentName) throws DataRetrievalException;
    public Student getStudentFromDatabase(String matricle) throws DataRetrievalException;
}
