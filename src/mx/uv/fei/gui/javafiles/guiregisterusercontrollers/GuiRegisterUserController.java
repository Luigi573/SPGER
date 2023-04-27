package mx.uv.fei.gui.javafiles.guiregisterusercontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
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

public class GuiRegisterUserController {

    @FXML
    private TextField alternateEmailTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField firstSurnameTextField;

    @FXML
    private TextField namesTextField;

    @FXML
    private Button registerButton;

    @FXML
    private TextField secondSurnameTextField;

    @FXML
    private ChoiceBox<String> statusChoiceBox;

    @FXML
    private TextField telephoneNumberTextField;

    @FXML
    private ChoiceBox<String> typeChoiceBox;

    @FXML
    void initialize() {
        this.typeChoiceBox.getItems().add("Estudiante");
        this.typeChoiceBox.getItems().add("Profesor");
        this.typeChoiceBox.getItems().add("Director");
        this.typeChoiceBox.getItems().add("Miembro de Cuerpo Académico");
        this.typeChoiceBox.getItems().add("Jefe de Carrera");
        this.typeChoiceBox.setValue("Estudiante");

        this.statusChoiceBox.getItems().add("Activo");
        this.statusChoiceBox.getItems().add("Egresado");
        this.statusChoiceBox.getItems().add("Inactivo");
        this.statusChoiceBox.setValue("Activo");
    }

    @FXML
    void registerButtonController(ActionEvent event) {
        if(!this.namesTextField.getText().trim().isEmpty() &&
           !this.firstSurnameTextField.getText().trim().isEmpty() &&
           !this.secondSurnameTextField.getText().trim().isEmpty() &&
           !this.emailTextField.getText().trim().isEmpty() &&
           !this.alternateEmailTextField.getText().trim().isEmpty() &&
           !this.telephoneNumberTextField.getText().trim().isEmpty()){
            switch(this.typeChoiceBox.getValue()){
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
                    this.registerStudent();
                    break;
                }
            }
        } else {
            System.out.println("Está vacío");
        }
    }

    private void registerDirector(){
        DirectorDAO directorDAO = new DirectorDAO();
        Director director = new Director();
        director.setName(this.namesTextField.getText());
        director.setFirstSurname(this.firstSurnameTextField.getText());
        director.setSecondSurname(this.secondSurnameTextField.getText());
        director.setEmailAddress(this.emailTextField.getText());
        director.setAlternateEmail(this.alternateEmailTextField.getText());
        director.setPhoneNumber(this.telephoneNumberTextField.getText());
        //director.setStatus(this.statusChoiceBox.getValue());
        directorDAO.addDirectorToDatabase(director);
    }

    private void registerAcademicBodyHead(){
        AcademicBodyHeadDAO academicBodyHeadDAO = new AcademicBodyHeadDAO();
        AcademicBodyHead academicBodyHead = new AcademicBodyHead();
        academicBodyHead.setName(this.namesTextField.getText());
        academicBodyHead.setFirstSurname(this.firstSurnameTextField.getText());
        academicBodyHead.setSecondSurname(this.secondSurnameTextField.getText());
        academicBodyHead.setEmailAddress(this.emailTextField.getText());
        academicBodyHead.setAlternateEmail(this.alternateEmailTextField.getText());
        academicBodyHead.setPhoneNumber(this.telephoneNumberTextField.getText());
        //academicBodyHead.setStatus(this.statusChoiceBox.getValue());
        academicBodyHeadDAO.addAcademicBodyHeadToDatabase(academicBodyHead);
    }

    private void registerDegreeBoss(){
        DegreeBossDAO degreeBossDAO = new DegreeBossDAO();
        DegreeBoss degreeBoss = new DegreeBoss();
        degreeBoss.setName(this.namesTextField.getText());
        degreeBoss.setFirstSurname(this.firstSurnameTextField.getText());
        degreeBoss.setSecondSurname(this.secondSurnameTextField.getText());
        degreeBoss.setEmailAddress(this.emailTextField.getText());
        degreeBoss.setAlternateEmail(this.alternateEmailTextField.getText());
        degreeBoss.setPhoneNumber(this.telephoneNumberTextField.getText());
        //degreeBoss.setStatus(this.statusChoiceBox.getValue());
        degreeBossDAO.addDegreeBossToDatabase(degreeBoss);
    }

    private void registerProfessor(){
        ProfessorDAO professorDAO = new ProfessorDAO();
        Professor professor = new Professor();
        professor.setName(this.namesTextField.getText());
        professor.setFirstSurname(this.firstSurnameTextField.getText());
        professor.setSecondSurname(this.secondSurnameTextField.getText());
        professor.setEmailAddress(this.emailTextField.getText());
        professor.setAlternateEmail(this.alternateEmailTextField.getText());
        professor.setPhoneNumber(this.telephoneNumberTextField.getText());
        //professor.setStatus(this.statusChoiceBox.getValue());
        professorDAO.addProfessorToDatabase(professor);
    }

    private void registerStudent(){
        StudentDAO studentDAO = new StudentDAO();
        Student student = new Student();
        student.setName(this.namesTextField.getText());
        student.setFirstSurname(this.firstSurnameTextField.getText());
        student.setSecondSurname(this.secondSurnameTextField.getText());
        student.setEmailAddress(this.emailTextField.getText());
        student.setAlternateEmail(this.alternateEmailTextField.getText());
        student.setPhoneNumber(this.telephoneNumberTextField.getText());
        //student.setStatus(this.statusChoiceBox.getValue());
        studentDAO.addStudentToDatabase(student);
    }

}
