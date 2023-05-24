package mx.uv.fei.logic.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.logic.daosinterfaces.IProfessorDAO;
import mx.uv.fei.logic.domain.Professor;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.exceptions.DataWritingException;

public class ProfessorDAO implements IProfessorDAO{
    private final DataBaseManager dataBaseManager;

    public ProfessorDAO(){
        dataBaseManager = new DataBaseManager();
    }

    @Override
    public void addProfessorToDatabase(Professor professor) throws DataWritingException{
        try{
            String query = "INSERT INTO Usuarios (nombre, apellidoPaterno, apellidoMaterno, correo, correoAlterno, " +
                            "númeroTeléfono, estado) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatementToInsertUserData = 
                dataBaseManager.getConnection().prepareStatement(query);
            preparedStatementToInsertUserData.setString(1, professor.getName());
            preparedStatementToInsertUserData.setString(2, professor.getFirstSurname());
            preparedStatementToInsertUserData.setString(3, professor.getSecondSurname());
            preparedStatementToInsertUserData.setString(4, professor.getEmailAddress());
            preparedStatementToInsertUserData.setString(5, professor.getAlternateEmail());
            preparedStatementToInsertUserData.setString(6, professor.getPhoneNumber());
            preparedStatementToInsertUserData.setString(7, professor.getStatus());
            preparedStatementToInsertUserData.executeUpdate();

            String queryForAssignUserIdToProfessor =
                "SELECT IdUsuario FROM Usuarios WHERE nombre = ? && " +
                "apellidoPaterno = ? && apellidoMaterno = ? && correo = ? && " +
                "correoAlterno = ? && númeroTeléfono = ? && estado = ?";
            PreparedStatement preparedStatementForAssignUserIdToProfessor = 
                dataBaseManager.getConnection().prepareStatement(queryForAssignUserIdToProfessor);
            preparedStatementForAssignUserIdToProfessor.setString(1, professor.getName());
            preparedStatementForAssignUserIdToProfessor.setString(2, professor.getFirstSurname());
            preparedStatementForAssignUserIdToProfessor.setString(3, professor.getSecondSurname());
            preparedStatementForAssignUserIdToProfessor.setString(4, professor.getEmailAddress());
            preparedStatementForAssignUserIdToProfessor.setString(5, professor.getAlternateEmail());
            preparedStatementForAssignUserIdToProfessor.setString(6, professor.getPhoneNumber());
            preparedStatementForAssignUserIdToProfessor.setString(7, professor.getStatus());
            ResultSet resultSet = preparedStatementForAssignUserIdToProfessor.executeQuery();
            if(resultSet.next()){
                professor.setUserId(resultSet.getInt("IdUsuario"));
            }

            String wholeQueryToInsertProfessorData = 
                "INSERT INTO Profesores (NumPersonal, IdUsuario) VALUES (?, ?)";
            PreparedStatement preparedStatementToInsertProfessorData = 
                dataBaseManager.getConnection().prepareStatement(wholeQueryToInsertProfessorData);
            preparedStatementToInsertProfessorData.setInt(1, professor.getStaffNumber());
            preparedStatementToInsertProfessorData.setInt(2, professor.getUserId());
            preparedStatementToInsertProfessorData.executeUpdate();

            preparedStatementToInsertProfessorData.close();
            dataBaseManager.getConnection().close();

        }catch(SQLException e){
            throw new DataWritingException("Fallo al recuperar la informacion. Verifique su conexion e intentelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }
    }

    @Override
    public void modifyProfessorDataFromDatabase(Professor newProfessorData, Professor originalProfessorData) throws DataWritingException{
        try{
            newProfessorData.setUserId(getUserIdFromProfessor(originalProfessorData));
            String queryForUpdateUserData = "UPDATE Usuarios SET nombre = ?, " + 
                           "apellidoPaterno = ?, apellidoMaterno = ?, correo = ?, " + 
                           "correoAlterno = ?, númeroTeléfono = ?, estado = ? " +
                           "WHERE nombre = ? && apellidoPaterno = ? && apellidoMaterno = ? && " + 
                           "correo = ? && correoAlterno = ? && númeroTeléfono = ? && estado = ?";
            PreparedStatement preparedStatement = 
                dataBaseManager.getConnection().prepareStatement(queryForUpdateUserData);
            preparedStatement.setString(1, newProfessorData.getName());
            preparedStatement.setString(2, newProfessorData.getFirstSurname());
            preparedStatement.setString(3, newProfessorData.getSecondSurname());
            preparedStatement.setString(4, newProfessorData.getEmailAddress());
            preparedStatement.setString(5, newProfessorData.getAlternateEmail());
            preparedStatement.setString(6, newProfessorData.getPhoneNumber());
            preparedStatement.setString(7, newProfessorData.getStatus());
            preparedStatement.setString(8, originalProfessorData.getName());
            preparedStatement.setString(9, originalProfessorData.getFirstSurname());
            preparedStatement.setString(10, originalProfessorData.getSecondSurname());
            preparedStatement.setString(11, originalProfessorData.getEmailAddress());
            preparedStatement.setString(12, originalProfessorData.getAlternateEmail());
            preparedStatement.setString(13, originalProfessorData.getPhoneNumber());
            preparedStatement.setString(14, originalProfessorData.getStatus());
            preparedStatement.executeUpdate();

            String queryForUpdateProfessorData = "UPDATE Profesores SET NumPersonal = ? " + 
                           "WHERE IdUsuario = ?";
            
            PreparedStatement preparedStatementForUpdateProfessorData = 
                dataBaseManager.getConnection().prepareStatement(queryForUpdateProfessorData);
            preparedStatementForUpdateProfessorData.setInt(1, newProfessorData.getStaffNumber());
            preparedStatementForUpdateProfessorData.setInt(2, newProfessorData.getUserId());
            preparedStatementForUpdateProfessorData.executeUpdate();
        }catch(SQLException e){
            throw new DataWritingException("Fallo al recuperar la informacion. Verifique su conexion e intentelo de nuevo");
        }catch(DataRetrievalException e) {
            throw new DataWritingException("Fallo al recuperar la informacion. Verifique su conexion e intentelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }
    }

    @Override
    public ArrayList<Professor> getProfessorsFromDatabase() throws DataRetrievalException{
        ArrayList<Professor> professors = new ArrayList<>();
        
        try{
            Statement statement = dataBaseManager.getConnection().createStatement();
            String query = "SELECT * FROM Usuarios U INNER JOIN Profesores P ON U.IdUsuario = P.IdUsuario";
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()) {
                Professor professor = new Professor();
                professor.setName(resultSet.getString("nombre"));
                professor.setFirstSurname(resultSet.getString("apellidoPaterno"));
                professor.setSecondSurname(resultSet.getString("apellidoMaterno"));
                professor.setEmailAddress(resultSet.getString("correo"));
                professor.setPassword(resultSet.getString("contraseña"));
                professor.setAlternateEmail(resultSet.getString("correoAlterno"));
                professor.setPhoneNumber(resultSet.getString("númeroTeléfono"));
                professor.setStatus(resultSet.getString("estado"));
                professor.setStaffNumber(resultSet.getInt("NumPersonal"));
                professors.add(professor);
            }
            resultSet.close();
            dataBaseManager.getConnection().close();
        }catch(SQLException e) {
            throw new DataRetrievalException("Fallo al recuperar la informacion. Verifique su conexion e intentelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }

        return professors;
    }

    @Override
    public ArrayList<Professor> getSpecifiedProfessorsFromDatabase(String professorName) throws DataRetrievalException{
        ArrayList<Professor> professors = new ArrayList<>();
        
        try{
            String query = "SELECT * FROM Usuarios U INNER JOIN Profesores P ON U.IdUsuario = P.IdUsuario WHERE U.Nombre LIKE ?";
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setString(1, professorName + '%');

            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                Professor professor = new Professor();
                professor.setName(resultSet.getString("nombre"));
                professor.setFirstSurname(resultSet.getString("apellidoPaterno"));
                professor.setSecondSurname(resultSet.getString("apellidoMaterno"));
                professor.setEmailAddress(resultSet.getString("correo"));
                professor.setPassword(resultSet.getString("contraseña"));
                professor.setAlternateEmail(resultSet.getString("correoAlterno"));
                professor.setPhoneNumber(resultSet.getString("númeroTeléfono"));
                professor.setStatus(resultSet.getString("estado"));
                professor.setStaffNumber(resultSet.getInt("NumPersonal"));
                professors.add(professor);
            }
            resultSet.close();
            dataBaseManager.getConnection().close();
        }catch(SQLException e){
            throw new DataRetrievalException("Fallo al recuperar la informacion. Verifique su conexion e intentelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }

        return professors;
    }

    @Override
    public Professor getProfessorFromDatabase(int personalNumber) throws DataRetrievalException{
        Professor professor = new Professor();

        try{
            String query = "SELECT * FROM Usuarios U INNER JOIN Profesores P ON U.IdUsuario = P.IdUsuario WHERE P.NumPersonal = ?";
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, personalNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                professor.setName(resultSet.getString("nombre"));
                professor.setFirstSurname(resultSet.getString("apellidoPaterno"));
                professor.setSecondSurname(resultSet.getString("apellidoMaterno"));
                professor.setEmailAddress(resultSet.getString("correo"));
                professor.setPassword(resultSet.getString("contraseña"));
                professor.setAlternateEmail(resultSet.getString("correoAlterno"));
                professor.setPhoneNumber(resultSet.getString("númeroTeléfono"));
                professor.setStatus(resultSet.getString("estado"));
                professor.setStaffNumber(resultSet.getInt("NumPersonal"));
            }

            resultSet.close();
            dataBaseManager.getConnection().close();
        }catch(SQLException e){
            throw new DataRetrievalException("Fallo al recuperar la informacion. Verifique su conexion e intentelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }

        return professor;
    }

    public boolean theProfessorIsAlreadyRegisted(Professor professor) throws DataRetrievalException{
        try{
            Statement statement = dataBaseManager.getConnection().createStatement();
            String query = "SELECT U.nombre, U.apellidoPaterno, U.apellidoMaterno, U.correo, " +
                           "U.correoAlterno, U.númeroTeléfono, U.estado, P.NumPersonal FROM Usuarios U " + 
                           "INNER JOIN Profesores P ON U.IdUsuario = P.IdUsuario";
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()) {
                if(resultSet.getString("nombre").equals(professor.getName()) &&
                   resultSet.getString("apellidoPaterno").equals(professor.getFirstSurname()) &&
                   resultSet.getString("apellidoMaterno").equals(professor.getSecondSurname()) &&
                   resultSet.getString("correo").equals(professor.getEmailAddress()) &&
                   resultSet.getString("correoAlterno").equals(professor.getAlternateEmail()) &&
                   resultSet.getString("númeroTeléfono").equals(professor.getPhoneNumber()) &&
                   resultSet.getString("estado").equals(professor.getStatus()) &&
                   resultSet.getInt("NumPersonal") == professor.getStaffNumber()) {

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

    private int getUserIdFromProfessor(Professor originalProfessorData) throws DataRetrievalException{
        int UserId = 0;

        try{
            String query = "SELECT U.IdUsuario FROM Usuarios U INNER JOIN Profesores P ON " + 
                           "U.IdUsuario = P.IdUsuario WHERE U.nombre = ? && " +
                           "U.apellidoPaterno = ? && U.apellidoMaterno = ? && U.correo = ? && " +
                           "U.correoAlterno = ? && U.númeroTeléfono = ? && U.estado = ? && P.NumPersonal = ?";
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setString(1, originalProfessorData.getName());
            preparedStatement.setString(2, originalProfessorData.getFirstSurname());
            preparedStatement.setString(3, originalProfessorData.getSecondSurname());
            preparedStatement.setString(4, originalProfessorData.getEmailAddress());
            preparedStatement.setString(5, originalProfessorData.getAlternateEmail());
            preparedStatement.setString(6, originalProfessorData.getPhoneNumber());
            preparedStatement.setString(6, originalProfessorData.getStatus());
            preparedStatement.setInt(7, originalProfessorData.getStaffNumber());

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                UserId = resultSet.getInt("IdUsuario");
            }
            
            resultSet.close();
            dataBaseManager.getConnection().close();
        }catch(SQLException e){
            throw new DataRetrievalException("Fallo al recuperar la informacion. Verifique su conexion e intentelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }

        return UserId;
    }
}