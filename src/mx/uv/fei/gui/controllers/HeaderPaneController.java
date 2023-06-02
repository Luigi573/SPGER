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
import mx.uv.fei.logic.domain.Course;
import mx.uv.fei.logic.domain.User;

public class HeaderPaneController{
    private User user;
    
    @FXML
    private Label NRCLabel;
    @FXML
    private Label titleLabel;
    @FXML
    private Label usernameLabel;
    
    @FXML
    private void goHome(ActionEvent event) {
        try{
            if(user != null){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/MainMenu.fxml"));
                Parent parent = loader.load();
                MainMenuController controller = (MainMenuController)loader.getController();
                controller.setUser(user);
                controller.loadHeader();
                
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                Scene scene = new Scene(parent);
                String css = this.getClass().getResource("/mx/uv/fei/gui/stylesfiles/Styles.css").toExternalForm();
                scene.getStylesheets().add(css);
                stage.setTitle("SPGER");
                stage.setScene(scene);
                stage.show();
            }
        }catch(IOException exception){
            new AlertPopUpGenerator().showMissingFilesMessage();
        }
    }
    
    public void setUser(User user){
        this.user = user;
        
        usernameLabel.setText(user.toString());
    }
    
    public void setCourse(Course course){
        if(course != null){
            titleLabel.setText(course.getName());
            NRCLabel.setText("NRC: " + course.getNrc());
        }
    }
}