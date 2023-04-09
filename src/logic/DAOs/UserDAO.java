package logic.DAOs;

import java.util.ArrayList;
import logic.DAOsInterfaces.IUserDAO;
import logic.domain.User;

public class UserDAO implements IUserDAO{

    @Override
    public void addUser(User user) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public ArrayList<User> getUserList() {
        
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
}
