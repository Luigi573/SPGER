package mx.uv.fei.gui.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import mx.uv.fei.gui.AlertPopUpGenerator;
import mx.uv.fei.gui.controllers.chronogram.ChronogramController;
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
    private void goToChronogram(ActionEvent event){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/chronogram/Chronogram.fxml"));
        
        try{
            Parent parent = loader.load();
            ChronogramController controller = (ChronogramController)loader.getController();
            controller.setCourse(course);
            
            if(Professor.class.isAssignableFrom(user.getClass())){
                controller.setUser(user);
            }else{
                controller.setUser(user);
            }
            
            controller.loadHeader();

            Scene scene = new Scene(parent);
            String css = this.getClass().getResource("/mx/uv/fei/gui/stylesfiles/Styles.css").toExternalForm();
            scene.getStylesheets().add(css);
            
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }catch(IOException exception){
            new AlertPopUpGenerator().showMissingFilesMessage();
        }
    }
    
    public void setCourse(Course course){
        this.course = course;
        
        courseLabel.setText(course.getName());
        NRCLabel.setText(Integer.toString(course.getNrc()));
        
        if(course.getScholarPeriod() != null){
            startDateLabel.setText(course.getScholarPeriod().getStartDate().toString());
            endDateLabel.setText(course.getScholarPeriod().getEndDate().toString());
        }
        
        Professor professor = course.getProfessor();
        professorLabel.setText(professor.getName() + " " + professor.getFirstSurname() + " " + professor.getSecondSurname().charAt(0) + ".");
    }
    public void setUser(User user){
        this.user = user;
    }
}