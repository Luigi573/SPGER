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
import mx.uv.fei.logic.exceptions.DataInsertionException;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.exceptions.DuplicatedPrimaryKeyException;

public class DegreeBossDAO implements IDegreeBossDAO{
    private final DataBaseManager dataBaseManager;
    
    public DegreeBossDAO(){
        this.dataBaseManager = new DataBaseManager();
    }

    @Override
    public int addDegreeBoss(DegreeBoss degreeBoss) throws DataInsertionException, DuplicatedPrimaryKeyException{
        int generatedId = 0;
        try{
            String queryToInsertDegreeBossDataToUserColumns = 
                "INSERT INTO Usuarios (nombre, apellidoPaterno, apellidoMaterno, correo, correoAlterno, númeroTeléfono, estado) " + 
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatementToInsertDegreeBossDataToUserColumns = 
                dataBaseManager.getConnection().prepareStatement(
                    queryToInsertDegreeBossDataToUserColumns, PreparedStatement.RETURN_GENERATED_KEYS
                );
            preparedStatementToInsertDegreeBossDataToUserColumns.setString(1, degreeBoss.getName());
            preparedStatementToInsertDegreeBossDataToUserColumns.setString(2, degreeBoss.getFirstSurname());
            preparedStatementToInsertDegreeBossDataToUserColumns.setString(3, degreeBoss.getSecondSurname());
            preparedStatementToInsertDegreeBossDataToUserColumns.setString(4, degreeBoss.getEmailAddress());
            preparedStatementToInsertDegreeBossDataToUserColumns.setString(5, degreeBoss.getAlternateEmail());
            preparedStatementToInsertDegreeBossDataToUserColumns.setString(6, degreeBoss.getPhoneNumber());
            preparedStatementToInsertDegreeBossDataToUserColumns.setString(7, degreeBoss.getStatus());
            preparedStatementToInsertDegreeBossDataToUserColumns.executeUpdate();

            ResultSet resultSet = preparedStatementToInsertDegreeBossDataToUserColumns.getGeneratedKeys();
            if(resultSet.next()){
                generatedId = resultSet.getInt(1);
                degreeBoss.setUserId(generatedId);
            }

            String wholeQueryToInsertDegreeBossDataToProfessorsColumns = 
                "INSERT INTO Profesores (NumPersonal, IdUsuario) VALUES (?, ?)";
            PreparedStatement preparedStatementToInsertDegreeBossDataToProfessorsColumns = 
                dataBaseManager.getConnection().prepareStatement(wholeQueryToInsertDegreeBossDataToProfessorsColumns);
            preparedStatementToInsertDegreeBossDataToProfessorsColumns.setInt(1, degreeBoss.getStaffNumber());
            preparedStatementToInsertDegreeBossDataToProfessorsColumns.setInt(2, degreeBoss.getUserId());
            preparedStatementToInsertDegreeBossDataToProfessorsColumns.executeUpdate();


            String queryToInsertDegreeBossDataToDegreeBossColumns = 
                "INSERT INTO JefesCarrera (NumPersonal) VALUES (?)";
            PreparedStatement preparedStatementToInsertDegreeBossDataToDegreeBossColumns = 
                dataBaseManager.getConnection().prepareStatement(queryToInsertDegreeBossDataToDegreeBossColumns);
            preparedStatementToInsertDegreeBossDataToDegreeBossColumns.setInt(1, degreeBoss.getStaffNumber());
            preparedStatementToInsertDegreeBossDataToDegreeBossColumns.executeUpdate();

            preparedStatementToInsertDegreeBossDataToDegreeBossColumns.close();
            dataBaseManager.closeConnection();

        }catch(SQLIntegrityConstraintViolationException e){
            deleteDegreeBossFromUsersTable(degreeBoss);
            throw new DuplicatedPrimaryKeyException("Jefe de carrera ya registrado en el sistema");
        }catch(SQLException e){
            throw new DataInsertionException("Error al agregar jefe de carrera. Inténtelo de nuevo más tarde");
        }finally{
            dataBaseManager.closeConnection();
        }

        return generatedId;
    }

