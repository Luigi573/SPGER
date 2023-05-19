package mx.uv.fei.logic.daos;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import mx.uv.fei.logic.domain.Student;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.exceptions.DataWritingException;

public class StudentDAOTest {
    @Test
    public void addStudentToDatabase() {
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
            Assertions.assertTrue(expectedStudent.equals(actualStudent));
        } catch (DataWritingException e) {
            e.printStackTrace();
        } catch (DataRetrievalException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void modifyStudentDataFromDatabase(){
        
    }

    @Test
    public void getStudentsFromDatabase(){
        
    }

    @Test
    public void getStudentList() {
        
    }

    @Test
    public void getActiveStudentsFromDatabase() {
        
    }

    @Test
    public void getSpecifiedActiveStudentsFromDatabase() {
        
    }

    @Test
    public void getSpecifiedStudentsFromDatabase() {
        
    }

    @Test
    public void getStudentFromDatabase() {
        
    }

    @Test
    public void theStudentIsAlreadyRegisted() {
        
    }

    @Test
    public void getUserIdFromStudent() {
        
    }
}
