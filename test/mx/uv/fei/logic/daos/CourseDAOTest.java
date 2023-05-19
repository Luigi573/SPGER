package mx.uv.fei.logic.daos;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import mx.uv.fei.logic.domain.Course;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.exceptions.DataWritingException;

public class CourseDAOTest{
    
    @Test
    public void addCourseToDatabaseTest() {
        try {
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
        } catch (DataWritingException e) {
            e.printStackTrace();
        } catch (DataRetrievalException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void modifyCourseDataFromDatabaseTest() {
        try {
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
        } catch (DataWritingException e) {
            e.printStackTrace();
        } catch (DataRetrievalException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getCoursesFromDatabaseTest() {
        try {
            CourseDAO courseDAO = new CourseDAO();
            Course actualCourse = courseDAO.getCoursesFromDatabase().get(0);

            Course expectedCourse = new Course();
            expectedCourse.setNrc(44471);
            expectedCourse.setIdScholarPeriod(1);
            expectedCourse.setStaffNumber(123456789);
            expectedCourse.setEEName("Experiencia Recepcional");
            expectedCourse.setSection(7);
            expectedCourse.setBlock(1);

            Assertions.assertTrue(expectedCourse.equals(actualCourse));
        } catch (DataRetrievalException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getSpecifiedCoursesFromDatabaseTest() {
        try {
            CourseDAO courseDAO = new CourseDAO();
            Course actualCourse = courseDAO.getSpecifiedCoursesFromDatabase("4").get(0);

            Course expectedCourse = new Course();
            expectedCourse.setNrc(44471);
            expectedCourse.setIdScholarPeriod(1);
            expectedCourse.setStaffNumber(123456789);
            expectedCourse.setEEName("Experiencia Recepcional");
            expectedCourse.setSection(7);
            expectedCourse.setBlock(1);

            Assertions.assertTrue(expectedCourse.equals(actualCourse));
        } catch (DataRetrievalException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getCourseFromDatabaseTest() {
        try {    
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
        } catch (DataRetrievalException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void theCourseIsAlreadyRegistedTest() {
        try {
            CourseDAO courseDAO = new CourseDAO();
            Course expectedCourse = new Course();
            expectedCourse.setNrc(46854);
            expectedCourse.setIdScholarPeriod(1);
            expectedCourse.setStaffNumber(123456789);
            expectedCourse.setEEName("Experiencia Recepcional");
            expectedCourse.setSection(8);
            expectedCourse.setBlock(2);

            Assertions.assertTrue(courseDAO.theCourseIsAlreadyRegisted(expectedCourse));
        } catch (DataRetrievalException e) {
            e.printStackTrace();
        }
    }
}
