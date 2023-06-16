package mx.uv.fei.logic.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;

import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.logic.daosinterfaces.IProfessorDAO;
import mx.uv.fei.logic.domain.Professor;
import mx.uv.fei.logic.exceptions.DataInsertionException;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.exceptions.DuplicatedPrimaryKeyException;

public class ProfessorDAO implements IProfessorDAO{
    private final DataBaseManager dataBaseManager;

    public ProfessorDAO(){
        dataBaseManager = new DataBaseManager();
    }

    @Override
    public int addProfessor(Professor professor) throws DataInsertionException, DuplicatedPrimaryKeyException{
        int generatedId = 0;
        try{
            String queryToInsertProfessorDataToUsersColumns = "INSERT INTO Usuarios (nombre, apellidoPaterno, apellidoMaterno, correo, correoAlterno, " +
                            "numeroTelefono, estado, contraseña) VALUES (?, ?, ?, ?, ?, ?, ?, SHA2(?, 256))";
            PreparedStatement preparedStatementToInsertProfessorDataToUsersColumns = 
                dataBaseManager.getConnection().prepareStatement(queryToInsertProfessorDataToUsersColumns, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatementToInsertProfessorDataToUsersColumns.setString(1, professor.getName());
            preparedStatementToInsertProfessorDataToUsersColumns.setString(2, professor.getFirstSurname());
            preparedStatementToInsertProfessorDataToUsersColumns.setString(3, professor.getSecondSurname());
            preparedStatementToInsertProfessorDataToUsersColumns.setString(4, professor.getEmailAddress());
            preparedStatementToInsertProfessorDataToUsersColumns.setString(5, professor.getAlternateEmail());
            preparedStatementToInsertProfessorDataToUsersColumns.setString(6, professor.getPhoneNumber());
            preparedStatementToInsertProfessorDataToUsersColumns.setString(7, professor.getStatus());
            preparedStatementToInsertProfessorDataToUsersColumns.setString(8, professor.getPassword());
            preparedStatementToInsertProfessorDataToUsersColumns.executeUpdate();

            ResultSet resultSet = preparedStatementToInsertProfessorDataToUsersColumns.getGeneratedKeys();
            if(resultSet.next()){
                generatedId = resultSet.getInt(1);
                professor.setUserId(generatedId);
            }

            String queryToInsertProfessorDataToProfessorColumns = 
                "INSERT INTO Profesores (NumPersonal, IdUsuario) VALUES (?, ?)";
            PreparedStatement preparedStatementToInsertProfessorDataToProfessorColumns = 
                dataBaseManager.getConnection().prepareStatement(queryToInsertProfessorDataToProfessorColumns);
            preparedStatementToInsertProfessorDataToProfessorColumns.setInt(1, professor.getStaffNumber());
            preparedStatementToInsertProfessorDataToProfessorColumns.setInt(2, professor.getUserId());
            preparedStatementToInsertProfessorDataToProfessorColumns.executeUpdate();

            preparedStatementToInsertProfessorDataToProfessorColumns.close();
            dataBaseManager.getConnection().close();

        }catch(SQLIntegrityConstraintViolationException e){
            deleteProfessorFromUsersTable(professor);
            throw new DuplicatedPrimaryKeyException("Profesor ya registrado en el sistema");
        }catch(SQLException e){
            throw new DataInsertionException("Error al agregar profesor. Inténtelo de nuevo más tarde");
        }finally{
            dataBaseManager.closeConnection();
        }
        return generatedId;
    }

    @Override
    public int modifyProfessorData(Professor professor) throws DataInsertionException, DuplicatedPrimaryKeyException{
        int result = 0;
        try{
            String queryForUpdateUserData = "UPDATE Usuarios SET nombre = ?, " + 
                           "apellidoPaterno = ?, apellidoMaterno = ?, correo = ?, " + 
                           "correoAlterno = ?, numeroTelefono = ?, estado = ? " +
                           "WHERE IdUsuario = ?";
            PreparedStatement preparedStatement = 
                dataBaseManager.getConnection().prepareStatement(queryForUpdateUserData);
            preparedStatement.setString(1, professor.getName());
            preparedStatement.setString(2, professor.getFirstSurname());
            preparedStatement.setString(3, professor.getSecondSurname());
            preparedStatement.setString(4, professor.getEmailAddress());
            preparedStatement.setString(5, professor.getAlternateEmail());
            preparedStatement.setString(6, professor.getPhoneNumber());
            preparedStatement.setString(7, professor.getStatus());
            preparedStatement.setInt(8, professor.getUserId());
            result = preparedStatement.executeUpdate();

            String queryForUpdateProfessorData = "UPDATE Profesores SET NumPersonal = ? " + 
                           "WHERE IdUsuario = ?";
            
            PreparedStatement preparedStatementForUpdateProfessorData = 
                dataBaseManager.getConnection().prepareStatement(queryForUpdateProfessorData);
            preparedStatementForUpdateProfessorData.setInt(1, professor.getStaffNumber());
            preparedStatementForUpdateProfessorData.setInt(2, professor.getUserId());
            preparedStatementForUpdateProfessorData.executeUpdate();
        }catch(SQLIntegrityConstraintViolationException e){
            throw new DuplicatedPrimaryKeyException("Profesor ya registrado en el sistema");
        }catch(SQLException e){
            throw new DataInsertionException("Error al modificar profesor. Inténtelo de nuevo más tarde");
        }finally{
            dataBaseManager.closeConnection();
        }

        return result;
    }

    @Override
    public ArrayList<Professor> getProfessors() throws DataRetrievalException{
        ArrayList<Professor> professors = new ArrayList<>();
        
        try{
            Statement statement = dataBaseManager.getConnection().createStatement();
            String query = "SELECT * FROM Usuarios U INNER JOIN Profesores P ON U.IdUsuario = P.IdUsuario";
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()) {
                Professor professor = new Professor();
                professor.setUserId(resultSet.getInt("IdUsuario"));
                professor.setName(resultSet.getString("nombre"));
                professor.setFirstSurname(resultSet.getString("apellidoPaterno"));
                professor.setSecondSurname(resultSet.getString("apellidoMaterno"));
                professor.setEmailAddress(resultSet.getString("correo"));
                professor.setPassword(resultSet.getString("contraseña"));
                professor.setAlternateEmail(resultSet.getString("correoAlterno"));
                professor.setPhoneNumber(resultSet.getString("numeroTelefono"));
                professor.setStatus(resultSet.getString("estado"));
                professor.setStaffNumber(resultSet.getInt("NumPersonal"));
                professors.add(professor);
            }
            resultSet.close();
            dataBaseManager.closeConnection();
        }catch(SQLException e) {
              ;
            throw new DataRetrievalException("Fallo al recuperar la informacion. Inténtelo de nuevo más tarde");
        }finally{
            dataBaseManager.closeConnection();
        }

        return professors;
    }

