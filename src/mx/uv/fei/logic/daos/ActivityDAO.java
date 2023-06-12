package mx.uv.fei.logic.daos;

import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.logic.daosinterfaces.IActivityDAO;
import mx.uv.fei.logic.domain.Activity;
import mx.uv.fei.logic.domain.statuses.ActivityStatus;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.exceptions.DataInsertionException;

public class ActivityDAO implements IActivityDAO{
    private final DataBaseManager dataBaseManager;
    
    public ActivityDAO(){
        dataBaseManager = new DataBaseManager();
    }
    
    @Override
    public int addActivity(Activity activity) throws DataInsertionException{
        int generatedId = 0;
        PreparedStatement statement;
        String query = "INSERT INTO Actividades(título, descripción, fechaInicio, fechaFin, IdAnteproyecto, estado) VALUES (?,?,?,?,?,?)";
        
        try{
            statement = dataBaseManager.getConnection().prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            
            statement.setString(1, activity.getTitle());
            statement.setString(2, activity.getDescription());
            statement.setDate(3, activity.getStartDate());
            statement.setDate(4, activity.getDueDate());
            statement.setInt(5, activity.getResearchId());
            statement.setString(6, ActivityStatus.ACTIVE.getValue());
            
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            
            if(resultSet.next()){
                generatedId = resultSet.getInt(1);
            }
        }catch(SQLException exception){
            throw new DataInsertionException("Error al agregar actividad. Verifique su conexion e intentelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }
        
        return generatedId;
    }

    @Override
    public ArrayList<Activity> getActivityList(int researchId) throws DataRetrievalException{
        ArrayList<Activity> activityList = new ArrayList();
        PreparedStatement statement;
        String query = "SELECT IdActividad, a.IdAnteproyecto, IdArchivo, a.título, a.descripción, a.fechaInicio, a.fechaFin, a.comentario, a.retroalimentación "
                + "FROM Actividades a INNER JOIN Anteproyectos ap ON a.IdAnteproyecto = ap.IdAnteproyecto WHERE a.IdAnteproyecto IN(?) "
                + "ORDER BY fechaFin, fechaInicio, título ASC";
        
        try{
            statement = dataBaseManager.getConnection().prepareStatement(query);
            
            statement.setInt(1, researchId);
            
            ResultSet resultSet = statement.executeQuery();
                        
            while(resultSet.next()){
                Activity activity = new Activity();
                
                activity.setId(resultSet.getInt("IdActividad"));
                activity.setTitle(resultSet.getString("a.título"));
                activity.setDescription(resultSet.getString("a.descripción"));
                activity.setStartDate(resultSet.getDate("a.fechaInicio"));
                activity.setDueDate(resultSet.getDate("a.fechaFin"));
                activity.setComment(resultSet.getString("a.comentario"));
                activity.setFeedback(resultSet.getString("a.retroalimentación"));
                
                if(activity.getFeedback() != null){
                    activity.setStatus(ActivityStatus.REVIEWED);
                }else if(resultSet.getInt("IdArchivo") > 0){
                    activity.setStatus(ActivityStatus.DELIVERED);
                }else{
                    activity.setStatus(ActivityStatus.ACTIVE);
                }
                
                activityList.add(activity);
            }
        }catch(SQLException exception){
            throw new DataRetrievalException("Fallo al recuperar la informacion. Verifique su conexion e intentelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }
        
        return activityList;
    }

    @Override
    public int modifyActivity(Activity activity) throws DataInsertionException{
        int result = 0;
        PreparedStatement statement;
        String query = "UPDATE Actividades SET título = ?, descripción = ?, fechaInicio = ?, fechaFin = ? WHERE IdActividad = ?";
        
        try{
            statement = dataBaseManager.getConnection().prepareStatement(query);
            statement.setString(1, activity.getTitle());
            statement.setString(2, activity.getDescription());
            statement.setDate(3, activity.getStartDate());
            statement.setDate(4, activity.getDueDate());
            statement.setInt(5, activity.getId()); 
           
            result = statement.executeUpdate();
        }catch(SQLException exception){
            System.out.println(exception.getMessage());
            throw new DataInsertionException("Error al modificar actividad. Verifique su conexion e intentelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }
        
        return result;
    }
    
    public int setComment(String comment, int activityId) throws DataInsertionException{
        int result = 0;
        PreparedStatement statement;
        String query = "UPDATE Actividades SET comentario = ? WHERE IdActividad IN(?)";
        
        try{
            statement = dataBaseManager.getConnection().prepareStatement(query);
            
            statement.setString(1, comment);
            statement.setInt(2, activityId);
            
            result = statement.executeUpdate();
        }catch(SQLException exception){
            throw new DataInsertionException("Error de conexión. Favor de revisar su conexión con la base de datos e inténtelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }
        
        return result;
    }
    @Override
    public int setFeedback(String feedback, int activityId)  throws DataInsertionException{
        int result = 0;
        PreparedStatement statement;
        String query = "UPDATE Actividades SET retroalimentación = ? WHERE IdActividad IN(?)";
        
        try{
            statement = dataBaseManager.getConnection().prepareStatement(query);
            
            statement.setString(1, feedback);
            statement.setInt(2, activityId);
            
            result = statement.executeUpdate();
        }catch(SQLException exception){
            throw new DataInsertionException("Error de conexión. Favor de revisar su conexión con la base de datos e inténtelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }
        
        return result;
    }
    @Override
    public boolean assertActivity(Activity activity){
        return !isBlank(activity) && isValidDate(activity);
    }

    public boolean isBlank(Activity activity){
        return activity.getTitle().isBlank() || activity.getDescription().isBlank();
    }
    
    public boolean isValidDate(Activity activity){
        return activity.getStartDate().compareTo(activity.getDueDate()) <= 0;
    }
    public boolean isValidTitle(Activity activity){
        return activity.getTitle().length() <= 50;
    }
}