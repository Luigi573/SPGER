package mx.uv.fei.logic.domain;

import java.sql.Date;

public class Activity {
    private Date startDate;
    private Date dueDate;
    private int id;
    private int researchId;
    private String comment;
    private String description;
    private String feedback;
    private String title;
    
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setResearchId(int researchId) {
        this.researchId = researchId;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    
    public Date getStartDate() {
        return startDate;
    }
    public Date getDueDate() {
        return dueDate;
    }
    public int getId() {
        return id;
    }
    public int getResearchId() {
        return researchId;
    }
    public String getComment() {
        return comment;
    }
    public String getDescription() {
        return description;
    }
    public String getFeedback() {
        return feedback;
    }
    public String getTitle() {
        return title;
    }
    @Override
    public String toString(){
        return "Title: " + title + "\nDescription: " + description + "\nStartDate: " + startDate + "\nDueDate: " + dueDate;
    }
}
