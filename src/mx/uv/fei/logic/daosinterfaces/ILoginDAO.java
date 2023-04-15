package mx.uv.fei.logic.daosinterfaces;

import mx.uv.fei.logic.domain.User;
import mx.uv.fei.logic.exceptions.LoginException;

public interface ILoginDAO {
    public boolean logIn(String EmailAddress, String password) throws LoginException;
}
