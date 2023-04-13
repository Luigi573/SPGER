package mx.uv.fei.logic.daosinterfaces;

import java.util.ArrayList;

import mx.uv.fei.logic.domain.Activity;
import mx.uv.fei.logic.exceptions.DataRetrievalException;

public interface IActivityDAO {
    public void addActivity(Activity activity);
    public ArrayList<Activity> getActivityList() throws DataRetrievalException;
}
