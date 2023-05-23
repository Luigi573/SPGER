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
import mx.uv.fei.gui.controllers.AlertPaneController;
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
import mx.uv.fei.logic.domain.statuses.ProfessorStatus;
import mx.uv.fei.logic.domain.statuses.StudentStatus;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.exceptions.DataWritingException;

public class GuiRegisterUserController {

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
    void initialize() {
        typeComboBox.getItems().add("Estudiante");
        typeComboBox.getItems().add("Profesor");
        typeComboBox.getItems().add("Director");
        typeComboBox.getItems().add("Miembro de Cuerpo Académico");
        typeComboBox.getItems().add("Jefe de Carrera");
        typeComboBox.setValue("Estudiante");
    }

    @FXML
    void registerButtonController(ActionEvent event) {
        if(!namesTextField.getText().trim().isEmpty() &&
           !firstSurnameTextField.getText().trim().isEmpty() &&
           !secondSurnameTextField.getText().trim().isEmpty() &&
           !emailTextField.getText().trim().isEmpty() &&
           !alternateEmailTextField.getText().trim().isEmpty() &&
           !telephoneNumberTextField.getText().trim().isEmpty() &&
           !matricleOrPersonalNumberTextField.getText().trim().isEmpty()) {

            if(allTextFieldsContainsCorrectValues()) {
                switch(typeComboBox.getValue()) {
                    case "Director": {
                        registerDirector();
                        break;
                    }
        
                    case "Miembro de Cuerpo Académico": {
                        registerAcademicBodyHead();
                        break;
                    }
        
                    case "Jefe de Carrera": {
                        registerDegreeBoss();
                        break;
                    }
        
                    case "Profesor": {
                        registerProfessor();
                        break;
                    }
        
                    case "Estudiante": {
                        registerStudent();
                        break;
                    }
                }

                AlertPopUpGenerator alertPopUpGenerator = new AlertPopUpGenerator();
                alertPopUpGenerator.showCustomMessage(AlertType.WARNING, "Éxito", "Usuario registrado exitosamente");

                guiUsersController.loadUserButtons();

                Stage stage = (Stage) registerButton.getScene().getWindow();
                stage.close();

            } else {
                AlertPopUpGenerator alertPopUpGenerator = new AlertPopUpGenerator();
                alertPopUpGenerator.showCustomMessage(AlertType.WARNING, "Error", "Algunos campos contienen datos inválidos");
            }

        } else {
            AlertPopUpGenerator alertPopUpGenerator = new AlertPopUpGenerator();
            alertPopUpGenerator.showCustomMessage(AlertType.WARNING, "Error", "Faltan campos por llenar");
        }
    }

    @FXML
    void typeComboBoxController(ActionEvent event) {
        if(typeComboBox.getValue().equals("Estudiante")){
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

    private void registerDirector() {
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
            if(directorDAO.theDirectorIsAlreadyRegisted(director)) {
                AlertPaneController alertPaneController = new AlertPaneController();
                alertPaneController.openWarningPane("El usuario ya está registrado en el sistema");
                return;
            }
            directorDAO.addDirectorToDatabase(director);
        } catch (DataRetrievalException e) {
            e.printStackTrace();
            AlertPaneController alertPaneController = new AlertPaneController();
            alertPaneController.openErrorPane("Hubo un error, inténtelo más tarde");
        } catch (DataWritingException e) {
            e.printStackTrace();
            AlertPaneController alertPaneController = new AlertPaneController();
            alertPaneController.openErrorPane("Hubo un error, inténtelo más tarde");
        }
    }

    private void registerAcademicBodyHead() {
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
                AlertPaneController alertPaneController = new AlertPaneController();
                alertPaneController.openWarningPane("El usuario ya está registrado en el sistema");
                return;
            }
            academicBodyHeadDAO.addAcademicBodyHeadToDatabase(academicBodyHead);
        } catch (DataRetrievalException e) {
            e.printStackTrace();
            AlertPaneController alertPaneController = new AlertPaneController();
            alertPaneController.openErrorPane("Hubo un error, inténtelo más tarde");
        } catch (DataWritingException e) {
            e.printStackTrace();
            AlertPaneController alertPaneController = new AlertPaneController();
            alertPaneController.openErrorPane("Hubo un error, inténtelo más tarde");
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
                if(degreeBossDAO.theDegreeBossIsAlreadyRegisted(degreeBoss)) {
                    AlertPaneController alertPaneController = new AlertPaneController();
                    alertPaneController.openWarningPane("El usuario ya está registrado en el sistema");
                    return;
                }
                degreeBossDAO.addDegreeBossToDatabase(degreeBoss);
        } catch (DataRetrievalException e) {
            e.printStackTrace();
            AlertPaneController alertPaneController = new AlertPaneController();
            alertPaneController.openErrorPane("Hubo un error, inténtelo más tarde");
        } catch (DataWritingException e) {
            e.printStackTrace();
            AlertPaneController alertPaneController = new AlertPaneController();
            alertPaneController.openErrorPane("Hubo un error, inténtelo más tarde");
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
                AlertPaneController alertPaneController = new AlertPaneController();
                alertPaneController.openWarningPane("El usuario ya está registrado en el sistema");
                return;
            }
            professorDAO.addProfessorToDatabase(professor);
        } catch (DataRetrievalException e) {
            e.printStackTrace();
            AlertPaneController alertPaneController = new AlertPaneController();
            alertPaneController.openErrorPane("Hubo un error, inténtelo más tarde");
        } catch (DataWritingException e) {
            e.printStackTrace();
            AlertPaneController alertPaneController = new AlertPaneController();
            alertPaneController.openErrorPane("Hubo un error, inténtelo más tarde");
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
            if(studentDAO.theStudentIsAlreadyRegisted(student)) {
                AlertPaneController alertPaneController = new AlertPaneController();
                alertPaneController.openWarningPane("El usuario ya está registrado en el sistema");
                return;
            }
            studentDAO.addStudentToDatabase(student);
        } catch (DataRetrievalException e) {
            e.printStackTrace();
            AlertPaneController alertPaneController = new AlertPaneController();
            alertPaneController.openErrorPane("Hubo un error, inténtelo más tarde");
        } catch (DataWritingException e) {
            e.printStackTrace();
            AlertPaneController alertPaneController = new AlertPaneController();
            alertPaneController.openErrorPane("Hubo un error, inténtelo más tarde");
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
    
        switch(typeComboBox.getValue()){
            case "Director": {
                matricleOrPersonalNumberPattern = Pattern.compile("^[0-9]{9}$");
                break;
            }

            case "Miembro de Cuerpo Académico": {
                matricleOrPersonalNumberPattern = Pattern.compile("^[0-9]{9}$");
                break;
            }

            case "Jefe de Carrera": {
                matricleOrPersonalNumberPattern = Pattern.compile("^[0-9]{9}$");
                break;
            }

            case "Profesor": {
                matricleOrPersonalNumberPattern = Pattern.compile("^[0-9]{9}$");
                break;
            }

            case "Estudiante": {
                matricleOrPersonalNumberPattern = Pattern.compile("^[z][S][0-9]{8}$");
                break;
            }
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
