package mx.uv.fei.logic.daosinterfaces;

import java.util.ArrayList;
import mx.uv.fei.logic.domain.Director;
import mx.uv.fei.logic.exceptions.DataRetrievalException;

public interface IDirectorDAO {
    public void addDirectorToDatabase(Director director);
    public void modifyDirectorDataFromDatabase(Director newDirectorData, Director originalDirectorData);
    public ArrayList<Director> getDirectorsFromDatabase();
    public ArrayList<Director> getSpecifiedDirectorsFromDatabase(String directorName);
    public Director getDirectorFromDatabase(int personalNumber);
    public ArrayList<Director> getDirectorList() throws DataRetrievalException;
}
