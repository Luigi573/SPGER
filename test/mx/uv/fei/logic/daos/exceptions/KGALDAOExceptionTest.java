package mx.uv.fei.logic.daos.exceptions;

import java.util.ArrayList;
import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.logic.daos.KGALDAO;
import mx.uv.fei.logic.domain.KGAL;
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
public class KGALDAOExceptionTest {
    private static DataBaseManager dataBaseManager;
    private static KGAL preloadedKgal;
    private static int kgalId; 
    private static KGAL preloadedKgal2;
    private static int kgalId2;
    private static int nextAvailableId;
    
    public KGALDAOExceptionTest() {
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
    @Rule
    public ExpectedException dataInsertionExceptionRule = ExpectedException.none();
    
    @Test
    public void testAddKGALException() throws DataInsertionException { //Stop MySQL Service before running
        System.out.println("addKGAL SQLException");
        
        dataInsertionExceptionRule.expect(DataInsertionException.class);
        dataInsertionExceptionRule.expectMessage("La información de la nueva LGAC no pudo ser guardada en la Base de Datos. Por favor intente de nuevo más tarde.");
    
        KGAL insertedKgal = new KGAL();
        insertedKgal.setDescription("LGAC de Prueba");
        KGALDAO instance = new KGALDAO();
        int expResult = nextAvailableId;
        int result = instance.addKGAL(insertedKgal);
        nextAvailableId = result + 1;
        System.out.println(expResult);
        System.out.println(result);
        assertEquals(expResult, result);
    }

    /**
     * Test of getKGALList method, of class KGALDAO.
     */
    @Rule
    public ExpectedException dataRetrievalExceptionRule = ExpectedException.none();
    
    @Test
    public void testGetKGALListException() throws DataRetrievalException { //Stop MySQL Service before running
        System.out.println("getKGALList SQLException");
        
        dataRetrievalExceptionRule.expect(DataRetrievalException.class);
        dataRetrievalExceptionRule.expectMessage("No fue posible recuperar la información de la LGAC. Por favor intente de nuevo más tarde.");
        
        KGALDAO instance = new KGALDAO();
        ArrayList<KGAL> expResult = new ArrayList();
        KGAL kgal1 = new KGAL();
        KGAL kgal2 = new KGAL();
        kgal1.setKgalID(kgalId);
        kgal2.setKgalID(kgalId2);
        kgal1.setDescription("Precargada para junit tests");
        kgal2.setDescription("Precargada para junit tests 2");
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

    /**
     * Test of getKGALListByDescription method, of class KGALDAO.
     */
    @Test
    public void testGetKGALListByDescriptionException() throws DataRetrievalException {
        System.out.println("getKGALListByDescription SQLException");
        
        dataRetrievalExceptionRule.expect(DataRetrievalException.class);
        dataRetrievalExceptionRule.expectMessage("No fue posible recuperar la LGAC especificada. Por favor intente de nuevo más tarde.");
        
        String description = "2";
        KGALDAO instance = new KGALDAO();
        KGAL kgal = new KGAL();
        kgal.setKgalID(kgalId2);
        kgal.setDescription("Precargada para junit tests 2");
        ArrayList<KGAL> expResult = new ArrayList();
        expResult.add(kgal);
        ArrayList<KGAL> result = instance.getKGALListByDescription(description);
        
        for(int i = 0; i < result.size(); i++) {
            System.out.println(result.get(i));
        }
        for(int i = 0; i < expResult.size(); i++) {
            System.out.println(expResult.get(i));
        }
        
        assertEquals(expResult, result);
    }

    /**
     * Test of updateKGALDescription method, of class KGALDAO.
     */
    @Test
    public void testUpdateKGALDescriptionException() throws DataRetrievalException {
        System.out.println("updateKGALDescription SQLException");
        
        dataRetrievalExceptionRule.expect(DataRetrievalException.class);
        dataRetrievalExceptionRule.expectMessage("La nueva descripción de la LGAC no pudo ser guardada. Por favor intente de nuevo más tarde.");
        
        String description = "Esta LGAC ha sido modificada";
        KGALDAO instance = new KGALDAO();
        int expResult = 1;
        int result = instance.updateKGALDescription(1, description);
        assertEquals(expResult, result);
    }
    
}
