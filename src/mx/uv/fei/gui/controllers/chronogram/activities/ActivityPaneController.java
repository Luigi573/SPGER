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

public class ActivityPaneController{
    private Activity activity;
    @FXML
    private Label dueDateLabel;
    @FXML
    private Label startDateLabel;
    @FXML
    private Label titleLabel;
    
    @FXML
    private void viewActivity(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/chronogram/activities/ActivityInfo.fxml"));
            Parent parent = loader.load();
            ActivityInfoController controller = (ActivityInfoController)loader.getController();
            controller.setActivity(activity);
            
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(parent);
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
    }
}