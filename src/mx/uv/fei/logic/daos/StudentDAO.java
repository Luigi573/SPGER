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
            String tablesToConsult = 
            "nombre, apellidoPaterno, apellidoMaterno, correo, contraseña, correoAlterno";
            String wholeQuery = "INSERT INTO Anteproyectos (" + tablesToConsult + ") VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(wholeQuery);
            preparedStatement.setString(1, student.getName());
            preparedStatement.setString(2, student.getFirstSurname());
            preparedStatement.setString(3, student.getSecondSurname());
            preparedStatement.setString(4, student.getEmailAddress());
            preparedStatement.setString(5, student.getPassword());
            preparedStatement.setString(6, student.getAlternateEmail());
            preparedStatement.setString(7, student.getStartDate());
            preparedStatement.setString(8, student.getFinishDate());
            preparedStatement.setString(9, student.getWaitedResults());
            preparedStatement.setString(10, student.getNote());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            dataBaseManager.getConnection().close();

        } catch (SQLException e) {
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
    public Student getStudentFromDatabase(String studentName){
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
    
}
