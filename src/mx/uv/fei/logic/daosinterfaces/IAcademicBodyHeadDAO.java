package mx.uv.fei.logic.daosinterfaces;

import java.util.ArrayList;

import mx.uv.fei.logic.domain.AcademicBodyHead;
import mx.uv.fei.logic.exceptions.DataInsertionException;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.exceptions.DuplicatedPrimaryKeyException;

public interface IAcademicBodyHeadDAO {
    public int addAcademicBodyHead(AcademicBodyHead academicBodyHead) throws DataInsertionException, DuplicatedPrimaryKeyException;
    public int modifyAcademicBodyHeadData(AcademicBodyHead academicBodyHead) throws DataInsertionException, DuplicatedPrimaryKeyException;
    public ArrayList<AcademicBodyHead> getAcademicBodyHeads() throws DataRetrievalException;
    public ArrayList<AcademicBodyHead> getSpecifiedAcademicBodyHeads(String academicBodyHeadName) throws DataRetrievalException;
    public AcademicBodyHead getAcademicBodyHead(int personalNumber) throws DataRetrievalException;
}
