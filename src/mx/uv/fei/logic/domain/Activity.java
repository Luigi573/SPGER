package mx.uv.fei.logic.domain;

import java.sql.Date;
import mx.uv.fei.logic.domain.statuses.ActivityStatus;

public class Activity {
    private ActivityStatus status;
    private Date startDate;
    private Date dueDate;
    private int id;
    private int researchId;
    private String comment;
    private String description;
    private String feedback;
    private String title;
    
    public void setStatus(ActivityStatus status){
        this.status = status;
    }
    public ActivityStatus getStatus(){
        return status;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    
    public Date getStartDate() {
        return startDate;
    }
    
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
    
    public Date getDueDate() {
        return dueDate;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }
    
    public void setResearchId(int researchId) {
        this.researchId = researchId;
    }
    
    public int getResearchId() {
        return researchId;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }
    
    public String getComment() {
        return comment;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
    
    public String getFeedback() {
        return feedback;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getTitle() {
        return title;
    }
    
    @Override
    public String toString(){
        return "Title: " + title + "\nDescription: " + description + "\nStartDate: " + startDate + "\nDueDate: " + dueDate;
    }
}
