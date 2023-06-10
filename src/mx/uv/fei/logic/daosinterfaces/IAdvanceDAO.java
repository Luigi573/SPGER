package mx.uv.fei.logic.daosinterfaces;

import java.util.ArrayList;

import mx.uv.fei.logic.domain.Advance;
import mx.uv.fei.logic.exceptions.DataInsertionException;
import mx.uv.fei.logic.exceptions.DataRetrievalException;

public interface IAdvanceDAO {
    public int addAdvance(Advance advance) throws DataInsertionException;
    public ArrayList<Advance> getAdvanceList(int activityId) throws DataRetrievalException;
    //public Advance getAdvanceByID(int advanceID) throws DataRetrievalException;
    public int setFeedback(Advance advance) throws DataInsertionException;
}
