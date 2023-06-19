package mx.uv.fei.gui.controllers.users;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.text.WordUtils;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mx.uv.fei.gui.AlertPopUpGenerator;
import mx.uv.fei.logic.PasswordAndEmailMaker;
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
import mx.uv.fei.logic.domain.UserType;
import mx.uv.fei.logic.domain.statuses.ProfessorStatus;
import mx.uv.fei.logic.domain.statuses.StudentStatus;
import mx.uv.fei.logic.exceptions.DataInsertionException;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.exceptions.DuplicatedPrimaryKeyException;

public class GuiRegisterUserController{
    private GuiUsersController guiUsersController;

    @FXML
    private TextField alternateEmailTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField firstSurnameTextField;
    @FXML
    private Text matricleOrStaffNumberText;
    @FXML
    private TextField matricleOrStaffNumberTextField;
    @FXML
    private TextField namesTextField;
    @FXML
    private Button registerButton;
    @FXML
    private TextField secondSurnameTextField;
    @FXML
    private TextField telephoneNumberTextField;
    @FXML
    private ComboBox<String> typeComboBox;

    @FXML
    private void initialize(){
        typeComboBox.getItems().add(UserType.STUDENT.getValue());
        typeComboBox.getItems().add(UserType.PROFESSOR.getValue());
        typeComboBox.getItems().add(UserType.DIRECTOR.getValue());
        typeComboBox.getItems().add(UserType.ACADEMIC_BODY_HEAD.getValue());
        typeComboBox.getItems().add(UserType.DEGREE_BOSS.getValue());
        typeComboBox.setValue(UserType.STUDENT.getValue());
    }
    @FXML
    private void registerButtonController(ActionEvent event){
        if(allTextFieldsContainsData()){
            if(specifiedInvalidDataMessageError().equals("")){
                if(!emailTextField.getText().equals(alternateEmailTextField.getText())){
                    if(specifiedDuplicatedEmailsMessageError(emailTextField.getText(), alternateEmailTextField.getText()).equals("")){
                        if(typeComboBox.getValue().equals(UserType.DIRECTOR.getValue())){
                            registerDirector();
                        }
                    
                        if(typeComboBox.getValue().equals(UserType.ACADEMIC_BODY_HEAD.getValue())){
                            registerAcademicBodyHead();
                        }
                    
                        if(typeComboBox.getValue().equals(UserType.DEGREE_BOSS.getValue())){
                            registerDegreeBoss();
                        }
                    
                        if(typeComboBox.getValue().equals(UserType.PROFESSOR.getValue())){
                            registerProfessor();
                        }
                    
                        if(typeComboBox.getValue().equals(UserType.STUDENT.getValue())){
                            registerStudent();
                        }
                        guiUsersController.loadUserButtons();
                        Stage stage = (Stage) registerButton.getScene().getWindow();
                        stage.close();
                    }else{
                        new AlertPopUpGenerator().showCustomMessage(AlertType.INFORMATION, "Error", specifiedDuplicatedEmailsMessageError(emailTextField.getText(), alternateEmailTextField.getText()));
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
    @FXML
    private void typeComboBoxController(ActionEvent event){
        if(typeComboBox.getValue().equals(UserType.STUDENT.getValue())){
            matricleOrStaffNumberText.setText("Matrícula: *");
            matricleOrStaffNumberTextField.setPrefWidth(295);
            matricleOrStaffNumberTextField.setLayoutX(487);
        }else{
            matricleOrStaffNumberText.setText("Número de Personal: *");
            matricleOrStaffNumberTextField.setPrefWidth(211);
            matricleOrStaffNumberTextField.setLayoutX(571);        
        }
    }

    public void setGuiUsersController(GuiUsersController guiUsersController) {
        this.guiUsersController = guiUsersController;
    }

    private void registerDirector(){
        try {
            DirectorDAO directorDAO = new DirectorDAO();
            Director director = new Director();
            director.setName(WordUtils.capitalize(namesTextField.getText().toLowerCase()));
            director.setFirstSurname(WordUtils.capitalize(firstSurnameTextField.getText().toLowerCase()));
            director.setSecondSurname(WordUtils.capitalize(secondSurnameTextField.getText().toLowerCase()));
            director.setEmailAddress(emailTextField.getText());
            director.setAlternateEmail(alternateEmailTextField.getText());
            director.setPhoneNumber(telephoneNumberTextField.getText());
            director.setPassword(new PasswordAndEmailMaker().securePasswordMaker());
            director.setStatus(ProfessorStatus.ACTIVE.getValue());
            director.setStaffNumber(Integer.parseInt(matricleOrStaffNumberTextField.getText()));
            directorDAO.addDirector(director);
            new PasswordAndEmailMaker().sendPassword(director.getEmailAddress(), director.getPassword());
            new PasswordAndEmailMaker().sendPassword(director.getAlternateEmail(), director.getPassword());
            new AlertPopUpGenerator().showCustomMessage(AlertType.INFORMATION, "Éxito", "Director registrado exitosamente"); 
        }catch(DataInsertionException e){
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }catch(DuplicatedPrimaryKeyException e) {
            new AlertPopUpGenerator().showCustomMessage(AlertType.ERROR, "Error", "El número de personal ya está usado");
        }
    }
    private void registerAcademicBodyHead(){
        try {
            AcademicBodyHeadDAO academicBodyHeadDAO = new AcademicBodyHeadDAO();
            AcademicBodyHead academicBodyHead = new AcademicBodyHead();
            academicBodyHead.setName(WordUtils.capitalize(namesTextField.getText().toLowerCase()));
            academicBodyHead.setFirstSurname(WordUtils.capitalize(firstSurnameTextField.getText().toLowerCase()));
            academicBodyHead.setSecondSurname(WordUtils.capitalize(secondSurnameTextField.getText().toLowerCase()));
            academicBodyHead.setEmailAddress(emailTextField.getText());
            academicBodyHead.setAlternateEmail(alternateEmailTextField.getText());
            academicBodyHead.setPhoneNumber(telephoneNumberTextField.getText());
            academicBodyHead.setPassword(new PasswordAndEmailMaker().securePasswordMaker());
            academicBodyHead.setStatus(ProfessorStatus.ACTIVE.getValue());
            academicBodyHead.setStaffNumber(Integer.parseInt(matricleOrStaffNumberTextField.getText()));
            academicBodyHeadDAO.addAcademicBodyHead(academicBodyHead);
            new PasswordAndEmailMaker().sendPassword(academicBodyHead.getEmailAddress(), academicBodyHead.getPassword());
            new PasswordAndEmailMaker().sendPassword(academicBodyHead.getAlternateEmail(), academicBodyHead.getPassword());
            new AlertPopUpGenerator().showCustomMessage(AlertType.INFORMATION, "Éxito", "Miembro de Cuerpo Académico registrado exitosamente");
        }catch(DataInsertionException e){
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }catch(DuplicatedPrimaryKeyException e) {
            new AlertPopUpGenerator().showCustomMessage(AlertType.ERROR, "Error", "El número de personal ya está usado");
        }
    }
    private void registerDegreeBoss(){
        try {
            DegreeBossDAO degreeBossDAO = new DegreeBossDAO();
            DegreeBoss degreeBoss = new DegreeBoss();
            degreeBoss.setName(WordUtils.capitalize(namesTextField.getText().toLowerCase()));
            degreeBoss.setFirstSurname(WordUtils.capitalize(firstSurnameTextField.getText().toLowerCase()));
            degreeBoss.setSecondSurname(WordUtils.capitalize(secondSurnameTextField.getText().toLowerCase()));
            degreeBoss.setEmailAddress(emailTextField.getText());
            degreeBoss.setAlternateEmail(alternateEmailTextField.getText());
            degreeBoss.setPhoneNumber(telephoneNumberTextField.getText());
            degreeBoss.setPassword(new PasswordAndEmailMaker().securePasswordMaker());
            degreeBoss.setStatus(ProfessorStatus.ACTIVE.getValue());
            degreeBoss.setStaffNumber(Integer.parseInt(matricleOrStaffNumberTextField.getText()));
            degreeBossDAO.addDegreeBoss(degreeBoss);
            new PasswordAndEmailMaker().sendPassword(degreeBoss.getEmailAddress(), degreeBoss.getPassword());
            new PasswordAndEmailMaker().sendPassword(degreeBoss.getAlternateEmail(), degreeBoss.getPassword());
            new AlertPopUpGenerator().showCustomMessage(AlertType.INFORMATION, "Éxito", "Jefe de Carrera registrado exitosamente");
        }catch(DataInsertionException e){
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }catch(DuplicatedPrimaryKeyException e) {
            new AlertPopUpGenerator().showCustomMessage(AlertType.ERROR, "Error", "El número de personal ya está usado");
        }
        
    }
    private void registerProfessor(){
        try {
            ProfessorDAO professorDAO = new ProfessorDAO();
            Professor professor = new Professor();
            professor.setName(WordUtils.capitalize(namesTextField.getText().toLowerCase()));
            professor.setFirstSurname(WordUtils.capitalize(firstSurnameTextField.getText().toLowerCase()));
            professor.setSecondSurname(WordUtils.capitalize(secondSurnameTextField.getText().toLowerCase()));
            professor.setEmailAddress(emailTextField.getText());
            professor.setAlternateEmail(alternateEmailTextField.getText());
            professor.setPhoneNumber(telephoneNumberTextField.getText());
            professor.setPassword(new PasswordAndEmailMaker().securePasswordMaker());
            professor.setStatus(ProfessorStatus.ACTIVE.getValue());
            professor.setStaffNumber(Integer.parseInt(matricleOrStaffNumberTextField.getText()));
            professorDAO.addProfessor(professor);
            new PasswordAndEmailMaker().sendPassword(professor.getEmailAddress(), professor.getPassword());
            new PasswordAndEmailMaker().sendPassword(professor.getAlternateEmail(), professor.getPassword());
            new AlertPopUpGenerator().showCustomMessage(AlertType.INFORMATION, "Éxito", "Profesor registrado exitosamente");
        }catch(DataInsertionException e){
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }catch(DuplicatedPrimaryKeyException e){
            new AlertPopUpGenerator().showCustomMessage(AlertType.ERROR, "Error", "El número de personal ya está usado");
        }
    }
    private void registerStudent(){
        try{
            StudentDAO studentDAO = new StudentDAO();
            Student student = new Student();
            student.setName(WordUtils.capitalize(namesTextField.getText().toLowerCase()));
            student.setFirstSurname(WordUtils.capitalize(firstSurnameTextField.getText().toLowerCase()));
            student.setSecondSurname(WordUtils.capitalize(secondSurnameTextField.getText().toLowerCase()));
            student.setEmailAddress(emailTextField.getText());
            student.setAlternateEmail(alternateEmailTextField.getText());
            student.setPhoneNumber(telephoneNumberTextField.getText());
            student.setPassword(new PasswordAndEmailMaker().securePasswordMaker());
            student.setStatus(StudentStatus.AVAILABLE.getValue());
            student.setMatricle(matricleOrStaffNumberTextField.getText());
            studentDAO.addStudent(student);
            new PasswordAndEmailMaker().sendPassword(student.getEmailAddress(), student.getPassword());
            new PasswordAndEmailMaker().sendPassword(student.getAlternateEmail(), student.getPassword());
            new AlertPopUpGenerator().showCustomMessage(AlertType.INFORMATION, "Éxito", "Estudiante registrado exitosamente");
        }catch(DataInsertionException e) {
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }catch(DuplicatedPrimaryKeyException e) {
            new AlertPopUpGenerator().showCustomMessage(AlertType.ERROR, "Error", "La Matrícula ya está usada");
        }
    }
    private boolean allTextFieldsContainsData(){
        return !namesTextField.getText().trim().isEmpty() && !firstSurnameTextField.getText().trim().isEmpty() &&
               !secondSurnameTextField.getText().trim().isEmpty() && !emailTextField.getText().trim().isEmpty() &&
               !alternateEmailTextField.getText().trim().isEmpty() && !telephoneNumberTextField.getText().trim().isEmpty() &&
               !matricleOrStaffNumberTextField.getText().trim().isEmpty();
    }
    private String specifiedInvalidDataMessageError(){
        String message = "";

        Pattern namesPattern = Pattern.compile("^[A-Za-zÁÉÍÓÚÜÑáéíóúüñ\\s]+(?: [A-Za-zÁÉÍÓÚÜÑáéíóúüñ\\s]+)*$"),
                firstSurnamePattern = Pattern.compile("^[A-Za-zÁÉÍÓÚÜÑáéíóúüñ\\s]+(?: [A-Za-zÁÉÍÓÚÜÑáéíóúüñ\\s]+)*$"),
                secondSurnamePattern = Pattern.compile("^[A-Za-zÁÉÍÓÚÜÑáéíóúüñ\\s]+(?: [A-Za-zÁÉÍÓÚÜÑáéíóúüñ\\s]+)*$"),
                emailPattern = Pattern.compile("^(.+)@uv.mx$"),
                alternateEmailPattern = Pattern.compile("^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$"),
                telephoneNumberPattern = Pattern.compile("^[0-9]{10}$"),
                matricleOrStaffNumberPattern;
    
        if(typeComboBox.getValue().equals(UserType.STUDENT.getValue())){
            matricleOrStaffNumberPattern = Pattern.compile("^[z][S][0-9]{8}$");
        }else{
            matricleOrStaffNumberPattern = Pattern.compile("^[0-9]{1,}$");
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
    private String specifiedDuplicatedEmailsMessageError(String email, String alternateEmail){
        String message = "";
        UserDAO userDAO = new UserDAO();
        try{
            if(!userDAO.theEmailIsAvailableToUseToRegister(email)){
                if(message.equals("")){
                    message = "correo electrónico";
                }else{
                    message = message + " y el correo electrónico";
                }
            }

            if(!userDAO.theAlternateEmailIsAvailableToRegister(alternateEmail)){
                if(message.equals("")){
                    message = "correo alterno";
                }else{
                    message = message + "y el correo alterno";
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
}