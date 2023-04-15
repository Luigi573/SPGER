package mx.uv.fei.logic.daos;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.logic.daosinterfaces.ILoginDAO;
import mx.uv.fei.logic.domain.User;
import mx.uv.fei.logic.exceptions.LoginException;

public class LoginDAO implements ILoginDAO{
    private DataBaseManager dataBaseManager;
    
    public LoginDAO(){
        dataBaseManager = new DataBaseManager();
    }
    
    @Override
    public boolean logIn(String emailAddress, String password) throws LoginException{
        PreparedStatement statement; ResultSet resultSet;
        String query = "SELECT * FROM Usuarios WHERE correo IN('?') AND contraseña IN('?')";
        
        try{
            statement = dataBaseManager.getConnection().prepareStatement(query);
            statement.setString(1, emailAddress);
            statement.setString(2, password);
            
            resultSet = statement.executeQuery(query);
            
            if(resultSet.next()){
                return true;
            }
        }catch(SQLException exception){
            throw new LoginException(exception.getMessage());
        }finally{
            dataBaseManager.closeConnection();
        }
        
        return false;
    }
}