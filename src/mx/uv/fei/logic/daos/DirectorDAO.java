package mx.uv.fei.logic.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;

import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.logic.daosinterfaces.IDirectorDAO;
import mx.uv.fei.logic.domain.Director;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.exceptions.DataInsertionException;

public class DirectorDAO implements IDirectorDAO{
    private final DataBaseManager dataBaseManager;
    
    public DirectorDAO(){
        dataBaseManager = new DataBaseManager();
    }

    @Override
    public void addDirectorToDatabase(Director director) throws DataInsertionException{
        try{
            String queryToInsertDirectorDataToUserColumns = 
                "INSERT INTO Usuarios (nombre, apellidoPaterno, apellidoMaterno, correo, correoAlterno, númeroTeléfono, estado) VALUES (?, ?, ?, ?, ?, ?, ?)";
            
            PreparedStatement preparedStatementToInsertDirectorDataToUserColumns = 
                dataBaseManager.getConnection().prepareStatement(queryToInsertDirectorDataToUserColumns);
            preparedStatementToInsertDirectorDataToUserColumns.setString(1, director.getName());
            preparedStatementToInsertDirectorDataToUserColumns.setString(2, director.getFirstSurname());
            preparedStatementToInsertDirectorDataToUserColumns.setString(3, director.getSecondSurname());
            preparedStatementToInsertDirectorDataToUserColumns.setString(4, director.getEmailAddress());
            preparedStatementToInsertDirectorDataToUserColumns.setString(5, director.getAlternateEmail());
            preparedStatementToInsertDirectorDataToUserColumns.setString(6, director.getPhoneNumber());
            preparedStatementToInsertDirectorDataToUserColumns.setString(7, director.getStatus());
            preparedStatementToInsertDirectorDataToUserColumns.executeUpdate();

            String queryForAssignUserIdToDirector =
                "SELECT IdUsuario FROM Usuarios WHERE nombre = ? && " +
                "apellidoPaterno = ? && apellidoMaterno = ? && correo = ? && " +
                "correoAlterno = ? && númeroTeléfono = ? && estado = ?";
            PreparedStatement preparedStatementForAssignUserIdToDirector =  
                dataBaseManager.getConnection().prepareStatement(queryForAssignUserIdToDirector);
            preparedStatementForAssignUserIdToDirector.setString(1, director.getName());
            preparedStatementForAssignUserIdToDirector.setString(2, director.getFirstSurname());
            preparedStatementForAssignUserIdToDirector.setString(3, director.getSecondSurname());
            preparedStatementForAssignUserIdToDirector.setString(4, director.getEmailAddress());
            preparedStatementForAssignUserIdToDirector.setString(5, director.getAlternateEmail());
            preparedStatementForAssignUserIdToDirector.setString(6, director.getPhoneNumber());
            preparedStatementForAssignUserIdToDirector.setString(7, director.getStatus());
            ResultSet resultSetForAssignUserIdToDirector = 
                preparedStatementForAssignUserIdToDirector.executeQuery();
            if(resultSetForAssignUserIdToDirector.next()){
                director.setUserId(resultSetForAssignUserIdToDirector.getInt("IdUsuario"));
            }

            String wholeQueryToInsertDirectorDataToProfessorsColumns = 
                "INSERT INTO Profesores (NumPersonal, IdUsuario) VALUES (?, ?)";
            PreparedStatement preparedStatementToInsertDirectorDataToProfessorsColumns = 
                dataBaseManager.getConnection().prepareStatement(wholeQueryToInsertDirectorDataToProfessorsColumns);
            preparedStatementToInsertDirectorDataToProfessorsColumns.setInt(1, director.getStaffNumber());
            preparedStatementToInsertDirectorDataToProfessorsColumns.setInt(2, director.getUserId());
            preparedStatementToInsertDirectorDataToProfessorsColumns.executeUpdate();

            String queryForAssignProfessorIdToDirector =
                "SELECT NumPersonal FROM Usuarios U INNER JOIN Profesores P " +
                "ON U.IdUsuario = P.IdUsuario WHERE nombre = ? && " +
                "apellidoPaterno = ? && apellidoMaterno = ? && correo = ? && " +
                "correoAlterno = ? && númeroTeléfono = ? && estado = ? && NumPersonal = ?";
            PreparedStatement preparedStatementForAssignProfessorIdToDirector = 
                dataBaseManager.getConnection().prepareStatement(queryForAssignProfessorIdToDirector);
            preparedStatementForAssignProfessorIdToDirector.setString(1, director.getName());
            preparedStatementForAssignProfessorIdToDirector.setString(2, director.getFirstSurname());
            preparedStatementForAssignProfessorIdToDirector.setString(3, director.getSecondSurname());
            preparedStatementForAssignProfessorIdToDirector.setString(4, director.getEmailAddress());
            preparedStatementForAssignProfessorIdToDirector.setString(5, director.getAlternateEmail());
            preparedStatementForAssignProfessorIdToDirector.setString(6, director.getPhoneNumber());
            preparedStatementForAssignProfessorIdToDirector.setString(7, director.getStatus());
            preparedStatementForAssignProfessorIdToDirector.setInt(8, director.getStaffNumber());
            
            ResultSet resultSetForAssignProfessorIdToDirector = 
                preparedStatementForAssignProfessorIdToDirector.executeQuery();
            if(resultSetForAssignProfessorIdToDirector.next()){
                director.setStaffNumber(resultSetForAssignProfessorIdToDirector.getInt("NumPersonal"));
            }

            String queryToInsertDirectorDataToDirectorColumns = 
                "INSERT INTO Directores (NumPersonal) VALUES (?)";
            
            PreparedStatement preparedStatementToInsertDirectorDataToDirectorColumns = 
                dataBaseManager.getConnection().prepareStatement(queryToInsertDirectorDataToDirectorColumns);
            preparedStatementToInsertDirectorDataToDirectorColumns.setInt(1, director.getStaffNumber());
            preparedStatementToInsertDirectorDataToDirectorColumns.executeUpdate();

            preparedStatementToInsertDirectorDataToDirectorColumns.close();
            dataBaseManager.getConnection().close();

        }catch(SQLIntegrityConstraintViolationException e){
            deleteDirectorFromUsersTable(director);
            throw new DataInsertionException("Error al agregar estudiante. Verifique su conexion e intentelo de nuevo");
        }catch(SQLException e){
            e.printStackTrace();
            throw new DataInsertionException("Error al agregar director. Verifique su conexion e intentelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }
    }

    @Override
    public void modifyDirectorData(Director newDirectorData, Director originalDirectorData) throws DataInsertionException{
        try{
            String queryForUpdateUserData = "UPDATE Usuarios SET nombre = ?, " + 
                           "apellidoPaterno = ?, apellidoMaterno = ?, correo = ?, " + 
                           "correoAlterno = ?, númeroTeléfono = ?, estado = ? " +
                           "WHERE nombre = ? && apellidoPaterno = ? && apellidoMaterno = ? && " + 
                           "correo = ? && correoAlterno = ? && númeroTeléfono = ? && estado = ?";
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(queryForUpdateUserData);
            preparedStatement.setString(1, newDirectorData.getName());
            preparedStatement.setString(2, newDirectorData.getFirstSurname());
            preparedStatement.setString(3, newDirectorData.getSecondSurname());
            preparedStatement.setString(4, newDirectorData.getEmailAddress());
            preparedStatement.setString(5, newDirectorData.getAlternateEmail());
            preparedStatement.setString(6, newDirectorData.getPhoneNumber());
            preparedStatement.setString(7, newDirectorData.getStatus());
            preparedStatement.setString(8, originalDirectorData.getName());
            preparedStatement.setString(9, originalDirectorData.getFirstSurname());
            preparedStatement.setString(10, originalDirectorData.getSecondSurname());
            preparedStatement.setString(11, originalDirectorData.getEmailAddress());
            preparedStatement.setString(12, originalDirectorData.getAlternateEmail());
            preparedStatement.setString(13, originalDirectorData.getPhoneNumber());
            preparedStatement.setString(14, originalDirectorData.getStatus());
            preparedStatement.executeUpdate();

            String queryForUpdateProfessorData = "UPDATE Profesores SET NumPersonal = ? " + 
                           "WHERE IdUsuario = ?";
            
            PreparedStatement preparedStatementForUpdateProfessorData = 
                dataBaseManager.getConnection().prepareStatement(queryForUpdateProfessorData);
            preparedStatementForUpdateProfessorData.setInt(1, newDirectorData.getStaffNumber());
            preparedStatementForUpdateProfessorData.setInt(2, newDirectorData.getUserId());
            preparedStatementForUpdateProfessorData.executeUpdate();

            String queryForUpdateDirectorData = "UPDATE Directores SET NumPersonal = ? " + 
                           "WHERE NumPersonal = ?";
            
            PreparedStatement preparedStatementForUpdateDirectorData = 
                dataBaseManager.getConnection().prepareStatement(queryForUpdateDirectorData);
            preparedStatementForUpdateDirectorData.setInt(1, newDirectorData.getStaffNumber());
            preparedStatementForUpdateDirectorData.setInt(2, originalDirectorData.getStaffNumber());
            preparedStatementForUpdateDirectorData.executeUpdate();
        }catch(SQLException e){
            throw new DataInsertionException("Error al agregar director. Verifique su conexion e intentelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }
    }
    
    @Override
    public ArrayList<Director> getDirectorList() throws DataRetrievalException{
        ArrayList<Director> directorList = new ArrayList<>();
        PreparedStatement statement;
        String query = "SELECT d.IdDirector, p.NumPersonal, u.nombre, u.apellidoPaterno, u.apellidoMaterno FROM Directores d "
                + "INNER JOIN Profesores p ON d.NumPersonal = p.NumPersonal INNER JOIN Usuarios u ON u.IdUsuario = p.IdUsuario";
        
        try{
            statement = dataBaseManager.getConnection().prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                Director director = new Director();
                
                director.setDirectorId(resultSet.getInt("d.IdDirector"));
                director.setStaffNumber(resultSet.getInt("p.NumPersonal"));
                director.setName(resultSet.getString("u.nombre"));
                director.setFirstSurname(resultSet.getString("u.apellidoPaterno"));
                director.setSecondSurname(resultSet.getString("u.apellidoMaterno"));
                
                directorList.add(director);
            }
        }catch(SQLException exception){
            throw new DataRetrievalException("Error al recuperar la información. Verifique su conexión e intentelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }
        
        return directorList;
    }
    
    @Override
    public ArrayList<Director> getDirectors() throws DataRetrievalException{
        ArrayList<Director> directors = new ArrayList<>();

        try{
            Statement statement = dataBaseManager.getConnection().createStatement();
            String query = "SELECT * FROM Usuarios U INNER JOIN Profesores P ON U.IdUsuario = P.IdUsuario INNER JOIN Directores D ON P.NumPersonal = D.NumPersonal";
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()) {
                Director director = new Director();
                director.setName(resultSet.getString("nombre"));
                director.setFirstSurname(resultSet.getString("apellidoPaterno"));
                director.setSecondSurname(resultSet.getString("apellidoMaterno"));
                director.setEmailAddress(resultSet.getString("correo"));
                director.setPassword(resultSet.getString("contraseña"));
                director.setAlternateEmail(resultSet.getString("correoAlterno"));
                director.setPhoneNumber(resultSet.getString("númeroTeléfono"));
                director.setStatus(resultSet.getString("estado"));
                director.setStaffNumber(resultSet.getInt("NumPersonal"));
                directors.add(director);
            }
            resultSet.close();
            dataBaseManager.getConnection().close();
        }catch(SQLException e){
            throw new DataRetrievalException("Error al recuperar la información. Verifique su conexión e intentelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }

        return directors;
    }

    @Override
    public ArrayList<Director> getSpecifiedDirectors(String directorName) throws DataRetrievalException{
        ArrayList<Director> directors = new ArrayList<>();

        try{
            String query = "SELECT * FROM Usuarios U INNER JOIN Profesores P ON U.IdUsuario = P.IdUsuario INNER JOIN Directores D ON P.NumPersonal = D.NumPersonal WHERE U.Nombre LIKE ?";
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setString(1, directorName + '%');
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                Director director = new Director();
                director.setName(resultSet.getString("nombre"));
                director.setFirstSurname(resultSet.getString("apellidoPaterno"));
                director.setSecondSurname(resultSet.getString("apellidoMaterno"));
                director.setEmailAddress(resultSet.getString("correo"));
                director.setPassword(resultSet.getString("contraseña"));
                director.setAlternateEmail(resultSet.getString("correoAlterno"));
                director.setPhoneNumber(resultSet.getString("númeroTeléfono"));
                director.setStatus(resultSet.getString("estado"));
                director.setStaffNumber(resultSet.getInt("NumPersonal"));
                directors.add(director);
            }
            resultSet.close();
            dataBaseManager.getConnection().close();
        }catch(SQLException e){
            throw new DataRetrievalException("Error al recuperar la información. Verifique su conexión e intentelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }

        return directors;
    }

    @Override
    public Director getDirector(int personalNumber) throws DataRetrievalException{
        Director director = new Director();

        try{
            String query = "SELECT * FROM Usuarios U INNER JOIN Profesores P ON U.IdUsuario = P.IdUsuario INNER JOIN Directores D ON P.NumPersonal = D.NumPersonal WHERE D.NumPersonal = ?";
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, personalNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                director.setName(resultSet.getString("nombre"));
                director.setFirstSurname(resultSet.getString("apellidoPaterno"));
                director.setSecondSurname(resultSet.getString("apellidoMaterno"));
                director.setEmailAddress(resultSet.getString("correo"));
                director.setPassword(resultSet.getString("contraseña"));
                director.setAlternateEmail(resultSet.getString("correoAlterno"));
                director.setPhoneNumber(resultSet.getString("númeroTeléfono"));
                director.setStatus(resultSet.getString("estado"));
                director.setStaffNumber(resultSet.getInt("NumPersonal"));
            }

            resultSet.close();
            dataBaseManager.getConnection().close();
        }catch(SQLException e){
            throw new DataRetrievalException("Error al recuperar la información. Verifique su conexión e intentelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }

        return director;
    }

    public boolean theDirectorIsAlreadyRegisted(Director director) throws DataRetrievalException{
        try{
            Statement statement = dataBaseManager.getConnection().createStatement();
            String query = "SELECT U.nombre, U.apellidoPaterno, U.apellidoMaterno, U.correo, " +
                           "U.correoAlterno, U.númeroTeléfono, U.estado, P.NumPersonal FROM Usuarios U " + 
                           "INNER JOIN Profesores P ON U.IdUsuario = P.IdUsuario INNER JOIN Directores D " +
                           "ON P.NumPersonal = D.NumPersonal";
            ResultSet resultSet = statement.executeQuery(query);
            
            while(resultSet.next()) {
                if(resultSet.getString("nombre").equals(director.getName()) &&
                   resultSet.getString("apellidoPaterno").equals(director.getFirstSurname()) &&
                   resultSet.getString("apellidoMaterno").equals(director.getSecondSurname()) &&
                   resultSet.getString("correo").equals(director.getEmailAddress()) &&
                   resultSet.getString("correoAlterno").equals(director.getAlternateEmail()) &&
                   resultSet.getString("númeroTeléfono").equals(director.getPhoneNumber()) &&
                   resultSet.getString("estado").equals(director.getStatus()) &&
                   resultSet.getInt("NumPersonal") == director.getStaffNumber()) {

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
    private void deleteDirectorFromUsersTable(Director director) throws DataInsertionException{
        String queryToInsertUserData = "DELETE FROM Usuarios WHERE nombre = ? && apellidoPaterno = ? && apellidoMaterno = ? && " +
            "correo = ? && correoAlterno = ? && númeroTeléfono = ? && estado = ?";
        try{
            PreparedStatement preparedStatementToInsertUserData = 
            dataBaseManager.getConnection().prepareStatement(queryToInsertUserData);
            preparedStatementToInsertUserData.setString(1, director.getName());
            preparedStatementToInsertUserData.setString(2, director.getFirstSurname());
            preparedStatementToInsertUserData.setString(3, director.getSecondSurname());
            preparedStatementToInsertUserData.setString(4, director.getEmailAddress());
            preparedStatementToInsertUserData.setString(5, director.getAlternateEmail());
            preparedStatementToInsertUserData.setString(6, director.getPhoneNumber());
            preparedStatementToInsertUserData.setString(7, director.getStatus());
            preparedStatementToInsertUserData.executeUpdate();
        }catch(SQLException e){
            throw new DataInsertionException("Error al eliminar profesor de la tabla usuarios");
        }
    }   
}