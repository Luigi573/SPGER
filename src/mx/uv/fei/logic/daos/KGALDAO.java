package mx.uv.fei.logic.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.logic.domain.KGAL;
import mx.uv.fei.logic.daosinterfaces.IKGALDAO;
import mx.uv.fei.logic.exceptions.DataInsertionException;
import mx.uv.fei.logic.exceptions.DataRetrievalException;

public class KGALDAO implements IKGALDAO {
    private final DataBaseManager dataBaseManager;
    
    public KGALDAO(){
        dataBaseManager = new DataBaseManager();
    }

    @Override
    public int addKGAL(KGAL kgal) throws DataInsertionException {
        int result = 0;
        String query = "INSERT INTO LGAC(descripción) VALUES(?)";
        
        try {
            PreparedStatement statement = dataBaseManager.getConnection().prepareStatement(query);
            statement.setString(1, kgal.getDescription());
            
            result = statement.executeUpdate();
        } catch (SQLException exception) {
            throw new DataInsertionException("La información de la nueva LGAC no pudo ser guardada en la Base de Datos. Por favor intente de nuevo más tarde.");
        }finally{
            dataBaseManager.closeConnection();
        }
        return result;
    }

    @Override
    public ArrayList<KGAL> getKGALList() throws DataRetrievalException {
        ArrayList<KGAL> kgalList = new ArrayList();
        String query = "SELECT * FROM LGAC";
        
        try {
            Statement statement = dataBaseManager.getConnection().createStatement();
            
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()) {
                KGAL kgal = new KGAL();
                kgal.setKgalID(rs.getInt("IdLGAC"));
                kgal.setDescription(rs.getString("descripción"));
                
                kgalList.add(kgal);
            }
        } catch (SQLException exception) {
            throw new DataRetrievalException("No fue posible recuperar la información de la LGAC. Por favor intente de nuevo más tarde.");
        }finally{
            dataBaseManager.closeConnection();
        }
        
        return kgalList;
    }

    @Override
    public KGAL getKGALByID(int kgalID) throws DataRetrievalException {
        String query = "SELECT * FROM LGAC WHERE IdLGAC = ?";
        KGAL kgal = new KGAL();
        
        try {
            PreparedStatement statement = dataBaseManager.getConnection().prepareStatement(query);
            statement.setInt(1, kgalID);
            
            ResultSet rs = statement.executeQuery();
            if(rs.next()) {
                kgal.setKgalID(rs.getInt("IdLGAC"));
                kgal.setDescription(rs.getString("descripción"));
            }
        } catch (SQLException exception) {
            throw new DataRetrievalException("No fue posible recuperar la información de la LGAC especificada. Por favor intente de nuevo más tarde.");
        }finally{
            dataBaseManager.closeConnection();
        }
        
        return kgal;
    }

    @Override
    public KGAL getKGALByDescription(String description) throws DataRetrievalException {
        String query = "SELECT * FROM LGAC WHERE descripción=?";
        
        try {
            PreparedStatement statement = dataBaseManager.getConnection().prepareStatement(query);
            statement.setString(1, description);
            KGAL kgal = new KGAL();
            
            ResultSet rs = statement.executeQuery();
            if(rs.next()) {
                kgal.setKgalID(rs.getInt("IdLGAC"));
                kgal.setDescription(rs.getString("descripción"));
            }
            
            return kgal;            
        } catch (SQLException exception) {           
            throw new DataRetrievalException("No fue posible recuperar la lista de LGAC. Por favor intente de nuevo más tarde.");
        }finally{
            dataBaseManager.closeConnection();
        }
    }

    @Override
    public ArrayList<KGAL> getKGALListByDescription(String description) throws DataRetrievalException {
        ArrayList<KGAL> kgalList = new ArrayList();
        String query = "SELECT * FROM LGAC WHERE descripción LIKE ?";
        
        try {
            PreparedStatement statement = dataBaseManager.getConnection().prepareStatement(query);
            statement.setString(1, "%" + description + "%");
            
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                KGAL kgal = new KGAL();
                kgal.setKgalID(rs.getInt("IdLGAC"));
                kgal.setDescription(rs.getString("descripción"));
                
                kgalList.add(kgal);
            }
            return kgalList;            
        } catch (SQLException exception) {
            throw new DataRetrievalException("No fue posible recuperar la LGAC especificada. Por favor intente de nuevo más tarde.");
        }finally{
            dataBaseManager.closeConnection();
        }
    }
    
    @Override
    public int updateKGALDescription(int kgalID, String description) throws DataRetrievalException {
        int result = 0;
        String query = "UPDATE LGAC SET descripción = ? WHERE IdLGAC = ?";
        
        try {
            PreparedStatement statement = dataBaseManager.getConnection().prepareStatement(query);
            statement.setString(1, description);
            statement.setInt(2, kgalID);
            
            result = statement.executeUpdate();
        } catch (SQLException exception) {
            throw new DataRetrievalException("La nueva descripción de la LGAC no pudo ser guardada. Por favor intente de nuevo más tarde.");
        }
        return result;
    }
}