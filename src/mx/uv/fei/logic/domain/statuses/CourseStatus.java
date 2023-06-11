package mx.uv.fei.logic.domain.statuses;

public enum CourseStatus {
    ACTIVE("Activo"), 
    FINISHED("Finalizado");
    
    private final String status;
    
    CourseStatus(String status){
        this.status = status;
    }
    
    public String getValue(){
        return status;
    }
}
