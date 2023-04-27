package mx.uv.fei.gui.javafiles.guiuserscontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class UserInformationController {

    @FXML
    private Label alternateEmailLabel;

    @FXML
    private Button editButton;

    @FXML
    private Label emailLabel;

    @FXML
    private Label firstSurnameLabel;

    @FXML
    private Label namesLabel;

    @FXML
    private Label secondSurnameLabel;

    @FXML
    private Label statusLabel;

    @FXML
    private Label telephoneNumberLabel;

    @FXML
    private Label typeLabel;

    private GuiUsersController guiUsersController;

    @FXML
    void editButtonController(ActionEvent event) {
        FXMLLoader userInformationControllerLoader = new FXMLLoader(
            getClass().getResource("/mx/uv/fei/gui/fxmlfiles/guiusers/ModifyUserInformation.fxml")
        );
        
    }

    public String getAlternateEmail() {
        return this.alternateEmailLabel.getText();
    }

    public void setAlternateEmail(String alternateEmail) {
        this.alternateEmailLabel.setText(alternateEmail);
    }

    public String getEmail() {
        return this.emailLabel.getText();
    }

    public void setEmail(String email) {
        this.emailLabel.setText(email);
    }

    public String getFirstSurname() {
        return this.firstSurnameLabel.getText();
    }

    public void setFirstSurname(String firstSurname) {
        this.firstSurnameLabel.setText(firstSurname);
    }

    public String getNames() {
        return this.namesLabel.getText();
    }

    public void setNames(String names) {
        this.namesLabel.setText(names);
    }

    public String getSecondSurname() {
        return this.secondSurnameLabel.getText();
    }

    public void setSecondSurname(String secondSurname) {
        this.secondSurnameLabel.setText(secondSurname);
    }

    public String getStatus() {
        return this.statusLabel.getText();
    }

    public void setStatus(String status) {
        this.statusLabel.setText(status);
    }

    public String getTelephoneNumber() {
        return this.telephoneNumberLabel.getText();
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumberLabel.setText(telephoneNumber);
    }

    public String getType() {
        return this.typeLabel.getText();
    }

    public void setType(String type) {
        this.typeLabel.setText(type);
    }

    public void setGuiUsersController(GuiUsersController guiUsersController){
        this.guiUsersController = guiUsersController;
    }
    

}
