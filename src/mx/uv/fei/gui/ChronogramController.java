package mx.uv.fei.gui;

import java.io.IOException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import mx.uv.fei.logic.daos.ActivityDAO;
import mx.uv.fei.logic.domain.Activity;
import mx.uv.fei.logic.exceptions.DataRetrievalException;

public class ChronogramController{
    private ActivityDAO activityDAO;
    private ArrayList<Activity> activityList;
    private Stage stage;
    private Scene scene;
    private Parent parent;
    private ActivityPane activityPane;
    @FXML
    private ComboBox studentChronogramComboBox;
    @FXML
    private VBox activityListVBox;
    
    @FXML
    public void initialize(){
        activityDAO = new ActivityDAO();
        
        try{
            activityList = activityDAO.getActivityList();
        }catch(DataRetrievalException exception){
            //Instance of GUI_ERROR_MESSAGE
            System.out.println(exception.getMessage());
        }
        
        for(Activity activity : activityList) {
            System.out.println(activity);
            
            activityPane = new ActivityPane(activity);
            activityListVBox.getChildren().add(activityPane);
        }
    }
    
    @FXML
    public void createActivity(ActionEvent event) throws IOException{
        parent = new FXMLLoader(getClass().getResource("CreateActivity.fxml")).load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(parent);
        stage.setTitle("SPGER");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void hide(ActionEvent event){
        studentChronogramComboBox.setVisible(false);
    }
    public VBox getActivityListVBox(){
        return activityListVBox;
    }
}