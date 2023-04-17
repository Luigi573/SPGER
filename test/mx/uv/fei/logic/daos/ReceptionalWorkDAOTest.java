package mx.uv.fei.logic.daos;

import java.util.ArrayList;
import mx.uv.fei.logic.domain.ReceptionalWork;
import mx.uv.fei.logic.exceptions.DataInsertionException;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jesús Manuel
 */
public class ReceptionalWorkDAOTest {
    
    public ReceptionalWorkDAOTest() {
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
     * Test of addReceptionalWork method, of class ReceptionalWorkDAO.
     */
    @Test
    public void testAddReceptionalWork() throws DataInsertionException {
        System.out.println("addReceptionalWork");
        ReceptionalWork receptionalWork = new ReceptionalWork();
        receptionalWork.setResearchID(10);
        receptionalWork.setName("Trabajo Recepcional de Prueba");
        receptionalWork.setDescription("Este trabajo recepcional es parte de una prueba");
        ReceptionalWorkDAO instance = new ReceptionalWorkDAO();
        int expResult = 1;
        int result = instance.addReceptionalWork(receptionalWork);
        assertEquals(expResult, result);
    }

    /**
     * Test of getReceptionalWorksList method, of class ReceptionalWorkDAO.
     */
    @Test
    public void testGetReceptionalWorksList() throws DataRetrievalException {
        System.out.println("getReceptionalWorksList");
        ReceptionalWorkDAO instance = new ReceptionalWorkDAO();
        ArrayList<ReceptionalWork> expResult = new ArrayList();
        ReceptionalWork receptionalWork1 = new ReceptionalWork();
        ReceptionalWork receptionalWork2 = new ReceptionalWork();
        receptionalWork1.setResearchID(10);
        receptionalWork2.setResearchID(20);
        receptionalWork1.setName("Trabajo Recepcional de Prueba");
        receptionalWork2.setName("Trabajo Recepcional de Prueba 2");
        receptionalWork1.setDescription("Este trabajo recepcional es parte de una prueba");
        receptionalWork2.setDescription("Este trabajo recepcional también es parte de una prueba");       
        ArrayList<ReceptionalWork> result = instance.getReceptionalWorksList();
        expResult.add(receptionalWork1);
        expResult.add(receptionalWork2);
        
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
     * Test of getReceptionalWorkByID method, of class ReceptionalWorkDAO.
     */
    @Test
    public void testGetReceptionalWorkByID() throws DataRetrievalException {
        System.out.println("getReceptionalWorkByID");
        int receptionalWorkID = 5;
        ReceptionalWorkDAO instance = new ReceptionalWorkDAO();
        ReceptionalWork expResult = new ReceptionalWork();
        expResult.setReceptionalWorkID(5);
        expResult.setResearchID(20);
        expResult.setName("Trabajo Recepcional de Prueba 2");
        expResult.setDescription("Este trabajo recepcional también es parte de una prueba");
        ReceptionalWork result = instance.getReceptionalWorkByID(receptionalWorkID);
        System.out.println(expResult);
        System.out.println(result);
        System.out.println(expResult.equals(result));
        assertEquals(expResult, result);
    }    
}
