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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllUsers'");
    }
    
}
