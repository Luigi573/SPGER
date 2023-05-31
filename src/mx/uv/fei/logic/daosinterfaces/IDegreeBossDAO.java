package mx.uv.fei.logic.daosinterfaces;

import java.util.ArrayList;

import mx.uv.fei.logic.domain.DegreeBoss;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.exceptions.DataInsertionException;

public interface IDegreeBossDAO {
    public int addDegreeBoss(DegreeBoss degreeBoss) throws DataInsertionException;
    public int modifyDegreeBossData(DegreeBoss degreeBoss) throws DataInsertionException;
    public ArrayList<DegreeBoss> getDegreeBosses() throws DataRetrievalException;
    public ArrayList<DegreeBoss> getSpecifiedDegreeBosses(String degreeBossName) throws DataRetrievalException;
    public DegreeBoss getDegreeBoss(int personalNumber) throws DataRetrievalException;
}
