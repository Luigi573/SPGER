package mx.uv.fei.logic.daos;

import java.util.ArrayList;
import mx.uv.fei.logic.daosinterfaces.IUserDAO;
import mx.uv.fei.logic.domain.User;

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
