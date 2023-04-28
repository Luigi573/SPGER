package mx.uv.fei.logic.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.logic.daosinterfaces.IStudentDAO;
import mx.uv.fei.logic.domain.Student;

public class StudentDAO implements IStudentDAO{

    @Override
    public void addStudentToDatabase(Student student) {
        try {
            DataBaseManager dataBaseManager = new DataBaseManager();
            String userTablesToConsult = 
                "nombre, apellidoPaterno, apellidoMaterno, correo, correoAlterno, númeroTeléfono";
            String wholeQueryToInsertUserData = "INSERT INTO Usuarios (" + userTablesToConsult + ") VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatementToInsertUserData = 
                dataBaseManager.getConnection().prepareStatement(wholeQueryToInsertUserData);
            preparedStatementToInsertUserData.setString(1, student.getName());
            preparedStatementToInsertUserData.setString(2, student.getFirstSurname());
            preparedStatementToInsertUserData.setString(3, student.getSecondSurname());
            preparedStatementToInsertUserData.setString(4, student.getEmailAddress());
            preparedStatementToInsertUserData.setString(5, student.getAlternateEmail());
            preparedStatementToInsertUserData.setString(6, student.getPhoneNumber());
            preparedStatementToInsertUserData.executeUpdate();

            String queryForAssignUserIdToStudent =
                "SELECT IdUsuario FROM Usuarios WHERE Nombre = ?";
            PreparedStatement preparedStatementForAssignUserIdToStudent = 
                dataBaseManager.getConnection().prepareStatement(queryForAssignUserIdToStudent);
            preparedStatementForAssignUserIdToStudent.setString(1, student.getName());
            ResultSet resultSet = preparedStatementForAssignUserIdToStudent.executeQuery();
            if(resultSet.next()){
                student.setIdUser(resultSet.getInt("IdUsuario"));
            }

            String studentTablesToConsult = "Matrícula, IdUsuario";
            String wholeQueryToInsertStudentData = 
                "INSERT INTO Estudiantes (" + studentTablesToConsult + ") VALUES (?, ?)";
            PreparedStatement preparedStatementToInsertStudentData = 
                dataBaseManager.getConnection().prepareStatement(wholeQueryToInsertStudentData);
            preparedStatementToInsertStudentData.setString(1, student.getMatricle());
            preparedStatementToInsertStudentData.setInt(2, student.getIdUser());
            preparedStatementToInsertStudentData.executeUpdate();

            preparedStatementToInsertStudentData.close();
            dataBaseManager.getConnection().close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void modifyStudentDataFromDatabase(Student student) {
        try {
            DataBaseManager dataBaseManager = new DataBaseManager();
            String query = "UPDATE Usuarios SET " + "nombre = ? " + 
                           "apellidoPaterno = ? " + "apellidoMaterno = ? " +
                           "correo = ? " + "correoAlterno = ? " +
                           "númeroTeléfono = ? " +
                           "WHERE nombre = ?";
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setString(0, student.getName());
            preparedStatement.setString(1, student.getFirstSurname());
            preparedStatement.setString(2, student.getSecondSurname());
            preparedStatement.setString(3, student.getEmailAddress());
            preparedStatement.setString(4, student.getAlternateEmail());
            preparedStatement.setString(5, student.getPhoneNumber());
            preparedStatement.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Student> getStudentsFromDatabase() {
        ArrayList<Student> students = new ArrayList<>();
        
        try {
            DataBaseManager dataBaseManager = new DataBaseManager();
            Statement statement = dataBaseManager.getConnection().createStatement();
            String query = "SELECT * FROM Usuarios U INNER JOIN Estudiantes E ON U.IdUsuario = E.IdUsuario";
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()) {
                Student student = new Student();
                student.setName(resultSet.getString("nombre"));
                student.setFirstSurname(resultSet.getString("apellidoPaterno"));
                student.setSecondSurname(resultSet.getString("apellidoMaterno"));
                student.setEmailAddress(resultSet.getString("correo"));
                student.setPassword(resultSet.getString("contraseña"));
                student.setAlternateEmail(resultSet.getString("correoAlterno"));
                student.setMatricle(resultSet.getString("Matrícula"));
                students.add(student);
            }
            resultSet.close();
            dataBaseManager.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return students;
    }

    @Override
    public ArrayList<Student> getSpecifiedStudentsFromDatabase(String studentName) {
        ArrayList<Student> students = new ArrayList<>();
        
        try {
            DataBaseManager dataBaseManager = new DataBaseManager();
            String query = "SELECT * FROM Usuarios U INNER JOIN Estudiantes E ON U.IdUsuario = E.IdUsuario WHERE U.Nombre LIKE ?";
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setString(1, studentName + '%');
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                Student student = new Student();
                student.setName(resultSet.getString("nombre"));
                student.setFirstSurname(resultSet.getString("apellidoPaterno"));
                student.setSecondSurname(resultSet.getString("apellidoMaterno"));
                student.setEmailAddress(resultSet.getString("correo"));
                student.setPassword(resultSet.getString("contraseña"));
                student.setAlternateEmail(resultSet.getString("correoAlterno"));
                student.setMatricle(resultSet.getString("Matrícula"));
                students.add(student);
            }
            resultSet.close();
            dataBaseManager.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return students;
    }

    @Override
    public Student getStudentFromDatabase(String studentName) {
        Student student = new Student();

        try {
            DataBaseManager dataBaseManager = new DataBaseManager();
            String query = "SELECT * FROM Usuarios U INNER JOIN Estudiantes E ON U.IdUsuario = E.IdUsuario WHERE U.Nombre = ?";
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setString(1, studentName);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                student.setName(resultSet.getString("nombre"));
                student.setFirstSurname(resultSet.getString("apellidoPaterno"));
                student.setSecondSurname(resultSet.getString("apellidoMaterno"));
                student.setEmailAddress(resultSet.getString("correo"));
                student.setPassword(resultSet.getString("contraseña"));
                student.setAlternateEmail(resultSet.getString("correoAlterno"));
                student.setMatricle(resultSet.getString("Matrícula"));
            }
            
            resultSet.close();
            dataBaseManager.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return student;
    }

    public boolean theStudentIsAlreadyRegisted(Student student) {
        try {
            DataBaseManager dataBaseManager = new DataBaseManager();
            Statement statement = dataBaseManager.getConnection().createStatement();
            String query = "SELECT Matrícula FROM Estudiantes";
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()) {
                if(resultSet.getString("Matrícula").equals(student.getMatricle())) {
                    resultSet.close();
                    dataBaseManager.getConnection().close();
                    return true;
                }
            }
            resultSet.close();
            dataBaseManager.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    
}
