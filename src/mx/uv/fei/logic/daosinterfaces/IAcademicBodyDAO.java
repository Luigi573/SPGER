package mx.uv.fei.logic.daosinterfaces;

import java.util.ArrayList;

import java.sql.SQLException;
import mx.uv.fei.logic.domain.AcademicBody;
import mx.uv.fei.logic.exceptions.DataRetrievalException;

public interface IAcademicBodyDAO {
    public int addAcademicBody(AcademicBody academicBody) throws SQLException;
    public ArrayList<AcademicBody> getAcademicBodyList() throws DataRetrievalException;
    public AcademicBody getAcademicBodyByID(int academicBodyID) throws DataRetrievalException;
}
