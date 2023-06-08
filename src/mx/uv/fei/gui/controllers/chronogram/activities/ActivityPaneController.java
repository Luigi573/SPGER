package mx.uv.fei.gui.controllers.chronogram.activities;

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
import mx.uv.fei.logic.domain.Activity;
import mx.uv.fei.logic.domain.Course;
import mx.uv.fei.logic.domain.User;

public class ActivityPaneController{
    private Activity activity;
    private Course course;
    private User user;
    
    @FXML
    private Label dueDateLabel;
    @FXML
    private Label titleLabel;
    @FXML
    private Label statusLabel;
    @FXML
    private Label startDateLabel;
    
    @FXML
    private void viewActivity(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/chronogram/activities/ActivityInfo.fxml"));
            Parent parent = loader.load();
            ActivityInfoController controller = (ActivityInfoController)loader.getController();
            controller.setActivity(activity);
            controller.setCourse(course);
            controller.setUser(user);
            controller.loadAdvances();
            controller.loadHeader();
            
            Scene scene = new Scene(parent);
            String css = this.getClass().getResource("/mx/uv/fei/gui/stylesfiles/Styles.css").toExternalForm();
            scene.getStylesheets().add(css);
            
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setTitle("SPGER");
            stage.setScene(scene);
            stage.show();
        }catch(IOException exception){
            new AlertPopUpGenerator().showMissingFilesMessage();
        }
    }
    
    public void setActivity(Activity activity){
        this.activity = activity;
        titleLabel.setText(activity.getTitle());
        startDateLabel.setText(activity.getStartDate().toString());
        dueDateLabel.setText(activity.getDueDate().toString());
        statusLabel.setText(activity.getStatus().getValue());
    }
    
    public void setCourse(Course course){
        this.course = course;
    }
    
    public void setUser(User user){
        this.user = user;
    }
}