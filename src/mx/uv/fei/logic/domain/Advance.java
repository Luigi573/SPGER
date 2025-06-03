package mx.uv.fei.logic.domain;

import java.sql.Date;
import java.util.ArrayList;

public class Advance {
    private int advanceID;
    private int activityID;
    private String comment;
    private Date date;
    private String feedback;
    private String status;
    private String title;
    private ArrayList<File> files;

    public Advance() {
        files = new ArrayList<>();
    }

    public int getAdvanceID() {
        return advanceID;
    }

    public void setAdvanceID(int advanceID) {
        this.advanceID = advanceID;
    }

    public int getActivityID() {
        return activityID;
    }

    public void setActivityID(int activityID) {
        this.activityID = activityID;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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
    public boolean equals(Object obj) {
        if (obj instanceof Advance advance) {
            return this.activityID == advance.getActivityID() && this.title.equals(advance.getTitle())
                    && this.comment.equals(advance.getComment());
        }

        return false;
    }

    @Override
    public String toString() {
        return "ID Avance: " + advanceID + " ID Actividad: " + activityID + " Titulo: "
                + title + " Comentarios: " + comment;
    }
}