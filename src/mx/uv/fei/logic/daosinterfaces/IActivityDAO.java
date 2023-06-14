package mx.uv.fei.logic.daosinterfaces;

import java.util.ArrayList;

import mx.uv.fei.logic.domain.Activity;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.exceptions.DataInsertionException;

public interface IActivityDAO {
    public int addActivity(Activity activity) throws DataInsertionException;
    public ArrayList<Activity> getActivityList(int researchId) throws DataRetrievalException;
    public int modifyActivity(Activity activity) throws DataInsertionException;
    public boolean assertActivity(Activity activity);
    public int setFeedback(String feedback, int activityId) throws DataInsertionException;
    public int setComment(String feedback, int activityId) throws DataInsertionException;
}
