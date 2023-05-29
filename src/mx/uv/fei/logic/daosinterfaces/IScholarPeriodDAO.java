package mx.uv.fei.logic.daosinterfaces;

import java.util.ArrayList;

import mx.uv.fei.logic.domain.ScholarPeriod;
import mx.uv.fei.logic.exceptions.DataRetrievalException;

public interface IScholarPeriodDAO {
    public ArrayList<ScholarPeriod> getScholarPeriods() throws DataRetrievalException;
    public ScholarPeriod getScholarPeriod(int idScholarPeriod) throws DataRetrievalException;
}
