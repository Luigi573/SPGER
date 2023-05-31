package mx.uv.fei.logic.daos;

import mx.uv.fei.logic.domain.Course;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.exceptions.DataInsertionException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class CourseDAOTest{
    
    @Test
    public void addCourseTest() {
        try {
            CourseDAO courseDAO = new CourseDAO();
            Course expectedCourse = new Course();
            expectedCourse.setNrc(10000);
            expectedCourse.setIdScholarPeriod(1);
            expectedCourse.setStaffNumber(100000000);
            expectedCourse.setName("Proyecto Guiado");
            expectedCourse.setSection(1);
            expectedCourse.setBlock(7);
            courseDAO.addCourse(expectedCourse);
            
            Course actualCourse = courseDAO.getCourse(Integer.toString(expectedCourse.getNrc()));     
            assertTrue(expectedCourse.equals(actualCourse));
        } catch (DataInsertionException e) {
            ();
        } catch (DataRetrievalException e) {
            ();
        }

    }

    @Test
    public int modifyCourseDataTest() {
        try {
            CourseDAO courseDAO = new CourseDAO();
            Course originalCourse = courseDAO.getCourse("10000");
            Course expectedCourse = new Course();
            expectedCourse.setNrc(10002);
            expectedCourse.setIdScholarPeriod(1);
            expectedCourse.setStaffNumber(100000001);
            expectedCourse.setName("Experiencia Recepcional");
            expectedCourse.setSection(2);
            expectedCourse.setBlock(8);
            courseDAO.modifyCourseData(expectedCourse, originalCourse);

            Course actualCourse = courseDAO.getCourse(Integer.toString(expectedCourse.getNrc()));             
            assertTrue(expectedCourse.equals(actualCourse));
        } catch (DataInsertionException e) {
            ();
        } catch (DataRetrievalException e) {
            ();
        }
    }

    @Test
    public void getCoursesTest() {
        try {
            CourseDAO courseDAO = new CourseDAO();
            Course actualCourse = courseDAO.getCourses().get(0);

            Course expectedCourse = new Course();
            expectedCourse.setNrc(10001);
            expectedCourse.setIdScholarPeriod(1);
            expectedCourse.setStaffNumber(100000000);
            expectedCourse.setName("Proyecto Guiado");
            expectedCourse.setSection(2);
            expectedCourse.setBlock(7);

            assertTrue(expectedCourse.equals(actualCourse));
        } catch (DataRetrievalException e) {
            ();
        }
    }

    @Test
    public void getSpecifiedCoursesTest() {
        try {
            CourseDAO courseDAO = new CourseDAO();
            Course actualCourse = courseDAO.getSpecifiedCourses("1").get(1);

            Course expectedCourse = new Course();
            expectedCourse.setNrc(10001);
            expectedCourse.setIdScholarPeriod(1);
            expectedCourse.setStaffNumber(100000000);
            expectedCourse.setName("Proyecto Guiado");
            expectedCourse.setSection(2);
            expectedCourse.setBlock(7);

            assertTrue(expectedCourse.equals(actualCourse));
        } catch (DataRetrievalException e) {
            ();
        }
    }

    @Test
    public void getCourseTest() throws DataRetrievalException{  
            CourseDAO courseDAO = new CourseDAO();
            Course expectedCourse = new Course();
            expectedCourse.setNrc(10001);
            expectedCourse.setIdScholarPeriod(1);
            expectedCourse.setStaffNumber(100000000);
            expectedCourse.setName("Proyecto Guiado");
            expectedCourse.setSection(2);
            expectedCourse.setBlock(7);

            Course actualCourse = courseDAO.getCourse(Integer.toString(expectedCourse.getNrc()));             
            assertTrue(expectedCourse.equals(actualCourse));
    }

    @Test
    public void theCourseIsAlreadyRegistedTest() {
        try {
            CourseDAO courseDAO = new CourseDAO();
            Course expectedCourse = new Course();
            expectedCourse.setNrc(10001);
            expectedCourse.setIdScholarPeriod(1);
            expectedCourse.setStaffNumber(100000000);
            expectedCourse.setName("Proyecto Guiado");
            expectedCourse.setSection(2);
            expectedCourse.setBlock(7);

            assertTrue(courseDAO.theCourseIsAlreadyRegisted(expectedCourse));
        } catch (DataRetrievalException e) {
            ();
        }
    }
}
