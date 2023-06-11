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
import mx.uv.fei.logic.domain.Professor;
import mx.uv.fei.logic.domain.ScholarPeriod;
import mx.uv.fei.logic.exceptions.DataInsertionException;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.exceptions.DuplicatedPrimaryKeyException;

public class CourseDAO implements ICourseDAO{
    private final DataBaseManager dataBaseManager;

    public CourseDAO(){
        dataBaseManager = new DataBaseManager();
    }
    
    @Override
    public int addCourse(Course course) throws DataInsertionException, DuplicatedPrimaryKeyException{
        int generatedId = 0;
        String query = 
        "INSERT INTO Cursos (NRC, IdPeriodoEscolar, NumPersonal, nombreEE, sección, bloque)" +
        " VALUES (?, ?, ?, ?, ?, ?)";
        try{
            PreparedStatement preparedStatement = 
                dataBaseManager.getConnection().prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, course.getNrc());

            if(course.getScholarPeriod() != null){
                preparedStatement.setInt(2, course.getScholarPeriod().getScholarPeriodId());
            }else{
                preparedStatement.setString(2, null);
            }

            if(course.getProfessor() != null){
                preparedStatement.setInt(3, course.getProfessor().getStaffNumber());
            }else{
                preparedStatement.setString(3, null);
            }

            preparedStatement.setString(4, course.getName());
            preparedStatement.setInt(5, course.getSection());
            preparedStatement.setInt(6, course.getBlock());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                generatedId = resultSet.getInt(1);
            }

        }catch(SQLIntegrityConstraintViolationException e){
            throw new DuplicatedPrimaryKeyException("Curso ya registrado en el sistema");
        }catch(SQLException e){
            throw new DataInsertionException("Error al agregar curso. Inténtelo de nuevo más tarde");
        }finally{
            dataBaseManager.closeConnection();
        }
        return generatedId;
    }

    @Override
    public int modifyCourseData(Course course) throws DataInsertionException, DuplicatedPrimaryKeyException{
        int result = 0;
        try{
            String query = "UPDATE Cursos SET NRC = ?, IdPeriodoEscolar = ?, NumPersonal = ?, nombreEE = ?, " + 
                           "sección = ?, bloque = ? WHERE NRC = ?";
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, course.getNrc());
            preparedStatement.setInt(2, course.getScholarPeriod().getScholarPeriodId());
            preparedStatement.setInt(3, course.getProfessor().getStaffNumber());
            preparedStatement.setString(4, course.getName());
            preparedStatement.setInt(5, course.getSection());
            preparedStatement.setInt(6, course.getBlock());
            preparedStatement.setInt(7, course.getNrc());
            System.out.println(preparedStatement.toString());
            result = preparedStatement.executeUpdate();
        }catch(SQLIntegrityConstraintViolationException e){
            throw new DuplicatedPrimaryKeyException("Curso ya registrado en el sistema");
        }catch(SQLException e){
            throw new DataInsertionException("Error al modificar curso. Inténtelo de nuevo más tarde");
        }finally{
            dataBaseManager.closeConnection();
        }

        return result;
    }

    @Override
    public ArrayList<Course> getCourses() throws DataRetrievalException{
        ArrayList<Course> courses = new ArrayList<>();

        try{
            Statement statement = dataBaseManager.getConnection().createStatement();
            String query = "SELECT * FROM Cursos";
            
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                Course course = new Course();
                course.setNrc(resultSet.getInt("NRC"));
                course.setName(resultSet.getString("nombreEE"));
                course.setSection(resultSet.getInt("sección"));
                course.setBlock(resultSet.getInt("bloque"));
                
                ScholarPeriod scholarPeriod = new ScholarPeriod();
                scholarPeriod.setScholarPeriodId(resultSet.getInt("IdPeriodoEscolar"));
                course.setScholarPeriod(scholarPeriod);
                Professor professor = new Professor();
                professor.setStaffNumber(resultSet.getInt("NumPersonal"));
                course.setProfessor(professor);

                courses.add(course);
            }
            resultSet.close();
        }catch(SQLException e){
            throw new DataRetrievalException("Fallo al recuperar la informacion. Inténtelo de nuevo más tarde");
        }finally{
            dataBaseManager.closeConnection();
        }

        return courses;
    }

    @Override
    public ArrayList<Course> getSpecifiedCourses(String courseName) throws DataRetrievalException{
        ArrayList<Course> courses = new ArrayList<>();

        try{
            String query = "SELECT * FROM Cursos WHERE NRC LIKE ?";
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setString(1, courseName + '%');
            ResultSet resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()){
                Course course = new Course();
                course.setNrc(resultSet.getInt("NRC"));
                course.setName(resultSet.getString("nombreEE"));
                course.setSection(resultSet.getInt("sección"));
                course.setBlock(resultSet.getInt("bloque"));
                
                ScholarPeriod scholarPeriod = new ScholarPeriod();
                scholarPeriod.setScholarPeriodId(resultSet.getInt("IdPeriodoEscolar"));
                course.setScholarPeriod(scholarPeriod);
                Professor professor = new Professor();
                professor.setStaffNumber(resultSet.getInt("NumPersonal"));
                course.setProfessor(professor);
                
                courses.add(course);
            }
            
            resultSet.close();
        }catch(SQLException e){
            throw new DataRetrievalException("Fallo al recuperar la informacion. Inténtelo de nuevo más tarde");
        }finally{
            dataBaseManager.closeConnection();
        }

        return courses;
    }
    
    @Override
    public Course getCourse(String courseNrc) throws DataRetrievalException{
        Course course = new Course();

        try{
            String query = "SELECT * FROM Cursos WHERE NRC = ?";
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setString(1, courseNrc);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            if(resultSet.next()){
                course.setNrc(resultSet.getInt("NRC"));
                course.setName(resultSet.getString("nombreEE"));
                course.setSection(resultSet.getInt("sección"));
                course.setBlock(resultSet.getInt("bloque"));
                
                ScholarPeriod scholarPeriod = new ScholarPeriod();
                scholarPeriod.setScholarPeriodId(resultSet.getInt("IdPeriodoEscolar"));
                course.setScholarPeriod(scholarPeriod);
                Professor professor = new Professor();
                professor.setStaffNumber(resultSet.getInt("NumPersonal"));
                course.setProfessor(professor);
            }
            
            resultSet.close();
        }catch(SQLException e){
            throw new DataRetrievalException("Fallo al recuperar la informacion. Inténtelo de nuevo más tarde");
        }finally{
            dataBaseManager.closeConnection();
        }

        return course;
    }
}
