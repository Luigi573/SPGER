package mx.uv.fei.logic.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.sql.SQLException;
import java.util.ArrayList;

import mx.uv.fei.logic.domain.Advance;
import mx.uv.fei.logic.exceptions.DataInsertionException;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.dataaccess.DataBaseManager;

public class AdvanceDAO{
    private final DataBaseManager dataBaseManager;
    
    public AdvanceDAO(){
        dataBaseManager = new DataBaseManager();
    }
    
    public int addAdvance(Advance advance) throws DataInsertionException {
        int generatedId = 0;
        String query = "INSERT INTO Avances(IdActividad, IdArchivo, título, comentario, estado, fecha) VALUES(?, ?, ?, ?, ?, NOW())";
        try {
            PreparedStatement statement = dataBaseManager.getConnection().prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            
            statement.setInt(1, advance.getActivityID());
            statement.setInt(2, advance.getFileID());
            statement.setString(3, advance.getTitle());
            statement.setString(4, advance.getComments());
            statement.setString(5, advance.getState());
            
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            
            if(resultSet.next()) {
                generatedId = resultSet.getInt(1);
            }
        } catch (SQLException exception) {
            throw new DataInsertionException("New Advance data could not be saved to the Database. Please try again later");
        }finally{
            dataBaseManager.closeConnection();
        }
        return generatedId;
    }
    
    public ArrayList<Advance> getAdvanceList() throws DataRetrievalException {
        ArrayList<Advance> advancesList = new ArrayList();
        
        String query = "SELECT * FROM Avances";
        try {
            PreparedStatement statement = dataBaseManager.getConnection().prepareStatement(query);
            
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                Advance advance = new Advance();
                advance.setAdvanceID(rs.getInt("IdAvances"));
                advance.setActivityID(rs.getInt("IdActividad"));
                advance.setFileID(rs.getInt("IdArchivo"));
                advance.setTitle(rs.getString("título"));
                advance.setComments(rs.getString("comentario"));
                advance.setState(rs.getString("estado"));
                
                advancesList.add(advance);
            }
        } catch (SQLException exception) {
            throw new DataRetrievalException("Error de conexión con la base de datos");
        }finally{
            dataBaseManager.closeConnection();
        }
        
        return advancesList;
    }
    
    public ArrayList<Advance> getActivityAdvanceList(int activityId) throws DataRetrievalException {
        ArrayList<Advance> advanceList = new ArrayList();
        PreparedStatement statement;
        String query = "SELECT IdAvance, IdActividad, IdArchivo, título, fecha, comentario, retroalimentación FROM Avances WHERE IdActividad IN(?)";
        
        try {
            statement = dataBaseManager.getConnection().prepareStatement(query);
            
            statement.setInt(1, activityId);
            
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                Advance advance = new Advance();
                advance.setAdvanceID(rs.getInt("IdAvance"));
                advance.setActivityID(rs.getInt("IdActividad"));
                advance.setTitle(rs.getString("título"));
                advance.setDate(rs.getDate("fecha"));
                advance.setComments(rs.getString("comentario"));
                advance.setFeedback(rs.getString("retroalimentación"));
                
               advanceList.add(advance);
            }
        } catch (SQLException exception) {
            throw new DataRetrievalException("Failed to retrieve advances information. Please try again later");
        }
        
        return advanceList;
    }
    
    public int setFeedback(Advance advance) throws DataInsertionException{
        int result = 0;
        PreparedStatement statement;
        String query = "UPDATE Avances SET retroalimentación = ? WHERE IdAvance IN(?)";
        
        try{
            statement = dataBaseManager.getConnection().prepareStatement(query);
            
            statement.setString(1, advance.getFeedback());
            statement.setInt(2, advance.getAdvanceID());
            
            result = statement.executeUpdate();
        }catch(SQLException exception){
            throw new DataInsertionException("Error de conexión. Verifique su conexiión a  la base de datos e inténtelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }
        
        return result;
    }

    public Advance getAdvanceByID(int advanceID) throws DataRetrievalException {
        ArrayList<Advance> advancesList = new ArrayList(getAdvanceList());
        Advance advanceByID = new Advance();
        int i = 0;
        boolean b = false;
        
        while(i < advancesList.size() && !b) {
            if(advancesList.get(i).getAdvanceID() == advanceID) {
                advanceByID = advancesList.get(i);
                b = true;
            }
            i++;
        }
                
        return advanceByID;
    }

    public int updateAdvanceInfo(int advanceToBeUpdatedID, Advance newAdvanceInfo) throws DataRetrievalException {
        int result;
        String query = "UPDATE Avances SET IdArchivo = ?, título = ?, comentario = ? WHERE IdAvances = ?";
        try {
            PreparedStatement statement = dataBaseManager.getConnection().prepareStatement(query);
            
            if (newAdvanceInfo.getFileID() != 0) {
                statement.setInt(1, newAdvanceInfo.getFileID());
            } else {
                statement.setNull(1, Types.INTEGER);
            }
            
            statement.setString(2, newAdvanceInfo.getTitle());
            statement.setString(3, newAdvanceInfo.getComments());
            statement.setInt(4, advanceToBeUpdatedID);
            
            result = statement.executeUpdate();
        } catch (SQLException exception) {
            throw new DataRetrievalException("La información del avance con ID " + advanceToBeUpdatedID + " no pudo ser modificada. Por favor, intente de nuevo más tarde.");
        }finally{
            dataBaseManager.closeConnection();
        }
        
        return result;
    }
}
