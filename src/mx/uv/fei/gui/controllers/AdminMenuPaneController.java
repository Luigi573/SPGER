package mx.uv.fei.gui.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mx.uv.fei.gui.AlertPopUpGenerator;
import mx.uv.fei.gui.controllers.courses.GuiCoursesController;
import mx.uv.fei.gui.controllers.users.GuiUsersController;
import mx.uv.fei.logic.domain.User;

public class AdminMenuPaneController{
    private User user;
    
    @FXML
    private void goToCourseManager(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/courses/GuiCourses.fxml"));
        
        try{
            if(user != null){
                Parent parent = loader.load();
                GuiCoursesController controller = (GuiCoursesController)loader.getController();
                controller.setUser(user);
                controller.loadHeader();
                
                Scene scene = new Scene(parent);
                String css = this.getClass().getResource("/mx/uv/fei/gui/stylesfiles/Styles.css").toExternalForm();
                scene.getStylesheets().add(css);
                
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setResizable(false);

                stage.show();
            }
        }catch(IOException exception){
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }
    }

    @FXML
    private void goToUserManager(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/users/GuiUsers.fxml"));
        
        try{
            if(user != null){
                Parent parent = loader.load();
                GuiUsersController controller = (GuiUsersController)loader.getController();
                controller.setUser(user);
                controller.loadHeader();
                
                Scene scene = new Scene(parent);
                String css = this.getClass().getResource("/mx/uv/fei/gui/stylesfiles/Styles.css").toExternalForm();
                scene.getStylesheets().add(css);
                
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setResizable(false);

                stage.show();
            }
        }catch(IOException exception){
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }
    }
    
    public void setUser(User user){
        this.user = user;
    }
}
