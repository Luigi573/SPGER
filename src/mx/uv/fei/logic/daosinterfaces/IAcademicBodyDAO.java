package mx.uv.fei.logic.daosinterfaces;

import java.util.ArrayList;

import mx.uv.fei.logic.domain.AcademicBody;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.exceptions.DataInsertionException;

public interface IAcademicBodyDAO {
    public int addAcademicBody(AcademicBody academicBody) throws DataInsertionException;
    public ArrayList<AcademicBody> getAcademicBodiesList() throws DataRetrievalException;
    public AcademicBody getAcademicBodyByID(int academicBodyID) throws DataRetrievalException;
}
