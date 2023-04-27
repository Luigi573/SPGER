package mx.uv.fei.logic.daosinterfaces;

import java.util.ArrayList;

import mx.uv.fei.logic.domain.AcademicBodyHead;

public interface IAcademicBodyHeadDAO {
    public void addAcademicBodyHeadToDatabase(AcademicBodyHead academicBodyHead);
    public ArrayList<AcademicBodyHead> getAcademicBodyHeadsFromDatabase();
    public ArrayList<AcademicBodyHead> getSpecifiedAcademicBodyHeadsFromDatabase(String academicBodyHeadName);
    public AcademicBodyHead getAcademicBodyHeadFromDatabase(String academicBodyHeadName);
}
