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
import mx.uv.fei.logic.domain.Director;
import mx.uv.fei.logic.domain.Professor;
import mx.uv.fei.logic.domain.Student;
import mx.uv.fei.logic.exceptions.LoginException;

public class LoginDAOTest {
    private static AcademicBodyHead preloadedAcademicBodyHead;
    private static DataBaseManager dataBaseManager;
    private static DegreeBoss preloadedAdmin;
    private static Director preloadedDirector;
    private static Professor preloadedProfessor;
    private static Student preloadedStudent;
        
    @BeforeClass
    public static void setUpClass() {
        dataBaseManager = new DataBaseManager();
        
        preloadedAcademicBodyHead = new AcademicBodyHead();
        preloadedAcademicBodyHead.setName("Ulises");
        preloadedAcademicBodyHead.setFirstSurname("Marinero");
        preloadedAcademicBodyHead.setSecondSurname("Aguilar");
        preloadedAcademicBodyHead.setEmailAddress("umarinero@uv.mx");
        preloadedAcademicBodyHead.setStaffNumber(47532);
        
        preloadedAdmin = new DegreeBoss();
        preloadedAdmin.setName("Jorge Octavio");
        preloadedAdmin.setFirstSurname("Ocharán");
        preloadedAdmin.setSecondSurname("Hernández");
        preloadedAdmin.setEmailAddress("jorocharan@uv.mx");
        preloadedAdmin.setStaffNumber(68532);
        
        preloadedDirector = new Director();
        preloadedDirector.setStaffNumber(85432);
        preloadedDirector.setSecondSurname("Ezzio Othoniel");
        preloadedDirector.setName("Acosta");
        preloadedDirector.setFirstSurname("Canseco");
        preloadedDirector.setEmailAddress("ezacosta@uv.mx");
        
        preloadedProfessor = new Professor();
        preloadedProfessor.setStaffNumber(64582);
        preloadedProfessor.setName("Jose Luis");
        preloadedProfessor.setFirstSurname("Cuellar");
        preloadedProfessor.setSecondSurname("Cessa");
        preloadedProfessor.setEmailAddress("luicuellar@uv.mx");
        
        preloadedStudent = new Student();
        preloadedStudent.setMatricle("zS23587532");
        preloadedStudent.setName("Xavier Arian");
        preloadedStudent.setFirstSurname("Olivares");
        preloadedStudent.setSecondSurname("Sánchez");
        preloadedStudent.setEmailAddress("zS23587532@estudiantes.uv.mx");
        
        try{
            //Adding an admin
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
            
            //Adding a director
            userStatement.setString(1, preloadedDirector.getName());
            userStatement.setString(2, preloadedDirector.getFirstSurname());
            userStatement.setString(3, preloadedDirector.getSecondSurname());
            userStatement.setString(4, preloadedDirector.getEmailAddress());
            userStatement.setString(5, "pensamientoDeCampirán2020");
            userStatement.executeUpdate();
            
            ResultSet generatedDirectorKeys = userStatement.getGeneratedKeys();
            if(generatedDirectorKeys.next()){
                preloadedDirector.setUserId(generatedDirectorKeys.getInt(1));
            }
            
            professorStatement.setInt(1, preloadedDirector.getStaffNumber());
            professorStatement.setInt(2, preloadedDirector.getUserId());
            professorStatement.executeUpdate();
            
            String directorQuery = "INSERT INTO Directores(NumPersonal) VALUES(?)";
            PreparedStatement directorStatement = dataBaseManager.getConnection().prepareStatement(directorQuery, PreparedStatement.RETURN_GENERATED_KEYS);
            
            directorStatement.setInt(1, preloadedDirector.getStaffNumber());
            directorStatement.executeUpdate();
            
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
        String query = "DELETE FROM Usuarios WHERE IdUsuario IN(?, ?, ?, ?, ?)";
        
        try{
            PreparedStatement statement = dataBaseManager.getConnection().prepareStatement(query);
            statement.setInt(1, preloadedAdmin.getUserId());
            statement.setInt(2, preloadedProfessor.getUserId());
            statement.setInt(3, preloadedStudent.getUserId());
            statement.setInt(4, preloadedAcademicBodyHead.getUserId());
            statement.setInt(5, preloadedDirector.getUserId());
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
        Student result = instance.logInStudent("zS23587532", "MeLoveCats1234");
        
        assertEquals(preloadedStudent, result);
    }
    
    @Test
    public void testLogInStudentFail() throws LoginException{
        LoginDAO instance = new LoginDAO();
        Student result = instance.logInStudent("zS23587532", "Xavier0573");
        
        System.out.println("Testing LogStudent with wrong password");
        
        assertNotEquals(preloadedStudent, result);
    }

    @Test
    public void testLogInProfessor() throws LoginException{
        LoginDAO instance = new LoginDAO();
        Professor result = instance.logInProfessor("luicuellar", "password");
                
        assertEquals(preloadedProfessor, result);
    }
    
    @Test
    public void testLogInProfessorFail() throws LoginException{
        LoginDAO instance = new LoginDAO();
        Professor result = instance.logInProfessor("luicuellar", "contraseña");
        
        System.out.println("Testing LogInProfessor with wrong password");
        
        assertNotEquals(preloadedProfessor, result);
    }

    @Test
    public void testLogInAdmin() throws LoginException{
        LoginDAO instance = new LoginDAO();
        DegreeBoss result = instance.logInAdmin("jorocharan", "vivaElDiseño2023");
        
        
        assertEquals(preloadedAdmin, result);
    }
    
    @Test
    public void testLogInAdminFail() throws LoginException{
        LoginDAO instance = new LoginDAO();
        DegreeBoss result = instance.logInAdmin("jorocharan", "viv34567");
        
        System.out.println("Testing LogInAdmin with wrong password");
        
        assertNotEquals(preloadedAdmin, result);
    }
    
    @Test
    public void testLogInAcademicBodyHead() throws LoginException{
        LoginDAO instance = new LoginDAO();
        AcademicBodyHead result = instance.logInAcademicBodyHead("umarinero", "programaciónOOP5462");
        
        assertEquals(preloadedAcademicBodyHead, result);
    }
    
    @Test
    public void testLogInAcademicBodyHeadFail() throws LoginException{
        LoginDAO instance = new LoginDAO();
        AcademicBodyHead result = instance.logInAcademicBodyHead("umarinero", "prograee24");
        
        System.out.println("\nTesting LogInAcademicBody with wrong password");
        
        assertNotEquals(preloadedAcademicBodyHead, result);
    }
    
    @Test
    public void testLogInDirector() throws LoginException {
        LoginDAO instance = new LoginDAO();
        Director result = instance.logInDirector("ezacosta", "pensamientoDeCampirán2020");
        
        assertEquals(preloadedDirector, result);
    }
    
    @Test
    public void testLogInDirectorFail() throws LoginException {
        LoginDAO instance = new LoginDAO();
        Director result = instance.logInDirector("ezacosta", "habilidadesDePensamiento");
        
        assertNotEquals(preloadedDirector, result);
    }
}