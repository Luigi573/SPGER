package mx.uv.fei.logic.daosinterfaces;

import java.util.ArrayList;

import mx.uv.fei.logic.domain.Activity;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.exceptions.DataInsertionException;

public interface IActivityDAO {
    public int addActivity(Activity activity) throws DataInsertionException;
    public ArrayList<Activity> getActivityList() throws DataRetrievalException;
    public int modifyActivity(int oldActivityId, Activity newActivity) throws DataInsertionException;
}
