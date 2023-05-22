package mx.uv.fei.gui.controllers.users;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;
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
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.exceptions.DataWritingException;

public class ModifyUserInformationController {
    
    private GuiUsersController guiUsersController;
    private UserInformationController userInformationController;

    @FXML
    private TextField alternateEmailTextField;
    @FXML
    private DialogPane dialogPane;
    @FXML
    private TextField emailTextField;
    @FXML
    private Text errorMessageText;
    @FXML
    private Button exitButton;
    @FXML
    private TextField firstSurnameTextField;
    @FXML
    private Label matricleOrPersonalNumberText;
    @FXML
    private TextField matricleOrPersonalNumberTextField;
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
    void initialize(){
        
    }

    @FXML
    void exitButtonController(ActionEvent event) {
        //this.guiUsersController.openPaneWithUserInformation(getFirstSurname(), getAlternateEmail(), getStatus());
    }

    @FXML
    void modifyButtonController(ActionEvent event) {
        if(!this.namesTextField.getText().trim().isEmpty() &&
           !this.firstSurnameTextField.getText().trim().isEmpty() &&
           !this.secondSurnameTextField.getText().trim().isEmpty() &&
           !this.emailTextField.getText().trim().isEmpty() &&
           !this.alternateEmailTextField.getText().trim().isEmpty() &&
           !this.telephoneNumberTextField.getText().trim().isEmpty() &&
           !this.matricleOrPersonalNumberTextField.getText().trim().isEmpty()) {
            if(allTextFieldsContainsCorrectValues()) {
                switch(this.userInformationController.getUserType()){
                    case "Director": {
                        this.modifyDirector();
                        break;
                    }
        
                    case "Miembro de Cuerpo Académico": {
                        this.modifyAcademicBodyHead();
                        break;
                    }
        
                    case "Jefe de Carrera": {
                        this.modifyDegreeBoss();
                        break;
                    }
        
                    case "Profesor": {
                        this.modifyProfessor();
                        break;
                    }
        
                    case "Estudiante": {
                        this.modifyStudent();
                        break;
                    }
                }

                AlertPopUpGenerator alertPopUpGenerator = new AlertPopUpGenerator();
                alertPopUpGenerator.showCustomMessage(AlertType.WARNING, "Éxito", "Usuario modificado exitosamente");

                this.guiUsersController.loadUserButtons();
            }
        } else {
            AlertPaneController alertPaneController = new AlertPaneController();
            alertPaneController.openWarningPane("Faltan campos por llenar");
        }
    }

    public String getAlternateEmail() {
        return this.alternateEmailTextField.getText();
    }

    public void setAlternateEmail(String alternateEmail) {
        this.alternateEmailTextField.setText(alternateEmail);
    }

    public String getEmail() {
        return this.emailTextField.getText();
    }

    public void setEmail(String email) {
        this.emailTextField.setText(email);
    }

    public String getFirstSurname() {
        return this.firstSurnameTextField.getText();
    }

    public void setFirstSurname(String firstSurname) {
        this.firstSurnameTextField.setText(firstSurname);
    }

    public String getMatriculeOrPersonalNumber() {
        return this.matricleOrPersonalNumberTextField.getText();
    }

    public void setMatricleOrPersonalNumber(String matricleOrPersonalNumber) {
        this.matricleOrPersonalNumberTextField.setText(matricleOrPersonalNumber);
    }

    public String getNames() {
        return this.namesTextField.getText();
    }

    public void setNames(String names) {
        this.namesTextField.setText(names);
    }

    public String getSecondSurname() {
        return this.secondSurnameTextField.getText();
    }

    public void setSecondSurname(String secondSurname) {
        this.secondSurnameTextField.setText(secondSurname);
    }

    public String getStatus() {
        return this.statusComboBox.getItems().get(0);
    }

    public void setStatus(String status) {
        this.statusComboBox.setValue(status);
    }

    public String getTelephoneNumber() {
        return this.telephoneNumberTextField.getText();
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumberTextField.setText(telephoneNumber);
    }

    public void setGuiUsersController(GuiUsersController guiUsersController) {
        this.guiUsersController = guiUsersController;
    }

    public void setUserInformationController(UserInformationController userInformationController){
        this.userInformationController = userInformationController;
    }

