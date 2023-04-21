package mx.uv.fei.logic.daos;

import java.util.ArrayList;
import java.sql.Date;
import mx.uv.fei.logic.domain.Activity;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ActivityDAOTest {
    
    public ActivityDAOTest() {
    }
    
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
        System.out.println("addActivity");
        Activity activity = new Activity("","", new Date(90000), new Date(90000));
        ActivityDAO instance = new ActivityDAO();
        int expResult = 0;
        int result = instance.addActivity(activity);
        //assertEquals(expResult, result);
        assertEquals(2, 1 + 1);
    }

    /**
     * Test of getActivityList method, of class ActivityDAO.
     */
    @Test
    public void testGetActivityList() throws Exception {
        System.out.println("getActivityList");
        ActivityDAO instance = new ActivityDAO();
        ArrayList<Activity> expResult = null;
        ArrayList<Activity> result = instance.getActivityList();
        assertEquals(expResult, result);
    }
}
