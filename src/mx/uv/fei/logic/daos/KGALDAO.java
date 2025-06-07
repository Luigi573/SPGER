package mx.uv.fei.logic.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.logic.daosinterfaces.IKGALDAO;
import mx.uv.fei.logic.domain.KGAL;
import mx.uv.fei.logic.exceptions.DataInsertionException;
import mx.uv.fei.logic.exceptions.DataRetrievalException;

public class KGALDAO implements IKGALDAO {
    private final DataBaseManager dataBaseManager;

    public KGALDAO () {
        dataBaseManager = new DataBaseManager();
    }
    
    @Override
    public int addKGAL(KGAL kgal) throws DataInsertionException {
        int generatedId = 0;
        String query = "insert into KGAL(description) values(?)";
        
        if (!kgal.getDescription().equals("")) {
            try {
                PreparedStatement statement = dataBaseManager.getConnection().prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
                statement.setString(1, kgal.getDescription());
                statement.executeUpdate();

                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    generatedId = resultSet.getInt(1);
                }
            } catch (SQLException e) {
                throw new DataInsertionException("La información de la nueva LGAC no pudo ser guardada en la Base de Datos. Por favor intente de nuevo más tarde.");
            }
        } else {
            throw new DataInsertionException("La descripción de la LGAC está vacía. Por favor complete la información.");
        }
        return generatedId;
    }

    @Override
    public ArrayList<KGAL> getKGALList() throws DataRetrievalException {
        ArrayList<KGAL> kgalList = new ArrayList<>();
        String query = "SELECT * FROM KGAL";
        try {
            Statement statement = dataBaseManager.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()) {
                KGAL kgal = new KGAL();
                kgal.setKgalId(resultSet.getInt("kgalId"));
                kgal.setDescription(resultSet.getString("description"));
                
                kgalList.add(kgal);
            }
        } catch (SQLException sql) {
            throw new DataRetrievalException("No fue posible recuperar la información de la LGAC. Por favor intente de nuevo más tarde.");
        }
        
        return kgalList;
    }

    @Override
    public KGAL getKGALByID(int kgalId) throws DataRetrievalException {
        String query = "SELECT * FROM KGAL where kgalId = ?";
        try {
            PreparedStatement statement = dataBaseManager.getConnection().prepareStatement(query);
            statement.setInt(1, kgalId);
            ResultSet resultSet = statement.executeQuery();
            KGAL kgal = new KGAL();
            if(resultSet.next()) {
                kgal.setKgalId(resultSet.getInt("kgalId"));
                kgal.setDescription(resultSet.getString("description"));
            }
            return kgal;            
        } catch (SQLException sql) {
            throw new DataRetrievalException("No fue posible recuperar la información de la LGAC especificada. Por favor intente de nuevo más tarde.");
        }
    }

    @Override
    public KGAL getKGALByDescription(String description) throws DataRetrievalException { //Not Used
        String query = "SELECT * FROM KGAL where description = ?";
        try {
            PreparedStatement statement = dataBaseManager.getConnection().prepareStatement(query);
            statement.setString(1, description);
            KGAL kgal = new KGAL();
            
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                kgal.setKgalId(resultSet.getInt("kgalId"));
                kgal.setDescription(resultSet.getString("description"));
            }
            return kgal;            
        } catch (SQLException sql) {           
            throw new DataRetrievalException("No fue posible recuperar la lista de LGAC. Por favor intente de nuevo más tarde.");
        }
    }

    @Override
    public ArrayList<KGAL> getKGALListByDescription(String description) throws DataRetrievalException {
        String query = "SELECT * FROM KGAL where description like ?";
        ArrayList<KGAL> kgalList = new ArrayList<>();
        
        try {
            PreparedStatement statement = dataBaseManager.getConnection().prepareStatement(query);
            statement.setString(1, "%" + description + "%");
            
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                KGAL kgal = new KGAL();
                kgal.setKgalId(resultSet.getInt("kgalId"));
                kgal.setDescription(resultSet.getString("description"));
                
                kgalList.add(kgal);
            }
            return kgalList;            
        } catch (SQLException sql) {
            throw new DataRetrievalException("No fue posible recuperar la LGAC especificada. Por favor intente de nuevo más tarde.");
        }
    }
    
    @Override
    public int updateKGALDescription(int kgalId, String description) throws DataInsertionException {
        int result = 0;
        String query = "update KGAL set description = ? where kgalId = ?";
        if (kgalId != 0) {
            try {
                PreparedStatement statement = dataBaseManager.getConnection().prepareStatement(query);
                statement.setString(1, description);
                statement.setInt(2, kgalId);
                result = statement.executeUpdate();
            } catch (SQLException sql) {
                throw new DataInsertionException("La nueva descripción de la LGAC no pudo ser guardada. Por favor intente de nuevo más tarde.");
            }
        } else {
            throw new DataInsertionException("La ID de la LGAC es incorrecta.");
        }
        return result;
    }
    
    public boolean isValidDescription(KGAL kgal){
        return kgal.getDescription().length() < 100;
    }
    
    public boolean isBlank(KGAL kgal){
        return kgal.getDescription().isBlank();
    }
}