    @Override
    public ArrayList<Professor> getSpecifiedProfessors(String professorName) throws DataRetrievalException{
        ArrayList<Professor> professors = new ArrayList<>();
        
        try{
            String query = "SELECT * FROM Usuarios U INNER JOIN Profesores P ON U.IdUsuario = P.IdUsuario WHERE U.Nombre LIKE ?";
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setString(1, professorName + '%');

            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                Professor professor = new Professor();
                professor.setUserId(resultSet.getInt("IdUsuario"));
                professor.setName(resultSet.getString("nombre"));
                professor.setFirstSurname(resultSet.getString("apellidoPaterno"));
                professor.setSecondSurname(resultSet.getString("apellidoMaterno"));
                professor.setEmailAddress(resultSet.getString("correo"));
                professor.setPassword(resultSet.getString("contraseña"));
                professor.setAlternateEmail(resultSet.getString("correoAlterno"));
                professor.setPhoneNumber(resultSet.getString("numeroTelefono"));
                professor.setStatus(resultSet.getString("estado"));
                professor.setStaffNumber(resultSet.getInt("NumPersonal"));
                professors.add(professor);
            }
            resultSet.close();
            dataBaseManager.closeConnection();
        }catch(SQLException e){
            throw new DataRetrievalException("Fallo al recuperar la informacion. Inténtelo de nuevo más tarde");
        }finally{
            dataBaseManager.closeConnection();
        }

        return professors;
    }

    @Override
    public Professor getProfessor(int staffNumber) throws DataRetrievalException{
        Professor professor = new Professor();

        try{
            String query = "SELECT * FROM Usuarios U INNER JOIN Profesores P ON U.IdUsuario = P.IdUsuario WHERE P.NumPersonal = ?";
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, staffNumber);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                professor.setUserId(resultSet.getInt("IdUsuario"));
                professor.setName(resultSet.getString("nombre"));
                professor.setFirstSurname(resultSet.getString("apellidoPaterno"));
                professor.setSecondSurname(resultSet.getString("apellidoMaterno"));
                professor.setEmailAddress(resultSet.getString("correo"));
                professor.setPassword(resultSet.getString("contraseña"));
                professor.setAlternateEmail(resultSet.getString("correoAlterno"));
                professor.setPhoneNumber(resultSet.getString("numeroTelefono"));
                professor.setStatus(resultSet.getString("estado"));
                professor.setStaffNumber(resultSet.getInt("NumPersonal"));
            }

            resultSet.close();
            dataBaseManager.closeConnection();
        }catch(SQLException e){
              ;
            throw new DataRetrievalException("Fallo al recuperar la informacion. Inténtelo de nuevo más tarde");
        }finally{
            dataBaseManager.closeConnection();
        }

        return professor;
    }
    
    private void deleteProfessorFromUsersTable(Professor professor) throws DataInsertionException{
        String queryToInsertUserData = "DELETE FROM Usuarios WHERE IdUsuario = ?";
        try{
            PreparedStatement preparedStatementToInsertUserData = 
            dataBaseManager.getConnection().prepareStatement(queryToInsertUserData);
            preparedStatementToInsertUserData.setInt(1, professor.getUserId());
            preparedStatementToInsertUserData.executeUpdate();
        }catch(SQLException e){
            throw new DataInsertionException("Error al eliminar profesor de la tabla usuarios");
        }
    }
}