package mx.uv.fei.logic.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.logic.daosinterfaces.IUserDAO;
import mx.uv.fei.logic.exceptions.DataInsertionException;
import mx.uv.fei.logic.exceptions.DataRetrievalException;

public class UserDAO implements IUserDAO {
    private final DataBaseManager dataBaseManager;

    public UserDAO() {
        dataBaseManager = new DataBaseManager();
    }

    @Override
    public boolean theEmailIsAvailableToUseToRegister(String email) throws DataRetrievalException {
        try {
            Statement statement = dataBaseManager.getConnection().createStatement();
            String query = "SELECT emailAddress FROM Users";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                if (email.equals(resultSet.getString("emailAddress"))) {
                    resultSet.close();
                    return false;
                }
            }
            resultSet.close();
            dataBaseManager.getConnection().close();
        } catch (SQLException e) {
            throw new DataRetrievalException(
                    "Error al recuperar la información. Verifique su conexión e intentelo de nuevo");
        } finally {
            dataBaseManager.closeConnection();
        }

        return true;
    }

    @Override
    public boolean theAlternateEmailIsAvailableToRegister(String alternateEmail) throws DataRetrievalException {
        try {
            Statement statement = dataBaseManager.getConnection().createStatement();
            String query = "SELECT alternateEmail FROM Users";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                if (alternateEmail.equals(resultSet.getString("alternateEmail"))) {
                    resultSet.close();
                    return false;
                }
            }
            resultSet.close();
            dataBaseManager.getConnection().close();
        } catch (SQLException e) {
            throw new DataRetrievalException(
                    "Error al recuperar la información. Verifique su conexión e intentelo de nuevo");
        } finally {
            dataBaseManager.closeConnection();
        }

        return true;
    }

    @Override
    public boolean theEmailIsAvailableToUseToModify(String email, int userId) throws DataRetrievalException {
        try {
            Statement statement = dataBaseManager.getConnection().createStatement();
            String query = "SELECT userId, emailAddress FROM Users";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                if (email.equals(resultSet.getString("emailAddress")) && userId != resultSet.getInt("userId")) {
                    resultSet.close();
                    return false;
                }
            }
            resultSet.close();
            dataBaseManager.getConnection().close();
        } catch (SQLException e) {
            throw new DataRetrievalException(
                    "Error al recuperar la información. Verifique su conexión e intentelo de nuevo");
        } finally {
            dataBaseManager.closeConnection();
        }

        return true;
    }

    @Override
    public boolean theAlternateEmailIsAvailableToModify(String alternateEmail, int userId)
            throws DataRetrievalException {
        try {
            Statement statement = dataBaseManager.getConnection().createStatement();
            String query = "SELECT userId, alternateEmail FROM Users";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                if (alternateEmail.equals(resultSet.getString("alternateEmail"))
                        && userId != resultSet.getInt("userId")) {
                    resultSet.close();
                    return false;
                }
            }
            resultSet.close();
            dataBaseManager.getConnection().close();
        } catch (SQLException e) {
            throw new DataRetrievalException(
                    "Error al recuperar la información. Verifique su conexión e intentelo de nuevo");
        } finally {
            dataBaseManager.closeConnection();
        }

        return true;
    }

    @Override
    public int updatePassword(String userPassword, int userId) throws DataInsertionException {
        int result = 0;
        try {
            String queryForUpdateUserData = "UPDATE Users SET password = SHA2(?, 256) WHERE userId = ?";
            PreparedStatement preparedStatementForUpdateUserData = dataBaseManager.getConnection()
                    .prepareStatement(queryForUpdateUserData);
            preparedStatementForUpdateUserData.setString(1, userPassword);
            preparedStatementForUpdateUserData.setInt(2, userId);
            result = preparedStatementForUpdateUserData.executeUpdate();
        } catch (SQLException e) {
            throw new DataInsertionException("Error al modificar la contraseña. Inténtelo de nuevo más tarde");
        } finally {
            dataBaseManager.closeConnection();
        }

        return result;
    }

    @Override
    public boolean hasUsersInTheDatabase() throws DataRetrievalException {
        try {
            Statement statement = dataBaseManager.getConnection().createStatement();
            String query = "SELECT userId FROM Users";
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                resultSet.close();
                return true;
            }

            resultSet.close();
            dataBaseManager.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataRetrievalException(
                    "Error al recuperar la información. Verifique su conexión e intentelo de nuevo");
        } finally {
            dataBaseManager.closeConnection();
        }

        return false;
    }
}
