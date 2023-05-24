package mx.uv.fei.gui.controllers.users;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import mx.uv.fei.logic.domain.UserType;

public class UserInformationController{
    private GuiUsersController guiUsersController;
    private UserController userController;

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

    @FXML
    private void editButtonController(ActionEvent event){
        guiUsersController.openModifyUserPane(this);
    }

    public String getAlternateEmail(){
        return alternateEmailLabel.getText();
    }
    public void setAlternateEmail(String alternateEmail){
        alternateEmailLabel.setText(alternateEmail);
    }
    public String getEmail(){
        return emailLabel.getText();
    }
    public void setEmail(String email){
        emailLabel.setText(email);
    }
    public String getFirstSurname(){
        return firstSurnameLabel.getText();
    }
    public void setFirstSurname(String firstSurname){
        firstSurnameLabel.setText(firstSurname);
    }
    public String getNames(){
        return namesLabel.getText();
    }
    public void setNames(String names){
        namesLabel.setText(names);
    }
    public String getSecondSurname(){
        return secondSurnameLabel.getText();
    }
    public void setSecondSurname(String secondSurname){
        secondSurnameLabel.setText(secondSurname);
    }
    public String getStatus(){
        return statusLabel.getText();
    }
    public void setStatus(String status){
        statusLabel.setText(status);
    }
    public String getTelephoneNumber(){
        return telephoneNumberLabel.getText();
    }
    public void setTelephoneNumber(String telephoneNumber){
        telephoneNumberLabel.setText(telephoneNumber);
    }
    public String getUserType(){
        return userTypeLabel.getText();
    }
    public void setUserType(String userType){
        userTypeLabel.setText(userType);
    }
    public String getMatriculeOrPersonalNumber(){
        return matricleOrPersonalNumberLabel.getText();
    }
    public void setMatricleOrPersonalNumber(String type){
        matricleOrPersonalNumberLabel.setText(type);
    }
    public void setGuiUsersController(GuiUsersController guiUsersController){
        this.guiUsersController = guiUsersController;
    }
    public UserController getUserController(){
        return userController;
    }
    public void setUserController(UserController userController){
        this.userController = userController;
    }
    public void setMatricleOrPersonalNumberText(){
        if(userTypeLabel.getText().equals(UserType.STUDENT.getValue())){
            matricleOrPersonalNumberText.setText("Matrícula: ");
            matricleOrPersonalNumberText.setPrefWidth(71);
            matricleOrPersonalNumberLabel.setPrefWidth(282);
        } else {
            matricleOrPersonalNumberText.setText("Número de Personal: ");
            matricleOrPersonalNumberText.setPrefWidth(141);
            matricleOrPersonalNumberLabel.setPrefWidth(213);
        }
    }
}