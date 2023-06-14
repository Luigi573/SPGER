package mx.uv.fei.logic.daos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.logic.domain.Advance;
import mx.uv.fei.logic.exceptions.DataInsertionException;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import org.junit.After;
import org.junit.Before;

public class AdvanceDAOTest {
    private static DataBaseManager dataBaseManager;
    private static int activityId;
    private static Advance preloadedAdvance;
    private static int nextAvailableId;
    
    @BeforeClass
    public static void setUpClass() {
        dataBaseManager = new DataBaseManager();
        PreparedStatement activityStatement;
        String activityQuery = "INSERT INTO Actividades(título, descripción, fechaInicio, fechaFin) VALUES(?,?,?,?)";
        
        try{
            activityStatement = dataBaseManager.getConnection().prepareStatement(activityQuery, Statement.RETURN_GENERATED_KEYS);
            
            activityStatement.setString(1, "Preloaded Activity");
            activityStatement.setString(2, "This is a preloaded activity to test Advance methods");
            activityStatement.setDate(3, Date.valueOf(LocalDate.of(2023, Month.MAY, 12)));
            activityStatement.setDate(4, Date.valueOf(LocalDate.of(2023, Month.MAY, 19)));
            
            activityStatement.executeUpdate();
            ResultSet generatedActivity = activityStatement.getGeneratedKeys();
            
            if(generatedActivity.next()){
                activityId = generatedActivity.getInt(1);
                System.out.println(activityId);
            }
            
            preloadedAdvance = new Advance();
            preloadedAdvance.setActivityID(activityId);
            
            String advanceQuery = "INSERT INTO Avances(IdActividad, título, comentario, fecha) VALUES(?,?,?,?)";
            PreparedStatement advanceStatement = dataBaseManager.getConnection().prepareStatement(advanceQuery, Statement.RETURN_GENERATED_KEYS);
            
            advanceStatement.setInt(1, activityId);
            advanceStatement.setString(2, "Preloaded advance");
            advanceStatement.setString(3, "This is a preloaded advance for testing getAdvanceList()");
            advanceStatement.setDate(4, Date.valueOf(LocalDate.now()));
            
            advanceStatement.executeUpdate();
            ResultSet generatedAdvanceKeys = advanceStatement.getGeneratedKeys();
            
            if(generatedAdvanceKeys.next()){
                preloadedAdvance.setAdvanceID(generatedAdvanceKeys.getInt(1));
            }
            
            nextAvailableId = preloadedAdvance.getAdvanceID() + 1;
        }catch(SQLException exception){
            fail("Test failed, couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }
    
    @AfterClass
    public static void tearDownClass() {
        PreparedStatement statement;
        String query = "DELETE FROM Actividades WHERE IdActividad IN(?)";
        
        try{
            statement = dataBaseManager.getConnection().prepareStatement(query);
            
            statement.setInt(1, activityId);
            statement.executeUpdate();
        }catch(SQLException exception){
            fail("Couldn't delete preloaded activity. No DB connection");
        }
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    ///////     Don't mind these tests, they aint mine and they're wrong
    @Test
    public void testAddAdvanceSuccesful() throws DataInsertionException {
        System.out.println("addAdvance");
        Advance advance = new Advance();
        System.out.println(activityId);
        advance.setActivityID(activityId);
        advance.setTitle("Preloaded advance");
        advance.setComment("Este avance es una prueba");
        advance.setFileID(0);
        AdvanceDAO instance = new AdvanceDAO();
        int expResult = nextAvailableId;
        int result = instance.addAdvance(advance);
        assertEquals(expResult, result);
    }
    
    @Test(expected = DataInsertionException.class)
    public void testAddAdvanceFail() throws DataInsertionException {
        System.out.println("addAdvance");
        Advance advance = new Advance();
        advance.setAdvanceID(0);
        advance.setTitle("Preloaded advance");
        advance.setComment("This is a preloaded advance for testing getAdvanceList()");
        AdvanceDAO instance = new AdvanceDAO();
        int expResult = 0;
        int result = instance.addAdvance(advance);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGetAdvanceList() throws DataRetrievalException {
        AdvanceDAO instance = new AdvanceDAO();
        ArrayList<Advance> advanceList = instance.getActivityAdvanceList(activityId);
        
        System.out.println("Retrieved advance data: ");
        System.out.println("Title: " + advanceList.get(0).getTitle());
        System.out.println("Comment: " + advanceList.get(0).getComment());
        System.out.println("Date: " + advanceList.get(0).getDate());
        
        assertTrue(!advanceList.isEmpty());
    }

    @Test
    public void testGetAdvanceByID() throws DataRetrievalException {
        System.out.println("getAdvanceByID");
        int advanceID = 8;
        AdvanceDAO instance = new AdvanceDAO();
        Advance advance1 = new Advance();
        advance1.setAdvanceID(8);
        advance1.setTitle("Avance de prueba unitaria num2");
        advance1.setComment("Este avance también es una prueba");
        Advance expResult = advance1;
        Advance result = instance.getAdvanceByID(advanceID);
        System.out.println(expResult);
        System.out.println(result);
        System.out.println(expResult.equals(result));
        assertEquals(expResult, result);
        fail("This test case is incomplete");
    }    
    
    //This test is correct
    @Test
    public void testSetFeedback() throws DataInsertionException{
        preloadedAdvance.setFeedback("Avance bien hecho, tiene 10");
        
        AdvanceDAO instance = new AdvanceDAO();
        int result = instance.setFeedback(preloadedAdvance);
        assertTrue(result > 0);
    }

    /**
     * Test of updateAdvanceInfo method, of class AdvanceDAO.
     */
    @Test
    public void testUpdateAdvanceInfoSuccessful() throws DataRetrievalException {
        System.out.println("updateAdvanceInfo Success");
        int advanceToBeUpdatedID = preloadedAdvance.getAdvanceID();
        Advance newAdvanceInfo = new Advance();
        newAdvanceInfo.setTitle("Avance modificado por pruebas");
        newAdvanceInfo.setComment("Este avance se modifico en un test de junit4");
        AdvanceDAO instance = new AdvanceDAO();
        int expResult = 1;
        int result = instance.updateAdvanceInfo(advanceToBeUpdatedID, newAdvanceInfo);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testUpdateAdvanceInfoFail() throws DataRetrievalException {
        System.out.println("updateAdvanceInfo Fail");
        int advanceToBeUpdatedID = preloadedAdvance.getAdvanceID() + 1;
        Advance newAdvanceInfo = new Advance();
        newAdvanceInfo.setTitle("Avance modificado por pruebas");
        newAdvanceInfo.setComment("Este avance se modifico en un test de junit4");
        AdvanceDAO instance = new AdvanceDAO();
        int expResult = 0;
        int result = instance.updateAdvanceInfo(advanceToBeUpdatedID, newAdvanceInfo);
        assertEquals(expResult, result);
    }
}
