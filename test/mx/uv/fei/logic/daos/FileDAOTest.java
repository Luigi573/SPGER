/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package mx.uv.fei.logic.daos;

import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.logic.domain.File;
import mx.uv.fei.logic.exceptions.DataInsertionException;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

/**
 *
 * @author Jesús Manuel
 */
public class FileDAOTest {
    private static DataBaseManager dataBaseManager;
    private static File preloadedFile;
    private static int nextAvailableId;
    
    
    public FileDAOTest() {
        
    }
    
    @BeforeClass
    public static void setUpClass() {
        dataBaseManager = new DataBaseManager();
        preloadedFile = new File();
        preloadedFile.setPath("C:\\Users\\Jesús Manuel\\Desktop\\SPGER");
        PreparedStatement preparedStatement;
        String fileQuery = "INSERT INTO Archivos(ruta) VALUES (?)";
        
        try {
            preparedStatement = dataBaseManager.getConnection().prepareStatement(fileQuery, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, preloadedFile.getFilePath());
            
            preparedStatement.executeUpdate();
            ResultSet generatedFile = preparedStatement.getGeneratedKeys();
            
            if (generatedFile.next()) {
                preloadedFile.setFileID(generatedFile.getInt(1));
            }
            
            nextAvailableId = preloadedFile.getFileID() + 1;
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
     * Test of addFile method, of class FileDAO.
     */
    @Test
    public void testAddFileSuccessful() throws DataInsertionException {
        System.out.println("addFile Success");
        String filePath = "C:\\Users\\Jesús Manuel\\Desktop";
        FileDAO instance = new FileDAO();
        int expResult = nextAvailableId;
        int result = instance.addFile(filePath);
        assertEquals(expResult, result);
    }

    @Test(expected = DataInsertionException.class)
    public void testAddFileFail() throws DataInsertionException {
        System.out.println("addFile Fail");
        String filePath = "";
        FileDAO instance = new FileDAO();
        int expResult = 0;
        int result = instance.addFile(filePath);
        assertEquals(expResult, result);
    }

    /**
     * Test of getFileByID method, of class FileDAO.
     */
    @Test
    public void testGetFileByIDSuccessful() throws DataRetrievalException {
        System.out.println("getFileByID");
        int fileID = preloadedFile.getFileID();
        FileDAO instance = new FileDAO();
        File expResult = preloadedFile;
        File result = instance.getFileByID(fileID);
        assertEquals(expResult, result);
    }
    
    @Test(expected = DataRetrievalException.class)
    public void testGetFileByIDFail() throws DataRetrievalException {
        System.out.println("getFileByID");
        int fileID = 0;
        FileDAO instance = new FileDAO();
        File expResult = null;
        File result = instance.getFileByID(fileID);
        assertEquals(expResult, result);
    }
}
