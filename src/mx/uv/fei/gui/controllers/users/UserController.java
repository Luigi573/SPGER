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
    private Label matricleOrPersonalNumberLabel;
    @FXML
    private Label matricleOrPersonalNumberText;
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
    public void setMatricleOrPersonalNumber(String matricleOrPersonalNumber){
        matricleOrPersonalNumberLabel.setText(matricleOrPersonalNumber);
    }
    public void setMatricleOrPersonalNumberText(String matricleOrPersonalNumber){
        matricleOrPersonalNumberText.setText(matricleOrPersonalNumber);
    }
    public String getType(){
        return typeLabel.getText();
    }
    public String getMatricleOrPersonalNumber(){
        return matricleOrPersonalNumberLabel.getText();
    }
    public void setLabelsCorrectBounds(String userType){
        if(userType.equals(UserType.STUDENT.getValue())){
            matricleOrPersonalNumberText.setPrefWidth(72);
            matricleOrPersonalNumberText.setLayoutX(10);
            matricleOrPersonalNumberLabel.setPrefWidth(373);
            matricleOrPersonalNumberLabel.setLayoutX(81);
        }else{
            matricleOrPersonalNumberText.setPrefWidth(143);
            matricleOrPersonalNumberText.setLayoutX(10);
            matricleOrPersonalNumberLabel.setPrefWidth(303);
            matricleOrPersonalNumberLabel.setLayoutX(151);
        }
    }
}