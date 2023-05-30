package mx.uv.fei.logic.daosinterfaces;

import java.util.ArrayList;
import mx.uv.fei.logic.domain.Director;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.exceptions.DataInsertionException;

public interface IDirectorDAO {
    public void addDirector (Director director) throws DataInsertionException;
    public void modifyDirectorData(Director newDirectorData, Director originalDirectorData) throws DataInsertionException;
    public ArrayList<Director> getDirectors() throws DataRetrievalException;
    public ArrayList<Director> getSpecifiedDirectors(String directorName) throws DataRetrievalException;
    public Director getDirector(int staffNumber) throws DataRetrievalException;
    public ArrayList<Director> getDirectorList() throws DataRetrievalException;
}
