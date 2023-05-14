package mx.uv.fei.gui.controllers.courses;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class CourseInformationController {

    private GuiCoursesController guiCoursesController;

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