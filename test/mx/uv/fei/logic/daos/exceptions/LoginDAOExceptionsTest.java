package mx.uv.fei.logic.daos.exceptions;

import mx.uv.fei.logic.daos.LoginDAO;
import mx.uv.fei.logic.exceptions.LoginException;
import org.junit.Test;
import static org.junit.Assert.*;

public class LoginDAOExceptionsTest {
    
    public LoginDAOExceptionsTest() {
        
    }

    @Test(expected = LoginException.class)
    public void testLogInStudent() throws LoginException {
        LoginDAO instance = new LoginDAO();
        
        instance.logInStudent("", "");
    }

    @Test(expected = LoginException.class)
    public void testLogInProfessor() throws LoginException {
        LoginDAO instance = new LoginDAO();
        
        instance.logInProfessor("", "");
    }

    @Test(expected = LoginException.class)
    public void testLogInAdmin() throws LoginException {
        LoginDAO instance = new LoginDAO();
        
        instance.logInAdmin("", "");
    }

    @Test(expected = LoginException.class)
    public void testLogInAcademicBodyHead() throws LoginException {
        LoginDAO instance = new LoginDAO();
        
        instance.logInAcademicBodyHead("", "");
    }
}
