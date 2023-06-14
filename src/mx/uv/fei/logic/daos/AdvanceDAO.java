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
        String query = "insert into Avances(IdActividad, IdArchivo, título, comentario, estado) values(?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = dataBaseManager.getConnection().prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);

            if (advance.getActivityID() == 0) {
                throw new DataInsertionException("El avance no está asignado a una actividad.");
            } 

            statement.setInt(1, advance.getActivityID());
            if (advance.getFileID() != 0) {
                statement.setInt(2, advance.getFileID());
            } else {
                statement.setNull(2, Types.INTEGER);
            }
            statement.setString(3, advance.getTitle());
            statement.setString(4, advance.getComment());
            statement.setString(5, advance.getState());

            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();

            if(resultSet.next()) {
                generatedId = resultSet.getInt(1);
            }
        } catch (SQLException exception) {
            throw new DataInsertionException("No se pudo guardar la información del avance. Por favor intente de nuevo más tarde.");
        }
        return generatedId;
    }
    
    @Override
    public ArrayList<Advance> getAdvancesList() throws DataRetrievalException {
        ArrayList<Advance> advancesList = new ArrayList();
        
        String query = "select * from Avances";
        try {
            Connection connection = dataBaseManager.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()) {
                Advance advance = new Advance();
                advance.setAdvanceID(rs.getInt("IdAvances"));
                advance.setActivityID(rs.getInt("IdActividad"));
                advance.setFileID(rs.getInt("IdArchivo"));
                advance.setTitle(rs.getString("título"));
                advance.setComment(rs.getString("comentario"));
                advance.setState(rs.getString("estado"));
                
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
                advance.setComment(rs.getString("comentario"));
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
                
        if(!b){
            System.out.println("There is no Advance that matches the given ID.");
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