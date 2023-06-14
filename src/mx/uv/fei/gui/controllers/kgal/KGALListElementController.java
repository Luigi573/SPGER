package mx.uv.fei.gui.controllers.kgal;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import mx.uv.fei.gui.AlertPopUpGenerator;
import mx.uv.fei.logic.domain.KGAL;
import mx.uv.fei.logic.domain.User;

public class KGALListElementController {
    private KGAL kgal;
    private User user;
    
    @FXML
    private Button btnUpdate;
    @FXML
    private Label lKGALDescription;
    @FXML
    private Label lKGALID;     
    @FXML
    private Pane pKGALListElement;
        
    public void setLabelText(KGAL kgal) {
        lKGALDescription.setText("Descripción: " + kgal.getDescription());
        lKGALID.setText("ID: " + kgal.getKgalID());
    }
    
    public void switchToUpdateKGALScene(ActionEvent event) {
        try {  
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/manageKGAL/UpdateKGAL.fxml"));
            Parent root = loader.load();
            UpdateKGALController controller = (UpdateKGALController)loader.getController();
            controller.setKGAL(kgal);
            
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene); 
            stage.show();     
        } catch (IOException e) {
            new AlertPopUpGenerator().showMissingFilesMessage();
        }
    }
    
    public void setKGAL(KGAL kgal) {
        this.kgal = kgal;
    }
}
