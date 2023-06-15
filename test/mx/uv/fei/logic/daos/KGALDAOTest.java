package mx.uv.fei.logic.daos;

import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import mx.uv.fei.dataaccess.DataBaseManager;
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
    private static DataBaseManager dataBaseManager;
    private static KGAL preloadedKgal;
    private static int kgalId; 
    private static KGAL preloadedKgal2;
    private static int kgalId2;
    private static int nextAvailableId;
    
    public KGALDAOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        dataBaseManager = new DataBaseManager();
        preloadedKgal = new KGAL();
        preloadedKgal2 = new KGAL();
        PreparedStatement KGALStatement;
        String KGALQuery = "insert into LGAC(descripción) values(?)";
        
        try {
            //Preloading first kgal
            KGALStatement = dataBaseManager.getConnection().prepareStatement(KGALQuery, PreparedStatement.RETURN_GENERATED_KEYS);
            KGALStatement.setString(1, "Precargada para junit tests");
            
            KGALStatement.executeUpdate();
            ResultSet generatedKGAL = KGALStatement.getGeneratedKeys();
            
            if (generatedKGAL.next()) {
                kgalId = generatedKGAL.getInt(1);
            }
            
            preloadedKgal.setKgalID(kgalId);
            preloadedKgal.setDescription("Precargada para junit tests 2");
            System.out.println("ID de la LGAC precargada 1: " + kgalId);
            
            
            //Preloading second kgal
            KGALStatement = dataBaseManager.getConnection().prepareStatement(KGALQuery, PreparedStatement.RETURN_GENERATED_KEYS);
            KGALStatement.setString(1, "Precargada para junit tests 2");
            
            KGALStatement.executeUpdate();
            ResultSet generatedKGAL2 = KGALStatement.getGeneratedKeys();
            
            if (generatedKGAL2.next()) {
                kgalId2 = generatedKGAL2.getInt(1);
            }
            
            preloadedKgal2.setKgalID(kgalId2);
            preloadedKgal2.setDescription("Precargada para junit tests");
            nextAvailableId = kgalId2 + 1;
            System.out.println("ID de la LGAC precargada 2: " + kgalId2 + " \nID de la proxima LGAC en guardarse: " + nextAvailableId);
        } catch (SQLException exception) {
            fail("El test falló, no se pudo conectar a la BD");
        } finally {
            dataBaseManager.closeConnection();
        }
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
    public void testAddKGALSuccesful() throws DataInsertionException {
        System.out.println("addKGALSuccesful");
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
    
    @Test(expected = DataInsertionException.class)
    public void testAddKGALFail() throws DataInsertionException {
        System.out.println("addKGALFailure");
        KGAL kgal = new KGAL();
        kgal.setKgalID(1);
        kgal.setDescription(""); //Campo vacío
        KGALDAO instance = new KGALDAO();
        int expResult = 0;
        int result = instance.addKGAL(kgal);
        System.out.println(expResult);
        System.out.println(result);
        assertEquals(expResult, result);
    }

    /**
     * Test of getKGALList method, of class KGALDAO.
     */
    @Test
    public void testGetKGALListSuccessful() throws DataRetrievalException {
        System.out.println("getKGALList");
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
    
    @Test
    public void testGetKGALListFail() throws DataRetrievalException {
        System.out.println("getKGALListFailure");
        KGALDAO instance = new KGALDAO();
        ArrayList<KGAL> expResult = new ArrayList();
        KGAL kgal1 = new KGAL();
        KGAL kgal2 = new KGAL();
        kgal1.setKgalID(kgalId);
        kgal2.setKgalID(kgalId2);
        kgal1.setDescription("Precargada para junit tests"); //First KGAL matches
        kgal2.setDescription("Descripción incorrecta"); //Second KGAL doesn't match
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
    public void testGetKGALByIDSuccessful() throws DataRetrievalException {
        System.out.println("getKGALByID");
        int kgalID = 1;
        KGALDAO instance = new KGALDAO();
        KGAL expResult = new KGAL();
        expResult.setKgalID(1);
        expResult.setDescription("Este es una LGAC de prueba");
        KGAL result = instance.getKGALByID(kgalID);
        System.out.println(result);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGetKGALByIDFail() throws DataRetrievalException {
        System.out.println("getKGALByID");
        int kgalID = 1;
        KGALDAO instance = new KGALDAO();
        KGAL expResult = new KGAL();
        expResult.setKgalID(1);
        expResult.setDescription("Este es una LGAC de prueba");
        KGAL result = instance.getKGALByID(kgalID);
        System.out.println(result);
        assertEquals(expResult, result);
    }

    /**
     * Test of getKGALByDescription method, of class KGALDAO.
     */
    @Test
    public void testGetKGALByDescription() throws DataRetrievalException {
        System.out.println("getKGALByDescription");
        int kgalID = 1;
        KGALDAO instance = new KGALDAO();
        KGAL expResult = new KGAL();
        expResult.setKgalID(1);
        expResult.setDescription("Este es una LGAC de prueba");
        KGAL result = instance.getKGALByID(kgalID);
        System.out.println(result);
        assertEquals(expResult, result);
    }

    /**
     * Test of updateKGALDescription method, of class KGALDAO.
     */
    @Test
    public void testUpdateKGALDescriptionSuccess() throws DataRetrievalException {
        System.out.println("updateKGALDescription");
        String description = "Esta LGAC ha sido modificada";
        KGALDAO instance = new KGALDAO();
        int expResult = 1;
        int result = instance.updateKGALDescription(kgalId, description);
        assertEquals(expResult, result);
    }
    
    @Test (expected = DataRetrievalException.class)
    public void testUpdateKGALDescriptionFail() throws DataRetrievalException {
        System.out.println("updateKGALDescription Fail");
        String description = "Este LGAC no se modificará";
        KGALDAO instance = new KGALDAO();
        int expResult = 0;
        int result = instance.updateKGALDescription(0, description);
        assertEquals(expResult, result);
    }

    /**
     * Test of getKGALListByDescription method, of class KGALDAO.
     */
    @Test
    public void testGetKGALListByDescriptionSuccessful() throws DataRetrievalException {
        System.out.println("getKGALListByDescription");
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
    
    @Test
    public void testGetKGALListByDescriptionFail() throws DataRetrievalException {
        System.out.println("getKGALListByDescription Fail");
        String description = "Descripción sin coincidencias";
        KGALDAO instance = new KGALDAO();
        ArrayList<KGAL> expResult = new ArrayList(); //we expect an empty list since no KGAL should match the given description
        
        ArrayList<KGAL> result = instance.getKGALListByDescription(description);
        
        for(int i = 0; i < result.size(); i++) {
            System.out.println(result.get(i));
        }
        for(int i = 0; i < expResult.size(); i++) {
            System.out.println(expResult.get(i));
        }
        
        assertEquals(expResult, result);
    }
    
}
