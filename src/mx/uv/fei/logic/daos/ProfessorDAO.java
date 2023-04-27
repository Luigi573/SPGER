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
    
}
