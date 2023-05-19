package mx.uv.fei.gui.controllers.chronogram.advances;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mx.uv.fei.gui.AlertPopUpGenerator;
import mx.uv.fei.logic.domain.Advance;

public class AdvanceInfoController{
    private Advance advance;
    
    @FXML
    private Pane headerPane;
    @FXML
    private Pane toolbarPane;
    @FXML
    private Label titleLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label statusLabel;
    @FXML
    private Text descriptionText;

    @FXML
    public void initialize() {
        loadHeader();
    }    

    @FXML
    private void editAdvance(ActionEvent event) {
        
    }
    @FXML
    private void feedback(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/chronogram/advances/FeedbackPopUp.fxml"));
        
        try{
            Parent parent = loader.load();
            FeedbackPopUpController controller = (FeedbackPopUpController)loader.getController();
            controller.setAdvance(advance);
            
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Retroalimentar avance");
            stage.setScene(scene);
            stage.setResizable(false);
            
            stage.showAndWait();
        }catch(IOException exception){
            AlertPopUpGenerator.showMissingFilesMessage();
        }
    }
    private void loadHeader(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/HeaderPane.fxml"));
        
        try{
            Pane header = loader.load();
            headerPane.getChildren().add(header);
            
        }catch(IOException exception){
            AlertPopUpGenerator.showMissingFilesMessage();
        }
    }
    protected void setAdvance(Advance advance){
        this.advance = advance;
        
        titleLabel.setText(advance.getTitle());
        dateLabel.setText(advance.getDate().toString());
        descriptionText.setText(advance.getComment());
    }
}
