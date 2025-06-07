package mx.uv.fei.logic.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;

import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.logic.daosinterfaces.ICourseDAO;
import mx.uv.fei.logic.domain.Course;
import mx.uv.fei.logic.domain.statuses.CourseStatus;
import mx.uv.fei.logic.exceptions.DataInsertionException;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.exceptions.DuplicatedPrimaryKeyException;

public class CourseDAO implements ICourseDAO {
    private final DataBaseManager dataBaseManager;

    public CourseDAO() {
        dataBaseManager = new DataBaseManager();
    }

    @Override
    public int addCourse(Course course) throws DataInsertionException, DuplicatedPrimaryKeyException {
        int generatedId = 0;
        String query = "INSERT INTO Courses (nrc, scholarPeriodId, staffNumber, name, section, block, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(query,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, course.getNrc());
            preparedStatement.setInt(2, course.getScholarPeriodId());
            preparedStatement.setInt(3, course.getStaffNumber());
            preparedStatement.setString(4, course.getName());
            preparedStatement.setInt(5, course.getSection());
            preparedStatement.setInt(6, course.getBlock());
            preparedStatement.setString(7, CourseStatus.ACTIVE.getValue());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                generatedId = resultSet.getInt(1);
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DuplicatedPrimaryKeyException("Curso ya registrado en el sistema");
        } catch (SQLException e) {
            throw new DataInsertionException("Error al agregar curso. Inténtelo de nuevo más tarde");
        } finally {
            dataBaseManager.closeConnection();
        }
        return generatedId;
    }

    @Override
    public int modifyCourseData(Course course, int oldNrc)
            throws DataInsertionException, DuplicatedPrimaryKeyException {
        int result = 0;
        try {
            String query = "UPDATE Courses SET nrc = ?, scholarPeriodId = ?, staffNumber = ?, name = ?, " +
                    "section = ?, block = ?, status = ? WHERE nrc = ?";
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, course.getNrc());
            preparedStatement.setInt(2, course.getScholarPeriodId());
            preparedStatement.setInt(3, course.getStaffNumber());
            preparedStatement.setString(4, course.getName());
            preparedStatement.setInt(5, course.getSection());
            preparedStatement.setInt(6, course.getBlock());
            preparedStatement.setString(7, course.getStatus());
            preparedStatement.setInt(8, oldNrc);

            result = preparedStatement.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DuplicatedPrimaryKeyException("Curso ya registrado en el sistema");
        } catch (SQLException e) {
            throw new DataInsertionException("Error al modificar curso. Inténtelo de nuevo más tarde");
        } finally {
            dataBaseManager.closeConnection();
        }

        return result;
    }

    @Override
    public ArrayList<Course> getCourses() throws DataRetrievalException {
        ArrayList<Course> courses = new ArrayList<>();

        try {
            Statement statement = dataBaseManager.getConnection().createStatement();
            String query = "SELECT nrc, scholarPeriodId, staffNumber, name, section, block, status FROM Courses";

            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Course course = new Course();
                course.setNrc(resultSet.getInt("nrc"));
                course.setScholarPeriodId(resultSet.getInt("scholarPeriodId"));
                course.setStaffNumber(resultSet.getInt("staffNumber"));
                course.setName(resultSet.getString("name"));
                course.setSection(resultSet.getInt("section"));
                course.setBlock(resultSet.getInt("block"));
                course.setStatus(resultSet.getString("status"));
                courses.add(course);
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new DataRetrievalException("Fallo al recuperar la informacion. Inténtelo de nuevo más tarde");
        } finally {
            dataBaseManager.closeConnection();
        }

        return courses;
    }

    @Override
    public ArrayList<Course> getSpecifiedCourses(String courseName) throws DataRetrievalException {
        ArrayList<Course> courses = new ArrayList<>();

        try {
            String query = "SELECT * FROM Courses WHERE nrc LIKE ?";
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setString(1, courseName + '%');
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Course course = new Course();
                course.setNrc(resultSet.getInt("nrc"));
                course.setScholarPeriodId(resultSet.getInt("scholarPeriodId"));
                course.setStaffNumber(resultSet.getInt("staffNumber"));
                course.setName(resultSet.getString("name"));
                course.setSection(resultSet.getInt("section"));
                course.setBlock(resultSet.getInt("block"));
                course.setStatus(resultSet.getString("status"));
                courses.add(course);
            }

            resultSet.close();
        } catch (SQLException e) {
            throw new DataRetrievalException("Fallo al recuperar la informacion. Inténtelo de nuevo más tarde");
        } finally {
            dataBaseManager.closeConnection();
        }

        return courses;
    }

    @Override
    public Course getCourse(String courseNrc) throws DataRetrievalException {
        Course course = new Course();

        try {
            String query = "SELECT * FROM Courses WHERE nrc = ?";
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setString(1, courseNrc);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                course.setNrc(resultSet.getInt("nrc"));
                course.setScholarPeriodId(resultSet.getInt("scholarPeriodId"));
                course.setStaffNumber(resultSet.getInt("staffNumber"));
                course.setName(resultSet.getString("name"));
                course.setSection(resultSet.getInt("section"));
                course.setBlock(resultSet.getInt("block"));
                course.setStatus(resultSet.getString("status"));
            }

            resultSet.close();
        } catch (SQLException e) {
            throw new DataRetrievalException("Fallo al recuperar la informacion. Inténtelo de nuevo más tarde");
        } finally {
            dataBaseManager.closeConnection();
        }

        return course;
    }
}
