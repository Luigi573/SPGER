package mx.uv.fei.gui.controllers.users;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.text.WordUtils;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mx.uv.fei.gui.AlertPopUpGenerator;
import mx.uv.fei.logic.daos.AcademicBodyHeadDAO;
import mx.uv.fei.logic.daos.DegreeBossDAO;
import mx.uv.fei.logic.daos.DirectorDAO;
import mx.uv.fei.logic.daos.ProfessorDAO;
import mx.uv.fei.logic.daos.StudentDAO;
import mx.uv.fei.logic.domain.AcademicBodyHead;
import mx.uv.fei.logic.domain.DegreeBoss;
import mx.uv.fei.logic.domain.Director;
import mx.uv.fei.logic.domain.Professor;
import mx.uv.fei.logic.domain.Student;
import mx.uv.fei.logic.domain.User;
import mx.uv.fei.logic.domain.UserType;
import mx.uv.fei.logic.domain.statuses.ProfessorStatus;
import mx.uv.fei.logic.domain.statuses.StudentStatus;
import mx.uv.fei.logic.exceptions.DataInsertionException;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.exceptions.DuplicatedPrimaryKeyException;

public class ModifyUserInformationController{
    private UserInformationController userInformationController;
    private User user;
    private User userToModify;

    @FXML
    private TextField alternateEmailTextField;
    @FXML
    private DialogPane dialogPane;
    @FXML
    private TextField emailTextField;
    @FXML
    private Button exitButton;
    @FXML
    private TextField firstSurnameTextField;
    @FXML
    private Label matricleOrStaffNumberText;
    @FXML
    private TextField matricleOrStaffNumberTextField;
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
    private void exitButtonController(ActionEvent event){
        returnToGuiUsers(event, user);
    }
    @FXML
    private void modifyButtonController(ActionEvent event){
        if(allTextFieldsContainsData()){
            if(specifiedInvalidDataMessageError().equals("")){
                if(userInformationController.getUserType().equals(UserType.DIRECTOR.getValue())){
                    modifyDirector(event);
                }

                if(userInformationController.getUserType().equals(UserType.ACADEMIC_BODY_HEAD.getValue())){
                    modifyAcademicBodyHead(event);
                }

                if(userInformationController.getUserType().equals(UserType.DEGREE_BOSS.getValue())){
                    modifyDegreeBoss(event);
                }

                if(userInformationController.getUserType().equals(UserType.PROFESSOR.getValue())){
                    modifyProfessor(event);
                }

                if(userInformationController.getUserType().equals(UserType.STUDENT.getValue())){
                    modifyStudent(event);
                }
            }else{
                new AlertPopUpGenerator().showCustomMessage(AlertType.WARNING, "Error", specifiedInvalidDataMessageError());
            }
        }else{
            new AlertPopUpGenerator().showCustomMessage(AlertType.WARNING, "Error", "Faltan campos por llenar");
        }
    }

