package mx.uv.fei.logic.daos;

import java.util.ArrayList;

import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.domain.Activity;
import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.logic.daosinterfaces.IActivityDAO;

import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;

public class ActivityDAO implements IActivityDAO{
    private DataBaseManager dataBaseManager;
    
    public ActivityDAO(){
        dataBaseManager = new DataBaseManager();
    }
    
    @Override
    public void addActivity(Activity activity) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public ArrayList<Activity> getActivityList() throws DataRetrievalException{
        ArrayList<Activity> activityList = new ArrayList();
        String title, description;
        SimpleDateFormat startDate, dueDate;
        String query = "SELECT * FROM Activities";
        
        try{
            Statement statement = dataBaseManager.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            
        }catch(SQLException exception){
            throw new DataRetrievalException("Failed to retrieve activity data, please verify your internet connection");
        }
        
        return activityList;
    }
    
    private boolean assertActivity(){
        return false;
    }
}
