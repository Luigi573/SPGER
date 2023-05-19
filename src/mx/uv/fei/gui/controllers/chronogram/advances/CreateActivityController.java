package mx.uv.fei.gui.controllers.chronogram.advances;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author lisre
 */
public class CreateActivityController implements Initializable {

    @FXML
    private TextField advanceTitleTextField;
    @FXML
    private Button returnButton;
    @FXML
    private TextArea advanceCommentsTextArea;
    @FXML
    private Button createAdvanceButton;
    @FXML
    private Button uploadFileButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void returnToAdvanceList(ActionEvent event) {
    }

    @FXML
    private void createAdvance(ActionEvent event) {
    }

    @FXML
    private void uploadFileToAdvance(ActionEvent event) {
    }
    
}
