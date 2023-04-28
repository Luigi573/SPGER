package mx.uv.fei.gui;

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
import mx.uv.fei.logic.domain.KGAL;

public class KGALListElementController {

    private Stage stage;
    private Scene scene;
    private Parent root;
    
    @FXML
    private Button btnUpdate;
    
    @FXML
    private Label lKGALDescription;

    @FXML
    private Label lKGALID;
            
    @FXML
    private Pane pKGALListElement;
    
    public KGALListElementController () {}
    
    @FXML
    public void initialize() {
        
    }
    
    public void setLabelText(KGAL kgal) {
        lKGALDescription.setText("Descripción: " + kgal.getDescription());
        lKGALID.setText("ID: " + kgal.getKgalID());
    }
    
    public void switchToUpdateKGAL(ActionEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("/mx/uv/fei/gui/UpdateKGAL.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene); 
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
