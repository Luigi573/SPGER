package mx.uv.fei.logic.daos;

import org.junit.jupiter.api.Test;

public class StudentsCoursesDAOTest {
    @Test
    void addStudentCourseToDatabaseTest() {
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
    void getStudentsMatriclesByCourseNRCFromDatabaseTest() {

    }
}
