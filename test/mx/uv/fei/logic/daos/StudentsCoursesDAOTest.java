package mx.uv.fei.logic.daos;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.exceptions.DataWritingException;

public class StudentsCoursesDAOTest {
    @Test
    void addStudentCourseToDatabaseTest() {
        try {
            StudentsCoursesDAO studentCoursesDAO = new StudentsCoursesDAO();
            String expectedMatricle = "zS10000001";
            String expectedNrc = "10001";
            studentCoursesDAO.addStudentCourseToDatabase(expectedMatricle, expectedNrc);

            Assertions.assertTrue(expectedMatricle.equals(actualMatricle));
        } catch (DataRetrievalException e) {
            e.printStackTrace();
        } catch (DataWritingException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getStudentsMatriclesByCourseNRCFromDatabaseTest() {
        try {
            StudentsCoursesDAO studentCoursesDAO = new StudentsCoursesDAO();
            String actualMatricle = studentCoursesDAO.getStudentsMatriclesByCourseNRCFromDatabase("10001").get(0);
            String expectedMatricle = "zS10000001";

            Assertions.assertTrue(expectedMatricle.equals(actualMatricle));
        } catch (DataRetrievalException e) {
            e.printStackTrace();
        }
    }

    @Test
    void removeStudentCourseFromDatabaseTest() {

    }
}
