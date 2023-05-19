package mx.uv.fei.gui.controllers.chronogram.activities;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class ActivityFileItemController {
    
    @FXML
    private Label lFileName;

    @FXML
    private Pane pKGALListElement;
    
    @FXML
    private Button removeFile;

    @FXML
    public void setLabelText(String fileName) {
        lFileName.setText(fileName);
    }
}
