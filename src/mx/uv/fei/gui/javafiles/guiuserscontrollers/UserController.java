package mx.uv.fei.gui.javafiles.guiuserscontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

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
        
    }

    void setGuiUsersController(GuiUsersController guiUsersController){
        this.guiUsersController = guiUsersController;
    }

    void setName(String name){
        this.name.setText(name);
    }

}
