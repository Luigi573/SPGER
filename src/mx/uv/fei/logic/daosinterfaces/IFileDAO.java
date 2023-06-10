package mx.uv.fei.logic.daosinterfaces;

import mx.uv.fei.logic.exceptions.DataInsertionException;

public interface IFileDAO {
    public int addFile(String filePath) throws DataInsertionException;
}
