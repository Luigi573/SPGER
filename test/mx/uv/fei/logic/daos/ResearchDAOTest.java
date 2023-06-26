package mx.uv.fei.logic.daos;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.logic.domain.ResearchProject;
import mx.uv.fei.logic.exceptions.DataInsertionException;
import mx.uv.fei.logic.exceptions.DataRetrievalException;

public class ResearchDAOTest {
    private static DataBaseManager dataBaseManager;
    private static ResearchProject preloadedResearch;
    
    @BeforeClass
    public static void setUpClass(){
        preloadedResearch = new ResearchProject();
        preloadedResearch.setStartDate(Date.valueOf(LocalDate.of(2023, Month.MARCH, 19)));
        preloadedResearch.setDueDate(Date.valueOf(LocalDate.of(2023, Month.DECEMBER, 2)));
        preloadedResearch.setTitle("Anteproyecto de prueba");
        preloadedResearch.setDescription("Este es un anteproyecto de prueba para el ResearchDAOTest");
        preloadedResearch.setExpectedResult("Se espera que el anteproyecto pase todas las pruebas exitosamente");
        preloadedResearch.setRequirements("Para lograrlo se necesitan hacer buenas pruebas");
        preloadedResearch.setSuggestedBibliography("APA");
        
        dataBaseManager = new DataBaseManager();
        PreparedStatement statement;
        
        String query = "INSERT INTO Anteproyectos(fechaFin, fechaInicio, título, descripción, "
                + "resultadosEsperados, requisitos) "
                + "VALUES(?,?,?,?,?,?);";
        
        try{
            statement = dataBaseManager.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            
            statement.setDate(1, preloadedResearch.getDueDate());
            statement.setDate(2, preloadedResearch.getStartDate());
            statement.setString(3, preloadedResearch.getTitle());
            statement.setString(4, preloadedResearch.getDescription());
            statement.setString(5, preloadedResearch.getExpectedResult());
            statement.setString(6, preloadedResearch.getRequirements());
            
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            
            if(generatedKeys.next()){
                preloadedResearch.setId(generatedKeys.getInt(1));
            }
        }catch(SQLException exception){
            fail("Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }
    
    @AfterClass
    public static void tearDownClass() {
        PreparedStatement statement;
        String query = "DELETE FROM Anteproyectos WHERE IdAnteproyecto IN(?)";
        
        try{
            statement = dataBaseManager.getConnection().prepareStatement(query);
            statement.setInt(1, preloadedResearch.getId());
            statement.executeUpdate();
        }catch(SQLException exception){
            fail("Failed to delete research. Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }

    @Test
    public void testAddResearch() throws DataInsertionException {
        System.out.println("addResearch");
        ResearchProject research = new ResearchProject();
        research.setStartDate(Date.valueOf(LocalDate.of(2023, Month.MARCH, 19)));
        research.setDueDate(Date.valueOf(LocalDate.of(2023, Month.MARCH, 19)));
        research.setTitle("Anteproyecto de prueba para el método addResearch()");
        research.setDescription("Este es un anteproyecto de prueba para el ResearchDAOTest");
        research.setExpectedResult("Se espera que el anteproyecto devuelva el Id del proyecto generado");
        research.setRequirements("Para lograrlo se necesitan hacer buenas pruebas");
        research.setSuggestedBibliography("APA");
        
        
        ResearchDAO instance = new ResearchDAO();
        int result = instance.addResearch(research);
        assertTrue(result > 0);
    }

    @Test
    public void testGetResearchProjectList() throws DataRetrievalException {
        ResearchDAO instance = new ResearchDAO();
        ArrayList<ResearchProject> result = instance.getResearchProjectList();
        
        assertTrue(result.contains(preloadedResearch));
    }

    @Test
    public void testModifyResearch() throws DataInsertionException {
        System.out.println("Testing modifyResearch");
        preloadedResearch.setTitle("Modified research project");
        preloadedResearch.setDescription("I modified the preloaded research so it now has this description");
        preloadedResearch.setExpectedResult("Expected result is researchDAO will return one, meaning it affected the specified research");
        preloadedResearch.setRequirements("DB connection");
        
        ResearchDAO instance = new ResearchDAO();
        int result = instance.modifyResearch(preloadedResearch);
        assertTrue(result > 0);
    }
    
    @Test
    public void testAssertResearch() {
        System.out.println("assertResearch");
        ResearchDAO instance = new ResearchDAO();
       
        System.out.println("This method is a combination of isBlank() and isValidDate(), if it passes both, it should pass this one");
        assertTrue(instance.assertResearch(preloadedResearch));
    }

    @Test
    public void testIsBlank(){
        System.out.println("\nTesting 'isBlank()' with no empty required fields");
        ResearchProject research = new ResearchProject();
        research.setTitle("");
        research.setDescription("");
        ResearchDAO instance = new ResearchDAO();
        
        assertTrue(instance.isBlank(research));
    }
    @Test
    public void testIsBlankFail(){
        System.out.println("\nTesting 'isBlank()' with at least one blank required field");
        ResearchProject research = preloadedResearch;
        ResearchDAO instance = new ResearchDAO();
        
        assertTrue(!instance.isBlank(research));
    }

    @Test
    public void testIsValidDate(){
        System.out.println("\nTesting 'isValidDate()' with valid input");
        ResearchDAO instance = new ResearchDAO();
        
        System.out.println("Start Date: " + preloadedResearch.getStartDate());
        System.out.println("DueDate: " + preloadedResearch.getDueDate());
        System.out.println("If the start date is before or the same date as dueDate, then it's valid");
        assertTrue(instance.isValidDate(preloadedResearch));
    }
    @Test
    public void testIsValidDateFail(){
        System.out.println("\nTesting 'isValidDate' with invalid input");
        ResearchProject research = new ResearchProject();
        research.setStartDate(Date.valueOf(LocalDate.of(2024, Month.DECEMBER, 31)));
        research.setDueDate(Date.valueOf(LocalDate.of(2023, Month.MAY, 19)));
        System.out.println("Start Date: " + research.getStartDate());
        System.out.println("DueDate: " + research.getDueDate());
        
        ResearchDAO instance = new ResearchDAO();
        System.out.println("If due date is before startDate, then it's invalid");
        assertTrue(!instance.isValidDate(research));
    }

    @Test
    public void testAreDirectorsDifferent(){    
        
    }
    
    @Test
    public void testAreDirectorsDifferentFail(){    
        
    }
}