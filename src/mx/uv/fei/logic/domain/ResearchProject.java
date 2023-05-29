package mx.uv.fei.logic.domain;

import java.sql.Date;
import java.util.ArrayList;
import mx.uv.fei.logic.domain.statuses.ResearchProjectStatus;

public class ResearchProject {
    private Date dueDate;
    private Date startDate;
    private ArrayList<Director> directors;
    private int id;
    private KGAL kgal;
    private String validationStatus;
    private String description;
    private String expectedResult;
    private String requirements;
    private String suggestedBibliography;
    private String title;
    private Student student;
    
    public ResearchProject(){
        directors = new ArrayList();
        kgal = new KGAL();
        student = new Student();
    }
    
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public void addDirector(Director director) {
        if(directors.size() <= 3){
            directors.add(director);
        }
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setKgal(KGAL kgal) {
        this.kgal = kgal;
    }
    public void setValidationStatus(String validationStatus) {
        this.validationStatus = validationStatus;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setExpectedResult(String expectedResult) {
        this.expectedResult = expectedResult;
    }
    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }
    public void setSuggestedBibliography(String suggestedBibliography) {
        this.suggestedBibliography = suggestedBibliography;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setStudent(Student student) {
        this.student = student;
    }
    public Date getDueDate() {
        return dueDate;
    }
    public Date getStartDate() {
        return startDate;
    }
    public Director getDirector(int index) {
        if(index > 0 && index <= directors.size()){
            return directors.get(index);
        }
        
        return null;
    }
    public int getId() {
        return id;
    }
    public KGAL getKgal() {
        return kgal;
    }
    public String getValidationStatus() {
        return validationStatus;
    }
    public String getDescription() {
        return description;
    }
    public String getExpectedResult() {
        return expectedResult;
    }
    public String getRequirements() {
        return requirements;
    }
    public String getSuggestedBibliography() {
        return suggestedBibliography;
    }
    public String getTitle() {
        return title;
    }
    public Student getStudent() {
        return student;
    }
    public void printData() {
        System.out.println("ID: " + id);
        System.out.println("Title: " + title);
        System.out.println("Description: " + description);
        System.out.println("Requirements: " + requirements);
        System.out.println("Suggested Bibliography: " + suggestedBibliography);
        System.out.println("Start Date: " + startDate.toString());
        System.out.println("Due Date: " + dueDate.toString());
        System.out.println("Expected Result: " + expectedResult);
        System.out.println("Directors: ");
        
        for (Director director : directors) {
            System.out.println("\t" + director.getName());
        }
        
        System.out.println("KGAL: " + kgal.getDescription());
        System.out.println("Student: " + student.getName());
    }
    @Override
    public boolean equals(Object obj){
        if(obj instanceof ResearchProject researchProject){
            return researchProject.getId() == this.id;
        }
        
        return false;
    }
    @Override
    public String toString(){
        return title;
    }
}