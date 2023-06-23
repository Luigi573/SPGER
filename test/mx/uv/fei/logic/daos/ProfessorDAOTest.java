package mx.uv.fei.logic.daos;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.logic.domain.Professor;
import mx.uv.fei.logic.domain.statuses.ProfessorStatus;
import mx.uv.fei.logic.exceptions.DataInsertionException;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.exceptions.DuplicatedPrimaryKeyException;

public class ProfessorDAOTest {
    private static DataBaseManager dataBaseManager;
    private static Professor preloadedProfessor;
    private static Professor failedProfessor;
    private static Professor preloadedProfessorForAddProfessorTest;

    @BeforeClass
    public static void setUpClass() {
        dataBaseManager = new DataBaseManager();
        
        try{
            //Adding a Professor
            preloadedProfessor = new Professor();
            preloadedProfessor.setName("Jorge Alberto");
            preloadedProfessor.setFirstSurname("Guevara");
            preloadedProfessor.setSecondSurname("Cerdán");
            preloadedProfessor.setEmailAddress("joalguece862@gmail.com");
            preloadedProfessor.setAlternateEmail("joalguece999@gmail.com");
            preloadedProfessor.setPhoneNumber("2289487337");
            preloadedProfessor.setStatus(ProfessorStatus.ACTIVE.getValue());
            preloadedProfessor.setStaffNumber(879823947);

            String userQuery = "INSERT INTO Usuarios(nombre, apellidoPaterno, apellidoMaterno, correo, correoAlterno, numeroTelefono, estado) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement userStatement = dataBaseManager.getConnection().prepareStatement(userQuery, Statement.RETURN_GENERATED_KEYS);
            userStatement.setString(1, preloadedProfessor.getName());
            userStatement.setString(2, preloadedProfessor.getFirstSurname());
            userStatement.setString(3, preloadedProfessor.getSecondSurname());
            userStatement.setString(4, preloadedProfessor.getEmailAddress());
            userStatement.setString(5, preloadedProfessor.getAlternateEmail());
            userStatement.setString(6, preloadedProfessor.getPhoneNumber());
            userStatement.setString(7, preloadedProfessor.getStatus());
            userStatement.executeUpdate();
            
            ResultSet generatedUserKeys = userStatement.getGeneratedKeys();
            if(generatedUserKeys.next()){
                preloadedProfessor.setUserId(generatedUserKeys.getInt(1));
            }
            generatedUserKeys.close();
            userStatement.close();
            
            String professorQuery = "INSERT INTO Profesores(NumPersonal, IdUsuario) VALUES (?,?)";
            PreparedStatement professorStatement = dataBaseManager.getConnection().prepareStatement(professorQuery);
            professorStatement.setInt(1, preloadedProfessor.getStaffNumber());
            professorStatement.setInt(2, preloadedProfessor.getUserId());
            professorStatement.executeUpdate();
            professorStatement.close();
            
            //Set data to a failed professor
            failedProfessor = new Professor();
            failedProfessor.setName("Andrés Guiovanni");
            failedProfessor.setFirstSurname("Flores");
            failedProfessor.setSecondSurname("Falfán");
            failedProfessor.setEmailAddress("anguioflofa982@gmail.com");
            failedProfessor.setAlternateEmail("anguioflofa333@gmail.com");
            failedProfessor.setPhoneNumber("2283788222");
            failedProfessor.setStatus(ProfessorStatus.INACTIVE.getValue());
            failedProfessor.setStaffNumber(988476534);

            //Set data to a Professor to use in addProfessorTest
            preloadedProfessorForAddProfessorTest = new Professor();
            preloadedProfessorForAddProfessorTest.setName("Heriberto García");
            preloadedProfessorForAddProfessorTest.setFirstSurname("Gonzalez");
            preloadedProfessorForAddProfessorTest.setSecondSurname("Nueces");
            preloadedProfessorForAddProfessorTest.setEmailAddress("hegagonu982@gmail.com");
            preloadedProfessorForAddProfessorTest.setStaffNumber(811578654);
            preloadedProfessorForAddProfessorTest.setUserId(999999);
        }catch(SQLException exception){
            fail("Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }
    
    @AfterClass
    public static void tearDownClass(){
        PreparedStatement statement;
        String queryToDeleteUser = "DELETE FROM Usuarios WHERE IdUsuario = ? || IdUsuario = ?";
        String queryToDeleteProfessor = "DELETE FROM Profesores WHERE NumPersonal = ? || NumPersonal = ?";
        
        try{
            statement = dataBaseManager.getConnection().prepareStatement(queryToDeleteUser);
            statement.setInt(1, preloadedProfessor.getUserId());
            statement.setInt(2, preloadedProfessorForAddProfessorTest.getUserId());
            statement.executeUpdate();

            statement = dataBaseManager.getConnection().prepareStatement(queryToDeleteProfessor);
            statement.setInt(1, preloadedProfessor.getStaffNumber());
            statement.setInt(2, preloadedProfessorForAddProfessorTest.getStaffNumber());
            statement.executeUpdate();
            statement.close();
        }catch(SQLException exception){
            fail("Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }

    @Test
    public void addProfessorTest() {
        try {
            ProfessorDAO instance = new ProfessorDAO();
            int result = instance.addProfessor(preloadedProfessorForAddProfessorTest);
            assertTrue(result > 0);
        }catch(DataInsertionException exception){
            fail("Couldn't connect to DB");
        }catch(DuplicatedPrimaryKeyException e) {
            fail("Duplicated primary key");
        }finally{
            dataBaseManager.closeConnection();
        }
    }

    @Test
    public void getProfessorTest() {
        try {
            ProfessorDAO instance = new ProfessorDAO();
            Professor result = instance.getProfessor(preloadedProfessor.getStaffNumber());
            assertTrue(result.equals(preloadedProfessor));
        }catch(DataRetrievalException exception){
            fail("Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }

    @Test
    public void getProfessorTestFail() {
        try {
            ProfessorDAO instance = new ProfessorDAO();
            Professor result = instance.getProfessor(preloadedProfessor.getStaffNumber());
            assertTrue(!result.equals(failedProfessor));
        }catch(DataRetrievalException exception){
            fail("Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }

    @Test
    public void getProfessorsTest() {
        try {
            ProfessorDAO instance = new ProfessorDAO();
            ArrayList<Professor> result = instance.getProfessors();
            assertTrue(result.contains(preloadedProfessor));
        }catch(DataRetrievalException exception){
              ;
            fail("Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }

    @Test
    public void getProfessorsTestFail() {
        try {
            ProfessorDAO instance = new ProfessorDAO();
            ArrayList<Professor> result = instance.getProfessors();
            assertTrue(!result.contains(failedProfessor));
        }catch(DataRetrievalException exception){
            fail("Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }

    @Test
    public void getSpecifiedProfessorsTest() {
        try {
            ProfessorDAO instance = new ProfessorDAO();
            ArrayList<Professor> result = instance.getSpecifiedProfessors("J");
            assertTrue(result.contains(preloadedProfessor));
        }catch(DataRetrievalException exception){
            fail("Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }

    @Test
    public void getSpecifiedProfessorsTestFail() {
        try {
            ProfessorDAO instance = new ProfessorDAO();
            ArrayList<Professor> result = instance.getSpecifiedProfessors("J");
            assertTrue(!result.contains(failedProfessor));
        }catch(DataRetrievalException exception){
            fail("Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }

    @Test
    public void modifyProfessorDataTest() {
        try {
            ProfessorDAO instance = new ProfessorDAO();
            preloadedProfessor.setName("José Humberto");
            preloadedProfessor.setFirstSurname("Mantarrayo");
            preloadedProfessor.setSecondSurname("Jimenez");
            preloadedProfessor.setEmailAddress("johumaji111@gmail.com");
            int result = instance.modifyProfessorData(preloadedProfessor);
            assertTrue(result > 0);
        }catch(DataInsertionException exception){
            fail("Couldn't connect to DB");
        }catch(DuplicatedPrimaryKeyException e) {
            fail("Duplicated primary key");
        }finally{
            dataBaseManager.closeConnection();
        }
    } 
}
