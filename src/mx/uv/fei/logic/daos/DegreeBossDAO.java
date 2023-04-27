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

    @Override
    public void addDegreeBossToDatabase(DegreeBoss degreeBoss) {
        
    }

    @Override
    public ArrayList<DegreeBoss> getDegreeBossesFromDatabase() {
        ArrayList<DegreeBoss> degreeBosses = new ArrayList<>();

        try {
            DataBaseManager dataBaseManager = new DataBaseManager();
            Statement statement = dataBaseManager.getConnection().createStatement();
            String query = "SELECT * FROM Usuarios U INNER JOIN Profesores P ON U.IdUsuario = P.IdUsuario INNER JOIN JefesCarrera JC ON P.IdProfesor = JC.IdProfesor";
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()) {
                DegreeBoss degreeBoss = new DegreeBoss();
                degreeBoss.setName(resultSet.getString("nombre"));
                degreeBoss.setFirstSurname(resultSet.getString("apellidoPaterno"));
                degreeBoss.setSecondSurname(resultSet.getString("apellidoMaterno"));
                degreeBoss.setEmailAddress(resultSet.getString("correo"));
                degreeBoss.setPassword(resultSet.getString("contraseña"));
                degreeBoss.setAlternateEmail(resultSet.getString("correoAlterno"));
                degreeBoss.setPersonalNumber(resultSet.getString("NúmeroDePersonal"));
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

        try {
            DataBaseManager dataBaseManager = new DataBaseManager();
            String query = "SELECT * FROM Usuarios U INNER JOIN Profesores P ON U.IdUsuario = P.IdUsuario INNER JOIN JefesCarrera JC ON P.IdProfesor = JC.IdProfesor WHERE U.Nombre LIKE ?";
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
                degreeBoss.setPersonalNumber(resultSet.getString("NúmeroDePersonal"));
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
    public DegreeBoss getDegreeBossFromDatabase(String degreeBossName) {
        DegreeBoss degreeBoss = new DegreeBoss();

        try {
            DataBaseManager dataBaseManager = new DataBaseManager();
            String query = "SELECT * FROM Usuarios U INNER JOIN Profesores P ON U.IdUsuario = P.IdUsuario INNER JOIN JefesCarrera JC ON P.IdProfesor = JC.IdProfesor WHERE U.Nombre = ?";
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setString(1, degreeBossName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                degreeBoss.setName(resultSet.getString("nombre"));
                degreeBoss.setFirstSurname(resultSet.getString("apellidoPaterno"));
                degreeBoss.setSecondSurname(resultSet.getString("apellidoMaterno"));
                degreeBoss.setEmailAddress(resultSet.getString("correo"));
                degreeBoss.setPassword(resultSet.getString("contraseña"));
                degreeBoss.setAlternateEmail(resultSet.getString("correoAlterno"));
                degreeBoss.setPersonalNumber(resultSet.getString("NúmeroDePersonal"));
            }
            
            resultSet.close();
            dataBaseManager.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return degreeBoss;
    }
    
}
