package mx.uv.fei.gui.controllers.users;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
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
import mx.uv.fei.logic.domain.UserType;
import mx.uv.fei.logic.domain.statuses.ProfessorStatus;
import mx.uv.fei.logic.domain.statuses.StudentStatus;
import mx.uv.fei.logic.exceptions.DataInsertionException;
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
        if(!namesTextField.getText().trim().isEmpty() &&
           !firstSurnameTextField.getText().trim().isEmpty() &&
           !secondSurnameTextField.getText().trim().isEmpty() &&
           !emailTextField.getText().trim().isEmpty() &&
           !alternateEmailTextField.getText().trim().isEmpty() &&
           !telephoneNumberTextField.getText().trim().isEmpty() &&
           !matricleOrStaffNumberTextField.getText().trim().isEmpty()){
            if(allTextFieldsContainsCorrectValues()){
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
                new AlertPopUpGenerator().showCustomMessage(AlertType.WARNING, "Error", "Algunos campos contienen datos inválidos");
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
        } else {
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
            director.setName(namesTextField.getText());
            director.setFirstSurname(firstSurnameTextField.getText());
            director.setSecondSurname(secondSurnameTextField.getText());
            director.setEmailAddress(emailTextField.getText());
            director.setAlternateEmail(alternateEmailTextField.getText());
            director.setPhoneNumber(telephoneNumberTextField.getText());
            director.setStatus(ProfessorStatus.ACTIVE.getValue());
            director.setStaffNumber(Integer.parseInt(matricleOrStaffNumberTextField.getText()));
            directorDAO.addDirector (director);
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
            academicBodyHead.setName(namesTextField.getText());
            academicBodyHead.setFirstSurname(firstSurnameTextField.getText());
            academicBodyHead.setSecondSurname(secondSurnameTextField.getText());
            academicBodyHead.setEmailAddress(emailTextField.getText());
            academicBodyHead.setAlternateEmail(alternateEmailTextField.getText());
            academicBodyHead.setPhoneNumber(telephoneNumberTextField.getText());
            academicBodyHead.setStatus(ProfessorStatus.ACTIVE.getValue());
            academicBodyHead.setStaffNumber(Integer.parseInt(matricleOrStaffNumberTextField.getText()));
            academicBodyHeadDAO.addAcademicBodyHead(academicBodyHead);
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
            degreeBoss.setName(namesTextField.getText());
            degreeBoss.setFirstSurname(firstSurnameTextField.getText());
            degreeBoss.setSecondSurname(secondSurnameTextField.getText());
            degreeBoss.setEmailAddress(emailTextField.getText());
            degreeBoss.setAlternateEmail(alternateEmailTextField.getText());
            degreeBoss.setPhoneNumber(telephoneNumberTextField.getText());
            degreeBoss.setStatus(ProfessorStatus.ACTIVE.getValue());
            degreeBoss.setStaffNumber(Integer.parseInt(matricleOrStaffNumberTextField.getText()));
            degreeBossDAO.addDegreeBoss(degreeBoss);
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
            professor.setName(namesTextField.getText());
            professor.setFirstSurname(firstSurnameTextField.getText());
            professor.setSecondSurname(secondSurnameTextField.getText());
            professor.setEmailAddress(emailTextField.getText());
            professor.setAlternateEmail(alternateEmailTextField.getText());
            professor.setPhoneNumber(telephoneNumberTextField.getText());
            professor.setStatus(ProfessorStatus.ACTIVE.getValue());
            professor.setStaffNumber(Integer.parseInt(matricleOrStaffNumberTextField.getText()));
            professorDAO.addProfessor(professor);
            new AlertPopUpGenerator().showCustomMessage(AlertType.INFORMATION, "Éxito", "Profesor registrado exitosamente");
        }catch(DataInsertionException e){
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }catch(DuplicatedPrimaryKeyException e) {
            new AlertPopUpGenerator().showCustomMessage(AlertType.ERROR, "Error", "El número de personal ya está usado");
        }
    }
    private void registerStudent(){
        try {
            StudentDAO studentDAO = new StudentDAO();
            Student student = new Student();
            student.setName(namesTextField.getText());
            student.setFirstSurname(firstSurnameTextField.getText());
            student.setSecondSurname(secondSurnameTextField.getText());
            student.setEmailAddress(emailTextField.getText());
            student.setAlternateEmail(alternateEmailTextField.getText());
            student.setPhoneNumber(telephoneNumberTextField.getText());
            student.setStatus(StudentStatus.AVAILABLE.getValue());
            student.setMatricle(matricleOrStaffNumberTextField.getText());
            studentDAO.addStudent (student);
            new AlertPopUpGenerator().showCustomMessage(AlertType.INFORMATION, "Éxito", "Estudiante registrado exitosamente");
        }catch(DataInsertionException e) {
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }catch(DuplicatedPrimaryKeyException e) {
            new AlertPopUpGenerator().showCustomMessage(AlertType.ERROR, "Error", "La Matrícula ya está usada");
        }
    }
    private boolean allTextFieldsContainsCorrectValues(){
        Pattern namesPattern = Pattern.compile("([A-Z][a-z]+)\\s?([A-Z][a-z]+)?\\s?([A-Z][a-z]+)?\\s?([A-Z][a-z]+)?"),
                firstSurnamePattern = Pattern.compile("([A-Z][a-z]+)\\s?([A-Z][a-z]+)?\\s?([A-Z][a-z]+)?\\s?([A-Z][a-z]+)?"),
                secondSurnamePattern = Pattern.compile("([A-Z][a-z]+)\\s?([A-Z][a-z]+)?\\s?([A-Z][a-z]+)?\\s?([A-Z][a-z]+)?"),
                emailPattern = Pattern.compile("^(.+)@uv.mx$"),
                alternateEmailPattern = Pattern.compile("^(.+)@uv.mx$"),
                telephoneNumberPattern = Pattern.compile("^[0-9]{10}$"),
                matricleOrStaffNumberPattern = Pattern.compile("");
    
        if(typeComboBox.getValue().equals(UserType.STUDENT.getValue())){
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

        if(namesMatcher.find() && firstSurnameMatcher.find() &&
           secondSurnameMatcher.find() && emailMatcher.find() &&
           alternateEmailMatcher.find() && telephoneNumberMatcher.find() &&
           matricleOrStaffNumberMatcher.find()){
            return true;
        }

        return false;
    }
}