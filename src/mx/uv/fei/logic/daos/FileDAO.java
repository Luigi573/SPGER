package mx.uv.fei.logic.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.logic.daosinterfaces.IFileDAO;
import mx.uv.fei.logic.domain.File;
import mx.uv.fei.logic.exceptions.DataInsertionException;
import mx.uv.fei.logic.exceptions.DataRetrievalException;

public class FileDAO implements IFileDAO {
    private final DataBaseManager dataBaseManager;
    
    public FileDAO() {
        dataBaseManager = new DataBaseManager();
    }
    
    @Override
    public int addFile(String filePath) throws DataInsertionException {
        int generatedId = 0;
        String query = "INSERT INTO Archivos(ruta) VALUES(?)";
        try {
            PreparedStatement statement = dataBaseManager.getConnection().prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, filePath);
            
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            
            if (resultSet.next()) {
                generatedId = resultSet.getInt(1);
            }
        }catch(SQLException exception){
            throw new DataInsertionException("No fue posible guardar la información del archivo. Por favor, intente de nuevo más tarde.");
        }finally{
            dataBaseManager.closeConnection();
        }
        return generatedId;
    }
    
    public File getFileByID(int fileID) throws DataRetrievalException {
        String query = "SELECT * FROM Archivos WHERE IdArchivo = ?";
        File file = new File();
        
        try {
            PreparedStatement statement = dataBaseManager.getConnection().prepareStatement(query);
            statement.setInt(1, fileID);
            ResultSet rs = statement.executeQuery();

            if(rs.next()) {
                file.setFileId(rs.getInt("IdArchivo"));
                file.setPath(rs.getString("ruta"));
            }
        }catch(SQLException exception){
            throw new DataRetrievalException("No fue posible recuperar la información del archivo. Por favor intente de nuevo más tarde.");
        }finally{
            dataBaseManager.closeConnection();
        }
        return file;
    }
}
