package mx.uv.fei.logic.daos;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.logic.domain.Course;
import mx.uv.fei.logic.domain.Professor;
import mx.uv.fei.logic.domain.ScholarPeriod;
import mx.uv.fei.logic.exceptions.DataInsertionException;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.exceptions.DuplicatedPrimaryKeyException;

public class CourseDAOTest{
    private static DataBaseManager dataBaseManager;
    private static Course preloadedCourse;
    private static Course failedCourse;
    private static Course preloadedCourseForAddCourseTest;

    @BeforeClass
    public static void setUpClass() {
        dataBaseManager = new DataBaseManager();
        
        try{    
            //Adding a Course
            preloadedCourse = new Course();
            preloadedCourse.setNrc(67489);
            preloadedCourse.setName("Experiencia Recepcional");
            preloadedCourse.setSection(2);
            preloadedCourse.setBlock(8);
           
            String courseQuery = "INSERT INTO Cursos(NRC, nombre, sección, bloque) VALUES (?, ?, ?, ?)";
            PreparedStatement courseStatement = dataBaseManager.getConnection().prepareStatement(courseQuery);
            courseStatement.setInt(1, preloadedCourse.getNrc());
            courseStatement.setString(2, preloadedCourse.getName());
            courseStatement.setInt(3, preloadedCourse.getSection());
            courseStatement.setInt(4, preloadedCourse.getBlock());
            courseStatement.executeUpdate();
            courseStatement.close();

            //Set data to a failed course
            failedCourse = new Course();
            failedCourse.setNrc(89377);
            failedCourse.setName("Proyecto Guiado");
            failedCourse.setSection(1);
            failedCourse.setBlock(7);

            //Set data to a Course to use in addCourseTest
            preloadedCourseForAddCourseTest = new Course();
            preloadedCourseForAddCourseTest.setNrc(29748);
            preloadedCourseForAddCourseTest.setName("Experiencia Recepcional");
            preloadedCourseForAddCourseTest.setSection(2);
            preloadedCourseForAddCourseTest.setBlock(9);
            preloadedCourseForAddCourseTest.setScholarPeriod(new ScholarPeriod());
            preloadedCourseForAddCourseTest.setProfessor(new Professor());
        }catch(SQLException exception){
            fail("Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }
    
    @AfterClass
    public static void tearDownClass(){
        PreparedStatement statement;
        String queryToDeleteCourse = "DELETE FROM Cursos WHERE NRC IN(?,?)";
        
        try{
            statement = dataBaseManager.getConnection().prepareStatement(queryToDeleteCourse);
            statement.setInt(1, preloadedCourse.getNrc());
            statement.setInt(2, preloadedCourseForAddCourseTest.getNrc());
            
            statement.executeUpdate();
            statement.close();
        }catch(SQLException exception){
            fail("Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }
    
    @Test
    public void addCourseTest() {
        try {
            CourseDAO instance = new CourseDAO();
            int result = instance.addCourse(preloadedCourseForAddCourseTest);
            assertTrue(result > 0);
        }catch(DataInsertionException e) {
            fail("Couldn't connect to DB");
        }catch(DuplicatedPrimaryKeyException e) {
            fail("Duplicated primary key");
        }finally{
            dataBaseManager.closeConnection();
        }
    }

    @Test
    public void modifyCourseDataTest() throws DataInsertionException, DuplicatedPrimaryKeyException{
        CourseDAO instance = new CourseDAO();
        preloadedCourse.setName("Proyecto Guiado");
        preloadedCourse.setBlock(7);
        preloadedCourse.setSection(2);
        preloadedCourse.setNrc(56752);

        int result = instance.modifyCourseData(preloadedCourse, 67489);
        assertTrue(result > 0);
    }

    @Test
    public void getCoursesTest() {
        try {
            CourseDAO instance = new CourseDAO();
            ArrayList<Course> result = instance.getCourses();
            assertTrue(result.contains(preloadedCourse));
        }catch(DataRetrievalException exception){
            fail("Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }

    @Test
    public void getCoursesTestFail() {
        try {
            CourseDAO instance = new CourseDAO();
            ArrayList<Course> result = instance.getCourses();
            assertTrue(!result.contains(failedCourse));
        }catch(DataRetrievalException exception){
            fail("Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }

    @Test
    public void getSpecifiedCoursesTest() {
        try {
            CourseDAO instance = new CourseDAO();
            ArrayList<Course> result = instance.getSpecifiedCourses("6");
            assertTrue(result.contains(preloadedCourse));
        }catch(DataRetrievalException exception){
            fail("Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }

    @Test
    public void getSpecifiedCoursesTestFail() {
        try {
            CourseDAO instance = new CourseDAO();
            ArrayList<Course> result = instance.getSpecifiedCourses("6");
            assertTrue(!result.contains(failedCourse));
        }catch(DataRetrievalException exception){
            fail("Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }

    @Test
    public void getCourseTest() throws DataRetrievalException{  
        try {
            CourseDAO instance = new CourseDAO();
            Course result = instance.getCourse(Integer.toString(preloadedCourse.getNrc()));
            assertTrue(result.equals(preloadedCourse));
        }catch(DataRetrievalException exception){
            fail("Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }

    @Test
    public void getCourseTestFail() throws DataRetrievalException{  
        try {
            CourseDAO instance = new CourseDAO();
            Course result = instance.getCourse(Integer.toString(preloadedCourse.getNrc()));
            assertTrue(!result.equals(failedCourse));
        }catch(DataRetrievalException exception){
            fail("Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }
}
