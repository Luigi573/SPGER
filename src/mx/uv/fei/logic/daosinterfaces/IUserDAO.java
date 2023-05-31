package mx.uv.fei.logic.daosinterfaces;

import java.util.ArrayList;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.domain.User;

public interface IUserDAO {
    public int addUser(User user);
    public User getUser();
    public ArrayList<User> getAllUsers();
}
