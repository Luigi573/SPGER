package mx.uv.fei.logic.daosinterfaces;

import java.util.ArrayList;

import mx.uv.fei.logic.domain.ScholarPeriod;
import mx.uv.fei.logic.exceptions.DataRetrievalException;

public interface IScholarPeriodDAO {
    public ArrayList<ScholarPeriod> getScholarPeriodsFromDatabase() throws DataRetrievalException;
    public ScholarPeriod getScholarPeriodFromDatabase(int idScholarPeriod) throws DataRetrievalException;
}
