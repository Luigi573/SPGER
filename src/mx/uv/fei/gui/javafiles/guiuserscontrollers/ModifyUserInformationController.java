package mx.uv.fei.gui.javafiles.guiuserscontrollers;

import java.util.ArrayList;

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
    void initialize(){
        
    }

    @FXML
    void exitButtonController(ActionEvent event) {
        this.guiUsersController.openPaneWithUserInformation(getFirstSurname(), getAlternateEmail(), getStatus());
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

    private void modifyStudent(){
        StudentDAO studentDAO = new StudentDAO();
        Student student = new Student();
        student.setName(this.namesTextField.getText());
        student.setFirstSurname(this.firstSurnameTextField.getText());
        student.setSecondSurname(this.secondSurnameTextField.getText());
        student.setEmailAddress(this.emailTextField.getText());
        student.setAlternateEmail(this.alternateEmailTextField.getText());
        student.setPhoneNumber(this.telephoneNumberTextField.getText());
        student.setMatricle(this.matricleOrPersonalNumberTextField.getText());
        if(studentDAO.theStudentIsAlreadyRegisted(student)){
            this.errorMessageText.setText("El usuario ya está registrado en el sistema");
            this.errorMessageText.setVisible(true);
            return;
        }
        studentDAO.modifyStudentDataFromDatabase(student, this.getAllLabelsDataFromUserInformationPane());
        this.errorMessageText.setVisible(false);
    }

    private void modifyProfessor(){
        ProfessorDAO professorDAO = new ProfessorDAO();
        Professor professor = new Professor();
        professor.setName(this.namesTextField.getText());
        professor.setFirstSurname(this.firstSurnameTextField.getText());
        professor.setSecondSurname(this.secondSurnameTextField.getText());
        professor.setEmailAddress(this.emailTextField.getText());
        professor.setAlternateEmail(this.alternateEmailTextField.getText());
        professor.setPhoneNumber(this.telephoneNumberTextField.getText());
        professor.setPersonalNumber(this.matricleOrPersonalNumberTextField.getText());
        if(professorDAO.theProfessorIsAlreadyRegisted(professor)){
            this.errorMessageText.setText("El usuario ya está registrado en el sistema");
            this.errorMessageText.setVisible(true);
            return;
        }
        professorDAO.modifyProfessorDataFromDatabase(professor, this.getAllLabelsDataFromUserInformationPane());
        this.errorMessageText.setVisible(false);
    }

    private void modifyDirector(){
        DirectorDAO directorDAO = new DirectorDAO();
        Director director = new Director();
        director.setName(this.namesTextField.getText());
        director.setFirstSurname(this.firstSurnameTextField.getText());
        director.setSecondSurname(this.secondSurnameTextField.getText());
        director.setEmailAddress(this.emailTextField.getText());
        director.setAlternateEmail(this.alternateEmailTextField.getText());
        director.setPhoneNumber(this.telephoneNumberTextField.getText());
        director.setPersonalNumber(this.matricleOrPersonalNumberTextField.getText());
        if(directorDAO.theDirectorIsAlreadyRegisted(director)){
            this.errorMessageText.setText("El usuario ya está registrado en el sistema");
            this.errorMessageText.setVisible(true);
            return;
        }
        directorDAO.modifyDirectorDataFromDatabase(director, this.getAllLabelsDataFromUserInformationPane());
        this.errorMessageText.setVisible(false);
    }

    private void modifyAcademicBodyHead(){
        AcademicBodyHeadDAO academicBodyHeadDAO = new AcademicBodyHeadDAO();
        AcademicBodyHead academicBodyHead = new AcademicBodyHead();
        academicBodyHead.setName(this.namesTextField.getText());
        academicBodyHead.setFirstSurname(this.firstSurnameTextField.getText());
        academicBodyHead.setSecondSurname(this.secondSurnameTextField.getText());
        academicBodyHead.setEmailAddress(this.emailTextField.getText());
        academicBodyHead.setAlternateEmail(this.alternateEmailTextField.getText());
        academicBodyHead.setPhoneNumber(this.telephoneNumberTextField.getText());
        academicBodyHead.setPersonalNumber(this.matricleOrPersonalNumberTextField.getText());
        if(academicBodyHeadDAO.theAcademicBodyHeadIsAlreadyRegisted(academicBodyHead)){
            this.errorMessageText.setText("El usuario ya está registrado en el sistema");
            this.errorMessageText.setVisible(true);
            return;
        }
        academicBodyHeadDAO.modifyAcademicBodyHeadDataFromDatabase(academicBodyHead, this.getAllLabelsDataFromUserInformationPane());
        this.errorMessageText.setVisible(false);
    }

    private void modifyDegreeBoss(){
        DegreeBossDAO degreeBossDAO = new DegreeBossDAO();
        DegreeBoss degreeBoss = new DegreeBoss();
        degreeBoss.setName(this.namesTextField.getText());
        degreeBoss.setFirstSurname(this.firstSurnameTextField.getText());
        degreeBoss.setSecondSurname(this.secondSurnameTextField.getText());
        degreeBoss.setEmailAddress(this.emailTextField.getText());
        degreeBoss.setAlternateEmail(this.alternateEmailTextField.getText());
        degreeBoss.setPhoneNumber(this.telephoneNumberTextField.getText());
        degreeBoss.setPersonalNumber(this.matricleOrPersonalNumberTextField.getText());
        if(degreeBossDAO.theDegreeBossIsAlreadyRegisted(degreeBoss)){
            this.errorMessageText.setText("El usuario ya está registrado en el sistema");
            this.errorMessageText.setVisible(true);
            return;
        }
        degreeBossDAO.modifyDegreeBossDataFromDatabase(degreeBoss, this.getAllLabelsDataFromUserInformationPane());
        this.errorMessageText.setVisible(false);
    }

    private ArrayList<String> getAllLabelsDataFromUserInformationPane(){
        ArrayList<String> textFieldsData = new ArrayList<>();
        textFieldsData.add(this.userInformationController.getNames());
        textFieldsData.add(this.userInformationController.getFirstSurname());
        textFieldsData.add(this.userInformationController.getSecondSurname());
        textFieldsData.add(this.userInformationController.getEmail());
        textFieldsData.add(this.userInformationController.getAlternateEmail());
        textFieldsData.add(this.userInformationController.getTelephoneNumber());
        textFieldsData.add(this.userInformationController.getMatricleOrPersonalNumber());
        return textFieldsData;
    }

}
