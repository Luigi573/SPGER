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
import mx.uv.fei.logic.exceptions.DataInsertionException;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.exceptions.DuplicatedPrimaryKeyException;

public class DirectorDAO implements IDirectorDAO {
    private final DataBaseManager dataBaseManager;

    public DirectorDAO() {
        dataBaseManager = new DataBaseManager();
    }

    @Override
    public int addDirector(Director director) throws DataInsertionException, DuplicatedPrimaryKeyException {
        int generatedId = 0;
        try {
            String queryToInsertDirectorDataToUserColumns = "INSERT INTO Users (name, firstSurname, secondSurname, emailAddress, alternateEmail, phoneNumber, status, password) VALUES (?, ?, ?, ?, ?, ?, ?, SHA2(?, 256))";

            PreparedStatement preparedStatementToInsertDirectorDataToUsersColumns = dataBaseManager.getConnection()
                    .prepareStatement(
                            queryToInsertDirectorDataToUserColumns, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatementToInsertDirectorDataToUsersColumns.setString(1, director.getName());
            preparedStatementToInsertDirectorDataToUsersColumns.setString(2, director.getFirstSurname());
            preparedStatementToInsertDirectorDataToUsersColumns.setString(3, director.getSecondSurname());
            preparedStatementToInsertDirectorDataToUsersColumns.setString(4, director.getEmailAddress());
            preparedStatementToInsertDirectorDataToUsersColumns.setString(5, director.getAlternateEmail());
            preparedStatementToInsertDirectorDataToUsersColumns.setString(6, director.getPhoneNumber());
            preparedStatementToInsertDirectorDataToUsersColumns.setString(7, director.getStatus());
            preparedStatementToInsertDirectorDataToUsersColumns.setString(8, director.getPassword());
            preparedStatementToInsertDirectorDataToUsersColumns.executeUpdate();

            ResultSet resultSet = preparedStatementToInsertDirectorDataToUsersColumns.getGeneratedKeys();
            if (resultSet.next()) {
                generatedId = resultSet.getInt(1);
                director.setUserId(generatedId);
            }

            String queryToInsertDirectorDataToProfessorsColumns = "INSERT INTO Professors (staffNumber, userId) VALUES (?, ?)";
            PreparedStatement preparedStatementToInsertDirectorDataToProfessorsColumns = dataBaseManager.getConnection()
                    .prepareStatement(queryToInsertDirectorDataToProfessorsColumns);
            preparedStatementToInsertDirectorDataToProfessorsColumns.setInt(1, director.getStaffNumber());
            preparedStatementToInsertDirectorDataToProfessorsColumns.setInt(2, director.getUserId());
            preparedStatementToInsertDirectorDataToProfessorsColumns.executeUpdate();

            String queryToInsertDirectorDataToDirectorColumns = "INSERT INTO Directors (staffNumber) VALUES (?)";

            PreparedStatement preparedStatementToInsertDirectorDataToDirectorColumns = dataBaseManager.getConnection()
                    .prepareStatement(queryToInsertDirectorDataToDirectorColumns);
            preparedStatementToInsertDirectorDataToDirectorColumns.setInt(1, director.getStaffNumber());
            preparedStatementToInsertDirectorDataToDirectorColumns.executeUpdate();

            preparedStatementToInsertDirectorDataToDirectorColumns.close();
            dataBaseManager.closeConnection();

        } catch (SQLIntegrityConstraintViolationException e) {
            deleteDirectorFromUsersTable(director);
            throw new DuplicatedPrimaryKeyException("Director ya registrado en el sistema");
        } catch (SQLException e) {
            throw new DataInsertionException("Error al agregar director. Inténtelo de nuevo más tarde");
        } finally {
            dataBaseManager.closeConnection();
        }

        return generatedId;
    }

    @Override
    public int modifyDirectorData(Director director) throws DataInsertionException, DuplicatedPrimaryKeyException {
        int result = 0;
        try {
            String queryForUpdateUserData = "UPDATE Users SET name = ?, " +
                    "firstSurname = ?, secondSurname = ?, emailAddress = ?, " +
                    "alternateEmail = ?, phoneNumber = ?, status = ? " +
                    "WHERE userId = ?";
            PreparedStatement preparedStatement = dataBaseManager.getConnection()
                    .prepareStatement(queryForUpdateUserData);
            preparedStatement.setString(1, director.getName());
            preparedStatement.setString(2, director.getFirstSurname());
            preparedStatement.setString(3, director.getSecondSurname());
            preparedStatement.setString(4, director.getEmailAddress());
            preparedStatement.setString(5, director.getAlternateEmail());
            preparedStatement.setString(6, director.getPhoneNumber());
            preparedStatement.setString(7, director.getStatus());
            preparedStatement.setInt(8, director.getUserId());
            result = preparedStatement.executeUpdate();

            String queryForUpdateProfessorData = "UPDATE Professors SET staffNumber = ? " +
                    "WHERE userId = ?";

            PreparedStatement preparedStatementForUpdateProfessorData = dataBaseManager.getConnection()
                    .prepareStatement(queryForUpdateProfessorData);
            preparedStatementForUpdateProfessorData.setInt(1, director.getStaffNumber());
            preparedStatementForUpdateProfessorData.setInt(2, director.getUserId());
            preparedStatementForUpdateProfessorData.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DuplicatedPrimaryKeyException("Director ya registrado en el sistema");
        } catch (SQLException e) {
            throw new DataInsertionException("Error al modificar director. Inténtelo de nuevo más tarde");
        } finally {
            dataBaseManager.closeConnection();
        }

        return result;
    }

    @Override
    public ArrayList<Director> getDirectorList() throws DataRetrievalException {
        ArrayList<Director> directorList = new ArrayList<>();
        PreparedStatement statement;
        String query = "SELECT d.directorId, p.staffNumber, u.name, u.firstSurname, u.secondSurname FROM Directors d "
                + "INNER JOIN Professors p ON d.staffNumber = p.staffNumber INNER JOIN Users u ON u.userId = p.userId";

        try {
            statement = dataBaseManager.getConnection().prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Director director = new Director();

                director.setDirectorId(resultSet.getInt("d.directorId"));
                director.setStaffNumber(resultSet.getInt("p.staffNumber"));
                director.setName(resultSet.getString("u.name"));
                director.setFirstSurname(resultSet.getString("u.firstSurname"));
                director.setSecondSurname(resultSet.getString("u.secondSurname"));
                directorList.add(director);
            }
        } catch (SQLException exception) {
            throw new DataRetrievalException(
                    "Error al recuperar la información. Verifique su conexión e intentelo de nuevo");
        } finally {
            dataBaseManager.closeConnection();
        }

        return directorList;
    }