    private void modifyStudent() {
        StudentDAO studentDAO = new StudentDAO();
        Student newStudentData = new Student();
        newStudentData.setName(this.namesTextField.getText());
        newStudentData.setFirstSurname(this.firstSurnameTextField.getText());
        newStudentData.setSecondSurname(this.secondSurnameTextField.getText());
        newStudentData.setEmailAddress(this.emailTextField.getText());
        newStudentData.setAlternateEmail(this.alternateEmailTextField.getText());
        newStudentData.setPhoneNumber(this.telephoneNumberTextField.getText());
        newStudentData.setStatus(this.statusComboBox.getValue());
        newStudentData.setMatricle(this.matricleOrPersonalNumberTextField.getText());

        Student originalStudentData = new Student();
        originalStudentData.setName(this.getAllLabelsDataFromUserInformationPane().get(0));
        originalStudentData.setFirstSurname(this.getAllLabelsDataFromUserInformationPane().get(1));
        originalStudentData.setSecondSurname(this.getAllLabelsDataFromUserInformationPane().get(2));
        originalStudentData.setEmailAddress(this.getAllLabelsDataFromUserInformationPane().get(3));
        originalStudentData.setAlternateEmail(this.getAllLabelsDataFromUserInformationPane().get(4));
        originalStudentData.setPhoneNumber(this.getAllLabelsDataFromUserInformationPane().get(5));
        originalStudentData.setStatus(this.getAllLabelsDataFromUserInformationPane().get(6));
        originalStudentData.setMatricle(this.getAllLabelsDataFromUserInformationPane().get(7));

        try {
            if(studentDAO.theStudentIsAlreadyRegisted(newStudentData)) {
                AlertPaneController alertPaneController = new AlertPaneController();
                alertPaneController.openWarningPane("El usuario ya está registrado en el sistema");
                return;
            }
        } catch (DataRetrievalException e) {
            e.printStackTrace();
            AlertPaneController alertPaneController = new AlertPaneController();
            alertPaneController.openErrorPane("Hubo un error, inténtelo más tarde");
        }
        try {
            studentDAO.modifyStudentDataFromDatabase(newStudentData, originalStudentData);
            AlertPaneController alertPaneController = new AlertPaneController();
            alertPaneController.openWarningPane("Usuario modificado exitosamente");
        } catch (DataWritingException e) {
            e.printStackTrace();
            AlertPaneController alertPaneController = new AlertPaneController();
            alertPaneController.openErrorPane("Hubo un error, inténtelo más tarde");
        }
    }

    private void modifyProfessor(){
        ProfessorDAO professorDAO = new ProfessorDAO();
        Professor newProfessorData = new Professor();
        newProfessorData.setName(this.namesTextField.getText());
        newProfessorData.setFirstSurname(this.firstSurnameTextField.getText());
        newProfessorData.setSecondSurname(this.secondSurnameTextField.getText());
        newProfessorData.setEmailAddress(this.emailTextField.getText());
        newProfessorData.setAlternateEmail(this.alternateEmailTextField.getText());
        newProfessorData.setPhoneNumber(this.telephoneNumberTextField.getText());
        newProfessorData.setStatus(this.statusComboBox.getValue());
        newProfessorData.setStaffNumber(Integer.parseInt(this.matricleOrPersonalNumberTextField.getText()));

        Professor originalProfessorData = new Professor();
        originalProfessorData.setName(this.getAllLabelsDataFromUserInformationPane().get(0));
        originalProfessorData.setFirstSurname(this.getAllLabelsDataFromUserInformationPane().get(1));
        originalProfessorData.setSecondSurname(this.getAllLabelsDataFromUserInformationPane().get(2));
        originalProfessorData.setEmailAddress(this.getAllLabelsDataFromUserInformationPane().get(3));
        originalProfessorData.setAlternateEmail(this.getAllLabelsDataFromUserInformationPane().get(4));
        originalProfessorData.setPhoneNumber(this.getAllLabelsDataFromUserInformationPane().get(5));
        originalProfessorData.setStatus(this.getAllLabelsDataFromUserInformationPane().get(6));
        originalProfessorData.setStaffNumber(Integer.parseInt(this.getAllLabelsDataFromUserInformationPane().get(7)));
        try {
            if(professorDAO.theProfessorIsAlreadyRegisted(newProfessorData)){
                AlertPaneController alertPaneController = new AlertPaneController();
                alertPaneController.openWarningPane("El usuario ya está registrado en el sistema");
                return;
            }
        } catch (DataRetrievalException e) {
            e.printStackTrace();
            AlertPaneController alertPaneController = new AlertPaneController();
            alertPaneController.openErrorPane("Hubo un error, inténtelo más tarde");
        }

        try {
            professorDAO.modifyProfessorDataFromDatabase(newProfessorData, originalProfessorData);
            AlertPaneController alertPaneController = new AlertPaneController();
            alertPaneController.openWarningPane("Usuario modificado exitosamente");
        } catch (DataWritingException e) {
            e.printStackTrace();
            AlertPaneController alertPaneController = new AlertPaneController();
            alertPaneController.openErrorPane("Hubo un error, inténtelo más tarde");
        }
    }

