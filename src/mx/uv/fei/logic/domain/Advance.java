package mx.uv.fei.logic.domain;

import java.sql.Date;

public class Advance {
    private int advanceID;
    private int activityID;
    private int fileID;
    private Date date;
    private String comments;
    private String feedback;
    private String title;
    private String state;

    public Advance(){
        
    }
    
    public Advance(int activityID, String title, String comments) {
        this.activityID = activityID;
        this.title = title;
        this.comments = comments;
    }
    
    public void setAdvanceID(int advanceID) {
        this.advanceID = advanceID;
    }
    
    public void setActivityID(int activityID) {
        this.activityID = activityID;
    }
    
    public void setComments(String comments) {
        this.comments = comments;
    }
    
    public void setDate(Date date){
        this.date = date;
    }
    
    public void setFileID(int fileID) {
        this.fileID = fileID;
    }
    
    public void setFeedback(String feedback){
        this.feedback = feedback;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    
    public int getAdvanceID() {
        return this.advanceID;
    }
    
    public int getActivityID() {
        return this.activityID;
    }
    
    public String getComments() {
        return this.comments;
    }
    
    public Date getDate(){
        return date;
    }
    
    public int getFileID() {
        return this.fileID;
    }
    
    public String getFeedback(){
        return feedback;
    }
            
    public String getTitle() {
        return this.title;
    }
    
    public String getState() {
        return this.state;
    }
    
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Advance)) {
            return false;
        }
            Advance advance = (Advance) o;
            return ((this.activityID == advance.getActivityID()) && (this.title.equals(advance.getTitle())) && (this.comments.equals(advance.getComments())));
    }

    @Override
    public String toString() {
        return "ID Avance: " + this.advanceID + " ID Actividad: " + this.activityID + " ID Archivo: " + this.fileID + " Titulo: " + this.title + " Comentarios: " + this.comments;
    }
}
