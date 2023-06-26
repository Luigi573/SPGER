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
import mx.uv.fei.logic.exceptions.DataInsertionException;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.exceptions.DuplicatedPrimaryKeyException;

public class StudentDAO implements IStudentDAO{
    private final DataBaseManager dataBaseManager;
    
    public StudentDAO(){
        dataBaseManager = new DataBaseManager();
    }

    @Override
    public int addStudent(Student student) throws DataInsertionException, DuplicatedPrimaryKeyException{
        int generatedId = 0;
        try{
            String queryToInsertStudentDataToUserColumns = "INSERT INTO Usuarios (nombre, apellidoPaterno, apellidoMaterno, correo, " +
                "correoAlterno, numeroTelefono, estado, contraseña) VALUES (?, ?, ?, ?, ?, ?, ?, SHA2(?, 256))";
            PreparedStatement preparedStatementToInsertStudentDataToUsersColumns = 
                dataBaseManager.getConnection().prepareStatement(queryToInsertStudentDataToUserColumns, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatementToInsertStudentDataToUsersColumns.setString(1, student.getName());
            preparedStatementToInsertStudentDataToUsersColumns.setString(2, student.getFirstSurname());
            preparedStatementToInsertStudentDataToUsersColumns.setString(3, student.getSecondSurname());
            preparedStatementToInsertStudentDataToUsersColumns.setString(4, student.getEmailAddress());
            preparedStatementToInsertStudentDataToUsersColumns.setString(5, student.getAlternateEmail());
            preparedStatementToInsertStudentDataToUsersColumns.setString(6, student.getPhoneNumber());
            preparedStatementToInsertStudentDataToUsersColumns.setString(7, student.getStatus());
            preparedStatementToInsertStudentDataToUsersColumns.setString(8, student.getPassword());
            preparedStatementToInsertStudentDataToUsersColumns.executeUpdate();
            ResultSet resultSet = preparedStatementToInsertStudentDataToUsersColumns.getGeneratedKeys();
            if(resultSet.next()){
                generatedId = resultSet.getInt(1);
                student.setUserId(generatedId);
            }

            String queryToInsertStudentDataToStudentColumns = 
                "INSERT INTO Estudiantes (Matrícula, IdUsuario) VALUES (?, ?)";
            PreparedStatement preparedStatementToInsertStudentDataToStudentColumns = 
                dataBaseManager.getConnection().prepareStatement(queryToInsertStudentDataToStudentColumns);
            preparedStatementToInsertStudentDataToStudentColumns.setString(1, student.getMatricle());
            preparedStatementToInsertStudentDataToStudentColumns.setInt(2, student.getUserId());
            preparedStatementToInsertStudentDataToStudentColumns.executeUpdate();

            preparedStatementToInsertStudentDataToStudentColumns.close();
            dataBaseManager.getConnection().close();

        }catch(SQLIntegrityConstraintViolationException e){
            deleteStudentFromUsersTable(student);
            throw new DuplicatedPrimaryKeyException("Estudiante ya registrado en el sistema");
        }catch(SQLException e){
            throw new DataInsertionException("Error al agregar estudiante. Inténtelo de nuevo más tarde");
        }finally{
            dataBaseManager.closeConnection();
        }

        return generatedId;
    }
    @Override
    public int modifyStudentData(Student student) throws DataInsertionException, DuplicatedPrimaryKeyException{
        int result = 0;
        try {
            String queryForUpdateUserData = "UPDATE Usuarios SET nombre = ?, " + 
                           "apellidoPaterno = ?, apellidoMaterno = ?, correo = ?, " + 
                           "correoAlterno = ?, numeroTelefono = ?, estado = ?" +
                           "WHERE IdUsuario = ?";
            PreparedStatement preparedStatementForUpdateUserData = 
                dataBaseManager.getConnection().prepareStatement(queryForUpdateUserData);
            preparedStatementForUpdateUserData.setString(1, student.getName());
            preparedStatementForUpdateUserData.setString(2, student.getFirstSurname());
            preparedStatementForUpdateUserData.setString(3, student.getSecondSurname());
            preparedStatementForUpdateUserData.setString(4, student.getEmailAddress());
            preparedStatementForUpdateUserData.setString(5, student.getAlternateEmail());
            preparedStatementForUpdateUserData.setString(6, student.getPhoneNumber());
            preparedStatementForUpdateUserData.setString(7, student.getStatus());
            preparedStatementForUpdateUserData.setInt(8, student.getUserId());
            result = preparedStatementForUpdateUserData.executeUpdate();

            String queryForUpdateStudentData = "UPDATE Estudiantes SET Matrícula = ? WHERE IdUsuario = ?";
            
            PreparedStatement preparedStatementForUpdateStudentData = 
                dataBaseManager.getConnection().prepareStatement(queryForUpdateStudentData);
            preparedStatementForUpdateStudentData.setString(1, student.getMatricle());
            preparedStatementForUpdateStudentData.setInt(2, student.getUserId());
            preparedStatementForUpdateStudentData.executeUpdate();
        }catch(SQLIntegrityConstraintViolationException e){
            throw new DuplicatedPrimaryKeyException("Estudiante ya registrado en el sistema");
        }catch(SQLException e){
            throw new DataInsertionException("Error al modificar estudiante. Inténtelo de nuevo más tarde");
        }finally{
            dataBaseManager.closeConnection();
        }

        return result;
    }
    @Override
    public ArrayList<Student> getStudents() throws DataRetrievalException{
        ArrayList<Student> students = new ArrayList<>();
        
        try {
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
                student.setPhoneNumber(resultSet.getString("numeroTelefono"));
                student.setStatus(resultSet.getString("estado"));
                student.setUserId(resultSet.getInt("IdUsuario"));
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
    public ArrayList<Student> getStudentList() throws DataRetrievalException {
        ArrayList<Student> studentList = new ArrayList<>();
        PreparedStatement statement;
        String query = "SELECT e.Matrícula, u.nombre, u.apellidoPaterno, u.apellidoMaterno FROM Estudiantes e INNER JOIN Usuarios u ON e.IdUsuario = u.IdUsuario";
        
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
            throw new DataRetrievalException("Error al recuperar estudiantes. Verifique su conexión e inténtelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }
        
        return studentList;
    }
    @Override
    public ArrayList<Student> getSpecifiedStudents(String studentName) throws DataRetrievalException{
        ArrayList<Student> students = new ArrayList<>();
        PreparedStatement statement;
        String query = "SELECT * FROM Usuarios U INNER JOIN Estudiantes E " + 
                           "ON U.IdUsuario = E.IdUsuario WHERE U.nombre LIKE ?";
        
        try {
            statement = dataBaseManager.getConnection().prepareStatement(query);
            statement.setString(1, studentName + '%');
            
            ResultSet resultSet = statement.executeQuery();
            
            while(resultSet.next()) {
                Student student = new Student();
                student.setName(resultSet.getString("nombre"));
                student.setFirstSurname(resultSet.getString("apellidoPaterno"));
                student.setSecondSurname(resultSet.getString("apellidoMaterno"));
                student.setEmailAddress(resultSet.getString("correo"));
                student.setPassword(resultSet.getString("contraseña"));
                student.setAlternateEmail(resultSet.getString("correoAlterno"));
                student.setPhoneNumber(resultSet.getString("numeroTelefono"));
                student.setStatus(resultSet.getString("estado"));
                student.setUserId(resultSet.getInt("IdUsuario"));
                student.setMatricle(resultSet.getString("Matrícula"));
                students.add(student);
            }
            resultSet.close();
            dataBaseManager.getConnection().close();
        }catch(SQLException exception){
            throw new DataRetrievalException("Error al recuperar la información. Verifique su conexión e intentelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }

        return students;
    }
    @Override
    public Student getStudent(String matricle) throws DataRetrievalException{
        Student student = new Student();
        String query = "SELECT * FROM Usuarios U INNER JOIN Estudiantes E " + 
                           "ON U.IdUsuario = E.IdUsuario WHERE E.Matrícula = ?";

        try {
            
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
                student.setPhoneNumber(resultSet.getString("numeroTelefono"));
                student.setStatus(resultSet.getString("estado"));
                student.setUserId(resultSet.getInt("IdUsuario"));
                student.setMatricle(resultSet.getString("Matrícula"));
            }
            
            resultSet.close();
            dataBaseManager.getConnection().close();
        }catch(SQLException e) {
            throw new DataRetrievalException("Error al recuperar la información. Verifique su conexión e intentelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }

        return student;
    }
    @Override
    public ArrayList<Student> getAvailableStudents() throws DataRetrievalException{
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
                student.setPhoneNumber(resultSet.getString("numeroTelefono"));
                student.setStatus(resultSet.getString("estado"));
                student.setUserId(resultSet.getInt("IdUsuario"));
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
    public ArrayList<Student> getSpecifiedAvailableStudents(String studentName) throws DataRetrievalException{
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
                student.setPhoneNumber(resultSet.getString("numeroTelefono"));
                student.setStatus(resultSet.getString("estado"));
                student.setUserId(resultSet.getInt("IdUsuario"));
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
    public ArrayList<Student> getActiveStudents() throws DataRetrievalException {
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
                student.setPhoneNumber(resultSet.getString("numeroTelefono"));
                student.setStatus(resultSet.getString("estado"));
                student.setUserId(resultSet.getInt("IdUsuario"));
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
    public ArrayList<Student> getSpecifiedActiveStudents(String studentName) throws DataRetrievalException{
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
                student.setPhoneNumber(resultSet.getString("numeroTelefono"));
                student.setStatus(resultSet.getString("estado"));
                student.setUserId(resultSet.getInt("IdUsuario"));
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
    
    public ArrayList<Student> getStudentsWithoutResearch() throws DataRetrievalException{
        ArrayList<Student> studentList = new ArrayList<>();
        PreparedStatement statement;
        String query = "SELECT e.Matrícula, u.* FROM Estudiantes e LEFT JOIN Anteproyectos a ON e.Matrícula = a.Matrícula "
                + "LEFT JOIN Usuarios u ON e.IdUsuario = u.IdUsuario WHERE a.Matrícula IS NULL;";
        
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
            throw new DataRetrievalException("Error al recuperar estudiantes. Verifique su conexión e inténtelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }
        
        return studentList;
    }
    
    private void deleteStudentFromUsersTable(Student student) throws DataInsertionException{
        String queryToInsertUserData = "DELETE FROM Usuarios WHERE IdUsuario = ?";
        try{
            PreparedStatement preparedStatementToInsertUserData = 
            dataBaseManager.getConnection().prepareStatement(queryToInsertUserData);
            preparedStatementToInsertUserData.setInt(1, student.getUserId());
            preparedStatementToInsertUserData.executeUpdate();
        }catch(SQLException e){
            throw new DataInsertionException("Error al eliminar estudiante de la tabla usuarios");
        }finally{
            dataBaseManager.closeConnection();
        }
    }
}
