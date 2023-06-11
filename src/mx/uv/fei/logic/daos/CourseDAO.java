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
import mx.uv.fei.logic.domain.statuses.CourseStatus;
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
        "INSERT INTO Cursos (NRC, IdPeriodoEscolar, NumPersonal, nombre, sección, bloque, estado)" +
        " VALUES (?, ?, ?, ?, ?, ?, ?)";
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
            preparedStatement.setString(7, CourseStatus.ACTIVE.getValue());
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
            String query = "UPDATE Cursos SET NRC = ?, IdPeriodoEscolar = ?, NumPersonal = ?, nombre = ?, " + 
                           "sección = ?, bloque = ?, estado = ? WHERE NRC = ?";
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, course.getNrc());
            preparedStatement.setInt(2, course.getScholarPeriod().getScholarPeriodId());
            preparedStatement.setInt(3, course.getProfessor().getStaffNumber());
            preparedStatement.setString(4, course.getName());
            preparedStatement.setInt(5, course.getSection());
            preparedStatement.setInt(6, course.getBlock());
            preparedStatement.setString(7, course.getStatus());
            preparedStatement.setInt(8, course.getNrc());
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
            String query = "SELECT c.NRC, c.NumPersonal, c.nombre, c.sección, c.bloque, up.nombre, up.apellidoPaterno, up.apellidoMaterno, "
                         + "pe.fechaInicio, pe.fechaFin FROM EstudiantesCurso ec LEFT JOIN Cursos c ON ec.NRC = c.NRC "
                         + "LEFT JOIN PeriodosEscolares pe ON c.IdPeriodoEscolar = pe.IdPeriodoEscolar "
                         + "LEFT JOIN Profesores p ON c.NumPersonal = p.NumPersonal LEFT JOIN Usuarios up ON p.IdUsuario = up.IdUsuario ";
            
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                Course course = new Course();
                course.setName(resultSet.getString("c.nombre"));
                course.setSection(resultSet.getInt("c.sección"));
                course.setBlock(resultSet.getInt("c.bloque"));
                course.setNrc(resultSet.getInt("c.NRC"));
                
                if(resultSet.getString("c.NumPersonal") != null){
                    Professor professor = new Professor();
                    course.setProfessor(professor);
                    
                    course.getProfessor().setStaffNumber(resultSet.getInt("c.NumPersonal"));
                    course.getProfessor().setName(resultSet.getString("up.nombre"));
                    course.getProfessor().setFirstSurname(resultSet.getString("up.apellidoPaterno"));
                    course.getProfessor().setSecondSurname(resultSet.getString("up.apellidoMaterno"));
                }else{
                    course.setProfessor(null);
                }
                
                if(resultSet.getDate("pe.fechaInicio") != null && resultSet.getDate("pe.fechaFin") != null){
                    ScholarPeriod period = new ScholarPeriod();
                    course.setScholarPeriod(period);
                    course.getScholarPeriod().setStartDate(resultSet.getDate("pe.fechaInicio"));
                    course.getScholarPeriod().setEndDate(resultSet.getDate("pe.fechaFin"));
                }else{
                    course.setScholarPeriod(null);
                }
                
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
                course.setName(resultSet.getString("nombre"));
                course.setSection(resultSet.getInt("sección"));
                course.setBlock(resultSet.getInt("bloque"));
                
                if(resultSet.getInt("IdPeriodoEscolar") != 0){
                    ScholarPeriod scholarPeriod = new ScholarPeriod();
                    scholarPeriod.setScholarPeriodId(resultSet.getInt("IdPeriodoEscolar"));
                    course.setScholarPeriod(scholarPeriod);
                }else{
                    course.setScholarPeriod(null);
                }

                if(resultSet.getString("NumPersonal") != null){
                    Professor professor = new Professor();
                    professor.setStaffNumber(resultSet.getInt("NumPersonal"));
                    course.setProfessor(professor);
                }else{
                    course.setProfessor(null);
                }
                
                course.setStatus(resultSet.getString("estado"));
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
                course.setName(resultSet.getString("nombre"));
                course.setSection(resultSet.getInt("sección"));
                course.setBlock(resultSet.getInt("bloque"));
                
                if(resultSet.getInt("IdPeriodoEscolar") != 0){
                    ScholarPeriod scholarPeriod = new ScholarPeriod();
                    scholarPeriod.setScholarPeriodId(resultSet.getInt("IdPeriodoEscolar"));
                    course.setScholarPeriod(scholarPeriod);
                }else{
                    course.setScholarPeriod(null);
                }

                if(resultSet.getString("NumPersonal") != null){
                    Professor professor = new Professor();
                    professor.setStaffNumber(resultSet.getInt("NumPersonal"));
                    course.setProfessor(professor);
                }else{
                    course.setProfessor(null);
                }

                course.setStatus(resultSet.getString("estado"));
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
