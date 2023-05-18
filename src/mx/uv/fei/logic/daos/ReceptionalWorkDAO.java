package mx.uv.fei.logic.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import mx.uv.fei.logic.daosinterfaces.IReceptionalWorkDAO;
import mx.uv.fei.logic.exceptions.DataInsertionException;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.logic.domain.ReceptionalWork;

public class ReceptionalWorkDAO implements IReceptionalWorkDAO{
    private DataBaseManager dataBaseManager;
    
    public ReceptionalWorkDAO() {
        dataBaseManager = new DataBaseManager();
    }
    
    @Override
    public int addReceptionalWork(ReceptionalWork receptionalWork) throws DataInsertionException {
        int result;
        String query = "insert into TrabajosRecepcionales(IdAnteproyecto, título, descripcion) values(?, ?, ?)";
        try {
            DataBaseManager dataBaseManager = new DataBaseManager();
            Connection connection = dataBaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, receptionalWork.getResearchID());
            statement.setString(2, receptionalWork.getName());
            statement.setString(3, receptionalWork.getDescription());
            result = statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataInsertionException("New Receptional Work data could not be saved to the Database. Please try again later");
        }
        return result;
    }

    @Override
    public ArrayList<ReceptionalWork> getReceptionalWorksList() throws DataRetrievalException {
        ArrayList<ReceptionalWork> receptionalWorksList = new ArrayList();
        
        String query = "select * from TrabajosRecepcionales";
        try {
            DataBaseManager dataBaseManager = new DataBaseManager();
            Connection connection = dataBaseManager.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()) {
                ReceptionalWork receptionalWork = new ReceptionalWork();
                receptionalWork.setReceptionalWorkID(rs.getInt("IdTrabajoRecepcional"));
                receptionalWork.setResearchID(rs.getInt("IdAnteproyecto"));
                receptionalWork.setName(rs.getString("nombre"));
                receptionalWork.setDescription(rs.getString("descripcion"));
                
               receptionalWorksList.add(receptionalWork);
            }
        } catch (SQLException sql) {
            throw new DataRetrievalException("Failed to retrieve Receptional Works information. Please try again later");
        }
        
        return receptionalWorksList;
    }

    @Override
    public ReceptionalWork getReceptionalWorkByID(int receptionalWorkID) throws DataRetrievalException {
        ArrayList<ReceptionalWork> receptionalWorksList = new ArrayList(getReceptionalWorksList());
        ReceptionalWork receptionalWorkByID = new ReceptionalWork();
        int i = 0;
        boolean b = false;
        
        while(i < receptionalWorksList.size() && !b) {
            if(receptionalWorksList.get(i).getReceptionalWorkID() == receptionalWorkID) {
                receptionalWorkByID = receptionalWorksList.get(i);
                b = true;
            }
            i++;
        }
                
        if(!b){
            System.out.println("There is no Receptional Work that matches the given ID.");
        }
        
        return receptionalWorkByID;
    }
    
}