    public String getAlternateEmail(){
        return alternateEmailTextField.getText();
    }
    public void setAlternateEmail(String alternateEmail){
        alternateEmailTextField.setText(alternateEmail);
    }
    public String getEmail(){
        return emailTextField.getText();
    }
    public void setEmail(String email){
        emailTextField.setText(email);
    }
    public String getFirstSurname(){
        return firstSurnameTextField.getText();
    }
    public void setFirstSurname(String firstSurname){
        firstSurnameTextField.setText(firstSurname);
    }
    public String getMatricleOrStaffNumber(){
        return matricleOrStaffNumberTextField.getText();
    }
    public void setMatricleOrStaffNumber(String matricleOrStaffNumber){
        matricleOrStaffNumberTextField.setText(matricleOrStaffNumber);
    }
    public String getNames(){
        return namesTextField.getText();
    }
    public void setNames(String names){
        namesTextField.setText(names);
    }
    public String getSecondSurname(){
        return secondSurnameTextField.getText();
    }
    public void setSecondSurname(String secondSurname){
        secondSurnameTextField.setText(secondSurname);
    }
    public String getStatus(){
        return statusComboBox.getItems().get(0);
    }
    public void setStatus(String status){
        statusComboBox.setValue(status);
    }
    public String getTelephoneNumber(){
        return telephoneNumberTextField.getText();
    }
    public void setTelephoneNumber(String telephoneNumber){
        telephoneNumberTextField.setText(telephoneNumber);
    }
    public void setUserInformationController(UserInformationController userInformationController){
        this.userInformationController = userInformationController;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public User getUserToModify() {
        return userToModify;
    }
    public void setUserToModify(User userToModify) {
        this.userToModify = userToModify;
    }
    public void setDataToStatusCombobox(String userType){
        if(userType.equals(UserType.STUDENT.getValue())){
            statusComboBox.getItems().add(StudentStatus.ACTIVE.getValue());
            statusComboBox.getItems().add(StudentStatus.AVAILABLE.getValue());
            statusComboBox.getItems().add(StudentStatus.GRADUATED.getValue());
            statusComboBox.getItems().add(StudentStatus.DROPPED.getValue());
        }else{
            statusComboBox.getItems().add(ProfessorStatus.ACTIVE.getValue());
            statusComboBox.getItems().add(ProfessorStatus.INACTIVE.getValue());
        }
    }
    public void setLabelsCorrectBounds(String userType){
        if(userType.equals(UserType.STUDENT.getValue())){
            matricleOrStaffNumberText.setText("Matrícula: *");
            matricleOrStaffNumberText.setPrefWidth(79);
            matricleOrStaffNumberText.setLayoutX(0);
            matricleOrStaffNumberText.setLayoutY(0);
            matricleOrStaffNumberTextField.setPrefWidth(351);
            matricleOrStaffNumberTextField.setLayoutX(1);
            matricleOrStaffNumberTextField.setLayoutY(7);
        }else{
            matricleOrStaffNumberText.setText("Número de Personal: *");
            matricleOrStaffNumberText.setPrefWidth(153);
            matricleOrStaffNumberText.setLayoutX(0);
            matricleOrStaffNumberText.setLayoutY(0);
            matricleOrStaffNumberTextField.setPrefWidth(278);
            matricleOrStaffNumberTextField.setLayoutX(1);
            matricleOrStaffNumberTextField.setLayoutY(7);
        }
    }

    private void modifyStudent(ActionEvent event){
        StudentDAO studentDAO = new StudentDAO();
        Student student = (Student)userToModify;
        student.setName(WordUtils.capitalize(namesTextField.getText().toLowerCase()));
        student.setFirstSurname(WordUtils.capitalize(firstSurnameTextField.getText().toLowerCase()));
        student.setSecondSurname(WordUtils.capitalize(secondSurnameTextField.getText().toLowerCase()));
        student.setEmailAddress(emailTextField.getText());
        student.setAlternateEmail(alternateEmailTextField.getText());
        student.setPhoneNumber(telephoneNumberTextField.getText());
        student.setStatus(statusComboBox.getValue());
        student.setMatricle(matricleOrStaffNumberTextField.getText());

        try{
            studentDAO.modifyStudentData(student);
            new AlertPopUpGenerator().showCustomMessage(AlertType.INFORMATION, "Éxito", "Estudiante modificado exitosamente");
            returnToGuiUsers(event, user);
        }catch(DataInsertionException e){
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }catch(DuplicatedPrimaryKeyException e) {
            new AlertPopUpGenerator().showCustomMessage(AlertType.ERROR, "Error", "La Matrícula ya está usada");
        }
    }
    private void modifyProfessor(ActionEvent event){
        ProfessorDAO professorDAO = new ProfessorDAO();
        Professor professor = (Professor)userToModify;
        professor.setName(WordUtils.capitalize(namesTextField.getText().toLowerCase()));
        professor.setFirstSurname(WordUtils.capitalize(firstSurnameTextField.getText().toLowerCase()));
        professor.setSecondSurname(WordUtils.capitalize(secondSurnameTextField.getText().toLowerCase()));
        professor.setEmailAddress(emailTextField.getText());
        professor.setAlternateEmail(alternateEmailTextField.getText());
        professor.setPhoneNumber(telephoneNumberTextField.getText());
        professor.setStatus(statusComboBox.getValue());
        professor.setStaffNumber(Integer.parseInt(matricleOrStaffNumberTextField.getText()));

        try{
            professorDAO.modifyProfessorData(professor);
            new AlertPopUpGenerator().showCustomMessage(AlertType.INFORMATION, "Éxito", "Profesor modificado exitosamente");
            returnToGuiUsers(event, user);
        }catch(DataInsertionException e){
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }catch(DuplicatedPrimaryKeyException e) {
            new AlertPopUpGenerator().showCustomMessage(AlertType.ERROR, "Error", "El número de personal ya está usado");
        }
    }
    private void modifyDirector(ActionEvent event){
        DirectorDAO directorDAO = new DirectorDAO();
        Director director = (Director)userToModify;
        director.setName(WordUtils.capitalize(namesTextField.getText().toLowerCase()));
        director.setFirstSurname(WordUtils.capitalize(firstSurnameTextField.getText().toLowerCase()));
        director.setSecondSurname(WordUtils.capitalize(secondSurnameTextField.getText().toLowerCase()));
        director.setEmailAddress(emailTextField.getText());
        director.setAlternateEmail(alternateEmailTextField.getText());
        director.setPhoneNumber(telephoneNumberTextField.getText());
        director.setStatus(statusComboBox.getValue());
        director.setStaffNumber(Integer.parseInt(matricleOrStaffNumberTextField.getText()));

        try{
            directorDAO.modifyDirectorData(director);
            new AlertPopUpGenerator().showCustomMessage(AlertType.INFORMATION, "Éxito", "Director modificado exitosamente");
            returnToGuiUsers(event, user);
        }catch(DataInsertionException e){
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }catch(DuplicatedPrimaryKeyException e) {
            new AlertPopUpGenerator().showCustomMessage(AlertType.ERROR, "Error", "El número de personal ya está usado");
        }
    }
    private void modifyAcademicBodyHead(ActionEvent event){
        AcademicBodyHeadDAO academicBodyHeadDAO = new AcademicBodyHeadDAO();
        AcademicBodyHead academicBodyHead = (AcademicBodyHead)userToModify;
        academicBodyHead.setName(WordUtils.capitalize(namesTextField.getText().toLowerCase()));
        academicBodyHead.setFirstSurname(WordUtils.capitalize(firstSurnameTextField.getText().toLowerCase()));
        academicBodyHead.setSecondSurname(WordUtils.capitalize(secondSurnameTextField.getText().toLowerCase()));
        academicBodyHead.setEmailAddress(emailTextField.getText());
        academicBodyHead.setAlternateEmail(alternateEmailTextField.getText());
        academicBodyHead.setPhoneNumber(telephoneNumberTextField.getText());
        academicBodyHead.setStatus(statusComboBox.getValue());
        academicBodyHead.setStaffNumber(Integer.parseInt(matricleOrStaffNumberTextField.getText()));

        try{
            academicBodyHeadDAO.modifyAcademicBodyHeadData(academicBodyHead);
            new AlertPopUpGenerator().showCustomMessage(AlertType.INFORMATION, "Éxito", "Miembro de Cuerpo Académico modificado exitosamente");
            returnToGuiUsers(event, user);
        }catch(DataInsertionException e){
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }catch(DuplicatedPrimaryKeyException e) {
            new AlertPopUpGenerator().showCustomMessage(AlertType.ERROR, "Error", "El número de personal ya está usado");
        }
    }
    private void modifyDegreeBoss(ActionEvent event){
        DegreeBossDAO degreeBossDAO = new DegreeBossDAO();
        DegreeBoss degreeBoss = (DegreeBoss)userToModify;
        degreeBoss.setName(WordUtils.capitalize(namesTextField.getText().toLowerCase()));
        degreeBoss.setFirstSurname(WordUtils.capitalize(firstSurnameTextField.getText().toLowerCase()));
        degreeBoss.setSecondSurname(WordUtils.capitalize(secondSurnameTextField.getText().toLowerCase()));
        degreeBoss.setEmailAddress(emailTextField.getText());
        degreeBoss.setAlternateEmail(alternateEmailTextField.getText());
        degreeBoss.setPhoneNumber(telephoneNumberTextField.getText());
        degreeBoss.setStatus(statusComboBox.getValue());
        degreeBoss.setStaffNumber(Integer.parseInt(matricleOrStaffNumberTextField.getText()));

        try{
            degreeBossDAO.modifyDegreeBossData(degreeBoss);
            if(user.getUserId() == userToModify.getUserId()){
                user = degreeBossDAO.getDegreeBoss(Integer.parseInt(getMatricleOrStaffNumber()));
            }
            new AlertPopUpGenerator().showCustomMessage(AlertType.INFORMATION, "Éxito", "Jefe de Carrera modificado exitosamente");
            returnToGuiUsers(event, user);
        }catch(DataInsertionException | DataRetrievalException e){
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }catch(DuplicatedPrimaryKeyException e) {
            new AlertPopUpGenerator().showCustomMessage(AlertType.ERROR, "Error", "El número de personal ya está usado");
        }
    }
    private boolean allTextFieldsContainsData(){
        return !namesTextField.getText().trim().isEmpty() && !firstSurnameTextField.getText().trim().isEmpty() &&
               !secondSurnameTextField.getText().trim().isEmpty() && !emailTextField.getText().trim().isEmpty() &&
               !alternateEmailTextField.getText().trim().isEmpty() && !telephoneNumberTextField.getText().trim().isEmpty() &&
               !matricleOrStaffNumberTextField.getText().trim().isEmpty() && statusComboBox.getValue() != null;
    }
    private String specifiedInvalidDataMessageError(){
        Pattern namesPattern = Pattern.compile("^[A-Za-zÁÉÍÓÚÜÑáéíóúüñ]+(?: [A-Za-zÁÉÍÓÚÜÑáéíóúüñ]+)*$"),
                firstSurnamePattern = Pattern.compile("^[A-Za-zÁÉÍÓÚÜÑáéíóúüñ]+(?: [A-Za-zÁÉÍÓÚÜÑáéíóúüñ]+)*$"),
                secondSurnamePattern = Pattern.compile("^[A-Za-zÁÉÍÓÚÜÑáéíóúüñ]+(?: [A-Za-zÁÉÍÓÚÜÑáéíóúüñ]+)*$"),
                emailPattern = Pattern.compile("^(.+)@uv.mx$"),
                alternateEmailPattern = Pattern.compile("^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$"),
                telephoneNumberPattern = Pattern.compile("^[0-9]{10}$"),
                matricleOrStaffNumberPattern;
    
        if(userInformationController.getUserType().equals(UserType.STUDENT.getValue())){
            matricleOrStaffNumberPattern = Pattern.compile("^[z][S][0-9]{8}$");
        }else{
            matricleOrStaffNumberPattern = Pattern.compile("^[0-9]{9}$");
        }

        Matcher namesMatcher = namesPattern.matcher(namesTextField.getText()),
                firstSurnameMatcher = firstSurnamePattern.matcher(firstSurnameTextField.getText()),
                secondSurnameMatcher = secondSurnamePattern.matcher(secondSurnameTextField.getText()),
                emailMatcher = emailPattern.matcher(emailTextField.getText()),
                alternateEmailMatcher = alternateEmailPattern.matcher(alternateEmailTextField.getText()),
                telephoneNumberMatcher = telephoneNumberPattern.matcher(telephoneNumberTextField.getText()),
                matricleOrStaffNumberMatcher = matricleOrStaffNumberPattern.matcher(matricleOrStaffNumberTextField.getText());

        if(!namesMatcher.find()){
            return "El campo para introducir el nombre contiene datos inválidos";
        }

        if(!firstSurnameMatcher.find()){
            return "El campo para introducir el apellido paterno contiene datos inválidos";
        }

        if(!secondSurnameMatcher.find()){
            return "El campo para introducir el apellido materno contiene datos inválidos";
        }

        if(!emailMatcher.find()){
            return "El campo para introducir el correo electrónico contiene datos inválidos";
        }

        if(!alternateEmailMatcher.find()){
            return "El campo para introducir el correo alterno contiene datos inválidos";
        }

        if(!telephoneNumberMatcher.find()){
            return "El campo para introducir el número de telefono contiene datos inválidos";
        }

        if(!matricleOrStaffNumberMatcher.find()){
            return "El campo para introducir la matrícula o el numero de personal contiene datos inválidos";
        }

        return "";
    }
    private void returnToGuiUsers(ActionEvent event, User user){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/users/GuiUsers.fxml"));
        
        try{
            if(user != null){
                Parent parent = loader.load();
                GuiUsersController controller = (GuiUsersController)loader.getController();
                controller.setUser(user);
                controller.loadHeader();
                
                Scene scene = new Scene(parent);
                String css = this.getClass().getResource("/mx/uv/fei/gui/stylesfiles/Styles.css").toExternalForm();
                scene.getStylesheets().add(css);
                
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setResizable(false);

                stage.show();
            }
        }catch(IOException exception){
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }
    }
}