    private void modifyDirector(){
        DirectorDAO directorDAO = new DirectorDAO();
        Director newDirectorData = new Director();
        newDirectorData.setName(this.namesTextField.getText());
        newDirectorData.setFirstSurname(this.firstSurnameTextField.getText());
        newDirectorData.setSecondSurname(this.secondSurnameTextField.getText());
        newDirectorData.setEmailAddress(this.emailTextField.getText());
        newDirectorData.setAlternateEmail(this.alternateEmailTextField.getText());
        newDirectorData.setPhoneNumber(this.telephoneNumberTextField.getText());
        newDirectorData.setStatus(this.statusComboBox.getValue());
        newDirectorData.setStaffNumber(Integer.parseInt(this.matricleOrPersonalNumberTextField.getText()));

        Director originalDirectorData = new Director();
        originalDirectorData.setName(this.getAllLabelsDataFromUserInformationPane().get(0));
        originalDirectorData.setFirstSurname(this.getAllLabelsDataFromUserInformationPane().get(1));
        originalDirectorData.setSecondSurname(this.getAllLabelsDataFromUserInformationPane().get(2));
        originalDirectorData.setEmailAddress(this.getAllLabelsDataFromUserInformationPane().get(3));
        originalDirectorData.setAlternateEmail(this.getAllLabelsDataFromUserInformationPane().get(4));
        originalDirectorData.setPhoneNumber(this.getAllLabelsDataFromUserInformationPane().get(5));
        originalDirectorData.setStatus(this.getAllLabelsDataFromUserInformationPane().get(6));
        originalDirectorData.setStaffNumber(Integer.parseInt(this.getAllLabelsDataFromUserInformationPane().get(7)));
        try {
            if(directorDAO.theDirectorIsAlreadyRegisted(newDirectorData)) {
                AlertPaneController alertPaneController = new AlertPaneController();
                alertPaneController.openWarningPane("El usuario ya está registrado en el sistema");
                return;
            }
        } catch (DataRetrievalException e) {
            e.printStackTrace();
            AlertPaneController alertPaneController = new AlertPaneController();
            alertPaneController.openErrorPane("Hubo un error, inténtelo más tarde");
        }
        try {
            directorDAO.modifyDirectorDataFromDatabase(newDirectorData, originalDirectorData);
            AlertPaneController alertPaneController = new AlertPaneController();
            alertPaneController.openWarningPane("Usuario modificado exitosamente");
        } catch (DataWritingException e) {
            e.printStackTrace();
            AlertPaneController alertPaneController = new AlertPaneController();
            alertPaneController.openErrorPane("Hubo un error, inténtelo más tarde");
        }
    }

