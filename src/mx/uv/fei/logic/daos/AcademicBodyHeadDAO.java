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
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.exceptions.DataInsertionException;

public class AcademicBodyHeadDAO implements IAcademicBodyHeadDAO{
    private final DataBaseManager dataBaseManager;
    
    public AcademicBodyHeadDAO(){
        dataBaseManager = new DataBaseManager();
    }
    
    @Override
    public void addAcademicBodyHead (AcademicBodyHead academicBodyHead) throws DataInsertionException{
        try{
            String wholeQueryToInsertAcademicBodyHeadDataToUserColumns = 
                "INSERT INTO Usuarios (nombre, apellidoPaterno, apellidoMaterno, correo, correoAlterno, númeroTeléfono, estado) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatementToInsertAcademicBodyHeadDataToUserColumns = 
                dataBaseManager.getConnection().prepareStatement(wholeQueryToInsertAcademicBodyHeadDataToUserColumns);
            preparedStatementToInsertAcademicBodyHeadDataToUserColumns.setString(1, academicBodyHead.getName());
            preparedStatementToInsertAcademicBodyHeadDataToUserColumns.setString(2, academicBodyHead.getFirstSurname());
            preparedStatementToInsertAcademicBodyHeadDataToUserColumns.setString(3, academicBodyHead.getSecondSurname());
            preparedStatementToInsertAcademicBodyHeadDataToUserColumns.setString(4, academicBodyHead.getEmailAddress());
            preparedStatementToInsertAcademicBodyHeadDataToUserColumns.setString(5, academicBodyHead.getAlternateEmail());
            preparedStatementToInsertAcademicBodyHeadDataToUserColumns.setString(6, academicBodyHead.getPhoneNumber());
            preparedStatementToInsertAcademicBodyHeadDataToUserColumns.setString(7, academicBodyHead.getStatus());
            preparedStatementToInsertAcademicBodyHeadDataToUserColumns.executeUpdate();

            String queryForAssignUserIdToAcademicBodyHead =
                "SELECT IdUsuario FROM Usuarios WHERE nombre = ? && " +
                "apellidoPaterno = ? && apellidoMaterno = ? && correo = ? && " +
                "correoAlterno = ? && númeroTeléfono = ? && estado = ?";
            PreparedStatement preparedStatementForAssignUserIdToAcademicBodyHead = 
                dataBaseManager.getConnection().prepareStatement(queryForAssignUserIdToAcademicBodyHead);
            preparedStatementForAssignUserIdToAcademicBodyHead.setString(1, academicBodyHead.getName());
            preparedStatementForAssignUserIdToAcademicBodyHead.setString(2, academicBodyHead.getFirstSurname());
            preparedStatementForAssignUserIdToAcademicBodyHead.setString(3, academicBodyHead.getSecondSurname());
            preparedStatementForAssignUserIdToAcademicBodyHead.setString(4, academicBodyHead.getEmailAddress());
            preparedStatementForAssignUserIdToAcademicBodyHead.setString(5, academicBodyHead.getAlternateEmail());
            preparedStatementForAssignUserIdToAcademicBodyHead.setString(6, academicBodyHead.getPhoneNumber());
            preparedStatementForAssignUserIdToAcademicBodyHead.setString(7, academicBodyHead.getStatus());
            ResultSet resultSetForAssignUserIdToAcademicBodyHead = 
                preparedStatementForAssignUserIdToAcademicBodyHead.executeQuery();
            if(resultSetForAssignUserIdToAcademicBodyHead.next()){
                academicBodyHead.setUserId(resultSetForAssignUserIdToAcademicBodyHead.getInt("IdUsuario"));
            }

            String wholeQueryToInsertAcademicBodyHeadDataToProfessorsColumns = 
                "INSERT INTO Profesores (NumPersonal, IdUsuario) VALUES (?, ?)";
            PreparedStatement preparedStatementToInsertAcademicBodyHeadDataToProfessorsColumns = 
                dataBaseManager.getConnection().prepareStatement(wholeQueryToInsertAcademicBodyHeadDataToProfessorsColumns);
            preparedStatementToInsertAcademicBodyHeadDataToProfessorsColumns.setInt(1, academicBodyHead.getStaffNumber());
            preparedStatementToInsertAcademicBodyHeadDataToProfessorsColumns.setInt(2, academicBodyHead.getUserId());
            preparedStatementToInsertAcademicBodyHeadDataToProfessorsColumns.executeUpdate();

            String queryForAssignProfessorIdToAcademicBodyHead =
                "SELECT NumPersonal FROM Usuarios U INNER JOIN Profesores P " +
                "ON U.IdUsuario = P.IdUsuario WHERE nombre = ? && " +
                "apellidoPaterno = ? && apellidoMaterno = ? && correo = ? && " +
                "correoAlterno = ? && númeroTeléfono = ? && estado = ? && NumPersonal = ?";
            PreparedStatement preparedStatementForAssignProfessorIdToAcademicBodyHead = 
                dataBaseManager.getConnection().prepareStatement(queryForAssignProfessorIdToAcademicBodyHead);
            preparedStatementForAssignProfessorIdToAcademicBodyHead.setString(1, academicBodyHead.getName());
            preparedStatementForAssignProfessorIdToAcademicBodyHead.setString(2, academicBodyHead.getFirstSurname());
            preparedStatementForAssignProfessorIdToAcademicBodyHead.setString(3, academicBodyHead.getSecondSurname());
            preparedStatementForAssignProfessorIdToAcademicBodyHead.setString(4, academicBodyHead.getEmailAddress());
            preparedStatementForAssignProfessorIdToAcademicBodyHead.setString(5, academicBodyHead.getAlternateEmail());
            preparedStatementForAssignProfessorIdToAcademicBodyHead.setString(6, academicBodyHead.getPhoneNumber());
            preparedStatementForAssignProfessorIdToAcademicBodyHead.setString(7, academicBodyHead.getStatus());
            preparedStatementForAssignProfessorIdToAcademicBodyHead.setInt(8, academicBodyHead.getStaffNumber());
            ResultSet resultSetForAssignProfessorIdToAcademicBodyHead = 
                preparedStatementForAssignProfessorIdToAcademicBodyHead.executeQuery();
            if(resultSetForAssignProfessorIdToAcademicBodyHead.next()){
                academicBodyHead.setStaffNumber(resultSetForAssignProfessorIdToAcademicBodyHead.getInt("NumPersonal"));
            }

            String queryToInsertAcademicBodyHeadDataToAcademicBodyHeadColumns = 
                "INSERT INTO ResponsablesCA (NumPersonal) VALUES (?)";
            PreparedStatement preparedStatementToInsertAcademicBodyHeadDataToAcademicBodyHeadColumns = 
                dataBaseManager.getConnection().prepareStatement(queryToInsertAcademicBodyHeadDataToAcademicBodyHeadColumns);
            preparedStatementToInsertAcademicBodyHeadDataToAcademicBodyHeadColumns.setInt(1, academicBodyHead.getStaffNumber());
            preparedStatementToInsertAcademicBodyHeadDataToAcademicBodyHeadColumns.executeUpdate();

            preparedStatementToInsertAcademicBodyHeadDataToAcademicBodyHeadColumns.close();
            dataBaseManager.getConnection().close();

        }catch(SQLIntegrityConstraintViolationException e){
            deleteAcademicBodyHeadFromUsersTable(academicBodyHead);
            throw new DataInsertionException("Error al agregar estudiante. Verifique su conexion e intentelo de nuevo");
        }catch(SQLException e){
            e.printStackTrace();
            throw new DataInsertionException("Error al agregar miembro del cuerpo académico. Verifique su conexion e intentelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }
    }

    @Override
    public void modifyAcademicBodyHeadData(AcademicBodyHead newAcademicBodyHeadData, AcademicBodyHead originalAcademicBodyHeadData) throws DataInsertionException{
        try{
            String queryForUpdateUserData = "UPDATE Usuarios SET nombre = ?, " + 
                           "apellidoPaterno = ?, apellidoMaterno = ?, correo = ?, " + 
                           "correoAlterno = ?, númeroTeléfono = ?, estado = ? " +
                           "WHERE nombre = ? && apellidoPaterno = ? && apellidoMaterno = ? && " + 
                           "correo = ? && correoAlterno = ? && númeroTeléfono = ? && estado = ?";
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(queryForUpdateUserData);
            preparedStatement.setString(1, newAcademicBodyHeadData.getName());
            preparedStatement.setString(2, newAcademicBodyHeadData.getFirstSurname());
            preparedStatement.setString(3, newAcademicBodyHeadData.getSecondSurname());
            preparedStatement.setString(4, newAcademicBodyHeadData.getEmailAddress());
            preparedStatement.setString(5, newAcademicBodyHeadData.getAlternateEmail());
            preparedStatement.setString(6, newAcademicBodyHeadData.getPhoneNumber());
            preparedStatement.setString(7, newAcademicBodyHeadData.getStatus());
            preparedStatement.setString(8, originalAcademicBodyHeadData.getName());
            preparedStatement.setString(9, originalAcademicBodyHeadData.getFirstSurname());
            preparedStatement.setString(10, originalAcademicBodyHeadData.getSecondSurname());
            preparedStatement.setString(11, originalAcademicBodyHeadData.getEmailAddress());
            preparedStatement.setString(12, originalAcademicBodyHeadData.getAlternateEmail());
            preparedStatement.setString(13, originalAcademicBodyHeadData.getPhoneNumber());
            preparedStatement.setString(14, originalAcademicBodyHeadData.getStatus());
            preparedStatement.executeUpdate();

            String queryForUpdateProfessorData = "UPDATE Profesores SET NumPersonal = ? " + 
                           "WHERE IdUsuario = ?";
            
            PreparedStatement preparedStatementForUpdateProfessorData = 
                dataBaseManager.getConnection().prepareStatement(queryForUpdateProfessorData);
            preparedStatementForUpdateProfessorData.setInt(1, newAcademicBodyHeadData.getStaffNumber());
            preparedStatementForUpdateProfessorData.setInt(2, newAcademicBodyHeadData.getUserId());
            preparedStatementForUpdateProfessorData.executeUpdate();

            String queryForUpdateAcademicBodyHeadData = "UPDATE ResponsablesCA SET NumPersonal = ? " + 
                           "WHERE NumPersonal = ?";
            
            PreparedStatement preparedStatementForUpdateAcademicBodyHeadData = 
                dataBaseManager.getConnection().prepareStatement(queryForUpdateAcademicBodyHeadData);
            preparedStatementForUpdateAcademicBodyHeadData.setInt(1, newAcademicBodyHeadData.getStaffNumber());
            preparedStatementForUpdateAcademicBodyHeadData.setInt(2, originalAcademicBodyHeadData.getStaffNumber());
            preparedStatementForUpdateAcademicBodyHeadData.executeUpdate();
        }catch(SQLException e){
            throw new DataInsertionException("Error al agregar miembro del cuerpo académico. Verifique su conexion e intentelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }
    }

    @Override
    public ArrayList<AcademicBodyHead> getAcademicBodyHeads() throws DataRetrievalException{
        ArrayList<AcademicBodyHead> academicBodyHeads = new ArrayList<>();

        try{
            Statement statement = dataBaseManager.getConnection().createStatement();
            String query = "SELECT * FROM Usuarios U INNER JOIN Profesores P ON U.IdUsuario = P.IdUsuario INNER JOIN ResponsablesCA RCA ON P.NumPersonal = RCA.NumPersonal";
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                AcademicBodyHead academicBodyHead = new AcademicBodyHead();
                academicBodyHead.setName(resultSet.getString("nombre"));
                academicBodyHead.setFirstSurname(resultSet.getString("apellidoPaterno"));
                academicBodyHead.setSecondSurname(resultSet.getString("apellidoMaterno"));
                academicBodyHead.setEmailAddress(resultSet.getString("correo"));
                academicBodyHead.setPassword(resultSet.getString("contraseña"));
                academicBodyHead.setAlternateEmail(resultSet.getString("correoAlterno"));
                academicBodyHead.setPhoneNumber(resultSet.getString("númeroTeléfono"));
                academicBodyHead.setStatus(resultSet.getString("estado"));
                academicBodyHead.setStaffNumber(resultSet.getInt("NumPersonal"));
                academicBodyHeads.add(academicBodyHead);
            }
            resultSet.close();
            dataBaseManager.getConnection().close();
        }catch(SQLException e){
            throw new DataRetrievalException("Fallo al recuperar la informacion. Verifique su conexion e intentelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }

        return academicBodyHeads;
    }

    @Override
    public ArrayList<AcademicBodyHead> getSpecifiedAcademicBodyHeads(String academicBodyHeadName) throws DataRetrievalException{
        ArrayList<AcademicBodyHead> academicBodyHeads = new ArrayList<>();

        try{
            String query = "SELECT * FROM Usuarios U INNER JOIN Profesores P ON U.IdUsuario = P.IdUsuario INNER JOIN ResponsablesCA RCA ON P.NumPersonal = RCA.NumPersonal WHERE U.Nombre LIKE ?";
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setString(1, academicBodyHeadName + '%');
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                AcademicBodyHead academicBodyHead = new AcademicBodyHead();
                academicBodyHead.setName(resultSet.getString("nombre"));
                academicBodyHead.setFirstSurname(resultSet.getString("apellidoPaterno"));
                academicBodyHead.setSecondSurname(resultSet.getString("apellidoMaterno"));
                academicBodyHead.setEmailAddress(resultSet.getString("correo"));
                academicBodyHead.setPassword(resultSet.getString("contraseña"));
                academicBodyHead.setAlternateEmail(resultSet.getString("correoAlterno"));
                academicBodyHead.setPhoneNumber(resultSet.getString("númeroTeléfono"));
                academicBodyHead.setStatus(resultSet.getString("estado"));
                academicBodyHead.setStaffNumber(resultSet.getInt("NumPersonal"));
                academicBodyHeads.add(academicBodyHead);
            }
            resultSet.close();
            dataBaseManager.getConnection().close();
        }catch(SQLException e){
            throw new DataRetrievalException("Fallo al recuperar la informacion. Verifique su conexion e intentelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }

        return academicBodyHeads;
    }

    @Override
    public AcademicBodyHead getAcademicBodyHead(int staffNumber) throws DataRetrievalException{
        AcademicBodyHead academicBodyHead = new AcademicBodyHead();

        try {
            String query = "SELECT * FROM Usuarios U INNER JOIN Profesores P ON U.IdUsuario = P.IdUsuario INNER JOIN ResponsablesCA RCA ON P.NumPersonal = RCA.NumPersonal WHERE RCA.NumPersonal = ?";
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, staffNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                academicBodyHead.setName(resultSet.getString("nombre"));
                academicBodyHead.setFirstSurname(resultSet.getString("apellidoPaterno"));
                academicBodyHead.setSecondSurname(resultSet.getString("apellidoMaterno"));
                academicBodyHead.setEmailAddress(resultSet.getString("correo"));
                academicBodyHead.setPassword(resultSet.getString("contraseña"));
                academicBodyHead.setAlternateEmail(resultSet.getString("correoAlterno"));
                academicBodyHead.setPhoneNumber(resultSet.getString("númeroTeléfono"));
                academicBodyHead.setStatus(resultSet.getString("estado"));
                academicBodyHead.setStaffNumber(resultSet.getInt("NumPersonal"));
            }
            
            resultSet.close();
            dataBaseManager.getConnection().close();
        }catch(SQLException e){
            throw new DataRetrievalException("Fallo al recuperar la informacion. Verifique su conexion e intentelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }

        return academicBodyHead;
    }

    public boolean theAcademicBodyHeadIsAlreadyRegisted(AcademicBodyHead academicBodyHead) throws DataRetrievalException{
        try{
            Statement statement = dataBaseManager.getConnection().createStatement();
            String query = "SELECT U.nombre, U.apellidoPaterno, U.apellidoMaterno, U.correo, " +
                           "U.correoAlterno, U.númeroTeléfono, U.estado, P.NumPersonal FROM Usuarios U " + 
                           "INNER JOIN Profesores P ON U.IdUsuario = P.IdUsuario INNER JOIN ResponsablesCA RCA " +
                           "ON P.NumPersonal = RCA.NumPersonal";
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()) {
                if(resultSet.getString("nombre").equals(academicBodyHead.getName()) &&
                   resultSet.getString("apellidoPaterno").equals(academicBodyHead.getFirstSurname()) &&
                   resultSet.getString("apellidoMaterno").equals(academicBodyHead.getSecondSurname()) &&
                   resultSet.getString("correo").equals(academicBodyHead.getEmailAddress()) &&
                   resultSet.getString("correoAlterno").equals(academicBodyHead.getAlternateEmail()) &&
                   resultSet.getString("númeroTeléfono").equals(academicBodyHead.getPhoneNumber()) &&
                   resultSet.getString("estado").equals(academicBodyHead.getStatus()) &&
                   resultSet.getInt("NumPersonal") == academicBodyHead.getStaffNumber()) {

                    resultSet.close();
                    dataBaseManager.getConnection().close();
                    return true;
                }
            }
            resultSet.close();
            dataBaseManager.getConnection().close();
        }catch(SQLException e){
            throw new DataRetrievalException("Fallo al recuperar la informacion. Verifique su conexion e intentelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }

        return false;
    }
    private void deleteAcademicBodyHeadFromUsersTable(AcademicBodyHead academicBodyHead) throws DataInsertionException{
        String queryToInsertUserData = "DELETE FROM Usuarios WHERE nombre = ? && apellidoPaterno = ? && apellidoMaterno = ? && " +
            "correo = ? && correoAlterno = ? && númeroTeléfono = ? && estado = ?";
        try{
            PreparedStatement preparedStatementToInsertUserData = 
            dataBaseManager.getConnection().prepareStatement(queryToInsertUserData);
            preparedStatementToInsertUserData.setString(1, academicBodyHead.getName());
            preparedStatementToInsertUserData.setString(2, academicBodyHead.getFirstSurname());
            preparedStatementToInsertUserData.setString(3, academicBodyHead.getSecondSurname());
            preparedStatementToInsertUserData.setString(4, academicBodyHead.getEmailAddress());
            preparedStatementToInsertUserData.setString(5, academicBodyHead.getAlternateEmail());
            preparedStatementToInsertUserData.setString(6, academicBodyHead.getPhoneNumber());
            preparedStatementToInsertUserData.setString(7, academicBodyHead.getStatus());
            preparedStatementToInsertUserData.executeUpdate();
        }catch(SQLException e){
            throw new DataInsertionException("Error al eliminar profesor de la tabla usuarios");
        }
    }
}