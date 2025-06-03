package mx.uv.fei.logic.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        String query = "SELECT u.userId, u.nombre, u.firstSurname, u.secondSurname, u.emailAddress, p.staffNumber FROM Users u "
                + " INNER JOIN Professors p ON u.userId = p.userId INNER JOIN JefesCarrera jc ON p.staffNumber = jc.staffNumber "
                + " WHERE u.emailAddress = ? AND u.password = SHA2(?, 256)";
        DegreeBoss degreeBoss = new DegreeBoss();
        
        try{
            statement = dataBaseManager.getConnection().prepareStatement(query);
            
            statement.setString(1, emailAddress + "@uv.mx");
            statement.setString(2, password);
            
            ResultSet resultSet = statement.executeQuery();
            
            if(resultSet.next()){
                degreeBoss.setName(resultSet.getString("u.nombre"));
                degreeBoss.setFirstSurname(resultSet.getString("u.firstSurname"));
                degreeBoss.setSecondSurname(resultSet.getString("u.secondSurname"));
                degreeBoss.setEmailAddress(resultSet.getString("u.emailAddress"));
                degreeBoss.setUserId(resultSet.getInt("u.userId"));
                degreeBoss.setStaffNumber(resultSet.getInt("p.staffNumber"));
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
        String query = "SELECT u.userId, u.nombre, u.firstSurname, u.secondSurname, u.emailAddress, p.staffNumber FROM Users u "
                + " INNER JOIN Professors p ON u.userId = p.userId INNER JOIN AcademicBodyHeads rca ON p.staffNumber = rca.staffNumber "
                + " WHERE u.emailAddress = ? AND u.password = SHA2(?, 256)";
        AcademicBodyHead academicBodyHead = new AcademicBodyHead();
        
        try{
            statement = dataBaseManager.getConnection().prepareStatement(query);
            statement.setString(1, emailAddress + "@uv.mx");
            statement.setString(2, password);
            
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                academicBodyHead.setUserId(resultSet.getInt("u.userId"));
                academicBodyHead.setName(resultSet.getString("u.nombre"));
                academicBodyHead.setFirstSurname(resultSet.getString("u.firstSurname"));
                academicBodyHead.setSecondSurname(resultSet.getString("u.secondSurname"));
                academicBodyHead.setEmailAddress(resultSet.getString("u.emailAddress"));
                academicBodyHead.setStaffNumber(resultSet.getInt("p.staffNumber"));
            }
        }catch(SQLException exception){
            throw new LoginException("Error de conexion. Verifique su conectividad a  la base de datos e inténtelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }
        
        return academicBodyHead;
    }
    
    @Override
    public Director logInDirector(String emailAddress, String password) throws LoginException {
        Director director = new Director();
        PreparedStatement statement;
        String query = "SELECT u.userId, u.nombre, u.firstSurname, u.secondSurname, u.emailAddress, p.staffNumber FROM Users u "
                + " INNER JOIN Professors p ON u.userId = p.userId INNER JOIN Directores d ON p.staffNumber = d.staffNumber "
                + " WHERE u.emailAddress = ? AND u.password = SHA2(?, 256)";
        
        try{
            statement = dataBaseManager.getConnection().prepareStatement(query);
            
            statement.setString(1, emailAddress + "@uv.mx");
            statement.setString(2, password);
            
            ResultSet resultSet = statement.executeQuery();
            
            if(resultSet.next()){
                director.setName(resultSet.getString("u.nombre"));
                director.setFirstSurname(resultSet.getString("u.firstSurname"));
                director.setSecondSurname(resultSet.getString("u.secondSurname"));
                director.setEmailAddress(resultSet.getString("u.emailAddress"));
                director.setUserId(resultSet.getInt("u.userId"));
                director.setStaffNumber(resultSet.getInt("p.staffNumber"));
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
        String query = "SELECT u.userId, u.nombre, u.firstSurname, u.secondSurname, u.emailAddress, p.staffNumber FROM Users u"
                + " INNER JOIN Professors p ON u.userId = p.userId WHERE u.emailAddress = ? AND u.password = SHA2(?, 256)";
        
        try{
            statement = dataBaseManager.getConnection().prepareStatement(query);
            statement.setString(1, emailAddress + "@uv.mx");
            statement.setString(2, password);
            
            ResultSet resultSet = statement.executeQuery();
            
            if(resultSet.next()){
                professor.setName(resultSet.getString("u.nombre"));
                professor.setFirstSurname(resultSet.getString("u.firstSurname"));
                professor.setSecondSurname(resultSet.getString("u.secondSurname"));
                professor.setEmailAddress(resultSet.getString("u.emailAddress"));
                professor.setUserId(resultSet.getInt("u.userId"));
                professor.setStaffNumber(resultSet.getInt("p.staffNumber"));
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
        String query = "SELECT u.userId, u.nombre, u.firstSurname, u.secondSurname, u.emailAddress, e.Matrícula FROM Users u"
                + " INNER JOIN Estudiantes e ON u.userId = e.userId WHERE e.Matrícula = ? AND password = SHA2(?, 256)";
        Student student = new Student();
        
        try{
            statement = dataBaseManager.getConnection().prepareStatement(query);
            statement.setString(1, matricle);
            statement.setString(2, password);
            
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                student.setUserId(resultSet.getInt("u.userId"));
                student.setName(resultSet.getString("u.nombre"));
                student.setFirstSurname(resultSet.getString("u.firstSurname"));
                student.setSecondSurname(resultSet.getString("u.secondSurname"));
                student.setEmailAddress(resultSet.getString("u.emailAddress"));
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