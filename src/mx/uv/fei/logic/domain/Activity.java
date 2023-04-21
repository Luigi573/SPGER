package mx.uv.fei.logic.domain;

import java.sql.Date;

public class Activity {
    String title;
    String description;
    Date startDate;
    Date dueDate; 
   
    public Activity(String title, String description, Date startDate, Date dueDate){
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.dueDate = dueDate;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public void setStartDate(Date startDate){
        this.startDate = startDate;
    }
    public void setDueDate(Date dueDate){
        this.dueDate = dueDate;
    }
    public String getTitle(){
        return title;
    }
    public String getDescription(){
        return description;
    }
    public Date getStartDate(){
        return startDate;
    }
    public Date getDueDate(){
        return dueDate;
    }
    public String toString(){
        return "Title: " + title + "\nDescription: " + description + "\nStartDate: " + startDate + "\nDueDate: " + dueDate;
    }
}
