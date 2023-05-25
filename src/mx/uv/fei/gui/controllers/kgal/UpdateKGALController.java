package mx.uv.fei.gui.controllers.kgal;

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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mx.uv.fei.logic.daos.KGALDAO;
import mx.uv.fei.logic.domain.KGAL;
import mx.uv.fei.logic.exceptions.DataRetrievalException;

public class UpdateKGALController {
    
    private KGAL kgal;
    
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button btnCancel;
    @FXML
    private Button btnSave;
    @FXML
    private TextField tfDescription;
    @FXML
    private Label lID;

    @FXML
    public void initialize() {
    }
    
    @FXML
    public void updateKGALDescription(ActionEvent event) {
        String newKgalDescription = tfDescription.getText();
        int result = 0;
        KGALDAO kgalDAO = new KGALDAO();
        try {
            result = kgalDAO.updateKGALDescription(this.kgal.getKgalID(), newKgalDescription);
        } catch (DataRetrievalException dre) {
            Alert errorMessage = new Alert(Alert.AlertType.ERROR);
            errorMessage.setHeaderText("Error al mostrar la información");
            errorMessage.setContentText(dre.getMessage());
            errorMessage.showAndWait();
        }
        if(result > 0) {
            Alert successMessage = new Alert(Alert.AlertType.CONFIRMATION);
            successMessage.setHeaderText("Operación exitosa");
            successMessage.setContentText("Se ha guardado la nueva Descripción de la LGAC correctamente.");
            successMessage.showAndWait();
        }
    }

    @FXML
    public void switchToKGALListScene(ActionEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("/mx/uv/fei/gui/fxml/kgal/KGALList.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            Alert errorMessage = new Alert(Alert.AlertType.ERROR);
            errorMessage.setHeaderText("Error al mostrar la información");
            errorMessage.setContentText(e.getMessage());
            errorMessage.showAndWait();
        }
    }

    public void setKGAL(KGAL kgal) {
        this.kgal = kgal;
        String lKgalID = Integer.toString(kgal.getKgalID());
        tfDescription.setText(kgal.getDescription());
        lID.setText(lKgalID);
    }
}
