package mx.uv.fei.gui.controllers.research;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import mx.uv.fei.logic.domain.ResearchProject;

public class ResearchVBoxPaneController{
    private ResearchProject research;
    private ScrollPane container;
    
    @FXML
    private Label titleLabel;
    @FXML
    private Label validationLabel;
    
    @FXML
    private void selectResearch(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/research/ResearchInfoPane.fxml"));
        
        try{
            Pane researchInfoPane = loader.load();
            ResearchInfoPaneController controller = (ResearchInfoPaneController)loader.getController();
            controller.setResearch(research);
            controller.setContainer(container);
            
            container.setContent(researchInfoPane);
        }catch(IOException exception){
            Alert errorMessage = new Alert(Alert.AlertType.ERROR);
            errorMessage.setContentText("Error al cargar, faltan archivos");
            errorMessage.showAndWait();
        }
    }
    public void setResearchProject(ResearchProject research){
        this.research = research;
        titleLabel.setText(research.getTitle());
    }
    public void setContainer(ScrollPane container){
        this.container = container;
    }
}