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
        expectedCourse.setStaffNumber(123456789);
        expectedCourse.setEEName("Proyecto Guiado");
        expectedCourse.setSection(7);
        expectedCourse.setBlock(1);
        courseDAO.addCourseToDatabase(expectedCourse);

        Course actualCourse = courseDAO.getCourseFromDatabase(Integer.toString(expectedCourse.getNrc()));     
        Assertions.assertTrue(expectedCourse.equals(actualCourse));
    }

    @Test
    public void modifyCourseDataFromDatabaseTest() {
        CourseDAO courseDAO = new CourseDAO();
        Course originalCourse = courseDAO.getCourseFromDatabase("46853");
        Course expectedCourse = new Course();
        expectedCourse.setNrc(46854);
        expectedCourse.setIdScholarPeriod(1);
        expectedCourse.setStaffNumber(123456789);
        expectedCourse.setEEName("Experiencia Recepcional");
        expectedCourse.setSection(8);
        expectedCourse.setBlock(2);
        courseDAO.modifyCourseDataFromDatabase(expectedCourse, originalCourse);

        Course actualCourse = courseDAO.getCourseFromDatabase(Integer.toString(expectedCourse.getNrc()));             
        Assertions.assertTrue(expectedCourse.equals(actualCourse));
    }

    @Test
    public void getCoursesFromDatabaseTest() {
        CourseDAO courseDAO = new CourseDAO();
        Course actualCourse = courseDAO.getCoursesFromDatabase().get(0);

        Course expectedCourse = new Course();
        expectedCourse.setNrc(44471);
        expectedCourse.setIdScholarPeriod(1);
        expectedCourse.setStaffNumber(123456789);
        expectedCourse.setEEName("Experiencia Recepcional");
        expectedCourse.setSection(8);
        expectedCourse.setBlock(1);
            
        Assertions.assertTrue(expectedCourse.equals(actualCourse));
    }

    @Test
    public void getSpecifiedCoursesFromDatabaseTest() {
        CourseDAO courseDAO = new CourseDAO();
        Course actualCourse = courseDAO.getSpecifiedCoursesFromDatabase("4").get(0);

        Course expectedCourse = new Course();
        expectedCourse.setNrc(44471);
        expectedCourse.setIdScholarPeriod(1);
        expectedCourse.setStaffNumber(123456789);
        expectedCourse.setEEName("Experiencia Recepcional");
        expectedCourse.setSection(8);
        expectedCourse.setBlock(1);
            
        Assertions.assertTrue(expectedCourse.equals(actualCourse));
    }

    @Test
    public void getCourseFromDatabaseTest() {
        CourseDAO courseDAO = new CourseDAO();
        Course expectedCourse = new Course();
        expectedCourse.setNrc(46854);
        expectedCourse.setIdScholarPeriod(1);
        expectedCourse.setStaffNumber(123456789);
        expectedCourse.setEEName("Experiencia Recepcional");
        expectedCourse.setSection(8);
        expectedCourse.setBlock(2);

        Course actualCourse = courseDAO.getCourseFromDatabase(Integer.toString(expectedCourse.getNrc()));             
        Assertions.assertTrue(expectedCourse.equals(actualCourse));
    }

    @Test
    public void theCourseIsAlreadyRegistedTest() {
        CourseDAO courseDAO = new CourseDAO();
        Course expectedCourse = new Course();
        expectedCourse.setNrc(46854);
        expectedCourse.setIdScholarPeriod(1);
        expectedCourse.setStaffNumber(123456789);
        expectedCourse.setEEName("Experiencia Recepcional");
        expectedCourse.setSection(8);
        expectedCourse.setBlock(2);

        Assertions.assertTrue(courseDAO.theCourseIsAlreadyRegisted(expectedCourse));
    }
}
