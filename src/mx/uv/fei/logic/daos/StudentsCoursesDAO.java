package mx.uv.fei.logic.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.logic.daosinterfaces.IStudentsCoursesDAO;
import mx.uv.fei.logic.domain.Course;
import mx.uv.fei.logic.domain.Professor;
import mx.uv.fei.logic.domain.ScholarPeriod;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.exceptions.DataInsertionException;

public class StudentsCoursesDAO implements IStudentsCoursesDAO{
    private final DataBaseManager dataBaseManager;

    public StudentsCoursesDAO(){
        dataBaseManager = new DataBaseManager();
    }

    @Override
    public void addStudentCourse (String studentMatricle, String courseNRC) throws DataInsertionException{
        try{
            String query = "INSERT INTO EstudiantesCurso (Matrícula, NRC) VALUES (?, ?)";
            PreparedStatement preparedStatement = 
            dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setString(1, studentMatricle);
            preparedStatement.setInt(2, Integer.parseInt(courseNRC));
            preparedStatement.executeUpdate();
        }catch(SQLException e){
            throw new DataInsertionException("Fallo al registrar estudiantes al curso. Verifique su conexion e intentelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }
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
            dataBaseManager.closeConnection();
        }catch(SQLException e){
            throw new DataRetrievalException("Fallo al recuperar la informacion. Verifique su conexion e intentelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }

        return studentsMatricles;
    }

    @Override
    public void removeStudentCourse(String studentMatricle, String courseNRC) throws DataInsertionException{
        String query = "DELETE FROM EstudiantesCurso WHERE Matrícula = ? && NRC = ?";
        
        try{
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setString(1, studentMatricle);
            preparedStatement.setInt(2, Integer.parseInt(courseNRC));
            
            preparedStatement.executeUpdate();
        }catch(SQLException e){
            throw new DataInsertionException("Fallo al eliminar el estudiante del curso. Verifique su conexion e intentelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }
    } 
    
    public ArrayList<Course> getStudentCourses(String matricle) throws DataRetrievalException{
        ArrayList<Course> courseList = new ArrayList();
        PreparedStatement statement;
        String query = "SELECT c.NRC, c.NumPersonal, c.nombre, c.sección, c.bloque, up.nombre, up.apellidoPaterno, up.apellidoMaterno, "
                + "pe.fechaInicio, pe.fechaFin FROM EstudiantesCurso ec LEFT JOIN Cursos c ON ec.NRC = c.NRC "
                + "LEFT JOIN PeriodosEscolares pe ON c.IdPeriodoEscolar = pe.IdPeriodoEscolar "
                + "LEFT JOIN Profesores p ON c.NumPersonal = p.NumPersonal LEFT JOIN Usuarios up ON p.IdUsuario = up.IdUsuario "
                + "WHERE ec.Matrícula = ? AND c.estado = 'Activo'  AND c.NumPersonal IS NOT NULL";
        
        try{
            statement = dataBaseManager.getConnection().prepareStatement(query);
            statement.setString(1, matricle);
            
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                Course course = new Course();
                
                course.setName(resultSet.getString("c.nombre"));
                course.setSection(resultSet.getInt("c.sección"));
                course.setBlock(resultSet.getInt("c.bloque"));
                course.setNrc(resultSet.getInt("c.NRC"));
                
                if(!resultSet.wasNull()){
                    Professor professor = new Professor();
                    course.setProfessor(professor);
                    
                    course.getProfessor().setStaffNumber(resultSet.getInt("c.NumPersonal"));
                    course.getProfessor().setName(resultSet.getString("up.nombre"));
                    course.getProfessor().setFirstSurname(resultSet.getString("up.apellidoPaterno"));
                    course.getProfessor().setSecondSurname(resultSet.getString("up.apellidoMaterno"));
                    
                    ScholarPeriod period = new ScholarPeriod();
                    course.setScholarPeriod(period);
                    course.getScholarPeriod().setStartDate(resultSet.getDate("pe.fechaInicio"));
                    course.getScholarPeriod().setEndDate(resultSet.getDate("pe.fechaFin"));
                }
                
                courseList.add(course);
            }
        }catch(SQLException exception){
            exception.printStackTrace();
            throw new DataRetrievalException("Error de conexión con la base de datos");
        }
        
        return courseList;
    }
}
