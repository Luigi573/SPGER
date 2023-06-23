package mx.uv.fei.logic.daos.exceptions;

import org.junit.Test;

import mx.uv.fei.logic.daos.ActivityDAO;
import mx.uv.fei.logic.domain.Activity;
import mx.uv.fei.logic.exceptions.DataInsertionException;
import mx.uv.fei.logic.exceptions.DataRetrievalException;

//It returns exception since attributes have not been initializated, which causes an SQLException on purpose
public class ActivityDAOExceptionsTest {
    
    public ActivityDAOExceptionsTest() {
    }
    
    
    @Test(expected = DataInsertionException.class)
    public void testAddActivity() throws DataInsertionException{
        Activity activity = new Activity();
        ActivityDAO instance = new ActivityDAO();
        
        instance.addActivity(activity);
    }

    @Test(expected = DataRetrievalException.class)
    public void testGetActivityList() throws DataRetrievalException{
        ActivityDAO instance = new ActivityDAO();
        
        instance.getActivityList(0);
    }

    @Test(expected = DataInsertionException.class)
    public void testModifyActivity() throws Exception {
        Activity activity = new Activity();
        ActivityDAO instance = new ActivityDAO();
        
        instance.addActivity(activity);
    }

    @Test(expected = DataInsertionException.class)
    public void testSetComment() throws Exception {
        Activity activity = new Activity();
        ActivityDAO instance = new ActivityDAO();
        
        instance.addActivity(activity);
    }

    @Test(expected = DataInsertionException.class)
    public void testSetFeedback() throws Exception {
        Activity activity = new Activity();
        ActivityDAO instance = new ActivityDAO();
        
        instance.addActivity(activity);
    }    
}
