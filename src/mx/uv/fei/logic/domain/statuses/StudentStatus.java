package mx.uv.fei.logic.domain.statuses;

public enum StudentStatus {
    ACTIVE("Activo"), 
    AVAILABLE("Disponible"), 
    GRADUATED("Graduado"), 
    DROPPED("Baja");

    private final String status;

    StudentStatus(String status){
        this.status = status;
    }
    
    public String getValue(){
        return status;
    }
}
