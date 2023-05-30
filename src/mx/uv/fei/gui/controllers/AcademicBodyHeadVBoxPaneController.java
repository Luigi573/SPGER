
package mx.uv.fei.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import mx.uv.fei.logic.domain.AcademicBodyHead;

public class AcademicBodyHeadVBoxPaneController{
    private AcademicBodyHead user;
    
    private void initialize() {
        
    }    

    @FXML
    private void goToResearchManager(ActionEvent event) {
        
    }
    
    public void setUser(AcademicBodyHead user){
        this.user = user;
        
        
    }
}
