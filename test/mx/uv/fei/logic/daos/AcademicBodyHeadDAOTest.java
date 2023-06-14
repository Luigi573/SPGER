package mx.uv.fei.logic.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.logic.domain.AcademicBodyHead;
import mx.uv.fei.logic.domain.statuses.ProfessorStatus;
import mx.uv.fei.logic.exceptions.DataInsertionException;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.exceptions.DuplicatedPrimaryKeyException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class AcademicBodyHeadDAOTest {
    private static DataBaseManager dataBaseManager;
    private static AcademicBodyHead preloadedAcademicBodyHead;
    private static AcademicBodyHead failedAcademicBodyHead;
    private static AcademicBodyHead preloadedAcademicBodyHeadForAddAcademicBodyHeadTest;

    @BeforeClass
    public static void setUpClass() {
        dataBaseManager = new DataBaseManager();
        
        try{
            //Adding a AcademicBodyHead
            preloadedAcademicBodyHead = new AcademicBodyHead();
            preloadedAcademicBodyHead.setName("Jorge Alberto");
            preloadedAcademicBodyHead.setFirstSurname("Delfín");
            preloadedAcademicBodyHead.setSecondSurname("Dominguez");
            preloadedAcademicBodyHead.setEmailAddress("joaldedo490@gmail.com");
            preloadedAcademicBodyHead.setAlternateEmail("joaldedo333@gmail.com");
            preloadedAcademicBodyHead.setPhoneNumber("2283492758");
            preloadedAcademicBodyHead.setStatus(ProfessorStatus.ACTIVE.getValue());
            preloadedAcademicBodyHead.setStaffNumber(489328392);

            String userQuery = "INSERT INTO Usuarios(nombre, apellidoPaterno, apellidoMaterno, correo, correoAlterno, númeroTeléfono, estado) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement userStatement = dataBaseManager.getConnection().prepareStatement(userQuery, Statement.RETURN_GENERATED_KEYS);
            userStatement.setString(1, preloadedAcademicBodyHead.getName());
            userStatement.setString(2, preloadedAcademicBodyHead.getFirstSurname());
            userStatement.setString(3, preloadedAcademicBodyHead.getSecondSurname());
            userStatement.setString(4, preloadedAcademicBodyHead.getEmailAddress());
            userStatement.setString(5, preloadedAcademicBodyHead.getAlternateEmail());
            userStatement.setString(6, preloadedAcademicBodyHead.getPhoneNumber());
            userStatement.setString(7, preloadedAcademicBodyHead.getStatus());
            userStatement.executeUpdate();
            
            ResultSet generatedUserKeys = userStatement.getGeneratedKeys();
            if(generatedUserKeys.next()){
                preloadedAcademicBodyHead.setUserId(generatedUserKeys.getInt(1));
            }
            generatedUserKeys.close();
            userStatement.close();
            
            String professorQuery = "INSERT INTO Profesores(NumPersonal, IdUsuario) VALUES (?,?)";
            PreparedStatement professorStatement = dataBaseManager.getConnection().prepareStatement(professorQuery);
            professorStatement.setInt(1, preloadedAcademicBodyHead.getStaffNumber());
            professorStatement.setInt(2, preloadedAcademicBodyHead.getUserId());
            professorStatement.executeUpdate();
            professorStatement.close();

            String academicBodyHeadQuery = "INSERT INTO ResponsablesCA(NumPersonal) VALUES (?)";
            PreparedStatement academicBodyHeadStatement = dataBaseManager.getConnection().prepareStatement(academicBodyHeadQuery);
            academicBodyHeadStatement.setInt(1, preloadedAcademicBodyHead.getStaffNumber());
            academicBodyHeadStatement.executeUpdate();
            academicBodyHeadStatement.close();
            
            //Set data to a failed AcademicBodyHead
            failedAcademicBodyHead = new AcademicBodyHead();
            failedAcademicBodyHead.setName("Hector");
            failedAcademicBodyHead.setFirstSurname("Arámburo");
            failedAcademicBodyHead.setSecondSurname("Gomez");
            failedAcademicBodyHead.setEmailAddress("hearago121@gmail.com");
            failedAcademicBodyHead.setAlternateEmail("hearago923@gmail.com");
            failedAcademicBodyHead.setPhoneNumber("2287299111");
            failedAcademicBodyHead.setStatus(ProfessorStatus.INACTIVE.getValue());
            failedAcademicBodyHead.setStaffNumber(849837489);

            //Set data to a Professor to use in addAcademicBodyHeadTest
            preloadedAcademicBodyHeadForAddAcademicBodyHeadTest = new AcademicBodyHead();
            preloadedAcademicBodyHeadForAddAcademicBodyHeadTest.setName("Joaquin");
            preloadedAcademicBodyHeadForAddAcademicBodyHeadTest.setFirstSurname("Guzmán");
            preloadedAcademicBodyHeadForAddAcademicBodyHeadTest.setSecondSurname("Sandijuela");
            preloadedAcademicBodyHeadForAddAcademicBodyHeadTest.setEmailAddress("joguza903@gmail.com");
            preloadedAcademicBodyHeadForAddAcademicBodyHeadTest.setStaffNumber(987293092);
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
        String queryToDeleteAcademicBodyHead = "DELETE FROM ResponsablesCA WHERE NumPersonal = ? || NumPersonal = ?";
        
        try{
            statement = dataBaseManager.getConnection().prepareStatement(queryToDeleteUser);
            statement.setInt(1, preloadedAcademicBodyHead.getUserId());
            statement.setInt(2, preloadedAcademicBodyHeadForAddAcademicBodyHeadTest.getUserId());
            statement.executeUpdate();

            statement = dataBaseManager.getConnection().prepareStatement(queryToDeleteProfessor);
            statement.setInt(1, preloadedAcademicBodyHead.getStaffNumber());
            statement.setInt(2, preloadedAcademicBodyHeadForAddAcademicBodyHeadTest.getStaffNumber());
            statement.executeUpdate();

            statement = dataBaseManager.getConnection().prepareStatement(queryToDeleteAcademicBodyHead);
            statement.setInt(1, preloadedAcademicBodyHead.getStaffNumber());
            statement.setInt(2, preloadedAcademicBodyHeadForAddAcademicBodyHeadTest.getStaffNumber());
            statement.executeUpdate();

            statement.close();
        }catch(SQLException exception){
            fail("Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }
    
    @Test
    public void addAcademicBodyHeadTest() throws DataInsertionException, DuplicatedPrimaryKeyException{
        AcademicBodyHeadDAO instance = new AcademicBodyHeadDAO();
        
        int result = instance.addAcademicBodyHead(preloadedAcademicBodyHeadForAddAcademicBodyHeadTest);
        assertTrue(result > 0);
    }

    @Test
    public void getAcademicBodyHeadTest() throws DataRetrievalException{
        AcademicBodyHeadDAO instance = new AcademicBodyHeadDAO();
        AcademicBodyHead result = instance.getAcademicBodyHead(preloadedAcademicBodyHead.getStaffNumber());
        assertTrue(result.equals(preloadedAcademicBodyHead));
    }

    @Test
    public void getAcademicBodyHeadTestFail() throws DataRetrievalException{
        AcademicBodyHeadDAO instance = new AcademicBodyHeadDAO();
        AcademicBodyHead result = instance.getAcademicBodyHead(preloadedAcademicBodyHead.getStaffNumber());
        
        assertTrue(!result.equals(preloadedAcademicBodyHeadForAddAcademicBodyHeadTest));
    }
    
    @Test
    public void getAcademicBodyHeadsTest() throws DataRetrievalException{
        AcademicBodyHeadDAO instance = new AcademicBodyHeadDAO();
        ArrayList<AcademicBodyHead> result = instance.getAcademicBodyHeads();
        
        assertTrue(result.contains(preloadedAcademicBodyHead));
    }

    @Test
    public void getAcademicBodyHeadsTestFail() throws DataRetrievalException {
        AcademicBodyHeadDAO instance = new AcademicBodyHeadDAO();
        ArrayList<AcademicBodyHead> result = instance.getAcademicBodyHeads();
        
        assertTrue(!result.contains(preloadedAcademicBodyHeadForAddAcademicBodyHeadTest));
    }

    @Test
    public void getSpecifiedAcademicBodyHeadsTest() throws DataRetrievalException{
        AcademicBodyHeadDAO instance = new AcademicBodyHeadDAO();
        ArrayList<AcademicBodyHead> result = instance.getSpecifiedAcademicBodyHeads("F");
        
        assertTrue(result.contains(preloadedAcademicBodyHead));
    }

    @Test
    public void getSpecifiedAcademicBodyHeadsTestFail() throws DataRetrievalException{
        AcademicBodyHeadDAO instance = new AcademicBodyHeadDAO();
        ArrayList<AcademicBodyHead> result = instance.getSpecifiedAcademicBodyHeads("F");
        
        assertTrue(!result.contains(preloadedAcademicBodyHeadForAddAcademicBodyHeadTest));
    }

    @Test
    public void modifyAcademicBodyHeadDataTest() throws DataInsertionException, DuplicatedPrimaryKeyException{
        AcademicBodyHeadDAO instance = new AcademicBodyHeadDAO();
        preloadedAcademicBodyHead.setName("Felipe Tobar");
        preloadedAcademicBodyHead.setFirstSurname("Guzmán");
        preloadedAcademicBodyHead.setSecondSurname("Toral");
        preloadedAcademicBodyHead.setEmailAddress("fetoguto901@gmail.com");
        
        int result = instance.modifyAcademicBodyHeadData(preloadedAcademicBodyHead);
        assertTrue(result > 0);
    }
}