package mx.uv.fei.gui.controllers.users;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
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
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.exceptions.DataWritingException;

public class GuiRegisterUserController{
    private GuiUsersController guiUsersController;

    @FXML
    private TextField alternateEmailTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private Text errorMessajeText;
    @FXML
    private TextField firstSurnameTextField;
    @FXML
    private Text matricleOrPersonalNumberText;
    @FXML
    private TextField matricleOrPersonalNumberTextField;
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
           !matricleOrPersonalNumberTextField.getText().trim().isEmpty()){
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

                new AlertPopUpGenerator().showCustomMessage(AlertType.WARNING, "Éxito", "Usuario registrado exitosamente");
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
            matricleOrPersonalNumberText.setText("Matrícula: ");
            matricleOrPersonalNumberTextField.setPrefWidth(303);
            matricleOrPersonalNumberTextField.setLayoutX(479);
        } else {
            matricleOrPersonalNumberText.setText("Número de Personal: ");
            matricleOrPersonalNumberTextField.setPrefWidth(219);
            matricleOrPersonalNumberTextField.setLayoutX(563);        
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
            director.setStaffNumber(Integer.parseInt(matricleOrPersonalNumberTextField.getText()));
            if(directorDAO.theDirectorIsAlreadyRegisted(director)){
                new AlertPopUpGenerator().showCustomMessage(AlertType.WARNING, "Error", "El usuario ya está registrado en el sistema");
                return;
            }
            directorDAO.addDirectorToDatabase(director);
        }catch(DataRetrievalException e){
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }catch(DataWritingException e){
            new AlertPopUpGenerator().showConnectionErrorMessage();
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
            academicBodyHead.setStaffNumber(Integer.parseInt(matricleOrPersonalNumberTextField.getText()));
            if(academicBodyHeadDAO.theAcademicBodyHeadIsAlreadyRegisted(academicBodyHead)){
                new AlertPopUpGenerator().showCustomMessage(AlertType.WARNING, "Error", "El usuario ya está registrado en el sistema");
                return;
            }
            academicBodyHeadDAO.addAcademicBodyHeadToDatabase(academicBodyHead);
        }catch(DataRetrievalException e){
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }catch(DataWritingException e){
            new AlertPopUpGenerator().showConnectionErrorMessage();
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
            degreeBoss.setStaffNumber(Integer.parseInt(matricleOrPersonalNumberTextField.getText()));
            if(degreeBossDAO.theDegreeBossIsAlreadyRegisted(degreeBoss)){
                new AlertPopUpGenerator().showCustomMessage(AlertType.WARNING, "Error", "El usuario ya está registrado en el sistema");
                return;
            }
            degreeBossDAO.addDegreeBossToDatabase(degreeBoss);
        }catch(DataRetrievalException e){
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }catch(DataWritingException e){
            new AlertPopUpGenerator().showConnectionErrorMessage();
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
            professor.setStaffNumber(Integer.parseInt(matricleOrPersonalNumberTextField.getText()));
            if(professorDAO.theProfessorIsAlreadyRegisted(professor)){
                new AlertPopUpGenerator().showCustomMessage(AlertType.WARNING, "Error", "El usuario ya está registrado en el sistema");
                return;
            }
            professorDAO.addProfessorToDatabase(professor);
        }catch(DataRetrievalException e){
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }catch(DataWritingException e){
            new AlertPopUpGenerator().showConnectionErrorMessage();
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
            student.setMatricle(matricleOrPersonalNumberTextField.getText());
            if(studentDAO.theStudentIsAlreadyRegisted(student)){
                new AlertPopUpGenerator().showCustomMessage(AlertType.WARNING, "Error", "El usuario ya está registrado en el sistema");
                return;
            }
            studentDAO.addStudentToDatabase(student);
        }catch(DataRetrievalException e){
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }catch(DataWritingException e){
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }
    }
    private boolean allTextFieldsContainsCorrectValues(){
        Pattern namesPattern = Pattern.compile("([A-Z][a-z]+)\\s?([A-Z][a-z]+)?\\s?([A-Z][a-z]+)?\\s?([A-Z][a-z]+)?"),
                firstSurnamePattern = Pattern.compile("([A-Z][a-z]+)\\s?([A-Z][a-z]+)?\\s?([A-Z][a-z]+)?\\s?([A-Z][a-z]+)?"),
                secondSurnamePattern = Pattern.compile("([A-Z][a-z]+)\\s?([A-Z][a-z]+)?\\s?([A-Z][a-z]+)?\\s?([A-Z][a-z]+)?"),
                emailPattern = Pattern.compile("^(.+)@(\\S+)$"),
                alternateEmailPattern = Pattern.compile("^(.+)@(\\S+)$"),
                telephoneNumberPattern = Pattern.compile("^[0-9]{10}$"),
                matricleOrPersonalNumberPattern = Pattern.compile("");
    
        if(typeComboBox.getValue().equals(UserType.STUDENT.getValue())){
            matricleOrPersonalNumberPattern = Pattern.compile("^[z][S][0-9]{8}$");
        }else{
            matricleOrPersonalNumberPattern = Pattern.compile("^[0-9]{9}$");
        }

        Matcher namesMatcher = namesPattern.matcher(namesTextField.getText()),
                firstSurnameMatcher = firstSurnamePattern.matcher(firstSurnameTextField.getText()),
                secondSurnameMatcher = secondSurnamePattern.matcher(secondSurnameTextField.getText()),
                emailMatcher = emailPattern.matcher(emailTextField.getText()),
                alternateEmailMatcher = alternateEmailPattern.matcher(alternateEmailTextField.getText()),
                telephoneNumberMatcher = telephoneNumberPattern.matcher(telephoneNumberTextField.getText()),
                matricleOrPersonalNumberMatcher = matricleOrPersonalNumberPattern.matcher(matricleOrPersonalNumberTextField.getText());

        if(namesMatcher.find() && firstSurnameMatcher.find() &&
           secondSurnameMatcher.find() && emailMatcher.find() &&
           alternateEmailMatcher.find() && telephoneNumberMatcher.find() &&
           matricleOrPersonalNumberMatcher.find()){
            return true;
        }

        return false;
    }
}