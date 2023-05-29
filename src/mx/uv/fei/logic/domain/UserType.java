package mx.uv.fei.logic.domain;

public enum UserType {
    STUDENT("Estudiante"),
    PROFESSOR("Profesor"),
    DIRECTOR("Director"),
    ACADEMIC_BODY_HEAD("Miembro de Cuerpo Académico"),
    DEGREE_BOSS("Jefe de Carrera");
    
    private final String userType;
    
    UserType(String userType){
        this.userType = userType;
    }
    
    public String getValue(){
        return userType;
    }
}