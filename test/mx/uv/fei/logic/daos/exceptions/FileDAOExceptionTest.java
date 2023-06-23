package mx.uv.fei.logic.daos.exceptions;

import java.util.ArrayList;
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
    public void testAddActivityFileException() throws DataInsertionException {
        System.out.println("addActivityFile");
        
        dataInsertionExceptionRule.expect(DataInsertionException.class);
        dataInsertionExceptionRule.expectMessage("No fue posible guardar la información del archivo. Por favor, intente de nuevo más tarde.");
        
        String filePath = "C:\\Users\\Jesús Manuel\\Desktop";
        int activityId = 1;
        FileDAO instance = new FileDAO();
        int expResult = 0;
        int result = instance.addActivityFile(filePath, activityId);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getFilesByActivity method, of class FileDAO.
     */
    @Test
    public void testGetFilesByActivityException() throws DataRetrievalException {
        System.out.println("getFilesByActivity");
        
        dataRetrievalExceptionRule.expect(DataRetrievalException.class);
        dataRetrievalExceptionRule.expectMessage("No fue posible recuperar los archivos. Por favor intente de nuevo más tarde.");
        
        int activityID = 1;
        FileDAO instance = new FileDAO();
        File file = new File();
        file.setFileID(1);
        file.setFilePath("C:\\Users\\Jesús Manuel\\Desktop");
        ArrayList<File> expResult = new ArrayList();
        expResult.add(file);
        ArrayList<File> result = instance.getFilesByActivity(activityID);
        
        for (File fileResult : result) {
            System.out.println(file);
        }
        for (File fileExpResult : expResult) {
            System.out.println(file);
        }
        
        assertEquals(expResult, result);
    }  
}
