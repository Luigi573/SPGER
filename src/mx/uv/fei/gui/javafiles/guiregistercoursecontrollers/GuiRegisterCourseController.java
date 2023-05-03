package mx.uv.fei.gui.javafiles.guiregistercoursecontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class GuiRegisterCourseController {

    @FXML
    private ComboBox<?> educativeExperienceComboBox;

    @FXML
    private Text errorMessajeText;

    @FXML
    private TextField nrcTextField;

    @FXML
    private ComboBox<?> periodComboBox;

    @FXML
    private Button registerButton;

    @FXML
    private ComboBox<?> sectionComboBox;

    @FXML
    private ComboBox<?> teacherComboBox;

    @FXML
    void registerButtonController(ActionEvent event) {

    }

}
