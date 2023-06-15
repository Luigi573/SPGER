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
        String query = "INSERT INTO Anteproyectos(fechaFin, fechaInicio, IdLGAC, descripción, "
                + "resultadosEsperados, requisitos, bibliografíaRecomendada, título, Matrícula, V°B°, "
                + "IdDirector1, IdDirector2, IdDirector3) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?);";
        
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
            
            if(research.getStudent().getMatricle() != null){
                statement.setString(9, research.getStudent().getMatricle());
            }else{
                statement.setNull(9, java.sql.Types.VARCHAR);
            }
            
            statement.setString(10, research.getValidationStatus());
            
            for(int i = 0; i < 3; i++){
                if(i < research.getDirectors().size()){
                    statement.setInt(i + 11, research.getDirectors().get(i).getDirectorId());
                }else{
                    statement.setNull(i + 11, java.sql.Types.INTEGER);
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
        String query = "SELECT DISTINCT a.IdAnteproyecto, a.fechaFin, a.fechaInicio, l.IdLGAC, l.descripción AS LGAC, a.título, a.V°B°, "
                + "a.IdDirector1, up1.nombre, up1.apellidoPaterno, up1.apellidoMaterno, a.IdDirector2, up2.nombre, up2.apellidoPaterno, up2.apellidoMaterno, a.IdDirector3, "
                + "up3.nombre, up3.apellidoPaterno, up3.apellidoMaterno, a.descripción, a.resultadosEsperados, a.requisitos, a.bibliografíaRecomendada, "
                + "a.Matrícula, ue.nombre, ue.apellidoPaterno, ue.apellidoMaterno FROM Anteproyectos a "
                + "LEFT JOIN Directores d1 ON a.IdDirector1 = d1.IdDirector LEFT JOIN Profesores p1 ON d1.NumPersonal = p1.NumPersonal LEFT JOIN Usuarios up1 ON p1.IdUsuario = up1.IdUsuario "
                + "LEFT JOIN Directores d2 ON a.IdDirector2 = d2.IdDirector LEFT JOIN Profesores p2 ON d2.NumPersonal = p2.NumPersonal LEFT JOIN Usuarios up2 ON p2.IdUsuario = up2.IdUsuario "
                + "LEFT JOIN Directores d3 ON a.IdDirector3 = d3.IdDirector LEFT JOIN Profesores p3 ON d3.NumPersonal = p3.NumPersonal LEFT JOIN Usuarios up3 ON p3.IdUsuario = up3.IdUsuario "
                + "LEFT JOIN Estudiantes e ON a.Matrícula = e.Matrícula LEFT JOIN Usuarios ue ON e.IdUsuario = ue.IdUsuario LEFT JOIN LGAC l ON l.IdLGAC = a.IdLGAC ORDER BY fechaFin, fechaInicio, título ASC";
        
        try{
            statement = dataBaseManager.getConnection().prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                ResearchProject research = new ResearchProject();
                
                research.setId(resultSet.getInt("a.IdAnteproyecto"));
                research.setDueDate(resultSet.getDate("a.fechaFin"));
                research.setStartDate(resultSet.getDate("a.fechaInicio"));
                
                //Concatenating column names since they're almost the same
                for(int i = 1; i <= 3; i++){
                    Director director = new Director();
                    director.setDirectorId(resultSet.getInt("a.IdDirector"+ i));
                    
                    if(!resultSet.wasNull()){
                        director.setName(resultSet.getString("up" + i + ".nombre"));
                        director.setFirstSurname(resultSet.getString("up" + i + ".apellidoPaterno"));
                        director.setSecondSurname(resultSet.getString("up" + i + ".apellidoPaterno"));
                        
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
                research.getStudent().setMatricle(resultSet.getString("a.Matrícula"));
                
                if(!resultSet.wasNull()){
                    research.getStudent().setName(resultSet.getString("ue.nombre"));
                    research.getStudent().setFirstSurname(resultSet.getString("ue.apellidoPaterno"));
                    research.getStudent().setSecondSurname(resultSet.getString("ue.apellidoMaterno"));
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
        String query = "SELECT DISTINCT a.IdAnteproyecto, a.título, a.Matrícula, u.nombre, u.apellidoPaterno, u.apellidoMaterno FROM Anteproyectos a " +
            " LEFT JOIN Directores d1 ON a.IdDirector1 = d1.IdDirector LEFT JOIN Profesores p1 ON d1.NumPersonal = p1.NumPersonal " +
            " LEFT JOIN Directores d2 ON a.IdDirector2 = d2.IdDirector  LEFT JOIN Profesores p2 ON d2.NumPersonal = p2.NumPersonal " +
            " LEFT JOIN Directores d3 ON a.IdDirector3 = d3.IdDirector LEFT JOIN Profesores p3 ON d3.NumPersonal = p3.NumPersonal " +
            " LEFT JOIN Estudiantes e ON a.Matrícula = e.Matrícula LEFT JOIN Usuarios u ON e.IdUsuario = u.IdUsuario " +
            " WHERE (p1.NumPersonal = ? OR p2.NumPersonal = ? OR p3.NumPersonal = ?) AND a.Matrícula IS NOT NULL";
        
        try{
            statement = dataBaseManager.getConnection().prepareStatement(query);
            statement.setInt(1, staffNumber);
            statement.setInt(2, staffNumber);
            statement.setInt(3, staffNumber);
            
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                ResearchProject research = new ResearchProject();
                
                research.setId(resultSet.getInt("a.IdAnteproyecto"));
                research.setTitle(resultSet.getString("a.título"));
                
                Student student = new Student();
                research.setStudent(student);
                research.getStudent().setMatricle(resultSet.getString("a.Matrícula"));
                research.getStudent().setName(resultSet.getString("u.nombre"));
                research.getStudent().setFirstSurname(resultSet.getString("u.apellidoPaterno"));
                research.getStudent().setSecondSurname(resultSet.getString("u.apellidoMaterno"));
                
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
        String query = "SELECT IdAnteproyecto, título FROM Anteproyectos WHERE Matrícula = ?";
        
        try{
            statement = dataBaseManager.getConnection().prepareStatement(query);
            statement.setString(1, matricle);
            
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                research.setId(resultSet.getInt("IdAnteproyecto"));
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
        String query = "SELECT DISTINCT a.IdAnteproyecto, a.título, a.Matrícula, u.nombre, u.apellidoPaterno, u.apellidoMaterno FROM Anteproyectos a "
                + " LEFT JOIN Directores d1 ON a.IdDirector1 = d1.IdDirector LEFT JOIN Profesores p1 ON d1.NumPersonal = p1.NumPersonal LEFT JOIN Cursos c1 ON p1.NumPersonal = c1.NumPersonal "
                + " LEFT JOIN Directores d2 ON a.IdDirector2 = d2.IdDirector LEFT JOIN Profesores p2 ON d2.NumPersonal = p2.NumPersonal LEFT JOIN Cursos c2 ON p2.NumPersonal = c2.NumPersonal "
                + " LEFT JOIN Directores d3 ON a.IdDirector3 = d3.IdDirector LEFT JOIN Profesores p3 ON d3.NumPersonal = p3.NumPersonal LEFT JOIN Cursos c3 ON p3.NumPersonal = c3.NumPersonal "
                + " LEFT JOIN Estudiantes e ON a.Matrícula = e.Matrícula LEFT JOIN Usuarios u ON e.IdUsuario = u.IdUsuario"
                + " WHERE (c1.NRC = ? OR c2.NRC = ? OR c3.NRC = ?)";
        
        try{
            statement = dataBaseManager.getConnection().prepareStatement(query);
            statement.setInt(1, NRC);
            statement.setInt(2, NRC);
            statement.setInt(3, NRC);
            
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                ResearchProject research = new ResearchProject();
                
                research.setId(resultSet.getInt("a.IdAnteproyecto"));
                research.setTitle(resultSet.getString("a.título"));
                
                Student student = new Student();
                student.setMatricle(resultSet.getString("a.Matrícula"));
                
                if(!resultSet.wasNull()){
                    student.setMatricle(resultSet.getString("a.Matrícula"));
                    student.setName(resultSet.getString("u.nombre"));
                    student.setFirstSurname(resultSet.getString("u.apellidoPaterno"));
                    student.setSecondSurname(resultSet.getString("u.apellidoMaterno"));
                    
                    research.setStudent(student);
                }
                
                
                researchList.add(research);
            }
            
        }catch(SQLException exception){
            throw new DataRetrievalException("No se pudo establecer conexión con la base de datos, inténtelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }
        
        return researchList;
    }
    
    public ArrayList<ResearchProject> getSpecifiedResearchProjectList(String researchName) throws DataRetrievalException{
        ArrayList<ResearchProject> researchProjectList = new ArrayList<>();
        PreparedStatement statement;
        String query = "SELECT DISTINCT a.IdAnteproyecto, a.fechaFin, a.fechaInicio, l.IdLGAC, l.descripción AS LGAC, a.título, a.V°B°, "
                + " a.IdDirector1, up1.nombre, up1.apellidoPaterno, up1.apellidoMaterno, a.IdDirector2, up2.nombre, up2.apellidoPaterno, up2.apellidoMaterno, a.IdDirector3, "
                + " up3.nombre, up3.apellidoPaterno, up3.apellidoMaterno, a.descripción, a.resultadosEsperados, a.requisitos, a.bibliografíaRecomendada, "
                + " a.Matrícula, ue.nombre, ue.apellidoPaterno, ue.apellidoMaterno FROM Anteproyectos a "
                + " LEFT JOIN Directores d1 ON a.IdDirector1 = d1.IdDirector LEFT JOIN Profesores p1 ON d1.NumPersonal = p1.NumPersonal LEFT JOIN Usuarios up1 ON p1.IdUsuario = up1.IdUsuario "
                + " LEFT JOIN Directores d2 ON a.IdDirector2 = d2.IdDirector LEFT JOIN Profesores p2 ON d2.NumPersonal = p2.NumPersonal LEFT JOIN Usuarios up2 ON p2.IdUsuario = up2.IdUsuario "
                + " LEFT JOIN Directores d3 ON a.IdDirector3 = d3.IdDirector LEFT JOIN Profesores p3 ON d3.NumPersonal = p3.NumPersonal LEFT JOIN Usuarios up3 ON p3.IdUsuario = up3.IdUsuario "
                + " LEFT JOIN Estudiantes e ON a.Matrícula = e.Matrícula LEFT JOIN Usuarios ue ON e.IdUsuario = ue.IdUsuario LEFT JOIN LGAC l ON l.IdLGAC = a.IdLGAC "
                + " WHERE a.título LIKE ? ORDER BY fechaFin, fechaInicio, título ASC";
        
        try{
            statement = dataBaseManager.getConnection().prepareStatement(query);
            statement.setString(1, researchName + '%');
            ResultSet resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                ResearchProject research = new ResearchProject();
                
                research.setId(resultSet.getInt("a.IdAnteproyecto"));
                research.setDueDate(resultSet.getDate("a.fechaFin"));
                research.setStartDate(resultSet.getDate("a.fechaInicio"));
                
                //Concatenating column names since they're almost the same
                for(int i = 1; i <= 3; i++){
                    Director director = new Director();
                    director.setDirectorId(resultSet.getInt("a.IdDirector"+ i));
                    
                    if(!resultSet.wasNull()){
                        director.setName(resultSet.getString("up" + i + ".nombre"));
                        director.setFirstSurname(resultSet.getString("up" + i + ".apellidoPaterno"));
                        director.setSecondSurname(resultSet.getString("up" + i + ".apellidoPaterno"));
                        
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
                research.getStudent().setMatricle(resultSet.getString("a.Matrícula"));
                
                if(!resultSet.wasNull()){
                    research.getStudent().setName(resultSet.getString("ue.nombre"));
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
        
        String query = "SELECT DISTINCT a.IdAnteproyecto, a.fechaFin, a.fechaInicio, l.IdLGAC, l.descripción AS LGAC, a.título, a.V°B°, "
                + " a.IdDirector1, up1.nombre, up1.apellidoPaterno, up1.apellidoMaterno, a.IdDirector2, up2.nombre, up2.apellidoPaterno, up2.apellidoMaterno, a.IdDirector3, "
                + " up3.nombre, up3.apellidoPaterno, up3.apellidoMaterno, a.descripción, a.resultadosEsperados, a.requisitos, a.bibliografíaRecomendada, "
                + " a.Matrícula, ue.nombre, ue.apellidoPaterno, ue.apellidoMaterno FROM Anteproyectos a "
                + " LEFT JOIN Directores d1 ON a.IdDirector1 = d1.IdDirector LEFT JOIN Profesores p1 ON d1.NumPersonal = p1.NumPersonal LEFT JOIN Usuarios up1 ON p1.IdUsuario = up1.IdUsuario "
                + " LEFT JOIN Directores d2 ON a.IdDirector2 = d2.IdDirector LEFT JOIN Profesores p2 ON d2.NumPersonal = p2.NumPersonal LEFT JOIN Usuarios up2 ON p2.IdUsuario = up2.IdUsuario "
                + " LEFT JOIN Directores d3 ON a.IdDirector3 = d3.IdDirector LEFT JOIN Profesores p3 ON d3.NumPersonal = p3.NumPersonal LEFT JOIN Usuarios up3 ON p3.IdUsuario = up3.IdUsuario "
                + " LEFT JOIN Estudiantes e ON a.Matrícula = e.Matrícula LEFT JOIN Usuarios ue ON e.IdUsuario = ue.IdUsuario LEFT JOIN LGAC l ON l.IdLGAC = a.IdLGAC  "
                + "WHERE a.título LIKE ? && a.V°B° = 'Validado' ORDER BY fechaFin, fechaInicio, título ASC";
        
        try{
            statement = dataBaseManager.getConnection().prepareStatement(query);
            statement.setString(1, researchName + '%');
            ResultSet resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                ResearchProject research = new ResearchProject();
                
                research.setId(resultSet.getInt("a.IdAnteproyecto"));
                research.setDueDate(resultSet.getDate("a.fechaFin"));
                research.setStartDate(resultSet.getDate("a.fechaInicio"));
                
                //Concatenating column names since they're almost the same
                for(int i = 1; i <= 3; i++){
                    Director director = new Director();
                    director.setDirectorId(resultSet.getInt("a.IdDirector"+ i));
                    
                    if(!resultSet.wasNull()){
                        director.setName(resultSet.getString("up" + i + ".nombre"));
                        director.setFirstSurname(resultSet.getString("up" + i + ".apellidoPaterno"));
                        director.setSecondSurname(resultSet.getString("up" + i + ".apellidoPaterno"));
                        
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
                research.getStudent().setMatricle(resultSet.getString("a.Matrícula"));
                
                if(!resultSet.wasNull()){
                    research.getStudent().setName(resultSet.getString("ue.nombre"));
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
        
        String query = "SELECT DISTINCT a.IdAnteproyecto, a.fechaFin, a.fechaInicio, l.IdLGAC, l.descripción AS LGAC, a.título, a.V°B°, "
                + " a.IdDirector1, up1.nombre, up1.apellidoPaterno, up1.apellidoMaterno, a.IdDirector2, up2.nombre, up2.apellidoPaterno, up2.apellidoMaterno, a.IdDirector3, "
                + " up3.nombre, up3.apellidoPaterno, up3.apellidoMaterno, a.descripción, a.resultadosEsperados, a.requisitos, a.bibliografíaRecomendada, "
                + " a.Matrícula, ue.nombre, ue.apellidoPaterno, ue.apellidoMaterno FROM Anteproyectos a "
                + " LEFT JOIN Directores d1 ON a.IdDirector1 = d1.IdDirector LEFT JOIN Profesores p1 ON d1.NumPersonal = p1.NumPersonal LEFT JOIN Usuarios up1 ON p1.IdUsuario = up1.IdUsuario "
                + " LEFT JOIN Directores d2 ON a.IdDirector2 = d2.IdDirector LEFT JOIN Profesores p2 ON d2.NumPersonal = p2.NumPersonal LEFT JOIN Usuarios up2 ON p2.IdUsuario = up2.IdUsuario "
                + " LEFT JOIN Directores d3 ON a.IdDirector3 = d3.IdDirector LEFT JOIN Profesores p3 ON d3.NumPersonal = p3.NumPersonal LEFT JOIN Usuarios up3 ON p3.IdUsuario = up3.IdUsuario "
                + " LEFT JOIN Estudiantes e ON a.Matrícula = e.Matrícula LEFT JOIN Usuarios ue ON e.IdUsuario = ue.IdUsuario LEFT JOIN LGAC l ON l.IdLGAC = a.IdLGAC  "
                + "WHERE a.título LIKE ? && a.V°B° = 'Propuesto' ORDER BY fechaFin, fechaInicio, título ASC";
        
        try{
            statement = dataBaseManager.getConnection().prepareStatement(query);
            statement.setString(1, researchName + '%');
            ResultSet resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                ResearchProject research = new ResearchProject();
                
                research.setId(resultSet.getInt("a.IdAnteproyecto"));
                research.setDueDate(resultSet.getDate("a.fechaFin"));
                research.setStartDate(resultSet.getDate("a.fechaInicio"));
                
                //Concatenating column names since they're almost the same
                for(int i = 1; i <= 3; i++){
                    Director director = new Director();
                    director.setDirectorId(resultSet.getInt("a.IdDirector"+ i));
                    
                    if(!resultSet.wasNull()){
                        director.setName(resultSet.getString("up" + i + ".nombre"));
                        director.setFirstSurname(resultSet.getString("up" + i + ".apellidoPaterno"));
                        director.setSecondSurname(resultSet.getString("up" + i + ".apellidoPaterno"));
                        
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
                research.getStudent().setMatricle(resultSet.getString("a.Matrícula"));
                
                if(!resultSet.wasNull()){
                    research.getStudent().setName(resultSet.getString("ue.nombre"));
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
        
        String query = "SELECT a.IdAnteproyecto, a.fechaFin, a.fechaInicio, l.IdLGAC, l.descripción AS LGAC, a.título, a.V°B°, "
                + " a.IdDirector1, up1.nombre, up1.apellidoPaterno, up1.apellidoMaterno, a.IdDirector2, up2.nombre, up2.apellidoPaterno, up2.apellidoMaterno, a.IdDirector3, "
                + " up3.nombre, up3.apellidoPaterno, up3.apellidoMaterno, a.descripción, a.resultadosEsperados, a.requisitos, a.bibliografíaRecomendada, "
                + " a.Matrícula, ue.nombre, ue.apellidoPaterno, ue.apellidoMaterno FROM Anteproyectos a "
                + " LEFT JOIN Directores d1 ON a.IdDirector1 = d1.IdDirector LEFT JOIN Profesores p1 ON d1.NumPersonal = p1.NumPersonal LEFT JOIN Usuarios up1 ON p1.IdUsuario = up1.IdUsuario "
                + " LEFT JOIN Directores d2 ON a.IdDirector2 = d2.IdDirector LEFT JOIN Profesores p2 ON d2.NumPersonal = p2.NumPersonal LEFT JOIN Usuarios up2 ON p2.IdUsuario = up2.IdUsuario "
                + " LEFT JOIN Directores d3 ON a.IdDirector3 = d3.IdDirector LEFT JOIN Profesores p3 ON d3.NumPersonal = p3.NumPersonal LEFT JOIN Usuarios up3 ON p3.IdUsuario = up3.IdUsuario "
                + " LEFT JOIN Estudiantes e ON a.Matrícula = e.Matrícula LEFT JOIN Usuarios ue ON e.IdUsuario = ue.IdUsuario LEFT JOIN LGAC l ON l.IdLGAC = a.IdLGAC "
                + "WHERE a.título LIKE ? && (a.V°B° = 'Validado' || a.V°B° = 'Propuesto') ORDER BY fechaFin, fechaInicio, título ASC";
        
        try{
            statement = dataBaseManager.getConnection().prepareStatement(query);
            statement.setString(1, researchName + '%');
            ResultSet resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                ResearchProject research = new ResearchProject();
                
                research.setId(resultSet.getInt("a.IdAnteproyecto"));
                research.setDueDate(resultSet.getDate("a.fechaFin"));
                research.setStartDate(resultSet.getDate("a.fechaInicio"));
                
                //Concatenating column names since they're almost the same
                for(int i = 1; i <= 3; i++){
                    Director director = new Director();
                    director.setDirectorId(resultSet.getInt("a.IdDirector"+ i));
                    
                    if(!resultSet.wasNull()){
                        director.setName(resultSet.getString("up" + i + ".nombre"));
                        director.setFirstSurname(resultSet.getString("up" + i + ".apellidoPaterno"));
                        director.setSecondSurname(resultSet.getString("up" + i + ".apellidoPaterno"));
                        
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
                research.getStudent().setMatricle(resultSet.getString("a.Matrícula"));
                
                if(!resultSet.wasNull()){
                    research.getStudent().setName(resultSet.getString("ue.nombre"));
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
        String query = "UPDATE Anteproyectos SET fechaFin = ?, fechaInicio = ?, IdLGAC = ?, descripción = ?, resultadosEsperados = ?, "
                + " requisitos = ?, bibliografíaRecomendada = ?, título = ?, Matrícula = ?, IdDirector1 = ?, IdDirector2 = ?, IdDirector3 = ? "
                + " WHERE IdAnteproyecto = ?";
        
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
            
            if(research.getStudent().getMatricle() != null){
                statement.setString(9, research.getStudent().getMatricle());
            }else{
                statement.setNull(9, java.sql.Types.VARCHAR);
            }
            
            
            
            for(int i = 0; i < 3; i++){
                if(i < research.getDirectors().size()){
                    statement.setInt(i + 10, research.getDirectors().get(i).getDirectorId());
                }else{
                    statement.setNull(i + 10, java.sql.Types.INTEGER);
                }
            }
            
            statement.setInt(13, research.getId());
            
            result = statement.executeUpdate();
        }catch(SQLException exception){
              ;
            throw new DataInsertionException("Error de conexión. Inténtelo de nuevo más tarde");
        }finally{
            dataBaseManager.closeConnection();
        }
        
        return result;
    }

    @Override
    public void validateResearch(ResearchProject researchProject) throws DataInsertionException{
        PreparedStatement statement;
        String query = "UPDATE Anteproyectos SET V°B° = ? WHERE IdAnteproyecto = ?";
        
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
        
        if(research.getDirectors().size() > 2){
            for(int i = 1; i < research.getDirectors().size(); i++){
                result = research.getDirectors().get(i).equals(research.getDirectors().get(i - 1));
            }
        }
        
        return result;
    }
}