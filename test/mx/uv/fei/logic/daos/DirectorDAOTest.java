package mx.uv.fei.logic.daos;

import java.util.ArrayList;
import mx.uv.fei.logic.domain.Director;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class DirectorDAOTest {
    @Test
    void testAddDirectorToDatabase() {

    }

    @Test
    void testGetDirector() {
        
    }

    @Test
    public void testGetDirectorList() throws Exception {
        DirectorDAO directorDAO = new DirectorDAO();
        ArrayList<Director> directorList = directorDAO.getDirectorList();
        
        assertNotNull("If size is greater than one, it means data was retrieved correctly", (directorList.size() > 0));
    }

    @Test
    void testGetDirectors() {
        
    }
    
    @Test
    void testGetSpecifiedDirectors() {
        
    }
    
    @Test
    void testModifyDirectorData() {
        
    }
    
    @Test
    void testTheDirectorIsAlreadyRegisted() {
        
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

}
