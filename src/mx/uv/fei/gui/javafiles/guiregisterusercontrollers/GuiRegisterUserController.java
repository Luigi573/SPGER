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
    private ChoiceBox<String> statusChoiceBox;

    @FXML
    private TextField telephoneNumberTextField;

    @FXML
    private ChoiceBox<String> typeChoiceBox;

    @FXML
    void initialize() {
        this.typeChoiceBox.getItems().add("Estudiante");
        this.typeChoiceBox.getItems().add("Profesor");
        this.typeChoiceBox.getItems().add("Director");
        this.typeChoiceBox.getItems().add("Miembro de Cuerpo Académico");
        this.typeChoiceBox.getItems().add("Jefe de Carrera");
        this.typeChoiceBox.setValue("Estudiante");

        this.statusChoiceBox.getItems().add("Activo");
        this.statusChoiceBox.getItems().add("Egresado");
        this.statusChoiceBox.getItems().add("Inactivo");
        this.statusChoiceBox.setValue("Activo");
    }

    @FXML
    void registerButtonController(ActionEvent event) {
        if(!this.namesTextField.getText().trim().isEmpty() &&
           !this.firstSurnameTextField.getText().trim().isEmpty() &&
           !this.secondSurnameTextField.getText().trim().isEmpty() &&
           !this.emailTextField.getText().trim().isEmpty() &&
           !this.alternateEmailTextField.getText().trim().isEmpty() &&
           !this.telephoneNumberTextField.getText().trim().isEmpty()){
            System.out.println("No Está vacío");
        } else {
            System.out.println("Está vacío");
        }
    }

}
