package mx.uv.fei.gui.controllers.courses;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class CourseController{
    private GuiCoursesController guiCoursesController;

    @FXML
    private Pane courseButton;
    @FXML
    private Label nameLabel;
    @FXML
    private Label nrcLabel;
    @FXML
    private Label scholarPeriodLabel;
    @FXML
    private Button userButton;

    @FXML
    private void userButtonController(ActionEvent event){
        this.guiCoursesController.openPaneWithCourseInformation(getNrc());
    }

    public GuiCoursesController getGuiCoursesController(){
        return guiCoursesController;
    }
    public void setGuiCoursesController(GuiCoursesController guiCoursesController){
        this.guiCoursesController = guiCoursesController;
    }
    public String getName(){
        return nameLabel.getText();
    }
    public void setName(String name){
        this.nameLabel.setText(name);
    }
    public String getNrc(){
        return nrcLabel.getText();
    }
    public void setNrc(String nrc){
        this.nrcLabel.setText(nrc);
    }
    public String getScholarPeriod(){
        return scholarPeriodLabel.getText();
    }
    public void setScholarPeriod(String status){
        this.scholarPeriodLabel.setText(status);
    }
}