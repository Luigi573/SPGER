/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package mx.uv.fei.logic.daos.exceptions;

import java.util.ArrayList;
import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.logic.daos.AdvanceDAO;
import mx.uv.fei.logic.domain.Advance;
import mx.uv.fei.logic.exceptions.DataInsertionException;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 *
 * @author Jesús Manuel
 */
public class AdvanceDAOExceptionTest {
    private static DataBaseManager dataBaseManager;
    private static int activityId;
    private static Advance preloadedAdvance;
    private static int nextAvailableId;
    
    public AdvanceDAOExceptionTest() {
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

    @Rule
    public ExpectedException dataInsertionExceptionRule = ExpectedException.none();
    @Rule
    public ExpectedException dataRetrievalExceptionRule = ExpectedException.none();
    /**
     * Test of addAdvance method, of class AdvanceDAO.
     */
    @Test
    public void testAddAdvanceException() throws DataInsertionException {
        System.out.println("addAdvance");
        
        dataInsertionExceptionRule.expect(DataInsertionException.class);
        dataInsertionExceptionRule.expectMessage("No se pudo guardar la información del avance. Por favor intente de nuevo más tarde.");
        
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

    /**
     * Test of getAdvancesList method, of class AdvanceDAO.
     */
    @Test
    public void testGetAdvancesList() throws Exception {
        System.out.println("getAdvancesList");
        AdvanceDAO instance = new AdvanceDAO();
        ArrayList<Advance> expResult = null;
        ArrayList<Advance> result = instance.getAdvancesList();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getActivityAdvanceList method, of class AdvanceDAO.
     */
    @Test
    public void testGetActivityAdvanceList() throws Exception {
        System.out.println("getActivityAdvanceList");
        int activityId = 0;
        AdvanceDAO instance = new AdvanceDAO();
        ArrayList<Advance> expResult = null;
        ArrayList<Advance> result = instance.getActivityAdvanceList(activityId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setFeedback method, of class AdvanceDAO.
     */
    @Test
    public void testSetFeedback() throws Exception {
        System.out.println("setFeedback");
        Advance advance = null;
        AdvanceDAO instance = new AdvanceDAO();
        int expResult = 0;
        int result = instance.setFeedback(advance);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAdvanceByID method, of class AdvanceDAO.
     */
    @Test
    public void testGetAdvanceByID() throws Exception {
        System.out.println("getAdvanceByID");
        int advanceID = 0;
        AdvanceDAO instance = new AdvanceDAO();
        Advance expResult = null;
        Advance result = instance.getAdvanceByID(advanceID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateAdvanceInfo method, of class AdvanceDAO.
     */
    @Test
    public void testUpdateAdvanceInfoException() throws DataRetrievalException {
        System.out.println("updateAdvanceInfo");
        int advanceToBeUpdatedID = 0;
        
        dataRetrievalExceptionRule.expect(DataRetrievalException.class);
        dataRetrievalExceptionRule.expectMessage("La información del avance con ID " + advanceToBeUpdatedID + " no pudo ser modificada. Por favor, intente de nuevo más tarde.");
        
        Advance newAdvanceInfo = null;
        AdvanceDAO instance = new AdvanceDAO();
        int expResult = 0;
        int result = instance.updateAdvanceInfo(advanceToBeUpdatedID, newAdvanceInfo);
        assertEquals(expResult, result);
    }
    
}
