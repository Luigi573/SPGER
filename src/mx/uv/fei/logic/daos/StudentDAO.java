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
    public void modifyStudentDataFromDatabase(Student newStudentData, ArrayList<String> originalStudentData) {
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
            preparedStatementForUpdateUserData.setString(7, originalStudentData.get(0));
            preparedStatementForUpdateUserData.setString(8, originalStudentData.get(1));
            preparedStatementForUpdateUserData.setString(9, originalStudentData.get(2));
            preparedStatementForUpdateUserData.setString(10, originalStudentData.get(3));
            preparedStatementForUpdateUserData.setString(11, originalStudentData.get(4));
            preparedStatementForUpdateUserData.setString(12, originalStudentData.get(5));
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
    public Student getStudentFromDatabase(String studentName) {
        Student student = new Student();

        try {
            DataBaseManager dataBaseManager = new DataBaseManager();
            String query = "SELECT * FROM Usuarios U INNER JOIN Estudiantes E " + 
                           "ON U.IdUsuario = E.IdUsuario WHERE U.Nombre = ?";
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

    public boolean theStudentIsAlreadyRegisted(Student student) {
        try {
            DataBaseManager dataBaseManager = new DataBaseManager();
            Statement statement = dataBaseManager.getConnection().createStatement();
            String query = "SELECT U.nombre, U.apellidoPaterno, U.apellidoMaterno, U.correo, " +
                           "U.correoAlterno, U.númeroTeléfono, E.Matrícula FROM Usuarios U " + 
                           "INNER JOIN Estudiantes E ON U.IdUsuario = E.IdUsuario";
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()) {
                if( (resultSet.getString("nombre").equals(student.getName()) &&
                   resultSet.getString("apellidoPaterno").equals(student.getFirstSurname()) &&
                   resultSet.getString("apellidoMaterno").equals(student.getSecondSurname()) &&
                   resultSet.getString("correo").equals(student.getEmailAddress()) &&
                   resultSet.getString("correoAlterno").equals(student.getAlternateEmail()) &&
                   resultSet.getString("númeroTeléfono").equals(student.getPhoneNumber()) ) ||
                   resultSet.getString("Matrícula").equals(student.getMatricle())) {
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

    private int getIdUserFromStudent(ArrayList<String> originalStudentData){
        int idUser = 0;

        try {
            DataBaseManager dataBaseManager = new DataBaseManager();
            String query = "SELECT U.IdUsuario FROM Usuarios U INNER JOIN Estudiantes E ON " + 
                           "U.IdUsuario = E.IdUsuario WHERE U.nombre = ? && " +
                           "U.apellidoPaterno = ? && U.apellidoMaterno = ? && U.correo = ? && " +
                           "U.correoAlterno = ? && U.númeroTeléfono = ? && E.Matrícula = ?";
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setString(1, originalStudentData.get(0));
            preparedStatement.setString(2, originalStudentData.get(1));
            preparedStatement.setString(3, originalStudentData.get(2));
            preparedStatement.setString(4, originalStudentData.get(3));
            preparedStatement.setString(5, originalStudentData.get(4));
            preparedStatement.setString(6, originalStudentData.get(5));
            preparedStatement.setString(7, originalStudentData.get(6));

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
