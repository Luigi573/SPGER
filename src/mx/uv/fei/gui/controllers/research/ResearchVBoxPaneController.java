package mx.uv.fei.gui.controllers.research;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import mx.uv.fei.gui.AlertPopUpGenerator;
import mx.uv.fei.logic.domain.ResearchProject;
import mx.uv.fei.logic.domain.statuses.ResearchProjectStatus;

public class ResearchVBoxPaneController{
    private ResearchProject research;
    private ResearchManagerController researchManagerController;
    private ScrollPane container;
    
    @FXML
    private AnchorPane researchPane;
    @FXML
    private Label titleLabel;
    @FXML
    private Label validationLabel;
    
    @FXML
    private void selectResearch(ActionEvent event){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/research/ResearchInfoPane.fxml"));
        
        try{
            Pane researchInfoPane = loader.load();
            ResearchInfoPaneController controller = (ResearchInfoPaneController)loader.getController();
            controller.setResearch(research);
            controller.setContainer(container);
            controller.setResearchManagerController(researchManagerController);
            if(research.getValidationStatus().equals(ResearchProjectStatus.PROPOSED.getValue()) ){
                controller.showValidateButton(true);
            }
            
            container.setContent(researchInfoPane);
        }catch(IOException exception){
            new AlertPopUpGenerator().showMissingFilesMessage();
        }
    }
    public ResearchManagerController getResearchManagerController(){
        return researchManagerController;
    }
    public void setResearchManagerController(ResearchManagerController researchManagerController){
        this.researchManagerController = researchManagerController;
    }
    public void setResearchProject(ResearchProject research){
        this.research = research;
        titleLabel.setText(research.getTitle());
        validationLabel.setText(research.getValidationStatus());
    }
    public void setContainer(ScrollPane container){
        this.container = container;
    }
}