package mx.uv.fei.logic.daos;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.logic.domain.Course;
import mx.uv.fei.logic.domain.Director;
import mx.uv.fei.logic.domain.ResearchProject;
import mx.uv.fei.logic.domain.Student;
import mx.uv.fei.logic.exceptions.DataInsertionException;
import mx.uv.fei.logic.exceptions.DataRetrievalException;

public class ResearchDAOTest {
    private static DataBaseManager dataBaseManager;
    private static Course preloadedCourse;
    private static Director preloadedDirector;
    private static ResearchProject preloadedResearch;
    private static Student preloadedStudent;
    
    @BeforeClass
    public static void setUpClass(){
        dataBaseManager = new DataBaseManager();
        
        preloadedCourse = new Course();
        preloadedCourse.setNrc(87453);
        preloadedCourse.setName("Proyecto Guiado");
        preloadedCourse.setProfessor(preloadedDirector);
        
        preloadedDirector = new Director();
        preloadedDirector.setStaffNumber(845321486);
        preloadedDirector.setSecondSurname("Guerrero");
        preloadedDirector.setName("María de los Ángeles");
        preloadedDirector.setFirstSurname("Navarro");
        
        preloadedStudent = new Student();
        preloadedStudent.setMatricle("zS21642610");
        preloadedStudent.setName("Juanito");
        preloadedStudent.setFirstSurname("Pérez");
        preloadedStudent.setSecondSurname("Sánchez");
        
        preloadedResearch = new ResearchProject();
        preloadedResearch.setStartDate(Date.valueOf(LocalDate.of(2023, Month.MARCH, 19)));
        preloadedResearch.setDueDate(Date.valueOf(LocalDate.of(2023, Month.DECEMBER, 2)));
        preloadedResearch.setTitle("Anteproyecto de prueba");
        preloadedResearch.setDescription("Este es un anteproyecto de prueba para el ResearchDAOTest");
        preloadedResearch.setExpectedResult("Se espera que el anteproyecto pase todas las pruebas exitosamente");
        preloadedResearch.setRequirements("Para lograrlo se necesitan hacer buenas pruebas");
        preloadedResearch.setSuggestedBibliography("APA");
        
        try{
            //Adding a director
            String userQuery = "INSERT INTO Usuarios(nombre, apellidoPaterno,apellidoMaterno) VALUES(?,?,?)";
            PreparedStatement userStatement = dataBaseManager.getConnection().prepareStatement(userQuery, PreparedStatement.RETURN_GENERATED_KEYS);
            
            userStatement.setString(1, preloadedDirector.getName());
            userStatement.setString(2, preloadedDirector.getFirstSurname());
            userStatement.setString(3, preloadedDirector.getSecondSurname());
            userStatement.executeUpdate();
            
            ResultSet generatedProfessorKeys = userStatement.getGeneratedKeys();
            
            if(generatedProfessorKeys.next()){
                preloadedDirector.setUserId(generatedProfessorKeys.getInt(1));
            }
            
            String professorQuery = "INSERT INTO Profesores(NumPersonal, IdUsuario) VALUES(?,?)";
            PreparedStatement professorStatement = dataBaseManager.getConnection().prepareStatement(professorQuery, PreparedStatement.RETURN_GENERATED_KEYS);
            
            professorStatement.setInt(1, preloadedDirector.getStaffNumber());
            professorStatement.setInt(2, preloadedDirector.getUserId());
            professorStatement.executeUpdate();
            
            String directorQuery = "INSERT INTO Directores(NumPersonal) VALUES(?)";
            PreparedStatement directorStatement = dataBaseManager.getConnection().prepareStatement(directorQuery, PreparedStatement.RETURN_GENERATED_KEYS);
            
            directorStatement.setInt(1, preloadedDirector.getStaffNumber());
            directorStatement.executeUpdate();
            
            ResultSet generatedDirectorKeys = directorStatement.getGeneratedKeys();
            
            if(generatedDirectorKeys.next()){
                preloadedDirector.setDirectorId(1);
            }
            
            //Adding a student
            userStatement.setString(1, preloadedStudent.getName());
            userStatement.setString(2, preloadedStudent.getFirstSurname());
            userStatement.setString(3, preloadedStudent.getSecondSurname());
            
            ResultSet generatedStudentKeys = userStatement.getGeneratedKeys();
            
            if(generatedStudentKeys.next()){
                preloadedStudent.setUserId(generatedStudentKeys.getInt(1));
            }
            
            String studentQuery = "INSERT INTO Estudiantes(Matrícula, IdUsuario) VALUES(?,?)";
            PreparedStatement studentStatement = dataBaseManager.getConnection().prepareStatement(studentQuery);
            
            studentStatement.setString(1, preloadedStudent.getMatricle());
            studentStatement.setInt(2, preloadedStudent.getUserId());
            
            //Adding course
            String courseQuery = "INSERT INTO Cursos(NRC, NumPersonal, nombre) VALUES(?,?)";
            PreparedStatement courseStatement = dataBaseManager.getConnection().prepareStatement(courseQuery);
            
            courseStatement.setInt(1, preloadedCourse.getNrc());
            courseStatement.setInt(2, preloadedCourse.getProfessor().getStaffNumber());
            courseStatement.setString(3, preloadedCourse.getName());
            
            userStatement.setString(1, preloadedStudent.getName());
            userStatement.setString(2, preloadedStudent.getFirstSurname());
            
            //Assign student to course
            String studentCourseQuery = "INSERT INTO EstudiantesCurso(Matrícula, NRC) VALUES(?,?)";
            PreparedStatement studentCourseStatement = dataBaseManager.getConnection().prepareStatement(studentCourseQuery);
            
            studentCourseStatement.setString(1, preloadedStudent.getMatricle());
            studentCourseStatement.setInt(2, preloadedCourse.getNrc());
            studentCourseStatement.executeUpdate();
            
            //Adding research
            String researchQuery = "INSERT INTO Anteproyectos(fechaFin, fechaInicio, título, descripción, resultadosEsperados, requisitos, IdDirector1, Matrícula) VALUES(?,?,?,?,?,?,?,?);";
            PreparedStatement researchStatement = dataBaseManager.getConnection().prepareStatement(researchQuery, PreparedStatement.RETURN_GENERATED_KEYS);
            
            researchStatement.setDate(1, preloadedResearch.getDueDate());
            researchStatement.setDate(2, preloadedResearch.getStartDate());
            researchStatement.setString(3, preloadedResearch.getTitle());
            researchStatement.setString(4, preloadedResearch.getDescription());
            researchStatement.setString(5, preloadedResearch.getExpectedResult());
            researchStatement.setString(6, preloadedResearch.getRequirements());
            researchStatement.setInt(7, preloadedDirector.getDirectorId());
            researchStatement.setString(8, preloadedStudent.getMatricle());
            
            researchStatement.executeUpdate();
            ResultSet generatedResearchKeys = researchStatement.getGeneratedKeys();
            
            if(generatedResearchKeys.next()){
                preloadedResearch.setId(generatedResearchKeys.getInt(1));
            }
        }catch(SQLException exception){
            fail("Failed to add test data. Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }
    
    @AfterClass
    public static void tearDownClass() {
        PreparedStatement statement;
        String query = "DELETE FROM Usuarios WHERE IdUsuario IN(?,?)";
        
        try{
            statement = dataBaseManager.getConnection().prepareStatement(query);
            statement.setInt(1, preloadedStudent.getUserId());
            statement.setInt(2, preloadedDirector.getUserId());
            
            statement.executeUpdate();
        }catch(SQLException exception){
            fail("Failed to delete test data. Couldn't connect to DB");
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
        preloadedResearch.printData();
        int result = instance.modifyResearch(preloadedResearch);
        assertTrue(result > 0);
    }

    @Test
    public void testGetDirectorsResearch() throws Exception{
        
    }

    @Test
    public void testGetStudentsResearch() throws Exception{
        
    }

    @Test
    public void testGetCourseResearch() throws Exception{
        
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
        ResearchDAO instance = new ResearchDAO();
        
        assertTrue(instance.areDirectorsDifferent(preloadedResearch));
    }
    
    @Test
    public void testAreDirectorsDifferentFail(){    
        ResearchDAO instance = new ResearchDAO();
        
        assertTrue(!instance.areDirectorsDifferent(preloadedResearch));
    }
}