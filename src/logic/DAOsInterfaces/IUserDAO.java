package logic.DAOsInterfaces;

import logic.domain.User;
import java.util.ArrayList;
import logic.exceptions.DataRetrievalException;

public interface IUserDAO {
    public void addUser(User user);
    public ArrayList<User> getUserList() throws DataRetrievalException;
}
