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
import mx.uv.fei.gui.controllers.AlertPaneController;
import mx.uv.fei.gui.controllers.courses.students.GuiUsersCourseController;

public class CourseInformationController {

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
    void adminUsersButtonController(ActionEvent event) {
        Parent guiUsersCourse;
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/mx/uv/fei/gui/fxml/courses/students/GuiUsersCourse.fxml")
            );

            GuiUsersCourseController guiUsersCourseController = new GuiUsersCourseController();
            guiUsersCourseController.setCourseInformationController(this);

            loader.setController(guiUsersCourseController);
            guiUsersCourse = loader.load();

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(guiUsersCourse);
            String css = this.getClass().getResource("/mx/uv/fei/gui/stylesfiles/Styles.css").toExternalForm();
            scene.getStylesheets().add(css);
            stage.setTitle("Administrar Usuarios");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            AlertPaneController alertPaneController = new AlertPaneController();
            alertPaneController.openErrorPane("Hubo un error, inténtelo más tarde");
        }   
    }

    @FXML
    void editButtonController(ActionEvent event) {
        this.guiCoursesController.openModifyCoursePane(this);
    }

    public GuiCoursesController getGuiCoursesController() {
        return guiCoursesController;
    }

    public void setGuiCoursesController(GuiCoursesController guiCoursesController) {
        this.guiCoursesController = guiCoursesController;
    }

    public String getBlock() {
        return this.blockLabel.getText();
    }

    public void setBlock(String block) {
        this.blockLabel.setText(block);
    }

    public String getEducativeExperience() {
        return this.educativeExperienceLabel.getText();
    }

    public void setEducativeExperience(String educativeExperience) {
        this.educativeExperienceLabel.setText(educativeExperience);
    }

    public String getNrc() {
        return this.nrcLabel.getText();
    }

    public void setNrc(String nrc) {
        this.nrcLabel.setText(nrc);
    }
    
    public String getProfessor() {
        return this.professorLabel.getText();
    }

    public void setProfessor(String professor) {
        this.professorLabel.setText(professor);
    }

    public String getScholarPeriod() {
        return this.scholarPeriodLabel.getText();
    }

    public void setScholarPeriod(String scholarPeriod) {
        this.scholarPeriodLabel.setText(scholarPeriod);
    }

    public String getSection() {
        return this.sectionLabel.getText();
    }

    public void setSection(String section) {
        this.sectionLabel.setText(section);;
    }

}