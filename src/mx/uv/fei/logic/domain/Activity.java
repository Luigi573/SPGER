package mx.uv.fei.logic.domain;

import java.sql.Date;
import java.util.ArrayList;

import mx.uv.fei.logic.domain.statuses.ActivityStatus;

public class Activity {
    private int activityId;
    private int researchProjectId;
    private String comment;
    private String description;
    private Date dueDate;
    private String feedback;
    private Date startDate;
    private ActivityStatus status;
    private String title;
    private ArrayList<File> files;

    public Activity() {
        files = new ArrayList<>();
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public int getResearchProjectId() {
        return researchProjectId;
    }

    public void setResearchProjectId(int researchProjectId) {
        this.researchProjectId = researchProjectId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public ActivityStatus getStatus() {
        return status;
    }

    public void setStatus(ActivityStatus status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<File> getFiles() {
        return files;
    }

    public void setFiles(ArrayList<File> files) {
        this.files = files;
    }

    @Override
    public String toString() {
        return "Title: " + title + "\nDescription: " + description + "\nStartDate: " + startDate + "\nDueDate: "
                + dueDate;
    }
}