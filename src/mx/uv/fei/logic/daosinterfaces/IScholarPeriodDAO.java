package mx.uv.fei.logic.daosinterfaces;

import java.util.ArrayList;

import mx.uv.fei.logic.domain.ScholarPeriod;
import mx.uv.fei.logic.exceptions.DataInsertionException;
import mx.uv.fei.logic.exceptions.DataRetrievalException;

public interface IScholarPeriodDAO {
    public int addScholarPeriod(ScholarPeriod scholarPeriod) throws DataInsertionException;
    public int modifyScholarPeriod(ScholarPeriod scholarPeriod) throws DataInsertionException;
    public ArrayList<ScholarPeriod> getScholarPeriods() throws DataRetrievalException;
    public ArrayList<ScholarPeriod> getSpecifiedScholarPeriodsByStartDate(String startDate) throws DataRetrievalException;
    public ScholarPeriod getScholarPeriod(int scholarPeriodId) throws DataRetrievalException;
    public ScholarPeriod getScholarPeriodByObject(ScholarPeriod scholarPeriod) throws DataRetrievalException;
}
