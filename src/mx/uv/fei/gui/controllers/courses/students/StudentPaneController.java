package mx.uv.fei.gui.controllers.courses.students;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.Pane;

public class StudentPaneController{
    @FXML
    private Label matricleLabel;
    @FXML
    private RadioButton selectStudentRadioButton;
    @FXML
    private Label studentNameLabel;
    @FXML
    private Pane studentPane;

    public String getMatricle(){
        return matricleLabel.getText();
    }
    public void setMatricle(String matricle){
        matricleLabel.setText(matricle);
    }
    public String getStudentName(){
        return studentNameLabel.getText();
    }
    public void setStudentName(String studentName){
        studentNameLabel.setText(studentName);
    }
}