    private void modifyAcademicBodyHead(){
        AcademicBodyHeadDAO academicBodyHeadDAO = new AcademicBodyHeadDAO();
        AcademicBodyHead newAcademicBodyHeadData = new AcademicBodyHead();
        newAcademicBodyHeadData.setName(this.namesTextField.getText());
        newAcademicBodyHeadData.setFirstSurname(this.firstSurnameTextField.getText());
        newAcademicBodyHeadData.setSecondSurname(this.secondSurnameTextField.getText());
        newAcademicBodyHeadData.setEmailAddress(this.emailTextField.getText());
        newAcademicBodyHeadData.setAlternateEmail(this.alternateEmailTextField.getText());
        newAcademicBodyHeadData.setPhoneNumber(this.telephoneNumberTextField.getText());
        newAcademicBodyHeadData.setStatus(this.statusComboBox.getValue());
        newAcademicBodyHeadData.setStaffNumber(Integer.parseInt(this.matricleOrPersonalNumberTextField.getText()));

        AcademicBodyHead originalAcademicBodyHeadData = new AcademicBodyHead();
        originalAcademicBodyHeadData.setName(this.getAllLabelsDataFromUserInformationPane().get(0));
        originalAcademicBodyHeadData.setFirstSurname(this.getAllLabelsDataFromUserInformationPane().get(1));
        originalAcademicBodyHeadData.setSecondSurname(this.getAllLabelsDataFromUserInformationPane().get(2));
        originalAcademicBodyHeadData.setEmailAddress(this.getAllLabelsDataFromUserInformationPane().get(3));
        originalAcademicBodyHeadData.setAlternateEmail(this.getAllLabelsDataFromUserInformationPane().get(4));
        originalAcademicBodyHeadData.setPhoneNumber(this.getAllLabelsDataFromUserInformationPane().get(5));
        originalAcademicBodyHeadData.setStatus(this.getAllLabelsDataFromUserInformationPane().get(6));
        originalAcademicBodyHeadData.setStaffNumber(Integer.parseInt(this.getAllLabelsDataFromUserInformationPane().get(7)));

        try {
            if(academicBodyHeadDAO.theAcademicBodyHeadIsAlreadyRegisted(newAcademicBodyHeadData)) {
                AlertPaneController alertPaneController = new AlertPaneController();
                alertPaneController.openWarningPane("El usuario ya está registrado en el sistema");
                return;
            }
        } catch (DataRetrievalException e) {
            e.printStackTrace();
            AlertPaneController alertPaneController = new AlertPaneController();
            alertPaneController.openErrorPane("Hubo un error, inténtelo más tarde");
        }
        try {
            academicBodyHeadDAO.modifyAcademicBodyHeadDataFromDatabase(newAcademicBodyHeadData, originalAcademicBodyHeadData);
            AlertPaneController alertPaneController = new AlertPaneController();
            alertPaneController.openWarningPane("Usuario modificado exitosamente");
        } catch (DataWritingException e) {
            e.printStackTrace();
            AlertPaneController alertPaneController = new AlertPaneController();
            alertPaneController.openErrorPane("Hubo un error, inténtelo más tarde");
        }
    }

    private void modifyDegreeBoss(){
        DegreeBossDAO degreeBossDAO = new DegreeBossDAO();
        DegreeBoss newDegreeBossData = new DegreeBoss();
        newDegreeBossData.setName(this.namesTextField.getText());
        newDegreeBossData.setFirstSurname(this.firstSurnameTextField.getText());
        newDegreeBossData.setSecondSurname(this.secondSurnameTextField.getText());
        newDegreeBossData.setEmailAddress(this.emailTextField.getText());
        newDegreeBossData.setAlternateEmail(this.alternateEmailTextField.getText());
        newDegreeBossData.setPhoneNumber(this.telephoneNumberTextField.getText());
        newDegreeBossData.setStatus(this.statusComboBox.getValue());
        newDegreeBossData.setStaffNumber(Integer.parseInt(this.matricleOrPersonalNumberTextField.getText()));

        DegreeBoss originalDegreeBossData = new DegreeBoss();
        originalDegreeBossData.setName(this.getAllLabelsDataFromUserInformationPane().get(0));
        originalDegreeBossData.setFirstSurname(this.getAllLabelsDataFromUserInformationPane().get(1));
        originalDegreeBossData.setSecondSurname(this.getAllLabelsDataFromUserInformationPane().get(2));
        originalDegreeBossData.setEmailAddress(this.getAllLabelsDataFromUserInformationPane().get(3));
        originalDegreeBossData.setAlternateEmail(this.getAllLabelsDataFromUserInformationPane().get(4));
        originalDegreeBossData.setPhoneNumber(this.getAllLabelsDataFromUserInformationPane().get(5));
        originalDegreeBossData.setStatus(this.getAllLabelsDataFromUserInformationPane().get(6));
        originalDegreeBossData.setStaffNumber(Integer.parseInt(this.getAllLabelsDataFromUserInformationPane().get(7)));

        try {
            if(degreeBossDAO.theDegreeBossIsAlreadyRegisted(newDegreeBossData)){
                AlertPaneController alertPaneController = new AlertPaneController();
                alertPaneController.openWarningPane("El usuario ya está registrado en el sistema");
                return;
            }
        } catch (DataRetrievalException e) {
            e.printStackTrace();
            AlertPaneController alertPaneController = new AlertPaneController();
            alertPaneController.openErrorPane("Hubo un error, inténtelo más tarde");
        }
        try {
            degreeBossDAO.modifyDegreeBossDataFromDatabase(newDegreeBossData, originalDegreeBossData);
            AlertPaneController alertPaneController = new AlertPaneController();
            alertPaneController.openWarningPane("Usuario modificado exitosamente");
        } catch (DataWritingException e) {
            e.printStackTrace();
            AlertPaneController alertPaneController = new AlertPaneController();
            alertPaneController.openErrorPane("Hubo un error, inténtelo más tarde");
        }
    }

