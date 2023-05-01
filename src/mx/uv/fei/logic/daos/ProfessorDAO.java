package mx.uv.fei.logic.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.logic.daosinterfaces.IProfessorDAO;
import mx.uv.fei.logic.domain.Professor;

public class ProfessorDAO implements IProfessorDAO{

    @Override
    public void addProfessorToDatabase(Professor professor) {
        try {
            DataBaseManager dataBaseManager = new DataBaseManager();
            String userTablesToConsult = 
                "nombre, apellidoPaterno, apellidoMaterno, correo, correoAlterno, númeroTeléfono";
            String wholeQueryToInsertUserData = "INSERT INTO Usuarios (" + userTablesToConsult + ") VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatementToInsertUserData = 
                dataBaseManager.getConnection().prepareStatement(wholeQueryToInsertUserData);
            preparedStatementToInsertUserData.setString(1, professor.getName());
            preparedStatementToInsertUserData.setString(2, professor.getFirstSurname());
            preparedStatementToInsertUserData.setString(3, professor.getSecondSurname());
            preparedStatementToInsertUserData.setString(4, professor.getEmailAddress());
            preparedStatementToInsertUserData.setString(5, professor.getAlternateEmail());
            preparedStatementToInsertUserData.setString(6, professor.getPhoneNumber());
            preparedStatementToInsertUserData.executeUpdate();

            String queryForAssignUserIdToProfessor =
                "SELECT IdUsuario FROM Usuarios WHERE nombre = ? && " +
                "apellidoPaterno = ? && apellidoMaterno = ? && correo = ? && " +
                "correoAlterno = ? && númeroTeléfono = ?";
            PreparedStatement preparedStatementForAssignUserIdToProfessor = 
                dataBaseManager.getConnection().prepareStatement(queryForAssignUserIdToProfessor);
            preparedStatementForAssignUserIdToProfessor.setString(1, professor.getName());
            preparedStatementForAssignUserIdToProfessor.setString(2, professor.getFirstSurname());
            preparedStatementForAssignUserIdToProfessor.setString(3, professor.getSecondSurname());
            preparedStatementForAssignUserIdToProfessor.setString(4, professor.getEmailAddress());
            preparedStatementForAssignUserIdToProfessor.setString(5, professor.getAlternateEmail());
            preparedStatementForAssignUserIdToProfessor.setString(6, professor.getPhoneNumber());
            ResultSet resultSet = preparedStatementForAssignUserIdToProfessor.executeQuery();
            if(resultSet.next()){
                professor.setIdUser(resultSet.getInt("IdUsuario"));
            }

            String professorTablesToConsult = "NúmeroDePersonal, IdUsuario";
            String wholeQueryToInsertProfessorData = 
                "INSERT INTO Profesores (" + professorTablesToConsult + ") VALUES (?, ?)";
            PreparedStatement preparedStatementToInsertProfessorData = 
                dataBaseManager.getConnection().prepareStatement(wholeQueryToInsertProfessorData);
            preparedStatementToInsertProfessorData.setString(1, professor.getPersonalNumber());
            preparedStatementToInsertProfessorData.setInt(2, professor.getIdUser());
            preparedStatementToInsertProfessorData.executeUpdate();

            preparedStatementToInsertProfessorData.close();
            dataBaseManager.getConnection().close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void modifyProfessorDataFromDatabase(Professor newProfessorData, ArrayList<String> originalProfessorData) {
        try {
            newProfessorData.setIdUser(this.getIdUserFromProfessor(originalProfessorData));
            DataBaseManager dataBaseManager = new DataBaseManager();
            String queryForUpdateUserData = "UPDATE Usuarios SET nombre = ?, " + 
                           "apellidoPaterno = ?, apellidoMaterno = ?, correo = ?, " + 
                           "correoAlterno = ?, númeroTeléfono = ? " +
                           "WHERE nombre = ? && apellidoPaterno = ? && apellidoMaterno = ? && " + 
                           "correo = ? && correoAlterno = ? && númeroTeléfono = ?";
            PreparedStatement preparedStatement = 
                dataBaseManager.getConnection().prepareStatement(queryForUpdateUserData);
            preparedStatement.setString(1, newProfessorData.getName());
            preparedStatement.setString(2, newProfessorData.getFirstSurname());
            preparedStatement.setString(3, newProfessorData.getSecondSurname());
            preparedStatement.setString(4, newProfessorData.getEmailAddress());
            preparedStatement.setString(5, newProfessorData.getAlternateEmail());
            preparedStatement.setString(6, newProfessorData.getPhoneNumber());
            preparedStatement.setString(7, originalProfessorData.get(0));
            preparedStatement.setString(8, originalProfessorData.get(1));
            preparedStatement.setString(9, originalProfessorData.get(2));
            preparedStatement.setString(10, originalProfessorData.get(3));
            preparedStatement.setString(11, originalProfessorData.get(4));
            preparedStatement.setString(12, originalProfessorData.get(5));
            preparedStatement.executeUpdate();

            String queryForUpdateProfessorData = "UPDATE Profesores SET NúmeroDePersonal = ? " + 
                           "WHERE IdUsuario = ?";
            
            PreparedStatement preparedStatementForUpdateProfessorData = 
                dataBaseManager.getConnection().prepareStatement(queryForUpdateProfessorData);
            preparedStatementForUpdateProfessorData.setString(1, newProfessorData.getPersonalNumber());
            preparedStatementForUpdateProfessorData.setInt(2, newProfessorData.getIdUser());
            preparedStatementForUpdateProfessorData.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Professor> getProfessorsFromDatabase() {
        ArrayList<Professor> professors = new ArrayList<>();
        
        try {
            DataBaseManager dataBaseManager = new DataBaseManager();
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
                professor.setPersonalNumber(resultSet.getString("NúmeroDePersonal"));
                professors.add(professor);
            }
            resultSet.close();
            dataBaseManager.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return professors;
    }


    @Override
    public ArrayList<Professor> getSpecifiedProfessorsFromDatabase(String professorName) {
        ArrayList<Professor> professors = new ArrayList<>();
        
        try {
            DataBaseManager dataBaseManager = new DataBaseManager();
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
                professor.setPersonalNumber(resultSet.getString("NúmeroDePersonal"));
                professors.add(professor);
            }
            resultSet.close();
            dataBaseManager.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return professors;
    }

    @Override
    public Professor getProfessorFromDatabase(String professorName) {
        Professor professor = new Professor();

        try {
            DataBaseManager dataBaseManager = new DataBaseManager();
            String query = "SELECT * FROM Usuarios U INNER JOIN Profesores P ON U.IdUsuario = P.IdUsuario WHERE U.Nombre = ?";
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setString(1, professorName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                professor.setName(resultSet.getString("nombre"));
                professor.setFirstSurname(resultSet.getString("apellidoPaterno"));
                professor.setSecondSurname(resultSet.getString("apellidoMaterno"));
                professor.setEmailAddress(resultSet.getString("correo"));
                professor.setPassword(resultSet.getString("contraseña"));
                professor.setAlternateEmail(resultSet.getString("correoAlterno"));
                professor.setPhoneNumber(resultSet.getString("númeroTeléfono"));
                professor.setPersonalNumber(resultSet.getString("NúmeroDePersonal"));
            }

            resultSet.close();
            dataBaseManager.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return professor;
    }

    public boolean theProfessorIsAlreadyRegisted(Professor professor) {
        try {
            DataBaseManager dataBaseManager = new DataBaseManager();
            Statement statement = dataBaseManager.getConnection().createStatement();
            String query = "SELECT U.nombre, U.apellidoPaterno, U.apellidoMaterno, U.correo, " +
                           "U.correoAlterno, U.númeroTeléfono, P.NúmeroDePersonal FROM Usuarios U " + 
                           "INNER JOIN Profesores P ON U.IdUsuario = P.IdUsuario";
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()) {
                if( (resultSet.getString("nombre").equals(professor.getName()) &&
                   resultSet.getString("apellidoPaterno").equals(professor.getFirstSurname()) &&
                   resultSet.getString("apellidoMaterno").equals(professor.getSecondSurname()) &&
                   resultSet.getString("correo").equals(professor.getEmailAddress()) &&
                   resultSet.getString("correoAlterno").equals(professor.getAlternateEmail()) &&
                   resultSet.getString("númeroTeléfono").equals(professor.getPhoneNumber()) ) ||
                   resultSet.getString("NúmeroDePersonal").equals(professor.getPersonalNumber())) {
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

    private int getIdUserFromProfessor(ArrayList<String> originalProfessorData){
        int idUser = 0;

        try {
            DataBaseManager dataBaseManager = new DataBaseManager();
            String query = "SELECT U.IdUsuario FROM Usuarios U INNER JOIN Profesores P ON " + 
                           "U.IdUsuario = P.IdUsuario WHERE U.nombre = ? && " +
                           "U.apellidoPaterno = ? && U.apellidoMaterno = ? && U.correo = ? && " +
                           "U.correoAlterno = ? && U.númeroTeléfono = ? && P.NúmeroDePersonal = ?";
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setString(1, originalProfessorData.get(0));
            preparedStatement.setString(2, originalProfessorData.get(1));
            preparedStatement.setString(3, originalProfessorData.get(2));
            preparedStatement.setString(4, originalProfessorData.get(3));
            preparedStatement.setString(5, originalProfessorData.get(4));
            preparedStatement.setString(6, originalProfessorData.get(5));
            preparedStatement.setString(7, originalProfessorData.get(6));

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                idUser = resultSet.getInt("IdUsuario");
            }
            
            resultSet.close();
            dataBaseManager.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return idUser;
    }
    
}
