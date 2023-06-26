package mx.uv.fei.logic.domain;

import java.sql.Date;
import java.util.ArrayList;

public class ResearchProject {
    private Date dueDate;
    private Date startDate;
    private ArrayList<Director> directors;
    private ArrayList<Student> students;
    private int id;
    private KGAL kgal;
    private String validationStatus;
    private String description;
    private String expectedResult;
    private String requirements;
    private String suggestedBibliography;
    private String title;
    
    public ResearchProject(){
        directors = new ArrayList<>();
        students = new ArrayList();
        kgal = new KGAL();
    }
    
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
    
    public Date getDueDate() {
        return dueDate;
    }
    
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    
    public Date getStartDate() {
        return startDate;
    }
    
    public void addDirector(Director director) {
        if(directors.size() <= 3){
            directors.add(director);
        }
    }
    
    public ArrayList<Director> getDirectors(){
        return directors;
    }
    
    public void addStudent(Student student) {
        if(students.size() <= 2){
            students.add(student);
        }
    }

    public ArrayList<Student> getStudents() {
        return students;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }
    
    public void setKgal(KGAL kgal) {
        this.kgal = kgal;
    }
    
    public KGAL getKgal() {
        return kgal;
    }
    
    public void setValidationStatus(String validationStatus) {
        this.validationStatus = validationStatus;
    }
    
    public String getValidationStatus() {
        return validationStatus;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setExpectedResult(String expectedResult) {
        this.expectedResult = expectedResult;
    }
    
    public String getExpectedResult() {
        return expectedResult;
    }
    
    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }
    
    public String getRequirements() {
        return requirements;
    }
    
    public void setSuggestedBibliography(String suggestedBibliography) {
        this.suggestedBibliography = suggestedBibliography;
    }
    
    public String getSuggestedBibliography() {
        return suggestedBibliography;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getTitle() {
        return title;
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