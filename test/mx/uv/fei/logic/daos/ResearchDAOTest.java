package mx.uv.fei.logic.daos;

import java.util.ArrayList;
import mx.uv.fei.logic.domain.ResearchProject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ResearchDAOTest {
    
    public ResearchDAOTest() {
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
    public void testAddResearch() throws Exception {
        System.out.println("addResearch");
        ResearchProject research = null;    
        ResearchDAO instance = new ResearchDAO();
        int expResult = 0;
        int result = instance.addResearch(research);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetResearchProjectList() throws Exception {
        System.out.println("getResearchProjectList");
        ResearchDAO instance = new ResearchDAO();
        ArrayList<ResearchProject> expResult = null;
        ArrayList<ResearchProject> result = instance.getResearchProjectList();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testModifyResearch() throws Exception {
        System.out.println("modifyResearch");
        ResearchProject research = null;
        ResearchDAO instance = new ResearchDAO();
        int expResult = 0;
        int result = instance.modifyResearch(research);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testAssertResearch() {
        System.out.println("assertResearch");
        ResearchProject research = null;
        ResearchDAO instance = new ResearchDAO();
        boolean expResult = false;
        boolean result = instance.assertResearch(research);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testIsBlank() {
        System.out.println("isBlank");
        ResearchProject research = null;
        ResearchDAO instance = new ResearchDAO();
        boolean expResult = false;
        boolean result = instance.isBlank(research);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testIsValidDate() {
        System.out.println("isValidDate");
        ResearchProject research = null;
        ResearchDAO instance = new ResearchDAO();
        boolean expResult = false;
        boolean result = instance.isValidDate(research);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }
    
}
