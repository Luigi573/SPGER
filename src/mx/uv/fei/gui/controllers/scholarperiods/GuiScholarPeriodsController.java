package mx.uv.fei.gui.controllers.scholarperiods;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mx.uv.fei.gui.AlertPopUpGenerator;
import mx.uv.fei.gui.controllers.HeaderPaneController;
import mx.uv.fei.logic.daos.ScholarPeriodDAO;
import mx.uv.fei.logic.domain.ScholarPeriod;
import mx.uv.fei.logic.domain.User;
import mx.uv.fei.logic.exceptions.DataRetrievalException;

public class GuiScholarPeriodsController {
    private User headerUser;

    @FXML
    private Pane backgroundPane;
    @FXML
    private TableColumn<ScholarPeriod, String> dueDateColumn;
    @FXML
    private Button editButton;
    @FXML
    private Button registerButton;
    @FXML
    private TableView<ScholarPeriod> scholarPeriodsTable;
    @FXML
    private Button searchStartDateButton;
    @FXML
    private DatePicker searchStartDateDatePicker;
    @FXML
    private TableColumn<ScholarPeriod, String> startDateColumn;

    @FXML
    public void initialize(){
        loadScholarPeriods();
    }

    @FXML
    private void cleanSearcherButtonController(ActionEvent event) {
        searchStartDateDatePicker.setValue(null);
    }

    @FXML
    private void editButtonController(ActionEvent event) {
        if(scholarPeriodsTable.getSelectionModel().getSelectedItem() != null){
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/scholarperiods/GuiAddOrModifyScholarPeriod.fxml"));
                Parent guiAddRefactions = loader.load();
                GuiAddOrModifyScholarPeriodController guiAddOrModifyScholarPeriodController = loader.getController();

                ScholarPeriod selectedScholarPeriod = scholarPeriodsTable.getSelectionModel().getSelectedItem();

                guiAddOrModifyScholarPeriodController.setEditing(true);
                guiAddOrModifyScholarPeriodController.setOriginalScholarPeriod(selectedScholarPeriod);
                guiAddOrModifyScholarPeriodController.editRefaction();
                guiAddOrModifyScholarPeriodController.setHeaderTextModify();
                guiAddOrModifyScholarPeriodController.setSaveOrUpdateButtonModify();
                guiAddOrModifyScholarPeriodController.setGuiScholarPeriodsController(this);

                Scene scene = new Scene(guiAddRefactions);
                String css = getClass().getResource("/mx/uv/fei/gui/stylesfiles/Styles.css").toExternalForm();
                scene.getStylesheets().add(css);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle(("Modificar Periodos Escolares"));

                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner((Stage)((Node)event.getSource()).getScene().getWindow());
                stage.setResizable(false);
                stage.showAndWait();

            }catch(IOException e){
                new AlertPopUpGenerator().showCustomMessage(AlertType.ERROR, "Error", "Hubo un error, inténtelo más tarde");
            }
        }else{
            new AlertPopUpGenerator().showCustomMessage(AlertType.ERROR, "Error", "Selecciona un periodo escolar para modificarlo");
            return;
        }
    }

    @FXML
    private void registerButtonController(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/scholarperiods/GuiAddOrModifyScholarPeriod.fxml"));
            Parent guiAddOrModifyScholarPeriod = loader.load();
            GuiAddOrModifyScholarPeriodController guiAddOrModifyScholarPeriodController = loader.getController();

            guiAddOrModifyScholarPeriodController.setGuiScholarPeriodsController(this);
            
            Scene scene = new Scene(guiAddOrModifyScholarPeriod);
            String css = getClass().getResource("/mx/uv/fei/gui/stylesfiles/Styles.css").toExternalForm();
            scene.getStylesheets().add(css);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle(("Registrar Periodo Escolar"));
            
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner((Stage)((Node)event.getSource()).getScene().getWindow());
            stage.setResizable(false);
            stage.showAndWait();
            
        }catch(IOException e){
            new AlertPopUpGenerator().showCustomMessage(AlertType.ERROR, "Error", "Hubo un error, inténtelo más tarde");
        }
    }

    @FXML
    private void searchStartDateButtonController(ActionEvent event) {
        String searchTermine;
        if(searchStartDateDatePicker.getValue() != null){
            searchTermine = Date.valueOf(searchStartDateDatePicker.getValue()).toString();
        }else{
            searchTermine = "";
        }
        
        try{
            ScholarPeriodDAO scholarPeriodDAO = new ScholarPeriodDAO();
            ArrayList<ScholarPeriod> refacciones = scholarPeriodDAO.getSpecifiedScholarPeriodsByStartDate(searchTermine);
            ObservableList<ScholarPeriod> scholarPeriodsList = FXCollections.observableArrayList(refacciones);
            scholarPeriodsTable.setItems(scholarPeriodsList);
        }catch(DataRetrievalException e){            
            new AlertPopUpGenerator().showCustomMessage(AlertType.ERROR, "Error", "Hubo un error, inténtelo más tarde");
        }
        
        startDateColumn.setCellValueFactory(new PropertyValueFactory<ScholarPeriod,String>("startDate"));
        dueDateColumn.setCellValueFactory(new PropertyValueFactory<ScholarPeriod,String>("endDate"));
    }

    public void loadHeader(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/HeaderPane.fxml"));
        
        try{
            Pane header = loader.load();
            HeaderPaneController headerPaneController = loader.getController();

            if(headerUser != null){
                headerPaneController.setUser(headerUser);
            }

            header.getStyleClass().add("/mx/uv/fei/gui/stylesfiles/Styles.css");
            backgroundPane.getChildren().add(header);
            
        }catch(IOException exception){
            new AlertPopUpGenerator().showMissingFilesMessage();
        }
    }
    public void loadScholarPeriods(){
        try{
            ScholarPeriodDAO scholarPeriodDAO = new ScholarPeriodDAO();
            ArrayList<ScholarPeriod> scholarPeriods = scholarPeriodDAO.getScholarPeriods();
            ObservableList<ScholarPeriod> scholarPeriodsList = FXCollections.observableArrayList(scholarPeriods);
            scholarPeriodsTable.setItems(scholarPeriodsList);
        }catch(DataRetrievalException e){            
            new AlertPopUpGenerator().showCustomMessage(AlertType.ERROR, "Error", "Hubo un error, inténtelo más tarde");
        }
        
        startDateColumn.setCellValueFactory(new PropertyValueFactory<ScholarPeriod,String>("startDate"));
        dueDateColumn.setCellValueFactory(new PropertyValueFactory<ScholarPeriod,String>("endDate"));
    }
    public User getHeaderUser() {
        return headerUser;
    }
    public void setHeaderUser(User headerUser) {
        this.headerUser = headerUser;
    }
}
