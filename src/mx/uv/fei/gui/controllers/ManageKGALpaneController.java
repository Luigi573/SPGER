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
import mx.uv.fei.gui.controllers.kgal.KGALListController;
import mx.uv.fei.logic.domain.User;

public class ManageKGALpaneController{
    private User user;
    
    
    @FXML
    public void initialize() {
        
    }    

    @FXML
    private void goToManageKGAL(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/kgal/KGALList.fxml"));
        
        try{
            Parent parent = loader.load();
            KGALListController controller = (KGALListController)loader.getController();
            controller.setUser(user);
            controller.loadHeader();
            controller.loadKgalList();

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
        
    public void setUser(User user){
        this.user = user;
    }
}
