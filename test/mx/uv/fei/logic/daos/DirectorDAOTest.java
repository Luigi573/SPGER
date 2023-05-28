package mx.uv.fei.logic.daos;

import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import mx.uv.fei.logic.domain.Director;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.exceptions.DataWritingException;


public class DirectorDAOTest {
    @Test
    public void addDirectorToDatabaseTest() {
        try {
            DirectorDAO directorDAO = new DirectorDAO();
            Director expectedDirector = new Director();
            expectedDirector.setName("Victor Jesús");
            expectedDirector.setFirstSurname("Cervantes");
            expectedDirector.setSecondSurname("Arrieta");
            expectedDirector.setEmailAddress("vijecear435@gmail.com");
            expectedDirector.setAlternateEmail("vijecear544@gmail.com");
            expectedDirector.setPhoneNumber("2289288384");
            expectedDirector.setStaffNumber(130000000);
            expectedDirector.setStatus("Activo");
            directorDAO.addDirectorToDatabase(expectedDirector);
            
            Director actualDirector = directorDAO.getDirectorFromDatabase(expectedDirector.getStaffNumber());     
            Assertions.assertTrue(expectedDirector.equals(actualDirector));
        } catch (DataWritingException e) {
            e.printStackTrace();
        } catch (DataRetrievalException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getDirectorFromDatabaseTest() {
        try {
            DirectorDAO directorDAO = new DirectorDAO();
            Director expectedDirector = new Director();
            expectedDirector.setName("Miguel Gilberto");
            expectedDirector.setFirstSurname("Chavez");
            expectedDirector.setSecondSurname("Gonzalez");
            expectedDirector.setEmailAddress("migichago998@gmail.com");
            expectedDirector.setAlternateEmail("migichago999@gmail.com");
            expectedDirector.setPhoneNumber("2281647833");
            expectedDirector.setStaffNumber(100000002);
            expectedDirector.setStatus("Disponible");
            Director actualDirector = directorDAO.getDirectorFromDatabase(expectedDirector.getStaffNumber());  

            Assertions.assertTrue(expectedDirector.equals(actualDirector));
        } catch (DataRetrievalException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getDirectorListTest() throws Exception {
        DirectorDAO directorDAO = new DirectorDAO();
        ArrayList<Director> directorList = directorDAO.getDirectorList();
        
        assertNotNull("If size is greater than one, it means data was retrieved correctly", (directorList.size() > 0));
    }

    @Test
    public void getDirectorsFromDatabaseTest() {
        try {
            DirectorDAO directorDAO = new DirectorDAO();
            Director expectedDirector = new Director();
            expectedDirector.setName("Miguel Gilberto");
            expectedDirector.setFirstSurname("Chavez");
            expectedDirector.setSecondSurname("Gonzalez");
            expectedDirector.setEmailAddress("migichago998@gmail.com");
            expectedDirector.setAlternateEmail("migichago999@gmail.com");
            expectedDirector.setPhoneNumber("2281647833");
            expectedDirector.setStaffNumber(100000002);
            expectedDirector.setStatus("Disponible");
            Director actualDirector = directorDAO.getDirectorsFromDatabase().get(0);   

            Assertions.assertTrue(expectedDirector.equals(actualDirector));
        } catch (DataRetrievalException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void getSpecifiedDirectorsFromDatabaseTest() {
        try {
            DirectorDAO directorDAO = new DirectorDAO();
            Director expectedDirector = new Director();
            expectedDirector.setName("Miguel Gilberto");
            expectedDirector.setFirstSurname("Chavez");
            expectedDirector.setSecondSurname("Gonzalez");
            expectedDirector.setEmailAddress("migichago998@gmail.com");
            expectedDirector.setAlternateEmail("migichago999@gmail.com");
            expectedDirector.setPhoneNumber("2281647833");
            expectedDirector.setStaffNumber(100000002);
            expectedDirector.setStatus("Disponible");
            Director actualDirector = directorDAO.getSpecifiedDirectorsFromDatabase("M").get(0); 

            Assertions.assertTrue(expectedDirector.equals(actualDirector));
        } catch (DataRetrievalException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void modifyDirectorDataFromDatabaseTest() {
        try {
            DirectorDAO directorDAO = new DirectorDAO();
            Director originalDirector = directorDAO.getDirectorFromDatabase(130000000);
            Director expectedDirector = new Director();
            expectedDirector.setName("Victor Jesús");
            expectedDirector.setFirstSurname("Cervantes");
            expectedDirector.setSecondSurname("Arrieta");
            expectedDirector.setEmailAddress("vijecear435@gmail.com");
            expectedDirector.setAlternateEmail("vijecear544@gmail.com");
            expectedDirector.setPhoneNumber("2289288384");
            expectedDirector.setStaffNumber(130000001);
            expectedDirector.setStatus("Activo");
            directorDAO.modifyDirectorDataFromDatabase(expectedDirector, originalDirector);
            
            Director actualDirector = directorDAO.getDirectorFromDatabase(expectedDirector.getStaffNumber());     
            Assertions.assertTrue(expectedDirector.equals(actualDirector));
        } catch (DataWritingException e) {
            e.printStackTrace();
        } catch (DataRetrievalException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void theDirectorIsAlreadyRegistedTest() {
        try {
            DirectorDAO directorDAO = new DirectorDAO();
            Director expectedDirector = new Director();
            expectedDirector.setName("Miguel Gilberto");
            expectedDirector.setFirstSurname("Chavez");
            expectedDirector.setSecondSurname("Gonzalez");
            expectedDirector.setEmailAddress("migichago998@gmail.com");
            expectedDirector.setAlternateEmail("migichago999@gmail.com");
            expectedDirector.setPhoneNumber("2281647833");
            expectedDirector.setStaffNumber(100000002);
            expectedDirector.setStatus("Disponible");

            Assertions.assertTrue(directorDAO.theDirectorIsAlreadyRegisted(expectedDirector));
        } catch (DataRetrievalException e) {
            e.printStackTrace();
        }
    }
    
    //@BeforeClass
    //public static void setUpClass() {
    //}
    //
    //@AfterClass
    //public static void tearDownClass() {
    //}
    //
    //@Before
    //public void setUp() {
    //}
    //
    //@After
    //public void tearDown() {
    //}

}
