package mx.uv.fei.logic.daosinterfaces;

import mx.uv.fei.logic.exceptions.DataInsertionException;
import mx.uv.fei.logic.exceptions.DataRetrievalException;

public interface IUserDAO{
    public boolean theEmailIsAvailableToUseToRegister(String email) throws DataRetrievalException;
    public boolean theAlternateEmailIsAvailableToRegister(String alternateEmail) throws DataRetrievalException;
    public boolean theEmailIsAvailableToUseToModify(String email, int userId) throws DataRetrievalException;
    public boolean theAlternateEmailIsAvailableToModify(String alternateEmail, int userId) throws DataRetrievalException;
    public int updatePassword(String userPassword, int userId) throws DataInsertionException;
    public boolean hasUsersInTheDatabase() throws DataRetrievalException;
}
