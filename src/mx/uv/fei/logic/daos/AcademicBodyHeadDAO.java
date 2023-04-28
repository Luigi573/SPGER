package mx.uv.fei.logic.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.logic.daosinterfaces.IAcademicBodyHeadDAO;
import mx.uv.fei.logic.domain.AcademicBodyHead;

public class AcademicBodyHeadDAO implements IAcademicBodyHeadDAO{

    @Override
    public void addAcademicBodyHeadToDatabase(AcademicBodyHead academicBodyHead) {
        try {
            DataBaseManager dataBaseManager = new DataBaseManager();
            String userColumnsToConsult = 
                "nombre, apellidoPaterno, apellidoMaterno, correo, correoAlterno, númeroTeléfono";
            String wholeQueryToInsertAcademicBodyHeadDataToUserColumns = 
                "INSERT INTO Usuarios (" + userColumnsToConsult + ") VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatementToInsertAcademicBodyHeadDataToUserColumns = 
                dataBaseManager.getConnection().prepareStatement(wholeQueryToInsertAcademicBodyHeadDataToUserColumns);
            preparedStatementToInsertAcademicBodyHeadDataToUserColumns.setString(1, academicBodyHead.getName());
            preparedStatementToInsertAcademicBodyHeadDataToUserColumns.setString(2, academicBodyHead.getFirstSurname());
            preparedStatementToInsertAcademicBodyHeadDataToUserColumns.setString(3, academicBodyHead.getSecondSurname());
            preparedStatementToInsertAcademicBodyHeadDataToUserColumns.setString(4, academicBodyHead.getEmailAddress());
            preparedStatementToInsertAcademicBodyHeadDataToUserColumns.setString(5, academicBodyHead.getAlternateEmail());
            preparedStatementToInsertAcademicBodyHeadDataToUserColumns.setString(6, academicBodyHead.getPhoneNumber());
            preparedStatementToInsertAcademicBodyHeadDataToUserColumns.executeUpdate();

            String queryForAssignUserIdToAcademicBodyHead =
                "SELECT IdUsuario FROM Usuarios WHERE Nombre = ?";
            PreparedStatement preparedStatementForAssignUserIdToAcademicBodyHead = 
                dataBaseManager.getConnection().prepareStatement(queryForAssignUserIdToAcademicBodyHead);
            preparedStatementForAssignUserIdToAcademicBodyHead.setString(1, academicBodyHead.getName());
            ResultSet resultSetForAssignUserIdToAcademicBodyHead = 
                preparedStatementForAssignUserIdToAcademicBodyHead.executeQuery();
            if(resultSetForAssignUserIdToAcademicBodyHead.next()){
                academicBodyHead.setIdUser(resultSetForAssignUserIdToAcademicBodyHead.getInt("IdUsuario"));
            }

            String wholeQueryToInsertAcademicBodyHeadDataToProfessorsColumns = 
                "INSERT INTO Profesores ('NúmeroDePersonal', 'IdUsuario') VALUES (?, ?)";
            PreparedStatement preparedStatementToInsertAcademicBodyHeadDataToProfessorsColumns = 
                dataBaseManager.getConnection().prepareStatement(wholeQueryToInsertAcademicBodyHeadDataToProfessorsColumns);
            preparedStatementToInsertAcademicBodyHeadDataToProfessorsColumns.setString(1, academicBodyHead.getPersonalNumber());
            preparedStatementToInsertAcademicBodyHeadDataToProfessorsColumns.setInt(2, academicBodyHead.getIdUser());
            preparedStatementToInsertAcademicBodyHeadDataToProfessorsColumns.executeUpdate();

            String queryForAssignProfessorIdToAcademicBodyHead =
                "SELECT IdProfesor FROM Usuarios U INNER JOIN Profesores P ON U.IdUsuario = P.IdUsuario WHERE Nombre = ?";
            PreparedStatement preparedStatementForAssignProfessorIdToAcademicBodyHead = 
                dataBaseManager.getConnection().prepareStatement(queryForAssignProfessorIdToAcademicBodyHead);
            preparedStatementForAssignProfessorIdToAcademicBodyHead.setString(1, academicBodyHead.getName());
            ResultSet resultSetForAssignProfessorIdToAcademicBodyHead = 
                preparedStatementForAssignProfessorIdToAcademicBodyHead.executeQuery();
            if(resultSetForAssignProfessorIdToAcademicBodyHead.next()){
                academicBodyHead.setIdProfessor(resultSetForAssignProfessorIdToAcademicBodyHead.getInt("IdUsuario"));
            }

            String queryToInsertAcademicBodyHeadDataToAcademicBodyHeadColumns = 
                "INSERT INTO ResponsablesCA ('IdProfesor') VALUES (?)";
            PreparedStatement preparedStatementToInsertAcademicBodyHeadDataToAcademicBodyHeadColumns = 
                dataBaseManager.getConnection().prepareStatement(queryToInsertAcademicBodyHeadDataToAcademicBodyHeadColumns);
            preparedStatementToInsertAcademicBodyHeadDataToAcademicBodyHeadColumns.setInt(1, academicBodyHead.getIdProfessor());
            preparedStatementToInsertAcademicBodyHeadDataToAcademicBodyHeadColumns.executeUpdate();

            preparedStatementToInsertAcademicBodyHeadDataToAcademicBodyHeadColumns.close();
            dataBaseManager.getConnection().close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void modifyAcademicBodyHeadDataFromDatabase(AcademicBodyHead academicBodyHead) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'modifyAcademicBodyHeadDataFromDatabase'");
    }

    @Override
    public ArrayList<AcademicBodyHead> getAcademicBodyHeadsFromDatabase() {
        ArrayList<AcademicBodyHead> academicBodyHeads = new ArrayList<>();

        try {
            DataBaseManager dataBaseManager = new DataBaseManager();
            Statement statement = dataBaseManager.getConnection().createStatement();
            String query = "SELECT * FROM Usuarios U INNER JOIN Profesores P ON U.IdUsuario = P.IdUsuario INNER JOIN ResponsablesCA RCA ON P.IdProfesor = RCA.IdProfesor";
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()) {
                AcademicBodyHead academicBodyHead = new AcademicBodyHead();
                academicBodyHead.setName(resultSet.getString("nombre"));
                academicBodyHead.setFirstSurname(resultSet.getString("apellidoPaterno"));
                academicBodyHead.setSecondSurname(resultSet.getString("apellidoMaterno"));
                academicBodyHead.setEmailAddress(resultSet.getString("correo"));
                academicBodyHead.setPassword(resultSet.getString("contraseña"));
                academicBodyHead.setAlternateEmail(resultSet.getString("correoAlterno"));
                academicBodyHead.setPersonalNumber(resultSet.getString("NúmeroDePersonal"));
                academicBodyHeads.add(academicBodyHead);
            }
            resultSet.close();
            dataBaseManager.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return academicBodyHeads;
    }

    @Override
    public ArrayList<AcademicBodyHead> getSpecifiedAcademicBodyHeadsFromDatabase(String academicBodyHeadName) {
        ArrayList<AcademicBodyHead> academicBodyHeads = new ArrayList<>();

        try {
            DataBaseManager dataBaseManager = new DataBaseManager();
            String query = "SELECT * FROM Usuarios U INNER JOIN Profesores P ON U.IdUsuario = P.IdUsuario INNER JOIN ResponsablesCA RCA ON P.IdProfesor = RCA.IdProfesor WHERE U.Nombre LIKE ?";
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setString(1, academicBodyHeadName + '%');
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                AcademicBodyHead academicBodyHead = new AcademicBodyHead();
                academicBodyHead.setName(resultSet.getString("nombre"));
                academicBodyHead.setFirstSurname(resultSet.getString("apellidoPaterno"));
                academicBodyHead.setSecondSurname(resultSet.getString("apellidoMaterno"));
                academicBodyHead.setEmailAddress(resultSet.getString("correo"));
                academicBodyHead.setPassword(resultSet.getString("contraseña"));
                academicBodyHead.setAlternateEmail(resultSet.getString("correoAlterno"));
                academicBodyHead.setPersonalNumber(resultSet.getString("NúmeroDePersonal"));
                academicBodyHeads.add(academicBodyHead);
            }
            resultSet.close();
            dataBaseManager.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return academicBodyHeads;
    }

    @Override
    public AcademicBodyHead getAcademicBodyHeadFromDatabase(String academicBodyHeadName) {
        AcademicBodyHead academicBodyHead = new AcademicBodyHead();

        try {
            DataBaseManager dataBaseManager = new DataBaseManager();
            String query = "SELECT * FROM Usuarios U INNER JOIN Profesores P ON U.IdUsuario = P.IdUsuario INNER JOIN ResponsablesCA RCA ON P.IdProfesor = RCA.IdProfesor WHERE U.Nombre = ?";
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setString(1, academicBodyHeadName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                academicBodyHead.setName(resultSet.getString("nombre"));
                academicBodyHead.setFirstSurname(resultSet.getString("apellidoPaterno"));
                academicBodyHead.setSecondSurname(resultSet.getString("apellidoMaterno"));
                academicBodyHead.setEmailAddress(resultSet.getString("correo"));
                academicBodyHead.setPassword(resultSet.getString("contraseña"));
                academicBodyHead.setAlternateEmail(resultSet.getString("correoAlterno"));
                academicBodyHead.setPersonalNumber(resultSet.getString("NúmeroDePersonal"));
            }
            
            resultSet.close();
            dataBaseManager.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return academicBodyHead;
    }

    public boolean theAcademicBodyHeadIsAlreadyRegisted(AcademicBodyHead academicBodyHead) {
        try {
            DataBaseManager dataBaseManager = new DataBaseManager();
            Statement statement = dataBaseManager.getConnection().createStatement();
            String query = "SELECT NúmeroDePersonal FROM Profesores";
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()) {
                if(resultSet.getString("NúmeroDePersonal").equals(academicBodyHead.getPersonalNumber())) {
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
