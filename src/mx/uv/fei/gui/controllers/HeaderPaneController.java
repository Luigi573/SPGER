package mx.uv.fei.gui.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
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
    private void goHome(MouseEvent event) {
        if(user != null){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/MainMenu.fxml"));
                Parent parent = loader.load();
                MainMenuController controller = (MainMenuController)loader.getController();
                controller.setUser(user);
                controller.loadHeader();

                Scene scene = new Scene(parent);
                String css = this.getClass().getResource("/mx/uv/fei/gui/stylesfiles/Styles.css").toExternalForm();
                scene.getStylesheets().add(css);

                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            }catch(IOException ex){
                new AlertPopUpGenerator().showMissingFilesMessage();
            }
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