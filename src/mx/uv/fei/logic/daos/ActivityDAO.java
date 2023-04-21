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
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.logic.daosinterfaces.IActivityDAO;
import mx.uv.fei.logic.domain.Activity;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.exceptions.DataWritingException;

public class ActivityDAO implements IActivityDAO{
    private DataBaseManager dataBaseManager;
    
    public ActivityDAO(){
        dataBaseManager = new DataBaseManager();
    }
    
    @Override
    public int addActivity(Activity activity) throws DataWritingException{
        int result = 0;
        PreparedStatement statement;
        String query = "INSERT INTO Actividades(título, descripción, fechaInicio, fechaFin) VALUES (?,?,?,?)";
        
        try{
            statement = dataBaseManager.getConnection().prepareStatement(query);
            statement.setString(1, activity.getTitle());
            statement.setString(2, activity.getDescription());
            statement.setDate(3, activity.getStartDate());
            statement.setDate(4, activity.getDueDate());
            
            result = statement.executeUpdate();
        }catch(SQLException exception){
            System.out.println(exception.getMessage());
            throw new DataWritingException("Error al agregar actividad. Verifique su conexion e intentelo de nuevo");
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
                startDate = resultSet.getDate("fechaInicio");
                dueDate = resultSet.getDate("fechaFin");
                
                activityList.add(new Activity(title, description, startDate, dueDate));
            }
        }catch(SQLException exception){
            System.out.println(exception.getMessage());
            throw new DataRetrievalException("Fallo al recuperar la informacion. Verifique su conexion e intentelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }
        
        return activityList;
    }
    public boolean assertActivity(Activity activity){
        return !isNull(activity) && !isBlank(activity) && isValidDate(activity);
    }
    public boolean isBlank(Activity activity){
        return activity.getTitle().equals("") && activity.getDescription().equals("");
    }
    public boolean isNull(Activity activity){
        if(activity != null){
            if(activity.getTitle() != null && activity.getDescription() != null){
                if(activity.getStartDate() != null && activity.getDueDate() != null){
                    return false;
                }
            }
        }
        
        return true;
    }
    public boolean isValidDate(Activity activity){
        return activity.getStartDate().compareTo(activity.getDueDate()) < 0;
    }
}
