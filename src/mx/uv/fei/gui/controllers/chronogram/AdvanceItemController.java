package mx.uv.fei.gui.controllers.chronogram;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class AdvanceItemController {

    @FXML
    private Pane advanceListItem;

    @FXML
    private Label advanceTitleLabel;

    @FXML
    private Label fileNameLabel;

    @FXML
    private Button seeAdvanceDetailsbutton;

    @FXML
    void seeAdvanceDetails(ActionEvent event) {

    }

    public void setAdvanceTitleLabel(String advanceTitle) {
        advanceTitleLabel.setText(advanceTitle);
    }
    
    public void setFileNameLabel(String fileName) {
        fileNameLabel.setText(fileName);
    }
}
