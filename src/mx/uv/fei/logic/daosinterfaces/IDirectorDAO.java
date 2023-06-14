package mx.uv.fei.logic.daosinterfaces;

import java.util.ArrayList;

import mx.uv.fei.logic.domain.Director;
import mx.uv.fei.logic.exceptions.DataInsertionException;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.exceptions.DuplicatedPrimaryKeyException;

public interface IDirectorDAO {
    public int addDirector(Director director) throws DataInsertionException, DuplicatedPrimaryKeyException;
    public int modifyDirectorData(Director director) throws DataInsertionException, DuplicatedPrimaryKeyException;
    public ArrayList<Director> getDirectors() throws DataRetrievalException;
    public ArrayList<Director> getSpecifiedDirectors(String directorName) throws DataRetrievalException;
    public Director getDirector(int personalNumber) throws DataRetrievalException;
    public ArrayList<Director> getDirectorList() throws DataRetrievalException;
}
