package mx.uv.fei.gui.controllers.users;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
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
import mx.uv.fei.logic.exceptions.DataRetrievalException;

public class GuiUsersController{

    @FXML
    private Pane backgroundPane;
    @FXML
    private ToggleButton onlyShowActiveUsersButton;
    @FXML
    private Button registerUserButton;
    @FXML
    private Button searchByNameButton;
    @FXML
    private TextField searchByNameTextField;
    @FXML
    private ScrollPane userInformationScrollPane;
    @FXML
    private VBox usersVBox;

    @FXML
    private void initialize(){
        //loadHeader();
        loadUserButtons();
    }
    @FXML
    private void registerUserButtonController(ActionEvent event){
        Parent guiRegisterUser;
        try{
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/mx/uv/fei/gui/fxml/users/GuiRegisterUser.fxml")
            );
            guiRegisterUser = loader.load();
            GuiRegisterUserController guiRegisterUserController = loader.getController();
            guiRegisterUserController.setGuiUsersController(this);
            Scene scene = new Scene(guiRegisterUser);
            String css = getClass().getResource("/mx/uv/fei/gui/stylesfiles/Styles.css").toExternalForm();
            scene.getStylesheets().add(css);
            Stage stage = new Stage();
            stage.setTitle("Registrar Usuario");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner((Stage)((Node)event.getSource()).getScene().getWindow());
            stage.setScene(scene);
            stage.show();
        }catch (IOException e){
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }   
    }
    @FXML
    private void searchByNameButtonController(ActionEvent event){
        usersVBox.getChildren().removeAll(usersVBox.getChildren());
        StudentDAO studentDAO = new StudentDAO();
        ProfessorDAO professorDAO = new ProfessorDAO();
        DirectorDAO directorDAO = new DirectorDAO();
        AcademicBodyHeadDAO academicBodyHeadDAO = new AcademicBodyHeadDAO();
        DegreeBossDAO degreeBossDAO = new DegreeBossDAO();
        try{
            ArrayList<Student> students = studentDAO.getSpecifiedStudents(searchByNameTextField.getText());
            ArrayList<Professor> professors = professorDAO.getSpecifiedProfessors(searchByNameTextField.getText());
            ArrayList<Director> directors = directorDAO.getSpecifiedDirectors(searchByNameTextField.getText());
            ArrayList<AcademicBodyHead> academicBodyHeads = academicBodyHeadDAO.getSpecifiedAcademicBodyHeads(searchByNameTextField.getText());
            ArrayList<DegreeBoss> degreeBosses = degreeBossDAO.getSpecifiedDegreeBosses(searchByNameTextField.getText());
    
            directorButtonMaker(directors);
            academicBodyHeadButtonMaker(academicBodyHeads);
            degreeBossButtonMaker(degreeBosses);
            professorButtonMaker(professors);
            studentButtonMaker(students);
        }catch(DataRetrievalException e){
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }
    }

    public void loadUserButtons(){
        usersVBox.getChildren().removeAll(usersVBox.getChildren());
        StudentDAO studentDAO = new StudentDAO();
        ProfessorDAO professorDAO = new ProfessorDAO();
        DirectorDAO directorDAO = new DirectorDAO();
        AcademicBodyHeadDAO academicBodyHeadDAO = new AcademicBodyHeadDAO();
        DegreeBossDAO degreeBossDAO = new DegreeBossDAO();
        try {
            ArrayList<Student> students = studentDAO.getStudents();
            ArrayList<Professor> professors = professorDAO.getProfessors();
            ArrayList<Director> directors = directorDAO.getDirectors();
            ArrayList<AcademicBodyHead> academicBodyHeads = academicBodyHeadDAO.getAcademicBodyHeads();
            ArrayList<DegreeBoss> degreeBosses = degreeBossDAO.getDegreeBosses();
            
            directorButtonMaker(directors);
            academicBodyHeadButtonMaker(academicBodyHeads);
            degreeBossButtonMaker(degreeBosses);
            professorButtonMaker(professors);
            studentButtonMaker(students);
        } catch (DataRetrievalException e) {
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }
    }
    //This method only should be used by the UserInformationController Class.
    public void openModifyUserPane(UserInformationController userInformationController){
        FXMLLoader modifyUserInformationControllerLoader = new FXMLLoader(
            getClass().getResource("/mx/uv/fei/gui/fxml/users/ModifyUserInformation.fxml")
        );

        try{
            VBox modifyUserInformationVBox = modifyUserInformationControllerLoader.load();
            ModifyUserInformationController modifyUserInformationController = modifyUserInformationControllerLoader.getController();
            modifyUserInformationController.setUser(userInformationController.getUser());
            modifyUserInformationController.setNames(userInformationController.getNames());
            modifyUserInformationController.setFirstSurname(userInformationController.getFirstSurname());
            modifyUserInformationController.setSecondSurname(userInformationController.getSecondSurname());
            modifyUserInformationController.setEmail(userInformationController.getEmail());
            modifyUserInformationController.setAlternateEmail(userInformationController.getAlternateEmail());
            modifyUserInformationController.setTelephoneNumber(userInformationController.getTelephoneNumber());
            modifyUserInformationController.setMatricleOrStaffNumber(userInformationController.getMatricleOrStaffNumber());
            modifyUserInformationController.setStatus(userInformationController.getStatus());
            modifyUserInformationController.setDataToStatusCombobox(userInformationController.getUserType());
            modifyUserInformationController.setLabelsCorrectBounds(userInformationController.getUserType());
            modifyUserInformationController.setUserInformationController(userInformationController);
            userInformationScrollPane.setContent(modifyUserInformationVBox);
            
        } catch (IOException e){
            new AlertPopUpGenerator().showMissingFilesMessage();
        }
    }
    //This method only should be used by the UserController Class.
    public void openPaneWithUserInformation(UserController userController){
        try{
            if(userController.getType().equals(UserType.DIRECTOR.getValue())){
                DirectorDAO directorDAO = new DirectorDAO();
                Director director = directorDAO.getDirector(
                    Integer.parseInt(userController.getMatricleOrStaffNumber())
                );
                openPaneWithDirectorInformation(director);
            }
            
            if(userController.getType().equals(UserType.ACADEMIC_BODY_HEAD.getValue())){
                AcademicBodyHeadDAO academicBodyHeadDAO = new AcademicBodyHeadDAO();
                AcademicBodyHead academicBodyHead = academicBodyHeadDAO.getAcademicBodyHead(
                    Integer.parseInt(userController.getMatricleOrStaffNumber())
                );
                openPaneWithAcademicBodyHeadInformation(academicBodyHead);
            }
            
            if(userController.getType().equals(UserType.DEGREE_BOSS.getValue())){
                DegreeBossDAO degreeBossDAO = new DegreeBossDAO();
                DegreeBoss degreeBoss = degreeBossDAO.getDegreeBoss(
                    Integer.parseInt(userController.getMatricleOrStaffNumber())
                );
                openPaneWithDegreeBossInformation(degreeBoss);
            }

            if(userController.getType().equals(UserType.PROFESSOR.getValue())){
                ProfessorDAO professorDAO = new ProfessorDAO();
                Professor professor = professorDAO.getProfessor(
                    Integer.parseInt(userController.getMatricleOrStaffNumber())
                );
                openPaneWithProfessorInformation(professor);
            }

            if(userController.getType().equals(UserType.STUDENT.getValue())){
                StudentDAO studentDAO = new StudentDAO();
                Student student = studentDAO.getStudent(
                    userController.getMatricleOrStaffNumber()
                );
                openPaneWithStudentInformation(student);
            }
        }catch(NumberFormatException e){
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }catch(DataRetrievalException e){
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }
    }

    //private void loadHeader(){
    //    FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/HeaderPane.fxml"));
    //    
    //    try{
    //        Pane header = loader.load();
    //        header.getStyleClass().add("/mx/uv/fei/gui/stylesfiles/Styles.css");
    //        backgroundPane.getChildren().add(header);
    //    } catch(IOException exception){
    //        new AlertPopUpGenerator().showMissingFilesMessage();
    //    }
    //}

    private void studentButtonMaker(ArrayList<Student> students){
        try{
            for(Student student : students){
                FXMLLoader userItemControllerLoader = new FXMLLoader(
                    getClass().getResource("/mx/uv/fei/gui/fxml/users/User.fxml")
                );
                Pane userItemPane;
                userItemPane = userItemControllerLoader.load();
                UserController userController = userItemControllerLoader.getController();
                userController.setName(student.getName() + " " + student.getFirstSurname() + " " + student.getSecondSurname());
                userController.setType(UserType.STUDENT.getValue());
                userController.setMatricleOrStaffNumber(student.getMatricle());
                userController.setMatricleOrStaffNumberText("Matrícula: ");
                userController.setLabelsCorrectBounds(UserType.STUDENT.getValue());
                userController.setGuiUsersController(this);
                usersVBox.getChildren().add(userItemPane);
            }
        }catch(IOException e){
            new AlertPopUpGenerator().showMissingFilesMessage();
        }    
    }
    private void professorButtonMaker(ArrayList<Professor> professors){
        try{
            for(Professor professor : professors){
                boolean professorAlreadyInUserVbox = false;
                for(Node pane : ((VBox)usersVBox).getChildren()) {
                    Node label = ((Pane)pane).getChildren().get(5);
                    if(((Label)label).getText().equals(Integer.toString(professor.getStaffNumber()))) {
                        professorAlreadyInUserVbox = true;
                        break;
                    }
                }

                if(!professorAlreadyInUserVbox){
                    FXMLLoader userItemControllerLoader = new FXMLLoader(
                        getClass().getResource("/mx/uv/fei/gui/fxml/users/User.fxml")
                    );
                    Pane userItemPane = userItemControllerLoader.load();
                    UserController userController = userItemControllerLoader.getController();
                    userController.setName(professor.getName() + " " + professor.getFirstSurname() + " " + professor.getSecondSurname());
                    userController.setType(UserType.PROFESSOR.getValue());
                    userController.setMatricleOrStaffNumber(Integer.toString(professor.getStaffNumber()));
                    userController.setMatricleOrStaffNumberText("Número de Personal: ");
                    userController.setLabelsCorrectBounds(UserType.PROFESSOR.getValue());
                    userController.setGuiUsersController(this);
                    usersVBox.getChildren().add(userItemPane);
                }
            }
        }catch(IOException e){
            new AlertPopUpGenerator().showMissingFilesMessage();
        }    
    }
    private void directorButtonMaker(ArrayList<Director> directors){
        try{
            for(Director director : directors){
                FXMLLoader userItemControllerLoader = new FXMLLoader(
                    getClass().getResource("/mx/uv/fei/gui/fxml/users/User.fxml")
                );
                Pane userItemPane = userItemControllerLoader.load();
                UserController userController = userItemControllerLoader.getController();
                userController.setName(director.getName() + " " + director.getFirstSurname() + " " + director.getSecondSurname());
                userController.setType(UserType.DIRECTOR.getValue());
                userController.setMatricleOrStaffNumber(Integer.toString(director.getStaffNumber()));
                userController.setMatricleOrStaffNumberText("Número de Personal: ");
                userController.setLabelsCorrectBounds(UserType.DIRECTOR.getValue());
                userController.setGuiUsersController(this);
                usersVBox.getChildren().add(userItemPane);
            }
        }catch(IOException e){
            new AlertPopUpGenerator().showMissingFilesMessage();
        }  
    }
    private void academicBodyHeadButtonMaker(ArrayList<AcademicBodyHead> academicBodyHeads){
        try{
            for(AcademicBodyHead academicBodyHead : academicBodyHeads){
                FXMLLoader userItemControllerLoader = new FXMLLoader(
                    getClass().getResource("/mx/uv/fei/gui/fxml/users/User.fxml")
                );
                Pane userItemPane = userItemControllerLoader.load();
                UserController userController = userItemControllerLoader.getController();
                userController.setName(academicBodyHead.getName() + " " + academicBodyHead.getFirstSurname() + " " + academicBodyHead.getSecondSurname());
                userController.setType(UserType.ACADEMIC_BODY_HEAD.getValue());
                userController.setMatricleOrStaffNumber(Integer.toString(academicBodyHead.getStaffNumber()));
                userController.setMatricleOrStaffNumberText("Número de Personal: ");
                userController.setLabelsCorrectBounds(UserType.ACADEMIC_BODY_HEAD.getValue());
                userController.setGuiUsersController(this);
                usersVBox.getChildren().add(userItemPane);
            }
        }catch(IOException e){
            new AlertPopUpGenerator().showMissingFilesMessage();
        }  
    }
    private void degreeBossButtonMaker(ArrayList<DegreeBoss> degreeBosses){
        try{
            for(DegreeBoss degreeBoss : degreeBosses){
                FXMLLoader userItemControllerLoader = new FXMLLoader(
                    getClass().getResource("/mx/uv/fei/gui/fxml/users/User.fxml")
                );
                Pane userItemPane = userItemControllerLoader.load();
                UserController userController = userItemControllerLoader.getController();
                userController.setName(degreeBoss.getName() + " " + degreeBoss.getFirstSurname() + " " + degreeBoss.getSecondSurname());
                userController.setType(UserType.DEGREE_BOSS.getValue());
                userController.setMatricleOrStaffNumber(Integer.toString(degreeBoss.getStaffNumber()));
                userController.setMatricleOrStaffNumberText("Número de Personal: ");
                userController.setLabelsCorrectBounds(UserType.DEGREE_BOSS.getValue());
                userController.setGuiUsersController(this);
                usersVBox.getChildren().add(userItemPane);
            }
        }catch(IOException e){
            new AlertPopUpGenerator().showMissingFilesMessage();
        }  
    }
    private void openPaneWithDirectorInformation(Director director){
        FXMLLoader userInformationControllerLoader = new FXMLLoader(
            getClass().getResource("/mx/uv/fei/gui/fxml/users/UserInformation.fxml")
        );
        try{
            VBox userInformationVBox = userInformationControllerLoader.load();
            UserInformationController userInformationController = userInformationControllerLoader.getController();
            userInformationController.setUser(director);
            userInformationController.setNames(director.getName());
            userInformationController.setFirstSurname(director.getFirstSurname());
            userInformationController.setSecondSurname(director.getSecondSurname());
            userInformationController.setEmail(director.getEmailAddress());
            userInformationController.setAlternateEmail(director.getAlternateEmail());
            userInformationController.setTelephoneNumber(director.getPhoneNumber());
            userInformationController.setUserType(UserType.DIRECTOR.getValue());
            userInformationController.setStatus(director.getStatus());
            userInformationController.setMatricleOrStaffNumber(Integer.toString(director.getStaffNumber()));
            userInformationController.setGuiUsersController(this);
            userInformationController.setMatricleOrStaffNumberText();
            userInformationScrollPane.setContent(userInformationVBox);
            
        }catch (IOException e){
            new AlertPopUpGenerator().showMissingFilesMessage();
        }
    }
    private void openPaneWithAcademicBodyHeadInformation(AcademicBodyHead academicBodyHead){
        FXMLLoader userInformationControllerLoader = new FXMLLoader(
            getClass().getResource("/mx/uv/fei/gui/fxml/users/UserInformation.fxml")
        );

        try{
            VBox userInformationVBox = userInformationControllerLoader.load();
            UserInformationController userInformationController = userInformationControllerLoader.getController();
            userInformationController.setUser(academicBodyHead);
            userInformationController.setNames(academicBodyHead.getName());
            userInformationController.setFirstSurname(academicBodyHead.getFirstSurname());
            userInformationController.setSecondSurname(academicBodyHead.getSecondSurname());
            userInformationController.setEmail(academicBodyHead.getEmailAddress());
            userInformationController.setAlternateEmail(academicBodyHead.getAlternateEmail());
            userInformationController.setTelephoneNumber(academicBodyHead.getPhoneNumber());
            userInformationController.setUserType(UserType.ACADEMIC_BODY_HEAD.getValue());
            userInformationController.setStatus(academicBodyHead.getStatus());
            userInformationController.setMatricleOrStaffNumber(Integer.toString(academicBodyHead.getStaffNumber()));
            userInformationController.setGuiUsersController(this);
            userInformationController.setMatricleOrStaffNumberText();
            userInformationScrollPane.setContent(userInformationVBox);
            
        }catch(IOException e){
            new AlertPopUpGenerator().showMissingFilesMessage();
        }
    }

    private void openPaneWithDegreeBossInformation(DegreeBoss degreeBoss){
        FXMLLoader userInformationControllerLoader = new FXMLLoader(
            getClass().getResource("/mx/uv/fei/gui/fxml/users/UserInformation.fxml")
        );

        try{
            VBox userInformationVBox = userInformationControllerLoader.load();
            UserInformationController userInformationController = userInformationControllerLoader.getController();
            userInformationController.setUser(degreeBoss);
            userInformationController.setNames(degreeBoss.getName());
            userInformationController.setFirstSurname(degreeBoss.getFirstSurname());
            userInformationController.setSecondSurname(degreeBoss.getSecondSurname());
            userInformationController.setEmail(degreeBoss.getEmailAddress());
            userInformationController.setAlternateEmail(degreeBoss.getAlternateEmail());
            userInformationController.setTelephoneNumber(degreeBoss.getPhoneNumber());
            userInformationController.setUserType(UserType.DEGREE_BOSS.getValue());
            userInformationController.setStatus(degreeBoss.getStatus());
            userInformationController.setMatricleOrStaffNumber(Integer.toString(degreeBoss.getStaffNumber()));
            userInformationController.setGuiUsersController(this);
            userInformationController.setMatricleOrStaffNumberText();
            userInformationScrollPane.setContent(userInformationVBox);
            
        }catch(IOException e){
            new AlertPopUpGenerator().showMissingFilesMessage();
        }
    }
    private void openPaneWithProfessorInformation(Professor professor){
        FXMLLoader userInformationControllerLoader = new FXMLLoader(
            getClass().getResource("/mx/uv/fei/gui/fxml/users/UserInformation.fxml")
        );

        try{
            VBox userInformationVBox = userInformationControllerLoader.load();
            UserInformationController userInformationController = userInformationControllerLoader.getController();
            userInformationController.setUser(professor);
            userInformationController.setNames(professor.getName());
            userInformationController.setFirstSurname(professor.getFirstSurname());
            userInformationController.setSecondSurname(professor.getSecondSurname());
            userInformationController.setEmail(professor.getEmailAddress());
            userInformationController.setAlternateEmail(professor.getAlternateEmail());
            userInformationController.setTelephoneNumber(professor.getPhoneNumber());
            userInformationController.setUserType(UserType.PROFESSOR.getValue());
            userInformationController.setStatus(professor.getStatus());
            userInformationController.setMatricleOrStaffNumber(Integer.toString(professor.getStaffNumber()));
            userInformationController.setGuiUsersController(this);
            userInformationController.setMatricleOrStaffNumberText();
            userInformationScrollPane.setContent(userInformationVBox);
            
        } catch (IOException e){
            new AlertPopUpGenerator().showMissingFilesMessage();
        }
    }
    private void openPaneWithStudentInformation(Student student){
        FXMLLoader userInformationControllerLoader = new FXMLLoader(
            getClass().getResource("/mx/uv/fei/gui/fxml/users/UserInformation.fxml")
        );

        try{
            VBox userInformationVBox = userInformationControllerLoader.load();
            UserInformationController userInformationController = userInformationControllerLoader.getController();
            userInformationController.setUser(student);
            userInformationController.setNames(student.getName());
            userInformationController.setFirstSurname(student.getFirstSurname());
            userInformationController.setSecondSurname(student.getSecondSurname());
            userInformationController.setEmail(student.getEmailAddress());
            userInformationController.setAlternateEmail(student.getAlternateEmail());
            userInformationController.setTelephoneNumber(student.getPhoneNumber());
            userInformationController.setUserType(UserType.STUDENT.getValue());
            userInformationController.setStatus(student.getStatus());
            userInformationController.setMatricleOrStaffNumber(student.getMatricle());
            userInformationController.setGuiUsersController(this);
            userInformationController.setMatricleOrStaffNumberText();
            userInformationScrollPane.setContent(userInformationVBox);
            
        }catch(IOException e){
            new AlertPopUpGenerator().showMissingFilesMessage();
        }
    }
}