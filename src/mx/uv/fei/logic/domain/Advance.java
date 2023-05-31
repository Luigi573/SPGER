package mx.uv.fei.logic.domain;

import java.sql.Date;

public class Advance {
    private int id;
    private int activityId;
    private Date date;
    private String title;
    private String comment;
    private String feedback;

    public Advance() {
        
    }
    
    public Advance(String studentID, int directorID, String title, String comments) {
        this.title = title;
        this.comment = comments;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public void setActivityId(int activityId){
        this.activityId = activityId;
    }
    public void setDate(Date date){
        this.date = date;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }
    public void setFeedback(String feedback){
        this.feedback = feedback;
    }
    
    public int getId() {
        return id;
    }
    public int getActivityId(){
        return activityId;
    }
    public Date getDate(){
        return date;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getComment() {
        return comment;
    }
    public String getFeedback(){
        return feedback;
    }
    
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Advance)) {
            return false;
        }
            Advance advance = (Advance) o;
            return ((title.equals(advance.getTitle())) && (comment.equals(advance.getComment())));
    }

    @Override
    public String toString() {
        return "ID Avance: "  + " Titulo: " + title + " Comentario: " + comment + "Fecha: " + date;
    }
}
