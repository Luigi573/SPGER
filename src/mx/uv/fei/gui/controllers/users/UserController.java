package mx.uv.fei.gui.controllers.users;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class UserController {
    GuiUsersController guiUsersController;

    @FXML
    private Label matricleOrStaffNumberLabel;

    @FXML
    private Label matricleOrStaffNumberText;

    @FXML
    private Label nameLabel;

    @FXML
    private Label typeLabel;

    @FXML
    private Button userButton;

    @FXML
    void userButtonController(ActionEvent event) {
        this.guiUsersController.openPaneWithUserInformation(this);
    }

    void setGuiUsersController(GuiUsersController guiUsersController) {
        this.guiUsersController = guiUsersController;
    }

    void setName(String name) {
        this.nameLabel.setText(name);
    }

    void setType(String type) {
        this.typeLabel.setText(type);
    }

    void setMatricleOrStaffNumber(String matricleOrStaffNumber) {
        this.matricleOrStaffNumberLabel.setText(matricleOrStaffNumber);
    }

    void setMatricleOrStaffNumberText(String matricleOrStaffNumber) {
        this.matricleOrStaffNumberText.setText(matricleOrStaffNumber);
    }

    String getType() {
        return this.typeLabel.getText();
    }

    String getMatricleOrStaffNumber() {
        return this.matricleOrStaffNumberLabel.getText();
    }
}
