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
import mx.uv.fei.logic.domain.Director;
import mx.uv.fei.logic.domain.User;
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
                    new AlertPopUpGenerator().showCustomMessage(Alert.AlertType.INFORMATION, "Mensaje de éxito", "Retroalimentación guardada exitosamente");
                }
                
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.close();
            }catch(DataInsertionException exception){
                new AlertPopUpGenerator().showConnectionErrorMessage();
            }
        }else{
            new AlertPopUpGenerator().showCustomMessage(Alert.AlertType.WARNING, "No se pudo agregar la retroalimentacion", "Favor de insertar texto");
        }
    }
    
    public void setAdvance(Advance advance){
        this.advance = advance;
        
        if(advance.getFeedback() != null){
            feedbackTextArea.setText(advance.getFeedback());
        }
    }
    
    public void setUser(User user){
        if(!Director.class.isAssignableFrom(user.getClass())){
            
        }
    }
}
