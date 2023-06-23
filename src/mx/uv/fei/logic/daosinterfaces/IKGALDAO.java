package mx.uv.fei.logic.daosinterfaces;

import java.util.ArrayList;

import mx.uv.fei.logic.domain.KGAL;
import mx.uv.fei.logic.exceptions.DataInsertionException;
import mx.uv.fei.logic.exceptions.DataRetrievalException;

public interface IKGALDAO {
    public int addKGAL(KGAL kgal) throws DataInsertionException;
    public ArrayList<KGAL> getKGALList() throws DataRetrievalException;
    public KGAL getKGALByID(int kgalID) throws DataRetrievalException;
    public KGAL getKGALByDescription(String description) throws DataRetrievalException;
    public ArrayList<KGAL> getKGALListByDescription(String description) throws DataRetrievalException;
    public int updateKGALDescription(int kgalID, String description) throws DataInsertionException;
}
