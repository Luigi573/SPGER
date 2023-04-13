package mx.uv.fei.logic.domain;

import java.text.SimpleDateFormat;

public class Activity {
    String title;
    String description;
    SimpleDateFormat startDate;
    SimpleDateFormat dueDate;
    
    public Activity(String title, String description, SimpleDateFormat startDate, SimpleDateFormat dueDate){
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
    public void setStartDate(SimpleDateFormat startDate){
        this.startDate = startDate;
    }
    public void setDueDate(SimpleDateFormat dueDate){
        this.dueDate = dueDate;
    }
    public String getTitle(){
        return title;
    }
    public String getDescription(){
        return description;
    }
    public SimpleDateFormat getStartDate(){
        return startDate;
    }
    public SimpleDateFormat getDueDate(){
        return dueDate;
    }
}
