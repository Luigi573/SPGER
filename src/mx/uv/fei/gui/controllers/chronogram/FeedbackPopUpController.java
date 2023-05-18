package mx.uv.fei.gui.controllers.chronogram;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mx.uv.fei.logic.daos.ActivityDAO;
import mx.uv.fei.logic.domain.Activity;
import mx.uv.fei.logic.exceptions.DataWritingException;

public class FeedbackPopUpController{
    private Activity activity;
    
    @FXML
    private Text activityCommentText;
    @FXML
    private TextArea feedbackTextArea;

    @FXML
    private void submitFeedback(ActionEvent event) {
        activity.setFeedback(feedbackTextArea.getText());
        
        if(!activity.getFeedback().isEmpty()){
            ActivityDAO activityDAO = new ActivityDAO();
            
            try{
                if(activityDAO.setFeedback(activity.getFeedback(), activity.getId()) > 0){
                    Alert successMessage = new Alert(Alert.AlertType.INFORMATION);
                    successMessage.setContentText("Retroalimentación agregada exitosamente");
                    successMessage.showAndWait();
                    
                    Stage stage = (Stage)feedbackTextArea.getScene().getWindow();
                    stage.close();
                }
            }catch(DataWritingException exception){
                Alert errorMessage = new Alert(Alert.AlertType.ERROR);
                errorMessage.setHeaderText("Error de conexión");
                errorMessage.setContentText("Favor de verificar su conexión a internet e inténtelo de nuevo");
                errorMessage.showAndWait();
            }
        }else{
            Alert warningMessage = new Alert(Alert.AlertType.WARNING);
            warningMessage.setHeaderText("No se puede guardar la actividad");
            warningMessage.setContentText("Favor de escribir al menos 1 caracter");
            warningMessage.showAndWait();
        }
    }
    public void setActivity(Activity activity){
        this.activity = activity;
        activityCommentText.setText(activity.getComment());
        
        if(activity.getFeedback() != null){
            feedbackTextArea.setText(activity.getFeedback());
        }
    }
}
