package mx.uv.fei.logic.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.logic.daosinterfaces.IResearchDAO;
import mx.uv.fei.logic.domain.Director;
import mx.uv.fei.logic.domain.ResearchProject;
import mx.uv.fei.logic.domain.Student;
import mx.uv.fei.logic.domain.statuses.ResearchProjectStatus;
import mx.uv.fei.logic.exceptions.DataInsertionException;
import mx.uv.fei.logic.exceptions.DataRetrievalException;

public class ResearchDAO implements IResearchDAO{
    private final DataBaseManager dataBaseManager;
    
    public ResearchDAO(){
        dataBaseManager = new DataBaseManager();
    }
    @Override
    public int addResearch(ResearchProject research) throws DataInsertionException {
        int generatedId = 0;
        PreparedStatement statement;
        String query = "INSERT INTO ResearchProjects(fechaFin, fechaInicio, IdLGAC, descripción, "
                + "resultadosEsperados, requisitos, bibliografíaRecomendada, título, Matrícula1, Matrícula2, V°B°, "
                + "IdDirector1, IdDirector2, IdDirector3) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
        
        try{
            statement = dataBaseManager.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            
            statement.setDate(1, research.getDueDate());
            statement.setDate(2, research.getStartDate());
            
            if(research.getKgal().getKgalID() != 0){
                statement.setInt(3, research.getKgal().getKgalID());
            }else{
                statement.setNull(3, java.sql.Types.INTEGER);
            }
            
            statement.setString(4, research.getDescription());
            statement.setString(5, research.getExpectedResult());
            statement.setString(6, research.getRequirements());
            statement.setString(7, research.getSuggestedBibliography());
            statement.setString(8, research.getTitle());
            
            for(int i = 0; i < 2; i++){
                if(i < research.getStudents().size()){
                    statement.setString(i + 9, research.getStudents().get(i).getMatricle());
                }else{
                    statement.setNull(i + 9, java.sql.Types.INTEGER);
                }
            }
            
            statement.setString(11, research.getValidationStatus());
            
            for(int i = 0; i < 3; i++){
                if(i < research.getDirectors().size()){
                    statement.setInt(i + 12, research.getDirectors().get(i).getDirectorId());
                }else{
                    statement.setNull(i + 12, java.sql.Types.INTEGER);
                }
            }
            
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            
            if(generatedKeys.next()){
                generatedId = generatedKeys.getInt(1);
            }
        }catch(SQLException exception){
            throw new DataInsertionException("Error de conexión. Inténtelo de nuevo más tarde");
        }finally{
            dataBaseManager.closeConnection();
        }
        
        return generatedId;
    }

