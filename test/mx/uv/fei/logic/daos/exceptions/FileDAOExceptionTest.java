package mx.uv.fei.logic.daos.exceptions;

import mx.uv.fei.logic.daos.FileDAO;
import mx.uv.fei.logic.domain.File;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import mx.uv.fei.logic.exceptions.DataInsertionException;
import mx.uv.fei.logic.exceptions.DataRetrievalException;

public class FileDAOExceptionTest {
    
    public FileDAOExceptionTest() {
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
     * Test of addFile method, of class FileDAO.
     */
    @Test
    public void testAddFileException() throws DataInsertionException {
        System.out.println("addFile");
        
        dataInsertionExceptionRule.expect(DataInsertionException.class);
        dataInsertionExceptionRule.expectMessage("No fue posible guardar la información del archivo. Por favor, intente de nuevo más tarde.");
        
        String filePath = "Desktop";
        FileDAO instance = new FileDAO();
        int expResult = 0;
        int result = instance.addFile(filePath);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFileByID method, of class FileDAO.
     */
    @Test
    public void testGetFileByIDException() throws DataRetrievalException {
        System.out.println("getFileByID");
        
        dataRetrievalExceptionRule.expect(DataRetrievalException.class);
        dataRetrievalExceptionRule.expectMessage("No fue posible recuperar la información del archivo. Por favor intente de nuevo más tarde.");
        
        int fileID = 0;
        FileDAO instance = new FileDAO();
        File expResult = null;
        File result = instance.getFileByID(fileID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
