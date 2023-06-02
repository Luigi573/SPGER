package mx.uv.fei.gui.controllers.reports;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import mx.uv.fei.gui.AlertPopUpGenerator;

public class ResearchItemController{
    /*private GuiResearchReportController guiResearchReportController;

    @FXML
    private Label researchNameLabel;
    @FXML
    private Button selectButton;

    @FXML
    private void selectButtonController(ActionEvent event){
        for(Node selectedResearchHbox : ((VBox)guiResearchReportController.
            getSelectedResearchesVBox()).getChildren()){
            Node selectedResearchLabel = ((HBox)selectedResearchHbox).getChildren().get(1);
            if(researchNameLabel.getText().equals(((Label)selectedResearchLabel).getText())){
                new AlertPopUpGenerator().showCustomMessage(AlertType.WARNING, "Error", "Este anteproyecto ya está seleccionado");
                return;
            }
        }
        
        guiResearchReportController.setElementToSelectedResearchesVBox(
            researchNameLabel.getText()
        );
        guiResearchReportController.removeElementFromResearchesVBox(
            researchNameLabel.getText()
        );
    }

    public void setResearchNameLabel(String researchName){
        researchNameLabel.setText(researchName);
    }
    public void setGuiResearchReportController(
        GuiResearchReportController guiResearchReportController){
            this.guiResearchReportController = guiResearchReportController;
    }*/
} 
