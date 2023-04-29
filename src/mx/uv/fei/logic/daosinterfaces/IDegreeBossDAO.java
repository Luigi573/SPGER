package mx.uv.fei.logic.daosinterfaces;

import java.util.ArrayList;

import mx.uv.fei.logic.domain.DegreeBoss;

public interface IDegreeBossDAO {
    public void addDegreeBossToDatabase(DegreeBoss degreeBoss);
    public void modifyDegreeBossDataFromDatabase(DegreeBoss newDegreeBossData, ArrayList<String> originalDegreeBossData);
    public ArrayList<DegreeBoss> getDegreeBossesFromDatabase();
    public ArrayList<DegreeBoss> getSpecifiedDegreeBossesFromDatabase(String degreeBossName);
    public DegreeBoss getDegreeBossFromDatabase(String degreeBossName);
}
