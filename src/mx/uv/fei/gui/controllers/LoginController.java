package mx.uv.fei.gui.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mx.uv.fei.gui.AlertPopUpGenerator;
import mx.uv.fei.logic.daos.LoginDAO;
import mx.uv.fei.logic.domain.AcademicBodyHead;
import mx.uv.fei.logic.domain.DegreeBoss;
import mx.uv.fei.logic.domain.Director;
import mx.uv.fei.logic.domain.Professor;
import mx.uv.fei.logic.domain.Student;
import mx.uv.fei.logic.domain.User;
import mx.uv.fei.logic.exceptions.LoginException;

public class LoginController{
    @FXML
    private TextField idTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Label errorLabel;

    @FXML
    private void logIn(ActionEvent event){        
        if(!idTextField.getText().isBlank() && !passwordField.getText().isBlank()){
            LoginDAO loginDAO = new LoginDAO(); 

            try{
                //Matricle and StaffNumber have their type default values if user is not found (null or '0')
                Student student = loginDAO.logInStudent(idTextField.getText(), passwordField.getText());

                if(student.getMatricle() != null){
                    openMainMenu(event, student);
                }else{
                    AcademicBodyHead academicBodyHead = loginDAO.logInAcademicBodyHead(idTextField.getText(), passwordField.getText());

                    if((academicBodyHead.getStaffNumber() > 0)){
                        openMainMenu(event, academicBodyHead);
                    }else{
                        DegreeBoss admin = loginDAO.logInAdmin(idTextField.getText(), passwordField.getText());
                        
                        if((admin.getStaffNumber() > 0)){
                            openMainMenu(event, admin);
                        }else{
                            Director director = loginDAO.logInDirector(idTextField.getText(), passwordField.getText());
                            
                            if(director.getStaffNumber() > 0){
                                openMainMenu(event, director);
                            }else{
                                Professor professor = loginDAO.logInProfessor(idTextField.getText(), passwordField.getText());

                                if((professor.getStaffNumber() > 0)){
                                    openMainMenu(event, professor);
                                }else{
                                    errorLabel.setText("Las credenciales ingresadas no coinciden con ningun usuario");
                                }
                            }
                        }
                    } 
                }
            }catch(LoginException exception){
                new AlertPopUpGenerator().showConnectionErrorMessage();
            }
        }else{
            errorLabel.setText("Faltan campos por llenar");
        }
    }
    
    private void openMainMenu(ActionEvent event, User user){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/MainMenu.fxml"));
            Parent parent = loader.load();
            MainMenuController controller = (MainMenuController)loader.getController();
            controller.setUser(user);
            controller.loadHeader();
            
            Scene scene = new Scene(parent);
            String css = this.getClass().getResource("/mx/uv/fei/gui/stylesfiles/Styles.css").toExternalForm();
            scene.getStylesheets().add(css);
            
            Stage stage = new Stage();
            stage.setTitle("SPGER");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
            
            Stage oldStage = (Stage)((Node)event.getSource()).getScene().getWindow();
            oldStage.close();
        }catch(IOException exception){            
            new AlertPopUpGenerator().showMissingFilesMessage();
        }
    }
}
