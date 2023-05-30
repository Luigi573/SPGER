package mx.uv.fei.logic.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;

import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.logic.daosinterfaces.IDegreeBossDAO;
import mx.uv.fei.logic.domain.DegreeBoss;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.exceptions.DataInsertionException;

public class DegreeBossDAO implements IDegreeBossDAO{
    private final DataBaseManager dataBaseManager;
    
    public DegreeBossDAO(){
        this.dataBaseManager = new DataBaseManager();
    }

    @Override
    public void addDegreeBoss (DegreeBoss degreeBoss) throws DataInsertionException{
        try{
            String wholeQueryToInsertDegreeBossDataToUserColumns = 
                "INSERT INTO Usuarios (nombre, apellidoPaterno, apellidoMaterno, correo, correoAlterno, númeroTeléfono, estado) " + 
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatementToInsertDegreeBossDataToUserColumns = 
                dataBaseManager.getConnection().prepareStatement(wholeQueryToInsertDegreeBossDataToUserColumns);
            preparedStatementToInsertDegreeBossDataToUserColumns.setString(1, degreeBoss.getName());
            preparedStatementToInsertDegreeBossDataToUserColumns.setString(2, degreeBoss.getFirstSurname());
            preparedStatementToInsertDegreeBossDataToUserColumns.setString(3, degreeBoss.getSecondSurname());
            preparedStatementToInsertDegreeBossDataToUserColumns.setString(4, degreeBoss.getEmailAddress());
            preparedStatementToInsertDegreeBossDataToUserColumns.setString(5, degreeBoss.getAlternateEmail());
            preparedStatementToInsertDegreeBossDataToUserColumns.setString(6, degreeBoss.getPhoneNumber());
            preparedStatementToInsertDegreeBossDataToUserColumns.setString(7, degreeBoss.getStatus());
            preparedStatementToInsertDegreeBossDataToUserColumns.executeUpdate();

            String queryForAssignUserIdToDegreeBoss =
                "SELECT IdUsuario FROM Usuarios WHERE nombre = ? && " +
                "apellidoPaterno = ? && apellidoMaterno = ? && correo = ? && " +
                "correoAlterno = ? && númeroTeléfono = ? && estado = ?";
            PreparedStatement preparedStatementForAssignUserIdToDegreeBoss =  
                dataBaseManager.getConnection().prepareStatement(queryForAssignUserIdToDegreeBoss);
            preparedStatementForAssignUserIdToDegreeBoss.setString(1, degreeBoss.getName());
            preparedStatementForAssignUserIdToDegreeBoss.setString(2, degreeBoss.getFirstSurname());
            preparedStatementForAssignUserIdToDegreeBoss.setString(3, degreeBoss.getSecondSurname());
            preparedStatementForAssignUserIdToDegreeBoss.setString(4, degreeBoss.getEmailAddress());
            preparedStatementForAssignUserIdToDegreeBoss.setString(5, degreeBoss.getAlternateEmail());
            preparedStatementForAssignUserIdToDegreeBoss.setString(6, degreeBoss.getPhoneNumber());
            preparedStatementForAssignUserIdToDegreeBoss.setString(7, degreeBoss.getStatus());
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
                "ON U.IdUsuario = P.IdUsuario WHERE U.nombre = ? && " +
                "U.apellidoPaterno = ? && U.apellidoMaterno = ? && U.correo = ? && " +
                "U.correoAlterno = ? && U.númeroTeléfono = ? && U.estado = ? && P.NumPersonal = ?";
            
            PreparedStatement preparedStatementForAssignProfessorIdToDegreeBoss = 
                dataBaseManager.getConnection().prepareStatement(queryForAssignProfessorIdToDegreeBoss);
            preparedStatementForAssignProfessorIdToDegreeBoss.setString(1, degreeBoss.getName());
            preparedStatementForAssignProfessorIdToDegreeBoss.setString(2, degreeBoss.getFirstSurname());
            preparedStatementForAssignProfessorIdToDegreeBoss.setString(3, degreeBoss.getSecondSurname());
            preparedStatementForAssignProfessorIdToDegreeBoss.setString(4, degreeBoss.getEmailAddress());
            preparedStatementForAssignProfessorIdToDegreeBoss.setString(5, degreeBoss.getAlternateEmail());
            preparedStatementForAssignProfessorIdToDegreeBoss.setString(6, degreeBoss.getPhoneNumber());
            preparedStatementForAssignProfessorIdToDegreeBoss.setString(7, degreeBoss.getStatus());
            preparedStatementForAssignProfessorIdToDegreeBoss.setInt(8, degreeBoss.getStaffNumber());
            
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

        }catch(SQLIntegrityConstraintViolationException e){
            deleteDegreeBossFromUsersTable(degreeBoss);
            throw new DataInsertionException("Error al agregar estudiante. Verifique su conexion e intentelo de nuevo");
        }catch(SQLException e){
            e.printStackTrace();
            throw new DataInsertionException("Error al agregar jefe de carrera. Verifique su conexion e intentelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }
    }

    @Override
    public void modifyDegreeBossData(DegreeBoss newDegreeBossData, DegreeBoss originalDegreeBossData) throws DataInsertionException{
        try{
            String query = "UPDATE Usuarios SET nombre = ?, " + 
                           "apellidoPaterno = ?, apellidoMaterno = ?, correo = ?, " + 
                           "correoAlterno = ?, númeroTeléfono = ?, estado = ? " +
                           "WHERE nombre = ? && apellidoPaterno = ? && apellidoMaterno = ? && " + 
                           "correo = ? && correoAlterno = ? && númeroTeléfono = ? && estado = ?";
            
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setString(1, newDegreeBossData.getName());
            preparedStatement.setString(2, newDegreeBossData.getFirstSurname());
            preparedStatement.setString(3, newDegreeBossData.getSecondSurname());
            preparedStatement.setString(4, newDegreeBossData.getEmailAddress());
            preparedStatement.setString(5, newDegreeBossData.getAlternateEmail());
            preparedStatement.setString(6, newDegreeBossData.getPhoneNumber());
            preparedStatement.setString(7, newDegreeBossData.getStatus());
            preparedStatement.setString(8, originalDegreeBossData.getName());
            preparedStatement.setString(9, originalDegreeBossData.getFirstSurname());
            preparedStatement.setString(10, originalDegreeBossData.getSecondSurname());
            preparedStatement.setString(11, originalDegreeBossData.getEmailAddress());
            preparedStatement.setString(12, originalDegreeBossData.getAlternateEmail());
            preparedStatement.setString(13, originalDegreeBossData.getPhoneNumber());
            preparedStatement.setString(14, originalDegreeBossData.getStatus());
            preparedStatement.executeUpdate();

            String queryForUpdateProfessorData = "UPDATE Profesores SET NumPersonal = ? " + 
                           "WHERE IdUsuario = ?";
            
            PreparedStatement preparedStatementForUpdateProfessorData = 
                dataBaseManager.getConnection().prepareStatement(queryForUpdateProfessorData);
            preparedStatementForUpdateProfessorData.setInt(1, newDegreeBossData.getStaffNumber());
            preparedStatementForUpdateProfessorData.setInt(2, newDegreeBossData.getUserId());
            preparedStatementForUpdateProfessorData.executeUpdate();

            String queryForUpdateDegreeBossData = "UPDATE JefesCarrera SET NumPersonal = ? " + 
                           "WHERE NumPersonal = ?";
            
            PreparedStatement preparedStatementForUpdateDegreeBossData = 
                dataBaseManager.getConnection().prepareStatement(queryForUpdateDegreeBossData);
            preparedStatementForUpdateDegreeBossData.setInt(1, newDegreeBossData.getStaffNumber());
            preparedStatementForUpdateDegreeBossData.setInt(2, originalDegreeBossData.getStaffNumber());
            preparedStatementForUpdateDegreeBossData.executeUpdate();
        }catch(SQLException e){
            throw new DataInsertionException("Error al agregar jefe de carrera. Verifique su conexion e intentelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }
    }

    @Override
    public ArrayList<DegreeBoss> getDegreeBosses() throws DataRetrievalException{
        ArrayList<DegreeBoss> degreeBosses = new ArrayList<>();

        try{
            Statement statement = dataBaseManager.getConnection().createStatement();
            String query = "SELECT * FROM Usuarios U INNER JOIN Profesores P ON U.IdUsuario = P.IdUsuario "
                            + "INNER JOIN JefesCarrera JC ON P.NumPersonal = JC.NumPersonal";
            
            ResultSet resultSet = statement.executeQuery(query);
            
            while(resultSet.next()){
                DegreeBoss degreeBoss = new DegreeBoss();
                degreeBoss.setName(resultSet.getString("nombre"));
                degreeBoss.setFirstSurname(resultSet.getString("apellidoPaterno"));
                degreeBoss.setSecondSurname(resultSet.getString("apellidoMaterno"));
                degreeBoss.setEmailAddress(resultSet.getString("correo"));
                degreeBoss.setPassword(resultSet.getString("contraseña"));
                degreeBoss.setAlternateEmail(resultSet.getString("correoAlterno"));
                degreeBoss.setPhoneNumber(resultSet.getString("númeroTeléfono"));
                degreeBoss.setStatus(resultSet.getString("estado"));
                degreeBoss.setStaffNumber(resultSet.getInt("NumPersonal"));
                degreeBosses.add(degreeBoss);
            }
            
            resultSet.close();
            dataBaseManager.getConnection().close();
        }catch(SQLException e){
            throw new DataRetrievalException("Fallo al recuperar la informacion. Verifique su conexion e intentelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }

        return degreeBosses;
    }

    @Override
    public ArrayList<DegreeBoss> getSpecifiedDegreeBosses(String degreeBossName) throws DataRetrievalException{
        ArrayList<DegreeBoss> degreeBosses = new ArrayList<>();
        String query = "SELECT * FROM Usuarios U INNER JOIN Profesores P ON U.IdUsuario = P.IdUsuario INNER JOIN JefesCarrera JC ON P.NumPersonal = JC.NumPersonal WHERE U.Nombre LIKE ?";

        try{  
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setString(1, degreeBossName + '%');
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                DegreeBoss degreeBoss = new DegreeBoss();
                degreeBoss.setName(resultSet.getString("nombre"));
                degreeBoss.setFirstSurname(resultSet.getString("apellidoPaterno"));
                degreeBoss.setSecondSurname(resultSet.getString("apellidoMaterno"));
                degreeBoss.setEmailAddress(resultSet.getString("correo"));
                degreeBoss.setPassword(resultSet.getString("contraseña"));
                degreeBoss.setAlternateEmail(resultSet.getString("correoAlterno"));
                degreeBoss.setPhoneNumber(resultSet.getString("númeroTeléfono"));
                degreeBoss.setStatus(resultSet.getString("estado"));
                degreeBoss.setStaffNumber(resultSet.getInt("NumPersonal"));
                degreeBosses.add(degreeBoss);
            }
            
            dataBaseManager.getConnection().close();
        }catch(SQLException exception){
            exception.printStackTrace();
            throw new DataRetrievalException("Fallo al recuperar la informacion. Verifique su conexion e intentelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }

        return degreeBosses;
    }

    @Override
    public DegreeBoss getDegreeBoss(int staffNumber) throws DataRetrievalException{
        DegreeBoss degreeBoss = new DegreeBoss();

        try{
            String query = "SELECT * FROM Usuarios U INNER JOIN Profesores P ON U.IdUsuario = P.IdUsuario INNER JOIN JefesCarrera JC ON P.NumPersonal = JC.NumPersonal WHERE JC.NumPersonal = ?";
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, staffNumber);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            
            if(resultSet.next()){
                degreeBoss.setName(resultSet.getString("nombre"));
                degreeBoss.setFirstSurname(resultSet.getString("apellidoPaterno"));
                degreeBoss.setSecondSurname(resultSet.getString("apellidoMaterno"));
                degreeBoss.setEmailAddress(resultSet.getString("correo"));
                degreeBoss.setPassword(resultSet.getString("contraseña"));
                degreeBoss.setAlternateEmail(resultSet.getString("correoAlterno"));
                degreeBoss.setPhoneNumber(resultSet.getString("númeroTeléfono"));
                degreeBoss.setStatus(resultSet.getString("estado"));
                degreeBoss.setStaffNumber(resultSet.getInt("NumPersonal"));
            }
            
            dataBaseManager.getConnection().close();
        }catch(SQLException exception){
            exception.printStackTrace();
            throw new DataRetrievalException("Fallo al recuperar la informacion. Verifique su conexion e intentelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }

        return degreeBoss;
    }

    public boolean theDegreeBossIsAlreadyRegisted(DegreeBoss degreeBoss) throws DataRetrievalException{
        PreparedStatement statement;
        String query = "SELECT U.nombre, U.apellidoPaterno, U.apellidoMaterno, U.correo, " +
                           "U.correoAlterno, U.númeroTeléfono, U.estado, P.NumPersonal FROM Usuarios U " + 
                           "INNER JOIN Profesores P ON U.IdUsuario = P.IdUsuario INNER JOIN JefesCarrera JC " +
                           "ON P.NumPersonal = JC.NumPersonal";
        
        try{
            statement = dataBaseManager.getConnection().prepareStatement(query);
            ResultSet resultSet = statement.executeQuery(query);
            
            while(resultSet.next()) {
                if(resultSet.getString("nombre").equals(degreeBoss.getName()) &&
                   resultSet.getString("apellidoPaterno").equals(degreeBoss.getFirstSurname()) &&
                   resultSet.getString("apellidoMaterno").equals(degreeBoss.getSecondSurname()) &&
                   resultSet.getString("correo").equals(degreeBoss.getEmailAddress()) &&
                   resultSet.getString("correoAlterno").equals(degreeBoss.getAlternateEmail()) &&
                   resultSet.getString("númeroTeléfono").equals(degreeBoss.getPhoneNumber()) &&
                   resultSet.getString("estado").equals(degreeBoss.getStatus()) &&
                   resultSet.getInt("NumPersonal") == degreeBoss.getStaffNumber()) {

                    resultSet.close();
                    dataBaseManager.getConnection().close();
                    return true;
                }
            }
            statement.close();
        }catch(SQLException exception){
            exception.printStackTrace();
            throw new DataRetrievalException("Fallo al recuperar la informacion. Verifique su conexion e intentelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }

        return false;
    }
    private void deleteDegreeBossFromUsersTable(DegreeBoss degreeBoss) throws DataInsertionException{
        String queryToInsertUserData = "DELETE FROM Usuarios WHERE nombre = ? && apellidoPaterno = ? && apellidoMaterno = ? && " +
            "correo = ? && correoAlterno = ? && númeroTeléfono = ? && estado = ?";
        try{
            PreparedStatement preparedStatementToInsertUserData = 
            dataBaseManager.getConnection().prepareStatement(queryToInsertUserData);
            preparedStatementToInsertUserData.setString(1, degreeBoss.getName());
            preparedStatementToInsertUserData.setString(2, degreeBoss.getFirstSurname());
            preparedStatementToInsertUserData.setString(3, degreeBoss.getSecondSurname());
            preparedStatementToInsertUserData.setString(4, degreeBoss.getEmailAddress());
            preparedStatementToInsertUserData.setString(5, degreeBoss.getAlternateEmail());
            preparedStatementToInsertUserData.setString(6, degreeBoss.getPhoneNumber());
            preparedStatementToInsertUserData.setString(7, degreeBoss.getStatus());
            preparedStatementToInsertUserData.executeUpdate();
        }catch(SQLException e){
            throw new DataInsertionException("Error al eliminar profesor de la tabla usuarios");
        }
    }
}