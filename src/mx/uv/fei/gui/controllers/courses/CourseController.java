package mx.uv.fei.gui.controllers.courses;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class CourseController {

    private GuiCoursesController guiCoursesController;

    @FXML
    private Label nameLabel;

    @FXML
    private Label nrcLabel;

    @FXML
    private Label scholarPeriodLabel;

    @FXML
    private Button userButton;

    @FXML
    void userButtonController(ActionEvent event) {
        this.guiCoursesController.openPaneWithCourseInformation(this.getNrc());
    }

    public GuiCoursesController getGuiCoursesController() {
        return guiCoursesController;
    }

    public void setGuiCoursesController(GuiCoursesController guiCoursesController) {
        this.guiCoursesController = guiCoursesController;
    }

    public String getEEName() {
        return this.nameLabel.getText();
    }

    public void setEEName(String name) {
        this.nameLabel.setText(name);
    }

    public String getNrc() {
        return this.nrcLabel.getText();
    }

    public void setNrc(String nrc) {
        this.nrcLabel.setText(nrc);
    }

    public String getScholarPeriod() {
        return this.scholarPeriodLabel.getText();
    }

    public void setScholarPeriod(String status) {
        this.scholarPeriodLabel.setText(status);
    }

}