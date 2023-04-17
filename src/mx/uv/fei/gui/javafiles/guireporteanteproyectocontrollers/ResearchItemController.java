package mx.uv.fei.gui.javafiles.guireporteanteproyectocontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.text.Text;

public class ResearchItemController {

    @FXML
    private Text researchNameText;

    @FXML
    private RadioButton selectResearchRadioButton;

    public Text getResearchNameText() {
        return researchNameText;
    }

    public void setResearchNameText(String researchName) {
        this.researchNameText.setText(researchName);
    }

    @FXML
    void selectResearchRadioButtonController(ActionEvent event) {

    }

}
