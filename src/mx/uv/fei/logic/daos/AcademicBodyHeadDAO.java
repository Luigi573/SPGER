package mx.uv.fei.logic.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.logic.daosinterfaces.IAcademicBodyHeadDAO;
import mx.uv.fei.logic.domain.AcademicBodyHead;

public class AcademicBodyHeadDAO implements IAcademicBodyHeadDAO{

    @Override
    public void addAcademicBodyHeadToDatabase(AcademicBodyHead academicBodyHead) {
        
    }

    @Override
    public ArrayList<AcademicBodyHead> getAcademicBodyHeadsFromDatabase() {
        ArrayList<AcademicBodyHead> academicBodyHeads = new ArrayList<>();

        try {
            DataBaseManager dataBaseManager = new DataBaseManager();
            Statement statement = dataBaseManager.getConnection().createStatement();
            String query = "SELECT * FROM Usuarios U INNER JOIN Profesores P ON U.IdUsuario = P.IdUsuario INNER JOIN ResponsablesCA RCA ON P.IdProfesor = RCA.IdProfesor";
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()) {
                AcademicBodyHead academicBodyHead = new AcademicBodyHead();
                academicBodyHead.setName(resultSet.getString("nombre"));
                academicBodyHead.setFirstSurname(resultSet.getString("apellidoPaterno"));
                academicBodyHead.setSecondSurname(resultSet.getString("apellidoMaterno"));
                academicBodyHead.setEmailAddress(resultSet.getString("correo"));
                academicBodyHead.setPassword(resultSet.getString("contraseña"));
                academicBodyHead.setAlternateEmail(resultSet.getString("correoAlterno"));
                academicBodyHead.setPersonalNumber(resultSet.getString("NúmeroDePersonal"));
                academicBodyHeads.add(academicBodyHead);
            }
            resultSet.close();
            dataBaseManager.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return academicBodyHeads;
    }

    @Override
    public ArrayList<AcademicBodyHead> getSpecifiedAcademicBodyHeadsFromDatabase(String academicBodyHeadName) {
        ArrayList<AcademicBodyHead> academicBodyHeads = new ArrayList<>();

        try {
            DataBaseManager dataBaseManager = new DataBaseManager();
            String query = "SELECT * FROM Usuarios U INNER JOIN Profesores P ON U.IdUsuario = P.IdUsuario INNER JOIN ResponsablesCA RCA ON P.IdProfesor = RCA.IdProfesor WHERE U.Nombre LIKE ?";
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setString(1, academicBodyHeadName + '%');
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                AcademicBodyHead academicBodyHead = new AcademicBodyHead();
                academicBodyHead.setName(resultSet.getString("nombre"));
                academicBodyHead.setFirstSurname(resultSet.getString("apellidoPaterno"));
                academicBodyHead.setSecondSurname(resultSet.getString("apellidoMaterno"));
                academicBodyHead.setEmailAddress(resultSet.getString("correo"));
                academicBodyHead.setPassword(resultSet.getString("contraseña"));
                academicBodyHead.setAlternateEmail(resultSet.getString("correoAlterno"));
                academicBodyHead.setPersonalNumber(resultSet.getString("NúmeroDePersonal"));
                academicBodyHeads.add(academicBodyHead);
            }
            resultSet.close();
            dataBaseManager.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return academicBodyHeads;
    }

    @Override
    public AcademicBodyHead getAcademicBodyHeadFromDatabase(String academicBodyHeadName) {
        AcademicBodyHead academicBodyHead = new AcademicBodyHead();

        try {
            DataBaseManager dataBaseManager = new DataBaseManager();
            String query = "SELECT * FROM Usuarios U INNER JOIN Profesores P ON U.IdUsuario = P.IdUsuario INNER JOIN ResponsablesCA RCA ON P.IdProfesor = RCA.IdProfesor WHERE U.Nombre = ?";
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setString(1, academicBodyHeadName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                academicBodyHead.setName(resultSet.getString("nombre"));
                academicBodyHead.setFirstSurname(resultSet.getString("apellidoPaterno"));
                academicBodyHead.setSecondSurname(resultSet.getString("apellidoMaterno"));
                academicBodyHead.setEmailAddress(resultSet.getString("correo"));
                academicBodyHead.setPassword(resultSet.getString("contraseña"));
                academicBodyHead.setAlternateEmail(resultSet.getString("correoAlterno"));
                academicBodyHead.setPersonalNumber(resultSet.getString("NúmeroDePersonal"));
            }
            
            resultSet.close();
            dataBaseManager.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return academicBodyHead;
    }
    
}
