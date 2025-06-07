package mx.uv.fei.logic.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;

import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.logic.daosinterfaces.IAcademicBodyHeadDAO;
import mx.uv.fei.logic.domain.AcademicBodyHead;
import mx.uv.fei.logic.exceptions.DataInsertionException;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.exceptions.DuplicatedPrimaryKeyException;

public class AcademicBodyHeadDAO implements IAcademicBodyHeadDAO {
    private final DataBaseManager dataBaseManager;

    public AcademicBodyHeadDAO() {
        dataBaseManager = new DataBaseManager();
    }

    @Override
    public int addAcademicBodyHead(AcademicBodyHead academicBodyHead)
            throws DataInsertionException, DuplicatedPrimaryKeyException {
        int generatedId = 0;
        try {
            String queryToInsertAcademicBodyHeadDataToUserColumns = "INSERT INTO Users (name, firstSurname, secondSurname, emailAddress, alternateEmail, phoneNumber, status, password) VALUES (?, ?, ?, ?, ?, ?, ?, SHA2(?, 256))";
            PreparedStatement preparedStatementToInsertAcademicBodyHeadDataToUserColumns = dataBaseManager
                    .getConnection().prepareStatement(queryToInsertAcademicBodyHeadDataToUserColumns,
                            PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatementToInsertAcademicBodyHeadDataToUserColumns.setString(1, academicBodyHead.getName());
            preparedStatementToInsertAcademicBodyHeadDataToUserColumns.setString(2, academicBodyHead.getFirstSurname());
            preparedStatementToInsertAcademicBodyHeadDataToUserColumns.setString(3,
                    academicBodyHead.getSecondSurname());
            preparedStatementToInsertAcademicBodyHeadDataToUserColumns.setString(4, academicBodyHead.getEmailAddress());
            preparedStatementToInsertAcademicBodyHeadDataToUserColumns.setString(5,
                    academicBodyHead.getAlternateEmail());
            preparedStatementToInsertAcademicBodyHeadDataToUserColumns.setString(6, academicBodyHead.getPhoneNumber());
            preparedStatementToInsertAcademicBodyHeadDataToUserColumns.setString(7, academicBodyHead.getStatus());
            preparedStatementToInsertAcademicBodyHeadDataToUserColumns.setString(8, academicBodyHead.getPassword());
            preparedStatementToInsertAcademicBodyHeadDataToUserColumns.executeUpdate();

            ResultSet resultSet = preparedStatementToInsertAcademicBodyHeadDataToUserColumns.getGeneratedKeys();
            if (resultSet.next()) {
                generatedId = resultSet.getInt(1);
                academicBodyHead.setUserId(generatedId);
            }

            String queryToInsertAcademicBodyHeadDataToProfessorsColumns = "INSERT INTO Professors (staffNumber, userId) VALUES (?, ?)";
            PreparedStatement preparedStatementToInsertAcademicBodyHeadDataToProfessorsColumns = dataBaseManager
                    .getConnection().prepareStatement(queryToInsertAcademicBodyHeadDataToProfessorsColumns);
            preparedStatementToInsertAcademicBodyHeadDataToProfessorsColumns.setInt(1,
                    academicBodyHead.getStaffNumber());
            preparedStatementToInsertAcademicBodyHeadDataToProfessorsColumns.setInt(2, academicBodyHead.getUserId());
            preparedStatementToInsertAcademicBodyHeadDataToProfessorsColumns.executeUpdate();

            String queryToInsertAcademicBodyHeadDataToAcademicBodyHeadColumns = "INSERT INTO AcademicBodyHeads (staffNumber) VALUES (?)";
            PreparedStatement preparedStatementToInsertAcademicBodyHeadDataToAcademicBodyHeadColumns = dataBaseManager
                    .getConnection().prepareStatement(queryToInsertAcademicBodyHeadDataToAcademicBodyHeadColumns);
            preparedStatementToInsertAcademicBodyHeadDataToAcademicBodyHeadColumns.setInt(1,
                    academicBodyHead.getStaffNumber());
            preparedStatementToInsertAcademicBodyHeadDataToAcademicBodyHeadColumns.executeUpdate();

            preparedStatementToInsertAcademicBodyHeadDataToAcademicBodyHeadColumns.close();
            dataBaseManager.closeConnection();

        } catch (SQLIntegrityConstraintViolationException e) {
            deleteAcademicBodyHeadFromUsersTable(academicBodyHead);
            throw new DuplicatedPrimaryKeyException("Miembro del cuerpo academico ya registrado en el sistema");
        } catch (SQLException e) {
            throw new DataInsertionException(
                    "Error al agregar miembro del cuerpo académico. Inténtelo de nuevo más tarde");
        } finally {
            dataBaseManager.closeConnection();
        }

        return generatedId;
    }

    @Override
    public int modifyAcademicBodyHeadData(AcademicBodyHead academicBodyHead)
            throws DataInsertionException, DuplicatedPrimaryKeyException {
        int result = 0;
        try {
            String queryForUpdateUserData = "UPDATE Users SET name = ?, " +
                    "firstSurname = ?, secondSurname = ?, emailAddress = ?, " +
                    "alternateEmail = ?, phoneNumber ?, status = ? " +
                    "WHERE userId = ?";
            PreparedStatement preparedStatement = dataBaseManager.getConnection()
                    .prepareStatement(queryForUpdateUserData);
            preparedStatement.setString(1, academicBodyHead.getName());
            preparedStatement.setString(2, academicBodyHead.getFirstSurname());
            preparedStatement.setString(3, academicBodyHead.getSecondSurname());
            preparedStatement.setString(4, academicBodyHead.getEmailAddress());
            preparedStatement.setString(5, academicBodyHead.getAlternateEmail());
            preparedStatement.setString(6, academicBodyHead.getPhoneNumber());
            preparedStatement.setString(7, academicBodyHead.getStatus());
            preparedStatement.setInt(8, academicBodyHead.getUserId());
            result = preparedStatement.executeUpdate();

            String queryForUpdateProfessorData = "UPDATE Professors SET staffNumber = ? " +
                    "WHERE userId = ?";

            PreparedStatement preparedStatementForUpdateProfessorData = dataBaseManager.getConnection()
                    .prepareStatement(queryForUpdateProfessorData);
            preparedStatementForUpdateProfessorData.setInt(1, academicBodyHead.getStaffNumber());
            preparedStatementForUpdateProfessorData.setInt(2, academicBodyHead.getUserId());
            preparedStatementForUpdateProfessorData.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DuplicatedPrimaryKeyException("Miembro del cuerpo academico ya registrado en el sistema");
        } catch (SQLException e) {
            throw new DataInsertionException(
                    "Error al modificar miembro del cuerpo académico. Inténtelo de nuevo más tarde");
        } finally {
            dataBaseManager.closeConnection();
        }

        return result;
    }

    @Override
    public ArrayList<AcademicBodyHead> getAcademicBodyHeads() throws DataRetrievalException {
        ArrayList<AcademicBodyHead> academicBodyHeads = new ArrayList<>();

        try {
            Statement statement = dataBaseManager.getConnection().createStatement();
            String query = "SELECT * FROM Users U INNER JOIN Profesors P ON U.userId = P.userId INNER JOIN AcademicBodyHeads ABH ON P.staffNumber = ABH.staffNumber";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                AcademicBodyHead academicBodyHead = new AcademicBodyHead();
                academicBodyHead.setUserId(resultSet.getInt("userId"));
                academicBodyHead.setName(resultSet.getString("name"));
                academicBodyHead.setFirstSurname(resultSet.getString("firstSurname"));
                academicBodyHead.setSecondSurname(resultSet.getString("secondSurname"));
                academicBodyHead.setEmailAddress(resultSet.getString("emailAddress"));
                academicBodyHead.setPassword(resultSet.getString("password"));
                academicBodyHead.setAlternateEmail(resultSet.getString("alternateEmail"));
                academicBodyHead.setPhoneNumber(resultSet.getString("phoneNumber"));
                academicBodyHead.setStatus(resultSet.getString("status"));
                academicBodyHead.setStaffNumber(resultSet.getInt("staffNumber"));
                academicBodyHeads.add(academicBodyHead);
            }
            resultSet.close();
            dataBaseManager.closeConnection();
        } catch (SQLException e) {
            throw new DataRetrievalException("Fallo al recuperar la informacion. Inténtelo de nuevo más tarde");
        } finally {
            dataBaseManager.closeConnection();
        }

        return academicBodyHeads;
    }

