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
                "SELECT IdUsuario FROM Usuarios WHERE Nombre = ?";
            PreparedStatement preparedStatementForAssignUserIdToProfessor = 
                dataBaseManager.getConnection().prepareStatement(queryForAssignUserIdToProfessor);
            preparedStatementForAssignUserIdToProfessor.setString(1, professor.getName());
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
    public void modifyProfessorDataFromDatabase(Professor professor) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'modifyProfessorDataFromDatabase'");
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
            String query = "SELECT NúmeroDePersonal FROM Profesores";
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()) {
                if(resultSet.getString("NúmeroDePersonal").equals(professor.getPersonalNumber())) {
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
