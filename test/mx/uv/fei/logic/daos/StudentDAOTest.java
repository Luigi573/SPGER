package mx.uv.fei.logic.daos;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import mx.uv.fei.logic.domain.Student;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.exceptions.DataWritingException;

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
            expectedStudent.setMatricle("zS11000000");
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
    public void modifyStudentDataFromDatabaseTest(){
        try {
            StudentDAO studentDAO = new StudentDAO();
            Student originalStudent = studentDAO.getStudentFromDatabase("zS11000000");
            Student expectedStudent = new Student();
            expectedStudent.setName("Felixiano");
            expectedStudent.setFirstSurname("Carranza");
            expectedStudent.setSecondSurname("Loeda");
            expectedStudent.setEmailAddress("fexcarloa345@gmail.com");
            expectedStudent.setAlternateEmail("fexcarloa399@gmail.com");
            expectedStudent.setPhoneNumber("2283457810");
            expectedStudent.setMatricle("zS11000001");
            expectedStudent.setStatus("Graduado");
            studentDAO.modifyStudentDataFromDatabase(expectedStudent, originalStudent);
            
            Student actualStudent = studentDAO.getStudentFromDatabase(expectedStudent.getMatricule());     
            Assertions.assertTrue(expectedStudent.equals(actualStudent));
        } catch (DataWritingException e) {
            e.printStackTrace();
        } catch (DataRetrievalException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getStudentsFromDatabaseTest() {
        try {
            StudentDAO studentDAO = new StudentDAO();
            Student expectedStudent = new Student();
            expectedStudent.setName("Luis Roberto");
            expectedStudent.setFirstSurname("Justo");
            expectedStudent.setSecondSurname("Moreno");
            expectedStudent.setEmailAddress("lurojumo342@gmail.com");
            expectedStudent.setAlternateEmail("lurojumo343@gmail.com");
            expectedStudent.setPhoneNumber("2288563472");
            expectedStudent.setMatricle("zS10000001");
            expectedStudent.setStatus("Activo");
            Student actualStudent = studentDAO.getStudentsFromDatabase().get(0);   

            Assertions.assertTrue(expectedStudent.equals(actualStudent));
        } catch (DataRetrievalException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getStudentListTest() {
        try {
            StudentDAO studentDAO = new StudentDAO();
            ArrayList<Student> studentList;
            studentList = studentDAO.getStudentList();
            assertNotNull("If size is greater than one, it means data was retrieved correctly", (studentList.size() > 0));
        } catch (DataRetrievalException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getActiveStudentsFromDatabaseTest() {
        try {
            StudentDAO studentDAO = new StudentDAO();
            Student expectedStudent = new Student();
            expectedStudent.setName("Luis Roberto");
            expectedStudent.setFirstSurname("Justo");
            expectedStudent.setSecondSurname("Moreno");
            expectedStudent.setEmailAddress("lurojumo342@gmail.com");
            expectedStudent.setAlternateEmail("lurojumo343@gmail.com");
            expectedStudent.setPhoneNumber("2288563472");
            expectedStudent.setMatricle("zS10000001");
            expectedStudent.setStatus("Activo");
            Student actualStudent = studentDAO.getActiveStudentsFromDatabase().get(0);   

            Assertions.assertTrue(expectedStudent.equals(actualStudent));
        } catch (DataRetrievalException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getSpecifiedActiveStudentsFromDatabaseTest() {
        try {
            StudentDAO studentDAO = new StudentDAO();
            Student expectedStudent = new Student();
            expectedStudent.setName("Luis Roberto");
            expectedStudent.setFirstSurname("Justo");
            expectedStudent.setSecondSurname("Moreno");
            expectedStudent.setEmailAddress("lurojumo342@gmail.com");
            expectedStudent.setAlternateEmail("lurojumo343@gmail.com");
            expectedStudent.setPhoneNumber("2288563472");
            expectedStudent.setMatricle("zS10000001");
            expectedStudent.setStatus("Activo");
            Student actualStudent = studentDAO.getSpecifiedActiveStudentsFromDatabase("L").get(0);   

            Assertions.assertTrue(expectedStudent.equals(actualStudent));
        } catch (DataRetrievalException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getSpecifiedStudentsFromDatabaseTest() {
        try {
            StudentDAO studentDAO = new StudentDAO();
            Student expectedStudent = new Student();
            expectedStudent.setName("Luis Roberto");
            expectedStudent.setFirstSurname("Justo");
            expectedStudent.setSecondSurname("Moreno");
            expectedStudent.setEmailAddress("lurojumo342@gmail.com");
            expectedStudent.setAlternateEmail("lurojumo343@gmail.com");
            expectedStudent.setPhoneNumber("2288563472");
            expectedStudent.setMatricle("zS10000001");
            expectedStudent.setStatus("Activo");
            Student actualStudent = studentDAO.getSpecifiedStudentsFromDatabase("L").get(0);

            Assertions.assertTrue(expectedStudent.equals(actualStudent));
        } catch (DataRetrievalException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getStudentFromDatabaseTest() {
        try {
            StudentDAO studentDAO = new StudentDAO();
            Student expectedStudent = new Student();
            expectedStudent.setName("Luis Roberto");
            expectedStudent.setFirstSurname("Justo");
            expectedStudent.setSecondSurname("Moreno");
            expectedStudent.setEmailAddress("lurojumo342@gmail.com");
            expectedStudent.setAlternateEmail("lurojumo343@gmail.com");
            expectedStudent.setPhoneNumber("2288563472");
            expectedStudent.setMatricle("zS10000001");
            expectedStudent.setStatus("Activo");
            Student actualStudent = studentDAO.getStudentFromDatabase(expectedStudent.getMatricule());  

            Assertions.assertTrue(expectedStudent.equals(actualStudent));
        } catch (DataRetrievalException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void theStudentIsAlreadyRegistedTest() {
        try {
            StudentDAO studentDAO = new StudentDAO();
            Student expectedStudent = new Student();
            expectedStudent.setName("Luis Roberto");
            expectedStudent.setFirstSurname("Justo");
            expectedStudent.setSecondSurname("Moreno");
            expectedStudent.setEmailAddress("lurojumo342@gmail.com");
            expectedStudent.setAlternateEmail("lurojumo343@gmail.com");
            expectedStudent.setPhoneNumber("2288563472");
            expectedStudent.setMatricle("zS10000001");
            expectedStudent.setStatus("Activo");

            Assertions.assertTrue(studentDAO.theStudentIsAlreadyRegisted(expectedStudent));
        } catch (DataRetrievalException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getUserIdFromStudentTest() {
        
    }
}
