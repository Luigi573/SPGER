package mx.uv.fei.gui.controllers.reports;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import mx.uv.fei.gui.controllers.AlertPaneController;

public class ResearchItemController {

    private GuiResearchReportController guiResearchReportController;

    @FXML
    private Label researchNameLabel;

    @FXML
    private Button selectButton;

    @FXML
    void selectButtonController(ActionEvent event) {
        for(Node selectedResearchHbox : ((VBox)this.guiResearchReportController.
            getSelectedResearchesVBox()).getChildren()){
            Node selectedResearchLabel = ((HBox)selectedResearchHbox).getChildren().get(1);
            if(this.researchNameLabel.getText().equals(((Label)selectedResearchLabel).getText())) {
                AlertPaneController alertPaneController = new AlertPaneController();
                alertPaneController.openErrorPane("Este anteproyecto ya está seleccionado");
                return;
            }
        }
        
        this.guiResearchReportController.setElementToSelectedResearchesVBox(
            this.researchNameLabel.getText()
        );
        this.guiResearchReportController.removeElementFromResearchesVBox(
            this.researchNameLabel.getText()
        );
    }

    public void setResearchNameLabel(String researchName) {
        this.researchNameLabel.setText(researchName);
    }

    public void setGuiResearchReportController(
        GuiResearchReportController guiResearchReportController){
            this.guiResearchReportController = guiResearchReportController;
    }

} 
