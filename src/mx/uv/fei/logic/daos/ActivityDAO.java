package mx.uv.fei.logic.daos;

import java.util.ArrayList;

import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.logic.daosinterfaces.IActivityDAO;
import mx.uv.fei.logic.domain.Activity;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.exceptions.DataInsertionException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ActivityDAO implements IActivityDAO{
    private DataBaseManager dataBaseManager;
    
    public ActivityDAO(){
        dataBaseManager = new DataBaseManager();
    }
    
    @Override
    public int addActivity(Activity activity) throws DataInsertionException{
        int result = 0;
        PreparedStatement statement;
        ResultSet resultSet;
        String query = "INSERT INTO Actividades(título, descripción, fechaCreación, fechaVencimiento) VALUES (?,?,?,?)";
        
        try{
            statement = dataBaseManager.getConnection().prepareStatement(query);
            
            statement.setString(1, activity.getTitle());
            statement.setString(2, activity.getDescription());
            statement.setDate(3, activity.getStartDate());
            statement.setDate(4, activity.getDueDate());
            
            result = statement.executeUpdate();
        }catch(SQLException exception){
            throw new DataInsertionException("Failed to add activity, please verify your internet connnection");
            //throw new DataWritingException(exception.getMessage());
        }finally{
            dataBaseManager.closeConnection();
        }
        return result;
    }

    @Override
    public ArrayList<Activity> getActivityList() throws DataRetrievalException{
        ArrayList<Activity> activityList = new ArrayList();
        String title, description;
        Date startDate, dueDate;
        PreparedStatement statement;
        String query = "SELECT * FROM Actividades";
        
        try{
            statement = dataBaseManager.getConnection().prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                title = resultSet.getString("título");
                description = resultSet.getString("descripción");
                startDate = resultSet.getDate("fechaCreación");
                dueDate = resultSet.getDate("fechaVencimiento");
                
                activityList.add(new Activity(title, description, startDate, dueDate));
            }
            
        }catch(SQLException exception){
            throw new DataRetrievalException("Failed to retrieve activity data, please verify your internet connection");
        }finally{
            dataBaseManager.closeConnection();
        }
        
        return activityList;
    }
    private boolean assertActivity(){
        return false;
    }
}
