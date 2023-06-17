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
        if (filePath.equals("")) {
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
    public File getFileByID(int fileID) throws DataRetrievalException {
        String query = "select * from Archivos where IdArchivo = ?";
        File file = new File();
        if (fileID > 0) {
            try {
                Connection connection = dataBaseManager.getConnection();
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, fileID);
                ResultSet resultSet = statement.executeQuery();

                if(resultSet.next()) {
                    file.setFileID(resultSet.getInt("IdArchivo"));
                    file.setFilePath(resultSet.getString("ruta"));
                }
            } catch (SQLException sql) {
                throw new DataRetrievalException("No fue posible recuperar la información del archivo. Por favor intente de nuevo más tarde.");
            }
        } else {
            throw new DataRetrievalException("El ID de la actividad es inválido, debe ser mayor a 0.");
        }
        return file;
    }
    
    public int addActivityFile(String filePath, int activityId) throws DataInsertionException {
        int generatedFileId;
        int generatedActivityFileId = 0;
        
        String activityFileQuery = "insert into ArchivosActividad(IdArchivo, IdActividad) values(?, ?)";    
        
        if (activityId > 0 && !filePath.equals("")) {
            try {
                generatedFileId = addFile(filePath);
                
                if (generatedFileId > 0) {
                    PreparedStatement statement = dataBaseManager.getConnection().prepareStatement(activityFileQuery, PreparedStatement.RETURN_GENERATED_KEYS);
                    statement.setInt(1, generatedFileId);
                    statement.setInt(2, activityId);

                    statement.executeUpdate();
                    ResultSet resultSet = statement.getGeneratedKeys();

                    if (resultSet.next()) {
                        generatedActivityFileId = resultSet.getInt(1);
                    }
                } else {
                    throw new DataInsertionException("Hubo un problema al guardar el archivo " + filePath + " .");
                }
            } catch (SQLException exception) {
                throw new DataInsertionException("No fue posible guardar la información del archivo. Por favor, intente de nuevo más tarde.");
            }
        } else {
                throw new DataInsertionException("Se recibió información de archivo o de actividad incorrecta.");
        }
        return generatedActivityFileId;
    }
    
    public ArrayList<File> getFilesByActivity(int activityID) throws DataRetrievalException {
        String query = "SELECT archivo.IdArchivo, archivo.ruta FROM ArchivosActividad AS actividad JOIN Archivos AS archivo ON actividad.IdArchivo = archivo.IdArchivo WHERE actividad.IdActividad = ?";
        ArrayList<File> activityFilesList = new ArrayList();
          
        if (activityID > 0) {
            try {
                PreparedStatement statement = dataBaseManager.getConnection().prepareStatement(query);
                statement.setInt(1, activityID);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    File file = new File();
                    file.setFileID(resultSet.getInt("IdArchivo"));
                    file.setFilePath(resultSet.getString("ruta"));

                    activityFilesList.add(file);
                }
            } catch (SQLException exception){
                throw new DataRetrievalException("No fue posible recuperar los archivos. Por favor intente de nuevo más tarde.");
            }
        } else {
            throw new DataRetrievalException("El ID de la actividad es menor a 1.");
        }
        return activityFilesList;
    }
}
