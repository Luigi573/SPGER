package mx.uv.fei.logic.daos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.logic.domain.Activity;
import mx.uv.fei.logic.domain.File;
import mx.uv.fei.logic.domain.statuses.ActivityStatus;
import mx.uv.fei.logic.exceptions.DataInsertionException;
import mx.uv.fei.logic.exceptions.DataRetrievalException;

public class FileDAOTest {
    private static DataBaseManager dataBaseManager;
    private static File preloadedFile;
    private static int nextAvailableFileId;
    private static Activity preloadedActivity;
    private static int nextAvailableActivityFileId;
    
    
    public FileDAOTest() {
        
    }
    
    @BeforeClass
    public static void setUpClass() {
        dataBaseManager = new DataBaseManager();
        preloadedFile = new File();
        preloadedFile.setFilePath("C:\\Users\\Jesús Manuel\\Desktop\\SPGER");
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
            
            nextAvailableFileId = preloadedFile.getFileID() + 1;
        } catch (SQLException exception) {
            fail("El test falló al crear el archivo precargado, no se pudo conectar a la BD");
        } finally {
            dataBaseManager.closeConnection();
        }
        
        preloadedActivity = new Activity();
        preloadedActivity.setResearchId(1);
        preloadedActivity.setTitle("Actividad de prueba junit");
        preloadedActivity.setDescription("Esta descripción fue creada para un test de junit");
        preloadedActivity.setStatus(ActivityStatus.ACTIVE);
        preloadedActivity.setFeedback("Actividad no ha sido revisada");
        
        String activityQuery = "INSERT INTO Actividades(IdAnteproyecto, título, descripción, estado, comentario, retroalimentación) VALUES(?, ?, ?, ?, ?, ?)";
        try {
            preparedStatement = dataBaseManager.getConnection().prepareStatement(activityQuery, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, preloadedActivity.getResearchId());
            preparedStatement.setString(2, preloadedActivity.getTitle());
            preparedStatement.setString(3, preloadedActivity.getDescription());
            preparedStatement.setString(4, preloadedActivity.getStatus().getValue());
            preparedStatement.setString(5, preloadedActivity.getComment());
            preparedStatement.setString(6, preloadedActivity.getFeedback());
            
            preparedStatement.executeUpdate();
            ResultSet generatedActivity = preparedStatement.getGeneratedKeys();
            
            if (generatedActivity.next()) {
                preloadedActivity.setId(generatedActivity.getInt(1));
            }
        } catch (SQLException exception) {
            fail("El test falló al crear la actividad precargada, no se pudo conectar a la BD");
        } finally {
            dataBaseManager.closeConnection();
        }
        
        String activityFileQuery = "INSERT INTO ArchivosActividad(IdActividad, IdArchivo) VALUES(?, ?)";
        try {
            preparedStatement = dataBaseManager.getConnection().prepareStatement(activityFileQuery, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, preloadedActivity.getId());
            preparedStatement.setInt(2, preloadedFile.getFileID());
            
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            
            if (resultSet.next()) {
                nextAvailableActivityFileId = resultSet.getInt(1) + 1;
            }
            
        } catch (SQLException exception) {
            fail("El test falló al crear la conexión entre la actividad y el archivo precargados, no se pudo conectar a la BD");
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

    @Rule
    public ExpectedException dataInsertionExceptionRule = ExpectedException.none();
    @Rule
    public ExpectedException dataRetrievalExceptionRule = ExpectedException.none();
    
    /**
     * Test of addFile method, of class FileDAO.
     */
    @Test
    public void testAddFileSuccessful() throws DataInsertionException {
        System.out.println("addFile Success");
        String filePath = "C:\\Users\\Jesús Manuel\\Desktop";
        FileDAO instance = new FileDAO();
        int expResult = nextAvailableFileId;
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

    /**
     * Test of addActivityFile method, of class FileDAO.
     */
    @Test
    public void testAddActivityFileSuccessful() throws DataInsertionException {
        System.out.println("addActivityFile Success");
        String filePath = "C:\\Users\\Jesús Manuel\\Desktop";
        int activityId = preloadedActivity.getId();
        FileDAO instance = new FileDAO();
        int expResult = nextAvailableActivityFileId;
        int result = instance.addActivityFile(filePath, activityId);
        assertEquals(expResult, result);
    }

    
    @Test
    public void testAddActivityFileFail() throws DataInsertionException {
        System.out.println("addActivityFile Fail");
        
        dataInsertionExceptionRule.expect(DataInsertionException.class);
        dataInsertionExceptionRule.expectMessage("Se recibió información de archivo o de actividad incorrecta.");
        
        String filePath = "C:\\Users\\Jesús Manuel\\Desktop";
        int activityId = 0;
        FileDAO instance = new FileDAO();
        int expResult = 0;
        int result = instance.addActivityFile(filePath, activityId);
        assertEquals(expResult, result);
    }

    /**
     * Test of getFilesByActivity method, of class FileDAO.
     */
    @Test
    public void testGetFilesByActivitySuccessful() throws DataRetrievalException {
        System.out.println("getFilesByActivity Success");
        int activityID = preloadedActivity.getId();
        FileDAO instance = new FileDAO();
        File file = new File();
        file.setFileID(preloadedFile.getFileID());
        file.setFilePath(preloadedFile.getFilePath());
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
    
    @Test
    public void testGetFilesByActivityFail() throws DataRetrievalException {
        System.out.println("getFilesByActivity Success");
        
        dataRetrievalExceptionRule.expect(DataRetrievalException.class);
        dataRetrievalExceptionRule.expectMessage("El ID de la actividad es menor a 1.");
        
        int activityID = 0;
        FileDAO instance = new FileDAO();
        File file = new File();
        file.setFileID(preloadedFile.getFileID());
        file.setFilePath(preloadedFile.getFilePath());
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
