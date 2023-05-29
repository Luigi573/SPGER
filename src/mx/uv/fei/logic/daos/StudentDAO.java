package mx.uv.fei.logic.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;

import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.logic.daosinterfaces.IStudentDAO;
import mx.uv.fei.logic.domain.Student;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.exceptions.DataInsertionException;

public class StudentDAO implements IStudentDAO{
    private final DataBaseManager dataBaseManager;
    
    public StudentDAO(){
        dataBaseManager = new DataBaseManager();
    }

    @Override
    public void addStudentToDatabase(Student student) throws DataInsertionException{
        try{
            String queryToInsertUserData = "INSERT INTO Usuarios (nombre, apellidoPaterno, apellidoMaterno, correo, " +
                "correoAlterno, númeroTeléfono, estado) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatementToInsertUserData = 
                dataBaseManager.getConnection().prepareStatement(queryToInsertUserData);
            preparedStatementToInsertUserData.setString(1, student.getName());
            preparedStatementToInsertUserData.setString(2, student.getFirstSurname());
            preparedStatementToInsertUserData.setString(3, student.getSecondSurname());
            preparedStatementToInsertUserData.setString(4, student.getEmailAddress());
            preparedStatementToInsertUserData.setString(5, student.getAlternateEmail());
            preparedStatementToInsertUserData.setString(6, student.getPhoneNumber());
            preparedStatementToInsertUserData.setString(7, student.getStatus());
            preparedStatementToInsertUserData.executeUpdate();

            String queryForAssignUserIdToStudent =
                "SELECT IdUsuario FROM Usuarios WHERE nombre = ? && " +
                "apellidoPaterno = ? && apellidoMaterno = ? && correo = ? && " +
                "correoAlterno = ? && númeroTeléfono = ? && estado = ?";
            PreparedStatement preparedStatementForAssignUserIdToStudent = 
                dataBaseManager.getConnection().prepareStatement(queryForAssignUserIdToStudent);
            preparedStatementForAssignUserIdToStudent.setString(1, student.getName());
            preparedStatementForAssignUserIdToStudent.setString(2, student.getFirstSurname());
            preparedStatementForAssignUserIdToStudent.setString(3, student.getSecondSurname());
            preparedStatementForAssignUserIdToStudent.setString(4, student.getEmailAddress());
            preparedStatementForAssignUserIdToStudent.setString(5, student.getAlternateEmail());
            preparedStatementForAssignUserIdToStudent.setString(6, student.getPhoneNumber());
            preparedStatementForAssignUserIdToStudent.setString(7, student.getStatus());
            ResultSet resultSet = preparedStatementForAssignUserIdToStudent.executeQuery();
            if(resultSet.next()){
                student.setUserId(resultSet.getInt("IdUsuario"));
            }

            String wholeQueryToInsertStudentData = 
                "INSERT INTO Estudiantes (Matrícula, IdUsuario) VALUES (?, ?)";
            PreparedStatement preparedStatementToInsertStudentData = 
                dataBaseManager.getConnection().prepareStatement(wholeQueryToInsertStudentData);
            preparedStatementToInsertStudentData.setString(1, student.getMatricule());
            preparedStatementToInsertStudentData.setInt(2, student.getUserId());
            preparedStatementToInsertStudentData.executeUpdate();

            preparedStatementToInsertStudentData.close();
            dataBaseManager.getConnection().close();

        }catch(SQLIntegrityConstraintViolationException e){
            deleteStudentFromUsersTable(student);
            throw new DataInsertionException("Error al agregar estudiante. Verifique su conexion e intentelo de nuevo");
        }catch(SQLException e){
            throw new DataInsertionException("Error al agregar estudiante. Verifique su conexion e intentelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }
    }
    
    @Override
    public void modifyStudentDataFromDatabase(Student newStudentData, Student originalStudentData) throws DataInsertionException{
        try{
            newStudentData.setUserId(this.getUserIdFromStudent(originalStudentData));
            String queryForUpdateUserData = "UPDATE Usuarios SET nombre = ?, " + 
                           "apellidoPaterno = ?, apellidoMaterno = ?, correo = ?, " + 
                           "correoAlterno = ?, númeroTeléfono = ?, estado = ? " +
                           "WHERE nombre = ? && apellidoPaterno = ? && apellidoMaterno = ? && " + 
                           "correo = ? && correoAlterno = ? && númeroTeléfono = ? && estado = ?";
            PreparedStatement preparedStatementForUpdateUserData = 
                dataBaseManager.getConnection().prepareStatement(queryForUpdateUserData);
            preparedStatementForUpdateUserData.setString(1, newStudentData.getName());
            preparedStatementForUpdateUserData.setString(2, newStudentData.getFirstSurname());
            preparedStatementForUpdateUserData.setString(3, newStudentData.getSecondSurname());
            preparedStatementForUpdateUserData.setString(4, newStudentData.getEmailAddress());
            preparedStatementForUpdateUserData.setString(5, newStudentData.getAlternateEmail());
            preparedStatementForUpdateUserData.setString(6, newStudentData.getPhoneNumber());
            preparedStatementForUpdateUserData.setString(7, newStudentData.getStatus());
            preparedStatementForUpdateUserData.setString(8, originalStudentData.getName());
            preparedStatementForUpdateUserData.setString(9, originalStudentData.getFirstSurname());
            preparedStatementForUpdateUserData.setString(10, originalStudentData.getSecondSurname());
            preparedStatementForUpdateUserData.setString(11, originalStudentData.getEmailAddress());
            preparedStatementForUpdateUserData.setString(12, originalStudentData.getAlternateEmail());
            preparedStatementForUpdateUserData.setString(13, originalStudentData.getPhoneNumber());
            preparedStatementForUpdateUserData.setString(14, originalStudentData.getStatus());
            preparedStatementForUpdateUserData.executeUpdate();

            String queryForUpdateStudentData = "UPDATE Estudiantes SET Matrícula = ? " + 
                           "WHERE IdUsuario = ?";
            
            PreparedStatement preparedStatementForUpdateStudentData = 
                dataBaseManager.getConnection().prepareStatement(queryForUpdateStudentData);
            preparedStatementForUpdateStudentData.setString(1, newStudentData.getMatricule());
            preparedStatementForUpdateStudentData.setInt(2, newStudentData.getUserId());
            preparedStatementForUpdateStudentData.executeUpdate();
        }catch(SQLException e){
            throw new DataInsertionException("Error al agregar estudiante. Verifique su conexion e intentelo de nuevo");
        }catch(DataRetrievalException e){
            throw new DataInsertionException("Error al agregar estudiante. Verifique su conexion e intentelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }
    }

    @Override
    public ArrayList<Student> getStudentsFromDatabase() throws DataRetrievalException{
        ArrayList<Student> students = new ArrayList<>();
        
        try{
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
                student.setStatus(resultSet.getString("estado"));
                student.setMatricle(resultSet.getString("Matrícula"));
                students.add(student);
            }
            resultSet.close();
            dataBaseManager.getConnection().close();
        }catch(SQLException e){
            throw new DataRetrievalException("Error al recuperar la información. Verifique su conexión e intentelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }

        return students;
    }

    @Override
    public ArrayList<Student> getStudentList() throws DataRetrievalException{
        ArrayList<Student> studentList = new ArrayList<>();
        PreparedStatement statement;
        String query = "SELECT e.Matrícula, u.nombre, u.apellidoPaterno, u.apellidoMaterno FROM Estudiantes e "
                + "INNER JOIN Usuarios u ON e.IdUsuario = u.IdUsuario";
        
        try{
            statement = dataBaseManager.getConnection().prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                Student student = new Student();
                
                student.setMatricle(resultSet.getString("e.Matrícula"));
                student.setName(resultSet.getString("u.nombre"));
                student.setFirstSurname(resultSet.getString("u.apellidoPaterno"));
                student.setSecondSurname(resultSet.getString("u.apellidoMaterno"));
                
                studentList.add(student);
            }
        }catch(SQLException exception){
            throw new DataRetrievalException("Error al recuperar estudiantes. VErifique su conexión e inténtelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }
        
        return studentList;
    }

    @Override
    public ArrayList<Student> getAvailableStudentsFromDatabase() throws DataRetrievalException{
        ArrayList<Student> students = new ArrayList<>();
        
        try{
            DataBaseManager dataBaseManager = new DataBaseManager();
            Statement statement = dataBaseManager.getConnection().createStatement();
            String query = "SELECT * FROM Usuarios U INNER JOIN Estudiantes E " + 
                           "ON U.IdUsuario = E.IdUsuario WHERE U.estado = 'Disponible'";
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
                student.setStatus(resultSet.getString("estado"));
                student.setMatricle(resultSet.getString("Matrícula"));
                students.add(student);
            }
            resultSet.close();
            dataBaseManager.getConnection().close();
        }catch(SQLException e){
            throw new DataRetrievalException("Error al recuperar la información. Verifique su conexión e intentelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }

        return students;
    }

    @Override
    public ArrayList<Student> getSpecifiedAvailableStudentsFromDatabase(String studentName) throws DataRetrievalException{
        ArrayList<Student> students = new ArrayList<>();
        
        try{
            DataBaseManager dataBaseManager = new DataBaseManager();
            String query = "SELECT * FROM Usuarios U INNER JOIN Estudiantes E " + 
                           "ON U.IdUsuario = E.IdUsuario WHERE U.Nombre LIKE ? && U.estado = 'Disponible'";
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
                student.setStatus(resultSet.getString("estado"));
                student.setMatricle(resultSet.getString("Matrícula"));
                students.add(student);
            }
            resultSet.close();
            dataBaseManager.getConnection().close();
        }catch(SQLException e){
            throw new DataRetrievalException("Error al recuperar la información. Verifique su conexión e intentelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }

        return students;
    }

    @Override
    public ArrayList<Student> getActiveStudentsFromDatabase() throws DataRetrievalException{
        ArrayList<Student> students = new ArrayList<>();
        
        try{
            DataBaseManager dataBaseManager = new DataBaseManager();
            Statement statement = dataBaseManager.getConnection().createStatement();
            String query = "SELECT * FROM Usuarios U INNER JOIN Estudiantes E " + 
                           "ON U.IdUsuario = E.IdUsuario WHERE U.estado = 'Activo'";
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
                student.setStatus(resultSet.getString("estado"));
                student.setMatricle(resultSet.getString("Matrícula"));
                students.add(student);
            }
            resultSet.close();
            dataBaseManager.getConnection().close();
        }catch(SQLException e){
            throw new DataRetrievalException("Error al recuperar la información. Verifique su conexión e intentelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }

        return students;
    }

    @Override
    public ArrayList<Student> getSpecifiedActiveStudentsFromDatabase(String studentName) throws DataRetrievalException{
        ArrayList<Student> students = new ArrayList<>();
        
        try{
            DataBaseManager dataBaseManager = new DataBaseManager();
            String query = "SELECT * FROM Usuarios U INNER JOIN Estudiantes E " + 
                           "ON U.IdUsuario = E.IdUsuario WHERE U.Nombre LIKE ? && U.estado = 'Activo'";
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
                student.setStatus(resultSet.getString("estado"));
                student.setMatricle(resultSet.getString("Matrícula"));
                students.add(student);
            }
            resultSet.close();
            dataBaseManager.getConnection().close();
        }catch(SQLException e){
            throw new DataRetrievalException("Error al recuperar la información. Verifique su conexión e intentelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }

        return students;
    }

    @Override
    public ArrayList<Student> getSpecifiedStudentsFromDatabase(String studentName) throws DataRetrievalException{
        ArrayList<Student> students = new ArrayList<>();
        PreparedStatement statement;
        String query = "SELECT * FROM Usuarios U INNER JOIN Estudiantes E " + 
                           "ON U.IdUsuario = E.IdUsuario WHERE U.Nombre LIKE ?";
        
        try{
            statement = dataBaseManager.getConnection().prepareStatement(query);
            statement.setString(1, studentName + '%');
            
            ResultSet resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                Student student = new Student();
                student.setName(resultSet.getString("nombre"));
                student.setFirstSurname(resultSet.getString("apellidoPaterno"));
                student.setSecondSurname(resultSet.getString("apellidoMaterno"));
                student.setEmailAddress(resultSet.getString("correo"));
                student.setPassword(resultSet.getString("contraseña"));
                student.setAlternateEmail(resultSet.getString("correoAlterno"));
                student.setPhoneNumber(resultSet.getString("númeroTeléfono"));
                student.setStatus(resultSet.getString("estado"));
                student.setMatricle(resultSet.getString("Matrícula"));
                students.add(student);
            }
            resultSet.close();
            dataBaseManager.getConnection().close();
        }catch(SQLException exception){
            exception.printStackTrace();
            throw new DataRetrievalException("Error al recuperar la información. Verifique su conexión e intentelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }

        return students;
    }

    @Override
    public Student getStudentFromDatabase(String matricle) throws DataRetrievalException{
        Student student = new Student();
        String query = "SELECT * FROM Usuarios U INNER JOIN Estudiantes E " + 
                           "ON U.IdUsuario = E.IdUsuario WHERE E.Matrícula = ?";

        try{
            
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
                student.setStatus(resultSet.getString("estado"));
                student.setMatricle(resultSet.getString("Matrícula"));
            }
            
            resultSet.close();
            dataBaseManager.getConnection().close();
        }catch(SQLException e){
            throw new DataRetrievalException("Error al recuperar la información. Verifique su conexión e intentelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }

        return student;
    }

    public boolean theStudentIsAlreadyRegisted(Student student) throws DataRetrievalException{
        String query = "SELECT * FROM Usuarios U INNER JOIN Estudiantes E ON U.IdUsuario = E.IdUsuario";
        
        try{
            Statement statement = dataBaseManager.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()) {
                if(resultSet.getString("nombre").equals(student.getName()) &&
                   resultSet.getString("apellidoPaterno").equals(student.getFirstSurname()) &&
                   resultSet.getString("apellidoMaterno").equals(student.getSecondSurname()) &&
                   resultSet.getString("correo").equals(student.getEmailAddress()) &&
                   resultSet.getString("correoAlterno").equals(student.getAlternateEmail()) &&
                   resultSet.getString("númeroTeléfono").equals(student.getPhoneNumber()) &&
                   resultSet.getString("estado").equals(student.getStatus()) &&
                   resultSet.getString("Matrícula").equals(student.getMatricule())) {
                    
                    resultSet.close();
                    dataBaseManager.getConnection().close();
                    return true;
                }
            }
            resultSet.close();
            dataBaseManager.getConnection().close();
        }catch(SQLException e){
            throw new DataRetrievalException("Error al recuperar la información. Verifique su conexión e intentelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }

        return false;
    }

    private int getUserIdFromStudent(Student student) throws DataRetrievalException{
        int userId = 0;
        String query = "SELECT U.IdUsuario FROM Usuarios U INNER JOIN Estudiantes E ON " + 
                           "U.IdUsuario = E.IdUsuario WHERE U.nombre = ? && " +
                           "U.apellidoPaterno = ? && U.apellidoMaterno = ? && U.correo = ? && " +
                           "U.correoAlterno = ? && U.númeroTeléfono = ? && U.estado = ? && E.Matrícula = ?";

        try{
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setString(1, student.getName());
            preparedStatement.setString(2, student.getFirstSurname());
            preparedStatement.setString(3, student.getSecondSurname());
            preparedStatement.setString(4, student.getEmailAddress());
            preparedStatement.setString(5, student.getAlternateEmail());
            preparedStatement.setString(6, student.getPhoneNumber());
            preparedStatement.setString(7, student.getStatus());
            preparedStatement.setString(8, student.getMatricule());

            ResultSet resultSet = preparedStatement.executeQuery();
            
            if(resultSet.next()){
                userId = resultSet.getInt("IdUsuario");
            }
            
            resultSet.close();
            dataBaseManager.getConnection().close();
        }catch(SQLException e){
            throw new DataRetrievalException("Error al recuperar la información. Verifique su conexión e intentelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }

        return userId;
    }
    private void deleteStudentFromUsersTable(Student student) throws DataInsertionException{
        String queryToInsertUserData = "DELETE FROM Usuarios WHERE nombre = ? && apellidoPaterno = ? && apellidoMaterno = ? && " +
            "correo = ? && correoAlterno = ? && númeroTeléfono = ? && estado = ?";
        try{
            PreparedStatement preparedStatementToInsertUserData = 
            dataBaseManager.getConnection().prepareStatement(queryToInsertUserData);
            preparedStatementToInsertUserData.setString(1, student.getName());
            preparedStatementToInsertUserData.setString(2, student.getFirstSurname());
            preparedStatementToInsertUserData.setString(3, student.getSecondSurname());
            preparedStatementToInsertUserData.setString(4, student.getEmailAddress());
            preparedStatementToInsertUserData.setString(5, student.getAlternateEmail());
            preparedStatementToInsertUserData.setString(6, student.getPhoneNumber());
            preparedStatementToInsertUserData.setString(7, student.getStatus());
            preparedStatementToInsertUserData.executeUpdate();
        }catch(SQLException e){
            throw new DataInsertionException("Error al eliminar estudiante de la tabla usuarios");
        }
    }
}