    @Override
    public ArrayList<ResearchProject> getResearchProjectList() throws DataRetrievalException{
        ArrayList<ResearchProject> researchProjectList = new ArrayList<>();
        PreparedStatement statement;
        String query = "SELECT DISTINCT a.researchProjectId, a.fechaFin, a.fechaInicio, l.IdLGAC, l.descripción AS LGAC, a.título, a.V°B°, "
                + " a.IdDirector1, up1.name, up1.firstSurname, up1.secondSurname, a.IdDirector2, up2.name, up2.firstSurname, up2.secondSurname, a.IdDirector3, "
                + " up3.name, up3.firstSurname, up3.secondSurname, a.descripción, a.resultadosEsperados, a.requisitos, a.bibliografíaRecomendada, "
                + " a.Matrícula1, ue1.name, ue1.firstSurname, ue1.secondSurname, "
                + " a.Matrícula2, ue2.name, ue2.firstSurname, ue2.secondSurname FROM ResearchProjects a "
                + " LEFT JOIN Directors d1 ON a.IdDirector1 = d1.IdDirector LEFT JOIN Professors p1 ON d1.staffNumber = p1.staffNumber LEFT JOIN Users up1 ON p1.userId = up1.userId "
                + " LEFT JOIN Directors d2 ON a.IdDirector2 = d2.IdDirector LEFT JOIN Professors p2 ON d2.staffNumber = p2.staffNumber LEFT JOIN Users up2 ON p2.userId = up2.userId "
                + " LEFT JOIN Directors d3 ON a.IdDirector3 = d3.IdDirector LEFT JOIN Professors p3 ON d3.staffNumber = p3.staffNumber LEFT JOIN Users up3 ON p3.userId = up3.userId "
                + " LEFT JOIN Estudiantes e1 ON a.Matrícula1 = e1.Matrícula LEFT JOIN Users ue1 ON e1.userId = ue1.userId "
                + " LEFT JOIN Estudiantes e2 ON a.Matrícula2 = e2.Matrícula LEFT JOIN Users ue2 ON e2.userId = ue2.userId "
                + " LEFT JOIN LGAC l ON l.IdLGAC = a.IdLGAC ORDER BY fechaFin, fechaInicio, título ASC";
        
        try{
            statement = dataBaseManager.getConnection().prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                ResearchProject research = new ResearchProject();
                
                research.setId(resultSet.getInt("a.researchProjectId"));
                research.setDueDate(resultSet.getDate("a.fechaFin"));
                research.setStartDate(resultSet.getDate("a.fechaInicio"));
                
                //Concatenating column names since they're almost the same
                for(int i = 1; i <= 3; i++){
                    Director director = new Director();
                    director.setDirectorId(resultSet.getInt("a.IdDirector"+ i));
                    
                    if(!resultSet.wasNull()){
                        director.setName(resultSet.getString("up" + i + ".name"));
                        director.setFirstSurname(resultSet.getString("up" + i + ".firstSurname"));
                        director.setSecondSurname(resultSet.getString("up" + i + ".secondSurname"));
                        
                        research.addDirector(director);
                    }
                }
                
                research.getKgal().setKgalID(resultSet.getInt("l.IdLGAC"));
                
                if(!resultSet.wasNull()){
                    research.getKgal().setDescription(resultSet.getString("LGAC"));
                }
                
                research.setDescription(resultSet.getString("a.descripción"));
                research.setExpectedResult(resultSet.getString("a.resultadosEsperados"));
                research.setRequirements(resultSet.getString("a.requisitos"));
                research.setSuggestedBibliography(resultSet.getString("a.bibliografíaRecomendada"));
                research.setTitle(resultSet.getString("a.título"));
                research.setValidationStatus(resultSet.getString("a.V°B°"));
                
                Student student = new Student();
                student.setMatricle(resultSet.getString("a.Matrícula1"));
                
                if(!resultSet.wasNull()){
                    student.setName(resultSet.getString("ue1.name"));
                    student.setFirstSurname(resultSet.getString("ue1.firstSurname"));
                    student.setSecondSurname(resultSet.getString("ue1.secondSurname"));
                    
                    research.addStudent(student);
                }
                
                Student student2 = new Student();
                student2.setMatricle(resultSet.getString("a.Matrícula2"));
                if(!resultSet.wasNull()){
                    student2.setName(resultSet.getString("ue2.name"));
                    student2.setFirstSurname(resultSet.getString("ue2.firstSurname"));
                    student2.setSecondSurname(resultSet.getString("ue2.secondSurname"));
                    
                    research.addStudent(student2);
                }
                
                researchProjectList.add(research);
            }
        }catch(SQLException exception){
            throw new DataRetrievalException("Fallo al recuperar la informacion. Inténtelo de nuevo más tarde");
        }finally{
            dataBaseManager.closeConnection();
        }
        
