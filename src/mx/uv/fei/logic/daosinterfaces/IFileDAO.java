package mx.uv.fei.logic.daosinterfaces;

import mx.uv.fei.logic.domain.File;
import mx.uv.fei.logic.exceptions.DataInsertionException;
import mx.uv.fei.logic.exceptions.DataRetrievalException;

public interface IFileDAO {
    public int addFile(String filePath) throws DataInsertionException;
    public int addFilesList();
    public int updateFilePath(String filePath) throws DataInsertionException;
    public File getFileByID(int fileID) throws DataRetrievalException;
}
