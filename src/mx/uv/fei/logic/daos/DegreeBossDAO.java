package mx.uv.fei.logic.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.logic.daosinterfaces.IDegreeBossDAO;
import mx.uv.fei.logic.domain.DegreeBoss;

public class DegreeBossDAO implements IDegreeBossDAO{
    private DataBaseManager dataBaseManager;
    
    public DegreeBossDAO(){
        
    }

    @Override
    public void addDegreeBossToDatabase(DegreeBoss degreeBoss) {
        try {
            String wholeQueryToInsertDegreeBossDataToUserColumns = 
                "INSERT INTO Usuarios (nombre, apellidoPaterno, apellidoMaterno, correo, correoAlterno, númeroTeléfono) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatementToInsertDegreeBossDataToUserColumns = 
                dataBaseManager.getConnection().prepareStatement(wholeQueryToInsertDegreeBossDataToUserColumns);
            preparedStatementToInsertDegreeBossDataToUserColumns.setString(1, degreeBoss.getName());
            preparedStatementToInsertDegreeBossDataToUserColumns.setString(2, degreeBoss.getFirstSurname());
            preparedStatementToInsertDegreeBossDataToUserColumns.setString(3, degreeBoss.getSecondSurname());
            preparedStatementToInsertDegreeBossDataToUserColumns.setString(4, degreeBoss.getEmailAddress());
            preparedStatementToInsertDegreeBossDataToUserColumns.setString(5, degreeBoss.getAlternateEmail());
            preparedStatementToInsertDegreeBossDataToUserColumns.setString(6, degreeBoss.getPhoneNumber());
            preparedStatementToInsertDegreeBossDataToUserColumns.executeUpdate();

            String queryForAssignUserIdToDegreeBoss =
                "SELECT IdUsuario FROM Usuarios WHERE nombre = ? && " +
                "apellidoPaterno = ? && apellidoMaterno = ? && correo = ? && " +
                "correoAlterno = ? && númeroTeléfono = ?";
            PreparedStatement preparedStatementForAssignUserIdToDegreeBoss =  
                dataBaseManager.getConnection().prepareStatement(queryForAssignUserIdToDegreeBoss);
            preparedStatementForAssignUserIdToDegreeBoss.setString(1, degreeBoss.getName());
            preparedStatementForAssignUserIdToDegreeBoss.setString(2, degreeBoss.getFirstSurname());
            preparedStatementForAssignUserIdToDegreeBoss.setString(3, degreeBoss.getSecondSurname());
            preparedStatementForAssignUserIdToDegreeBoss.setString(4, degreeBoss.getEmailAddress());
            preparedStatementForAssignUserIdToDegreeBoss.setString(5, degreeBoss.getAlternateEmail());
            preparedStatementForAssignUserIdToDegreeBoss.setString(6, degreeBoss.getPhoneNumber());
            ResultSet resultSetForAssignUserIdToDegreeBoss = 
                preparedStatementForAssignUserIdToDegreeBoss.executeQuery();
            if(resultSetForAssignUserIdToDegreeBoss.next()){
                degreeBoss.setUserId(resultSetForAssignUserIdToDegreeBoss.getInt("IdUsuario"));
            }

            String wholeQueryToInsertDegreeBossDataToProfessorsColumns = 
                "INSERT INTO Profesores (NumPersonal, IdUsuario) VALUES (?, ?)";
            PreparedStatement preparedStatementToInsertDegreeBossDataToProfessorsColumns = 
                dataBaseManager.getConnection().prepareStatement(wholeQueryToInsertDegreeBossDataToProfessorsColumns);
            preparedStatementToInsertDegreeBossDataToProfessorsColumns.setInt(1, degreeBoss.getStaffNumber());
            preparedStatementToInsertDegreeBossDataToProfessorsColumns.setInt(2, degreeBoss.getUserId());
            preparedStatementToInsertDegreeBossDataToProfessorsColumns.executeUpdate();

            String queryForAssignProfessorIdToDegreeBoss =
                "SELECT NumPersonal FROM Usuarios U INNER JOIN Profesores P " +
                "ON U.IdUsuario = P.IdUsuario WHERE nombre = ? && " +
                "apellidoPaterno = ? && apellidoMaterno = ? && correo = ? && " +
                "correoAlterno = ? && númeroTeléfono = ? && NumPersonal = ?";
            
            PreparedStatement preparedStatementForAssignProfessorIdToDegreeBoss = 
                dataBaseManager.getConnection().prepareStatement(queryForAssignProfessorIdToDegreeBoss);
            preparedStatementForAssignProfessorIdToDegreeBoss.setString(1, degreeBoss.getName());
            preparedStatementForAssignProfessorIdToDegreeBoss.setString(2, degreeBoss.getFirstSurname());
            preparedStatementForAssignProfessorIdToDegreeBoss.setString(3, degreeBoss.getSecondSurname());
            preparedStatementForAssignProfessorIdToDegreeBoss.setString(4, degreeBoss.getEmailAddress());
            preparedStatementForAssignProfessorIdToDegreeBoss.setString(5, degreeBoss.getAlternateEmail());
            preparedStatementForAssignProfessorIdToDegreeBoss.setString(6, degreeBoss.getPhoneNumber());
            preparedStatementForAssignProfessorIdToDegreeBoss.setInt(7, degreeBoss.getStaffNumber());
            
            ResultSet resultSetForAssignProfessorIdToDegreeBoss = 
                preparedStatementForAssignProfessorIdToDegreeBoss.executeQuery();
            
            if(resultSetForAssignProfessorIdToDegreeBoss.next()){
                degreeBoss.setStaffNumber(resultSetForAssignProfessorIdToDegreeBoss.getInt("NumPersonal"));
            }

            String queryToInsertDegreeBossDataToDegreeBossColumns = 
                "INSERT INTO JefesCarrera (NumPersonal) VALUES (?)";
            PreparedStatement preparedStatementToInsertDegreeBossDataToDegreeBossColumns = 
                dataBaseManager.getConnection().prepareStatement(queryToInsertDegreeBossDataToDegreeBossColumns);
            preparedStatementToInsertDegreeBossDataToDegreeBossColumns.setInt(1, degreeBoss.getStaffNumber());
            preparedStatementToInsertDegreeBossDataToDegreeBossColumns.executeUpdate();

            preparedStatementToInsertDegreeBossDataToDegreeBossColumns.close();
            dataBaseManager.getConnection().close();

        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            
        }
    }

