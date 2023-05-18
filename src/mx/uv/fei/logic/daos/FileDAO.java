package mx.uv.fei.logic.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.logic.daosinterfaces.IFileDAO;
import mx.uv.fei.logic.domain.File;
import mx.uv.fei.logic.exceptions.DataInsertionException;

public class FileDAO implements IFileDAO {
    private DataBaseManager dataBaseManager;
    
    public FileDAO() {
        dataBaseManager = new DataBaseManager();
    }
    
    @Override
    public int addFile(String filePath) throws DataInsertionException {
        int result;
        String query = "insert into Archivos(ruta) values(?)";
        try {
            Connection connection = dataBaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, filePath);
            result = statement.executeUpdate();
        } catch (SQLException sql) {
            throw new DataInsertionException("No fue posible guardar la información del archivo. Por favor, intente de nuevo más tarde.");
        }
        return result;
    }

    @Override
    public int addFilesList() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    @Override
    public int updateFilePath(String filePath) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }    
}
