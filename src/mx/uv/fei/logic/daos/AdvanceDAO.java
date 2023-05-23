package mx.uv.fei.logic.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import mx.uv.fei.logic.daosinterfaces.IAdvanceDAO;
import mx.uv.fei.logic.domain.Advance;
import mx.uv.fei.logic.exceptions.DataInsertionException;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.dataaccess.DataBaseManager;

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
            Connection connection = dataBaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
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
                advance.setComments(rs.getString("comentario"));
                advance.setState(rs.getString("estado"));
                
               advancesList.add(advance);
            }
        } catch (SQLException sql) {
            throw new DataRetrievalException("Failed to retrieve advances information. Please try again later");
        }
        
        return advancesList;
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
    
}
