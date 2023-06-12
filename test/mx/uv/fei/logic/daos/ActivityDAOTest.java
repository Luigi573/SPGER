package mx.uv.fei.logic.daos;

import java.util.ArrayList;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.logic.domain.Activity;
import mx.uv.fei.logic.domain.statuses.ActivityStatus;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ActivityDAOTest {
    private static int researchId;
    private static Activity preloadedActivity;
    private static DataBaseManager dataBaseManager;
     
    @BeforeClass
    public static void setUpClass() {
        dataBaseManager = new DataBaseManager();
        PreparedStatement researchStatement;
        String researchQuery = "INSERT INTO Anteproyectos(título) VALUES(?)"; 
        
        try{
            researchStatement = dataBaseManager.getConnection().prepareStatement(researchQuery, Statement.RETURN_GENERATED_KEYS);
            researchStatement.setString(1, "Test research project");
            
            researchStatement.executeUpdate();
            ResultSet generatedResearch = researchStatement.getGeneratedKeys();
            
            if(generatedResearch.next()){
                researchId = generatedResearch.getInt(1);
            }
            
            //Preload an activity for the getActivityListTest() and modifyActivityTest() tests
            preloadedActivity = new Activity();
            preloadedActivity.setResearchId(researchId);
            preloadedActivity.setTitle("Preloaded Activity");
            preloadedActivity.setDescription("This is a preloaded activity to test getActivityList() & modifyActivity() methods");
            preloadedActivity.setStartDate(Date.valueOf(LocalDate.of(2023, Month.MAY, 12)));
            preloadedActivity.setDueDate(Date.valueOf(LocalDate.of(2023, Month.MAY, 19)));
            preloadedActivity.setStatus(ActivityStatus.ACTIVE);
            
            PreparedStatement activityStatement;
            String activityQuery = "INSERT INTO Actividades(IdAnteproyecto, título, descripción, fechaInicio, fechaFin) VALUES(?,?,?,?,?)";
            
            activityStatement = dataBaseManager.getConnection().prepareStatement(activityQuery, Statement.RETURN_GENERATED_KEYS);
            activityStatement.setInt(1, preloadedActivity.getResearchId());
            activityStatement.setString(2, preloadedActivity.getTitle());
            activityStatement.setString(3, preloadedActivity.getDescription());
            activityStatement.setDate(4, preloadedActivity.getStartDate());
            activityStatement.setDate(5, preloadedActivity.getDueDate());
            
            activityStatement.executeUpdate();
            ResultSet generatedActivity = activityStatement.getGeneratedKeys();
            
            if(generatedActivity.next()){
                preloadedActivity.setId(generatedActivity.getInt(1));
            }
        }catch(SQLException exception){
            fail("Test failed, couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }
    
    @AfterClass
    public static void tearDownClass() {
        PreparedStatement deleteStatement;
        String query = "DELETE FROM Anteproyectos WHERE IdAnteproyecto IN(?)";
        
        try{
            deleteStatement = dataBaseManager.getConnection().prepareStatement(query);
            deleteStatement.setInt(1, researchId);
            
            deleteStatement.executeUpdate();
        }catch(SQLException exception){
            fail("Failed to delete research, no DB connection could be established");
        }finally{
            dataBaseManager.closeConnection();
        }
    }

    @Test
    public void testAddActivity() throws Exception {
        System.out.println("Test 'ActivityDAO.addActivity()'");
        LocalDate startDate = LocalDate.of(2023, Month.MARCH, 26);
        LocalDate dueDate = LocalDate.of(2023, Month.DECEMBER, 2);
        
        
        Activity activity = new Activity();
        activity.setTitle("Add Activity Test");
        activity.setDescription("This activity is made to test ActivityDAO.addActivity");
        activity.setStartDate(Date.valueOf(startDate));
        activity.setDueDate(Date.valueOf(dueDate));
        activity.setResearchId(researchId);
        activity.setStatus(ActivityStatus.ACTIVE);
        
        ActivityDAO activityDAO = new ActivityDAO();
        
        int result = activityDAO.addActivity(activity);
        
        System.out.println("If total of affected rows is greater than 0, activity was created sucessfully");
        System.out.println("Generated activity Id: " + result);
        assertTrue(result > 0);
    }
    
    @Test
    public void testGetActivityList() throws Exception {
        ActivityDAO instance = new ActivityDAO();
        ArrayList<Activity> result = instance.getActivityList(researchId);
        
        System.out.println("If list is not empty, it means preloaded activity got retrieved sucessfully");
        
        assertTrue((!result.isEmpty()));
    }
    
    @Test
    public void testModifyActivity() throws Exception{
        Activity activity = preloadedActivity;
        activity.setTitle("Modified activity Test");
        activity.setDescription("This is a modification of the preloaded activity");
        ActivityDAO instance = new ActivityDAO();
        
        int result = instance.modifyActivity(activity);
        System.out.println("If affected rows is greater than 0, it means that activity was modified sucessfully");
        assertTrue(result > 0);
    }
    
    @Test
    public void testSetFeedback() throws Exception {
        Activity activity = new Activity();
        activity.setFeedback("");
        
    }
    
    @Test
    public void testAssertActivity() {
        Activity activity = preloadedActivity;
        ActivityDAO instance = new ActivityDAO();
        
        System.out.println("If it returns true, it means activity is neither blank or has invalid date");
        assertTrue(instance.assertActivity(activity));
    }
    
    @Test
    public void testIsBlank(){
        ActivityDAO instance = new ActivityDAO();
        
        System.out.println("IsBlank() will return true when either the title or description are blank");
        assertTrue(!instance.isBlank(preloadedActivity));
    }
    
    @Test
    public void testIsBlankFail() {
        System.out.println("isBlank() test with blank data");
        Activity activity = new Activity();
        activity.setTitle("Blank description activity");
        activity.setDescription("");
        
        System.out.println("IsBlank will return true when either the title or description are blank");
        System.out.println("Title: " + activity.getTitle());
        System.out.println("Description: " + activity.getDescription());
        
        ActivityDAO instance = new ActivityDAO();
        assertTrue(instance.isBlank(activity));
    }
    @Test
    public void testIsValidDateSuccess() {
        Activity activity = preloadedActivity;
        ActivityDAO instance = new ActivityDAO();
        
        System.out.println("If this is true, it means that startDate goes before or the same day as dueDate");
        System.out.println("Start date: " + activity.getStartDate());
        System.out.println("Due date: " + activity.getDueDate());
        assertTrue(instance.isValidDate(activity));
    }
    @Test
    public void testIsValidDateFail() {
        Activity activity = new Activity();
        ActivityDAO instance = new ActivityDAO();
        
        activity.setStartDate(Date.valueOf(LocalDate.of(2023, Month.MARCH, 20)));
        activity.setDueDate(Date.valueOf(LocalDate.of(2023, Month.MARCH, 1)));
        
        System.out.println("If this is true, it means that startDate goes before or the same day as dueDate");
        System.out.println("Start date: " + activity.getStartDate());
        System.out.println("Due date: " + activity.getDueDate());
        assertTrue(!instance.isValidDate(activity));
    }

    @Test
    public void testIsValidTitle(){
        Activity activity = new Activity();
        activity.setTitle("This is a valid title for an activity");
        ActivityDAO instance = new ActivityDAO();
        
        assertTrue(instance.isValidTitle(activity));
    }
    
    @Test
    public void testIsValidTitleFail(){
        Activity activity = new Activity();
        activity.setTitle("This is not a valid title for an activity. "
                + "It is too long to actually display it and the DB only accepts up to 50 char");
        ActivityDAO instance = new ActivityDAO();
        
        assertTrue(!instance.isValidTitle(activity));
    }
}
