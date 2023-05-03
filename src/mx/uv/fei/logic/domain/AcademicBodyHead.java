package mx.uv.fei.logic.domain;

import mx.uv.fei.logic.domain.statuses.ProfessorStatus;

public class AcademicBodyHead extends Professor{
    private int idAcademicBodyHead;

    public int getIdUser() {
        return super.idUser;
    }

    public void setIdUser(int idUser) {
        super.idUser = idUser;
    }
    
    public String getName() {
        return super.name;
    }

    public void setName(String name) {
        super.name = name;
    }

    public String getFirstSurname() {
        return super.firstSurname;
    }

    public void setFirstSurname(String firstSurname) {
        super.firstSurname = firstSurname;
    }

    public String getSecondSurname() {
        return super.secondSurname;
    }

    public void setSecondSurname(String secondSurname) {
        super.secondSurname = secondSurname;
    }

    public String getEmailAddress() {
        return super.emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        super.emailAddress = emailAddress;
    }

    public String getPassword() {
        return super.password;
    }

    public void setPassword(String password) {
        super.password = password;
    }

    public String getAlternateEmail() {
        return super.alternateEmail;
    }

    public void setAlternateEmail(String alternateEmail) {
        super.alternateEmail = alternateEmail;
    }

    public String getPhoneNumber() {
        return super.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        super.phoneNumber = phoneNumber;
    }

    public int getIdProfessor() {
        return super.idProfessor;
    }

    public void setIdProfessor(int idProfessor) {
        super.idProfessor = idProfessor;
    }

    public String getPersonalNumber() {
        return super.personalNumber;
    }

    public void setPersonalNumber(String personalNumber) {
        super.personalNumber = personalNumber;
    }

    public ProfessorStatus getStatus(){
        return super.status;
    }

    public void setStatus(ProfessorStatus status){
        super.status = status;
    }

    public int getIdAcademicBodyHead() {
        return this.idAcademicBodyHead;
    }

    public void setIdAcademicBodyHead(int idAcademicBodyHead) {
        this.idAcademicBodyHead = idAcademicBodyHead;
    }

}
