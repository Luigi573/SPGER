package mx.uv.fei.gui.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import mx.uv.fei.gui.AlertPopUpGenerator;
import mx.uv.fei.logic.domain.User;

public class MainMenuController{
    private User user;
    
    @FXML
    private Pane headerPane;
    
    @FXML
    private void initialize() {
        loadHeader();
    }
    
    public void setUser(User user){
        this.user = user;
        
        loadHeader();
    }
    
    private void loadHeader(){
        headerPane.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/HeaderPane.fxml"));
        
        try{
            Pane header = loader.load();
            
            if(user != null){
                HeaderPaneController controller = (HeaderPaneController)loader.getController();
                controller.setUser(user);
            }
            
            headerPane.getChildren().add(header);
            
        }catch(IOException exception){
            new AlertPopUpGenerator().showMissingFilesMessage();
        }
    }
}
