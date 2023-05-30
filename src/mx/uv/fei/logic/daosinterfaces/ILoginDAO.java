package mx.uv.fei.logic.daosinterfaces;

import mx.uv.fei.logic.domain.AcademicBodyHead;
import mx.uv.fei.logic.domain.DegreeBoss;
import mx.uv.fei.logic.domain.Professor;
import mx.uv.fei.logic.domain.Student;
import mx.uv.fei.logic.exceptions.LoginException;

public interface ILoginDAO {
    public Student logInStudent(String matricle, String password) throws LoginException;
    public Professor logInProfessor(String emailAddress, String password) throws LoginException;
    public DegreeBoss logInAdmin(String emailAddress, String password) throws LoginException;
    public AcademicBodyHead logInAcademicBodyHead(String emailAddress, String password) throws LoginException;
}
