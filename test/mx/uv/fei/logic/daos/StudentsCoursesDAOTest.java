package mx.uv.fei.logic.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.logic.daosinterfaces.IStudentsCoursesDAO;

public class StudentsCoursesDAOTest implements IStudentsCoursesDAO{

    @Override
    public void addStudentCourseToDatabase(String studentMatricle, String courseNRC) {
        try {
            DataBaseManager dataBaseManager = new DataBaseManager();
            String query = "INSERT INTO EstudiantesCurso (Matrícula, NRC) VALUES (?, ?)";
            PreparedStatement preparedStatement = 
            dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setString(1, studentMatricle);
            preparedStatement.setInt(2, Integer.parseInt(courseNRC));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }

    @Override
    public ArrayList<String> getStudentsMatriclesByCourseNRCFromDatabase(String courseNRC) {
        ArrayList<String> studentsMatricles = new ArrayList<>();
        
        try {
            DataBaseManager dataBaseManager = new DataBaseManager();
            String query = "SELECT Matrícula FROM EstudiantesCurso WHERE NRC = ?";
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, Integer.parseInt(courseNRC));
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                studentsMatricles.add(resultSet.getString("Matrícula"));
            }
            resultSet.close();
            dataBaseManager.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return studentsMatricles;
    }

    @Override
    public void removeStudentCourseFromDatabase(String studentMatricle, String courseNRC) {
        try {
            DataBaseManager dataBaseManager = new DataBaseManager();
            String query = "DELETE FROM EstudiantesCurso WHERE Matrícula = ? && NRC = ?";
            PreparedStatement preparedStatement = 
            dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setString(1, studentMatricle);
            preparedStatement.setInt(2, Integer.parseInt(courseNRC));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
}
