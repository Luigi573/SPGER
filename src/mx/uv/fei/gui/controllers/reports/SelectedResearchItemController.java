package mx.uv.fei.gui.controllers.reports;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class SelectedResearchItemController {

    private GuiResearchReportController guiResearchReportController;

    @FXML
    private Button deselectButton;

    @FXML
    private Label selectedResearchNameLabel;

    @FXML
    void deselectButtonController(ActionEvent event) {
        this.guiResearchReportController.setElementToResearchesVBox(
            this.selectedResearchNameLabel.getText()
        );
        this.guiResearchReportController.removeElementFromSelectedResearchesVBox(
            this.selectedResearchNameLabel.getText()
        );
    }

    void setSelectedResearchNameLabel(String title){
        this.selectedResearchNameLabel.setText(title);
    }

    void setGuiResearchReportController(
        GuiResearchReportController guiResearchReportController){
            this.guiResearchReportController = guiResearchReportController;
    }
}
