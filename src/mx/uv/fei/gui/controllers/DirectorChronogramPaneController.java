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
import mx.uv.fei.gui.controllers.chronogram.ChronogramController;
import mx.uv.fei.logic.domain.Director;

public class DirectorChronogramPaneController {
    private Director director;
    
    @FXML
    private void goToChronogram(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/chronogram/Chronogram.fxml"));
        
        try{
            Parent parent = loader.load();
            ChronogramController controller = (ChronogramController)loader.getController();
            controller.setUser(director);
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
    
    public void setDirector(Director director){
        this.director = director;
    }
}
