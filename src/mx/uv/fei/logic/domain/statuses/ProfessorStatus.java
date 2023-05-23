package mx.uv.fei.logic.domain.statuses;

public enum ProfessorStatus {
    ACTIVE("Activo"), 
    INACTIVE("Inactivo");
    
    private final String status;
    
    ProfessorStatus(String status){
        this.status = status;
    }
    
    public String getValue(){
        return status;
    }
}