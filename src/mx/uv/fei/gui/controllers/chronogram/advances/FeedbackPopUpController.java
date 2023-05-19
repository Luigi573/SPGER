/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package mx.uv.fei.gui.controllers.chronogram.advances;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import mx.uv.fei.gui.AlertPopUpGenerator;
import mx.uv.fei.logic.daos.AdvanceDAO;
import mx.uv.fei.logic.domain.Advance;
import mx.uv.fei.logic.exceptions.DataInsertionException;

public class FeedbackPopUpController{
    private Advance advance;
    
    @FXML
    private TextArea feedbackTextArea;

    @FXML
    private void submitFeedback(ActionEvent event) {
        if(!feedbackTextArea.getText().isBlank()){
            advance.setFeedback(feedbackTextArea.getText());
            
            AdvanceDAO advanceDAO = new AdvanceDAO();
            try{
                if(advanceDAO.setFeedback(advance) > 0){
                    AlertPopUpGenerator.showCustomMessage(Alert.AlertType.INFORMATION, "Mensaje de éxito", "Retroalimentación guardada exitosamente");
                }
                
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.close();
            }catch(DataInsertionException exception){
                AlertPopUpGenerator.showConnectionErrorMessage();
            }
        }else{
            AlertPopUpGenerator.showCustomMessage(Alert.AlertType.WARNING, "No se pudo agregar la retroalimentacion", "Favor de insertar texto");
        }
    }
    
    public void setAdvance(Advance advance){
        this.advance = advance;
        
        if(advance.getFeedback() != null){
            feedbackTextArea.setText(advance.getFeedback());
        }
    }
}
