package mx.uv.fei.gui.javafiles.guiuserscoursecontrollers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ProfessorPaneController {

    @FXML
    private Label nameLabel;

    @FXML
    private Label personalNumberLabel;

    public String getPersonalNumber() {
        return this.personalNumberLabel.getText();
    }

    public void setPersonalNumber(String personalNumber) {
        this.personalNumberLabel.setText(personalNumber);
    }

    public String getName() {
        return this.nameLabel.getText();
    }

    public void setName(String name) {
        this.nameLabel.setText(name);
    }

}
