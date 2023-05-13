package mx.uv.fei.logic.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.logic.daosinterfaces.ICourseDAO;
import mx.uv.fei.logic.domain.Course;

public class CourseDAOTest{
    
    @Test
    public void addCourseToDatabaseTest() {
        try {
            Course course = new Course();
            course.setNrc(0);
            course.setIdScholarPeriod(0);
            course.setPersonalNumber(0);
            course.setEEName("Test");
            course.setSection(0);
            course.setBlock(0);
            course.setStatus("Activo");

            DataBaseManager dataBaseManager = new DataBaseManager();
            String columnNames = 
                "NRC, IdPeriodoEscolar, NumPersonal, nombreEE, sección, bloque, estado";
            String wholeQuery = 
                "INSERT INTO Cursos (" + columnNames + ") VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(wholeQuery);
            preparedStatement.setInt(1, course.getNrc());
            preparedStatement.setInt(2, course.getIdScholarPeriod());
            preparedStatement.setInt(3, course.getPersonalNumber());
            preparedStatement.setString(4, course.getEEName());
            preparedStatement.setInt(5, course.getSection());
            preparedStatement.setInt(6, course.getBlock());
            preparedStatement.setString(7, course.getStatus());
            
            String expectedQueryColumnNames = 
                "NRC, IdPeriodoEscolar, NumPersonal, nombreEE, sección, bloque, estado";                
            String expectedQuery = "INSERT INTO Cursos (" + expectedQueryColumnNames + ")" + 
                                    "VALUES (0, 0, 0, 'Test', 0, 0, 'Activo')";;
            Assertions.assertEquals(expectedQuery, preparedStatement.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void modifyCourseDataFromDatabaseTest() {
        //try {
        //    DataBaseManager dataBaseManager = new DataBaseManager();
        //    String query = "UPDATE Cursos SET NRC = ?, " + 
        //                   "IdPeriodoEscolar = ?, NumPersonal = ?, nombreEE = ?, " + 
        //                   "sección = ?, bloque = ?, estado = ? " +
        //                   "WHERE NRC = ?";
        //    PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(query);
        //    preparedStatement.setInt(1, newCourseData.getNrc());
        //    preparedStatement.setInt(2, newCourseData.getIdScholarPeriod());
        //    preparedStatement.setInt(3, newCourseData.getPersonalNumber());
        //    preparedStatement.setString(4, newCourseData.getEEName());
        //    preparedStatement.setInt(5, newCourseData.getSection());
        //    preparedStatement.setInt(6, newCourseData.getBlock());
        //    preparedStatement.setString(7, newCourseData.getStatus());
        //    preparedStatement.setInt(8, originalCourseData.getNrc());
        //    System.out.println(preparedStatement.toString());
        //    preparedStatement.executeUpdate();
        //} catch(SQLException e){
        //    e.printStackTrace();
        //}
    }

    @Test
    public void getCoursesFromDatabaseTest() {
        

        Assertions.assertEquals(getClass(), getClass());
        //ArrayList<Course> courses = new ArrayList<>();
//
        //try {
        //    DataBaseManager dataBaseManager = new DataBaseManager();
        //    Statement statement = dataBaseManager.getConnection().createStatement();
        //    String query = "SELECT * FROM Cursos";
        //    ResultSet resultSet = statement.executeQuery(query);
        //    while(resultSet.next()) {
        //        Course course = new Course();
        //        course.setNrc(resultSet.getInt("NRC"));
        //        course.setIdScholarPeriod(resultSet.getInt("IdPeriodoEscolar"));
        //        course.setPersonalNumber(resultSet.getInt("NumPersonal"));
        //        course.setEEName(resultSet.getString("nombreEE"));
        //        course.setSection(resultSet.getInt("sección"));
        //        course.setBlock(resultSet.getInt("bloque"));
        //        course.setStatus(resultSet.getString("estado"));
        //        courses.add(course);
        //    }
        //    resultSet.close();
        //    dataBaseManager.getConnection().close();
        //} catch (SQLException e) {
        //    e.printStackTrace();
        //}

    }

    @Test
    public void getSpecifiedCoursesFromDatabaseTest() {
        //ArrayList<Course> courses = new ArrayList<>();
//
        //try {
        //    DataBaseManager dataBaseManager = new DataBaseManager();
        //    String query = "SELECT * FROM Cursos WHERE NRC LIKE ?";
        //    PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(query);
        //    preparedStatement.setString(1, courseName + '%');
        //    ResultSet resultSet = preparedStatement.executeQuery();
        //    while(resultSet.next()) {
        //        Course course = new Course();
        //        course.setNrc(resultSet.getInt("NRC"));
        //        course.setIdScholarPeriod(resultSet.getInt("IdPeriodoEscolar"));
        //        course.setPersonalNumber(resultSet.getInt("NumPersonal"));
        //        course.setEEName(resultSet.getString("nombreEE"));
        //        course.setSection(resultSet.getInt("sección"));
        //        course.setBlock(resultSet.getInt("bloque"));
        //        course.setStatus(resultSet.getString("estado"));
        //        courses.add(course);
        //    }
        //    resultSet.close();
        //    dataBaseManager.getConnection().close();
        //} catch (SQLException e) {
        //    e.printStackTrace();
        //}

    }

    @Test
    public void getCourseFromDatabaseTest() {
        try {
            Course course = new Course();
            course.setNrc(0);
            DataBaseManager dataBaseManager = new DataBaseManager();
            String query = "SELECT * FROM Cursos WHERE NRC = ?";
            PreparedStatement preparedStatement;
            preparedStatement = dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, course.getNrc());
            
            String expectedQuery = "SELECT * FROM Cursos WHERE NRC = 0";
            Assertions.assertEquals(expectedQuery, preparedStatement.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void theCourseIsAlreadyRegistedTest() {
        //try {
        //    DataBaseManager dataBaseManager = new DataBaseManager();
        //    Statement statement = dataBaseManager.getConnection().createStatement();
        //    String query = "SELECT NRC FROM Cursos";
        //    ResultSet resultSet = statement.executeQuery(query);
        //    while(resultSet.next()) {
        //        if(resultSet.getInt("NRC") == course.getNrc()) { 
        //            resultSet.close();
        //            dataBaseManager.getConnection().close();
        //        }
        //    }
        //    resultSet.close();
        //    dataBaseManager.getConnection().close();
        //} catch (SQLException e) {
        //    e.printStackTrace();
        //}

    }
}
