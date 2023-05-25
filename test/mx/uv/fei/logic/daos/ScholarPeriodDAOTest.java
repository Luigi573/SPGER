package mx.uv.fei.logic.daos;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import mx.uv.fei.logic.domain.ScholarPeriod;
import mx.uv.fei.logic.exceptions.DataRetrievalException;

public class ScholarPeriodDAOTest{
    @Test
    void getScholarPeriodFromDatabaseTest(){
        try {    
            ScholarPeriodDAO scholarPeriodDAO = new ScholarPeriodDAO();
            ScholarPeriod expectedScholarPeriod = new ScholarPeriod();
            expectedScholarPeriod.setStartDate("2018-02-07");
            expectedScholarPeriod.setEndDate("2018-06-02");

            ScholarPeriod actualScholarPeriod = scholarPeriodDAO.getScholarPeriodFromDatabase(1);
            Assertions.assertTrue(expectedScholarPeriod.equals(actualScholarPeriod));
        } catch (DataRetrievalException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getScholarPeriodsFromDatabaseTest(){
        try {
            ScholarPeriodDAO scholarPeriodDAO = new ScholarPeriodDAO();
            ScholarPeriod actualScholarPeriod = scholarPeriodDAO.getScholarPeriodsFromDatabase().get(0);

            ScholarPeriod expectedScholarPeriod = new ScholarPeriod();
            expectedScholarPeriod.setStartDate("2018-02-07");
            expectedScholarPeriod.setEndDate("2018-06-02");

            Assertions.assertTrue(expectedScholarPeriod.equals(actualScholarPeriod));
        } catch (DataRetrievalException e) {
            e.printStackTrace();
        }
    }
}
