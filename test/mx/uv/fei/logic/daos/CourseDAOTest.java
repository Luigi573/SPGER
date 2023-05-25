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
            expectedCourse.setNrc(10000);
            expectedCourse.setIdScholarPeriod(1);
            expectedCourse.setStaffNumber(100000000);
            expectedCourse.setEEName("Proyecto Guiado");
            expectedCourse.setSection(1);
            expectedCourse.setBlock(7);
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
            Course originalCourse = courseDAO.getCourseFromDatabase("10000");
            Course expectedCourse = new Course();
            expectedCourse.setNrc(10002);
            expectedCourse.setIdScholarPeriod(1);
            expectedCourse.setStaffNumber(100000001);
            expectedCourse.setEEName("Experiencia Recepcional");
            expectedCourse.setSection(2);
            expectedCourse.setBlock(8);
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
            expectedCourse.setNrc(10001);
            expectedCourse.setIdScholarPeriod(1);
            expectedCourse.setStaffNumber(100000000);
            expectedCourse.setEEName("Proyecto Guiado");
            expectedCourse.setSection(2);
            expectedCourse.setBlock(7);

            Assertions.assertTrue(expectedCourse.equals(actualCourse));
        } catch (DataRetrievalException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getSpecifiedCoursesFromDatabaseTest() {
        try {
            CourseDAO courseDAO = new CourseDAO();
            Course actualCourse = courseDAO.getSpecifiedCoursesFromDatabase("1").get(1);

            Course expectedCourse = new Course();
            expectedCourse.setNrc(10001);
            expectedCourse.setIdScholarPeriod(1);
            expectedCourse.setStaffNumber(100000000);
            expectedCourse.setEEName("Proyecto Guiado");
            expectedCourse.setSection(2);
            expectedCourse.setBlock(7);

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
            expectedCourse.setNrc(10001);
            expectedCourse.setIdScholarPeriod(1);
            expectedCourse.setStaffNumber(100000000);
            expectedCourse.setEEName("Proyecto Guiado");
            expectedCourse.setSection(2);
            expectedCourse.setBlock(7);

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
            expectedCourse.setNrc(10001);
            expectedCourse.setIdScholarPeriod(1);
            expectedCourse.setStaffNumber(100000000);
            expectedCourse.setEEName("Proyecto Guiado");
            expectedCourse.setSection(2);
            expectedCourse.setBlock(7);

            Assertions.assertTrue(courseDAO.theCourseIsAlreadyRegisted(expectedCourse));
        } catch (DataRetrievalException e) {
            e.printStackTrace();
        }
    }
}
