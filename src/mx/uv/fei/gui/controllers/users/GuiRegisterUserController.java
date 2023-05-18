package mx.uv.fei.gui.controllers.users;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.exceptions.DataWritingException;

public class GuiRegisterUserController {
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
        this.typeComboBox.getItems().add("Estudiante");
        this.typeComboBox.getItems().add("Profesor");
        this.typeComboBox.getItems().add("Director");
        this.typeComboBox.getItems().add("Miembro de Cuerpo Académico");
        this.typeComboBox.getItems().add("Jefe de Carrera");
        this.typeComboBox.setValue("Estudiante");
    }

    @FXML
    void registerButtonController(ActionEvent event) {
        if(!this.namesTextField.getText().trim().isEmpty() &&
           !this.firstSurnameTextField.getText().trim().isEmpty() &&
           !this.secondSurnameTextField.getText().trim().isEmpty() &&
           !this.emailTextField.getText().trim().isEmpty() &&
           !this.alternateEmailTextField.getText().trim().isEmpty() &&
           !this.telephoneNumberTextField.getText().trim().isEmpty() &&
           !this.matricleOrPersonalNumberTextField.getText().trim().isEmpty()) {

            if(allTextFieldsContainsCorrectValues()) {
                this.errorMessajeText.setText("Usuario registrado con éxito");
                this.errorMessajeText.setVisible(true);
                switch(this.typeComboBox.getValue()) {
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
                this.errorMessajeText.setText("Algunos campos contienen datos inválidos");
                this.errorMessajeText.setVisible(true);
            }

        } else {
            this.errorMessajeText.setText("Faltan campos por llenar");
            this.errorMessajeText.setVisible(true);
        }
    }

    @FXML
    void typeComboBoxController(ActionEvent event) {
        if(this.typeComboBox.getValue().equals("Estudiante")){
            this.matricleOrPersonalNumberText.setText("Matrícula");
        } else {
            this.matricleOrPersonalNumberText.setText("Número de Personal");
        }
    }

    private void registerDirector() {
        try {
            DirectorDAO directorDAO = new DirectorDAO();
            Director director = new Director();
            director.setName(this.namesTextField.getText());
            director.setFirstSurname(this.firstSurnameTextField.getText());
            director.setSecondSurname(this.secondSurnameTextField.getText());
            director.setEmailAddress(this.emailTextField.getText());
            director.setAlternateEmail(this.alternateEmailTextField.getText());
            director.setPhoneNumber(this.telephoneNumberTextField.getText());
            director.setStaffNumber(Integer.parseInt(this.matricleOrPersonalNumberTextField.getText()));
            if(directorDAO.theDirectorIsAlreadyRegisted(director)) {
                //TODO OPEN ALERT PANE
                this.errorMessajeText.setText("El usuario ya está registrado en el sistema");
                this.errorMessajeText.setVisible(true);
                return;
            }
            directorDAO.addDirectorToDatabase(director);
        } catch (DataRetrievalException e) {
            e.printStackTrace();
        } catch (DataWritingException e) {
            e.printStackTrace();
        }
    }

    private void registerAcademicBodyHead() {
        try {
            AcademicBodyHeadDAO academicBodyHeadDAO = new AcademicBodyHeadDAO();
            AcademicBodyHead academicBodyHead = new AcademicBodyHead();
            academicBodyHead.setName(this.namesTextField.getText());
            academicBodyHead.setFirstSurname(this.firstSurnameTextField.getText());
            academicBodyHead.setSecondSurname(this.secondSurnameTextField.getText());
            academicBodyHead.setEmailAddress(this.emailTextField.getText());
            academicBodyHead.setAlternateEmail(this.alternateEmailTextField.getText());
            academicBodyHead.setPhoneNumber(this.telephoneNumberTextField.getText());
            academicBodyHead.setStaffNumber(Integer.parseInt(this.matricleOrPersonalNumberTextField.getText()));
            if(academicBodyHeadDAO.theAcademicBodyHeadIsAlreadyRegisted(academicBodyHead)){
                this.errorMessajeText.setVisible(true);
                this.errorMessajeText.setText("El usuario ya está registrado en el sistema");
                return;
            }
            academicBodyHeadDAO.addAcademicBodyHeadToDatabase(academicBodyHead);
        } catch (DataRetrievalException e) {
            e.printStackTrace();
        } catch (DataWritingException e) {
            e.printStackTrace();
        }
    }

    private void registerDegreeBoss(){
        try {
        DegreeBossDAO degreeBossDAO = new DegreeBossDAO();
        DegreeBoss degreeBoss = new DegreeBoss();
        degreeBoss.setName(this.namesTextField.getText());
        degreeBoss.setFirstSurname(this.firstSurnameTextField.getText());
        degreeBoss.setSecondSurname(this.secondSurnameTextField.getText());
        degreeBoss.setEmailAddress(this.emailTextField.getText());
        degreeBoss.setAlternateEmail(this.alternateEmailTextField.getText());
        degreeBoss.setPhoneNumber(this.telephoneNumberTextField.getText());
        degreeBoss.setStaffNumber(Integer.parseInt(this.matricleOrPersonalNumberTextField.getText()));
            if(degreeBossDAO.theDegreeBossIsAlreadyRegisted(degreeBoss)){
                this.errorMessajeText.setText("El usuario ya está registrado en el sistema");
                this.errorMessajeText.setVisible(true);
                return;
            }
            degreeBossDAO.addDegreeBossToDatabase(degreeBoss);
        } catch (DataRetrievalException e) {
            e.printStackTrace();
        } catch (DataWritingException e) {
            e.printStackTrace();
        }
        
    }

    private void registerProfessor(){
        try {
            ProfessorDAO professorDAO = new ProfessorDAO();
            Professor professor = new Professor();
            professor.setName(this.namesTextField.getText());
            professor.setFirstSurname(this.firstSurnameTextField.getText());
            professor.setSecondSurname(this.secondSurnameTextField.getText());
            professor.setEmailAddress(this.emailTextField.getText());
            professor.setAlternateEmail(this.alternateEmailTextField.getText());
            professor.setPhoneNumber(this.telephoneNumberTextField.getText());
            professor.setStaffNumber(Integer.parseInt(this.matricleOrPersonalNumberTextField.getText()));
            if(professorDAO.theProfessorIsAlreadyRegisted(professor)){
                this.errorMessajeText.setText("El usuario ya está registrado en el sistema");
                this.errorMessajeText.setVisible(true);
                return;
            }
            professorDAO.addProfessorToDatabase(professor);
        } catch (DataRetrievalException e) {
            e.printStackTrace();
        } catch (DataWritingException e) {
            e.printStackTrace();
        }
    }

    private void registerStudent(){
        try {
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
                this.errorMessajeText.setText("El usuario ya está registrado en el sistema");
                this.errorMessajeText.setVisible(true);
                return;
            }
            studentDAO.addStudentToDatabase(student);
        } catch (DataRetrievalException e) {
            e.printStackTrace();
        } catch (DataWritingException e) {
            e.printStackTrace();
        }
    }

    private boolean allTextFieldsContainsCorrectValues(){
        Pattern namesPattern = Pattern.compile("^[A-Z][a-z]+$"),
                firstSurnamePattern = Pattern.compile("^[A-Z][a-z]+$"),
                secondSurnamePattern = Pattern.compile("^[A-Z][a-z]+$"),
                emailPattern = Pattern.compile("^(.+)@(\\S+)$"),
                alternateEmailPattern = Pattern.compile("^(.+)@(\\S+)$"),
                telephoneNumberPattern = Pattern.compile("^[0-9]{10}$"),
                matricleOrPersonalNumberPattern = Pattern.compile("");
    
        switch(this.typeComboBox.getValue()){
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
