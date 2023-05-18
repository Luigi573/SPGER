package mx.uv.fei.logic.daosinterfaces;

import java.util.ArrayList;

import mx.uv.fei.logic.domain.AcademicBodyHead;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.exceptions.DataWritingException;

public interface IAcademicBodyHeadDAO {
    public void addAcademicBodyHeadToDatabase(AcademicBodyHead academicBodyHead) throws DataWritingException;
    public void modifyAcademicBodyHeadDataFromDatabase(AcademicBodyHead newAcademicBodyHeadData, AcademicBodyHead originalAcademicBodyHeadData) throws DataWritingException;
    public ArrayList<AcademicBodyHead> getAcademicBodyHeadsFromDatabase() throws DataRetrievalException;
    public ArrayList<AcademicBodyHead> getSpecifiedAcademicBodyHeadsFromDatabase(String academicBodyHeadName) throws DataRetrievalException;
    public AcademicBodyHead getAcademicBodyHeadFromDatabase(int personalNumber) throws DataRetrievalException;
}