        return researchProjectList;
    }
    
    public ArrayList<ResearchProject> getDirectorsResearch(int staffNumber) throws DataRetrievalException{
        ArrayList<ResearchProject> researchList = new ArrayList<>();
        PreparedStatement statement;
        String query = "SELECT DISTINCT a.researchProjectId, a.título, a.Matrícula1, u1.name, u1.firstSurname, u1.secondSurname, " +
            " a.Matrícula2, u2.name, u2.firstSurname, u2.secondSurname FROM ResearchProjects a " +
            " LEFT JOIN Directors d1 ON a.IdDirector1 = d1.IdDirector LEFT JOIN Professors p1 ON d1.staffNumber = p1.staffNumber " +
            " LEFT JOIN Directors d2 ON a.IdDirector2 = d2.IdDirector  LEFT JOIN Professors p2 ON d2.staffNumber = p2.staffNumber " +
            " LEFT JOIN Directors d3 ON a.IdDirector3 = d3.IdDirector LEFT JOIN Professors p3 ON d3.staffNumber = p3.staffNumber " +
            " LEFT JOIN Estudiantes e1 ON a.Matrícula1 = e1.Matrícula LEFT JOIN Users u1 ON e1.userId = u1.userId " + 
            " LEFT JOIN Estudiantes e2 ON a.Matrícula2 = e2.Matrícula LEFT JOIN Users u2 ON e2.userId = u2.userId " +
            " WHERE (p1.staffNumber = ? OR p2.staffNumber = ? OR p3.staffNumber = ?)";
        
        try{
            statement = dataBaseManager.getConnection().prepareStatement(query);
            statement.setInt(1, staffNumber);
            statement.setInt(2, staffNumber);
            statement.setInt(3, staffNumber);
            
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                ResearchProject research = new ResearchProject();
                
                research.setId(resultSet.getInt("a.researchProjectId"));
                research.setTitle(resultSet.getString("a.título"));
                
                Student student1 = new Student();
                student1.setMatricle(resultSet.getString("a.Matrícula1"));
                student1.setName(resultSet.getString("u1.name"));
                student1.setFirstSurname(resultSet.getString("u1.firstSurname"));
                student1.setSecondSurname(resultSet.getString("u1.secondSurname"));
                research.addStudent(student1);
                
                Student student2 = new Student();
                student2.setMatricle(resultSet.getString("a.Matrícula2"));
                student2.setName(resultSet.getString("u2.name"));
                student2.setFirstSurname(resultSet.getString("u2.firstSurname"));
                student2.setSecondSurname(resultSet.getString("u2.secondSurname"));
                research.addStudent(student2);
                
                researchList.add(research);
            }            
        }catch(SQLException exception){
            throw new DataRetrievalException("No se pudo establecer conexión con la base de datos, inténtelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }
        
        return researchList;
    }
    
    public ResearchProject getStudentsResearch(String matricle) throws DataRetrievalException{
        ResearchProject research = new ResearchProject();
        PreparedStatement statement;
        String query = "SELECT researchProjectId, título FROM ResearchProjects WHERE Matrícula1 IN(?) OR Matrícula2 IN(?)";
        
        try{
            statement = dataBaseManager.getConnection().prepareStatement(query);
            statement.setString(1, matricle);
            statement.setString(2, matricle);
            
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                research.setId(resultSet.getInt("researchProjectId"));
                research.setTitle(resultSet.getString("título"));
            }
            
            resultSet.close();
            statement.close();
        }catch(SQLException exception){
            throw new DataRetrievalException("No se pudo establecer conexión con la base de datos, inténtelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }
        
        return research;
    }
    
    public ArrayList<ResearchProject> getCourseResearch(int NRC) throws DataRetrievalException{
        ArrayList<ResearchProject> researchList = new ArrayList<>();
        PreparedStatement statement;
        String query = "SELECT DISTINCT a.researchProjectId, a.título, a.Matrícula1, u1.name, u1.firstSurname, u1.secondSurname, "
                + "a.Matrícula2, u2.name, u2.firstSurname, u2.secondSurname FROM ResearchProjects a "
                + "LEFT JOIN Estudiantes e1 ON a.Matrícula1 = e1.Matrícula INNER JOIN EstudiantesCurso ec1 ON e1.Matrícula = ec1.Matrícula "
                + "LEFT JOIN Estudiantes e2 ON a.Matrícula2 = e2.Matrícula INNER JOIN EstudiantesCurso ec2 ON e2.Matrícula = ec2.Matrícula "
                + "LEFT JOIN Cursos c1 ON ec1.NRC = c1.NRC INNER JOIN Users u1 ON e1.userId = u1.userId "
                + "LEFT JOIN Cursos c2 ON ec1.NRC = c2.NRC INNER JOIN Users u2 ON e2.userId = u2.userId "
                + "WHERE c1.NRC IN(?) OR c2.NRC IN(?);";
        
        try{
            statement = dataBaseManager.getConnection().prepareStatement(query);
            statement.setInt(1, NRC);
            statement.setInt(2, NRC);
            
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                ResearchProject research = new ResearchProject();
                
                research.setId(resultSet.getInt("a.researchProjectId"));
                research.setTitle(resultSet.getString("a.título"));
                
                Student student = new Student();
                student.setMatricle(resultSet.getString("a.Matrícula1"));
                
                if(!resultSet.wasNull()){
                    student.setName(resultSet.getString("u1.name"));
                    student.setFirstSurname(resultSet.getString("u1.firstSurname"));
                    student.setSecondSurname(resultSet.getString("u1.secondSurname"));
                    
                    research.addStudent(student);
                }
                
                Student student2 = new Student();
                student2.setMatricle(resultSet.getString("a.Matrícula2"));
                if(!resultSet.wasNull()){
                    student2.setName(resultSet.getString("u2.name"));
                    student2.setFirstSurname(resultSet.getString("u2.firstSurname"));
                    student2.setSecondSurname(resultSet.getString("u2.secondSurname"));
                    
                    research.addStudent(student2);
                }
                
                researchList.add(research);
            }
            
        }catch(SQLException exception){
            exception.printStackTrace();
            throw new DataRetrievalException("No se pudo establecer conexión con la base de datos, inténtelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }
        
        return researchList;
    }
    
    public ArrayList<ResearchProject> getSpecifiedResearchProjectList(String researchName) throws DataRetrievalException{
        ArrayList<ResearchProject> researchProjectList = new ArrayList<>();
        PreparedStatement statement;
        String query = "SELECT DISTINCT a.researchProjectId, a.fechaFin, a.fechaInicio, l.IdLGAC, l.descripción AS LGAC, a.título, a.V°B°, "
                + " a.IdDirector1, up1.name, up1.firstSurname, up1.secondSurname, a.IdDirector2, up2.name, up2.firstSurname, up2.secondSurname, a.IdDirector3, "
                + " up3.name, up3.firstSurname, up3.secondSurname, a.descripción, a.resultadosEsperados, a.requisitos, a.bibliografíaRecomendada, "
                + " a.Matrícula1, ue1.name, ue1.firstSurname, ue1.secondSurname, a.Matrícula2, ue2.name, ue2.firstSurname, ue2.secondSurname FROM ResearchProjects a "
                + " LEFT JOIN Directors d1 ON a.IdDirector1 = d1.IdDirector LEFT JOIN Professors p1 ON d1.staffNumber = p1.staffNumber LEFT JOIN Users up1 ON p1.userId = up1.userId "
                + " LEFT JOIN Directors d2 ON a.IdDirector2 = d2.IdDirector LEFT JOIN Professors p2 ON d2.staffNumber = p2.staffNumber LEFT JOIN Users up2 ON p2.userId = up2.userId "
                + " LEFT JOIN Directors d3 ON a.IdDirector3 = d3.IdDirector LEFT JOIN Professors p3 ON d3.staffNumber = p3.staffNumber LEFT JOIN Users up3 ON p3.userId = up3.userId "
                + " LEFT JOIN Estudiantes e1 ON a.Matrícula1 = e1.Matrícula LEFT JOIN Users ue1 ON e1.userId = ue1.userId "
                + " LEFT JOIN Estudiantes e2 ON a.Matrícula2 = e2.Matrícula LEFT JOIN Users ue2 ON e2.userId = ue2.userId "
                + " LEFT JOIN LGAC l ON l.IdLGAC = a.IdLGAC WHERE a.título LIKE ? ORDER BY fechaFin, fechaInicio, título ASC";
        
        try{
            statement = dataBaseManager.getConnection().prepareStatement(query);
            statement.setString(1, researchName + '%');
            ResultSet resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                ResearchProject research = new ResearchProject();
                
                research.setId(resultSet.getInt("a.researchProjectId"));
                research.setDueDate(resultSet.getDate("a.fechaFin"));
                research.setStartDate(resultSet.getDate("a.fechaInicio"));
                
                //Concatenating column names since they're almost the same
                for(int i = 1; i <= 3; i++){
                    Director director = new Director();
                    director.setDirectorId(resultSet.getInt("a.IdDirector"+ i));
                    
                    if(!resultSet.wasNull()){
                        director.setName(resultSet.getString("up" + i + ".name"));
                        director.setFirstSurname(resultSet.getString("up" + i + ".firstSurname"));
                        director.setSecondSurname(resultSet.getString("up" + i + ".firstSurname"));
                        
                        research.addDirector(director);
                    }
                }
                
                research.getKgal().setKgalID(resultSet.getInt("l.IdLGAC"));
                
                if(!resultSet.wasNull()){
                    research.getKgal().setDescription(resultSet.getString("LGAC"));
                }
                
                research.setDescription(resultSet.getString("a.descripción"));
                research.setExpectedResult(resultSet.getString("a.resultadosEsperados"));
                research.setRequirements(resultSet.getString("a.requisitos"));
                research.setSuggestedBibliography(resultSet.getString("a.bibliografíaRecomendada"));
                research.setTitle(resultSet.getString("a.título"));
                research.setValidationStatus(resultSet.getString("a.V°B°"));
                
                Student student = new Student();
                student.setMatricle(resultSet.getString("a.Matrícula1"));
                
                if(!resultSet.wasNull()){
                    student.setName(resultSet.getString("ue1.name"));
                    student.setFirstSurname(resultSet.getString("ue1.firstSurname"));
                    student.setSecondSurname(resultSet.getString("ue1.secondSurname"));
                    
                    research.addStudent(student);
                }
                
                Student student2 = new Student();
                student2.setMatricle(resultSet.getString("a.Matrícula2"));
                if(!resultSet.wasNull()){
                    student2.setName(resultSet.getString("ue2.name"));
                    student2.setFirstSurname(resultSet.getString("ue2.firstSurname"));
                    student2.setSecondSurname(resultSet.getString("ue2.secondSurname"));
                    
                    research.addStudent(student2);
                }
                
                researchProjectList.add(research);
            }
        }catch(SQLException exception){
            throw new DataRetrievalException("Fallo al recuperar la informacion. Inténtelo de nuevo más tarde");
        }finally{
            dataBaseManager.closeConnection();
        }
        
        return researchProjectList;
    }

    public ArrayList<ResearchProject> getSpecifiedValidatedResearchProjectList(String researchName) throws DataRetrievalException{
        ArrayList<ResearchProject> researchProjectList = new ArrayList<>();
        PreparedStatement statement;
        
        String query = "SELECT DISTINCT a.researchProjectId, a.fechaFin, a.fechaInicio, l.IdLGAC, l.descripción AS LGAC, a.título, a.V°B°, "
                + " a.IdDirector1, up1.name, up1.firstSurname, up1.secondSurname, a.IdDirector2, up2.name, up2.firstSurname, up2.secondSurname, a.IdDirector3, "
                + " up3.name, up3.firstSurname, up3.secondSurname, a.descripción, a.resultadosEsperados, a.requisitos, a.bibliografíaRecomendada, "
                + " a.Matrícula1, ue1.name, ue1.firstSurname, ue1.secondSurname, a.Matrícula2, ue2.name, ue2.firstSurname, ue2.secondSurname FROM ResearchProjects a "
                + " LEFT JOIN Directors d1 ON a.IdDirector1 = d1.IdDirector LEFT JOIN Professors p1 ON d1.staffNumber = p1.staffNumber LEFT JOIN Users up1 ON p1.userId = up1.userId "
                + " LEFT JOIN Directors d2 ON a.IdDirector2 = d2.IdDirector LEFT JOIN Professors p2 ON d2.staffNumber = p2.staffNumber LEFT JOIN Users up2 ON p2.userId = up2.userId "
                + " LEFT JOIN Directors d3 ON a.IdDirector3 = d3.IdDirector LEFT JOIN Professors p3 ON d3.staffNumber = p3.staffNumber LEFT JOIN Users up3 ON p3.userId = up3.userId "
                + " LEFT JOIN Estudiantes e1 ON a.Matrícula1 = e1.Matrícula LEFT JOIN Users ue1 ON e1.userId = ue1.userId "
                + " LEFT JOIN Estudiantes e2 ON a.Matrícula2 = e2.Matrícula LEFT JOIN Users ue2 ON e2.userId = ue2.userId "
                + " LEFT JOIN LGAC l ON l.IdLGAC = a.IdLGAC WHERE a.título LIKE ? && a.V°B° = 'Validado' ORDER BY fechaFin, fechaInicio, título ASC";
        
        try{
            statement = dataBaseManager.getConnection().prepareStatement(query);
            statement.setString(1, researchName + '%');
            ResultSet resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                ResearchProject research = new ResearchProject();
                
                research.setId(resultSet.getInt("a.researchProjectId"));
                research.setDueDate(resultSet.getDate("a.fechaFin"));
                research.setStartDate(resultSet.getDate("a.fechaInicio"));
                
                //Concatenating column names since they're almost the same
                for(int i = 1; i <= 3; i++){
                    Director director = new Director();
                    director.setDirectorId(resultSet.getInt("a.IdDirector"+ i));
                    
                    if(!resultSet.wasNull()){
                        director.setName(resultSet.getString("up" + i + ".name"));
                        director.setFirstSurname(resultSet.getString("up" + i + ".firstSurname"));
                        director.setSecondSurname(resultSet.getString("up" + i + ".firstSurname"));
                        
                        research.addDirector(director);
                    }
                }
                
                research.getKgal().setKgalID(resultSet.getInt("l.IdLGAC"));
                
                if(!resultSet.wasNull()){
                    research.getKgal().setDescription(resultSet.getString("LGAC"));
                }
                
                research.setDescription(resultSet.getString("a.descripción"));
                research.setExpectedResult(resultSet.getString("a.resultadosEsperados"));
                research.setRequirements(resultSet.getString("a.requisitos"));
                research.setSuggestedBibliography(resultSet.getString("a.bibliografíaRecomendada"));
                research.setTitle(resultSet.getString("a.título"));
                research.setValidationStatus(resultSet.getString("a.V°B°"));
                
                Student student = new Student();
                student.setMatricle(resultSet.getString("a.Matrícula1"));
                
                if(!resultSet.wasNull()){
                    student.setName(resultSet.getString("ue1.name"));
                    student.setFirstSurname(resultSet.getString("ue1.firstSurname"));
                    student.setSecondSurname(resultSet.getString("ue1.secondSurname"));
                }
                
                Student student2 = new Student();
                student2.setMatricle(resultSet.getString("a.Matrícula2"));
                if(!resultSet.wasNull()){
                    student2.setName(resultSet.getString("ue2.name"));
                    student2.setFirstSurname(resultSet.getString("ue2.firstSurname"));
                    student2.setSecondSurname(resultSet.getString("ue2.secondSurname"));
                    
                    research.getStudents().add(student2);
                }
                
                researchProjectList.add(research);
            }
        }catch(SQLException exception){
            throw new DataRetrievalException("Fallo al recuperar la informacion. Inténtelo de nuevo más tarde");
        }finally{
            dataBaseManager.closeConnection();
        }
        
        return researchProjectList;
    }

    public ArrayList<ResearchProject> getSpecifiedNotValidatedResearchProjectList(String researchName) throws DataRetrievalException{
        ArrayList<ResearchProject> researchProjectList = new ArrayList<>();
        PreparedStatement statement;
        
        String query = "SELECT DISTINCT a.researchProjectId, a.fechaFin, a.fechaInicio, l.IdLGAC, l.descripción AS LGAC, a.título, a.V°B°, "
                + " a.IdDirector1, up1.name, up1.firstSurname, up1.secondSurname, a.IdDirector2, up2.name, up2.firstSurname, up2.secondSurname, a.IdDirector3, "
                + " up3.name, up3.firstSurname, up3.secondSurname, a.descripción, a.resultadosEsperados, a.requisitos, a.bibliografíaRecomendada, "
                + " a.Matrícula1, ue1.name, ue1.firstSurname, ue1.secondSurname, a.Matrícula2, ue2.name, ue2.firstSurname, ue2.secondSurname FROM ResearchProjects a "
                + " LEFT JOIN Directors d1 ON a.IdDirector1 = d1.IdDirector LEFT JOIN Professors p1 ON d1.staffNumber = p1.staffNumber LEFT JOIN Users up1 ON p1.userId = up1.userId "
                + " LEFT JOIN Directors d2 ON a.IdDirector2 = d2.IdDirector LEFT JOIN Professors p2 ON d2.staffNumber = p2.staffNumber LEFT JOIN Users up2 ON p2.userId = up2.userId "
                + " LEFT JOIN Directors d3 ON a.IdDirector3 = d3.IdDirector LEFT JOIN Professors p3 ON d3.staffNumber = p3.staffNumber LEFT JOIN Users up3 ON p3.userId = up3.userId "
                + " LEFT JOIN Estudiantes e1 ON a.Matrícula1 = e1.Matrícula LEFT JOIN Users ue1 ON e1.userId = ue1.userId "
                + " LEFT JOIN Estudiantes e2 ON a.Matrícula2 = e2.Matrícula LEFT JOIN Users ue2 ON e2.userId = ue2.userId "
                + " LEFT JOIN LGAC l ON l.IdLGAC = a.IdLGAC WHERE a.título LIKE ? && a.V°B° = 'Propuesto' ORDER BY fechaFin, fechaInicio, título ASC";
        
        try{
            statement = dataBaseManager.getConnection().prepareStatement(query);
            statement.setString(1, researchName + '%');
            ResultSet resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                ResearchProject research = new ResearchProject();
                
                research.setId(resultSet.getInt("a.researchProjectId"));
                research.setDueDate(resultSet.getDate("a.fechaFin"));
                research.setStartDate(resultSet.getDate("a.fechaInicio"));
                
                //Concatenating column names since they're almost the same
                for(int i = 1; i <= 3; i++){
                    Director director = new Director();
                    director.setDirectorId(resultSet.getInt("a.IdDirector"+ i));
                    
                    if(!resultSet.wasNull()){
                        director.setName(resultSet.getString("up" + i + ".name"));
                        director.setFirstSurname(resultSet.getString("up" + i + ".firstSurname"));
                        director.setSecondSurname(resultSet.getString("up" + i + ".firstSurname"));
                        
                        research.addDirector(director);
                    }
                }
                
                research.getKgal().setKgalID(resultSet.getInt("l.IdLGAC"));
                
                if(!resultSet.wasNull()){
                    research.getKgal().setDescription(resultSet.getString("LGAC"));
                }
                
                research.setDescription(resultSet.getString("a.descripción"));
                research.setExpectedResult(resultSet.getString("a.resultadosEsperados"));
                research.setRequirements(resultSet.getString("a.requisitos"));
                research.setSuggestedBibliography(resultSet.getString("a.bibliografíaRecomendada"));
                research.setTitle(resultSet.getString("a.título"));
                research.setValidationStatus(resultSet.getString("a.V°B°"));
                
                Student student = new Student();
                student.setMatricle(resultSet.getString("a.Matrícula1"));
                
                if(!resultSet.wasNull()){
                    student.setName(resultSet.getString("ue1.name"));
                    student.setFirstSurname(resultSet.getString("ue1.firstSurname"));
                    student.setSecondSurname(resultSet.getString("ue1.secondSurname"));
                }
                
                Student student2 = new Student();
                student2.setMatricle(resultSet.getString("a.Matrícula2"));
                if(!resultSet.wasNull()){
                    student2.setName(resultSet.getString("ue2.name"));
                    student2.setFirstSurname(resultSet.getString("ue2.firstSurname"));
                    student2.setSecondSurname(resultSet.getString("ue2.secondSurname"));
                    
                    research.getStudents().add(student2);
                }
                
                researchProjectList.add(research);
            }
        }catch(SQLException exception){
            throw new DataRetrievalException("Fallo al recuperar la informacion. Inténtelo de nuevo más tarde");
        }finally{
            dataBaseManager.closeConnection();
        }
        
        return researchProjectList;
    }

    public ArrayList<ResearchProject> getSpecifiedValidatedAndNotValidatedResearchProjectList(String researchName) throws DataRetrievalException{
        ArrayList<ResearchProject> researchProjectList = new ArrayList<>();
        PreparedStatement statement;
        
        String query = "SELECT DISTINCT a.researchProjectId, a.fechaFin, a.fechaInicio, l.IdLGAC, l.descripción AS LGAC, a.título, a.V°B°, "
                + " a.IdDirector1, up1.name, up1.firstSurname, up1.secondSurname, a.IdDirector2, up2.name, up2.firstSurname, up2.secondSurname, a.IdDirector3, "
                + " up3.name, up3.firstSurname, up3.secondSurname, a.descripción, a.resultadosEsperados, a.requisitos, a.bibliografíaRecomendada, "
                + " a.Matrícula1, ue1.name, ue1.firstSurname, ue1.secondSurname, a.Matrícula2, ue2.name, ue2.firstSurname, ue2.secondSurname FROM ResearchProjects a "
                + " LEFT JOIN Directors d1 ON a.IdDirector1 = d1.IdDirector LEFT JOIN Professors p1 ON d1.staffNumber = p1.staffNumber LEFT JOIN Users up1 ON p1.userId = up1.userId "
                + " LEFT JOIN Directors d2 ON a.IdDirector2 = d2.IdDirector LEFT JOIN Professors p2 ON d2.staffNumber = p2.staffNumber LEFT JOIN Users up2 ON p2.userId = up2.userId "
                + " LEFT JOIN Directors d3 ON a.IdDirector3 = d3.IdDirector LEFT JOIN Professors p3 ON d3.staffNumber = p3.staffNumber LEFT JOIN Users up3 ON p3.userId = up3.userId "
                + " LEFT JOIN Estudiantes e1 ON a.Matrícula1 = e1.Matrícula LEFT JOIN Users ue1 ON e1.userId = ue1.userId "
                + " LEFT JOIN Estudiantes e2 ON a.Matrícula2 = e2.Matrícula LEFT JOIN Users ue2 ON e2.userId = ue2.userId "
                + " LEFT JOIN LGAC l ON l.IdLGAC = a.IdLGAC WHERE a.título LIKE ? && (a.V°B° = 'Validado' || a.V°B° = 'Propuesto') ORDER BY fechaFin, fechaInicio, título ASC";
        
        try{
            statement = dataBaseManager.getConnection().prepareStatement(query);
            statement.setString(1, researchName + '%');
            ResultSet resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                ResearchProject research = new ResearchProject();
                
                research.setId(resultSet.getInt("a.researchProjectId"));
                research.setDueDate(resultSet.getDate("a.fechaFin"));
                research.setStartDate(resultSet.getDate("a.fechaInicio"));
                
                //Concatenating column names since they're almost the same
                for(int i = 1; i <= 3; i++){
                    Director director = new Director();
                    director.setDirectorId(resultSet.getInt("a.IdDirector"+ i));
                    
                    if(!resultSet.wasNull()){
                        director.setName(resultSet.getString("up" + i + ".name"));
                        director.setFirstSurname(resultSet.getString("up" + i + ".firstSurname"));
                        director.setSecondSurname(resultSet.getString("up" + i + ".firstSurname"));
                        
                        research.addDirector(director);
                    }
                }
                
                research.getKgal().setKgalID(resultSet.getInt("l.IdLGAC"));
                
                if(!resultSet.wasNull()){
                    research.getKgal().setDescription(resultSet.getString("LGAC"));
                }
                
                research.setDescription(resultSet.getString("a.descripción"));
                research.setExpectedResult(resultSet.getString("a.resultadosEsperados"));
                research.setRequirements(resultSet.getString("a.requisitos"));
                research.setSuggestedBibliography(resultSet.getString("a.bibliografíaRecomendada"));
                research.setTitle(resultSet.getString("a.título"));
                research.setValidationStatus(resultSet.getString("a.V°B°"));
                
                Student student = new Student();
                student.setMatricle(resultSet.getString("a.Matrícula1"));
                
                if(!resultSet.wasNull()){
                    student.setName(resultSet.getString("ue1.name"));
                    student.setFirstSurname(resultSet.getString("ue1.firstSurname"));
                    student.setSecondSurname(resultSet.getString("ue1.secondSurname"));
                    
                    research.addStudent(student);
                }
                
                Student student2 = new Student();
                student2.setMatricle(resultSet.getString("a.Matrícula2"));
                if(!resultSet.wasNull()){
                    student2.setName(resultSet.getString("ue2.name"));
                    student2.setFirstSurname(resultSet.getString("ue2.firstSurname"));
                    student2.setSecondSurname(resultSet.getString("ue2.secondSurname"));
                    
                    research.addStudent(student2);
                }
                
                researchProjectList.add(research);
            }
        }catch(SQLException exception){
            throw new DataRetrievalException("Fallo al recuperar la informacion. Inténtelo de nuevo más tarde");
        }finally{
            dataBaseManager.closeConnection();
        }
        
        return researchProjectList;
    }

    @Override
    public int modifyResearch(ResearchProject research) throws DataInsertionException{
        int result = 0;
        PreparedStatement statement;
        String query = "UPDATE ResearchProjects SET fechaFin = ?, fechaInicio = ?, IdLGAC = ?, descripción = ?, resultadosEsperados = ?, "
                + " requisitos = ?, bibliografíaRecomendada = ?, título = ?, Matrícula1 = ?, Matrícula2 = ?, IdDirector1 = ?, IdDirector2 = ?, IdDirector3 = ? "
                + " WHERE researchProjectId = ?";
        
        try{
            statement = dataBaseManager.getConnection().prepareStatement(query);
            
            statement.setDate(1, research.getDueDate());
            statement.setDate(2, research.getStartDate());
            
            if(research.getKgal().getKgalID() != 0){
                statement.setInt(3, research.getKgal().getKgalID());
            }else{
                statement.setNull(3, java.sql.Types.INTEGER);
            }
            
            statement.setString(4, research.getDescription());
            statement.setString(5, research.getExpectedResult());
            statement.setString(6, research.getRequirements());
            statement.setString(7, research.getSuggestedBibliography());
            statement.setString(8, research.getTitle());
            
            for(int i = 0; i < 2; i++){
                if(i < research.getStudents().size()){
                    statement.setString(i + 9, research.getStudents().get(i).getMatricle());
                }else{
                    statement.setNull(i + 9, java.sql.Types.INTEGER);
                }
            }
            
            for(int i = 0; i < 3; i++){
                if(i < research.getDirectors().size()){
                    statement.setInt(i + 11, research.getDirectors().get(i).getDirectorId());
                }else{
                    statement.setNull(i + 11, java.sql.Types.INTEGER);
                }
            }
            
            statement.setInt(14, research.getId());
            
            result = statement.executeUpdate();
        }catch(SQLException exception){
            throw new DataInsertionException("Error de conexión. Inténtelo de nuevo más tarde");
        }finally{
            dataBaseManager.closeConnection();
        }
        
        return result;
    }

    @Override
    public void validateResearch(ResearchProject researchProject) throws DataInsertionException{
        PreparedStatement statement;
        String query = "UPDATE ResearchProjects SET V°B° = ? WHERE researchProjectId = ?";
        
        try{
            statement = dataBaseManager.getConnection().prepareStatement(query);
            
            statement.setString(1, ResearchProjectStatus.VALIDATED.getValue());
            statement.setInt(2, researchProject.getId());
            statement.executeUpdate();
        }catch(SQLException exception){
            throw new DataInsertionException("Error de conexión. Inténtelo de nuevo más tarde");
        }finally{
            dataBaseManager.closeConnection();
        }
    }

    @Override
    public boolean assertResearch(ResearchProject research){
        return !isBlank(research) && isValidDate(research) && areDirectorsDifferent(research);
    }

    public boolean isBlank(ResearchProject research){
        return research.getTitle().isBlank();
    }

    public boolean isValidDate(ResearchProject research){
        return research.getStartDate().compareTo(research.getDueDate()) <= 0;
    }
    
    public boolean areDirectorsDifferent(ResearchProject research){
        boolean result = true;
        
        if(research.getDirectors().size() >= 2){
            for(int i = 1; i < research.getDirectors().size(); i++){
                result = !research.getDirectors().get(i).equals(research.getDirectors().get(i - 1));
            }
        }
        
        return result;
    }
    
    public boolean areStudentsDifferent(ResearchProject research){
        boolean result = true;
        
        if(research.getStudents().size() >= 2){
            for(int i = 1; i < research.getStudents().size(); i++){
                result = !research.getStudents().get(i).equals(research.getStudents().get(i - 1));
            }
        }
        
        return result;
    }
}