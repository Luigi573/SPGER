package mx.uv.fei.logic.daos;

import mx.uv.fei.logic.domain.Student;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.exceptions.DataInsertionException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class StudentDAOTest {
    @Test
    public void addStudentTest() {
        try {
            StudentDAO studentDAO = new StudentDAO();
            Student expectedStudent = new Student();
            expectedStudent.setName("Felixiano");
            expectedStudent.setFirstSurname("Carranza");
            expectedStudent.setSecondSurname("Loeda");
            expectedStudent.setEmailAddress("fexcarloa345@gmail.com");
            expectedStudent.setAlternateEmail("fexcarloa349@gmail.com");
            expectedStudent.setPhoneNumber("2283457810");
            expectedStudent.setMatricle("zS53943219");
            expectedStudent.setStatus("Graduado");
            studentDAO.addStudent(expectedStudent);
            
            Student actualStudent = studentDAO.getStudent(expectedStudent.getMatricle());     
            
            assertTrue(expectedStudent.equals(actualStudent));
        } catch (DataInsertionException e) {
            ();
        } catch (DataRetrievalException e) {
            ();
        }
    }
    
    @Test
    public int modifyStudentDataTest(){
        try {
            StudentDAO studentDAO = new StudentDAO();
            Student originalStudent = studentDAO.getStudent("zS53943219");
            Student expectedStudent = new Student();
            expectedStudent.setName("Felixiano");
            expectedStudent.setFirstSurname("Carranza");
            expectedStudent.setSecondSurname("Loeda");
            expectedStudent.setEmailAddress("fexcarloa345@gmail.com");
            expectedStudent.setAlternateEmail("fexcarloa399@gmail.com");
            expectedStudent.setPhoneNumber("2283457810");
            expectedStudent.setMatricle("zS53943219");
            expectedStudent.setStatus("Graduado");
            studentDAO.modifyStudentData(expectedStudent, originalStudent);
            
            Student actualStudent = studentDAO.getStudent(expectedStudent.getMatricle());     
            assertTrue(expectedStudent.equals(actualStudent));
        } catch (DataInsertionException e) {
            ();
        } catch (DataRetrievalException e) {
            ();
        }
    }

    @Test
    public void getStudentsTest() {
        
    }

    @Test
    public void getStudentListTest() {
        
    }

    @Test
    public void getActiveStudentsTest() {
        
    }

    @Test
    public void getSpecifiedActiveStudentsTest() {
        
    }

    @Test
    public void getSpecifiedStudentsTest() {
        
    }

    @Test
    public void getStudentTest() {
        try {
            StudentDAO studentDAO = new StudentDAO();
            Student expectedStudent = new Student();
            expectedStudent.setName("Felixiano");
            expectedStudent.setFirstSurname("Carranza");
            expectedStudent.setSecondSurname("Loeda");
            expectedStudent.setEmailAddress("fexcarloa345@gmail.com");
            expectedStudent.setAlternateEmail("fexcarloa349@gmail.com");
            expectedStudent.setPhoneNumber("2283457810");
            expectedStudent.setMatricle("zS53943219");
            expectedStudent.setStatus("Graduado");
            Student actualStudent = studentDAO.getStudent(expectedStudent.getMatricle());     
            assertTrue(expectedStudent.equals(actualStudent));
        } catch (DataRetrievalException e) {
            ();
        }
    }

    @Test
    public void theStudentIsAlreadyRegistedTest() {
        try {
            StudentDAO studentDAO = new StudentDAO();
            Student expectedStudent = new Student();
            expectedStudent.setName("Felixiano");
            expectedStudent.setFirstSurname("Carranza");
            expectedStudent.setSecondSurname("Loeda");
            expectedStudent.setEmailAddress("fexcarloa345@gmail.com");
            expectedStudent.setAlternateEmail("fexcarloa399@gmail.com");
            expectedStudent.setPhoneNumber("2283457810");
            expectedStudent.setMatricle("zS53943219");
            expectedStudent.setStatus("Graduado");

            assertTrue(studentDAO.theStudentIsAlreadyRegisted(expectedStudent));
        } catch (DataRetrievalException e) {
            ();
        }
    }

    @Test
    public void getUserIdFromStudentTest() {
        
    }
}
