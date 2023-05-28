package mx.uv.fei.logic.daos;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import mx.uv.fei.logic.domain.AcademicBodyHead;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.exceptions.DataWritingException;

public class AcademicBodyHeadDAOTest {
    
    @Test
    void addAcademicBodyHeadToDatabaseTest() {
        try {
            AcademicBodyHeadDAO academicBodyHeadDAO = new AcademicBodyHeadDAO();
            AcademicBodyHead expectedAcademicBodyHead = new AcademicBodyHead();
            expectedAcademicBodyHead.setName("Guiovanny Manuel");
            expectedAcademicBodyHead.setFirstSurname("Salazar");
            expectedAcademicBodyHead.setSecondSurname("Vazquez");
            expectedAcademicBodyHead.setEmailAddress("lonjanini453@gmail.com");
            expectedAcademicBodyHead.setAlternateEmail("lonjanini111@gmail.com");
            expectedAcademicBodyHead.setPhoneNumber("2288923093");
            expectedAcademicBodyHead.setStaffNumber(140000000);
            expectedAcademicBodyHead.setStatus("Disponible");
            academicBodyHeadDAO.addAcademicBodyHeadToDatabase(expectedAcademicBodyHead);
            
            AcademicBodyHead actualAcademicBodyHead = academicBodyHeadDAO.getAcademicBodyHeadFromDatabase(expectedAcademicBodyHead.getStaffNumber());     
            Assertions.assertTrue(expectedAcademicBodyHead.equals(actualAcademicBodyHead));
        } catch (DataWritingException e) {
            e.printStackTrace();
        } catch (DataRetrievalException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getAcademicBodyHeadFromDatabaseTest() {
        try {
            AcademicBodyHeadDAO academicBodyHeadDAO = new AcademicBodyHeadDAO();
            AcademicBodyHead expectedAcademicBodyHead = new AcademicBodyHead();
            expectedAcademicBodyHead.setName("José René");
            expectedAcademicBodyHead.setFirstSurname("Mendoza");
            expectedAcademicBodyHead.setSecondSurname("Gonzalez");
            expectedAcademicBodyHead.setEmailAddress("joremago112@gmail.com");
            expectedAcademicBodyHead.setAlternateEmail("joremago111@gmail.com");
            expectedAcademicBodyHead.setPhoneNumber("2288563333");
            expectedAcademicBodyHead.setStaffNumber(100000003);
            expectedAcademicBodyHead.setStatus("Disponible");
            AcademicBodyHead actualAcademicBodyHead = academicBodyHeadDAO.getAcademicBodyHeadFromDatabase(expectedAcademicBodyHead.getStaffNumber());  

            Assertions.assertTrue(expectedAcademicBodyHead.equals(actualAcademicBodyHead));
        } catch (DataRetrievalException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getAcademicBodyHeadsFromDatabaseTest() {
        try {
            AcademicBodyHeadDAO academicBodyHeadDAO = new AcademicBodyHeadDAO();
            AcademicBodyHead expectedAcademicBodyHead = new AcademicBodyHead();
            expectedAcademicBodyHead.setName("José René");
            expectedAcademicBodyHead.setFirstSurname("Mendoza");
            expectedAcademicBodyHead.setSecondSurname("Gonzalez");
            expectedAcademicBodyHead.setEmailAddress("joremago112@gmail.com");
            expectedAcademicBodyHead.setAlternateEmail("joremago111@gmail.com");
            expectedAcademicBodyHead.setPhoneNumber("2288563333");
            expectedAcademicBodyHead.setStaffNumber(100000003);
            expectedAcademicBodyHead.setStatus("Disponible");
            AcademicBodyHead actualAcademicBodyHead = academicBodyHeadDAO.getAcademicBodyHeadsFromDatabase().get(0);   

            Assertions.assertTrue(expectedAcademicBodyHead.equals(actualAcademicBodyHead));
        } catch (DataRetrievalException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getSpecifiedAcademicBodyHeadsFromDatabaseTest() {
        try {
            AcademicBodyHeadDAO academicBodyHeadDAO = new AcademicBodyHeadDAO();
            AcademicBodyHead expectedAcademicBodyHead = new AcademicBodyHead();
            expectedAcademicBodyHead.setName("José René");
            expectedAcademicBodyHead.setFirstSurname("Mendoza");
            expectedAcademicBodyHead.setSecondSurname("Gonzalez");
            expectedAcademicBodyHead.setEmailAddress("joremago112@gmail.com");
            expectedAcademicBodyHead.setAlternateEmail("joremago111@gmail.com");
            expectedAcademicBodyHead.setPhoneNumber("2288563333");
            expectedAcademicBodyHead.setStaffNumber(100000003);
            expectedAcademicBodyHead.setStatus("Disponible");
            AcademicBodyHead actualAcademicBodyHead = academicBodyHeadDAO.getSpecifiedAcademicBodyHeadsFromDatabase("J").get(0); 

            Assertions.assertTrue(expectedAcademicBodyHead.equals(actualAcademicBodyHead));
        } catch (DataRetrievalException e) {
            e.printStackTrace();
        }
    }

    @Test
    void modifyAcademicBodyHeadDataFromDatabaseTest() {
        try {
            AcademicBodyHeadDAO academicBodyHeadDAO = new AcademicBodyHeadDAO();
            AcademicBodyHead originalAcademicBodyHead = academicBodyHeadDAO.getAcademicBodyHeadFromDatabase(140000000);
            AcademicBodyHead expectedAcademicBodyHead = new AcademicBodyHead();
            expectedAcademicBodyHead.setName("Guiovanny Manuel");
            expectedAcademicBodyHead.setFirstSurname("Salazar");
            expectedAcademicBodyHead.setSecondSurname("Vazquez");
            expectedAcademicBodyHead.setEmailAddress("lonjanini411@gmail.com");
            expectedAcademicBodyHead.setAlternateEmail("lonjanini111@gmail.com");
            expectedAcademicBodyHead.setPhoneNumber("2288923093");
            expectedAcademicBodyHead.setStaffNumber(140000001);
            expectedAcademicBodyHead.setStatus("No Disponible");
            academicBodyHeadDAO.modifyAcademicBodyHeadDataFromDatabase(expectedAcademicBodyHead, originalAcademicBodyHead);
            
            AcademicBodyHead actualAcademicBodyHead = academicBodyHeadDAO.getAcademicBodyHeadFromDatabase(expectedAcademicBodyHead.getStaffNumber());     
            Assertions.assertTrue(expectedAcademicBodyHead.equals(actualAcademicBodyHead));
        } catch (DataWritingException e) {
            e.printStackTrace();
        } catch (DataRetrievalException e) {
            e.printStackTrace();
        }
    }

    @Test
    void theAcademicBodyHeadIsAlreadyRegistedTest() {
        try {
            AcademicBodyHeadDAO academicBodyHeadDAO = new AcademicBodyHeadDAO();
            AcademicBodyHead expectedAcademicBodyHead = new AcademicBodyHead();
            expectedAcademicBodyHead.setName("José René");
            expectedAcademicBodyHead.setFirstSurname("Mendoza");
            expectedAcademicBodyHead.setSecondSurname("Gonzalez");
            expectedAcademicBodyHead.setEmailAddress("joremago112@gmail.com");
            expectedAcademicBodyHead.setAlternateEmail("joremago111@gmail.com");
            expectedAcademicBodyHead.setPhoneNumber("2288563333");
            expectedAcademicBodyHead.setStaffNumber(100000003);
            expectedAcademicBodyHead.setStatus("Disponible");

            Assertions.assertTrue(academicBodyHeadDAO.theAcademicBodyHeadIsAlreadyRegisted(expectedAcademicBodyHead));
        } catch (DataRetrievalException e) {
            e.printStackTrace();
        }
    }
}
