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
                "SELECT IdUsuario FROM Usuarios WHERE nombre = ? && " +
                "apellidoPaterno = ? && apellidoMaterno = ? && correo = ? && " +
                "correoAlterno = ? && númeroTeléfono = ?";
            PreparedStatement preparedStatementForAssignUserIdToStudent = 
                dataBaseManager.getConnection().prepareStatement(queryForAssignUserIdToStudent);
            preparedStatementForAssignUserIdToStudent.setString(1, student.getName());
            preparedStatementForAssignUserIdToStudent.setString(2, student.getFirstSurname());
            preparedStatementForAssignUserIdToStudent.setString(3, student.getSecondSurname());
            preparedStatementForAssignUserIdToStudent.setString(4, student.getEmailAddress());
            preparedStatementForAssignUserIdToStudent.setString(5, student.getAlternateEmail());
            preparedStatementForAssignUserIdToStudent.setString(6, student.getPhoneNumber());
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
    public void modifyStudentDataFromDatabase(Student newStudentData, Student originalStudentData) {
        try {
            newStudentData.setIdUser(this.getIdUserFromStudent(originalStudentData));
            DataBaseManager dataBaseManager = new DataBaseManager();
            String queryForUpdateUserData = "UPDATE Usuarios SET nombre = ?, " + 
                           "apellidoPaterno = ?, apellidoMaterno = ?, correo = ?, " + 
                           "correoAlterno = ?, númeroTeléfono = ? " +
                           "WHERE nombre = ? && apellidoPaterno = ? && apellidoMaterno = ? && " + 
                           "correo = ? && correoAlterno = ? && númeroTeléfono = ?";
            PreparedStatement preparedStatementForUpdateUserData = 
                dataBaseManager.getConnection().prepareStatement(queryForUpdateUserData);
            preparedStatementForUpdateUserData.setString(1, newStudentData.getName());
            preparedStatementForUpdateUserData.setString(2, newStudentData.getFirstSurname());
            preparedStatementForUpdateUserData.setString(3, newStudentData.getSecondSurname());
            preparedStatementForUpdateUserData.setString(4, newStudentData.getEmailAddress());
            preparedStatementForUpdateUserData.setString(5, newStudentData.getAlternateEmail());
            preparedStatementForUpdateUserData.setString(6, newStudentData.getPhoneNumber());
            preparedStatementForUpdateUserData.setString(7, originalStudentData.getName());
            preparedStatementForUpdateUserData.setString(8, originalStudentData.getFirstSurname());
            preparedStatementForUpdateUserData.setString(9, originalStudentData.getSecondSurname());
            preparedStatementForUpdateUserData.setString(10, originalStudentData.getEmailAddress());
            preparedStatementForUpdateUserData.setString(11, originalStudentData.getAlternateEmail());
            preparedStatementForUpdateUserData.setString(12, originalStudentData.getPhoneNumber());
            preparedStatementForUpdateUserData.executeUpdate();

            String queryForUpdateStudentData = "UPDATE Estudiantes SET Matrícula = ? " + 
                           "WHERE IdUsuario = ?";
            
            PreparedStatement preparedStatementForUpdateStudentData = 
                dataBaseManager.getConnection().prepareStatement(queryForUpdateStudentData);
            preparedStatementForUpdateStudentData.setString(1, newStudentData.getMatricle());
            preparedStatementForUpdateStudentData.setInt(2, newStudentData.getIdUser());
            preparedStatementForUpdateStudentData.executeUpdate();
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
                student.setPhoneNumber(resultSet.getString("númeroTeléfono"));
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
            String query = "SELECT * FROM Usuarios U INNER JOIN Estudiantes E " + 
                           "ON U.IdUsuario = E.IdUsuario WHERE U.Nombre LIKE ?";
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
                student.setPhoneNumber(resultSet.getString("númeroTeléfono"));
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
    public Student getStudentFromDatabase(String matricle) {
        Student student = new Student();

        try {
            DataBaseManager dataBaseManager = new DataBaseManager();
            String query = "SELECT * FROM Usuarios U INNER JOIN Estudiantes E " + 
                           "ON U.IdUsuario = E.IdUsuario WHERE E.Matrícula = ?";
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setString(1, matricle);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                student.setName(resultSet.getString("nombre"));
                student.setFirstSurname(resultSet.getString("apellidoPaterno"));
                student.setSecondSurname(resultSet.getString("apellidoMaterno"));
                student.setEmailAddress(resultSet.getString("correo"));
                student.setPassword(resultSet.getString("contraseña"));
                student.setAlternateEmail(resultSet.getString("correoAlterno"));
                student.setPhoneNumber(resultSet.getString("númeroTeléfono"));
                student.setMatricle(resultSet.getString("Matrícula"));
            }
            
            resultSet.close();
            dataBaseManager.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return student;
    }

    public boolean theStudentIsAlreadyRegisted(String matricle) {
        try {
            DataBaseManager dataBaseManager = new DataBaseManager();
            Statement statement = dataBaseManager.getConnection().createStatement();
            String query = "SELECT Matrícula FROM Estudiantes";
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()) {
                if(resultSet.getString("Matrícula").equals(matricle)) {
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

    private int getIdUserFromStudent(Student originalStudentData){
        int idUser = 0;

        try {
            DataBaseManager dataBaseManager = new DataBaseManager();
            String query = "SELECT U.IdUsuario FROM Usuarios U INNER JOIN Estudiantes E ON " + 
                           "U.IdUsuario = E.IdUsuario WHERE U.nombre = ? && " +
                           "U.apellidoPaterno = ? && U.apellidoMaterno = ? && U.correo = ? && " +
                           "U.correoAlterno = ? && U.númeroTeléfono = ? && E.Matrícula = ?";
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setString(1, originalStudentData.getName());
            preparedStatement.setString(2, originalStudentData.getFirstSurname());
            preparedStatement.setString(3, originalStudentData.getSecondSurname());
            preparedStatement.setString(4, originalStudentData.getEmailAddress());
            preparedStatement.setString(5, originalStudentData.getAlternateEmail());
            preparedStatement.setString(6, originalStudentData.getPhoneNumber());
            preparedStatement.setString(7, originalStudentData.getMatricle());

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                idUser = resultSet.getInt("IdUsuario");
            }
            
            resultSet.close();
            dataBaseManager.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return idUser;
    }
    
}
