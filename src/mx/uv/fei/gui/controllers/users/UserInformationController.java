package mx.uv.fei.gui.controllers.users;

import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import mx.uv.fei.gui.AlertPopUpGenerator;
import mx.uv.fei.logic.PasswordAndEmailMaker;
import mx.uv.fei.logic.daos.UserDAO;
import mx.uv.fei.logic.domain.User;
import mx.uv.fei.logic.domain.UserType;
import mx.uv.fei.logic.exceptions.DataInsertionException;

public class UserInformationController{
    private GuiUsersController guiUsersController;
    private String userPassword;
    private UserController userController;
    private User user;
    private User headerUser;

    @FXML
    private Label alternateEmailLabel;
    @FXML
    private DialogPane dialogPane;
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
        String userPassword = new PasswordAndEmailMaker().securePasswordMaker();
        try{
            Alert confirmationMessage = new Alert(Alert.AlertType.CONFIRMATION);        
            confirmationMessage.setHeaderText("Mandar Correo");
            confirmationMessage.setContentText("¿Está seguro de que quiere generar una contraseña para el usuario seleccionado y mandarsela por correo?");

            dialogPane = confirmationMessage.getDialogPane();
            String css = getClass().getResource("/mx/uv/fei/gui/stylesfiles/Styles.css").toExternalForm();
            dialogPane.getStylesheets().add(css);
            dialogPane.getStyleClass().add("dialog");

            Optional<ButtonType> choice = confirmationMessage.showAndWait();
            if(choice.isPresent() && choice.get() == ButtonType.OK){
                new UserDAO().updatePassword(userPassword, user.getUserId());
                new PasswordAndEmailMaker().sendPassword(getEmail(), userPassword);
                new PasswordAndEmailMaker().sendPassword(getAlternateEmail(), userPassword);
                new AlertPopUpGenerator().showCustomMessage(AlertType.INFORMATION, "Éxito", "Correo enviado exitosamente");
            }
        }catch(DataInsertionException e){
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }
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
    public User getHeaderUser() {
        return headerUser;
    }
    public void setHeaderUser(User headerUser) {
        this.headerUser = headerUser;
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
    public User getUser(){
        return user;
    }
    public void setUser(User user){
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