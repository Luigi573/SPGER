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

    @Override
    public int addKGAL(KGAL kgal) throws DataInsertionException {
        int result;
        String query = "insert into LGAC(descripcion) values(?)";
        try {
            dataBaseManager = new DataBaseManager();
            Connection connection = dataBaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, kgal.getDescription());
            result = statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataInsertionException("New Knowledge Generation and Application Line data could not be saved to the Database. Please try again later");
        }
        return result;
    }

    @Override
    public ArrayList<KGAL> getKGALList() throws DataRetrievalException {
        ArrayList<KGAL> kgalList = new ArrayList();
        
        String query = "select * from LGAC";
        try {
            dataBaseManager = new DataBaseManager();
            Connection connection = dataBaseManager.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()) {
                KGAL kgal = new KGAL();
                kgal.setKgalID(rs.getInt("IdLGAC"));
                kgal.setDescription(rs.getString("descripcion"));
                
                kgalList.add(kgal);
            }
        } catch (SQLException sql) {
            throw new DataRetrievalException("Failed to retrieve KGAL information. Please try again later");
        }
        
        return kgalList;
    }

    @Override
    public KGAL getKGALByID(int kgalID) throws DataRetrievalException {
        String query = "select * from LGAC where IdLGAC=?";
        try {
            dataBaseManager = new DataBaseManager();
            Connection connection = dataBaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, kgalID);
            ResultSet rs = statement.executeQuery();
            KGAL kgal = new KGAL();
            if(rs.next()) {
                kgal.setKgalID(rs.getInt("IdLGAC"));
                kgal.setDescription(rs.getString("descripcion"));
            } else {
                System.out.println("We couldn't find a KAGL that matches the given ID");
            }
            return kgal;            
        } catch (SQLException sql) {
            throw new DataRetrievalException("Failed to retrieve the specified KGAL. Please try again later");
        }
    }

    @Override
    public KGAL getKGALByDescription(String description) throws DataRetrievalException {
        String query = "select * from LGAC where descripcion=?";
        try {
            dataBaseManager = new DataBaseManager();
            Connection connection = dataBaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, description);
            ResultSet rs = statement.executeQuery();
            KGAL kgal = new KGAL();
            if(rs.next()) {
                kgal.setKgalID(rs.getInt("IdLGAC"));
                kgal.setDescription(rs.getString("descripcion"));
            } else {
                System.out.println("We couldn't find a KAGL that matches the given description");
            }
            return kgal;            
        } catch (SQLException sql) {
            throw new DataRetrievalException("Failed to retrieve the specified KGAL. Please try again later");
        }
    }

    @Override
    public int updateKGALDescription(int kgalID, String description) throws DataRetrievalException {
        int result;
        String query = "update LGAC set descripcion=? where IdLGAC=?";
        try {
            dataBaseManager = new DataBaseManager();
            Connection connection = dataBaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, description);
            statement.setInt(2, kgalID);
            result = statement.executeUpdate();
        } catch (SQLException sql) {
            System.out.println(sql.getMessage());
            throw new DataRetrievalException("New KGAL description could not be saved. Please try again later.");
        }
        return result;
    }
}
