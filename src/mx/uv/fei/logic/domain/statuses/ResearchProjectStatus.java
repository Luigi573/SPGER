package mx.uv.fei.logic.domain.statuses;

public enum ResearchProjectStatus {
    PROPOSED("Propuesto"), 
    VALIDATED("Validado"),
    ASSIGNED("Asignado");

    private final String status;
    
    ResearchProjectStatus(String status){
        this.status = status;
    }
    
    public String getValue(){
        return status;
    }
}
