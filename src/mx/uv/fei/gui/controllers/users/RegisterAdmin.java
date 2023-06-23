package mx.uv.fei.gui.controllers.users;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.text.WordUtils;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mx.uv.fei.gui.AlertPopUpGenerator;
import mx.uv.fei.logic.PasswordAndEmailMaker;
import mx.uv.fei.logic.daos.DegreeBossDAO;
import mx.uv.fei.logic.domain.DegreeBoss;
import mx.uv.fei.logic.domain.UserType;
import mx.uv.fei.logic.domain.statuses.ProfessorStatus;
import mx.uv.fei.logic.exceptions.DataInsertionException;
import mx.uv.fei.logic.exceptions.DuplicatedPrimaryKeyException;

public class RegisterAdmin{

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
        typeComboBox.setValue(UserType.DEGREE_BOSS.getValue());
        typeComboBox.setDisable(true);
    }
    @FXML
    private void registerButtonController(ActionEvent event){
        if(allTextFieldsContainsData()){
            if(specifiedInvalidDataMessageError().equals("")){
                if(!emailTextField.getText().equals(alternateEmailTextField.getText())){
                    try {
                        DegreeBossDAO degreeBossDAO = new DegreeBossDAO();
                        DegreeBoss degreeBoss = new DegreeBoss();
                        degreeBoss.setName(WordUtils.capitalize(namesTextField.getText().toLowerCase()));
                        degreeBoss.setFirstSurname(WordUtils.capitalize(firstSurnameTextField.getText().toLowerCase()));
                        degreeBoss.setSecondSurname(WordUtils.capitalize(secondSurnameTextField.getText().toLowerCase()));
                        degreeBoss.setEmailAddress(emailTextField.getText());
                        degreeBoss.setAlternateEmail(alternateEmailTextField.getText());
                        degreeBoss.setPhoneNumber(telephoneNumberTextField.getText());
                        degreeBoss.setPassword(new PasswordAndEmailMaker().securePasswordMaker());
                        degreeBoss.setStatus(ProfessorStatus.ACTIVE.getValue());
                        degreeBoss.setStaffNumber(Integer.parseInt(matricleOrStaffNumberTextField.getText()));
                        degreeBossDAO.addDegreeBoss(degreeBoss);
                        new PasswordAndEmailMaker().sendPassword(degreeBoss.getEmailAddress(), degreeBoss.getPassword());
                        new PasswordAndEmailMaker().sendPassword(degreeBoss.getAlternateEmail(), degreeBoss.getPassword());
                        new AlertPopUpGenerator().showCustomMessage(AlertType.INFORMATION, "Éxito", "Jefe de Carrera registrado exitosamente");
                    }catch(DataInsertionException e){
                        new AlertPopUpGenerator().showConnectionErrorMessage();
                    }catch(DuplicatedPrimaryKeyException e){
                        new AlertPopUpGenerator().showCustomMessage(AlertType.ERROR, "Error", "El número de personal ya está usado");
                    }

                    Stage stage = (Stage) registerButton.getScene().getWindow();
                    stage.close();
                }else{
                    new AlertPopUpGenerator().showCustomMessage(AlertType.WARNING, "Error", "El correo electrónico no puede ser el mismo que el correo alterno");
                }
            }else{
                new AlertPopUpGenerator().showCustomMessage(AlertType.WARNING, "Error", specifiedInvalidDataMessageError());
            }
        }else{
            new AlertPopUpGenerator().showCustomMessage(AlertType.WARNING, "Error", "Faltan campos por llenar");
        }
    }
    private boolean allTextFieldsContainsData(){
        return !namesTextField.getText().trim().isEmpty() && !firstSurnameTextField.getText().trim().isEmpty() &&
               !secondSurnameTextField.getText().trim().isEmpty() && !emailTextField.getText().trim().isEmpty() &&
               !alternateEmailTextField.getText().trim().isEmpty() && !telephoneNumberTextField.getText().trim().isEmpty() &&
               !matricleOrStaffNumberTextField.getText().trim().isEmpty();
    }
    private String specifiedInvalidDataMessageError(){
        String message = "";

        Pattern namesPattern = Pattern.compile("^[A-Za-zÁÉÍÓÚÜÑáéíóúüñ\\s]+(?: [A-Za-zÁÉÍÓÚÜÑáéíóúüñ\\s]+)*$"),
                firstSurnamePattern = Pattern.compile("^[A-Za-zÁÉÍÓÚÜÑáéíóúüñ\\s]+(?: [A-Za-zÁÉÍÓÚÜÑáéíóúüñ\\s]+)*$"),
                secondSurnamePattern = Pattern.compile("^[A-Za-zÁÉÍÓÚÜÑáéíóúüñ\\s]+(?: [A-Za-zÁÉÍÓÚÜÑáéíóúüñ\\s]+)*$"),
                alternateEmailPattern = Pattern.compile("^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$"),
                telephoneNumberPattern = Pattern.compile("^[0-9]{10}$"),
                matricleOrStaffNumberPattern = Pattern.compile("^[0-9]{1,9}$"),
                emailPattern = Pattern.compile("^(.+)@uv.mx$");

        Matcher namesMatcher = namesPattern.matcher(namesTextField.getText()),
                firstSurnameMatcher = firstSurnamePattern.matcher(firstSurnameTextField.getText()),
                secondSurnameMatcher = secondSurnamePattern.matcher(secondSurnameTextField.getText()),
                emailMatcher = emailPattern.matcher(emailTextField.getText()),
                alternateEmailMatcher = alternateEmailPattern.matcher(alternateEmailTextField.getText()),
                telephoneNumberMatcher = telephoneNumberPattern.matcher(telephoneNumberTextField.getText()),
                matricleOrStaffNumberMatcher = matricleOrStaffNumberPattern.matcher(matricleOrStaffNumberTextField.getText());

        if(!namesMatcher.find()){
            if(message.equals("")){
                message = "nombre";
            }else{
                message = message + ", nombre";
            }
        }

        if(!firstSurnameMatcher.find()){
            if(message.equals("")){
                message = "apellido paterno";
            }else{
                message = message + ", apellido paterno";
            }
        }

        if(!secondSurnameMatcher.find()){
            if(message.equals("")){
                message = "apellido materno";
            }else{
                message = message + ", apellido materno";
            }
        }

        if(!emailMatcher.find()){
            if(message.equals("")){
                message = "correo electrónico";
            }else{
                message = message + ", correo electrónico";
            }
        }

        if(!alternateEmailMatcher.find()){
            if(message.equals("")){
                message = "correo alterno";
            }else{
                message = message + ", correo alterno";
            }
        }

        if(!telephoneNumberMatcher.find()){
            if(message.equals("")){
                message = "número de teléfono";
            }else{
                message = message + ", número de teléfono";
            }
        }

        if(!matricleOrStaffNumberMatcher.find()){
            if(message.equals("")){
                message = "matrícula o número de personal";
            }else{
                message = message + ", matrícula o número de personal";
            }
        }

        if(!message.equals("")){
            message = "Los campos que tienen datos inválidos son: " + message + ".";
        }

        return message;
    }
}