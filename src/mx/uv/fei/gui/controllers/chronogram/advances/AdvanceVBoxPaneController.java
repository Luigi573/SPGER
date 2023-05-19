package mx.uv.fei.gui.controllers.chronogram.advances;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import mx.uv.fei.logic.domain.Advance;

public class AdvanceVBoxPaneController{
    private Advance advance;
    
    @FXML
    private Label titleLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label statusLabel;

    @FXML
    public void initialize() {
        
    }    

    @FXML
    private void viewAdvanceInfo(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/chronogram/advances/AdvanceInfo.fxml"));
        
        try{
            Parent parent = loader.load();
            AdvanceInfoController controller = (AdvanceInfoController)loader.getController();
            controller.setAdvance(advance);
            
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(parent);
            stage.setTitle("SPGER");
            stage.setScene(scene);
            stage.show();
            
        }catch(IOException exception){
            
        }
    }
    
    public void setAdvance(Advance advance){
        this.advance = advance;
        
        titleLabel.setText(advance.getTitle());
        dateLabel.setText(advance.getDate().toString());
    }
}