    @Override
    public ArrayList<AcademicBodyHead> getSpecifiedAcademicBodyHeads(String academicBodyHeadName)
            throws DataRetrievalException {
        ArrayList<AcademicBodyHead> academicBodyHeads = new ArrayList<>();

        try {
            String query = "SELECT * FROM Users U INNER JOIN Profesors P ON U.userId = P.userId INNER JOIN AcademicBodyHeads ABH ON P.staffNumber = ABH.staffNumber WHERE U.name LIKE ?";
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setString(1, academicBodyHeadName + '%');
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                AcademicBodyHead academicBodyHead = new AcademicBodyHead();
                academicBodyHead.setUserId(resultSet.getInt("userId"));
                academicBodyHead.setName(resultSet.getString("name"));
                academicBodyHead.setFirstSurname(resultSet.getString("firstSurname"));
                academicBodyHead.setSecondSurname(resultSet.getString("secondSurname"));
                academicBodyHead.setEmailAddress(resultSet.getString("emailAddress"));
                academicBodyHead.setPassword(resultSet.getString("password"));
                academicBodyHead.setAlternateEmail(resultSet.getString("alternateEmail"));
                academicBodyHead.setPhoneNumber(resultSet.getString("phoneNumber"));
                academicBodyHead.setStaffNumber(resultSet.getInt("staffNumber"));
                academicBodyHeads.add(academicBodyHead);
            }
            resultSet.close();
            dataBaseManager.closeConnection();
        } catch (SQLException e) {
            throw new DataRetrievalException("Fallo al recuperar la informacion. Inténtelo de nuevo más tarde");
        } finally {
            dataBaseManager.closeConnection();
        }

