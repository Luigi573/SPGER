package mx.uv.fei.gui.javafiles.guiuserscontrollers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
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

public class ModifyUserInformationController {

    private GuiUsersController guiUsersController;

    private UserInformationController userInformationController;

    @FXML
    private TextField alternateEmailTextField;

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
    void exitButtonController(ActionEvent event) {
        this.guiUsersController.openPaneWithUserInformation(
            this.userInformationController.getMatricleOrPersonalNumber(), 
            this.userInformationController.getUserType()
        );
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
                switch(this.userInformationController.getUserType()) {
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
            } else {
                this.errorMessageText.setText("Algunos campos contienen datos inválidos");
                this.errorMessageText.setVisible(true);
            }

        } else {
            this.errorMessageText.setText("Faltan campos por llenar");
            this.errorMessageText.setVisible(true);
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

    public String getMatricleOrPersonalNumber() {
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

    public void setGuiUsersController(GuiUsersController guiUsersController){
        this.guiUsersController = guiUsersController;
    }

    public void setUserInformationController(UserInformationController userInformationController){
        this.userInformationController = userInformationController;
    }

    private void modifyStudent() {
        StudentDAO studentDAO = new StudentDAO();
        Student newStudentData = new Student();
        Student originalStudentData = studentDAO.getStudentFromDatabase(this.userInformationController.getMatricleOrPersonalNumber());
        newStudentData.setName(this.namesTextField.getText());
        newStudentData.setFirstSurname(this.firstSurnameTextField.getText());
        newStudentData.setSecondSurname(this.secondSurnameTextField.getText());
        newStudentData.setEmailAddress(this.emailTextField.getText());
        newStudentData.setAlternateEmail(this.alternateEmailTextField.getText());
        newStudentData.setPhoneNumber(this.telephoneNumberTextField.getText());
        newStudentData.setStatus(this.statusComboBox.getValue());
        newStudentData.setMatricle(this.matricleOrPersonalNumberTextField.getText());
        if(studentDAO.theStudentIsAlreadyRegisted(newStudentData)){
            this.errorMessageText.setText("El usuario ya está registrado en el sistema");
            this.errorMessageText.setVisible(true);
            return;
        }
        studentDAO.modifyStudentDataFromDatabase(newStudentData, originalStudentData);
        this.errorMessageText.setText("Usuario modificado exitosamente");
        this.errorMessageText.setVisible(true);
        this.exitButton.setVisible(false);
        this.modifyButton.setVisible(false);
        this.guiUsersController.refreshUserList();
    }

    private void modifyProfessor() {
        ProfessorDAO professorDAO = new ProfessorDAO();
        Professor newProfessorData = new Professor();
        Professor originalProfessorData = professorDAO.getProfessorFromDatabase(Integer.parseInt(userInformationController.getMatricleOrPersonalNumber()));
        newProfessorData.setName(this.namesTextField.getText());
        newProfessorData.setFirstSurname(this.firstSurnameTextField.getText());
        newProfessorData.setSecondSurname(this.secondSurnameTextField.getText());
        newProfessorData.setEmailAddress(this.emailTextField.getText());
        newProfessorData.setAlternateEmail(this.alternateEmailTextField.getText());
        newProfessorData.setPhoneNumber(this.telephoneNumberTextField.getText());
        newProfessorData.setStatus(this.statusComboBox.getValue());
        newProfessorData.setPersonalNumber(Integer.parseInt(this.matricleOrPersonalNumberTextField.getText()));
        if(professorDAO.theProfessorIsAlreadyRegisted(newProfessorData)){
            this.errorMessageText.setText("El usuario ya está registrado en el sistema");
            this.errorMessageText.setVisible(true);
            return;
        }
        professorDAO.modifyProfessorDataFromDatabase(newProfessorData, originalProfessorData);
        this.errorMessageText.setText("Usuario modificado exitosamente");
        this.errorMessageText.setVisible(true);
        this.exitButton.setVisible(false);
        this.modifyButton.setVisible(false);
        this.guiUsersController.refreshUserList();
    }

    private void modifyDirector() {
        DirectorDAO directorDAO = new DirectorDAO();
        Director newDirectorData = new Director();
        Director originalDirectorData = directorDAO.getDirectorFromDatabase(Integer.parseInt(this.userInformationController.getMatricleOrPersonalNumber()));
        newDirectorData.setName(this.namesTextField.getText());
        newDirectorData.setFirstSurname(this.firstSurnameTextField.getText());
        newDirectorData.setSecondSurname(this.secondSurnameTextField.getText());
        newDirectorData.setEmailAddress(this.emailTextField.getText());
        newDirectorData.setAlternateEmail(this.alternateEmailTextField.getText());
        newDirectorData.setPhoneNumber(this.telephoneNumberTextField.getText());
        newDirectorData.setStatus(this.statusComboBox.getValue());
        newDirectorData.setPersonalNumber(Integer.parseInt(this.matricleOrPersonalNumberTextField.getText()));
        if(directorDAO.theDirectorIsAlreadyRegisted(newDirectorData)){
            this.errorMessageText.setText("El usuario ya está registrado en el sistema");
            this.errorMessageText.setVisible(true);
            return;
        }
        directorDAO.modifyDirectorDataFromDatabase(newDirectorData, originalDirectorData);
        this.errorMessageText.setText("Usuario modificado exitosamente");
        this.errorMessageText.setVisible(true);
        this.exitButton.setVisible(false);
        this.modifyButton.setVisible(false);
        this.guiUsersController.refreshUserList();
    }

    private void modifyAcademicBodyHead() {
        AcademicBodyHeadDAO academicBodyHeadDAO = new AcademicBodyHeadDAO();
        AcademicBodyHead newAcademicBodyHeadData = new AcademicBodyHead();
        AcademicBodyHead originalAcademicBodyHeadData = academicBodyHeadDAO.getAcademicBodyHeadFromDatabase(Integer.parseInt(this.userInformationController.getMatricleOrPersonalNumber()));
        newAcademicBodyHeadData.setName(this.namesTextField.getText());
        newAcademicBodyHeadData.setFirstSurname(this.firstSurnameTextField.getText());
        newAcademicBodyHeadData.setSecondSurname(this.secondSurnameTextField.getText());
        newAcademicBodyHeadData.setEmailAddress(this.emailTextField.getText());
        newAcademicBodyHeadData.setAlternateEmail(this.alternateEmailTextField.getText());
        newAcademicBodyHeadData.setPhoneNumber(this.telephoneNumberTextField.getText());
        newAcademicBodyHeadData.setStatus(this.statusComboBox.getValue());
        newAcademicBodyHeadData.setPersonalNumber(Integer.parseInt(this.matricleOrPersonalNumberTextField.getText()));
        if(academicBodyHeadDAO.theAcademicBodyHeadIsAlreadyRegisted(newAcademicBodyHeadData)){
            this.errorMessageText.setText("El usuario ya está registrado en el sistema");
            this.errorMessageText.setVisible(true);
            return;
        }
        academicBodyHeadDAO.modifyAcademicBodyHeadDataFromDatabase(newAcademicBodyHeadData, originalAcademicBodyHeadData);
        this.errorMessageText.setText("Usuario modificado exitosamente");
        this.errorMessageText.setVisible(true);
        this.exitButton.setVisible(false);
        this.modifyButton.setVisible(false);
        this.guiUsersController.refreshUserList();
    }

    private void modifyDegreeBoss() {
        DegreeBossDAO degreeBossDAO = new DegreeBossDAO();
        DegreeBoss newDegreeBossData = new DegreeBoss();
        DegreeBoss originalDegreeBossData = degreeBossDAO.getDegreeBossFromDatabase(Integer.parseInt(this.userInformationController.getMatricleOrPersonalNumber()));
        newDegreeBossData.setName(this.namesTextField.getText());
        newDegreeBossData.setFirstSurname(this.firstSurnameTextField.getText());
        newDegreeBossData.setSecondSurname(this.secondSurnameTextField.getText());
        newDegreeBossData.setEmailAddress(this.emailTextField.getText());
        newDegreeBossData.setAlternateEmail(this.alternateEmailTextField.getText());
        newDegreeBossData.setPhoneNumber(this.telephoneNumberTextField.getText());
        newDegreeBossData.setStatus(this.statusComboBox.getValue());
        newDegreeBossData.setPersonalNumber(Integer.parseInt(this.matricleOrPersonalNumberTextField.getText()));
        if(degreeBossDAO.theDegreeBossIsAlreadyRegisted(newDegreeBossData)){
            this.errorMessageText.setText("El usuario ya está registrado en el sistema");
            this.errorMessageText.setVisible(true);
            return;
        }
        degreeBossDAO.modifyDegreeBossDataFromDatabase(newDegreeBossData, originalDegreeBossData);
        this.errorMessageText.setText("Usuario modificado exitosamente");
        this.errorMessageText.setVisible(true);
        this.exitButton.setVisible(false);
        this.modifyButton.setVisible(false);
        this.guiUsersController.refreshUserList();
    }

    public void setStatusesToStatusComboBox() {
        if(this.userInformationController.getUserType().equals("Estudiante")){
            this.statusComboBox.getItems().addAll(StudentStatus.Activo.name());
            this.statusComboBox.getItems().addAll(StudentStatus.Baja.name());
            this.statusComboBox.getItems().addAll(StudentStatus.Disponible.name());
            this.statusComboBox.getItems().addAll(StudentStatus.Graduado.name());
        } else {
            this.statusComboBox.getItems().addAll(ProfessorStatus.Activo.name());
            this.statusComboBox.getItems().addAll(ProfessorStatus.Inactivo.name());
        }

        this.statusComboBox.setValue(this.userInformationController.getStatus());
    }

    private boolean allTextFieldsContainsCorrectValues() {
        Pattern namesPattern = Pattern.compile("^[A-Z][a-z]+$"),
                firstSurnamePattern = Pattern.compile("^[A-Z][a-z]+$"),
                secondSurnamePattern = Pattern.compile("^[A-Z][a-z]+$"),
                emailPattern = Pattern.compile("^(.+)@(\\S+)$"),
                alternateEmailPattern = Pattern.compile("^(.+)@(\\S+)$"),
                telephoneNumberPattern = Pattern.compile("^[0-9]{10}$"),
                matricleOrPersonalNumberPattern = Pattern.compile("");
    
        switch(this.userInformationController.getUserType()){
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

    public void setMatricleOrPersonalNumberText() {
        if(this.userInformationController.getUserType().equals("Estudiante")) {
            this.matricleOrPersonalNumberText.setText("Matrícula: ");
            this.matricleOrPersonalNumberText.setPrefWidth(70);
            this.matricleOrPersonalNumberTextField.setPrefWidth(282);
        } else {
            this.matricleOrPersonalNumberText.setText("N° de Personal: ");
            this.matricleOrPersonalNumberText.setPrefWidth(107);
            this.matricleOrPersonalNumberTextField.setPrefWidth(245);
        }
    }

}
