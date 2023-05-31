package mx.uv.fei.logic.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.logic.daosinterfaces.IStudentsCoursesDAO;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.exceptions.DataInsertionException;

public class StudentsCoursesDAO implements IStudentsCoursesDAO{
    private final DataBaseManager dataBaseManager;

    public StudentsCoursesDAO(){
        dataBaseManager = new DataBaseManager();
    }

    @Override
    public int addStudentCourse(String studentMatricle, String courseNRC) throws DataInsertionException{
        int generatedId = 0;
        try{
            String query = "INSERT INTO EstudiantesCurso (Matrícula, NRC) VALUES (?, ?)";
            PreparedStatement preparedStatement = 
            dataBaseManager.getConnection().prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, studentMatricle);
            preparedStatement.setInt(2, Integer.parseInt(courseNRC));
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                generatedId = resultSet.getInt(1);
            }
        }catch(SQLException e){
            throw new DataInsertionException("Fallo al registrar estudiantes al curso. Verifique su conexion e intentelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }
        return generatedId;
    }

    @Override
    public ArrayList<String> getStudentsMatriclesByCourseNRC(String courseNRC) throws DataRetrievalException{
        ArrayList<String> studentsMatricles = new ArrayList<>();
        
        try {
            String query = "SELECT Matrícula FROM EstudiantesCurso WHERE NRC = ?";
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, Integer.parseInt(courseNRC));
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                studentsMatricles.add(resultSet.getString("Matrícula"));
            }
            resultSet.close();
            dataBaseManager.getConnection().close();
        }catch(SQLException e){
            throw new DataRetrievalException("Fallo al recuperar la informacion. Verifique su conexion e intentelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }

        return studentsMatricles;
    }

    @Override
    public void removeStudentCourse(String studentMatricle, String courseNRC) throws DataInsertionException{
        try{
            String query = "DELETE FROM EstudiantesCurso WHERE Matrícula = ? && NRC = ?";
            PreparedStatement preparedStatement = 
            dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setString(1, studentMatricle);
            preparedStatement.setInt(2, Integer.parseInt(courseNRC));
            preparedStatement.executeUpdate();
        }catch(SQLException e){
            throw new DataInsertionException("Fallo al eliminar el estudiante del curso. Verifique su conexion e intentelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }
    } 
}
