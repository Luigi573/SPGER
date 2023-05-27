package mx.uv.fei.logic.daos;

import java.util.ArrayList;
import mx.uv.fei.logic.domain.Advance;
import mx.uv.fei.logic.exceptions.DataInsertionException;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class AdvanceDAOTest {
    
    public AdvanceDAOTest() {
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

    /**
     * Test of addAdvance method, of class AdvanceDAO.
     */
    @Test
    public void testAddAdvance() throws DataInsertionException {
        System.out.println("addAdvance");
        Advance advance = new Advance();
        advance.setActivityID(1);
        advance.setFileID(5);
        advance.setTitle("Avance de prueba unitaria");
        advance.setComments("Este avance es una prueba");
        advance.setState("Sin revisar");
        AdvanceDAO instance = new AdvanceDAO();
        int expResult = 1;
        int result = instance.addAdvance(advance);
        assertEquals(expResult, result);
    }

    /**
     * Test of getAdvancesList method, of class AdvanceDAO.
     */
    @Test
    public void testGetAdvancesList() throws DataRetrievalException {
        System.out.println("getAdvancesList");
        AdvanceDAO instance = new AdvanceDAO();
        Advance advance1 = new Advance();
        Advance advance2 = new Advance();
        advance1.setAdvanceID(7);
        advance2.setAdvanceID(8);
//        advance1.setMatricle("zs21013873");
//        advance2.setMatricle("zs21013873");
//        advance1.setDirectorID(5);
//        advance2.setDirectorID(5);
        advance1.setTitle("Avance de prueba unitaria");
        advance2.setTitle("Avance de prueba unitaria num2");
        advance1.setComments("Este avance es una prueba");
        advance2.setComments("Este avance también es una prueba");      
        ArrayList<Advance> expResult = new ArrayList();
        expResult.add(advance1);
        expResult.add(advance2);
        ArrayList<Advance> result = instance.getAdvancesList();
        
        for(int i = 0; i < result.size(); i++) {
            System.out.println(result.get(i));
        }
        for(int i = 0; i < expResult.size(); i++) {
            System.out.println(expResult.get(i));
        }
        
        System.out.println(expResult.equals(result));
        assertEquals(expResult, result);
    }

    /**
     * Test of getAdvanceByID method, of class AdvanceDAO.
     */
    @Test
    public void testGetAdvanceByID() throws DataRetrievalException {
        System.out.println("getAdvanceByID");
        int advanceID = 8;
        AdvanceDAO instance = new AdvanceDAO();
        Advance advance1 = new Advance();
        advance1.setAdvanceID(8);
//        advance1.setMatricle("zs21013873");
//        advance1.setDirectorID(5);
        advance1.setTitle("Avance de prueba unitaria num2");
        advance1.setComments("Este avance también es una prueba");
        Advance expResult = advance1;
        Advance result = instance.getAdvanceByID(advanceID);
        System.out.println(expResult);
        System.out.println(result);
        System.out.println(expResult.equals(result));
        assertEquals(expResult, result);
    }    
}
