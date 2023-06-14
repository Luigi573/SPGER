package mx.uv.fei.gui.controllers.chronogram.advances;

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
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mx.uv.fei.gui.AlertPopUpGenerator;
import mx.uv.fei.gui.controllers.HeaderPaneController;
import mx.uv.fei.gui.controllers.chronogram.activities.ActivityFileItemController;
import mx.uv.fei.gui.controllers.chronogram.activities.ActivityInfoController;
import mx.uv.fei.logic.daos.FileDAO;
import mx.uv.fei.logic.domain.Activity;
import mx.uv.fei.logic.domain.Advance;
import mx.uv.fei.logic.domain.Course;
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
    private Button editButton;
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
    private VBox advanceFileVBox;

    @FXML
    private void editAdvance(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/chronogram/ModifyAdvance.fxml"));
            Parent parent = loader.load();
            ModifyAdvanceController controller = (ModifyAdvanceController)loader.getController();
            controller.setAdvance(advance);
            
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(parent);
            stage.setTitle("SPGER");
            stage.setScene(scene);
            stage.show();
        }catch(IllegalStateException | IOException exception){
           new AlertPopUpGenerator().showMissingFilesMessage();
        }
    }
    
     @FXML
    private void returnToAdvanceList(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/chronogram/ActivityInfo.fxml"));
            Parent parent = loader.load();
            ActivityInfoController controller = (ActivityInfoController)loader.getController();
            controller.setActivity(activity);
            
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(parent);
            stage.setTitle("SPGER");
            stage.setScene(scene);
            stage.show();
        }catch(IllegalStateException | IOException exception){
            Alert errorMessage = new Alert(Alert.AlertType.ERROR);
            errorMessage.setHeaderText("Error de carga");
            errorMessage.setContentText("No se pudo abrir la ventana, verifique que el archivo .fxml esté en su ubicación correcta");
            errorMessage.showAndWait();
        }
    }
    
    @FXML
    private void openFeedbackPopUp(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/chronogram/advances/FeedbackPopUp.fxml"));
        
        try{
            Parent parent = loader.load();
            FeedbackPopUpController controller = loader.getController();
            controller.setAdvance(advance);
            controller.setUser(user);
            
            Scene scene = new Scene(parent);
            String css = this.getClass().getResource("/mx/uv/fei/gui/stylesfiles/Styles.css").toExternalForm();
            scene.getStylesheets().add(css);
            
            Stage stage = new Stage();
            stage.setTitle("Retroalimentar avance");
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
        
        if(!commentTextArea.getText().isEmpty()){
            commentTextArea.setEditable(false);
        }
        
        FileDAO fileDAO = new FileDAO();
        File file;
        
        if(advance.getFileID() != 0) {
            try {
                file = fileDAO.getFileByID(advance.getFileID());
                
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/chronogram/ActivityFileItem.fxml"));               
                try {
                    Pane advancePane = loader.load();
                    ActivityFileItemController controller = (ActivityFileItemController)loader.getController();
                    controller.setFile(file.getFilePath());

                    advanceFileVBox.getChildren().add(advancePane);
                } catch(IllegalStateException | IOException exception){
                    Alert errorMessage = new Alert(Alert.AlertType.ERROR);
                    errorMessage.setHeaderText("Error de carga");
                    errorMessage.setContentText("No se pudo abrir la ventana, verifique que el archivo .fxml esté en su ubicación correcta");
                    errorMessage.showAndWait();
                }
            } catch (DataRetrievalException exception) {
                Alert errorMessage = new Alert(Alert.AlertType.ERROR);
                errorMessage.setHeaderText("Error de conexión");
                errorMessage.setContentText("Favor de verificar su conexión a internet e inténtelo de nuevo");
                errorMessage.showAndWait();
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
        }
    }
}