    @Override
    public ArrayList<Director> getDirectors() throws DataRetrievalException {
        ArrayList<Director> directors = new ArrayList<>();

        try {
            Statement statement = dataBaseManager.getConnection().createStatement();
            String query = "SELECT * FROM Users U INNER JOIN Professors P ON U.userId = P.userId INNER JOIN Directors D ON P.staffNumber = D.staffNumber";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Director director = new Director();
                director.setUserId(resultSet.getInt("userId"));
                director.setName(resultSet.getString("name"));
                director.setFirstSurname(resultSet.getString("firstSurname"));
                director.setSecondSurname(resultSet.getString("secondSurname"));
                director.setEmailAddress(resultSet.getString("emailAddress"));
                director.setPassword(resultSet.getString("password"));
                director.setAlternateEmail(resultSet.getString("alternateEmail"));
                director.setPhoneNumber(resultSet.getString("phoneNumber"));
                director.setStatus(resultSet.getString("status"));
                director.setStaffNumber(resultSet.getInt("staffNumber"));
                directors.add(director);
            }
            resultSet.close();
            dataBaseManager.closeConnection();
        } catch (SQLException e) {
            throw new DataRetrievalException(
                    "Error al recuperar la información. Verifique su conexión e intentelo de nuevo");
        } finally {
            dataBaseManager.closeConnection();
        }

        return directors;
    }

    @Override
    public ArrayList<Director> getSpecifiedDirectors(String directorName) throws DataRetrievalException {
        ArrayList<Director> directors = new ArrayList<>();

        try {
            String query = "SELECT * FROM Users U INNER JOIN Professors P ON U.userId = P.userId INNER JOIN Directors D ON P.staffNumber = D.staffNumber WHERE U.name LIKE ?";
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setString(1, directorName + '%');
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Director director = new Director();
                director.setUserId(resultSet.getInt("userId"));
                director.setName(resultSet.getString("name"));
                director.setFirstSurname(resultSet.getString("firstSurname"));
                director.setSecondSurname(resultSet.getString("secondSurname"));
                director.setEmailAddress(resultSet.getString("emailAddress"));
                director.setPassword(resultSet.getString("password"));
                director.setAlternateEmail(resultSet.getString("alternateEmail"));
                director.setPhoneNumber(resultSet.getString("phoneNumber"));
                director.setStatus(resultSet.getString("status"));
                director.setStaffNumber(resultSet.getInt("staffNumber"));
                directors.add(director);
            }
            resultSet.close();
            dataBaseManager.closeConnection();
        } catch (SQLException e) {
            throw new DataRetrievalException(
                    "Error al recuperar la información. Verifique su conexión e intentelo de nuevo");
        } finally {
            dataBaseManager.closeConnection();
        }

        return directors;
    }

    @Override
    public Director getDirector(int staffNumber) throws DataRetrievalException {
        Director director = new Director();

        try {
            String query = "SELECT * FROM Users U INNER JOIN Professors P ON U.userId = P.userId INNER JOIN Directors D ON P.staffNumber = D.staffNumber WHERE D.staffNumber = ?";
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, staffNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                director.setUserId(resultSet.getInt("userId"));
                director.setName(resultSet.getString("name"));
                director.setFirstSurname(resultSet.getString("firstSurname"));
                director.setSecondSurname(resultSet.getString("secondSurname"));
                director.setEmailAddress(resultSet.getString("emailAddress"));
                director.setPassword(resultSet.getString("password"));
                director.setAlternateEmail(resultSet.getString("alternateEmail"));
                director.setPhoneNumber(resultSet.getString("phoneNumber"));
                director.setStatus(resultSet.getString("status"));
                director.setStaffNumber(resultSet.getInt("staffNumber"));
            }

            resultSet.close();
            dataBaseManager.closeConnection();
        } catch (SQLException e) {
            throw new DataRetrievalException(
                    "Error al recuperar la información. Verifique su conexión e intentelo de nuevo");
        } finally {
            dataBaseManager.closeConnection();
        }

        return director;
    }

    private void deleteDirectorFromUsersTable(Director director) throws DataInsertionException {
        String queryToInsertUserData = "DELETE FROM Users WHERE userId = ?";
        try {
            PreparedStatement preparedStatementToInsertUserData = dataBaseManager.getConnection()
                    .prepareStatement(queryToInsertUserData);
            preparedStatementToInsertUserData.setInt(1, director.getUserId());
            preparedStatementToInsertUserData.executeUpdate();
        } catch (SQLException e) {
            throw new DataInsertionException("Error al eliminar director de la tabla Users");
        }
    }
}