    @Override
    public void modifyDegreeBossDataFromDatabase(DegreeBoss newDegreeBossData, DegreeBoss originalDegreeBossData) {
        try {
            DataBaseManager dataBaseManager = new DataBaseManager();
            String query = "UPDATE Usuarios SET nombre = ?, " + 
                           "apellidoPaterno = ?, apellidoMaterno = ?, correo = ?, " + 
                           "correoAlterno = ?, númeroTeléfono = ? " +
                           "WHERE nombre = ? && apellidoPaterno = ? && apellidoMaterno = ? && " + 
                           "correo = ? && correoAlterno = ? && númeroTeléfono = ?";
            
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setString(1, newDegreeBossData.getName());
            preparedStatement.setString(2, newDegreeBossData.getFirstSurname());
            preparedStatement.setString(3, newDegreeBossData.getSecondSurname());
            preparedStatement.setString(4, newDegreeBossData.getEmailAddress());
            preparedStatement.setString(5, newDegreeBossData.getAlternateEmail());
            preparedStatement.setString(6, newDegreeBossData.getPhoneNumber());
            preparedStatement.setString(7, originalDegreeBossData.getName());
            preparedStatement.setString(8, originalDegreeBossData.getFirstSurname());
            preparedStatement.setString(9, originalDegreeBossData.getSecondSurname());
            preparedStatement.setString(10, originalDegreeBossData.getEmailAddress());
            preparedStatement.setString(11, originalDegreeBossData.getAlternateEmail());
            preparedStatement.setString(12, originalDegreeBossData.getPhoneNumber());
            preparedStatement.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<DegreeBoss> getDegreeBossesFromDatabase() {
        ArrayList<DegreeBoss> degreeBosses = new ArrayList<>();

        try {
            DataBaseManager dataBaseManager = new DataBaseManager();
            Statement statement = dataBaseManager.getConnection().createStatement();
            String query = "SELECT * FROM Usuarios U INNER JOIN Profesores P ON U.IdUsuario = P.IdUsuario "
                            + "INNER JOIN JefesCarrera JC ON P.NumPersonal = JC.NumPersonal";
            
            ResultSet resultSet = statement.executeQuery(query);
            
            while(resultSet.next()) {
                DegreeBoss degreeBoss = new DegreeBoss();
                degreeBoss.setName(resultSet.getString("nombre"));
                degreeBoss.setFirstSurname(resultSet.getString("apellidoPaterno"));
                degreeBoss.setSecondSurname(resultSet.getString("apellidoMaterno"));
                degreeBoss.setEmailAddress(resultSet.getString("correo"));
                degreeBoss.setPassword(resultSet.getString("contraseña"));
                degreeBoss.setAlternateEmail(resultSet.getString("correoAlterno"));
                degreeBoss.setPhoneNumber(resultSet.getString("númeroTeléfono"));
                degreeBoss.setStaffNumber(resultSet.getInt("NumPersonal"));
                degreeBosses.add(degreeBoss);
            }
            
            resultSet.close();
            dataBaseManager.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return degreeBosses;
    }

    @Override
    public ArrayList<DegreeBoss> getSpecifiedDegreeBossesFromDatabase(String degreeBossName) {
        ArrayList<DegreeBoss> degreeBosses = new ArrayList<>();
        String query = "SELECT * FROM Usuarios U INNER JOIN Profesores P ON U.IdUsuario = P.IdUsuario INNER JOIN JefesCarrera JC ON P.NumPersonal = JC.NumPersonal WHERE U.Nombre LIKE ?";

        try {
            
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setString(1, degreeBossName + '%');
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                DegreeBoss degreeBoss = new DegreeBoss();
                degreeBoss.setName(resultSet.getString("nombre"));
                degreeBoss.setFirstSurname(resultSet.getString("apellidoPaterno"));
                degreeBoss.setSecondSurname(resultSet.getString("apellidoMaterno"));
                degreeBoss.setEmailAddress(resultSet.getString("correo"));
                degreeBoss.setPassword(resultSet.getString("contraseña"));
                degreeBoss.setAlternateEmail(resultSet.getString("correoAlterno"));
                degreeBoss.setPhoneNumber(resultSet.getString("númeroTeléfono"));
                degreeBoss.setStaffNumber(resultSet.getInt("NumPersonal"));
                degreeBosses.add(degreeBoss);
            }
            
            dataBaseManager.getConnection().close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return degreeBosses;
    }

    @Override
    public DegreeBoss getDegreeBossFromDatabase(int personalNumber) {
        DegreeBoss degreeBoss = new DegreeBoss();

        try {
            DataBaseManager dataBaseManager = new DataBaseManager();
            String query = "SELECT * FROM Usuarios U INNER JOIN Profesores P ON U.IdUsuario = P.IdUsuario INNER JOIN JefesCarrera JC ON P.NumPersonal = JC.NumPersonal WHERE JC.NumPersonal = ?";
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, personalNumber);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            
            if(resultSet.next()) {
                degreeBoss.setName(resultSet.getString("nombre"));
                degreeBoss.setFirstSurname(resultSet.getString("apellidoPaterno"));
                degreeBoss.setSecondSurname(resultSet.getString("apellidoMaterno"));
                degreeBoss.setEmailAddress(resultSet.getString("correo"));
                degreeBoss.setPassword(resultSet.getString("contraseña"));
                degreeBoss.setAlternateEmail(resultSet.getString("correoAlterno"));
                degreeBoss.setPhoneNumber(resultSet.getString("númeroTeléfono"));
                degreeBoss.setStaffNumber(resultSet.getInt("NumPersonal"));
            }
            
            dataBaseManager.getConnection().close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return degreeBoss;
    }

    public boolean theDegreeBossIsAlreadyRegisted(int personalNumber) {
        PreparedStatement statement;
        String query = "SELECT NumPersonal FROM Profesores";
        
        try {
            statement = dataBaseManager.getConnection().prepareStatement(query);
            ResultSet resultSet = statement.executeQuery(query);
            
            while(resultSet.next()) {
                if(resultSet.getInt("NumPersonal") == personalNumber) {
                    resultSet.close();
                    dataBaseManager.getConnection().close();
                    return true;
                }
            }
            
            statement.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }finally{
            
        }

        return false;
    }
    
}
