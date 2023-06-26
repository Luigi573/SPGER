package mx.uv.fei.gui.controllers.chronogram.advances;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mx.uv.fei.gui.AlertPopUpGenerator;
import mx.uv.fei.gui.controllers.HeaderPaneController;
import mx.uv.fei.gui.controllers.chronogram.activities.ActivityFileItemController;
import mx.uv.fei.gui.controllers.chronogram.activities.ActivityInfoController;
import mx.uv.fei.logic.daos.AdvanceDAO;
import mx.uv.fei.logic.daos.FileDAO;
import mx.uv.fei.logic.domain.Activity;
import mx.uv.fei.logic.domain.Advance;
import mx.uv.fei.logic.domain.Course;
import mx.uv.fei.logic.domain.Director;
import mx.uv.fei.logic.domain.File;
import mx.uv.fei.logic.domain.Professor;
import mx.uv.fei.logic.domain.User;
import mx.uv.fei.logic.exceptions.DataRetrievalException;

public class AdvanceInfoController{
    private Activity activity;
    private Advance advance;
    private Course course;
    private User user;
    
    @FXML
    private Button modifyAdvanceButton;
    @FXML
    private Button feedbackButton;
    @FXML
    private Pane headerPane;
    @FXML
    private Label titleLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label statusLabel;
    @FXML
    private TextArea commentTextArea;
    @FXML
    private VBox fileVBox;
    
    @FXML
    private void modifyAdvance(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/chronogram/advances/ModifyAdvance.fxml"));
            Parent parent = loader.load();
            ModifyAdvanceController controller = (ModifyAdvanceController)loader.getController();
            controller.setActivity(activity);
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
            exception.printStackTrace();
            new AlertPopUpGenerator().showMissingFilesMessage();
        }
    }
    
     @FXML
    private void returnToAdvanceList(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/chronogram/advances/ModifyAdvance.fxml"));
            Parent parent = loader.load();
            ModifyAdvanceController controller = (ModifyAdvanceController)loader.getController();
            controller.setActivity(activity);
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
            exception.printStackTrace();
            new AlertPopUpGenerator().showMissingFilesMessage();
        }
    }
    
    @FXML
    private void goBack(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/chronogram/activities/ActivityInfo.fxml"));
            Parent parent = loader.load();
            ActivityInfoController controller = (ActivityInfoController)loader.getController();
            controller.setActivity(activity);
            controller.setCourse(course);
            controller.setUser(user);
            controller.loadAdvances();
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
    
    @FXML
    private void openFeedbackPopUp(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/chronogram/advances/FeedbackPopUp.fxml"));
        
        try{
            Parent parent = loader.load();
            FeedbackPopUpController controller = loader.getController();
            controller.setAdvance(advance);
            
            if(!Director.class.isAssignableFrom(user.getClass())){
                controller.disableWriting();
            }
            
            Scene scene = new Scene(parent);
            String css = this.getClass().getResource("/mx/uv/fei/gui/stylesfiles/Styles.css").toExternalForm();
            scene.getStylesheets().add(css);
            
            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner((Stage)((Node)event.getSource()).getScene().getWindow());
            stage.setScene(scene);
            stage.setResizable(false);
            
            stage.showAndWait();
        }catch(IOException exception){
            new AlertPopUpGenerator().showMissingFilesMessage();
        }
    }
    
    public void loadHeader(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/HeaderPane.fxml"));
        
        try{
            Pane header = loader.load();
            HeaderPaneController controller = loader.getController();
            controller.setCourse(course);
            controller.setUser(user);
            
            headerPane.getChildren().setAll(header);
        }catch(IOException exception){
            new AlertPopUpGenerator().showMissingFilesMessage();
        }
    }
    
    public void setActivity(Activity activity){
        this.activity = activity;
    }
    
    public void setAdvance(Advance advance){
        this.advance = advance;
        
        commentTextArea.setText(advance.getComment());
        dateLabel.setText(advance.getDate().toString());
        titleLabel.setText(advance.getTitle());
        statusLabel.setText(advance.getState());
        
        if(advance.getFeedback() != null){
            feedbackButton.setVisible(false);
        }
                
        FileDAO fileDAO = new FileDAO();
        File file;
        
        if(advance.getFileID() != 0) {
            try {
                file = fileDAO.getFileByID(advance.getFileID());
                
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/chronogram/activities/ActivityFileItem.fxml"));               
                try {
                    Pane advancePane = loader.load();
                    ActivityFileItemController controller = (ActivityFileItemController)loader.getController();
                    if (file != null){
                        controller.setFile(file.getFilePath());
                    }
                    fileVBox.getChildren().add(advancePane);
                } catch(IOException exception){
                    new AlertPopUpGenerator().showMissingFilesMessage();
                }
            } catch (DataRetrievalException exception) {
                new AlertPopUpGenerator().showConnectionErrorMessage();
            }
        }
    }
    
    public void setCourse(Course course){
        this.course = course;
    }  
    
    public void setUser(User user){
        this.user = user;
        
        if(Professor.class.isAssignableFrom(user.getClass())){
            commentTextArea.setEditable(false);
            modifyAdvanceButton.setVisible(false);
            
            if(Director.class.isAssignableFrom(user.getClass())){
                feedbackButton.setText("Retroalimentar");
            }
        }
    }
}
