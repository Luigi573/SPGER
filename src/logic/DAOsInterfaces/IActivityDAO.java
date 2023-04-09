package logic.DAOsInterfaces;

import logic.domain.Activity;
import java.util.ArrayList;
import logic.exceptions.DataRetrievalException;

public interface IActivityDAO {
    public void addActivity(Activity activity);
    public ArrayList<Activity> getActivityList() throws DataRetrievalException;
}
