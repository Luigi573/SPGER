package mx.uv.fei.gui.controllers.users;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import mx.uv.fei.logic.domain.UserType;

public class UserController{
    GuiUsersController guiUsersController;

    @FXML
    private Label matricleOrStaffNumberLabel;
    @FXML
    private Label matricleOrStaffNumberText;
    @FXML
    private Label nameLabel;
    @FXML
    private Label typeLabel;
    @FXML
    private Pane userPane;
    @FXML
    private Button userButton;

    @FXML
    private void userButtonController(ActionEvent event){
        guiUsersController.openPaneWithUserInformation(this);
    }

    public void setGuiUsersController(GuiUsersController guiUsersController){
        this.guiUsersController = guiUsersController;
    }
    public void setName(String name){
        nameLabel.setText(name);
    }
    public void setType(String type){
        typeLabel.setText(type);
    }
    public void setMatricleOrStaffNumber(String matricleOrStaffNumber){
        matricleOrStaffNumberLabel.setText(matricleOrStaffNumber);
    }
    public void setMatricleOrStaffNumberText(String matricleOrStaffNumber){
        matricleOrStaffNumberText.setText(matricleOrStaffNumber);
    }
    public String getType(){
        return typeLabel.getText();
    }
    public String getMatricleOrStaffNumber(){
        return matricleOrStaffNumberLabel.getText();
    }
    public void setLabelsCorrectBounds(String userType){
        if(userType.equals(UserType.STUDENT.getValue())){
            matricleOrStaffNumberText.setPrefWidth(72);
            matricleOrStaffNumberText.setLayoutX(10);
            matricleOrStaffNumberLabel.setPrefWidth(373);
            matricleOrStaffNumberLabel.setLayoutX(81);
        }else{
            matricleOrStaffNumberText.setPrefWidth(143);
            matricleOrStaffNumberText.setLayoutX(10);
            matricleOrStaffNumberLabel.setPrefWidth(303);
            matricleOrStaffNumberLabel.setLayoutX(151);
        }
    }
}