package mx.uv.fei.gui.controllers.users;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class UserController {
    GuiUsersController guiUsersController;

    @FXML
    private Label matricleOrPersonalNumberLabel;
    @FXML
    private Label matricleOrPersonalNumberText;
    @FXML
    private Label nameLabel;
    @FXML
    private Label typeLabel;
    @FXML
    private Pane userPane;
    @FXML
    private Button userButton;

    @FXML
    private void userButtonController(ActionEvent event) {
        this.guiUsersController.openPaneWithUserInformation(this);
    }

    public void setGuiUsersController(GuiUsersController guiUsersController) {
        this.guiUsersController = guiUsersController;
    }

    public void setName(String name) {
        this.nameLabel.setText(name);
    }

    public void setType(String type) {
        this.typeLabel.setText(type);
    }

    public void setMatricleOrPersonalNumber(String matricleOrPersonalNumber) {
        this.matricleOrPersonalNumberLabel.setText(matricleOrPersonalNumber);
    }

    public void setMatricleOrPersonalNumberText(String matricleOrPersonalNumber) {
        this.matricleOrPersonalNumberText.setText(matricleOrPersonalNumber);
    }

    public String getType() {
        return this.typeLabel.getText();
    }

    public String getMatriculeOrPersonalNumber() {
        return this.matricleOrPersonalNumberLabel.getText();
    }

    public void setLabelsCorrectBounds(String userType) {
        if(userType.equals("Estudiante")) {
            matricleOrPersonalNumberText.setPrefWidth(72);
            matricleOrPersonalNumberText.setLayoutX(10);
            matricleOrPersonalNumberLabel.setPrefWidth(373);
            matricleOrPersonalNumberLabel.setLayoutX(81);
        } else {
            matricleOrPersonalNumberText.setPrefWidth(143);
            matricleOrPersonalNumberText.setLayoutX(10);
            matricleOrPersonalNumberLabel.setPrefWidth(303);
            matricleOrPersonalNumberLabel.setLayoutX(151);
        }
    }
}
