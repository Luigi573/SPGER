package mx.uv.fei.logic.daosinterfaces;

import java.util.ArrayList;

import mx.uv.fei.logic.domain.DegreeBoss;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.exceptions.DataWritingException;

public interface IDegreeBossDAO {
    public void addDegreeBossToDatabase(DegreeBoss degreeBoss) throws DataWritingException;
    public void modifyDegreeBossDataFromDatabase(DegreeBoss newDegreeBossData, DegreeBoss originalDegreeBossData) throws DataWritingException;
    public ArrayList<DegreeBoss> getDegreeBossesFromDatabase() throws DataRetrievalException;
    public ArrayList<DegreeBoss> getSpecifiedDegreeBossesFromDatabase(String degreeBossName) throws DataRetrievalException;
    public DegreeBoss getDegreeBossFromDatabase(int personalNumber) throws DataRetrievalException;
}
