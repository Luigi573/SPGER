package mx.uv.fei.logic.daos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.logic.daosinterfaces.IUserDAO;
import mx.uv.fei.logic.domain.User;

public class UserDAO implements IUserDAO{

    @Override
    public void addUser(User user) {
         
    }

    @Override
    public ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        try {
            DataBaseManager dataBaseManager = new DataBaseManager();
            Statement statement = dataBaseManager.getConnection().createStatement();
            String query = "SELECT * FROM Usuarios ";
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                User user = new User();
                user.setName(resultSet.getString("nombre"));
                user.setFirstSurname(resultSet.getString("apellidoPaterno"));
                user.setSecondSurname(resultSet.getString("apellidoMaterno"));
                user.setEmailAddress(resultSet.getString("correo"));
                user.setPassword(resultSet.getString("contraseña"));
                user.setAlternateEmail(resultSet.getString("correoAlterno"));
                if(resultSet.getString("IdEstudiante") != null){
                    user.setUserType(resultSet.getString("correoAlterno"));
                }


                user.setStartDate(resultSet.getString("fechaInicio"));
                user.setFinishDate(resultSet.getString("fechaFin"));
                user.setWaitedResults(resultSet.getString("resultadosEsperados"));
                user.setNote(resultSet.getString("nota"));
                users.add(user);
            }
            resultSet.close();
            dataBaseManager.getConnection().close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
    
}
