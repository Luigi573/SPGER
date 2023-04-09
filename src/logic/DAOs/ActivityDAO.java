package logic.DAOs;

import DataAccess.DataBaseManager;
import java.util.ArrayList;
import logic.DAOsInterfaces.IActivityDAO;
import logic.domain.Activity;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import logic.exceptions.DataRetrievalException;

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
