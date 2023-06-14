package mx.uv.fei.gui.controllers.courses;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import mx.uv.fei.gui.AlertPopUpGenerator;
import mx.uv.fei.gui.controllers.courses.students.GuiUsersCourseController;

public class CourseInformationController{
    private GuiCoursesController guiCoursesController;

    @FXML
    private Button adminUsersButton;
    @FXML
    private Label blockLabel;
    @FXML
    private Button editButton;
    @FXML
    private Label educativeExperienceLabel;
    @FXML
    private Label nrcLabel;
    @FXML
    private Label professorLabel;
    @FXML
    private Label scholarPeriodLabel;
    @FXML
    private Label sectionLabel;
    @FXML
    private Label statusLabel;

    @FXML
    private void adminUsersButtonController(ActionEvent event){
        Parent guiUsersCourse;
        try{
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/mx/uv/fei/gui/fxml/courses/students/GuiUsersCourse.fxml")
            );

            GuiUsersCourseController guiUsersCourseController = new GuiUsersCourseController();
            guiUsersCourseController.setCourseInformationController(this);
            guiUsersCourseController.setUser(guiCoursesController.getUser());

            loader.setController(guiUsersCourseController);
            guiUsersCourse = loader.load();

            Scene scene = new Scene(guiUsersCourse);
            String css = getClass().getResource("/mx/uv/fei/gui/stylesfiles/Styles.css").toExternalForm();
            scene.getStylesheets().add(css);
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setTitle("Administrar Usuarios");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }catch(IOException e){
            new AlertPopUpGenerator().showMissingFilesMessage();
        }   
    }
    @FXML
    private void editButtonController(ActionEvent event){
        guiCoursesController.openModifyCoursePane(this);
    }

    public GuiCoursesController getGuiCoursesController(){
        return guiCoursesController;
    }
    public void setGuiCoursesController(GuiCoursesController guiCoursesController){
        this.guiCoursesController = guiCoursesController;
    }
    public String getBlock(){
        return blockLabel.getText();
    }
    public void setBlock(String block){
        blockLabel.setText(block);
    }
    public String getEducativeExperience(){
        return educativeExperienceLabel.getText();
    }
    public void setEducativeExperience(String educativeExperience){
        educativeExperienceLabel.setText(educativeExperience);
    }
    public String getNrc(){
        return nrcLabel.getText();
    }
    public void setNrc(String nrc){
        nrcLabel.setText(nrc);
    }   
    public String getProfessor(){
        return professorLabel.getText();
    }
    public void setProfessor(String professor){
        professorLabel.setText(professor);
    }
    public String getScholarPeriod(){
        return scholarPeriodLabel.getText();
    }
    public void setScholarPeriod(String scholarPeriod){
        scholarPeriodLabel.setText(scholarPeriod);
    }
    public String getSection(){
        return sectionLabel.getText();
    }
    public void setSection(String section){
        sectionLabel.setText(section);
    }
    public String getStatus(){
        return statusLabel.getText();
    }
    public void setStatus(String status){
        statusLabel.setText(status);
    }
}