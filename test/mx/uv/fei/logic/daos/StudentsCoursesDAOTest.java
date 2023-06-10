package mx.uv.fei.logic.daos;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.logic.domain.Course;
import mx.uv.fei.logic.domain.Student;
import mx.uv.fei.logic.exceptions.DataInsertionException;
import mx.uv.fei.logic.exceptions.DataRetrievalException;

public class StudentsCoursesDAOTest {
    private static DataBaseManager dataBaseManager;
    private static Course preloadedCourse;
    private static Student preloadedStudent;

    @BeforeClass
    public static void setUpClass() {
        dataBaseManager = new DataBaseManager();
        
        try{
            //Adding a Student
            preloadedStudent = new Student();
            preloadedStudent.setName("José Manuel");
            preloadedStudent.setFirstSurname("Macías");
            preloadedStudent.setSecondSurname("Hueto");
            preloadedStudent.setEmailAddress("zS28765676@estudiantes.uv.mx");
            preloadedStudent.setMatricle("zS28765676");
           
            String userQuery = "INSERT INTO Usuarios(nombre, apellidoPaterno, apellidoMaterno, correo) VALUES (?, ?, ?, ?)";
            PreparedStatement userStatement = dataBaseManager.getConnection().prepareStatement(userQuery, Statement.RETURN_GENERATED_KEYS);
            userStatement.setString(1, preloadedStudent.getName());
            userStatement.setString(2, preloadedStudent.getFirstSurname());
            userStatement.setString(3, preloadedStudent.getSecondSurname());
            userStatement.setString(4, preloadedStudent.getEmailAddress());
            userStatement.executeUpdate();
            
            ResultSet generatedUserKeys = userStatement.getGeneratedKeys();
            if(generatedUserKeys.next()){
                preloadedStudent.setUserId(generatedUserKeys.getInt(1));
            }
            generatedUserKeys.close();
            userStatement.close();
            
            String studentQuery = "INSERT INTO Estudiantes(Matrícula, IdUsuario) VALUES (?,?)";
            PreparedStatement studentStatement = dataBaseManager.getConnection().prepareStatement(studentQuery);
            studentStatement.setString(1, preloadedStudent.getMatricle());
            studentStatement.setInt(2, preloadedStudent.getUserId());
            studentStatement.executeUpdate();
            studentStatement.close();

            //Adding a Course
            preloadedCourse = new Course();
            preloadedCourse.setName("Proyecto Guiado");
            preloadedCourse.setBlock(1);
            preloadedCourse.setSection(8);
            preloadedCourse.setNrc(73898);
            String courseQuery = "INSERT INTO Cursos (nombreEE, bloque, sección, NRC) VALUES (?, ?, ?, ?)";
            PreparedStatement courseStatement = dataBaseManager.getConnection().prepareStatement(courseQuery);
            courseStatement.setString(1, preloadedCourse.getName());
            courseStatement.setInt(2, preloadedCourse.getBlock());
            courseStatement.setInt(3, preloadedCourse.getSection());
            courseStatement.setInt(4, preloadedCourse.getNrc());
            courseStatement.executeUpdate();
            courseStatement.close();

            //Adding a StudentCourse
            String studentCourseQuery = "INSERT INTO EstudiantesCurso (Matrícula, NRC) VALUES (?, ?)";
            PreparedStatement studentCourseStatement = dataBaseManager.getConnection().prepareStatement(studentCourseQuery);
            studentCourseStatement.setString(1, preloadedStudent.getMatricle());
            studentCourseStatement.setInt(2, preloadedCourse.getNrc());
            studentCourseStatement.executeUpdate();
            studentCourseStatement.close();
        }catch(SQLException exception){
            fail("Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }
    
    @AfterClass
    public static void tearDownClass(){
        PreparedStatement statement;
        String queryToDeleteUser = "DELETE FROM Usuarios WHERE IdUsuario = ?";
        String queryToDeleteStudent = "DELETE FROM Estudiantes WHERE Matrícula = ?";
        String queryToDeleteCourse = "DELETE FROM Cursos WHERE NRC = ?";
        String queryToDeleteStudentCourse = "DELETE FROM EstudiantesCurso WHERE Matrícula = ? && NRC = ?";
        
        try{
            //Delete User
            statement = dataBaseManager.getConnection().prepareStatement(queryToDeleteUser);
            statement.setInt(1, preloadedStudent.getUserId());
            statement.executeUpdate();

            //Delete Student
            statement = dataBaseManager.getConnection().prepareStatement(queryToDeleteStudent);
            statement.setString(1, preloadedStudent.getMatricle());
            statement.executeUpdate();

            //Delete Course
            statement = dataBaseManager.getConnection().prepareStatement(queryToDeleteCourse);
            statement.setInt(1, preloadedCourse.getNrc());
            statement.executeUpdate();

            //Delete StudentCourse
            statement = dataBaseManager.getConnection().prepareStatement(queryToDeleteStudentCourse);
            statement.setString(1, preloadedStudent.getMatricle());
            statement.setInt(2, preloadedCourse.getNrc());
            statement.executeUpdate();
            
            statement.close();
        }catch(SQLException exception){
            fail("Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }
    
    @Test
    public void addStudentCourseTest() {
        try {
            StudentsCoursesDAO instance = new StudentsCoursesDAO();
            int result = instance.addStudentCourse("zS28765676", Integer.toString(73898));
            assertTrue(result > 0);
        }catch(DataInsertionException exception){
            fail("Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }

    @Test
    public void getStudentsMatriclesByCourseNRCTest() {
        try {
            StudentsCoursesDAO instance = new StudentsCoursesDAO();
            ArrayList<String> result = instance.getStudentsMatriclesByCourseNRC(Integer.toString(73898));
            assertTrue(result.contains("zS28765676"));
        }catch(DataRetrievalException exception){
            fail("Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }

    @Test
    public void getStudentsMatriclesByCourseNRCTestFail() {
        try {
            StudentsCoursesDAO instance = new StudentsCoursesDAO();
            ArrayList<String> result = instance.getStudentsMatriclesByCourseNRC(Integer.toString(73898));
            assertTrue(!result.contains("zS39865676"));
        }catch(DataRetrievalException exception){
            fail("Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }
}
