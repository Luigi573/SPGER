package mx.uv.fei.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import mx.uv.fei.logic.domain.KGAL;

public class KGALListElementController {

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
    
}
