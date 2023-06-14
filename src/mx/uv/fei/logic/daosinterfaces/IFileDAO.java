package mx.uv.fei.logic.daosinterfaces;

import java.util.ArrayList;
import mx.uv.fei.logic.domain.File;
import mx.uv.fei.logic.exceptions.DataInsertionException;
import mx.uv.fei.logic.exceptions.DataRetrievalException;

public interface IFileDAO {
    public int addFile(String filePath) throws DataInsertionException;
    public int addActivityFile(int fileId, int activityID) throws DataInsertionException;
    public File getFileByID(int fileID) throws DataRetrievalException;
    public ArrayList<Integer> getFilesByActivity(int activityID) throws DataRetrievalException;
}
