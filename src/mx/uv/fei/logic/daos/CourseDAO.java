package mx.uv.fei.logic.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.logic.daosinterfaces.ICourseDAO;
import mx.uv.fei.logic.domain.Course;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.exceptions.DataInsertionException;

public class CourseDAO implements ICourseDAO{

    private final DataBaseManager dataBaseManager;

    public CourseDAO(){
        dataBaseManager = new DataBaseManager();
    }
    
    @Override
    public void addCourseToDatabase(Course course) throws DataInsertionException{
        try{
            String query = 
                "INSERT INTO Cursos (NRC, IdPeriodoEscolar, NumPersonal, nombreEE, sección, bloque)" +
                " VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = 
                dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, course.getNrc());
            preparedStatement.setInt(2, course.getIdScholarPeriod());
            preparedStatement.setInt(3, course.getStaffNumber());
            preparedStatement.setString(4, course.getEEName());
            preparedStatement.setInt(5, course.getSection());
            preparedStatement.setInt(6, course.getBlock());
            preparedStatement.executeUpdate();

        }catch(SQLException e){
            throw new DataInsertionException("Error al agregar curso. Verifique su conexion e intentelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }
    }

    @Override
    public void modifyCourseDataFromDatabase(Course newCourseData, Course originalCourseData) throws DataInsertionException{
        try{
            String query = "UPDATE Cursos SET NRC = ?, " + 
                           "IdPeriodoEscolar = ?, NumPersonal = ?, nombreEE = ?, " + 
                           "sección = ?, bloque = ? WHERE NRC = ?";
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, newCourseData.getNrc());
            preparedStatement.setInt(2, newCourseData.getIdScholarPeriod());
            preparedStatement.setInt(3, newCourseData.getStaffNumber());
            preparedStatement.setString(4, newCourseData.getEEName());
            preparedStatement.setInt(5, newCourseData.getSection());
            preparedStatement.setInt(6, newCourseData.getBlock());
            preparedStatement.setInt(7, originalCourseData.getNrc());
            preparedStatement.executeUpdate();
        }catch(SQLException e){
            throw new DataInsertionException("Error al modificar curso. Verifique su conexion e intentelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }
    }

    @Override
    public ArrayList<Course> getCoursesFromDatabase() throws DataRetrievalException{
        ArrayList<Course> courses = new ArrayList<>();

        try{
            Statement statement = dataBaseManager.getConnection().createStatement();
            String query = "SELECT * FROM Cursos";
            
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                Course course = new Course();
                course.setNrc(resultSet.getInt("NRC"));
                course.setIdScholarPeriod(resultSet.getInt("IdPeriodoEscolar"));
                course.setStaffNumber(resultSet.getInt("NumPersonal"));
                course.setEEName(resultSet.getString("nombreEE"));
                course.setSection(resultSet.getInt("sección"));
                course.setBlock(resultSet.getInt("bloque"));
                courses.add(course);
            }
            resultSet.close();
            dataBaseManager.getConnection().close();
        }catch(SQLException e){
            throw new DataRetrievalException("Fallo al recuperar la informacion. Verifique su conexion e intentelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }

        return courses;
    }

    @Override
    public ArrayList<Course> getSpecifiedCoursesFromDatabase(String courseName) throws DataRetrievalException{
        ArrayList<Course> courses = new ArrayList<>();

        try{
            String query = "SELECT * FROM Cursos WHERE NRC LIKE ?";
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setString(1, courseName + '%');
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Course course = new Course();
                course.setNrc(resultSet.getInt("NRC"));
                course.setIdScholarPeriod(resultSet.getInt("IdPeriodoEscolar"));
                course.setStaffNumber(resultSet.getInt("NumPersonal"));
                course.setEEName(resultSet.getString("nombreEE"));
                course.setSection(resultSet.getInt("sección"));
                course.setBlock(resultSet.getInt("bloque"));
                courses.add(course);
            }
            resultSet.close();
            dataBaseManager.getConnection().close();
        }catch(SQLException e){
            throw new DataRetrievalException("Fallo al recuperar la informacion. Verifique su conexion e intentelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }

        return courses;
    }

    @Override
    public Course getCourseFromDatabase(String courseNrc) throws DataRetrievalException{
        Course course = new Course();

        try{
            String query = "SELECT * FROM Cursos WHERE NRC = ?";
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setString(1, courseNrc);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                course.setNrc(resultSet.getInt("NRC"));
                course.setIdScholarPeriod(resultSet.getInt("IdPeriodoEscolar"));
                course.setStaffNumber(resultSet.getInt("NumPersonal"));
                course.setEEName(resultSet.getString("nombreEE"));
                course.setSection(resultSet.getInt("sección"));
                course.setBlock(resultSet.getInt("bloque"));
            }
            
            resultSet.close();
            dataBaseManager.getConnection().close();
        }catch(SQLException e){
            throw new DataRetrievalException("Fallo al recuperar la informacion. Verifique su conexion e intentelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }

        return course;
    }

    public boolean theCourseIsAlreadyRegisted(Course course) throws DataRetrievalException{
        try{
            Statement statement = dataBaseManager.getConnection().createStatement();
            String query = "SELECT * FROM Cursos";
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()) {
                if(resultSet.getInt("NRC") == course.getNrc() &&
                   resultSet.getInt("IdPeriodoEscolar") == course.getIdScholarPeriod() &&
                   resultSet.getInt("NumPersonal") == course.getStaffNumber() &&
                   resultSet.getString("nombreEE").equals(course.getEEName()) &&
                   resultSet.getInt("sección") == course.getSection() &&
                   resultSet.getInt("bloque") == course.getBlock()){
                    
                   resultSet.close();
                   dataBaseManager.getConnection().close();
                   return true;
                }
            }

            resultSet.close();
            dataBaseManager.getConnection().close();
        }catch(SQLException e){
            throw new DataRetrievalException("Fallo al recuperar la informacion. Verifique su conexion e intentelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }

        return false;
    }
}
