package mx.uv.fei.logic.daosinterfaces;

import java.util.ArrayList;

import mx.uv.fei.logic.domain.AcademicBodyHead;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.exceptions.DataInsertionException;

public interface IAcademicBodyHeadDAO {
    public void addAcademicBodyHeadToDatabase(AcademicBodyHead academicBodyHead) throws DataInsertionException;
    public void modifyAcademicBodyHeadData(AcademicBodyHead newAcademicBodyHeadData, AcademicBodyHead originalAcademicBodyHeadData) throws DataInsertionException;
    public ArrayList<AcademicBodyHead> getAcademicBodyHeads() throws DataRetrievalException;
    public ArrayList<AcademicBodyHead> getSpecifiedAcademicBodyHeads(String academicBodyHeadName) throws DataRetrievalException;
    public AcademicBodyHead getAcademicBodyHead(int personalNumber) throws DataRetrievalException;
}
