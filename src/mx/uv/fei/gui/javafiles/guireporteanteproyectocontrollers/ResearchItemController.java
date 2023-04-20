package mx.uv.fei.gui.javafiles.guireporteanteproyectocontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class ResearchItemController {

    private GuiReporteAnteproyectoController guiReporteAnteproyectoController;

    @FXML
    private Label researchNameLabel;

    @FXML
    private Button selectButton;

    @FXML
    void selectButtonController(ActionEvent event) {
        this.guiReporteAnteproyectoController.setElementToSelectedResearchesVBox(
            this.researchNameLabel.getText()
        );
        this.guiReporteAnteproyectoController.removeElementFromResearchesVBox(
            this.researchNameLabel.getText()
        );
    }

    public void setResearchNameLabel(String researchName) {
        this.researchNameLabel.setText(researchName);
    }

    public void setGuiReporteAnteproyectoController(
        GuiReporteAnteproyectoController guiReporteAnteproyectoController){
            this.guiReporteAnteproyectoController = guiReporteAnteproyectoController;
    }

}
