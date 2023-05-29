package mx.uv.fei.gui.controllers.users;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
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
import mx.uv.fei.logic.exceptions.DataInsertionException;

public class ModifyUserInformationController{
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
    private void exitButtonController(ActionEvent event){
        returnToGuiUsers(event);
    }
    @FXML
    private void modifyButtonController(ActionEvent event){
        if(!namesTextField.getText().trim().isEmpty() &&
           !firstSurnameTextField.getText().trim().isEmpty() &&
           !secondSurnameTextField.getText().trim().isEmpty() &&
           !emailTextField.getText().trim().isEmpty() &&
           !alternateEmailTextField.getText().trim().isEmpty() &&
           !telephoneNumberTextField.getText().trim().isEmpty() &&
           !matricleOrPersonalNumberTextField.getText().trim().isEmpty()){
            if(allTextFieldsContainsCorrectValues()){
                if(userInformationController.getUserType().equals(UserType.DIRECTOR.getValue())){
                    modifyDirector(event);
                }

                if(userInformationController.getUserType().equals(UserType.ACADEMIC_BODY_HEAD.getValue())){
                    modifyAcademicBodyHead(event);
                }

                if(userInformationController.getUserType().equals(UserType.DEGREE_BOSS.getValue())){
                    modifyDegreeBoss(event);
                }

                if(userInformationController.getUserType().equals(UserType.PROFESSOR.getValue())){
                    modifyProfessor(event);
                }

                if(userInformationController.getUserType().equals(UserType.STUDENT.getValue())){
                    modifyStudent(event);
                }
            }
        }else{
            new AlertPopUpGenerator().showCustomMessage(AlertType.WARNING, "Error", "Faltan campos por llenar");
        }
    }

    public String getAlternateEmail(){
        return alternateEmailTextField.getText();
    }
    public void setAlternateEmail(String alternateEmail){
        alternateEmailTextField.setText(alternateEmail);
    }
    public String getEmail(){
        return emailTextField.getText();
    }
    public void setEmail(String email){
        emailTextField.setText(email);
    }
    public String getFirstSurname(){
        return firstSurnameTextField.getText();
    }
    public void setFirstSurname(String firstSurname){
        firstSurnameTextField.setText(firstSurname);
    }
    public String getMatriculeOrPersonalNumber(){
        return matricleOrPersonalNumberTextField.getText();
    }
    public void setMatricleOrPersonalNumber(String matricleOrPersonalNumber){
        matricleOrPersonalNumberTextField.setText(matricleOrPersonalNumber);
    }
    public String getNames(){
        return namesTextField.getText();
    }
    public void setNames(String names){
        namesTextField.setText(names);
    }
    public String getSecondSurname(){
        return secondSurnameTextField.getText();
    }
    public void setSecondSurname(String secondSurname){
        secondSurnameTextField.setText(secondSurname);
    }
    public String getStatus(){
        return statusComboBox.getItems().get(0);
    }
    public void setStatus(String status){
        statusComboBox.setValue(status);
    }
    public String getTelephoneNumber(){
        return telephoneNumberTextField.getText();
    }
    public void setTelephoneNumber(String telephoneNumber){
        telephoneNumberTextField.setText(telephoneNumber);
    }
    public void setUserInformationController(UserInformationController userInformationController){
        this.userInformationController = userInformationController;
    }
    public void setDataToStatusCombobox(String userType){
        if(userType.equals(UserType.STUDENT.getValue())){
            statusComboBox.getItems().add(StudentStatus.ACTIVE.getValue());
            statusComboBox.getItems().add(StudentStatus.AVAILABLE.getValue());
            statusComboBox.getItems().add(StudentStatus.GRADUATED.getValue());
            statusComboBox.getItems().add(StudentStatus.DROPPED.getValue());
        } else{
            statusComboBox.getItems().add(ProfessorStatus.ACTIVE.getValue());
            statusComboBox.getItems().add(ProfessorStatus.INACTIVE.getValue());
        }
    }
    public void setLabelsCorrectBounds(String userType){
        if(userType.equals(UserType.STUDENT.getValue())){
            matricleOrPersonalNumberText.setText("Matrícula: ");
            matricleOrPersonalNumberText.setPrefWidth(72);
            matricleOrPersonalNumberText.setLayoutX(0);
            matricleOrPersonalNumberText.setLayoutY(0);
            matricleOrPersonalNumberTextField.setPrefWidth(350);
            matricleOrPersonalNumberTextField.setLayoutX(1);
            matricleOrPersonalNumberTextField.setLayoutY(7);
        } else {
            matricleOrPersonalNumberText.setText("Número de Personal: ");
            matricleOrPersonalNumberText.setPrefWidth(144);
            matricleOrPersonalNumberText.setLayoutX(0);
            matricleOrPersonalNumberText.setLayoutY(0);
            matricleOrPersonalNumberTextField.setPrefWidth(286);
            matricleOrPersonalNumberTextField.setLayoutX(1);
            matricleOrPersonalNumberTextField.setLayoutY(7);
        }
    }

