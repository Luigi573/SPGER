package mx.uv.fei.logic.domain;

import java.sql.Date;

public class ResearchProject {
    private int researchProjectId;
    private int studentMatricle1;
    private int studentMatricle2;
    private int directorStaffNumber1;
    private int directorStaffNumber2;
    private int directorStaffNumber3;
    private int kgalId;
    private String description;
    private Date dueDate;
    private String expectedResults;
    private String requirements;
    private Date startDate;
    private String suggestedBibliography;
    private String title;
    private String validationStatus;

    public int getResearchProjectId() {
        return researchProjectId;
    }

    public void setResearchProjectId(int researchProjectId) {
        this.researchProjectId = researchProjectId;
    }

    public int getStudentMatricle1() {
        return studentMatricle1;
    }

    public void setStudentMatricle1(int studentMatricle1) {
        this.studentMatricle1 = studentMatricle1;
    }

    public int getStudentMatricle2() {
        return studentMatricle2;
    }

    public void setStudentMatricle2(int studentMatricle2) {
        this.studentMatricle2 = studentMatricle2;
    }

    public int getDirectorStaffNumber1() {
        return directorStaffNumber1;
    }

    public void setDirectorStaffNumber1(int directorStaffNumber1) {
        this.directorStaffNumber1 = directorStaffNumber1;
    }

    public int getDirectorStaffNumber2() {
        return directorStaffNumber2;
    }

    public void setDirectorStaffNumber2(int directorStaffNumber2) {
        this.directorStaffNumber2 = directorStaffNumber2;
    }

    public int getDirectorStaffNumber3() {
        return directorStaffNumber3;
    }

    public void setDirectorStaffNumber3(int directorStaffNumber3) {
        this.directorStaffNumber3 = directorStaffNumber3;
    }

    public int getKgalId() {
        return kgalId;
    }

    public void setKgalId(int kgalId) {
        this.kgalId = kgalId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getExpectedResults() {
        return expectedResults;
    }

    public void setExpectedResults(String expectedResults) {
        this.expectedResults = expectedResults;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getSuggestedBibliography() {
        return suggestedBibliography;
    }

    public void setSuggestedBibliography(String suggestedBibliography) {
        this.suggestedBibliography = suggestedBibliography;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValidationStatus() {
        return validationStatus;
    }

    public void setValidationStatus(String validationStatus) {
        this.validationStatus = validationStatus;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ResearchProject researchProject) {
            return researchProject.getResearchProjectId() == this.researchProjectId;
        }

        return false;
    }

    @Override
    public String toString() {
        return title;
    }
}