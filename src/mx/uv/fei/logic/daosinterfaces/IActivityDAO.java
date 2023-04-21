package mx.uv.fei.logic.daosinterfaces;

import java.util.ArrayList;

import mx.uv.fei.logic.domain.Activity;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.exceptions.DataWritingException;

public interface IActivityDAO {
    public int addActivity(Activity activity) throws DataWritingException;
    public ArrayList<Activity> getActivityList() throws DataRetrievalException;
}
