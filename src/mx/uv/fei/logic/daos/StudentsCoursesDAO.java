package mx.uv.fei.logic.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.logic.daosinterfaces.IStudentsCoursesDAO;
import mx.uv.fei.logic.domain.Course;
import mx.uv.fei.logic.domain.statuses.CourseStatus;
import mx.uv.fei.logic.exceptions.DataInsertionException;
import mx.uv.fei.logic.exceptions.DataRetrievalException;

public class StudentsCoursesDAO implements IStudentsCoursesDAO {
    private final DataBaseManager dataBaseManager;

    public StudentsCoursesDAO() {
        dataBaseManager = new DataBaseManager();
    }

    @Override
    public int addStudentCourse(String studentMatricle, String courseNRC) throws DataInsertionException {
        int result = 0;

        try {
            String query = "INSERT INTO StudentsCourses (matricle, nrc) VALUES (?, ?)";
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setString(1, studentMatricle);
            preparedStatement.setInt(2, Integer.parseInt(courseNRC));

            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataInsertionException("Fallo al registrar estudiantes al curso. Inténtelo de nuevo más tarde");
        } finally {
            dataBaseManager.closeConnection();
        }
        return result;
    }

    @Override
    public ArrayList<String> getStudentsMatriclesByCourseNRC(String courseNRC) throws DataRetrievalException {
        ArrayList<String> studentsMatricles = new ArrayList<>();

        try {
            String query = "SELECT matricle FROM StudentsCourses WHERE nrc = ?";
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, Integer.parseInt(courseNRC));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                studentsMatricles.add(resultSet.getString("matricle"));
            }
            resultSet.close();
            dataBaseManager.closeConnection();
        } catch (SQLException e) {
            throw new DataRetrievalException("Fallo al recuperar la informacion. Inténtelo de nuevo más tarde");
        } finally {
            dataBaseManager.closeConnection();
        }

        return studentsMatricles;
    }

    @Override
    public void removeStudentCourse(String studentMatricle, String courseNRC) throws DataInsertionException {
        String query = "DELETE FROM StudentsCourses WHERE matricle = ? && nrc = ?";

        try {
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setString(1, studentMatricle);
            preparedStatement.setInt(2, Integer.parseInt(courseNRC));

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataInsertionException("Fallo al eliminar el estudiante del curso. Inténtelo de nuevo más tarde");
        } finally {
            dataBaseManager.closeConnection();
        }
    }

    public ArrayList<Course> getStudentCourses(String matricle) throws DataRetrievalException {
        ArrayList<Course> courseList = new ArrayList<>();
        PreparedStatement statement;
        String query = "SELECT * FROM StudentsCourses sc LEFT JOIN Courses c ON sc.nrc = c.nrc "
                + "WHERE sc.matricle = ? AND c.status = ? AND c.staffNumber IS NOT NULL";

        try {
            statement = dataBaseManager.getConnection().prepareStatement(query);
            statement.setString(1, matricle);
            statement.setString(2, CourseStatus.ACTIVE.getValue());

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Course course = new Course();
                course.setName(resultSet.getString("c.name"));
                course.setSection(resultSet.getInt("c.section"));
                course.setBlock(resultSet.getInt("c.block"));
                course.setNrc(resultSet.getInt("c.nrc"));
                course.setScholarPeriodId(resultSet.getInt("c.scholarPeriodId"));
                course.setStaffNumber(resultSet.getInt("c.staffNumber"));
                courseList.add(course);
            }
        } catch (SQLException exception) {
            throw new DataRetrievalException("Error de conexión con la base de datos");
        }

        return courseList;
    }
}