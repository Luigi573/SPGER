package mx.uv.fei.gui.controllers.chronogram.activities;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import mx.uv.fei.gui.AlertPopUpGenerator;
import mx.uv.fei.logic.daos.ActivityDAO;
import mx.uv.fei.logic.domain.Activity;
import mx.uv.fei.logic.domain.Director;
import mx.uv.fei.logic.domain.Professor;
import mx.uv.fei.logic.domain.User;
import mx.uv.fei.logic.exceptions.DataInsertionException;

public class FeedbackPopUpController{
    private Activity activity;
    
    @FXML
    private Button submitButton;
    @FXML
    private TextArea feedbackTextArea;

    @FXML
    private void submitFeedback(ActionEvent event) {
        activity.setFeedback(feedbackTextArea.getText());
        
        if(!activity.getFeedback().isEmpty()){
            ActivityDAO activityDAO = new ActivityDAO();
            
            try{
                if(activityDAO.setFeedback(activity.getFeedback(), activity.getId()) > 0){
                    new AlertPopUpGenerator().showCustomMessage(Alert.AlertType.INFORMATION, "", "Retroalimentación agregada exitosamente");
                    
                    Stage stage = (Stage)feedbackTextArea.getScene().getWindow();
                    stage.close();
                }
            }catch(DataInsertionException exception){
                new AlertPopUpGenerator().showConnectionErrorMessage();
            }
        }else{
            new AlertPopUpGenerator().showCustomMessage(Alert.AlertType.WARNING, "No se puede guardar la retroalimentación", "Favor de escribir al menos 1 caracter");
        }
    }
    public void setActivity(Activity activity){
        this.activity = activity;
        
        if(activity.getFeedback() != null){
            feedbackTextArea.setText(activity.getFeedback());
        }
    }
    public void setUser(User user){
        if(!Director.class.isAssignableFrom(user.getClass())){
            feedbackTextArea.setEditable(false);
            submitButton.setVisible(false);
        }
    }
}
