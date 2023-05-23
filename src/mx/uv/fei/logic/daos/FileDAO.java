package mx.uv.fei.logic.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.logic.daosinterfaces.IFileDAO;
import mx.uv.fei.logic.exceptions.DataInsertionException;

public class FileDAO implements IFileDAO {
    private DataBaseManager dataBaseManager;
    
    public FileDAO() {
        dataBaseManager = new DataBaseManager();
    }
    
    @Override
    public int addFile(String filePath) throws DataInsertionException {
        int generatedId = 0;
        String query = "insert into Archivos(ruta) values(?)";
        try {
            Connection connection = dataBaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, filePath);
            
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            
            if (resultSet.next()) {
                generatedId = resultSet.getInt(1);
            }
        } catch (SQLException sql) {
            throw new DataInsertionException("No fue posible guardar la información del archivo. Por favor, intente de nuevo más tarde.");
        }
        return generatedId;
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
