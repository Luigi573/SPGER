package mx.uv.fei.logic.domain;

public enum UserType {
    ACADEMIC_BODY_HEAD("Miembro de Cuerpo Académico"),
    DEGREE_BOSS("Jefe de Carrera"),
    DIRECTOR("Director"),
    PROFESSOR("Profesor"),
    STUDENT("Estudiante");
    
    private final String userType;
    
    UserType(String userType){
        this.userType = userType;
    }
    
    public String getValue(){
        return userType;
    }
}