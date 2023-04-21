package mx.uv.fei.logic.daos;

import java.util.ArrayList;
import mx.uv.fei.logic.domain.KGAL;
import mx.uv.fei.logic.exceptions.DataInsertionException;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class KGALDAOTest {
    
    public KGALDAOTest() {
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
     * Test of addKGAL method, of class KGALDAO.
     */
    @Test
    public void testAddKGAL() throws DataInsertionException {
        System.out.println("addKGAL");
        KGAL kgal = new KGAL();
        kgal.setKgalID(1);
        kgal.setDescription("LGAC de Prueba");
        KGALDAO instance = new KGALDAO();
        int expResult = 1;
        int result = instance.addKGAL(kgal);
        assertEquals(expResult, result);
    }

    /**
     * Test of getKGALList method, of class KGALDAO.
     */
    @Test
    public void testGetKGALList() throws DataRetrievalException {
        System.out.println("getKGALList");
        KGALDAO instance = new KGALDAO();
        ArrayList<KGAL> expResult = new ArrayList();
        KGAL kgal1 = new KGAL();
        KGAL kgal2 = new KGAL();
        kgal1.setKgalID(1);
        kgal2.setKgalID(2);
        kgal1.setDescription("Este es una LGAC de prueba");
        kgal2.setDescription("LGAC de Prueba");
        expResult.add(kgal1);
        expResult.add(kgal2);
        ArrayList<KGAL> result = instance.getKGALList();
        
        for(int i = 0; i < result.size(); i++) {
            System.out.println(result.get(i));
        }
        for(int i = 0; i < expResult.size(); i++) {
            System.out.println(expResult.get(i));
        }
        
        assertEquals(expResult, result);
    }

    /**
     * Test of getKGALByID method, of class KGALDAO.
     */
    @Test
    public void testGetKGALByID() throws Exception {
        System.out.println("getKGALByID");
        int kgalID = 0;
        KGALDAO instance = new KGALDAO();
        KGAL expResult = null;
        KGAL result = instance.getKGALByID(kgalID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getKGALByDescription method, of class KGALDAO.
     */
    @Test
    public void testGetKGALByDescription() throws Exception {
        System.out.println("getKGALByDescription");
        String description = "";
        KGALDAO instance = new KGALDAO();
        KGAL expResult = null;
        KGAL result = instance.getKGALByDescription(description);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
