package mx.uv.fei.gui.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import mx.uv.fei.gui.AlertPopUpGenerator;
import mx.uv.fei.gui.controllers.scholarperiods.GuiScholarPeriodsController;
import mx.uv.fei.logic.domain.User;

public class ManageScholarPeriodsPaneController{
    private User user;

    @FXML
    private Pane mainMenuSpecificPane;

    @FXML
    private void goToManageScholarPeriods(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/scholarperiods/GuiScholarPeriods.fxml"));
        
        try{
            Parent parent = loader.load();
            GuiScholarPeriodsController controller = (GuiScholarPeriodsController)loader.getController();
            controller.setHeaderUser(user);
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

    public void setUser(User user){
        this.user = user;
    }
}
