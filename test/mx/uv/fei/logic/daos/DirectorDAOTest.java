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
import mx.uv.fei.logic.domain.Director;
import mx.uv.fei.logic.domain.statuses.ProfessorStatus;
import mx.uv.fei.logic.exceptions.DataInsertionException;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.exceptions.DuplicatedPrimaryKeyException;

public class DirectorDAOTest {
    private static DataBaseManager dataBaseManager;
    private static Director preloadedDirector;
    private static Director failedDirector;
    private static Director preloadedDirectorForAddDirectorTest;

    @BeforeClass
    public static void setUpClass() {
        dataBaseManager = new DataBaseManager();
        
        try{
            //Adding a Director
            preloadedDirector = new Director();
            preloadedDirector.setName("Miguel Angel");
            preloadedDirector.setFirstSurname("Jimenez");
            preloadedDirector.setSecondSurname("Castillo");
            preloadedDirector.setEmailAddress("mianjica1581@gmail.com");
            preloadedDirector.setAlternateEmail("mianjica2345333@gmail.com");
            preloadedDirector.setPhoneNumber("2283492758");
            preloadedDirector.setStatus(ProfessorStatus.ACTIVE.getValue());
            preloadedDirector.setStaffNumber(489328392);

            String userQuery = "INSERT INTO Usuarios(nombre, apellidoPaterno, apellidoMaterno, correo, correoAlterno, númeroTeléfono, estado) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement userStatement = dataBaseManager.getConnection().prepareStatement(userQuery, Statement.RETURN_GENERATED_KEYS);
            userStatement.setString(1, preloadedDirector.getName());
            userStatement.setString(2, preloadedDirector.getFirstSurname());
            userStatement.setString(3, preloadedDirector.getSecondSurname());
            userStatement.setString(4, preloadedDirector.getEmailAddress());
            userStatement.setString(5, preloadedDirector.getAlternateEmail());
            userStatement.setString(6, preloadedDirector.getPhoneNumber());
            userStatement.setString(7, preloadedDirector.getStatus());
            userStatement.executeUpdate();
            
            ResultSet generatedUserKeys = userStatement.getGeneratedKeys();
            if(generatedUserKeys.next()){
                preloadedDirector.setUserId(generatedUserKeys.getInt(1));
            }
            generatedUserKeys.close();
            userStatement.close();
            
            String professorQuery = "INSERT INTO Profesores(NumPersonal, IdUsuario) VALUES (?,?)";
            PreparedStatement professorStatement = dataBaseManager.getConnection().prepareStatement(professorQuery);
            professorStatement.setInt(1, preloadedDirector.getStaffNumber());
            professorStatement.setInt(2, preloadedDirector.getUserId());
            professorStatement.executeUpdate();
            professorStatement.close();

            String DirectorQuery = "INSERT INTO Directores(NumPersonal) VALUES (?)";
            PreparedStatement DirectorStatement = dataBaseManager.getConnection().prepareStatement(DirectorQuery);
            DirectorStatement.setInt(1, preloadedDirector.getStaffNumber());
            DirectorStatement.executeUpdate();
            DirectorStatement.close();
            
            //Set data to a failed Director
            failedDirector = new Director();
            failedDirector.setName("Hector");
            failedDirector.setFirstSurname("Arámburo");
            failedDirector.setSecondSurname("Gomez");
            failedDirector.setEmailAddress("hearago121@gmail.com");
            failedDirector.setAlternateEmail("hearago923@gmail.com");
            failedDirector.setPhoneNumber("2287299111");
            failedDirector.setStatus(ProfessorStatus.INACTIVE.getValue());
            failedDirector.setStaffNumber(849837489);

            //Set data to a Professor to use in addDirectorTest
            preloadedDirectorForAddDirectorTest = new Director();
            preloadedDirectorForAddDirectorTest.setName("Joaquin");
            preloadedDirectorForAddDirectorTest.setFirstSurname("Guzmán");
            preloadedDirectorForAddDirectorTest.setSecondSurname("Sandijuela");
            preloadedDirectorForAddDirectorTest.setEmailAddress("joguza903@gmail.com");
            preloadedDirectorForAddDirectorTest.setStaffNumber(987293092);
        }catch(SQLException exception){
            fail("Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }
    
    @AfterClass
    public static void tearDownClass() {
        PreparedStatement statement;
        String queryToDeleteUser = "DELETE FROM Usuarios WHERE IdUsuario = ? || IdUsuario = ?";
        String queryToDeleteProfessor = "DELETE FROM Profesores WHERE NumPersonal = ? || NumPersonal = ?";
        String queryToDeleteDirector = "DELETE FROM Directores WHERE NumPersonal = ? || NumPersonal = ?";
        
        try{
            statement = dataBaseManager.getConnection().prepareStatement(queryToDeleteUser);
            statement.setInt(1, preloadedDirector.getUserId());
            statement.setInt(2, preloadedDirectorForAddDirectorTest.getUserId());
            statement.executeUpdate();

            statement = dataBaseManager.getConnection().prepareStatement(queryToDeleteProfessor);
            statement.setInt(1, preloadedDirector.getStaffNumber());
            statement.setInt(2, preloadedDirectorForAddDirectorTest.getStaffNumber());
            statement.executeUpdate();

            statement = dataBaseManager.getConnection().prepareStatement(queryToDeleteDirector);
            statement.setInt(1, preloadedDirector.getStaffNumber());
            statement.setInt(2, preloadedDirectorForAddDirectorTest.getStaffNumber());
            statement.executeUpdate();

            statement.close();
        }catch(SQLException exception){
            fail("Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }

    @Test
    public void addDirectorTest() {
        try {
            DirectorDAO instance = new DirectorDAO();
            int result = instance.addDirector(preloadedDirectorForAddDirectorTest);
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
    public void getDirectorTest() {
        try {
            DirectorDAO instance = new DirectorDAO();
            Director result = instance.getDirector(preloadedDirector.getStaffNumber());
            assertTrue(result.equals(preloadedDirector));
        }catch(DataRetrievalException exception){
            fail("Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }

    @Test
    public void getDirectorTestFail() {
        try {
            DirectorDAO instance = new DirectorDAO();
            Director result = instance.getDirector(preloadedDirector.getStaffNumber());
            assertTrue(!result.equals(failedDirector));
        }catch(DataRetrievalException exception){
            fail("Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }

    @Test
    public void getDirectorListTest(){
        try {
            DirectorDAO instance = new DirectorDAO();
            ArrayList<Director> result;
            result = instance.getDirectorList();
            assertTrue(result.contains(preloadedDirector));
        }catch(DataRetrievalException exception){
            fail("Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }

    @Test
    public void getDirectorListTestFail(){
        try {
            DirectorDAO instance = new DirectorDAO();
            ArrayList<Director> result;
            result = instance.getDirectorList();
            assertTrue(!result.contains(failedDirector));
        }catch(DataRetrievalException exception){
            fail("Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }

    @Test
    public void getDirectorsTest() {
        try {
            DirectorDAO instance = new DirectorDAO();
            ArrayList<Director> result = instance.getDirectors();
            assertTrue(result.contains(preloadedDirector));
        }catch(DataRetrievalException exception){
            fail("Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }

    @Test
    public void getDirectorsTestFail() {
        try {
            DirectorDAO instance = new DirectorDAO();
            ArrayList<Director> result = instance.getDirectors();
            assertTrue(!result.contains(failedDirector));
        }catch(DataRetrievalException exception){
            fail("Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }
    
    @Test
    public void getSpecifiedDirectorsTest() {
        try {
            DirectorDAO instance = new DirectorDAO();
            ArrayList<Director> result = instance.getSpecifiedDirectors("Mi");
            assertTrue(result.contains(preloadedDirector));
        }catch(DataRetrievalException exception){
            fail("Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }

    @Test
    public void getSpecifiedDirectorsTestFail() {
        try {
            DirectorDAO instance = new DirectorDAO();
            ArrayList<Director> result = instance.getSpecifiedDirectors("Mi");
            assertTrue(!result.contains(failedDirector));
        }catch(DataRetrievalException exception){
            fail("Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }
    
    @Test
    public void modifyDirectorDataTest() {
        try {
            DirectorDAO instance = new DirectorDAO();
            preloadedDirector.setName("Felipe Tobar");
            preloadedDirector.setFirstSurname("Guzmán");
            preloadedDirector.setSecondSurname("Toral");
            preloadedDirector.setEmailAddress("fetoguto901@gmail.com");
            int result = instance.modifyDirectorData(preloadedDirector);
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
