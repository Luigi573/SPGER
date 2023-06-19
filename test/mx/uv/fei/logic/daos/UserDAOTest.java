package mx.uv.fei.logic.daos;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.logic.domain.Student;
import mx.uv.fei.logic.domain.User;
import mx.uv.fei.logic.exceptions.DataRetrievalException;

public class UserDAOTest {
    private static DataBaseManager dataBaseManager;
    private static User preloadedUser;
    private static User failedUser;

    @BeforeClass
    public static void setUpClass() {
        dataBaseManager = new DataBaseManager();
        
        try{
            //Adding a User
            preloadedUser = new Student();
            preloadedUser.setEmailAddress("abrvaqu862@uv.mx");
            preloadedUser.setAlternateEmail("abrvaqu999@gmail.com");

            String userQuery = "INSERT INTO Usuarios(correo, correoAlterno) VALUES (?, ?)";
            PreparedStatement userStatement = dataBaseManager.getConnection().prepareStatement(userQuery, Statement.RETURN_GENERATED_KEYS);
            userStatement.setString(1, preloadedUser.getEmailAddress());
            userStatement.setString(2, preloadedUser.getAlternateEmail());
            userStatement.executeUpdate();
            
            ResultSet generatedUserKeys = userStatement.getGeneratedKeys();
            if(generatedUserKeys.next()){
                preloadedUser.setUserId(generatedUserKeys.getInt(1));
            }
            generatedUserKeys.close();
            userStatement.close();

        
            //Set data to a failed professor
            failedUser = new Student();
            failedUser.setEmailAddress("lurojumo892@gmail.com");
            failedUser.setAlternateEmail("lurojumo783@gmail.com");
        }catch(SQLException exception){
            fail("Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }
    
    @AfterClass
    public static void tearDownClass(){
        PreparedStatement statement;
        String queryToDeleteUser = "DELETE FROM Usuarios WHERE IdUsuario = ?";
        
        try{
            statement = dataBaseManager.getConnection().prepareStatement(queryToDeleteUser);
            statement.setInt(1, preloadedUser.getUserId());
            statement.executeUpdate();
            statement.close();
        }catch(SQLException exception){
            fail("Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }

    @Test
    public void theEmailIsAvailableToUseToRegisterTest(){
        try {
            UserDAO instance = new UserDAO();
            assertTrue(!instance.theEmailIsAvailableToUseToRegister(preloadedUser.getEmailAddress()));
        }catch(DataRetrievalException exception){
            fail("Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }

    @Test
    public void theEmailIsAvailableToUseToRegisterTestFail(){
        try {
            UserDAO instance = new UserDAO();
            assertTrue(instance.theEmailIsAvailableToUseToRegister(failedUser.getEmailAddress()));
        }catch(DataRetrievalException exception){
            fail("Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }

    @Test
    public void theAlternateEmailIsAvailableToUseToRegisterTest(){
        try {
            UserDAO instance = new UserDAO();
            assertTrue(!instance.theAlternateEmailIsAvailableToRegister(preloadedUser.getAlternateEmail()));
        }catch(DataRetrievalException exception){
            fail("Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }

    @Test
    public void theAlternateEmailIsAvailableToUseToRegisterTestFail(){
        try {
            UserDAO instance = new UserDAO();
            assertTrue(instance.theAlternateEmailIsAvailableToRegister(failedUser.getAlternateEmail()));
        }catch(DataRetrievalException exception){
            fail("Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }

    @Test
    public void theEmailIsAvailableToUseToModifyTest(){
        try {
            UserDAO instance = new UserDAO();
            assertTrue(instance.theEmailIsAvailableToUseToModify(preloadedUser.getEmailAddress(), preloadedUser.getUserId()));
        }catch(DataRetrievalException exception){
            fail("Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }

    @Test
    public void theEmailIsAvailableToUseToModifyTestFail(){
        try {
            UserDAO instance = new UserDAO();
            assertTrue(instance.theEmailIsAvailableToUseToModify(failedUser.getEmailAddress(), failedUser.getUserId()));
        }catch(DataRetrievalException exception){
            fail("Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }

    @Test
    public void theAlternateEmailIsAvailableToUseToModifyTest(){
        try {
            UserDAO instance = new UserDAO();
            assertTrue(instance.theAlternateEmailIsAvailableToModify(preloadedUser.getAlternateEmail(), preloadedUser.getUserId()));
        }catch(DataRetrievalException exception){
            fail("Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }

    @Test
    public void theAlternateEmailIsAvailableToUseToModifyTestFail(){
        try {
            UserDAO instance = new UserDAO();
            assertTrue(instance.theAlternateEmailIsAvailableToModify(failedUser.getAlternateEmail(), failedUser.getUserId()));
        }catch(DataRetrievalException exception){
            fail("Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }
}
