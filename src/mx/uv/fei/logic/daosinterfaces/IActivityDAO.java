package mx.uv.fei.logic.daosinterfaces;

import java.util.ArrayList;

import mx.uv.fei.logic.domain.Activity;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.exceptions.DataWritingException;

public interface IActivityDAO {
    public int addActivity(Activity activity) throws DataWritingException;
    public ArrayList<Activity> getActivityList(int researchId) throws DataRetrievalException;
    public int modifyActivity(Activity activity) throws DataWritingException;
    public boolean assertActivity(Activity activity);
    public int setComment(String comment, int activityId) throws DataWritingException;
    public int setFeedback(String feedback, int activityId) throws DataWritingException;
}
