package mx.uv.fei.logic.daosinterfaces;

import java.util.ArrayList;

import mx.uv.fei.logic.domain.ReceptionalWork;
import mx.uv.fei.logic.exceptions.DataInsertionException;
import mx.uv.fei.logic.exceptions.DataRetrievalException;


public interface IReceptionalWorkDAO {
    public int addReceptionalWork(ReceptionalWork receptionalWork) throws DataInsertionException;
    public ArrayList<ReceptionalWork> getReceptionalWorksList() throws DataRetrievalException;
    public ReceptionalWork getReceptionalWorkByID(int receptionalWorkID) throws DataRetrievalException;
}
