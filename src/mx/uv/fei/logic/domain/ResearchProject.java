package mx.uv.fei.logic.domain;

import java.sql.Date;

public class ResearchProject {
    private int researchProjectId;
    private int studentId1;
    private int studentId2;
    private int directorId1;
    private int directorId2;
    private int directorId3;
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

    public int getStudentId1() {
        return studentId1;
    }

    public void setStudentId1(int studentId1) {
        this.studentId1 = studentId1;
    }

    public int getStudentId2() {
        return studentId2;
    }

    public void setStudentId2(int studentId2) {
        this.studentId2 = studentId2;
    }

    public int getDirectorId1() {
        return directorId1;
    }

    public void setDirectorId1(int directorId1) {
        this.directorId1 = directorId1;
    }

    public int getDirectorId2() {
        return directorId2;
    }

    public void setDirectorId2(int directorId2) {
        this.directorId2 = directorId2;
    }

    public int getDirectorId3() {
        return directorId3;
    }

    public void setDirectorId3(int directorId3) {
        this.directorId3 = directorId3;
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