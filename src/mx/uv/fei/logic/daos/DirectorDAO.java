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
                "nombre, apellidoPaterno, apellidoMaterno, correo, correoAlterno, númeroTeléfono";
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
            preparedStatementToInsertDirectorDataToUserColumns.executeUpdate();

            String queryForAssignUserIdToDirector =
                "SELECT IdUsuario FROM Usuarios WHERE nombre = ? && " +
                "apellidoPaterno = ? && apellidoMaterno = ? && correo = ? && " +
                "correoAlterno = ? && númeroTeléfono = ?";
            PreparedStatement preparedStatementForAssignUserIdToDirector =  
                dataBaseManager.getConnection().prepareStatement(queryForAssignUserIdToDirector);
            preparedStatementForAssignUserIdToDirector.setString(1, director.getName());
            preparedStatementForAssignUserIdToDirector.setString(2, director.getFirstSurname());
            preparedStatementForAssignUserIdToDirector.setString(3, director.getSecondSurname());
            preparedStatementForAssignUserIdToDirector.setString(4, director.getEmailAddress());
            preparedStatementForAssignUserIdToDirector.setString(5, director.getAlternateEmail());
            preparedStatementForAssignUserIdToDirector.setString(6, director.getPhoneNumber());
            ResultSet resultSetForAssignUserIdToDirector = 
                preparedStatementForAssignUserIdToDirector.executeQuery();
            if(resultSetForAssignUserIdToDirector.next()){
                director.setIdUser(resultSetForAssignUserIdToDirector.getInt("IdUsuario"));
            }

            String wholeQueryToInsertDirectorDataToProfessorsColumns = 
                "INSERT INTO Profesores (NumPersonal, IdUsuario) VALUES (?, ?)";
            PreparedStatement preparedStatementToInsertDirectorDataToProfessorsColumns = 
                dataBaseManager.getConnection().prepareStatement(wholeQueryToInsertDirectorDataToProfessorsColumns);
            preparedStatementToInsertDirectorDataToProfessorsColumns.setString(1, director.getPersonalNumber());
            preparedStatementToInsertDirectorDataToProfessorsColumns.setInt(2, director.getIdUser());
            preparedStatementToInsertDirectorDataToProfessorsColumns.executeUpdate();

            String queryForAssignProfessorIdToDirector =
                "SELECT IdProfesor FROM Usuarios U INNER JOIN Profesores P " +
                "ON U.IdUsuario = P.IdUsuario WHERE nombre = ? && " +
                "apellidoPaterno = ? && apellidoMaterno = ? && correo = ? && " +
                "correoAlterno = ? && númeroTeléfono = ? && NumPersonal = ?";
            PreparedStatement preparedStatementForAssignProfessorIdToDirector = 
                dataBaseManager.getConnection().prepareStatement(queryForAssignProfessorIdToDirector);
            preparedStatementForAssignProfessorIdToDirector.setString(1, director.getName());
            preparedStatementForAssignProfessorIdToDirector.setString(2, director.getFirstSurname());
            preparedStatementForAssignProfessorIdToDirector.setString(3, director.getSecondSurname());
            preparedStatementForAssignProfessorIdToDirector.setString(4, director.getEmailAddress());
            preparedStatementForAssignProfessorIdToDirector.setString(5, director.getAlternateEmail());
            preparedStatementForAssignProfessorIdToDirector.setString(6, director.getPhoneNumber());
            preparedStatementForAssignProfessorIdToDirector.setString(7, director.getPersonalNumber());
            ResultSet resultSetForAssignProfessorIdToDirector = 
                preparedStatementForAssignProfessorIdToDirector.executeQuery();
            if(resultSetForAssignProfessorIdToDirector.next()){
                director.setIdProfessor(resultSetForAssignProfessorIdToDirector.getInt("IdProfesor"));
            }

            String queryToInsertDirectorDataToDirectorColumns = 
                "INSERT INTO Directores (IdProfesor) VALUES (?)";
            PreparedStatement preparedStatementToInsertDirectorDataToDirectorColumns = 
                dataBaseManager.getConnection().prepareStatement(queryToInsertDirectorDataToDirectorColumns);
            preparedStatementToInsertDirectorDataToDirectorColumns.setInt(1, director.getIdProfessor());
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
                           "correoAlterno = ?, númeroTeléfono = ? " +
                           "WHERE nombre = ? && apellidoPaterno = ? && apellidoMaterno = ? && " + 
                           "correo = ? && correoAlterno = ? && númeroTeléfono = ?";
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(queryForUpdateUserData);
            preparedStatement.setString(1, newDirectorData.getName());
            preparedStatement.setString(2, newDirectorData.getFirstSurname());
            preparedStatement.setString(3, newDirectorData.getSecondSurname());
            preparedStatement.setString(4, newDirectorData.getEmailAddress());
            preparedStatement.setString(5, newDirectorData.getAlternateEmail());
            preparedStatement.setString(6, newDirectorData.getPhoneNumber());
            preparedStatement.setString(7, originalDirectorData.getName());
            preparedStatement.setString(8, originalDirectorData.getFirstSurname());
            preparedStatement.setString(9, originalDirectorData.getSecondSurname());
            preparedStatement.setString(10, originalDirectorData.getEmailAddress());
            preparedStatement.setString(11, originalDirectorData.getAlternateEmail());
            preparedStatement.setString(12, originalDirectorData.getPhoneNumber());
            preparedStatement.executeUpdate();
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
            String query = "SELECT * FROM Usuarios U INNER JOIN Profesores P ON U.IdUsuario = P.IdUsuario INNER JOIN Directores D ON P.IdProfesor = D.IdProfesor";
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
                director.setPersonalNumber(resultSet.getString("NumPersonal"));
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
            String query = "SELECT * FROM Usuarios U INNER JOIN Profesores P ON U.IdUsuario = P.IdUsuario INNER JOIN Directores D ON P.IdProfesor = D.IdProfesor WHERE U.Nombre LIKE ?";
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
                director.setPersonalNumber(resultSet.getString("NumPersonal"));
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
            String query = "SELECT * FROM Usuarios U INNER JOIN Profesores P ON U.IdUsuario = P.IdUsuario INNER JOIN Directores D ON P.IdProfesor = D.IdProfesor WHERE D.NumPersonal = ?";
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
                director.setPersonalNumber(resultSet.getString("NumPersonal"));
            }

            resultSet.close();
            dataBaseManager.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return director;
    }

    public boolean theDirectorIsAlreadyRegisted(int personalNumber) {
        try {
            DataBaseManager dataBaseManager = new DataBaseManager();
            Statement statement = dataBaseManager.getConnection().createStatement();
            String query = "SELECT NumPersonal FROM Profesores";
            //String query = "SELECT U.nombre, U.apellidoPaterno, U.apellidoMaterno, U.correo, " +
            //               "U.correoAlterno, U.númeroTeléfono, P.NumPersonal FROM Usuarios U " + 
            //               "INNER JOIN Profesores P ON U.IdUsuario = P.IdUsuario";
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()) {
                if(resultSet.getInt("NumPersonal") == personalNumber) {
                    resultSet.close();
                    dataBaseManager.getConnection().close();
                    return true;
                }
                //if( (resultSet.getString("nombre").equals(director.getName()) &&
                //   resultSet.getString("apellidoPaterno").equals(director.getFirstSurname()) &&
                //   resultSet.getString("apellidoMaterno").equals(director.getSecondSurname()) &&
                //   resultSet.getString("correo").equals(director.getEmailAddress()) &&
                //   resultSet.getString("correoAlterno").equals(director.getAlternateEmail()) &&
                //   resultSet.getString("númeroTeléfono").equals(director.getPhoneNumber())) ||
                //   resultSet.getString("NumPersonal").equals(director.getPersonalNumber())) {
                //    resultSet.close();
                //    dataBaseManager.getConnection().close();
                //    return true;
                //}
            }
            resultSet.close();
            dataBaseManager.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    
}
