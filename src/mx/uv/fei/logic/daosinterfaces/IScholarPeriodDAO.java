package mx.uv.fei.logic.daosinterfaces;

import java.util.ArrayList;

import mx.uv.fei.logic.domain.ScholarPeriod;

public interface IScholarPeriodDAO {
    public ArrayList<ScholarPeriod> getScholarPeriodsFromDatabase();
    public ScholarPeriod getScholarPeriodFromDatabase(int idScholarPeriod);
}
