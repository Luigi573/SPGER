package mx.uv.fei.gui.controllers.users;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import mx.uv.fei.logic.domain.User;

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
    private Label matricleOrPersonalNumberLabel;

    @FXML
    private Label matricleOrPersonalNumberText;

    @FXML
    private Label namesLabel;

    @FXML
    private Label secondSurnameLabel;

    @FXML
    private Label statusLabel;

    @FXML
    private Label telephoneNumberLabel;

    @FXML
    private Label userTypeLabel;

    private GuiUsersController guiUsersController;

    private UserController userController;

    @FXML
    void editButtonController(ActionEvent event) {
        this.guiUsersController.openModifyUserPane(this);
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

    public String getUserType() {
        return this.userTypeLabel.getText();
    }

    public void setUserType(String userType) {
        this.userTypeLabel.setText(userType);
    }

    public String getMatriculeOrPersonalNumber() {
        return this.matricleOrPersonalNumberLabel.getText();
    }

    public void setMatricleOrPersonalNumber(String type) {
        this.matricleOrPersonalNumberLabel.setText(type);
    }

    public void setGuiUsersController(GuiUsersController guiUsersController){
        this.guiUsersController = guiUsersController;
    }

    public void setMatricleOrPersonalNumberText(){
        if(this.userTypeLabel.getText() == "Estudiante"){
            this.matricleOrPersonalNumberText.setText("Matrícula");
        } else {
            this.matricleOrPersonalNumberText.setText("Número de Personal");
        }
    }

}
