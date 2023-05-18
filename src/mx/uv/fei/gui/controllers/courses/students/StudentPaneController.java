package mx.uv.fei.gui.controllers.courses.students;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;

public class StudentPaneController {

    @FXML
    private Label matricleLabel;

    @FXML
    private RadioButton selectStudentRadioButton;

    @FXML
    private Label studentNameLabel;

    public String getMatricle() {
        return this.matricleLabel.getText();
    }

    public void setMatricle(String matricle) {
        this.matricleLabel.setText(matricle);
    }

    public String getStudentName() {
        return this.studentNameLabel.getText();
    }

    public void setStudentName(String studentName) {
        this.studentNameLabel.setText(studentName);
    }

}