    private ArrayList<String> getAllLabelsDataFromUserInformationPane(){
        ArrayList<String> userInformationPaneData = new ArrayList<>();
        userInformationPaneData.add(this.userInformationController.getNames());
        userInformationPaneData.add(this.userInformationController.getFirstSurname());
        userInformationPaneData.add(this.userInformationController.getSecondSurname());
        userInformationPaneData.add(this.userInformationController.getEmail());
        userInformationPaneData.add(this.userInformationController.getAlternateEmail());
        userInformationPaneData.add(this.userInformationController.getTelephoneNumber());
        userInformationPaneData.add(this.userInformationController.getStatus());
        userInformationPaneData.add(this.userInformationController.getMatriculeOrPersonalNumber());
        return userInformationPaneData;
    }

    private boolean allTextFieldsContainsCorrectValues(){
        Pattern namesPattern = Pattern.compile("([A-Z][a-z]+)\\s?([A-Z][a-z]+)?\\s?([A-Z][a-z]+)?\\s?([A-Z][a-z]+)?"),
                firstSurnamePattern = Pattern.compile("([A-Z][a-z]+)\\s?([A-Z][a-z]+)?\\s?([A-Z][a-z]+)?\\s?([A-Z][a-z]+)?"),
                secondSurnamePattern = Pattern.compile("([A-Z][a-z]+)\\s?([A-Z][a-z]+)?\\s?([A-Z][a-z]+)?\\s?([A-Z][a-z]+)?"),
                emailPattern = Pattern.compile("^(.+)@(\\S+)$"),
                alternateEmailPattern = Pattern.compile("^(.+)@(\\S+)$"),
                telephoneNumberPattern = Pattern.compile("^[0-9]{10}$"),
                matricleOrPersonalNumberPattern = Pattern.compile("");
    
        switch(this.userInformationController.getUserType()) {
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

        Matcher namesMatcher = namesPattern.matcher(this.namesTextField.getText()),
                firstSurnameMatcher = firstSurnamePattern.matcher(this.firstSurnameTextField.getText()),
                secondSurnameMatcher = secondSurnamePattern.matcher(this.secondSurnameTextField.getText()),
                emailMatcher = emailPattern.matcher(this.emailTextField.getText()),
                alternateEmailMatcher = alternateEmailPattern.matcher(this.alternateEmailTextField.getText()),
                telephoneNumberMatcher = telephoneNumberPattern.matcher(this.telephoneNumberTextField.getText()),
                matricleOrPersonalNumberMatcher = matricleOrPersonalNumberPattern.matcher(this.matricleOrPersonalNumberTextField.getText());

        if(namesMatcher.find() && firstSurnameMatcher.find() &&
           secondSurnameMatcher.find() && emailMatcher.find() &&
           alternateEmailMatcher.find() && telephoneNumberMatcher.find() &&
           matricleOrPersonalNumberMatcher.find()){
            return true;
        }

        return false;
    }

}
