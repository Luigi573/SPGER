package mx.uv.fei.logic.daos;

import java.sql.Connection;
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
    private DataBaseManager dataBaseManager;

    public KGALDAO () {
        dataBaseManager = new DataBaseManager();
    }
    
    @Override
    public int addKGAL(KGAL kgal) throws DataInsertionException {
        int result;
        String query = "insert into LGAC(descripción) values(?)";
        try {
            Connection connection = dataBaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, kgal.getDescription());
            result = statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataInsertionException("La información de la nueva LGAC no pudo ser guardada en la Base de Datos. Por favor intente de nuevo más tarde.");
        }
        return result;
    }

    @Override
    public ArrayList<KGAL> getKGALList() throws DataRetrievalException {
        ArrayList<KGAL> kgalList = new ArrayList();
        
        String query = "select * from LGAC";
        try {
            Connection connection = dataBaseManager.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()) {
                KGAL kgal = new KGAL();
                kgal.setKgalID(rs.getInt("IdLGAC"));
                kgal.setDescription(rs.getString("descripción"));
                
                kgalList.add(kgal);
            }
        } catch (SQLException sql) {
            throw new DataRetrievalException("No fue posible recuperar la información de la LGAC. Por favor intente de nuevo más tarde.");
        }
        
        return kgalList;
    }

    @Override
    public KGAL getKGALByID(int kgalID) throws DataRetrievalException {
        String query = "select * from LGAC where IdLGAC=?";
        try {
            Connection connection = dataBaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, kgalID);
            ResultSet rs = statement.executeQuery();
            KGAL kgal = new KGAL();
            if(rs.next()) {
                kgal.setKgalID(rs.getInt("IdLGAC"));
                kgal.setDescription(rs.getString("descripción"));
            } else {
                System.out.println("No encontramos ninguna LGAC que coincida con el ID proporcionado.");
            }
            return kgal;            
        } catch (SQLException sql) {
            throw new DataRetrievalException("No fue posible recuperar la información de la LGAC especificada. Por favor intente de nuevo más tarde.");
        }
    }

    @Override
    public KGAL getKGALByDescription(String description) throws DataRetrievalException {
        String query = "select * from LGAC where descripción=?";
        try {
            Connection connection = dataBaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, description);
            KGAL kgal = new KGAL();
            
            ResultSet rs = statement.executeQuery();
            if(rs.next()) {
                kgal.setKgalID(rs.getInt("IdLGAC"));
                kgal.setDescription(rs.getString("descripción"));
            } else {
                System.out.println("No encontramos ninguna LGAC que coincida con la descripción dada.");
            }
            return kgal;            
        } catch (SQLException sql) {           
            throw new DataRetrievalException("No fue posible recuperar la lista de LGAC. Por favor intente de nuevo más tarde.");
        }
    }

    @Override
    public ArrayList<KGAL> getKGALListByDescription(String description) throws DataRetrievalException {
        String query = "select * from LGAC where descripción like ?";
        ArrayList<KGAL> kgalList = new ArrayList();
        try {
            Connection connection = dataBaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, "%" + description + "%");
            
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                KGAL kgal = new KGAL();
                kgal.setKgalID(rs.getInt("IdLGAC"));
                kgal.setDescription(rs.getString("descripción"));
                
                kgalList.add(kgal);
            }
            return kgalList;            
        } catch (SQLException sql) {
            throw new DataRetrievalException("No fue posible recuperar la LGAC especificada. Por favor intente de nuevo más tarde.");
        }
    }
    
    @Override
    public int updateKGALDescription(int kgalID, String description) throws DataRetrievalException {
        int result;
        String query = "update LGAC set descripción=? where IdLGAC=?";
        try {
            Connection connection = dataBaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, description);
            statement.setInt(2, kgalID);
            result = statement.executeUpdate();
        } catch (SQLException sql) {
            throw new DataRetrievalException("La nueva descripción de la LGAC no pudo ser guardada. Por favor intente de nuevo más tarde.");
        }
        return result;
    }
}