package mx.uv.fei.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import mx.uv.fei.logic.domain.Course;
import mx.uv.fei.logic.domain.Professor;
import mx.uv.fei.logic.domain.User;

public class CourseHBoxPaneController{
    private Course course;
    private User user;
    
    @FXML
    private Label courseLabel;
    @FXML
    private Label NRCLabel;
    @FXML
    private Label startDateLabel;
    @FXML
    private Label endDateLabel;
    @FXML
    private Label professorLabel;
    
    @FXML
    private void initialize() {
        // TODO
    }    
    
    @FXML
    private void goToChronogram(ActionEvent actionEvent){
        
    }
    
    public void setCourse(Course course){
        this.course = course;
        
        courseLabel.setText(course.getName());
        NRCLabel.setText(Integer.toString(course.getNrc()));
        startDateLabel.setText(course.getScholarPeriod().getStartDate().toString());
        endDateLabel.setText(course.getScholarPeriod().getEndDate().toString());
        
        Professor professor = course.getProfessor();
        professorLabel.setText(professor.getName() + " " + professor.getFirstSurname() + " " + professor.getSecondSurname().charAt(0) + ".");
    }
    public void setUser(User user){
        this.user = user;
    }
}
