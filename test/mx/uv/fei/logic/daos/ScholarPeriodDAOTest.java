package mx.uv.fei.logic.daos;

import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.logic.domain.ScholarPeriod;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import static org.junit.Assert.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.junit.Test;

public class ScholarPeriodDAOTest{
    private static DataBaseManager dataBaseManager;
    private static ScholarPeriod preloadedScholarPeriod;
    private static ScholarPeriod failedScholarPeriod;

    @BeforeClass
    public static void setUpClass() {
        dataBaseManager = new DataBaseManager();
        
        try{
            //Adding a Scholar Period
            preloadedScholarPeriod = new ScholarPeriod();
            preloadedScholarPeriod.setStartDate("2023-02-07");
            preloadedScholarPeriod.setEndDate("2023-06-02");
           
            String scholarPeriodQuery = "INSERT INTO PeriodosEscolares(fechaInicio, fechaFin) VALUES (?, ?)";
            PreparedStatement scholarPeriodStatement = dataBaseManager.getConnection().prepareStatement(scholarPeriodQuery, Statement.RETURN_GENERATED_KEYS);
            scholarPeriodStatement.setString(1, preloadedScholarPeriod.getStartDate());
            scholarPeriodStatement.setString(2, preloadedScholarPeriod.getEndDate());
            scholarPeriodStatement.executeUpdate();
            
            ResultSet generatedScholarPeriodKeys = scholarPeriodStatement.getGeneratedKeys();
            if(generatedScholarPeriodKeys.next()){
                preloadedScholarPeriod.setIdScholarPeriod(generatedScholarPeriodKeys.getInt(1));
            }
            generatedScholarPeriodKeys.close();
            scholarPeriodStatement.close();

            //Set data to a failed Scholar Period
            failedScholarPeriod = new ScholarPeriod();
            failedScholarPeriod.setStartDate("2021-02-07");
            failedScholarPeriod.setEndDate("2021-06-02");
        }catch(SQLException exception){
            fail("Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }
    
    @AfterClass
    public static void tearDownClass(){
        PreparedStatement statement;
        String queryToDeleteScholarPeriod = "DELETE FROM PeriodosEscolares WHERE IdPeriodoEscolar = ?";
        
        try{
            statement = dataBaseManager.getConnection().prepareStatement(queryToDeleteScholarPeriod);
            statement.setInt(1, preloadedScholarPeriod.getIdScholarPeriod());
            statement.executeUpdate();
            statement.close();
        }catch(SQLException exception){
            fail("Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }

    @Test
    public void getScholarPeriodTest(){
        try {
            ScholarPeriodDAO instance = new ScholarPeriodDAO();
            ScholarPeriod result = instance.getScholarPeriod(preloadedScholarPeriod.getIdScholarPeriod());
            assertTrue(result.equals(preloadedScholarPeriod));
        }catch(DataRetrievalException exception){
            fail("Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }

    @Test
    public void getScholarPeriodTestFail(){
        try {
            ScholarPeriodDAO instance = new ScholarPeriodDAO();
            ScholarPeriod result = instance.getScholarPeriod(preloadedScholarPeriod.getIdScholarPeriod());
            assertTrue(!result.equals(failedScholarPeriod));
        }catch(DataRetrievalException exception){
            fail("Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }

    @Test
    public void getScholarPeriodsTest(){
        try {
            ScholarPeriodDAO instance = new ScholarPeriodDAO();
            ArrayList<ScholarPeriod> result = instance.getScholarPeriods();
            assertTrue(result.contains(preloadedScholarPeriod));
        }catch(DataRetrievalException exception){
            fail("Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }

    @Test
    public void getScholarPeriodsTestFail(){
        try {
            ScholarPeriodDAO instance = new ScholarPeriodDAO();
            ArrayList<ScholarPeriod> result = instance.getScholarPeriods();

            assertTrue(!result.contains(failedScholarPeriod));
        }catch(DataRetrievalException exception){
            fail("Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }
}