    private void modifyStudent(ActionEvent event){
        StudentDAO studentDAO = new StudentDAO();
        Student newStudentData = new Student();
        newStudentData.setName(namesTextField.getText());
        newStudentData.setFirstSurname(firstSurnameTextField.getText());
        newStudentData.setSecondSurname(secondSurnameTextField.getText());
        newStudentData.setEmailAddress(emailTextField.getText());
        newStudentData.setAlternateEmail(alternateEmailTextField.getText());
        newStudentData.setPhoneNumber(telephoneNumberTextField.getText());
        newStudentData.setStatus(statusComboBox.getValue());
        newStudentData.setMatricle(matricleOrPersonalNumberTextField.getText());

        Student originalStudentData = new Student();
        originalStudentData.setName(getAllLabelsDataFromUserInformationPane().get(0));
        originalStudentData.setFirstSurname(getAllLabelsDataFromUserInformationPane().get(1));
        originalStudentData.setSecondSurname(getAllLabelsDataFromUserInformationPane().get(2));
        originalStudentData.setEmailAddress(getAllLabelsDataFromUserInformationPane().get(3));
        originalStudentData.setAlternateEmail(getAllLabelsDataFromUserInformationPane().get(4));
        originalStudentData.setPhoneNumber(getAllLabelsDataFromUserInformationPane().get(5));
        originalStudentData.setStatus(getAllLabelsDataFromUserInformationPane().get(6));
        originalStudentData.setMatricle(getAllLabelsDataFromUserInformationPane().get(7));

        try{
            if(studentDAO.theStudentIsAlreadyRegisted(newStudentData)){
                new AlertPopUpGenerator().showCustomMessage(AlertType.WARNING, "Error", "El usuario ya está registrado en el sistema");
                return;
            }
        }catch(DataRetrievalException e){
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }
        try{
            studentDAO.modifyStudentDataFromDatabase(newStudentData, originalStudentData);
            new AlertPopUpGenerator().showCustomMessage(AlertType.WARNING, "Éxito", "Usuario modificado exitosamente");
            returnToGuiUsers(event);
        }catch(DataInsertionException e){
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }
    }
    private void modifyProfessor(ActionEvent event){
        ProfessorDAO professorDAO = new ProfessorDAO();
        Professor newProfessorData = new Professor();
        newProfessorData.setName(namesTextField.getText());
        newProfessorData.setFirstSurname(firstSurnameTextField.getText());
        newProfessorData.setSecondSurname(secondSurnameTextField.getText());
        newProfessorData.setEmailAddress(emailTextField.getText());
        newProfessorData.setAlternateEmail(alternateEmailTextField.getText());
        newProfessorData.setPhoneNumber(telephoneNumberTextField.getText());
        newProfessorData.setStatus(statusComboBox.getValue());
        newProfessorData.setStaffNumber(Integer.parseInt(matricleOrPersonalNumberTextField.getText()));

        Professor originalProfessorData = new Professor();
        originalProfessorData.setName(getAllLabelsDataFromUserInformationPane().get(0));
        originalProfessorData.setFirstSurname(getAllLabelsDataFromUserInformationPane().get(1));
        originalProfessorData.setSecondSurname(getAllLabelsDataFromUserInformationPane().get(2));
        originalProfessorData.setEmailAddress(getAllLabelsDataFromUserInformationPane().get(3));
        originalProfessorData.setAlternateEmail(getAllLabelsDataFromUserInformationPane().get(4));
        originalProfessorData.setPhoneNumber(getAllLabelsDataFromUserInformationPane().get(5));
        originalProfessorData.setStatus(getAllLabelsDataFromUserInformationPane().get(6));
        originalProfessorData.setStaffNumber(Integer.parseInt(getAllLabelsDataFromUserInformationPane().get(7)));
        try{
            if(professorDAO.theProfessorIsAlreadyRegisted(newProfessorData)){
                new AlertPopUpGenerator().showCustomMessage(AlertType.WARNING, "Error", "El usuario ya está registrado en el sistema");
                return;
            }
        }catch(DataRetrievalException e) {
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }

        try{
            professorDAO.modifyProfessorDataFromDatabase(newProfessorData, originalProfessorData);
            new AlertPopUpGenerator().showCustomMessage(AlertType.WARNING, "Éxito", "Usuario modificado exitosamente");
            returnToGuiUsers(event);
        }catch (DataInsertionException e){
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }
    }
    private void modifyDirector(ActionEvent event){
        DirectorDAO directorDAO = new DirectorDAO();
        Director newDirectorData = new Director();
        newDirectorData.setName(namesTextField.getText());
        newDirectorData.setFirstSurname(firstSurnameTextField.getText());
        newDirectorData.setSecondSurname(secondSurnameTextField.getText());
        newDirectorData.setEmailAddress(emailTextField.getText());
        newDirectorData.setAlternateEmail(alternateEmailTextField.getText());
        newDirectorData.setPhoneNumber(telephoneNumberTextField.getText());
        newDirectorData.setStatus(statusComboBox.getValue());
        newDirectorData.setStaffNumber(Integer.parseInt(matricleOrPersonalNumberTextField.getText()));

        Director originalDirectorData = new Director();
        originalDirectorData.setName(getAllLabelsDataFromUserInformationPane().get(0));
        originalDirectorData.setFirstSurname(getAllLabelsDataFromUserInformationPane().get(1));
        originalDirectorData.setSecondSurname(getAllLabelsDataFromUserInformationPane().get(2));
        originalDirectorData.setEmailAddress(getAllLabelsDataFromUserInformationPane().get(3));
        originalDirectorData.setAlternateEmail(getAllLabelsDataFromUserInformationPane().get(4));
        originalDirectorData.setPhoneNumber(getAllLabelsDataFromUserInformationPane().get(5));
        originalDirectorData.setStatus(getAllLabelsDataFromUserInformationPane().get(6));
        originalDirectorData.setStaffNumber(Integer.parseInt(getAllLabelsDataFromUserInformationPane().get(7)));
        try{
            if(directorDAO.theDirectorIsAlreadyRegisted(newDirectorData)){
                new AlertPopUpGenerator().showCustomMessage(AlertType.WARNING, "Error", "El usuario ya está registrado en el sistema");
                return;
            }
        }catch(DataRetrievalException e){
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }
        try{
            directorDAO.modifyDirectorDataFromDatabase(newDirectorData, originalDirectorData);
            new AlertPopUpGenerator().showCustomMessage(AlertType.WARNING, "Éxito", "Usuario modificado exitosamente");
            returnToGuiUsers(event);
        }catch(DataInsertionException e) {
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }
    }
    private void modifyAcademicBodyHead(ActionEvent event){
        AcademicBodyHeadDAO academicBodyHeadDAO = new AcademicBodyHeadDAO();
        AcademicBodyHead newAcademicBodyHeadData = new AcademicBodyHead();
        newAcademicBodyHeadData.setName(namesTextField.getText());
        newAcademicBodyHeadData.setFirstSurname(firstSurnameTextField.getText());
        newAcademicBodyHeadData.setSecondSurname(secondSurnameTextField.getText());
        newAcademicBodyHeadData.setEmailAddress(emailTextField.getText());
        newAcademicBodyHeadData.setAlternateEmail(alternateEmailTextField.getText());
        newAcademicBodyHeadData.setPhoneNumber(telephoneNumberTextField.getText());
        newAcademicBodyHeadData.setStatus(statusComboBox.getValue());
        newAcademicBodyHeadData.setStaffNumber(Integer.parseInt(matricleOrPersonalNumberTextField.getText()));

        AcademicBodyHead originalAcademicBodyHeadData = new AcademicBodyHead();
        originalAcademicBodyHeadData.setName(getAllLabelsDataFromUserInformationPane().get(0));
        originalAcademicBodyHeadData.setFirstSurname(getAllLabelsDataFromUserInformationPane().get(1));
        originalAcademicBodyHeadData.setSecondSurname(getAllLabelsDataFromUserInformationPane().get(2));
        originalAcademicBodyHeadData.setEmailAddress(getAllLabelsDataFromUserInformationPane().get(3));
        originalAcademicBodyHeadData.setAlternateEmail(getAllLabelsDataFromUserInformationPane().get(4));
        originalAcademicBodyHeadData.setPhoneNumber(getAllLabelsDataFromUserInformationPane().get(5));
        originalAcademicBodyHeadData.setStatus(getAllLabelsDataFromUserInformationPane().get(6));
        originalAcademicBodyHeadData.setStaffNumber(Integer.parseInt(getAllLabelsDataFromUserInformationPane().get(7)));

        try{
            if(academicBodyHeadDAO.theAcademicBodyHeadIsAlreadyRegisted(newAcademicBodyHeadData)){
                new AlertPopUpGenerator().showCustomMessage(AlertType.WARNING, "Error", "El usuario ya está registrado en el sistema");
                return;
            }
        }catch(DataRetrievalException e){
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }
        try{
            academicBodyHeadDAO.modifyAcademicBodyHeadDataFromDatabase(newAcademicBodyHeadData, originalAcademicBodyHeadData);

            new AlertPopUpGenerator().showCustomMessage(AlertType.WARNING, "Éxito", "Usuario modificado exitosamente");
            returnToGuiUsers(event);
        }catch(DataInsertionException e){
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }
    }
    private void modifyDegreeBoss(ActionEvent event){
        DegreeBossDAO degreeBossDAO = new DegreeBossDAO();
        DegreeBoss newDegreeBossData = new DegreeBoss();
        newDegreeBossData.setName(namesTextField.getText());
        newDegreeBossData.setFirstSurname(firstSurnameTextField.getText());
        newDegreeBossData.setSecondSurname(secondSurnameTextField.getText());
        newDegreeBossData.setEmailAddress(emailTextField.getText());
        newDegreeBossData.setAlternateEmail(alternateEmailTextField.getText());
        newDegreeBossData.setPhoneNumber(telephoneNumberTextField.getText());
        newDegreeBossData.setStatus(statusComboBox.getValue());
        newDegreeBossData.setStaffNumber(Integer.parseInt(matricleOrPersonalNumberTextField.getText()));

        DegreeBoss originalDegreeBossData = new DegreeBoss();
        originalDegreeBossData.setName(getAllLabelsDataFromUserInformationPane().get(0));
        originalDegreeBossData.setFirstSurname(getAllLabelsDataFromUserInformationPane().get(1));
        originalDegreeBossData.setSecondSurname(getAllLabelsDataFromUserInformationPane().get(2));
        originalDegreeBossData.setEmailAddress(getAllLabelsDataFromUserInformationPane().get(3));
        originalDegreeBossData.setAlternateEmail(getAllLabelsDataFromUserInformationPane().get(4));
        originalDegreeBossData.setPhoneNumber(getAllLabelsDataFromUserInformationPane().get(5));
        originalDegreeBossData.setStatus(getAllLabelsDataFromUserInformationPane().get(6));
        originalDegreeBossData.setStaffNumber(Integer.parseInt(getAllLabelsDataFromUserInformationPane().get(7)));

        try{
            if(degreeBossDAO.theDegreeBossIsAlreadyRegisted(newDegreeBossData)){
                new AlertPopUpGenerator().showCustomMessage(AlertType.WARNING, "Error", "El usuario ya está registrado en el sistema");
                return;
            }
        }catch(DataRetrievalException e) {
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }
        try{
            degreeBossDAO.modifyDegreeBossDataFromDatabase(newDegreeBossData, originalDegreeBossData);

            new AlertPopUpGenerator().showCustomMessage(AlertType.WARNING, "Éxito", "Usuario modificado exitosamente");
            returnToGuiUsers(event);
        }catch(DataInsertionException e){
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }
    }
    private ArrayList<String> getAllLabelsDataFromUserInformationPane(){
        ArrayList<String> userInformationPaneData = new ArrayList<>();
        userInformationPaneData.add(userInformationController.getNames());
        userInformationPaneData.add(userInformationController.getFirstSurname());
        userInformationPaneData.add(userInformationController.getSecondSurname());
        userInformationPaneData.add(userInformationController.getEmail());
        userInformationPaneData.add(userInformationController.getAlternateEmail());
        userInformationPaneData.add(userInformationController.getTelephoneNumber());
        userInformationPaneData.add(userInformationController.getStatus());
        userInformationPaneData.add(userInformationController.getMatriculeOrPersonalNumber());
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
    
        if(userInformationController.getUserType().equals(UserType.STUDENT.getValue())){
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
    private void returnToGuiUsers(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/users/GuiUsers.fxml"));
            Parent parent = loader.load();
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(parent);
            String css = getClass().getResource("/mx/uv/fei/gui/stylesfiles/Styles.css").toExternalForm();
            scene.getStylesheets().add(css);
            stage.setTitle("SPGER");
            stage.setScene(scene);
            stage.show();
        }catch(IllegalStateException | IOException exception){
            AlertPopUpGenerator alertPopUpGenerator = new AlertPopUpGenerator();
            new AlertPopUpGenerator().showMissingFilesMessage();
        }
    }
}
