package mx.uv.fei.logic.domain;

import java.sql.Date;

public class Advance {
    private int advanceID;
    private int activityID;
    private int fileID;
    private Date date;
    private String comment;
    private String feedback;
    private String title;    
    private String status;

    public void setAdvanceID(int advanceID) {
        this.advanceID = advanceID;
    }
    
    public int getAdvanceID() {
        return this.advanceID;
    }
    
    public void setActivityID(int activityID) {
        this.activityID = activityID;
    }
    
    public int getActivityID() {
        return this.activityID;
    }
    
    public void setFileID(int fileID) {
        this.fileID = fileID;
    }
    
    public int getFileID() {
        return this.fileID;
    }
    
    public void setDate(Date date){
        this.date = date;
    }
    
    public Date getDate(){
        return date;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }
    
    public String getComment() {
        return comment;
    }
    
    public void setFeedback(String feedback){
        this.feedback = feedback;
    }
    
    public String getFeedback(){
        return feedback;
    }
    
    public void setState(String status) {
        this.status = status;
    }
    
    public String getState() {
        return status;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Advance advance) {
            return this.activityID == advance.getActivityID() && this.title.equals(advance.getTitle()) && this.comment.equals(advance.getComment());
        }
            
        return false;
    }

    @Override
    public String toString() {
        return "ID Avance: " + advanceID + " ID Actividad: " + activityID + " ID Archivo: " + fileID + " Titulo: " + title + " Comentarios: " + comment;
    }
}
