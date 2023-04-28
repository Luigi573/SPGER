package mx.uv.fei.logic.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.logic.daosinterfaces.IDirectorDAO;
import mx.uv.fei.logic.domain.Director;

public class DirectorDAO implements IDirectorDAO{

    @Override
    public void addDirectorToDatabase(Director director) {
        
    }

    @Override
    public void modifyDirectorDataFromDatabase(Director director) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'modifyDirectorDataFromDatabase'");
    }

    @Override
    public ArrayList<Director> getDirectorsFromDatabase() {
        ArrayList<Director> directors = new ArrayList<>();

        try {
            DataBaseManager dataBaseManager = new DataBaseManager();
            Statement statement = dataBaseManager.getConnection().createStatement();
            String query = "SELECT * FROM Usuarios U INNER JOIN Profesores P ON U.IdUsuario = P.IdUsuario INNER JOIN Directores D ON P.IdProfesor = D.IdProfesor";
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()) {
                Director director = new Director();
                director.setName(resultSet.getString("nombre"));
                director.setFirstSurname(resultSet.getString("apellidoPaterno"));
                director.setSecondSurname(resultSet.getString("apellidoMaterno"));
                director.setEmailAddress(resultSet.getString("correo"));
                director.setPassword(resultSet.getString("contraseña"));
                director.setAlternateEmail(resultSet.getString("correoAlterno"));
                director.setPersonalNumber(resultSet.getString("NúmeroDePersonal"));
                directors.add(director);
            }
            resultSet.close();
            dataBaseManager.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return directors;
    }

    @Override
    public ArrayList<Director> getSpecifiedDirectorsFromDatabase(String directorName) {
        ArrayList<Director> directors = new ArrayList<>();

        try {
            DataBaseManager dataBaseManager = new DataBaseManager();
            String query = "SELECT * FROM Usuarios U INNER JOIN Profesores P ON U.IdUsuario = P.IdUsuario INNER JOIN Directores D ON P.IdProfesor = D.IdProfesor WHERE U.Nombre LIKE ?";
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setString(1, directorName + '%');
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                Director director = new Director();
                director.setName(resultSet.getString("nombre"));
                director.setFirstSurname(resultSet.getString("apellidoPaterno"));
                director.setSecondSurname(resultSet.getString("apellidoMaterno"));
                director.setEmailAddress(resultSet.getString("correo"));
                director.setPassword(resultSet.getString("contraseña"));
                director.setAlternateEmail(resultSet.getString("correoAlterno"));
                director.setPersonalNumber(resultSet.getString("NúmeroDePersonal"));
                directors.add(director);
            }
            resultSet.close();
            dataBaseManager.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return directors;
    }

    @Override
    public Director getDirectorFromDatabase(String directorName){
        Director director = new Director();

        try {
            DataBaseManager dataBaseManager = new DataBaseManager();
            String query = "SELECT * FROM Usuarios U INNER JOIN Profesores P ON U.IdUsuario = P.IdUsuario INNER JOIN Directores D ON P.IdProfesor = D.IdProfesor WHERE U.Nombre = ?";
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setString(1, directorName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                director.setName(resultSet.getString("nombre"));
                director.setFirstSurname(resultSet.getString("apellidoPaterno"));
                director.setSecondSurname(resultSet.getString("apellidoMaterno"));
                director.setEmailAddress(resultSet.getString("correo"));
                director.setPassword(resultSet.getString("contraseña"));
                director.setAlternateEmail(resultSet.getString("correoAlterno"));
                director.setPersonalNumber(resultSet.getString("NúmeroDePersonal"));
            }

            resultSet.close();
            dataBaseManager.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return director;
    }

    public boolean theDirectorIsAlreadyRegisted(Director director) {
        try {
            DataBaseManager dataBaseManager = new DataBaseManager();
            Statement statement = dataBaseManager.getConnection().createStatement();
            String query = "SELECT NúmeroDePersonal FROM Profesores";
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()) {
                if(resultSet.getString("NúmeroDePersonal").equals(director.getPersonalNumber())) {
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
