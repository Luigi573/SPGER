package mx.uv.fei.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import mx.uv.fei.logic.daos.KGALDAO;
import mx.uv.fei.logic.exceptions.DataRetrievalException;

public class UpdateKGALController {

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnSave;

    @FXML
    private TextField tfDescription;

    @FXML
    private TextField tfID;

    @FXML
    void updateKGALDescription(ActionEvent event) {
        int kgalID = Integer.parseInt(tfID.getText());
        String newKgalDescription = tfDescription.getText();
        KGALDAO kgalDAO = new KGALDAO();
        try {
            kgalDAO.updateKGALDescription(kgalID, newKgalDescription);
        } catch (DataRetrievalException dre) {
            System.out.println(dre.getMessage());
        }
    }

    @FXML
    void switchToKGALList(ActionEvent event) {

    }

}
