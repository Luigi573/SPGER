package mx.uv.fei.gui.javafiles.guireporteanteproyectocontrollers;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

    //private ArrayList<ResearchItemController> researchItems = new ArrayList<>();

    //private ArrayList<RecentlySearchedResearchItemController> recentlySearchedResearchItemControllers = new ArrayList<>();

    @FXML
    private Text findByTitleText;

    @FXML
    private TextField findByTitleTextField;

    @FXML
    private Button generateReportButton;

    @FXML
    private Pane guiCrearAnteproyectoBody;

    @FXML
    private VBox recentlySearchedResearchesVBox;

    @FXML
    private Text recentlySelectedResearchesText;

    @FXML
    private Text researchNameText21;

    @FXML
    private Text researchNameText211;

    @FXML
    private VBox researchesVBox;

    @FXML
    private Button searchResearchesButton;

    @FXML
    private RadioButton selectResearchRadioButton21;

    @FXML
    private RadioButton selectResearchRadioButton211;

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
    void findByTitleTextFieldController(ActionEvent event) {
    
    }

    @FXML
    void generateReportButtonController(ActionEvent event) {

    }

    @FXML
    void searchResearchesButtonController(ActionEvent event) {
        try {
            this.researchesVBox.getChildren().removeAll(this.researchesVBox.getChildren());
            ResearchesReportDAO researchesReportDAO = new ResearchesReportDAO();
            ArrayList<Research> researches = new ArrayList<>();
            researches = researchesReportDAO.getResearchesFromDatabase(this.findByTitleTextField.getText());
            for(Research research : researches){
                FXMLLoader researchItemControllerLoader = new FXMLLoader(
                    getClass().getResource("/mx/uv/fei/gui/fxmlfiles/guireporteanteproyecto/ResearchItem.fxml")
                );
                //ResearchItemController researchItemController = new ResearchItemController();
                HBox researchItemHBox = researchItemControllerLoader.load();
                ResearchItemController researchItemController = researchItemControllerLoader.getController();
                System.out.println(research.getTitle());
                researchItemController.setResearchNameText(research.getTitle());
                //Label label = (Label) researchItemHBox.getChildren().get(1);
                //label.getChildrenUnmodifiable();
                //this.researchItems.add(researchItemController);
                this.researchesVBox.getChildren().add(researchItemHBox);
            }

            //for(ResearchItemController researchItemController : this.researchItems){
            //    
            //    
            //}
        } catch (IOException e) {
            e.printStackTrace();
        }
            
    }

    @FXML
    void selectResearchRadioButton2Controller(ActionEvent event) {

    }

    @FXML
    void showNotValidatedToggleButtonController(ActionEvent event) {

    }

    @FXML
    void showResearchesToggleButtonController(ActionEvent event) {

    }

    @FXML
    void showValidatedToggleButtonController(ActionEvent event) {

    }

}