        return academicBodyHeads;
    }

    @Override
    public AcademicBodyHead getAcademicBodyHead(int staffNumber) throws DataRetrievalException {
        AcademicBodyHead academicBodyHead = new AcademicBodyHead();

        try {
            String query = "SELECT * FROM Users U INNER JOIN Profesors P ON U.userId = P.userId INNER JOIN AcademicBodyHeads ABH ON P.staffNumber = ABH.staffNumber WHERE ABH.staffNumber = ?";
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, staffNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                academicBodyHead.setUserId(resultSet.getInt("userId"));
                academicBodyHead.setName(resultSet.getString("name"));
                academicBodyHead.setFirstSurname(resultSet.getString("firstSurname"));
                academicBodyHead.setSecondSurname(resultSet.getString("secondSurname"));
                academicBodyHead.setEmailAddress(resultSet.getString("emailAddress"));
                academicBodyHead.setPassword(resultSet.getString("password"));
                academicBodyHead.setAlternateEmail(resultSet.getString("alternateEmail"));
                academicBodyHead.setPhoneNumber(resultSet.getString("phoneNumber"));
                academicBodyHead.setStatus(resultSet.getString("status"));
                academicBodyHead.setStaffNumber(resultSet.getInt("staffNumber"));
            }

            resultSet.close();
            dataBaseManager.closeConnection();
        } catch (SQLException e) {
            throw new DataRetrievalException("Fallo al recuperar la informacion. Inténtelo de nuevo más tarde");
        } finally {
            dataBaseManager.closeConnection();
        }

        return academicBodyHead;
    }

    private void deleteAcademicBodyHeadFromUsersTable(AcademicBodyHead academicBodyHead) throws DataInsertionException {
        String queryToInsertUserData = "DELETE FROM Users WHERE userId = ?";
        try {
            PreparedStatement preparedStatementToInsertUserData = dataBaseManager.getConnection()
                    .prepareStatement(queryToInsertUserData);
            preparedStatementToInsertUserData.setInt(1, academicBodyHead.getUserId());
            preparedStatementToInsertUserData.executeUpdate();
        } catch (SQLException e) {
            throw new DataInsertionException("Error al eliminar miembro de cuerpo academico de la tabla Users");
        }
    }
}