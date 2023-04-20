package mx.uv.fei.gui.javafiles.guireporteanteproyectocontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class SelectedResearchItemController {

    private GuiReporteAnteproyectoController guiReporteAnteproyectoController;

    @FXML
    private Button deselectButton;

    @FXML
    private Label selectedResearchNameLabel;

    @FXML
    void deselectButtonController(ActionEvent event) {
        this.guiReporteAnteproyectoController.setElementToResearchesVBox(
            this.selectedResearchNameLabel.getText()
        );
        this.guiReporteAnteproyectoController.removeElementFromSelectedResearchesVBox(
            this.selectedResearchNameLabel.getText()
        );
    }

    void setSelectedResearchNameLabel(String title){
        this.selectedResearchNameLabel.setText(title);
    }

    void setGuiReporteAnteproyectoController(
        GuiReporteAnteproyectoController guiReporteAnteproyectoController){
            this.guiReporteAnteproyectoController = guiReporteAnteproyectoController;
    }
}
