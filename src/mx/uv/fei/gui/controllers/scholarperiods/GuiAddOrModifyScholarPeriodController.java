package mx.uv.fei.gui.controllers.scholarperiods;

import java.sql.Date;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import mx.uv.fei.gui.AlertPopUpGenerator;
import mx.uv.fei.logic.daos.ScholarPeriodDAO;
import mx.uv.fei.logic.domain.ScholarPeriod;
import mx.uv.fei.logic.exceptions.DataInsertionException;
import mx.uv.fei.logic.exceptions.DataRetrievalException;

public class GuiAddOrModifyScholarPeriodController {
    private boolean editing;
    private GuiScholarPeriodsController guiScholarPeriodsController;
    private ScholarPeriod originalScholarPeriod;

    @FXML
    private DatePicker dueDateDatePicker;

    @FXML
    private Label headerText;

    @FXML
    private Button saveRegisterOrUpdateButton;

    @FXML
    private DatePicker startDateDatePicker;

    @FXML
    private void cancelRegisterOrUpdateButtonController(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void saveRegisterOrUpdateButtonController(ActionEvent event) {
        if(Date.valueOf(startDateDatePicker.getValue()).compareTo(Date.valueOf(dueDateDatePicker.getValue())) < 0){
            ScholarPeriodDAO scholarPeriodDAO = new ScholarPeriodDAO();
            ScholarPeriod scholarPeriod = new ScholarPeriod();
            scholarPeriod.setStartDate(Date.valueOf(startDateDatePicker.getValue()));
            scholarPeriod.setEndDate(Date.valueOf(dueDateDatePicker.getValue()));
            if(editing){
                scholarPeriod.setScholarPeriodId(originalScholarPeriod.getScholarPeriodId());
                try{
                    scholarPeriodDAO.modifyScholarPeriod(scholarPeriod);
                    new AlertPopUpGenerator().showCustomMessage(AlertType.INFORMATION, "Éxito", "Periodo Escolar modificado con éxito");
                }catch(DataInsertionException e){
                    new AlertPopUpGenerator().showCustomMessage(AlertType.ERROR, "Error", "Hubo un error, inténtelo más tarde");
                    return;
                }
                Node source = (Node) event.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
                guiScholarPeriodsController.loadScholarPeriods();
            }else{
                if(!scholarPeriodAlreadyRegisted(scholarPeriod)){
                    try{
                        scholarPeriodDAO.addScholarPeriod(scholarPeriod);
                        new AlertPopUpGenerator().showCustomMessage(AlertType.INFORMATION, "Éxito", "Periodo Escolar registrado con éxito");
                    }catch(DataInsertionException e){
                        new AlertPopUpGenerator().showCustomMessage(AlertType.ERROR, "Error", "Hubo un error, inténtelo más tarde");
                        return;
                    }
                    Node source = (Node) event.getSource();
                    Stage stage = (Stage) source.getScene().getWindow();
                    stage.close();
                    guiScholarPeriodsController.loadScholarPeriods();
                }else{
                    new AlertPopUpGenerator().showCustomMessage(AlertType.WARNING, "Error", "El periodo escolar ya está registrado en el sistema");
                }
            }
        }else{
            new AlertPopUpGenerator().showCustomMessage(AlertType.WARNING, "Error", "La fecha de inicio no puede ser después de la fecha de fin");
        }
    }

    public void setEditing(boolean x){
        editing = x;
    }
    public boolean getEditing(){
        return editing;
    }
    public ScholarPeriod getOriginalScholarPeriod(){
        return originalScholarPeriod;
    }
    public void setOriginalScholarPeriod(ScholarPeriod originalScholarPeriod){
        this.originalScholarPeriod = originalScholarPeriod;
    }
    public void setGuiScholarPeriodsController(GuiScholarPeriodsController guiScholarPeriodsController) {
        this.guiScholarPeriodsController = guiScholarPeriodsController;
    }
    public void editRefaction(){
        startDateDatePicker.setValue(originalScholarPeriod.getStartDate().toLocalDate());
        dueDateDatePicker.setValue(originalScholarPeriod.getEndDate().toLocalDate());
    }
    public void setHeaderTextModify(){
        headerText.setText("Modificar Periodo Escolar");
    }
    public void setSaveOrUpdateButtonModify(){
        saveRegisterOrUpdateButton.setText("Modificar");
    }
    private boolean scholarPeriodAlreadyRegisted(ScholarPeriod scholarPeriod){
        ScholarPeriodDAO scholarPeriodDAO = new ScholarPeriodDAO();
        boolean scholarPeriodAlreadyRegisted = false;
        try {
            if(scholarPeriodDAO.getScholarPeriodByObject(scholarPeriod).getStartDate() != null &&
               scholarPeriodDAO.getScholarPeriodByObject(scholarPeriod).getEndDate() != null){
                scholarPeriodAlreadyRegisted = scholarPeriodDAO.getScholarPeriodByObject(scholarPeriod).equals(scholarPeriod);
            }
        } catch (DataRetrievalException e) {
            new AlertPopUpGenerator().showCustomMessage(AlertType.ERROR, "Error", "Hubo un error, inténtelo más tarde");
        }
        return scholarPeriodAlreadyRegisted;
    }
}
