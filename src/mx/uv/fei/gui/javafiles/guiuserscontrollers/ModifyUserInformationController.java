package mx.uv.fei.gui.javafiles.guiuserscontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import mx.uv.fei.logic.daos.StudentDAO;
import mx.uv.fei.logic.domain.Student;

public class ModifyUserInformationController {

    private GuiUsersController guiUsersController;

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
    private ComboBox<String> typeComboBox;

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
            switch(this.typeComboBox.getValue()){
                case "Director": {
                    this.registerDirector();
                    break;
                }
    
                case "Miembro de Cuerpo Académico": {
                    this.registerAcademicBodyHead();
                    break;
                }
    
                case "Jefe de Carrera": {
                    this.registerDegreeBoss();
                    break;
                }
    
                case "Profesor": {
                    this.registerProfessor();
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

    public String getType() {
        return this.typeComboBox.getItems().get(0);
    }

    public void setType(String type) {
        this.typeComboBox.setValue(type);
    }

    public void setGuiUsersController(GuiUsersController guiUsersController){
        this.guiUsersController = guiUsersController;
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
        studentDAO.modifyStudentDataFromDatabase(student);
    }

}
