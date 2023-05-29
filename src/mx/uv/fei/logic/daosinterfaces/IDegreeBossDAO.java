package mx.uv.fei.logic.daosinterfaces;

import java.util.ArrayList;

import mx.uv.fei.logic.domain.DegreeBoss;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.exceptions.DataInsertionException;

public interface IDegreeBossDAO {
    public void addDegreeBossToDatabase(DegreeBoss degreeBoss) throws DataInsertionException;
    public void modifyDegreeBossDataFromDatabase(DegreeBoss newDegreeBossData, DegreeBoss originalDegreeBossData) throws DataInsertionException;
    public ArrayList<DegreeBoss> getDegreeBossesFromDatabase() throws DataRetrievalException;
    public ArrayList<DegreeBoss> getSpecifiedDegreeBossesFromDatabase(String degreeBossName) throws DataRetrievalException;
    public DegreeBoss getDegreeBossFromDatabase(int personalNumber) throws DataRetrievalException;
}
