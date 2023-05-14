package mx.uv.fei.logic.daos;

import java.util.ArrayList;
import java.sql.Date;
import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.logic.domain.Activity;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ActivityDAOTest {
     
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testAddActivity() throws Exception {
        Activity activity = new Activity();
        activity.setTitle("Actividad 1");
        activity.setDescription("Caso de prueba");
        activity.setStartDate(new Date(54596958));
        activity.setDueDate(new Date(65565888));
        
        ActivityDAO activityDAO = new ActivityDAO();
        
        int result = activityDAO.addActivity(activity);
        assertTrue("If total of affected rows is greater than 0, activity was created sucessfully", (result > 0));
    }
    @Test
    public void testGetActivityList() throws Exception {
        System.out.println("getActivityList");
        ActivityDAO instance = new ActivityDAO();
        //ArrayList<Activity> result = instance.getActivityList();
        
        //assertTrue("", (!result.isEmpty()));
    }
    @Test
    public void testModifyActivity() throws Exception{
        System.out.println("modifyActivity");
        Activity activity = null;
        ActivityDAO instance = new ActivityDAO();
        int expResult = 0;
        int result = instance.modifyActivity(activity);
        assertEquals(expResult, result);
    }
    @Test
    public void testAssertActivity() {
        System.out.println("assertActivity");
        Activity activity = null;
        ActivityDAO instance = new ActivityDAO();
        boolean expResult = false;
        boolean result = instance.assertActivity(activity);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    @Test
    public void testIsBlank() {
        System.out.println("isBlank");
        Activity activity = null;
        ActivityDAO instance = new ActivityDAO();
        boolean expResult = false;
        boolean result = instance.isBlank(activity);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    @Test
    public void testIsNull() {
        System.out.println("isNull");
        Activity activity = null;
        ActivityDAO instance = new ActivityDAO();
        boolean expResult = false;
        boolean result = instance.isNull(activity);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    @Test
    public void testIsValidDate() {
        System.out.println("isValidDate");
        Activity activity = null;
        ActivityDAO instance = new ActivityDAO();
        boolean expResult = false;
        boolean result = instance.isValidDate(activity);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
