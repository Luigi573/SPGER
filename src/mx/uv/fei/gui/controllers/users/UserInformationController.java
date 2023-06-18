package mx.uv.fei.gui.controllers.users;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import mx.uv.fei.logic.EmailMaker;
import mx.uv.fei.logic.domain.User;
import mx.uv.fei.logic.domain.UserType;

public class UserInformationController{
    private GuiUsersController guiUsersController;
    private String userPassword;
    private UserController userController;
    private User user;

    @FXML
    private Label alternateEmailLabel;
    @FXML
    private Button editButton;
    @FXML
    private Label emailLabel;
    @FXML
    private Label firstSurnameLabel;
    @FXML
    private Label matricleOrStaffNumberLabel;
    @FXML
    private Label matricleOrStaffNumberText;
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
    @FXML
    private void sendEmailWithItsPasswordButtonController(ActionEvent event){
        new EmailMaker().sendPassword(getEmail(), getUserPassword());
        new EmailMaker().sendPassword(getAlternateEmail(), getUserPassword());
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
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public String getUserType(){
        return userTypeLabel.getText();
    }
    public void setUserType(String userType){
        userTypeLabel.setText(userType);
    }
    public String getUserPassword() {
        return userPassword;
    }
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
    public String getMatricleOrStaffNumber(){
        return matricleOrStaffNumberLabel.getText();
    }
    public void setMatricleOrStaffNumber(String type){
        matricleOrStaffNumberLabel.setText(type);
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
    public void setMatricleOrStaffNumberText(){
        if(userTypeLabel.getText().equals(UserType.STUDENT.getValue())){
            matricleOrStaffNumberText.setText("Matrícula: ");
            matricleOrStaffNumberText.setPrefWidth(71);
            matricleOrStaffNumberLabel.setPrefWidth(282);
        } else {
            matricleOrStaffNumberText.setText("Número de Personal: ");
            matricleOrStaffNumberText.setPrefWidth(141);
            matricleOrStaffNumberLabel.setPrefWidth(213);
        }
    }
}