package mx.uv.fei.gui.javafiles.guireporteanteproyectocontrollers;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import mx.uv.fei.logic.daos.ResearchesReportDAO;
import mx.uv.fei.logic.domain.Research;

public class GuiReporteAnteproyectoController {

    @FXML
    private Text errorMessageText;

    @FXML
    private Text findByTitleText;

    @FXML
    private TextField findByTitleTextField;

    @FXML
    private Button generateReportButton;

    @FXML
    private Pane guiCrearAnteproyectoBody;

    @FXML
    private VBox selectedResearchesVBox;

    @FXML
    private Text recentlySelectedResearchesText;

    @FXML
    private VBox researchesVBox;

    @FXML
    private Button searchResearchesButton;

    @FXML
    private Text showNotValidatedText;

    @FXML
    private ToggleButton showNotValidatedToggleButton;

    @FXML
    private Text showResearchesText;

    @FXML
    private ToggleButton showResearchesToggleButton;

    @FXML
    private Text showValidatedText;

    @FXML
    private ToggleButton showValidatedToggleButton;

    @FXML
    private ImageView spgerLogo;

    @FXML
    private Text spgerText;

    @FXML
    private ImageView userLogo;

    @FXML
    private Text userNameText;

    @FXML
    private Text windowText;

    @FXML
    void generateReportButtonController(ActionEvent event) {

    }

    @FXML
    void searchResearchesButtonController(ActionEvent event) {
        this.researchesVBox.getChildren().removeAll(this.researchesVBox.getChildren());
        ResearchesReportDAO researchesReportDAO = new ResearchesReportDAO();
        ArrayList<Research> researches = new ArrayList<>();
        researches = researchesReportDAO.getResearchesFromDatabase(this.findByTitleTextField.getText());
        try {
            for(Research research : researches){
                FXMLLoader researchItemControllerLoader = new FXMLLoader(
                    getClass().getResource("/mx/uv/fei/gui/fxmlfiles/guireporteanteproyecto/ResearchItem.fxml")
                );
                HBox researchItemHBox = researchItemControllerLoader.load();
                ResearchItemController researchItemController = researchItemControllerLoader.getController();
                researchItemController.setResearchNameLabel(research.getTitle());
                researchItemController.setGuiReporteAnteproyectoController(this);
                this.researchesVBox.getChildren().add(researchItemHBox);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
            
    }

    @FXML
    void showNotValidatedToggleButtonController(ActionEvent event) {

    }

    @FXML
    void showValidatedToggleButtonController(ActionEvent event) {

    }

    //This method only should be executed by the ResearchItemController Class, do not execute this method in another class.
    void setElementToSelectedResearchesVBox(String selectedResearchTitle) {
        try {
            FXMLLoader selectedResearchItemControllerLoader = new FXMLLoader(
                getClass().getResource("/mx/uv/fei/gui/fxmlfiles/guireporteanteproyecto/SelectedResearchItem.fxml")
            );
            HBox selectedResearchItemHBox;
            selectedResearchItemHBox = selectedResearchItemControllerLoader.load();
            SelectedResearchItemController selectedResearchItemController = selectedResearchItemControllerLoader.getController();
            selectedResearchItemController.setSelectedResearchNameLabel(selectedResearchTitle);
            this.selectedResearchesVBox.getChildren().add(selectedResearchItemHBox);
            selectedResearchItemController.setGuiReporteAnteproyectoController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //This method only should be executed by the SelectedResearchItemController Class, do not execute this method in another class.
    void setElementToResearchesVBox(String researchTitle) {
        try {
            FXMLLoader researchItemControllerLoader = new FXMLLoader(
                getClass().getResource("/mx/uv/fei/gui/fxmlfiles/guireporteanteproyecto/ResearchItem.fxml")
            );
            HBox researchItemHBox;
            researchItemHBox = researchItemControllerLoader.load();
            ResearchItemController researchItemController = researchItemControllerLoader.getController();
            researchItemController.setResearchNameLabel(researchTitle);
            this.researchesVBox.getChildren().add(researchItemHBox);
            researchItemController.setGuiReporteAnteproyectoController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //This method only should be executed by the ResearchItemController Class, do not execute this method in another class.
    void removeElementFromResearchesVBox(String researchItemControllerTitle){
        int researchesVBoxIndexForRemove = 0;
        for(Node hbox : ((VBox)this.researchesVBox).getChildren()) {
            Node label = ((HBox)hbox).getChildren().get(1); 
            if(((Label)label).getText() == researchItemControllerTitle) {
                this.researchesVBox.getChildren().remove(researchesVBoxIndexForRemove);
                break;
            }    
            researchesVBoxIndexForRemove++;
        }
    }

    //This method only should be executed by the SelectedResearchItemController Class, do not execute this method in another class.
    void removeElementFromSelectedResearchesVBox(String selectedResearchItemControllerTitle){
        int selectedResearchesVBoxIndexForRemove = 0;
        for(Node hbox : ((VBox)this.selectedResearchesVBox).getChildren()) {
            Node label = ((HBox)hbox).getChildren().get(1); 
            if(((Label)label).getText() == selectedResearchItemControllerTitle) {
                this.selectedResearchesVBox.getChildren().remove(selectedResearchesVBoxIndexForRemove);
                break;
            }    
            selectedResearchesVBoxIndexForRemove++;
        }
    }

}
