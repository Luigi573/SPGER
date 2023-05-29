package mx.uv.fei.logic.domain.statuses;

public enum ActivityStatus {
    ACTIVE("Por entregar"), 
    DELIVERED("Entregada"), 
    REVIEWED("Revisada"), 
    WITH_ADVANCES("Con avances");
    
    private final String status;
    
    private ActivityStatus(String status){
        this.status = status;
    }
    
    public String getValue(){
        return status;
    }
}