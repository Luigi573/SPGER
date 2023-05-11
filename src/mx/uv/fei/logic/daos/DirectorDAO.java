package mx.uv.fei.logic.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.logic.daosinterfaces.IDirectorDAO;
import mx.uv.fei.logic.domain.Director;

public class DirectorDAO implements IDirectorDAO{

    @Override
    public void addDirectorToDatabase(Director director) {
        try {
            DataBaseManager dataBaseManager = new DataBaseManager();
            String userColumnsToConsult = 
                "nombre, apellidoPaterno, apellidoMaterno, correo, correoAlterno, númeroTeléfono, estado";
            String wholeQueryToInsertDirectorDataToUserColumns = 
                "INSERT INTO Usuarios (" + userColumnsToConsult + ") VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatementToInsertDirectorDataToUserColumns = 
                dataBaseManager.getConnection().prepareStatement(wholeQueryToInsertDirectorDataToUserColumns);
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
                director.setIdUser(resultSetForAssignUserIdToDirector.getInt("IdUsuario"));
            }

            String wholeQueryToInsertDirectorDataToProfessorsColumns = 
                "INSERT INTO Profesores (NumPersonal, IdUsuario) VALUES (?, ?)";
            PreparedStatement preparedStatementToInsertDirectorDataToProfessorsColumns = 
                dataBaseManager.getConnection().prepareStatement(wholeQueryToInsertDirectorDataToProfessorsColumns);
            preparedStatementToInsertDirectorDataToProfessorsColumns.setInt(1, director.getPersonalNumber());
            preparedStatementToInsertDirectorDataToProfessorsColumns.setInt(2, director.getIdUser());
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
            preparedStatementForAssignProfessorIdToDirector.setInt(8, director.getPersonalNumber());
            ResultSet resultSetForAssignProfessorIdToDirector = 
                preparedStatementForAssignProfessorIdToDirector.executeQuery();
            if(resultSetForAssignProfessorIdToDirector.next()){
                director.setPersonalNumber(resultSetForAssignProfessorIdToDirector.getInt("NumPersonal"));
            }

            String queryToInsertDirectorDataToDirectorColumns = 
                "INSERT INTO Directores (NumPersonal) VALUES (?)";
            PreparedStatement preparedStatementToInsertDirectorDataToDirectorColumns = 
                dataBaseManager.getConnection().prepareStatement(queryToInsertDirectorDataToDirectorColumns);
            preparedStatementToInsertDirectorDataToDirectorColumns.setInt(1, director.getPersonalNumber());
            preparedStatementToInsertDirectorDataToDirectorColumns.executeUpdate();

            preparedStatementToInsertDirectorDataToDirectorColumns.close();
            dataBaseManager.getConnection().close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void modifyDirectorDataFromDatabase(Director newDirectorData, Director originalDirectorData) {
        try {
            DataBaseManager dataBaseManager = new DataBaseManager();
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
            preparedStatementForUpdateProfessorData.setInt(1, newDirectorData.getPersonalNumber());
            preparedStatementForUpdateProfessorData.setInt(2, newDirectorData.getIdUser());
            preparedStatementForUpdateProfessorData.executeUpdate();

            String queryForUpdateDirectorData = "UPDATE Directores SET NumPersonal = ? " + 
                           "WHERE NumPersonal = ?";
            
            PreparedStatement preparedStatementForUpdateDirectorData = 
                dataBaseManager.getConnection().prepareStatement(queryForUpdateDirectorData);
            preparedStatementForUpdateDirectorData.setInt(1, newDirectorData.getPersonalNumber());
            preparedStatementForUpdateDirectorData.setInt(2, originalDirectorData.getPersonalNumber());
            preparedStatementForUpdateDirectorData.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Director> getDirectorsFromDatabase() {
        ArrayList<Director> directors = new ArrayList<>();

        try {
            DataBaseManager dataBaseManager = new DataBaseManager();
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
                director.setPhoneNumber(resultSet.getString("estado"));
                director.setPersonalNumber(resultSet.getInt("NumPersonal"));
                directors.add(director);
            }
            resultSet.close();
            dataBaseManager.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return directors;
    }

    @Override
    public ArrayList<Director> getSpecifiedDirectorsFromDatabase(String directorName) {
        ArrayList<Director> directors = new ArrayList<>();

        try {
            DataBaseManager dataBaseManager = new DataBaseManager();
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
                director.setPhoneNumber(resultSet.getString("estado"));
                director.setPersonalNumber(resultSet.getInt("NumPersonal"));
                directors.add(director);
            }
            resultSet.close();
            dataBaseManager.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return directors;
    }

    @Override
    public Director getDirectorFromDatabase(int personalNumber){
        Director director = new Director();

        try {
            DataBaseManager dataBaseManager = new DataBaseManager();
            String query = "SELECT * FROM Usuarios U INNER JOIN Profesores P ON U.IdUsuario = P.IdUsuario INNER JOIN Directores D ON P.NumPersonal = D.NumPersonal WHERE D.NumPersonal = ?";
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, personalNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                director.setName(resultSet.getString("nombre"));
                director.setFirstSurname(resultSet.getString("apellidoPaterno"));
                director.setSecondSurname(resultSet.getString("apellidoMaterno"));
                director.setEmailAddress(resultSet.getString("correo"));
                director.setPassword(resultSet.getString("contraseña"));
                director.setAlternateEmail(resultSet.getString("correoAlterno"));
                director.setPhoneNumber(resultSet.getString("númeroTeléfono"));
                director.setPhoneNumber(resultSet.getString("estado"));
                director.setPersonalNumber(resultSet.getInt("NumPersonal"));
            }

            resultSet.close();
            dataBaseManager.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return director;
    }

    public boolean theDirectorIsAlreadyRegisted(Director director) {
        try {
            DataBaseManager dataBaseManager = new DataBaseManager();
            Statement statement = dataBaseManager.getConnection().createStatement();
            String query = "SELECT U.nombre, U.apellidoPaterno, U.apellidoMaterno, U.correo, " +
                           "U.correoAlterno, U.númeroTeléfono, U.estado, P.NumPersonal FROM Usuarios U " + 
                           "INNER JOIN Profesores P ON U.IdUsuario = P.IdUsuario INNER JOIN Directores D " +
                           "ON P.NumPersonal = D.NumPersonal";
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()) {
                if( (resultSet.getString("nombre").equals(director.getName()) &&
                   resultSet.getString("apellidoPaterno").equals(director.getFirstSurname()) &&
                   resultSet.getString("apellidoMaterno").equals(director.getSecondSurname()) &&
                   resultSet.getString("correo").equals(director.getEmailAddress()) &&
                   resultSet.getString("correoAlterno").equals(director.getAlternateEmail()) &&
                   resultSet.getString("númeroTeléfono").equals(director.getPhoneNumber()) &&
                   resultSet.getString("estado").equals(director.getStatus()) &&
                   resultSet.getInt("NumPersonal") == director.getPersonalNumber()) ||
                   resultSet.getInt("NumPersonal") == director.getPersonalNumber()) {
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
