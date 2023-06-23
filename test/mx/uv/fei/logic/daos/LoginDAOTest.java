package mx.uv.fei.logic.daos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.logic.domain.AcademicBodyHead;
import mx.uv.fei.logic.domain.DegreeBoss;
import mx.uv.fei.logic.domain.Professor;
import mx.uv.fei.logic.domain.Student;
import mx.uv.fei.logic.exceptions.LoginException;

public class LoginDAOTest {
    private static AcademicBodyHead preloadedAcademicBodyHead;
    private static DataBaseManager dataBaseManager;
    private static DegreeBoss preloadedAdmin;
    private static Professor preloadedProfessor;
    private static Student preloadedStudent;
    
    public LoginDAOTest() {
        
    }
    
    @BeforeClass
    public static void setUpClass() {
        dataBaseManager = new DataBaseManager();
        
        try{
            //Adding an admin
            preloadedAdmin = new DegreeBoss();
            preloadedAdmin.setName("Jorge Octavio");
            preloadedAdmin.setFirstSurname("Ocharán");
            preloadedAdmin.setSecondSurname("Hernández");
            preloadedAdmin.setEmailAddress("gmartinez@uv.mx");
            preloadedAdmin.setStaffNumber(68532);
            
            String userQuery = "INSERT INTO Usuarios(nombre, apellidoPaterno, apellidoMaterno, correo, contraseña) VALUES(?, ?, ?, ?, SHA2(?, 256))";
            PreparedStatement userStatement = dataBaseManager.getConnection().prepareStatement(userQuery, Statement.RETURN_GENERATED_KEYS);
            userStatement.setString(1, preloadedAdmin.getName());
            userStatement.setString(2, preloadedAdmin.getFirstSurname());
            userStatement.setString(3, preloadedAdmin.getSecondSurname());
            userStatement.setString(4, preloadedAdmin.getEmailAddress());
            userStatement.setString(5, "vivaElDiseño2023");
            userStatement.executeUpdate();
            
            ResultSet generatedAdminKeys = userStatement.getGeneratedKeys();
            if(generatedAdminKeys.next()){
                preloadedAdmin.setUserId(generatedAdminKeys.getInt(1));
            }
            generatedAdminKeys.close();
            
            String professorQuery = "INSERT INTO Profesores(NumPersonal, IdUsuario) VALUES(?,?)";
            PreparedStatement professorStatement = dataBaseManager.getConnection().prepareStatement(professorQuery);
            professorStatement.setInt(1, preloadedAdmin.getStaffNumber());
            professorStatement.setInt(2, preloadedAdmin.getUserId());
            professorStatement.executeUpdate();
            
            String adminQuery = "INSERT INTO JefesCarrera(NumPersonal) VALUES(?)";
            PreparedStatement adminStatement = dataBaseManager.getConnection().prepareStatement(adminQuery);
            adminStatement.setInt(1, preloadedAdmin.getStaffNumber());
            adminStatement.executeUpdate();
            adminStatement.close();
            
            //Adding a professor
            preloadedProfessor = new Professor();
            preloadedProfessor.setName("Juan Carlos");
            preloadedProfessor.setFirstSurname("Pérez");
            preloadedProfessor.setSecondSurname("Arriaga");
            preloadedProfessor.setEmailAddress("elrevo@uv.com");
            preloadedProfessor.setStaffNumber(64582);
            
            userStatement.setString(1, preloadedProfessor.getName());
            userStatement.setString(2, preloadedProfessor.getFirstSurname());
            userStatement.setString(3, preloadedProfessor.getSecondSurname());
            userStatement.setString(4, preloadedProfessor.getEmailAddress());
            userStatement.setString(5, "password");
            userStatement.executeUpdate();
            
            ResultSet generatedProfessorKeys = userStatement.getGeneratedKeys();
            if(generatedProfessorKeys.next()){
                preloadedProfessor.setUserId(generatedProfessorKeys.getInt(1));
            }
            generatedProfessorKeys.close();
            
            professorStatement.setInt(1, preloadedProfessor.getStaffNumber());
            professorStatement.setInt(2, preloadedProfessor.getUserId());
            professorStatement.executeUpdate();
            
            //Adding a student
            preloadedStudent = new Student();
            preloadedStudent.setName("Xavier Arian");
            preloadedStudent.setFirstSurname("Olivares");
            preloadedStudent.setSecondSurname("Sánchez");
            preloadedStudent.setEmailAddress("zS21013906@estudiantes.uv.mx");
            preloadedStudent.setMatricle("zS29613906");
            
            userStatement.setString(1, preloadedStudent.getName());
            userStatement.setString(2, preloadedStudent.getFirstSurname());
            userStatement.setString(3, preloadedStudent.getSecondSurname());
            userStatement.setString(4, preloadedStudent.getEmailAddress());
            userStatement.setString(5, "MeLoveCats1234");
            userStatement.executeUpdate();
            
            ResultSet generatedStudentKeys = userStatement.getGeneratedKeys();
            if(generatedStudentKeys.next()){
                preloadedStudent.setUserId(generatedStudentKeys.getInt(1));
            }
            generatedStudentKeys.close();
            
            String studentQuery = "INSERT INTO Estudiantes(Matrícula, IdUsuario) VALUES(?,?)";
            PreparedStatement studentStatement = dataBaseManager.getConnection().prepareStatement(studentQuery);
            studentStatement.setString(1, preloadedStudent.getMatricle());
            studentStatement.setInt(2, preloadedStudent.getUserId());
            studentStatement.executeUpdate();
            studentStatement.close();
            
            //Adding an Academic Body Head
            preloadedAcademicBodyHead = new AcademicBodyHead();
            preloadedAcademicBodyHead.setName("Maria de los Ángeles");
            preloadedAcademicBodyHead.setFirstSurname("Arenas");
            preloadedAcademicBodyHead.setSecondSurname("Valdez");
            preloadedAcademicBodyHead.setEmailAddress("umarinero@uv.mx");
            preloadedAcademicBodyHead.setStaffNumber(47532);
            
            userStatement.setString(1, preloadedAcademicBodyHead.getName());
            userStatement.setString(2, preloadedAcademicBodyHead.getFirstSurname());
            userStatement.setString(3, preloadedAcademicBodyHead.getSecondSurname());
            userStatement.setString(4, preloadedAcademicBodyHead.getEmailAddress());
            userStatement.setString(5, "programaciónOOP5462");
            userStatement.executeUpdate();
            
            ResultSet generatedAcademicBodyHeadKeys = userStatement.getGeneratedKeys();
            if(generatedAcademicBodyHeadKeys.next()){
                preloadedAcademicBodyHead.setUserId(generatedAcademicBodyHeadKeys.getInt(1));
            }
            generatedAcademicBodyHeadKeys.close();
            
            professorStatement.setInt(1, preloadedAcademicBodyHead.getStaffNumber());
            professorStatement.setInt(2, preloadedAcademicBodyHead.getUserId());
            professorStatement.executeUpdate();
            
            String academicBodyHeadQuery = "INSERT INTO ResponsablesCA(NumPersonal) VALUES(?)";
            PreparedStatement academicBodyHeadStatement = dataBaseManager.getConnection().prepareStatement(academicBodyHeadQuery);
            academicBodyHeadStatement.setInt(1, preloadedAcademicBodyHead.getStaffNumber());
            academicBodyHeadStatement.executeUpdate();
            academicBodyHeadStatement.close();
            
            userStatement.close();
        }catch(SQLException exception){
              ;
            fail("Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }
    
    @AfterClass
    public static void tearDownClass(){
        PreparedStatement statement;
        String query = "DELETE FROM Usuarios WHERE IdUsuario IN(?, ?, ?, ?)";
        
        try{
            statement = dataBaseManager.getConnection().prepareStatement(query);
            statement.setInt(1, preloadedAdmin.getUserId());
            statement.setInt(2, preloadedProfessor.getUserId());
            statement.setInt(3, preloadedStudent.getUserId());
            statement.setInt(4, preloadedAcademicBodyHead.getUserId());
            statement.executeUpdate();
            
            statement.close();
        }catch(SQLException exception){
            fail("Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }

    @Test
    public void testLogInStudent() throws LoginException{
        LoginDAO instance = new LoginDAO();
        Student result = instance.logInStudent("zS29613906", "MeLoveCats1234");
        
        System.out.println("Expected data: ");
        System.out.println("Student Name: " + preloadedStudent);
        System.out.println("Matricle: " + preloadedStudent.getMatricle());
        
        System.out.println("\nActual data: ");
        System.out.println("Student Name: " + result);
        System.out.println("Matricle: " + result.getMatricle());
        
        assertEquals(preloadedStudent, result);
    }
    
    @Test
    public void testLogInStudentFail() throws LoginException{
        LoginDAO instance = new LoginDAO();
        Student result = instance.logInStudent("zS29613906", "Xavier0573");
        
        System.out.println("Testing LogStudent with wrong password");
        
        assertNotEquals(preloadedStudent, result);
    }

    @Test
    public void testLogInprofessor() throws LoginException{
        LoginDAO instance = new LoginDAO();
        Professor result = instance.logInProfessor("elrevo@uv.com", "password");
        
        System.out.println("Expected data: ");
        System.out.println("Professor name: " + preloadedProfessor);
        System.out.println("Staff Number: " + preloadedProfessor.getStaffNumber());
        System.out.println("Email Address: " + preloadedProfessor.getEmailAddress());
        
        System.out.println("\nActual data: ");
        System.out.println("Admin name: " + result);
        System.out.println("Staff Number: " + result.getStaffNumber());
        System.out.println("Email Address: " + result.getEmailAddress());
        
        assertEquals(preloadedProfessor, result);
    }
    
    @Test
    public void testLogInprofessorFail() throws LoginException{
        LoginDAO instance = new LoginDAO();
        Professor result = instance.logInProfessor("jaguevara@uv.mx", "contraseña");
        
        System.out.println("Testing LogInProfessor with wrong password");
        
        assertNotEquals(preloadedProfessor, result);
    }

    @Test
    public void testLogInAdmin() throws LoginException{
        LoginDAO instance = new LoginDAO();
        DegreeBoss result = instance.logInAdmin("gmartinez@uv.mx", "vivaElDiseño2023");
        
        System.out.println("Expected data: ");
        System.out.println("Admin name: " + preloadedAdmin);
        System.out.println("Staff Number: " + preloadedAdmin.getStaffNumber());
        System.out.println("Email Address: " + preloadedAdmin.getEmailAddress());
        
        System.out.println("\nActual data: ");
        System.out.println("Admin name: " + result);
        System.out.println("Staff Number: " + result.getStaffNumber());
        System.out.println("Email Address: " + preloadedAdmin.getEmailAddress());
        
        assertEquals(preloadedAdmin, result);
    }
    
    @Test
    public void testLogInAdminFail() throws LoginException{
        LoginDAO instance = new LoginDAO();
        DegreeBoss result = instance.logInAdmin("gmartinez@uv.mx", "viv34567");
        
        System.out.println("Testing LogInAdmin with wrong password");
        
        assertNotEquals(preloadedAdmin, result);
    }
    
    @Test
    public void testLogInAcademicBodyHead() throws LoginException{
        LoginDAO instance = new LoginDAO();
        AcademicBodyHead result = instance.logInAcademicBodyHead("umarinero@uv.mx", "programaciónOOP5462");
        
        System.out.println("Expected data: ");
        System.out.println("Academic body head name: " + preloadedAcademicBodyHead);
        System.out.println("Staff Number: " + preloadedAcademicBodyHead.getStaffNumber());
        
        System.out.println("\nActual data: ");
        System.out.println("Academic body head: " + result);
        System.out.println("Staff Number: " + result.getStaffNumber());
        
        assertEquals(preloadedAcademicBodyHead, result);
    }
    
    @Test
    public void testLogInAcademicBodyHeadFail() throws LoginException{
        LoginDAO instance = new LoginDAO();
        AcademicBodyHead result = instance.logInAcademicBodyHead("umarinero@uv.mx", "prograee24");
        
        System.out.println("\nTesting LogInAcademicBody with wrong password");
        
        assertNotEquals(preloadedAcademicBodyHead, result);
    }

}