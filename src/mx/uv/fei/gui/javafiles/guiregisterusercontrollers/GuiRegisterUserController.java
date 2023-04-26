package mx.uv.fei.gui.javafiles.guiregisterusercontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class GuiRegisterUserController {

    @FXML
    private TextField alternateEmailTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField firstSurnameTextField;

    @FXML
    private TextField namesTextField;

    @FXML
    private Button registerButton;

    @FXML
    private TextField secondSurnameTextField;

    @FXML
    private ChoiceBox<?> statusChoiceBox;

    @FXML
    private TextField telephoneNumberTextField;

    @FXML
    private ChoiceBox<?> typeChoiceBox;

    @FXML
    void registerButtonController(ActionEvent event) {

    }

}
