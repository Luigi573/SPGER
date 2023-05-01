package mx.uv.fei.gui.javafiles.guiuserscontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import mx.uv.fei.logic.domain.Professor;

public class UserController {
    GuiUsersController guiUsersController;

    @FXML
    private Label name;

    @FXML
    private Label status;

    @FXML
    private Label type;

    @FXML
    private Button userButton;

    @FXML
    void userButtonController(ActionEvent event) {
        this.guiUsersController.openPaneWithUserInformation(
            this.name.getText(),
            this.type.getText(),
            this.status.getText()
        );
    }

    void setGuiUsersController(GuiUsersController guiUsersController){
        this.guiUsersController = guiUsersController;
    }

    void setName(String name){
        this.name.setText(name);
    }

    void setStatus(String status){
        this.status.setText(status);
    }

    void setType(String type){
        this.type.setText(type);
    }

}
