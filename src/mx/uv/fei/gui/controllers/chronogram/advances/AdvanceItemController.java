package mx.uv.fei.gui.controllers.chronogram.advances;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import mx.uv.fei.logic.domain.Advance;

public class AdvanceItemController {
    private Advance advance;
    
    private Stage stage;
    private Scene scene;
    private Parent parent;
    
    @FXML
    private Pane advanceListItem;

    @FXML
    private Label advanceTitleLabel;

    @FXML
    private Button seeAdvanceDetailsbutton;

    @FXML
    private void seeAdvanceDetails(ActionEvent event) {          
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/chronogram/AdvanceInfo.fxml"));
            parent = loader.load();
            AdvanceInfoController controller = (AdvanceInfoController)loader.getController();
            //controller.setAdvanceInfo(advance);
            //controller.setActivity();
            
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(parent);
            stage.setTitle("SPGER");
            stage.setScene(scene);
            stage.show();       
        } catch (IllegalStateException | IOException exception) {
            Alert errorMessage = new Alert(Alert.AlertType.ERROR);
            errorMessage.setHeaderText("Error de carga");
            errorMessage.setContentText("No se pudo abrir la ventana, verifique que el archivo .fxml esté en su ubicación correcta");
            errorMessage.showAndWait();
        }
    }

    private void setAdvanceTitleLabel(String advanceTitle) {
        advanceTitleLabel.setText(advanceTitle);
    }
    
    public void setAdvance(Advance advance) {
        this.advance = advance;
        setAdvanceTitleLabel(advance.getTitle());
    }
}