    @Override
    public int modifyDegreeBossData(DegreeBoss degreeBoss) throws DataInsertionException, DuplicatedPrimaryKeyException{
        int result = 0;
        try{
            String query = "UPDATE Usuarios SET nombre = ?, " + 
                           "apellidoPaterno = ?, apellidoMaterno = ?, correo = ?, " + 
                           "correoAlterno = ?, númeroTeléfono = ?, estado = ? " +
                           "WHERE IdUsuario = ?";
            
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setString(1, degreeBoss.getName());
            preparedStatement.setString(2, degreeBoss.getFirstSurname());
            preparedStatement.setString(3, degreeBoss.getSecondSurname());
            preparedStatement.setString(4, degreeBoss.getEmailAddress());
            preparedStatement.setString(5, degreeBoss.getAlternateEmail());
            preparedStatement.setString(6, degreeBoss.getPhoneNumber());
            preparedStatement.setString(7, degreeBoss.getStatus());
            preparedStatement.setInt(8, degreeBoss.getUserId());
            result = preparedStatement.executeUpdate();

            String queryForUpdateProfessorData = "UPDATE Profesores SET NumPersonal = ? " + 
                           "WHERE IdUsuario = ?";
            
            PreparedStatement preparedStatementForUpdateProfessorData = 
                dataBaseManager.getConnection().prepareStatement(queryForUpdateProfessorData);
            preparedStatementForUpdateProfessorData.setInt(1, degreeBoss.getStaffNumber());
            preparedStatementForUpdateProfessorData.setInt(2, degreeBoss.getUserId());
            preparedStatementForUpdateProfessorData.executeUpdate();
        }catch(SQLIntegrityConstraintViolationException e){
            throw new DuplicatedPrimaryKeyException("Jefe de carrera ya registrado en el sistema");
        }catch(SQLException e){
            throw new DataInsertionException("Error al modificar jefe de carrera. Inténtelo de nuevo más tarde");
        }finally{
            dataBaseManager.closeConnection();
        }
        return result;
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
                degreeBoss.setUserId(resultSet.getInt("IdUsuario"));
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
            dataBaseManager.closeConnection();
        }catch(SQLException e){
            throw new DataRetrievalException("Fallo al recuperar la informacion. Inténtelo de nuevo más tarde");
        }finally{
            dataBaseManager.closeConnection();
        }

        return degreeBosses;
    }

    @Override
    public ArrayList<DegreeBoss> getSpecifiedDegreeBosses(String degreeBossName) throws DataRetrievalException{
        ArrayList<DegreeBoss> degreeBosses = new ArrayList<>();
        String query = "SELECT * FROM Usuarios U INNER JOIN Profesores P ON U.IdUsuario = P.IdUsuario INNER JOIN JefesCarrera JC ON P.NumPersonal = JC.NumPersonal WHERE U.nombre LIKE ?";

        try{  
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setString(1, degreeBossName + '%');
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                DegreeBoss degreeBoss = new DegreeBoss();
                degreeBoss.setUserId(resultSet.getInt("IdUsuario"));
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
            
            dataBaseManager.closeConnection();
        }catch(SQLException exception){
            throw new DataRetrievalException("Fallo al recuperar la informacion. Inténtelo de nuevo más tarde");
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
                degreeBoss.setUserId(resultSet.getInt("IdUsuario"));
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
            
            dataBaseManager.closeConnection();
        }catch(SQLException exception){
            throw new DataRetrievalException("Fallo al recuperar la informacion. Inténtelo de nuevo más tarde");
        }finally{
            dataBaseManager.closeConnection();
        }

        return degreeBoss;
    }

    private void deleteDegreeBossFromUsersTable(DegreeBoss degreeBos) throws DataInsertionException{
        String queryToInsertUserData = "DELETE FROM Usuarios WHERE IdUsuario = ?";
        try{
            PreparedStatement preparedStatementToInsertUserData = 
            dataBaseManager.getConnection().prepareStatement(queryToInsertUserData);
            preparedStatementToInsertUserData.setInt(1, degreeBos.getUserId());
            preparedStatementToInsertUserData.executeUpdate();
        }catch(SQLException e){
            throw new DataInsertionException("Error al eliminar jefe de carrera de la tabla usuarios");
        }
    }
}