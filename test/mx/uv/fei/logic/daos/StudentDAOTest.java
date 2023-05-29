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
    public void addStudentToDatabaseTest() {
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
            studentDAO.addStudentToDatabase(expectedStudent);
            
            Student actualStudent = studentDAO.getStudentFromDatabase(expectedStudent.getMatricule());     
            
            assertTrue(expectedStudent.equals(actualStudent));
        } catch (DataInsertionException e) {
            e.printStackTrace();
        } catch (DataRetrievalException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void modifyStudentDataFromDatabaseTest(){
        try {
            StudentDAO studentDAO = new StudentDAO();
            Student originalStudent = studentDAO.getStudentFromDatabase("zS53943219");
            Student expectedStudent = new Student();
            expectedStudent.setName("Felixiano");
            expectedStudent.setFirstSurname("Carranza");
            expectedStudent.setSecondSurname("Loeda");
            expectedStudent.setEmailAddress("fexcarloa345@gmail.com");
            expectedStudent.setAlternateEmail("fexcarloa399@gmail.com");
            expectedStudent.setPhoneNumber("2283457810");
            expectedStudent.setMatricle("zS53943219");
            expectedStudent.setStatus("Graduado");
            studentDAO.modifyStudentDataFromDatabase(expectedStudent, originalStudent);
            
            Student actualStudent = studentDAO.getStudentFromDatabase(expectedStudent.getMatricule());     
            assertTrue(expectedStudent.equals(actualStudent));
        } catch (DataInsertionException e) {
            e.printStackTrace();
        } catch (DataRetrievalException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getStudentsFromDatabaseTest() {
        
    }

    @Test
    public void getStudentListTest() {
        
    }

    @Test
    public void getActiveStudentsFromDatabaseTest() {
        
    }

    @Test
    public void getSpecifiedActiveStudentsFromDatabaseTest() {
        
    }

    @Test
    public void getSpecifiedStudentsFromDatabaseTest() {
        
    }

    @Test
    public void getStudentFromDatabaseTest() {
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
            Student actualStudent = studentDAO.getStudentFromDatabase(expectedStudent.getMatricule());     
            assertTrue(expectedStudent.equals(actualStudent));
        } catch (DataRetrievalException e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }

    @Test
    public void getUserIdFromStudentTest() {
        
    }
}
