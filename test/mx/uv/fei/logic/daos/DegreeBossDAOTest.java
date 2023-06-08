package mx.uv.fei.logic.daos;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.logic.domain.DegreeBoss;
import mx.uv.fei.logic.domain.statuses.ProfessorStatus;
import mx.uv.fei.logic.exceptions.DataInsertionException;
import mx.uv.fei.logic.exceptions.DataRetrievalException;

public class DegreeBossDAOTest {
    private static DataBaseManager dataBaseManager;
    private static DegreeBoss preloadedDegreeBoss;
    private static DegreeBoss failedDegreeBoss;
    private static DegreeBoss preloadedDegreeBossForAddDegreeBossTest;

    @BeforeClass
    public static void setUpClass() {
        dataBaseManager = new DataBaseManager();
        
        try{
            //Adding a DegreeBoss
            preloadedDegreeBoss = new DegreeBoss();
            preloadedDegreeBoss.setName("Humberto");
            preloadedDegreeBoss.setFirstSurname("Salazar");
            preloadedDegreeBoss.setSecondSurname("Vasquez");
            preloadedDegreeBoss.setEmailAddress("husava124@gmail.com");
            preloadedDegreeBoss.setAlternateEmail("husava2294@gmail.com");
            preloadedDegreeBoss.setPhoneNumber("2283492758");
            preloadedDegreeBoss.setStatus(ProfessorStatus.ACTIVE.getValue());
            preloadedDegreeBoss.setStaffNumber(489247839);

            String userQuery = "INSERT INTO Usuarios(nombre, apellidoPaterno, apellidoMaterno, correo, correoAlterno, númeroTeléfono, estado) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement userStatement = dataBaseManager.getConnection().prepareStatement(userQuery, Statement.RETURN_GENERATED_KEYS);
            userStatement.setString(1, preloadedDegreeBoss.getName());
            userStatement.setString(2, preloadedDegreeBoss.getFirstSurname());
            userStatement.setString(3, preloadedDegreeBoss.getSecondSurname());
            userStatement.setString(4, preloadedDegreeBoss.getEmailAddress());
            userStatement.setString(5, preloadedDegreeBoss.getAlternateEmail());
            userStatement.setString(6, preloadedDegreeBoss.getPhoneNumber());
            userStatement.setString(7, preloadedDegreeBoss.getStatus());
            userStatement.executeUpdate();
            
            ResultSet generatedUserKeys = userStatement.getGeneratedKeys();
            if(generatedUserKeys.next()){
                preloadedDegreeBoss.setUserId(generatedUserKeys.getInt(1));
            }
            generatedUserKeys.close();
            userStatement.close();
            
            String professorQuery = "INSERT INTO Profesores(NumPersonal, IdUsuario) VALUES (?,?)";
            PreparedStatement professorStatement = dataBaseManager.getConnection().prepareStatement(professorQuery);
            professorStatement.setInt(1, preloadedDegreeBoss.getStaffNumber());
            professorStatement.setInt(2, preloadedDegreeBoss.getUserId());
            professorStatement.executeUpdate();
            professorStatement.close();

            String degreeBossQuery = "INSERT INTO JefesCarrera(NumPersonal) VALUES (?)";
            PreparedStatement degreeBoss = dataBaseManager.getConnection().prepareStatement(degreeBossQuery);
            degreeBoss.setInt(1, preloadedDegreeBoss.getStaffNumber());
            degreeBoss.executeUpdate();
            degreeBoss.close();
            
            //Set data to a failed DegreeBoss
            failedDegreeBoss = new DegreeBoss();
            failedDegreeBoss.setName("Paloma");
            failedDegreeBoss.setFirstSurname("Melarejo");
            failedDegreeBoss.setSecondSurname("Gutierrez");
            failedDegreeBoss.setEmailAddress("pamegu289@gmail.com");
            failedDegreeBoss.setAlternateEmail("pamegu324@gmail.com");
            failedDegreeBoss.setPhoneNumber("2287299111");
            failedDegreeBoss.setStatus(ProfessorStatus.INACTIVE.getValue());
            failedDegreeBoss.setStaffNumber(982758932);

            //Set data to a Professor to use in addDegreeBossTest
            preloadedDegreeBossForAddDegreeBossTest = new DegreeBoss();
            preloadedDegreeBossForAddDegreeBossTest.setName("Rubén");
            preloadedDegreeBossForAddDegreeBossTest.setFirstSurname("Gomez");
            preloadedDegreeBossForAddDegreeBossTest.setSecondSurname("Aguirre");
            preloadedDegreeBossForAddDegreeBossTest.setEmailAddress("rugoag394@gmail.com");
            preloadedDegreeBossForAddDegreeBossTest.setStaffNumber(492394829);
        }catch(SQLException exception){
            fail("Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }
    
    @AfterClass
    public static void tearDownClass() {
        PreparedStatement statement;
        String queryToDeleteUser = "DELETE FROM Usuarios WHERE IdUsuario = ? || IdUsuario = ?";
        String queryToDeleteProfessor = "DELETE FROM Profesores WHERE NumPersonal = ? || NumPersonal = ?";
        String queryToDeletedegreeBoss = "DELETE FROM JefesCarrera WHERE NumPersonal = ? || NumPersonal = ?";
        
        try{
            statement = dataBaseManager.getConnection().prepareStatement(queryToDeleteUser);
            statement.setInt(1, preloadedDegreeBoss.getUserId());
            statement.setInt(2, preloadedDegreeBossForAddDegreeBossTest.getUserId());
            statement.executeUpdate();

            statement = dataBaseManager.getConnection().prepareStatement(queryToDeleteProfessor);
            statement.setInt(1, preloadedDegreeBoss.getStaffNumber());
            statement.setInt(2, preloadedDegreeBossForAddDegreeBossTest.getStaffNumber());
            statement.executeUpdate();

            statement = dataBaseManager.getConnection().prepareStatement(queryToDeletedegreeBoss);
            statement.setInt(1, preloadedDegreeBoss.getStaffNumber());
            statement.setInt(2, preloadedDegreeBossForAddDegreeBossTest.getStaffNumber());
            statement.executeUpdate();

            statement.close();
        }catch(SQLException exception){
            fail("Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }

    @Test
    public void addDegreeBossTest() {
        try {
            DegreeBossDAO instance = new DegreeBossDAO();
            int result = instance.addDegreeBoss(preloadedDegreeBossForAddDegreeBossTest);
            assertTrue(result > 0);
        }catch(DataInsertionException exception){
            fail("Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }

    @Test
    public void getDegreeBossTest() {
        try {
            DegreeBossDAO instance = new DegreeBossDAO();
            DegreeBoss result = instance.getDegreeBoss(preloadedDegreeBoss.getStaffNumber());
            assertTrue(result.equals(preloadedDegreeBoss));
        }catch(DataRetrievalException exception){
            fail("Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }

    @Test
    public void getDegreeBossTestFail() {
        try {
            DegreeBossDAO instance = new DegreeBossDAO();
            DegreeBoss result = instance.getDegreeBoss(preloadedDegreeBoss.getStaffNumber());
            assertTrue(!result.equals(failedDegreeBoss));
        }catch(DataRetrievalException exception){
            fail("Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }

    @Test
    public void getDegreeBossesTest() {
        try {
            DegreeBossDAO instance = new DegreeBossDAO();
            ArrayList<DegreeBoss> result = instance.getDegreeBosses();
            assertTrue(result.contains(preloadedDegreeBoss));
        }catch(DataRetrievalException exception){
            fail("Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }

    @Test
    public void getDegreeBossesTestFail() {
        try {
            DegreeBossDAO instance = new DegreeBossDAO();
            ArrayList<DegreeBoss> result = instance.getDegreeBosses();
            assertTrue(!result.contains(failedDegreeBoss));
        }catch(DataRetrievalException exception){
            fail("Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }

    @Test
    public void getSpecifiedDegreeBossesTest() {
        try {
            DegreeBossDAO instance = new DegreeBossDAO();
            ArrayList<DegreeBoss> result = instance.getSpecifiedDegreeBosses("C");
            assertTrue(result.contains(preloadedDegreeBoss));
        }catch(DataRetrievalException exception){
            fail("Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }

    @Test
    public void getSpecifiedDegreeBossesTestFail() {
        try {
            DegreeBossDAO instance = new DegreeBossDAO();
            ArrayList<DegreeBoss> result = instance.getSpecifiedDegreeBosses("H");
            assertTrue(!result.contains(failedDegreeBoss));
        }catch(DataRetrievalException exception){
            fail("Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }

    @Test
    public void modifyDegreeBossDataTest() {
        try {
            DegreeBossDAO instance = new DegreeBossDAO();
            preloadedDegreeBoss.setName("Cotorro");
            preloadedDegreeBoss.setFirstSurname("Sanchez");
            preloadedDegreeBoss.setSecondSurname("Natalio");
            preloadedDegreeBoss.setEmailAddress("cosana901@gmail.com");
            int result = instance.modifyDegreeBossData(preloadedDegreeBoss);
            assertTrue(result > 0);
        }catch(DataInsertionException exception){
            fail("Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }
}
