package mx.uv.fei.gui;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import mx.uv.fei.logic.domain.Activity;

public class ActivityPaneController{
    @FXML
    private Label dueDateLabel;
    @FXML
    private Label startDateLabel;
    @FXML
    private Label titleLabel;
    private Activity activity;
    
    
    
    @FXML
    public void initialize(Activity activity){
        FXMLLoader loader;
        titleLabel.setText(activity.getTitle());
        startDateLabel.setText("Fecha de inicio: " + activity.getStartDate());
        dueDateLabel.setText("Fecha de entrega: " + activity.getDueDate());
        
        loader = new FXMLLoader(getClass().getResource("ChronogramActivityPanel.fxml"));
        
        try{
            loader.load();
        }catch(IOException exception){
            System.out.println("Error al cargar panel de actividad");
        }
    }
    @FXML
    public void viewActivity(ActionEvent event){
        
    }
    public void setActivity(){
        
    }
}
