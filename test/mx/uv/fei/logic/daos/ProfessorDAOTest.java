package mx.uv.fei.logic.daos;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import mx.uv.fei.logic.domain.Professor;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.exceptions.DataWritingException;

public class ProfessorDAOTest {
    @Test
    void addProfessorToDatabaseTest() {
        try {
            ProfessorDAO professorDAO = new ProfessorDAO();
            Professor expectedProfessor = new Professor();
            expectedProfessor.setName("Jorge Alberto");
            expectedProfessor.setFirstSurname("Delfín");
            expectedProfessor.setSecondSurname("Dominguez");
            expectedProfessor.setEmailAddress("joaldedo555@gmail.com");
            expectedProfessor.setAlternateEmail("joaldedo599@gmail.com");
            expectedProfessor.setPhoneNumber("2287283982");
            expectedProfessor.setStaffNumber(120000000);
            expectedProfessor.setStatus("Activo");
            professorDAO.addProfessorToDatabase(expectedProfessor);
            
            Professor actualProfessor = professorDAO.getProfessorFromDatabase(expectedProfessor.getStaffNumber());     
            Assertions.assertTrue(expectedProfessor.equals(actualProfessor));
        } catch (DataWritingException e) {
            e.printStackTrace();
        } catch (DataRetrievalException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getProfessorFromDatabaseTest() {
        try {
            ProfessorDAO professorDAO = new ProfessorDAO();
            Professor expectedProfessor = new Professor();
            expectedProfessor.setName("Jorge Alberto");
            expectedProfessor.setFirstSurname("Guevara");
            expectedProfessor.setSecondSurname("Cerdán");
            expectedProfessor.setEmailAddress("jaga234@gmail.com");
            expectedProfessor.setAlternateEmail("jaga243@gmail.com");
            expectedProfessor.setPhoneNumber("2283487254");
            expectedProfessor.setStaffNumber(100000001);
            expectedProfessor.setStatus("No Disponible");
            Professor actualProfessor = professorDAO.getProfessorFromDatabase(expectedProfessor.getStaffNumber());  

            Assertions.assertTrue(expectedProfessor.equals(actualProfessor));
        } catch (DataRetrievalException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getProfessorsFromDatabaseTest() {
        try {
            ProfessorDAO professorDAO = new ProfessorDAO();
            Professor expectedProfessor = new Professor();
            expectedProfessor.setName("Jorge Alberto");
            expectedProfessor.setFirstSurname("Guevara");
            expectedProfessor.setSecondSurname("Cerdán");
            expectedProfessor.setEmailAddress("jaga234@gmail.com");
            expectedProfessor.setAlternateEmail("jaga243@gmail.com");
            expectedProfessor.setPhoneNumber("2283487254");
            expectedProfessor.setStaffNumber(100000001);
            expectedProfessor.setStatus("No Disponible");
            Professor actualProfessor = professorDAO.getProfessorsFromDatabase().get(0);   

            Assertions.assertTrue(expectedProfessor.equals(actualProfessor));
        } catch (DataRetrievalException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getSpecifiedProfessorsFromDatabaseTest() {
        try {
            ProfessorDAO professorDAO = new ProfessorDAO();
            Professor expectedProfessor = new Professor();
            expectedProfessor.setName("Jorge Alberto");
            expectedProfessor.setFirstSurname("Guevara");
            expectedProfessor.setSecondSurname("Cerdán");
            expectedProfessor.setEmailAddress("jaga234@gmail.com");
            expectedProfessor.setAlternateEmail("jaga243@gmail.com");
            expectedProfessor.setPhoneNumber("2283487254");
            expectedProfessor.setStaffNumber(100000001);
            expectedProfessor.setStatus("No Disponible");
            Professor actualProfessor = professorDAO.getSpecifiedProfessorsFromDatabase("J").get(0); 

            Assertions.assertTrue(expectedProfessor.equals(actualProfessor));
        } catch (DataRetrievalException e) {
            e.printStackTrace();
        }
    }

    @Test
    void modifyProfessorDataFromDatabaseTest() {
        try {
            ProfessorDAO professorDAO = new ProfessorDAO();
            Professor originalProfessor = professorDAO.getProfessorFromDatabase(120000000);
            Professor expectedProfessor = new Professor();
            expectedProfessor.setName("Jorge Alberto");
            expectedProfessor.setFirstSurname("Delfín");
            expectedProfessor.setSecondSurname("Dominguez");
            expectedProfessor.setEmailAddress("joaldedo555@gmail.com");
            expectedProfessor.setAlternateEmail("joaldedo599@gmail.com");
            expectedProfessor.setPhoneNumber("2287283982");
            expectedProfessor.setStaffNumber(120000001);
            expectedProfessor.setStatus("Disponible");
            professorDAO.modifyProfessorDataFromDatabase(expectedProfessor, originalProfessor);
            
            Professor actualProfessor = professorDAO.getProfessorFromDatabase(expectedProfessor.getStaffNumber());     
            Assertions.assertTrue(expectedProfessor.equals(actualProfessor));
        } catch (DataWritingException e) {
            e.printStackTrace();
        } catch (DataRetrievalException e) {
            e.printStackTrace();
        }
    }

    @Test
    void theProfessorIsAlreadyRegistedTest() {
        try {
            ProfessorDAO professorDAO = new ProfessorDAO();
            Professor expectedProfessor = new Professor();
            expectedProfessor.setName("Jorge Alberto");
            expectedProfessor.setFirstSurname("Guevara");
            expectedProfessor.setSecondSurname("Cerdán");
            expectedProfessor.setEmailAddress("jaga234@gmail.com");
            expectedProfessor.setAlternateEmail("jaga243@gmail.com");
            expectedProfessor.setPhoneNumber("2283487254");
            expectedProfessor.setStaffNumber(100000001);
            expectedProfessor.setStatus("No Disponible");

            Assertions.assertTrue(professorDAO.theProfessorIsAlreadyRegisted(expectedProfessor));
        } catch (DataRetrievalException e) {
            e.printStackTrace();
        }
    }
}
