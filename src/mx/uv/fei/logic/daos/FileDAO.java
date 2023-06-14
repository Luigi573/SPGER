package mx.uv.fei.logic.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.logic.daosinterfaces.IFileDAO;
import mx.uv.fei.logic.domain.File;
import mx.uv.fei.logic.exceptions.DataInsertionException;
import mx.uv.fei.logic.exceptions.DataRetrievalException;

public class FileDAO implements IFileDAO {
    private DataBaseManager dataBaseManager;
    
    public FileDAO() {
        dataBaseManager = new DataBaseManager();
    }
    
    @Override
    public int addFile(String filePath) throws DataInsertionException {
        int generatedId = 0;
        String query = "insert into Archivos(ruta) values(?)";
        if (filePath == "") {
            throw new DataInsertionException("Ruta de Archivo inválida.");
        } else {
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
        }
        return generatedId;
    }
    
    @Override
    public int addActivityFile(int fileId, int activityId) throws DataInsertionException {
        int generatedId = 0;
        String query = "insert into ArchivosActividades(IdArchivo, IdActividad) values(?, ?)";
        
        if (activityId > 0 && fileId > 0) {
            try {
                Connection connection = dataBaseManager.getConnection();
                PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
                statement.setInt(1, fileId);
                statement.setInt(2, activityId);

                statement.executeUpdate();
                ResultSet resultSet = statement.getGeneratedKeys();

                if (resultSet.next()) {
                    generatedId = resultSet.getInt(1);
                }
            } catch (SQLException sql) {
                throw new DataInsertionException("No fue posible guardar la información del archivo. Por favor, intente de nuevo más tarde.");
            }
        }
        return generatedId;
    }
    
    @Override
    public File getFileByID(int fileID) throws DataRetrievalException {
        String query = "select * from Archivos where IdArchivo = ?";
        File file = new File();
        try {
            Connection connection = dataBaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, fileID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                file.setFileID(resultSet.getInt("IdArchivo"));
                file.setPath(resultSet.getString("ruta"));
            } else {
                throw new DataRetrievalException("No se encontró el archivo en cuestión.");
            }
        } catch (SQLException sql) {
            throw new DataRetrievalException("No fue posible recuperar la información del archivo. Por favor intente de nuevo más tarde.");
        }       
        return file;
    }

    @Override
    public ArrayList<Integer> getFilesByActivity(int activityID) throws DataRetrievalException {
        String query = "select * from ArchivosActividades where IdActividad = ?";
        ArrayList<Integer> fileIdList = new ArrayList();
        
        try {
            PreparedStatement statement = dataBaseManager.getConnection().prepareStatement(query);
            statement.setInt(1, activityID);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                fileIdList.add(resultSet.getInt("IdArchivo"));
            }
        } catch (SQLException exception){
            throw new DataRetrievalException("No fue posible recuperar los archivos. Por favor intente de nuevo más tarde.");
        }
        
        return fileIdList;
    }

    
}
