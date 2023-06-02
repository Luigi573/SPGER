package mx.uv.fei.logic.daos;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.logic.daosinterfaces.ILoginDAO;
import mx.uv.fei.logic.domain.AcademicBodyHead;
import mx.uv.fei.logic.domain.DegreeBoss;
import mx.uv.fei.logic.domain.Director;
import mx.uv.fei.logic.domain.Professor;
import mx.uv.fei.logic.domain.Student;
import mx.uv.fei.logic.exceptions.LoginException;

public class LoginDAO implements ILoginDAO{
    private final DataBaseManager dataBaseManager;
    
    public LoginDAO(){
        dataBaseManager = new DataBaseManager();
    }
    
    @Override
    public DegreeBoss logInAdmin(String emailAddress, String password) throws LoginException {
        PreparedStatement statement;
        String query = "SELECT u.IdUsuario, u.nombre, u.apellidoPaterno, u.apellidoMaterno, u.correo, p.NumPersonal FROM Usuarios u "
                + " INNER JOIN Profesores p ON u.IdUsuario = p.IdUsuario INNER JOIN JefesCarrera jc ON p.NumPersonal = jc.NumPersonal "
                + " WHERE u.correo = ? AND u.contraseña = SHA2(?, 256)";
        DegreeBoss degreeBoss = new DegreeBoss();
        
        try{
            statement = dataBaseManager.getConnection().prepareStatement(query);
            
            statement.setString(1, emailAddress + "@uv.mx");
            statement.setString(2, password);
            
            ResultSet resultSet = statement.executeQuery();
            
            if(resultSet.next()){
                degreeBoss.setName(resultSet.getString("u.nombre"));
                degreeBoss.setFirstSurname(resultSet.getString("u.apellidoPaterno"));
                degreeBoss.setSecondSurname(resultSet.getString("u.apellidoMaterno"));
                degreeBoss.setEmailAddress(resultSet.getString("u.correo"));
                degreeBoss.setUserId(resultSet.getInt("u.IdUsuario"));
                degreeBoss.setStaffNumber(resultSet.getInt("p.NumPersonal"));
            }
        }catch(SQLException exception){
            throw new LoginException("Error de conexion. Favor de verificar su conexion e intentelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }
        
        return degreeBoss;
    }
    
    @Override
    public AcademicBodyHead logInAcademicBodyHead(String emailAddress, String password) throws LoginException {
        PreparedStatement statement;
        String query = "SELECT u.IdUsuario, u.nombre, u.apellidoPaterno, u.apellidoMaterno, u.correo, p.NumPersonal FROM Usuarios u "
                + " INNER JOIN Profesores p ON u.IdUsuario = p.IdUsuario INNER JOIN ResponsablesCA rca ON p.NumPersonal = rca.NumPersonal "
                + " WHERE u.correo = ? AND u.contraseña = SHA2(?, 256)";
        AcademicBodyHead academicBodyHead = new AcademicBodyHead();
        
        try{
            statement = dataBaseManager.getConnection().prepareStatement(query);
            statement.setString(1, emailAddress + "@uv.mx");
            statement.setString(2, password);
            
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                academicBodyHead.setUserId(resultSet.getInt("u.IdUsuario"));
                academicBodyHead.setName(resultSet.getString("u.nombre"));
                academicBodyHead.setFirstSurname(resultSet.getString("u.apellidoPaterno"));
                academicBodyHead.setSecondSurname(resultSet.getString("u.apellidoMaterno"));
                academicBodyHead.setEmailAddress(resultSet.getString("u.correo"));
                academicBodyHead.setStaffNumber(resultSet.getInt("p.NumPersonal"));
            }
        }catch(SQLException exception){
            throw new LoginException("Error de conexion. Verifique su conectividad a  la base de datos e inténtelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }
        
        return academicBodyHead;
    }
    
    public Director logInDirector(String emailAddress, String password) throws LoginException {
        Director director = new Director();
        PreparedStatement statement;
        String query = "SELECT u.IdUsuario, u.nombre, u.apellidoPaterno, u.apellidoMaterno, u.correo, p.NumPersonal FROM Usuarios u "
                + " INNER JOIN Profesores p ON u.IdUsuario = p.IdUsuario INNER JOIN Directores d ON p.NumPersonal = d.NumPersonal "
                + " WHERE u.correo = ? AND u.contraseña = SHA2(?, 256)";
        
        try{
            statement = dataBaseManager.getConnection().prepareStatement(query);
            
            statement.setString(1, emailAddress + "@uv.mx");
            statement.setString(2, password);
            
            ResultSet resultSet = statement.executeQuery();
            
            if(resultSet.next()){
                director.setName(resultSet.getString("u.nombre"));
                director.setFirstSurname(resultSet.getString("u.apellidoPaterno"));
                director.setSecondSurname(resultSet.getString("u.apellidoMaterno"));
                director.setEmailAddress(resultSet.getString("u.correo"));
                director.setUserId(resultSet.getInt("u.IdUsuario"));
                director.setStaffNumber(resultSet.getInt("p.NumPersonal"));
            }
        }catch(SQLException exception){
            throw new LoginException("Error de conexion. Favor de verificar su conexion e intentelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }
        
        return director;
    }
    
    @Override
    public Professor logInProfessor(String emailAddress, String password) throws LoginException{
        PreparedStatement statement;
        Professor professor = new Professor();
        String query = "SELECT u.IdUsuario, u.nombre, u.apellidoPaterno, u.apellidoMaterno, u.correo, p.NumPersonal FROM Usuarios u"
                + " INNER JOIN Profesores p ON u.IdUsuario = p.IdUsuario WHERE u.correo = ? AND u.contraseña = SHA2(?, 256)";
        
        try{
            statement = dataBaseManager.getConnection().prepareStatement(query);
            statement.setString(1, emailAddress + "@uv.mx");
            statement.setString(2, password);
            
            ResultSet resultSet = statement.executeQuery();
            
            if(resultSet.next()){
                professor.setName(resultSet.getString("u.nombre"));
                professor.setFirstSurname(resultSet.getString("u.apellidoPaterno"));
                professor.setSecondSurname(resultSet.getString("u.apellidoMaterno"));
                professor.setEmailAddress(resultSet.getString("u.correo"));
                professor.setUserId(resultSet.getInt("u.IdUsuario"));
                professor.setStaffNumber(resultSet.getInt("p.NumPersonal"));
            }
        }catch(SQLException exception){
            throw new LoginException("Error de conexión. Favor de verificar su conexión e inténtelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }
        
        return professor;
    }
    
    
    @Override
    public Student logInStudent(String matricle, String password) throws LoginException{
        PreparedStatement statement;
        String query = "SELECT u.IdUsuario, u.nombre, u.apellidoPaterno, u.apellidoMaterno, u.correo, e.Matrícula FROM Usuarios u"
                + " INNER JOIN Estudiantes e ON u.IdUsuario = e.IdUsuario WHERE e.Matrícula = ? AND contraseña = SHA2(?, 256)";
        Student student = new Student();
        
        try{
            statement = dataBaseManager.getConnection().prepareStatement(query);
            statement.setString(1, matricle);
            statement.setString(2, password);
            
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                student.setUserId(resultSet.getInt("u.IdUsuario"));
                student.setName(resultSet.getString("u.nombre"));
                student.setFirstSurname(resultSet.getString("u.apellidoPaterno"));
                student.setSecondSurname(resultSet.getString("u.apellidoMaterno"));
                student.setEmailAddress(resultSet.getString("u.correo"));
                student.setMatricle(resultSet.getString("e.Matrícula"));
            }
            
            resultSet.close();
        }catch(SQLException exception){
            throw new LoginException("Error de conexión. Favor de verificar su conexión e inténtelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }
        
        return student;
    }
}