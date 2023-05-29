package mx.uv.fei.logic.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import mx.uv.fei.logic.daosinterfaces.IAdvanceDAO;
import mx.uv.fei.logic.domain.Advance;
import mx.uv.fei.logic.exceptions.DataInsertionException;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.dataaccess.DataBaseManager;

public class AdvanceDAO implements IAdvanceDAO{
    private final DataBaseManager dataBaseManager;
    
    public AdvanceDAO(){
        dataBaseManager = new DataBaseManager();
    }
    
    @Override
    public int addAdvance(Advance advance) throws DataInsertionException {
        int result;
        String query = "INSERT INTO Avances(IdActividad, título, comentario) VALUES(?, ?, ?)";
        try {
            Connection connection = dataBaseManager.getConnection();
            
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(3, advance.getTitle());
            statement.setString(4, advance.getComment());
            
            result = statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataInsertionException("New Advance data could not be saved to the Database. Please try again later");
        }
        return result;
    }

    @Override
    public ArrayList<Advance> getAdvanceList(int activityId) throws DataRetrievalException {
        ArrayList<Advance> advanceList = new ArrayList();
        PreparedStatement statement;
        String query = "SELECT IdAvance, IdActividad, IdArchivo, título, fecha, comentario, retroalimentación FROM Avances WHERE IdActividad IN(?)";
        
        try {
            statement = dataBaseManager.getConnection().prepareStatement(query);
            
            statement.setInt(1, activityId);
            
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                Advance advance = new Advance();
                advance.setId(rs.getInt("IdAvance"));
                advance.setActivityId(rs.getInt("IdActividad"));
                advance.setTitle(rs.getString("título"));
                advance.setDate(rs.getDate("fecha"));
                advance.setComment(rs.getString("comentario"));
                advance.setFeedback(rs.getString("retroalimentación"));
                
               advanceList.add(advance);
            }
        } catch (SQLException sql) {
            throw new DataRetrievalException("Failed to retrieve advances information. Please try again later");
        }
        
        return advanceList;
    }
/*
    @Override
    public Advance getAdvanceByID(int advanceID) throws DataRetrievalException {
<<<<<<< HEAD
        ArrayList<Advance> advancesList = new ArrayList(getAdvanceList());
=======
        ArrayList<Advance> advancesList = new ArrayList<>(getAdvancesList());
>>>>>>> b76b93d15655b9f5dfd27c9fc867dc5e2c09b660
        Advance advanceByID = new Advance();
        int i = 0;
        boolean b = false;
        
        while(i < advancesList.size() && !b) {
            if(advancesList.get(i).getId() == advanceID) {
                advanceByID = advancesList.get(i);
                b = true;
            }
            i++;
        }
                
        if(!b){
            System.out.println("There is no Advance that matches the given ID.");
        }
        
        return advanceByID;
    }*/
    @Override
    public int setFeedback(Advance advance) throws DataInsertionException{
        int result = 0;
        PreparedStatement statement;
        String query = "UPDATE Avances SET retroalimentación = ? WHERE IdAvance IN(?)";
        
        try{
            statement = dataBaseManager.getConnection().prepareStatement(query);
            
            statement.setString(1, advance.getFeedback());
            statement.setInt(2, advance.getId());
            
            result = statement.executeUpdate();
        }catch(SQLException exception){
            throw new DataInsertionException("Error de conexión. Verifique su conexiión a internet e inténtelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }
        
        return result;
    }
}
