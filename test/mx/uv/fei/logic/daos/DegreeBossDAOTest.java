package mx.uv.fei.logic.daos;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import mx.uv.fei.logic.domain.DegreeBoss;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.exceptions.DataWritingException;

public class DegreeBossDAOTest {
    @Test
    void addDegreeBossToDatabaseTest() {
        try {
            DegreeBossDAO degreeBossDAO = new DegreeBossDAO();
            DegreeBoss expectedDegreeBoss = new DegreeBoss();
            expectedDegreeBoss.setName("Diana Laura");
            expectedDegreeBoss.setFirstSurname("Estrada");
            expectedDegreeBoss.setSecondSurname("Hernandez");
            expectedDegreeBoss.setEmailAddress("dilaeshe2314@gmail.com");
            expectedDegreeBoss.setAlternateEmail("dilaeshe23199@gmail.com");
            expectedDegreeBoss.setPhoneNumber("2286298112");
            expectedDegreeBoss.setStaffNumber(150000000);
            expectedDegreeBoss.setStatus("Disponible");
            degreeBossDAO.addDegreeBossToDatabase(expectedDegreeBoss);
            
            DegreeBoss actualDegreeBoss = degreeBossDAO.getDegreeBossFromDatabase(expectedDegreeBoss.getStaffNumber());     
            Assertions.assertTrue(expectedDegreeBoss.equals(actualDegreeBoss));
        } catch (DataWritingException e) {
            e.printStackTrace();
        } catch (DataRetrievalException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getDegreeBossFromDatabaseTest() {
        try {
            DegreeBossDAO degreeBossDAO = new DegreeBossDAO();
            DegreeBoss expectedDegreeBoss = new DegreeBoss();
            expectedDegreeBoss.setName("Mauricio");
            expectedDegreeBoss.setFirstSurname("Ortega");
            expectedDegreeBoss.setSecondSurname("Mújica");
            expectedDegreeBoss.setEmailAddress("cricoso222@gmail.com");
            expectedDegreeBoss.setAlternateEmail("cricoso232@gmail.com");
            expectedDegreeBoss.setPhoneNumber("2284627839");
            expectedDegreeBoss.setStaffNumber(100000004);
            expectedDegreeBoss.setStatus("No Disponible");
            DegreeBoss actualDegreeBoss = degreeBossDAO.getDegreeBossFromDatabase(expectedDegreeBoss.getStaffNumber());  

            Assertions.assertTrue(expectedDegreeBoss.equals(actualDegreeBoss));
        } catch (DataRetrievalException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getDegreeBossesFromDatabaseTest() {
        try {
            DegreeBossDAO degreeBossDAO = new DegreeBossDAO();
            DegreeBoss expectedDegreeBoss = new DegreeBoss();
            expectedDegreeBoss.setName("Mauricio");
            expectedDegreeBoss.setFirstSurname("Ortega");
            expectedDegreeBoss.setSecondSurname("Mújica");
            expectedDegreeBoss.setEmailAddress("cricoso222@gmail.com");
            expectedDegreeBoss.setAlternateEmail("cricoso232@gmail.com");
            expectedDegreeBoss.setPhoneNumber("2284627839");
            expectedDegreeBoss.setStaffNumber(100000004);
            expectedDegreeBoss.setStatus("No Disponible");
            DegreeBoss actualDegreeBoss = degreeBossDAO.getDegreeBossesFromDatabase().get(0);   

            Assertions.assertTrue(expectedDegreeBoss.equals(actualDegreeBoss));
        } catch (DataRetrievalException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getSpecifiedDegreeBossesFromDatabaseTest() {
        try {
            DegreeBossDAO degreeBossDAO = new DegreeBossDAO();
            DegreeBoss expectedDegreeBoss = new DegreeBoss();
            expectedDegreeBoss.setName("Mauricio");
            expectedDegreeBoss.setFirstSurname("Ortega");
            expectedDegreeBoss.setSecondSurname("Mújica");
            expectedDegreeBoss.setEmailAddress("cricoso222@gmail.com");
            expectedDegreeBoss.setAlternateEmail("cricoso232@gmail.com");
            expectedDegreeBoss.setPhoneNumber("2284627839");
            expectedDegreeBoss.setStaffNumber(100000004);
            expectedDegreeBoss.setStatus("No Disponible");
            DegreeBoss actualDegreeBoss = degreeBossDAO.getSpecifiedDegreeBossesFromDatabase("M").get(0); 

            Assertions.assertTrue(expectedDegreeBoss.equals(actualDegreeBoss));
        } catch (DataRetrievalException e) {
            e.printStackTrace();
        }
    }

    @Test
    void modifyDegreeBossDataFromDatabaseTest() {
        try {
            DegreeBossDAO degreeBossDAO = new DegreeBossDAO();
            DegreeBoss originalDegreeBoss = degreeBossDAO.getDegreeBossFromDatabase(150000000);
            DegreeBoss expectedDegreeBoss = new DegreeBoss();
            expectedDegreeBoss.setName("Diana Laura");
            expectedDegreeBoss.setFirstSurname("Estrada");
            expectedDegreeBoss.setSecondSurname("Hernandez");
            expectedDegreeBoss.setEmailAddress("dilaeshe2314@gmail.com");
            expectedDegreeBoss.setAlternateEmail("dilaeshe93199@gmail.com");
            expectedDegreeBoss.setPhoneNumber("2286298512");
            expectedDegreeBoss.setStaffNumber(150000001);
            expectedDegreeBoss.setStatus("Disponible");
            degreeBossDAO.modifyDegreeBossDataFromDatabase(expectedDegreeBoss, originalDegreeBoss);
            
            DegreeBoss actualDegreeBoss = degreeBossDAO.getDegreeBossFromDatabase(expectedDegreeBoss.getStaffNumber());     
            Assertions.assertTrue(expectedDegreeBoss.equals(actualDegreeBoss));
        } catch (DataWritingException e) {
            e.printStackTrace();
        } catch (DataRetrievalException e) {
            e.printStackTrace();
        }
    }

    @Test
    void theDegreeBossIsAlreadyRegistedTest() {
        try {
            DegreeBossDAO degreeBossDAO = new DegreeBossDAO();
            DegreeBoss expectedDegreeBoss = new DegreeBoss();
            expectedDegreeBoss.setName("Mauricio");
            expectedDegreeBoss.setFirstSurname("Ortega");
            expectedDegreeBoss.setSecondSurname("Mújica");
            expectedDegreeBoss.setEmailAddress("cricoso222@gmail.com");
            expectedDegreeBoss.setAlternateEmail("cricoso232@gmail.com");
            expectedDegreeBoss.setPhoneNumber("2284627839");
            expectedDegreeBoss.setStaffNumber(100000004);
            expectedDegreeBoss.setStatus("No Disponible");

            Assertions.assertTrue(degreeBossDAO.theDegreeBossIsAlreadyRegisted(expectedDegreeBoss));
        } catch (DataRetrievalException e) {
            e.printStackTrace();
        }
    }
}
