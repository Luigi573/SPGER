package mx.uv.fei.logic.daosinterfaces;

import java.util.ArrayList;
import mx.uv.fei.logic.domain.Director;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.exceptions.DataInsertionException;

public interface IDirectorDAO {
    public void addDirectorToDatabase(Director director) throws DataInsertionException;
    public void modifyDirectorDataFromDatabase(Director newDirectorData, Director originalDirectorData) throws DataInsertionException;
    public ArrayList<Director> getDirectorsFromDatabase() throws DataRetrievalException;
    public ArrayList<Director> getSpecifiedDirectorsFromDatabase(String directorName) throws DataRetrievalException;
    public Director getDirectorFromDatabase(int personalNumber) throws DataRetrievalException;
    public ArrayList<Director> getDirectorList() throws DataRetrievalException;
}
