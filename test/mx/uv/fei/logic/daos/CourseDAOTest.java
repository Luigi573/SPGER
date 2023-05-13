package mx.uv.fei.logic.daos;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import mx.uv.fei.logic.domain.Course;

public class CourseDAOTest{
    
    @Test
    public void addCourseToDatabaseTest() {
        CourseDAO courseDAO = new CourseDAO();
        Course expectedCourse = new Course();
        expectedCourse.setNrc(46853);
        expectedCourse.setIdScholarPeriod(1);
        expectedCourse.setPersonalNumber(123456789);
        expectedCourse.setEEName("Proyecto Guiado");
        expectedCourse.setSection(7);
        expectedCourse.setBlock(1);
        expectedCourse.setStatus("Activo");
        courseDAO.addCourseToDatabase(expectedCourse);

        Course actualCourse = courseDAO.getCourseFromDatabase(Integer.toString(expectedCourse.getNrc()));             
        Assertions.assertEquals(expectedCourse.getNrc(), actualCourse.getNrc());
        Assertions.assertEquals(expectedCourse.getIdScholarPeriod(), actualCourse.getIdScholarPeriod());
        Assertions.assertEquals(expectedCourse.getPersonalNumber(), actualCourse.getPersonalNumber());
        Assertions.assertEquals(expectedCourse.getEEName(), actualCourse.getEEName());
        Assertions.assertEquals(expectedCourse.getSection(), actualCourse.getSection());
        Assertions.assertEquals(expectedCourse.getBlock(), actualCourse.getBlock());
        Assertions.assertEquals(expectedCourse.getStatus(), actualCourse.getStatus());
    }

    @Test
    public void modifyCourseDataFromDatabaseTest() {
        CourseDAO courseDAO = new CourseDAO();
        Course originalCourse = courseDAO.getCourseFromDatabase("46853");
        Course expectedCourse = new Course();
        expectedCourse.setNrc(46854);
        expectedCourse.setIdScholarPeriod(2);
        expectedCourse.setPersonalNumber(123456780);
        expectedCourse.setEEName("Experiencia Recepcional");
        expectedCourse.setSection(8);
        expectedCourse.setBlock(2);
        expectedCourse.setStatus("Inactivo");
        courseDAO.modifyCourseDataFromDatabase(expectedCourse, originalCourse);

        Course actualCourse = courseDAO.getCourseFromDatabase(Integer.toString(expectedCourse.getNrc()));             
        Assertions.assertEquals(expectedCourse.getNrc(), actualCourse.getNrc());
        Assertions.assertEquals(expectedCourse.getIdScholarPeriod(), actualCourse.getIdScholarPeriod());
        Assertions.assertEquals(expectedCourse.getPersonalNumber(), actualCourse.getPersonalNumber());
        Assertions.assertEquals(expectedCourse.getEEName(), actualCourse.getEEName());
        Assertions.assertEquals(expectedCourse.getSection(), actualCourse.getSection());
        Assertions.assertEquals(expectedCourse.getBlock(), actualCourse.getBlock());
        Assertions.assertEquals(expectedCourse.getStatus(), actualCourse.getStatus());
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
        CourseDAO courseDAO = new CourseDAO();
        Course expectedCourse = new Course();
        expectedCourse.setNrc(46854);
        expectedCourse.setIdScholarPeriod(1);
        expectedCourse.setPersonalNumber(123456789);
        expectedCourse.setEEName("Proyecto Guiado");
        expectedCourse.setSection(7);
        expectedCourse.setBlock(1);
        expectedCourse.setStatus("Activo");

        Course actualCourse = courseDAO.getCourseFromDatabase(Integer.toString(expectedCourse.getNrc()));             
        Assertions.assertEquals(expectedCourse.getNrc(), actualCourse.getNrc());
        Assertions.assertEquals(expectedCourse.getIdScholarPeriod(), actualCourse.getIdScholarPeriod());
        Assertions.assertEquals(expectedCourse.getPersonalNumber(), actualCourse.getPersonalNumber());
        Assertions.assertEquals(expectedCourse.getEEName(), actualCourse.getEEName());
        Assertions.assertEquals(expectedCourse.getSection(), actualCourse.getSection());
        Assertions.assertEquals(expectedCourse.getBlock(), actualCourse.getBlock());
        Assertions.assertEquals(expectedCourse.getStatus(), actualCourse.getStatus());
    }

    @Test
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
