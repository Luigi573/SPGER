package mx.uv.fei.gui.javafiles.guiuserscontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class ModifyUserInformationController {

    private GuiUsersController guiUsersController;

    @FXML
    private TextField alternateEmailTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField firstSurnameTextField;

    @FXML
    private Button modifyButton;

    @FXML
    private TextField namesTextField;

    @FXML
    private TextField secondSurnameTextField;

    @FXML
    private ComboBox<String> statusComboBox;

    @FXML
    private TextField telephoneNumberTextField;

    @FXML
    private ComboBox<String> typeComboBox;

    @FXML
    void initialize(){
        
    }

    @FXML
    void modifyButtonController(ActionEvent event) {

    }

    public String getAlternateEmail() {
        return this.alternateEmailTextField.getText();
    }

    public void setAlternateEmail(String alternateEmail) {
        this.alternateEmailTextField.setText(alternateEmail);
    }

    public String getEmail() {
        return this.emailTextField.getText();
    }

    public void setEmail(String email) {
        this.emailTextField.setText(email);
    }

    public String getFirstSurname() {
        return this.firstSurnameTextField.getText();
    }

    public void setFirstSurname(String firstSurname) {
        this.firstSurnameTextField.setText(firstSurname);
    }

    public String getNames() {
        return this.namesTextField.getText();
    }

    public void setNames(String names) {
        this.namesTextField.setText(names);
    }

    public String getSecondSurname() {
        return this.secondSurnameTextField.getText();
    }

    public void setSecondSurname(String secondSurname) {
        this.secondSurnameTextField.setText(secondSurname);
    }

    public String getStatus() {
        return this.statusComboBox.getItems().get(0);
    }

    public void setStatus(String status) {
        this.statusComboBox.setValue(status);
    }

    public String getTelephoneNumber() {
        return this.telephoneNumberTextField.getText();
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumberTextField.setText(telephoneNumber);
    }

    public String getType() {
        return this.typeComboBox.getItems().get(0);
    }

    public void setType(String type) {
        this.typeComboBox.setValue(type);
    }

    public void setGuiUsersController(GuiUsersController guiUsersController){
        this.guiUsersController = guiUsersController;
    }

}
