package mx.uv.fei.logic.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Types;
import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.logic.daosinterfaces.IAdvanceDAO;
import mx.uv.fei.logic.domain.Advance;
import mx.uv.fei.logic.exceptions.DataInsertionException;
import mx.uv.fei.logic.exceptions.DataRetrievalException;

public class AdvanceDAO implements IAdvanceDAO{
    private DataBaseManager dataBaseManager;
    
    public AdvanceDAO(){
        dataBaseManager = new DataBaseManager();
    }
    
    @Override
    public int addAdvance(Advance advance) throws DataInsertionException {
        int generatedId = 0;
        String query = "insert into Avances(IdActividad, IdArchivo, título, comentario, estado, fecha) values(?, ?, ?, ?, ?, NOW())";
        try {
            PreparedStatement statement = dataBaseManager.getConnection().prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setInt(1, advance.getActivityID());
            
            if (advance.getFileID() > 0) {
                statement.setInt(2, advance.getFileID());
                advance.setState("Entregado");
            } else {
                statement.setNull(2, Types.INTEGER);
                advance.setState("Entregado");
            }
            
            if (!advance.getTitle().equals("")) {
                statement.setString(3, advance.getTitle());
            } else {
                throw new DataInsertionException("El título del avance no puede estar vacío.");
            }
            statement.setString(4, advance.getComment());
            statement.setString(5, advance.getState());
            
            statement.executeUpdate();
            
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                generatedId = resultSet.getInt(1);
            }
        } catch (SQLException exception) {
            throw new DataInsertionException("New Advance data could not be saved to the Database. Please try again later");
        }
        return generatedId;
    }
    
    @Override
    public ArrayList<Advance> getAdvancesList() throws DataRetrievalException {
        ArrayList<Advance> advancesList = new ArrayList();
        
        String query = "select * from Avances";
        try {
            Statement statement = dataBaseManager.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()) {
                Advance advance = new Advance();
                advance.setAdvanceID(resultSet.getInt("IdAvances"));
                advance.setActivityID(resultSet.getInt("IdActividad"));
                advance.setFileID(resultSet.getInt("IdArchivo"));
                advance.setTitle(resultSet.getString("título"));
                advance.setComment(resultSet.getString("comentario"));
                advance.setState(resultSet.getString("estado"));
                
               advancesList.add(advance);
            }
        } catch (SQLException sql) {
            throw new DataRetrievalException("Failed to retrieve advances information. Please try again later");
        }
        
        return advancesList;
    }
    
    public ArrayList<Advance> getActivityAdvanceList(int activityId) throws DataRetrievalException {
        ArrayList<Advance> advanceList = new ArrayList<>();
        PreparedStatement statement;
        String query = "SELECT IdAvance, IdActividad, IdArchivo, título, fecha, comentario, retroalimentación, estado FROM Avances WHERE IdActividad IN(?)";
        
        try {
            statement = dataBaseManager.getConnection().prepareStatement(query);
            
            statement.setInt(1, activityId);
            
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                Advance advance = new Advance();
                advance.setAdvanceID(resultSet.getInt("IdAvance"));
                advance.setActivityID(resultSet.getInt("IdActividad"));
                advance.setTitle(resultSet.getString("título"));
                advance.setDate(resultSet.getDate("fecha"));
                advance.setComment(resultSet.getString("comentario"));
                advance.setFeedback(resultSet.getString("retroalimentación"));
                advance.setState(resultSet.getString("estado"));
                advance.setFileID(resultSet.getInt("IdArchivo"));
                
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
        String query = "UPDATE Avances SET retroalimentación = ?, estado = ? WHERE IdAvance IN(?)";
        
        try{
            statement = dataBaseManager.getConnection().prepareStatement(query);
            
            statement.setString(1, advance.getFeedback());
            statement.setString(2, "Retroalimentado");
            statement.setInt(3, advance.getAdvanceID());
            
            result = statement.executeUpdate();
        }catch(SQLException exception){
            throw new DataInsertionException("Error de conexión. Verifique su conexiión a  la base de datos e inténtelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }
        
        return result;
    }

    @Override
    public Advance getAdvanceByID(int advanceID) throws DataRetrievalException {
        ArrayList<Advance> advancesList = new ArrayList(getAdvancesList());
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

    @Override
    public int updateAdvanceInfo(int advanceToBeUpdatedID, Advance newAdvanceInfo) throws DataRetrievalException {
        int result;
        String query = "update Avances set IdArchivo = ?, título = ?, comentario = ? where IdAvance = ?";
        try {
            PreparedStatement statement = dataBaseManager.getConnection().prepareStatement(query);
            if (newAdvanceInfo.getFileID() != 0) {
                statement.setInt(1, newAdvanceInfo.getFileID());
            } else {
                statement.setNull(1, Types.INTEGER);
            }
            statement.setString(2, newAdvanceInfo.getTitle());
            statement.setString(3, newAdvanceInfo.getComment());
            statement.setInt(4, advanceToBeUpdatedID);
            result = statement.executeUpdate();
        } catch (SQLException sql) {
            throw new DataRetrievalException("La información del avance con ID " + advanceToBeUpdatedID + " no pudo ser modificada. Por favor, intente de nuevo más tarde.");
        }
        return result;
    }   
}