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
import mx.uv.fei.gui.AlertPopUpGenerator;
import mx.uv.fei.logic.domain.Advance;
import mx.uv.fei.logic.domain.Course;
import mx.uv.fei.logic.domain.User;

public class AdvanceVBoxPaneController{
    private Advance advance;
    private Course course;
    private User user;
    
    @FXML
    private Label titleLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label statusLabel;   

    @FXML
    private void viewAdvanceInfo(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/chronogram/advances/AdvanceInfo.fxml"));
        
        try{
            Parent parent = loader.load();
            AdvanceInfoController controller = (AdvanceInfoController)loader.getController();
            controller.setAdvance(advance);
            controller.setCourse(course);
            controller.setUser(user);
            controller.loadHeader();
            
            Scene scene = new Scene(parent);
            String css = this.getClass().getResource("/mx/uv/fei/gui/stylesfiles/Styles.css").toExternalForm();
            scene.getStylesheets().add(css);
            
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setTitle("SPGER");
            stage.setScene(scene);
            stage.show();
            
        }catch(IOException exception){
            new AlertPopUpGenerator().showMissingFilesMessage();
        }
    }
    
    public void setAdvance(Advance advance){
        this.advance = advance;
        
        titleLabel.setText(advance.getTitle());
        dateLabel.setText(advance.getDate().toString());
        statusLabel.setText(advance.getState());
    }
    
    public void setCourse(Course course){
        this.course = course;
    }
    
    public void setUser(User user){
        this.user = user;
    }
}
