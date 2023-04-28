package mx.uv.fei.logic.daos;

import java.util.ArrayList;
import mx.uv.fei.logic.domain.AcademicBody;
import mx.uv.fei.logic.exceptions.DataInsertionException;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class AcademicBodyDAOTest {
    
    public AcademicBodyDAOTest() {
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
     * Test of addAcademicBody method, of class AcademicBodyDAO.
     */
    @Test
    public void testAddAcademicBody() throws DataInsertionException {
        System.out.println("addAcademicBody");
        AcademicBody academicBody = new AcademicBody();
        academicBody.setAcademicBodyHeadID(10);
        academicBody.setDescription("Este es un cuerpo académico de prueba de unidad");
        AcademicBodyDAO instance = new AcademicBodyDAO();
        int expResult = 1;
        int result = instance.addAcademicBody(academicBody);
        assertEquals(expResult, result);
    }

    /**
     * Test of getAcademicBodiesList method, of class AcademicBodyDAO.
     */
    @Test
    public void testGetAcademicBodiesList() throws DataRetrievalException {
        System.out.println("getAcademicBodiesList");
        AcademicBodyDAO instance = new AcademicBodyDAO();
        ArrayList<AcademicBody> expResult = new ArrayList();
        AcademicBody academicBody1 = new AcademicBody();
        AcademicBody academicBody2 = new AcademicBody();
        academicBody1.setAcademicBodyID(18);
        academicBody2.setAcademicBodyID(19);
        academicBody1.setAcademicBodyHeadID(1);
        academicBody2.setAcademicBodyHeadID(2);
        academicBody1.setDescription("CA de prueba 1");
        academicBody2.setDescription("CA de prueba 2");
        ArrayList<AcademicBody> result = instance.getAcademicBodiesList();
        expResult.add(academicBody1);
        expResult.add(academicBody2);

        
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
     * Test of getAcademicBodyByID method, of class AcademicBodyDAO.
     */
    @Test
    public void testGetAcademicBodyByID() throws DataRetrievalException {
        System.out.println("getAcademicBodyByID");
        int academicBodyID = 19;
        AcademicBody academicBody1 = new AcademicBody();
        academicBody1.setAcademicBodyID(19);
        academicBody1.setAcademicBodyHeadID(2);
        academicBody1.setDescription("CA de prueba 2");
        AcademicBodyDAO instance = new AcademicBodyDAO();
        AcademicBody expResult = academicBody1;
        AcademicBody result = instance.getAcademicBodyByID(academicBodyID);
        System.out.println(academicBody1);
        System.out.println(result);
        
        System.out.println(expResult.equals(result));
        assertEquals(expResult, result);
    }
    
}
