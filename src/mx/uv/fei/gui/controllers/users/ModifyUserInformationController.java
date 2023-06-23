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
import mx.uv.fei.logic.daos.UserDAO;
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
    private User headerUser;
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
        returnToGuiUsers(event, headerUser);
    }
    @FXML
    private void modifyButtonController(ActionEvent event){
        if(allTextFieldsContainsData()){
            if(specifiedInvalidDataMessageError().equals("")){
                if(!emailTextField.getText().equals(alternateEmailTextField.getText())){
                    if(specifiedDuplicatedEmailsMessageError(emailTextField.getText(), alternateEmailTextField.getText(), userToModify.getUserId()).equals("")){
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
                        new AlertPopUpGenerator().showCustomMessage(AlertType.INFORMATION, "Error", specifiedDuplicatedEmailsMessageError(emailTextField.getText(), alternateEmailTextField.getText(), userToModify.getUserId()));
                    }
                }else{
                    new AlertPopUpGenerator().showCustomMessage(AlertType.WARNING, "Error", "El correo electrónico no puede ser el mismo que el correo alterno");
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
    public User getHeaderUser(){
        return headerUser;
    }
    public void setHeaderUser(User headerUser){
        this.headerUser = headerUser;
    }
    public User getUserToModify(){
        return userToModify;
    }
    public void setUserToModify(User userToModify){
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
            returnToGuiUsers(event, headerUser);
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
            returnToGuiUsers(event, headerUser);
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
            returnToGuiUsers(event, headerUser);
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
            returnToGuiUsers(event, headerUser);
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
            if(headerUser.getUserId() == userToModify.getUserId()){
                headerUser = degreeBossDAO.getDegreeBoss(Integer.parseInt(getMatricleOrStaffNumber()));
            }
            new AlertPopUpGenerator().showCustomMessage(AlertType.INFORMATION, "Éxito", "Jefe de Carrera modificado exitosamente");
            returnToGuiUsers(event, headerUser);
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
        String message = "";

        Pattern namesPattern = Pattern.compile("([A-Za-zÁÉÍÓÚÜÑáéíóúüñ\\\\s]+(?: [A-Za-zÁÉÍÓÚÜÑáéíóúüñ\\\\s]+)*){1,30}$"),
                firstSurnamePattern = Pattern.compile("([A-Za-zÁÉÍÓÚÜÑáéíóúüñ\\\\s]+(?: [A-Za-zÁÉÍÓÚÜÑáéíóúüñ\\\\s]+)*){1,30}$"),
                secondSurnamePattern = Pattern.compile("([A-Za-zÁÉÍÓÚÜÑáéíóúüñ\\\\s]+(?: [A-Za-zÁÉÍÓÚÜÑáéíóúüñ\\\\s]+)*){1,30}$"),
                alternateEmailPattern = Pattern.compile("^(?=.{1,50}$)[\\\\w.%+-]+@[\\\\w.-]+\\\\.[a-zA-Z]{2,}$"),
                telephoneNumberPattern = Pattern.compile("^[0-9]{10}$"),
                matricleOrStaffNumberPattern, emailPattern;
    
        if(userInformationController.getUserType().equals(UserType.STUDENT.getValue())){
            matricleOrStaffNumberPattern = Pattern.compile("^[z][S][0-9]{8}$");
            emailPattern = Pattern.compile("^(?=.{1,50}$)(.+)@estudiantes\\.uv\\.mx$");
        }else{
            matricleOrStaffNumberPattern = Pattern.compile("^[0-9]{1,9}$");
            emailPattern = Pattern.compile("^(?=.{1,50}$)(.+)@uv\\.mx$");
        }

        Matcher namesMatcher = namesPattern.matcher(namesTextField.getText()),
                firstSurnameMatcher = firstSurnamePattern.matcher(firstSurnameTextField.getText()),
                secondSurnameMatcher = secondSurnamePattern.matcher(secondSurnameTextField.getText()),
                emailMatcher = emailPattern.matcher(emailTextField.getText()),
                alternateEmailMatcher = alternateEmailPattern.matcher(alternateEmailTextField.getText()),
                telephoneNumberMatcher = telephoneNumberPattern.matcher(telephoneNumberTextField.getText()),
                matricleOrStaffNumberMatcher = matricleOrStaffNumberPattern.matcher(matricleOrStaffNumberTextField.getText());

        if(!namesMatcher.find()){
            if(message.equals("")){
                message = "nombre";
            }else{
                message = message + ", nombre";
            }
        }

        if(!firstSurnameMatcher.find()){
            if(message.equals("")){
                message = "apellido paterno";
            }else{
                message = message + ", apellido paterno";
            }
        }

        if(!secondSurnameMatcher.find()){
            if(message.equals("")){
                message = "apellido materno";
            }else{
                message = message + ", apellido materno";
            }
        }

        if(!emailMatcher.find()){
            if(message.equals("")){
                message = "correo electrónico";
            }else{
                message = message + ", correo electrónico";
            }
        }

        if(!alternateEmailMatcher.find()){
            if(message.equals("")){
                message = "correo alterno";
            }else{
                message = message + ", correo alterno";
            }
        }

        if(!telephoneNumberMatcher.find()){
            if(message.equals("")){
                message = "número de teléfono";
            }else{
                message = message + ", número de teléfono";
            }
        }

        if(!matricleOrStaffNumberMatcher.find()){
            if(message.equals("")){
                message = "matrícula o número de personal";
            }else{
                message = message + ", matrícula o número de personal";
            }
        }

        if(!message.equals("")){
            message = "Los campos que tienen datos inválidos son: " + message + ".";
        }

        return message;
    }
    private String specifiedDuplicatedEmailsMessageError(String email, String alternateEmail, int userId){
        String message = "";
        UserDAO userDAO = new UserDAO();
        try{
            if(!userDAO.theEmailIsAvailableToUseToModify(email, userId)){
                if(message.equals("")){
                    message = "correo electrónico";
                }else{
                    message = message + " y el correo electrónico";
                }
            }

            if(!userDAO.theAlternateEmailIsAvailableToModify(alternateEmail, userId)){
                if(message.equals("")){
                    message = "correo alterno";
                }else{
                    message = message + " y el correo alterno";
                }
            }
        }catch(DataRetrievalException e){
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }

        if(!message.equals("")){
            message = "El " + message + " ya están usados.";
        }

        return message;
    }
    private void returnToGuiUsers(ActionEvent event, User headerUser){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/users/GuiUsers.fxml"));
        
        try{
            if(headerUser != null){
                Parent parent = loader.load();
                GuiUsersController controller = (GuiUsersController)loader.getController();
                controller.setHeaderUser(headerUser);
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