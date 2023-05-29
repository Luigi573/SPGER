package mx.uv.fei.logic.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.logic.daosinterfaces.IResearchDAO;
import mx.uv.fei.logic.domain.ResearchProject;
import mx.uv.fei.logic.domain.statuses.ResearchProjectStatus;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.exceptions.DataInsertionException;

public class ResearchDAO implements IResearchDAO{
    private final DataBaseManager dataBaseManager;
    
    public ResearchDAO(){
        dataBaseManager = new DataBaseManager();
    }
    @Override
<<<<<<< HEAD
    public int addResearch(ResearchProject research) throws DataInsertionException {
        int generatedId = 0;
=======
    public int addResearch(ResearchProject research) throws DataWritingException{
        int result = 0;
>>>>>>> b76b93d15655b9f5dfd27c9fc867dc5e2c09b660
        PreparedStatement statement;
        String query = "INSERT INTO Anteproyectos(fechaFin, fechaInicio, IdLGAC, descripción, "
                + "resultadosEsperados, requisitos, bibliografíaRecomendada, título, Matrícula, "
                + "IdDirector1, IdDirector2, IdDirector3, V°B°) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?);";
        
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
            
            if(research.getStudent().getMatricule() != null){
                statement.setString(9, research.getStudent().getMatricule());
            }else{
                statement.setNull(9, java.sql.Types.VARCHAR);
            }
            
            for(int i = 0; i < 3; i++){
                if(research.getDirector(i).getDirectorId() != 0){
                    statement.setInt(i + 10, research.getDirector(i).getDirectorId());
                }else{
                    statement.setNull(i + 10, java.sql.Types.INTEGER);
                }
            }

            statement.setString(13, research.getValidationStatus());
            
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            
            if(generatedKeys.next()){
                generatedId = generatedKeys.getInt(1);
            }
        }catch(SQLException exception){
<<<<<<< HEAD
            throw new DataInsertionException("Error de conexión. Verifique su conexion e intentelo de nuevo");
=======
            exception.printStackTrace();
            throw new DataWritingException("Error de conexión. Verifique su conexion e intentelo de nuevo");
>>>>>>> b76b93d15655b9f5dfd27c9fc867dc5e2c09b660
        }finally{
            dataBaseManager.closeConnection();
        }
        
        return generatedId;
    }

    @Override
    public ArrayList<ResearchProject> getResearchProjectList() throws DataRetrievalException{
        ArrayList<ResearchProject> researchProjectList = new ArrayList<>();
        PreparedStatement statement;
        
        String query = "SELECT a.IdAnteproyecto, a.fechaFin, a.fechaInicio, a.IdDirector1, CONCAT(up1.nombre, ' ', up1.apellidoPaterno, ' ', up1.apellidoMaterno) AS nombreDirector1, "
                + "a.IdDirector2, CONCAT(up2.nombre, ' ', up2.apellidoPaterno, ' ', up2.apellidoMaterno) AS nombreDirector2, "
                + "a.IdDirector3, CONCAT(up3.nombre, ' ', up3.apellidoPaterno, ' ', up3.apellidoMaterno) AS nombreDirector3, "
                + "l.IdLGAC, l.descripción AS LGAC, a.descripción, a.resultadosEsperados, a.requisitos, a.bibliografíaRecomendada, "
                + "a.título, a.Matrícula, a.V°B°, CONCAT(ue.nombre, ' ', ue.apellidoPaterno, ' ', ue.apellidoMaterno) AS estudianteAsignado "
                + "FROM Anteproyectos a LEFT JOIN Directores d ON a.IdDirector1 = d.IdDirector LEFT JOIN Profesores p ON d.NumPersonal = p.NumPersonal "
                + "LEFT JOIN Usuarios up1 ON p.IdUsuario = up1.IdUsuario LEFT JOIN Usuarios up2 ON p.IdUsuario = up2.IdUsuario "
                + "LEFT JOIN Usuarios up3 ON p.IdUsuario = up3.IdUsuario LEFT JOIN Estudiantes e ON a.Matrícula = e.Matrícula "
                + "LEFT JOIN Usuarios ue ON e.IdUsuario = ue.IdUsuario LEFT JOIN LGAC l ON l.IdLGAC = a.IdLGAC ORDER BY fechaFin, fechaInicio, título ASC";
        
        try{
            statement = dataBaseManager.getConnection().prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                ResearchProject research = new ResearchProject();
                
                research.setId(resultSet.getInt("a.IdAnteproyecto"));
                research.setDueDate(resultSet.getDate("a.fechaFin"));
                research.setStartDate(resultSet.getDate("a.fechaInicio"));
                
                //Concatenating column names since they're almost the same
                for(int i = 0; i < 3; i++){
                    research.getDirector(i).setDirectorId(resultSet.getInt("a.IdDirector"+ (i + 1)));
                
                    if(!resultSet.wasNull()){
                        research.getDirector(i).setName(resultSet.getString("nombreDirector" + (i + 1)));
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
                    research.getStudent().setName(resultSet.getString("estudianteAsignado"));
                }
                
                researchProjectList.add(research);
            }
        }catch(SQLException exception){
            exception.printStackTrace();
            throw new DataRetrievalException("Fallo al recuperar la informacion. Verifique su conexion e intentelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }
        
        return researchProjectList;
    }

    @Override
<<<<<<< HEAD
    public int modifyResearch(ResearchProject research) throws DataInsertionException {
=======
    public ArrayList<ResearchProject> getSpecifiedResearchProjectList(String researchName) throws DataRetrievalException{
        ArrayList<ResearchProject> researchProjectList = new ArrayList<>();
        PreparedStatement statement;
        
        String query = "SELECT a.IdAnteproyecto, a.fechaFin, a.fechaInicio, a.IdDirector1, CONCAT(up1.nombre, ' ', up1.apellidoPaterno, ' ', up1.apellidoMaterno) AS nombreDirector1, "
                + "a.IdDirector2, CONCAT(up2.nombre, ' ', up2.apellidoPaterno, ' ', up2.apellidoMaterno) AS nombreDirector2, "
                + "a.IdDirector3, CONCAT(up3.nombre, ' ', up3.apellidoPaterno, ' ', up3.apellidoMaterno) AS nombreDirector3, "
                + "l.IdLGAC, l.descripción AS LGAC, a.descripción, a.resultadosEsperados, a.requisitos, a.bibliografíaRecomendada, "
                + "a.título, a.Matrícula, a.V°B°, CONCAT(ue.nombre, ' ', ue.apellidoPaterno, ' ', ue.apellidoMaterno) AS estudianteAsignado "
                + "FROM Anteproyectos a LEFT JOIN Directores d ON a.IdDirector1 = d.IdDirector LEFT JOIN Profesores p ON d.NumPersonal = p.NumPersonal "
                + "LEFT JOIN Usuarios up1 ON p.IdUsuario = up1.IdUsuario LEFT JOIN Usuarios up2 ON p.IdUsuario = up2.IdUsuario "
                + "LEFT JOIN Usuarios up3 ON p.IdUsuario = up3.IdUsuario LEFT JOIN Estudiantes e ON a.Matrícula = e.Matrícula "
                + "LEFT JOIN Usuarios ue ON e.IdUsuario = ue.IdUsuario LEFT JOIN LGAC l ON l.IdLGAC = a.IdLGAC WHERE a.título LIKE ? ORDER BY fechaFin, fechaInicio, título ASC";
        
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
                for(int i = 0; i < 3; i++){
                    research.getDirector(i).setDirectorId(resultSet.getInt("a.IdDirector"+ (i + 1)));
                
                    if(!resultSet.wasNull()){
                        research.getDirector(i).setName(resultSet.getString("nombreDirector" + (i + 1)));
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
                    research.getStudent().setName(resultSet.getString("estudianteAsignado"));
                }
                
                researchProjectList.add(research);
            }
        }catch(SQLException exception){
            exception.printStackTrace();
            throw new DataRetrievalException("Fallo al recuperar la informacion. Verifique su conexion e intentelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }
        
        return researchProjectList;
    }

    @Override
    public ArrayList<ResearchProject> getSpecifiedValidatedResearchProjectList(String researchName) throws DataRetrievalException{
        ArrayList<ResearchProject> researchProjectList = new ArrayList<>();
        PreparedStatement statement;
        
        String query = "SELECT a.IdAnteproyecto, a.fechaFin, a.fechaInicio, a.IdDirector1, CONCAT(up1.nombre, ' ', up1.apellidoPaterno, ' ', up1.apellidoMaterno) AS nombreDirector1, "
                + "a.IdDirector2, CONCAT(up2.nombre, ' ', up2.apellidoPaterno, ' ', up2.apellidoMaterno) AS nombreDirector2, "
                + "a.IdDirector3, CONCAT(up3.nombre, ' ', up3.apellidoPaterno, ' ', up3.apellidoMaterno) AS nombreDirector3, "
                + "l.IdLGAC, l.descripción AS LGAC, a.descripción, a.resultadosEsperados, a.requisitos, a.bibliografíaRecomendada, "
                + "a.título, a.Matrícula, a.V°B°, CONCAT(ue.nombre, ' ', ue.apellidoPaterno, ' ', ue.apellidoMaterno) AS estudianteAsignado "
                + "FROM Anteproyectos a LEFT JOIN Directores d ON a.IdDirector1 = d.IdDirector LEFT JOIN Profesores p ON d.NumPersonal = p.NumPersonal "
                + "LEFT JOIN Usuarios up1 ON p.IdUsuario = up1.IdUsuario LEFT JOIN Usuarios up2 ON p.IdUsuario = up2.IdUsuario "
                + "LEFT JOIN Usuarios up3 ON p.IdUsuario = up3.IdUsuario LEFT JOIN Estudiantes e ON a.Matrícula = e.Matrícula "
                + "LEFT JOIN Usuarios ue ON e.IdUsuario = ue.IdUsuario LEFT JOIN LGAC l ON l.IdLGAC = a.IdLGAC WHERE a.título LIKE ? && a.V°B° = 'Validado' "
                + "ORDER BY fechaFin, fechaInicio, título ASC";
        
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
                for(int i = 0; i < 3; i++){
                    research.getDirector(i).setDirectorId(resultSet.getInt("a.IdDirector"+ (i + 1)));
                
                    if(!resultSet.wasNull()){
                        research.getDirector(i).setName(resultSet.getString("nombreDirector" + (i + 1)));
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
                    research.getStudent().setName(resultSet.getString("estudianteAsignado"));
                }
                
                researchProjectList.add(research);
            }
        }catch(SQLException exception){
            exception.printStackTrace();
            throw new DataRetrievalException("Fallo al recuperar la informacion. Verifique su conexion e intentelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }
        
        return researchProjectList;
    }

    @Override
    public ArrayList<ResearchProject> getSpecifiedNotValidatedResearchProjectList(String researchName) throws DataRetrievalException{
        ArrayList<ResearchProject> researchProjectList = new ArrayList<>();
        PreparedStatement statement;
        
        String query = "SELECT a.IdAnteproyecto, a.fechaFin, a.fechaInicio, a.IdDirector1, CONCAT(up1.nombre, ' ', up1.apellidoPaterno, ' ', up1.apellidoMaterno) AS nombreDirector1, "
                + "a.IdDirector2, CONCAT(up2.nombre, ' ', up2.apellidoPaterno, ' ', up2.apellidoMaterno) AS nombreDirector2, "
                + "a.IdDirector3, CONCAT(up3.nombre, ' ', up3.apellidoPaterno, ' ', up3.apellidoMaterno) AS nombreDirector3, "
                + "l.IdLGAC, l.descripción AS LGAC, a.descripción, a.resultadosEsperados, a.requisitos, a.bibliografíaRecomendada, "
                + "a.título, a.Matrícula, a.V°B°, CONCAT(ue.nombre, ' ', ue.apellidoPaterno, ' ', ue.apellidoMaterno) AS estudianteAsignado "
                + "FROM Anteproyectos a LEFT JOIN Directores d ON a.IdDirector1 = d.IdDirector LEFT JOIN Profesores p ON d.NumPersonal = p.NumPersonal "
                + "LEFT JOIN Usuarios up1 ON p.IdUsuario = up1.IdUsuario LEFT JOIN Usuarios up2 ON p.IdUsuario = up2.IdUsuario "
                + "LEFT JOIN Usuarios up3 ON p.IdUsuario = up3.IdUsuario LEFT JOIN Estudiantes e ON a.Matrícula = e.Matrícula "
                + "LEFT JOIN Usuarios ue ON e.IdUsuario = ue.IdUsuario LEFT JOIN LGAC l ON l.IdLGAC = a.IdLGAC WHERE a.título LIKE ? && a.V°B° = 'Propuesto' "
                + "ORDER BY fechaFin, fechaInicio, título ASC";
        
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
                for(int i = 0; i < 3; i++){
                    research.getDirector(i).setDirectorId(resultSet.getInt("a.IdDirector"+ (i + 1)));
                
                    if(!resultSet.wasNull()){
                        research.getDirector(i).setName(resultSet.getString("nombreDirector" + (i + 1)));
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
                    research.getStudent().setName(resultSet.getString("estudianteAsignado"));
                }
                
                researchProjectList.add(research);
            }
        }catch(SQLException exception){
            exception.printStackTrace();
            throw new DataRetrievalException("Fallo al recuperar la informacion. Verifique su conexion e intentelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }
        
        return researchProjectList;
    }

    @Override
    public ArrayList<ResearchProject> getSpecifiedValidatedAndNotValidatedResearchProjectList(String researchName) throws DataRetrievalException{
        ArrayList<ResearchProject> researchProjectList = new ArrayList<>();
        PreparedStatement statement;
        
        String query = "SELECT a.IdAnteproyecto, a.fechaFin, a.fechaInicio, a.IdDirector1, CONCAT(up1.nombre, ' ', up1.apellidoPaterno, ' ', up1.apellidoMaterno) AS nombreDirector1, "
                + "a.IdDirector2, CONCAT(up2.nombre, ' ', up2.apellidoPaterno, ' ', up2.apellidoMaterno) AS nombreDirector2, "
                + "a.IdDirector3, CONCAT(up3.nombre, ' ', up3.apellidoPaterno, ' ', up3.apellidoMaterno) AS nombreDirector3, "
                + "l.IdLGAC, l.descripción AS LGAC, a.descripción, a.resultadosEsperados, a.requisitos, a.bibliografíaRecomendada, "
                + "a.título, a.Matrícula, a.V°B°, CONCAT(ue.nombre, ' ', ue.apellidoPaterno, ' ', ue.apellidoMaterno) AS estudianteAsignado "
                + "FROM Anteproyectos a LEFT JOIN Directores d ON a.IdDirector1 = d.IdDirector LEFT JOIN Profesores p ON d.NumPersonal = p.NumPersonal "
                + "LEFT JOIN Usuarios up1 ON p.IdUsuario = up1.IdUsuario LEFT JOIN Usuarios up2 ON p.IdUsuario = up2.IdUsuario "
                + "LEFT JOIN Usuarios up3 ON p.IdUsuario = up3.IdUsuario LEFT JOIN Estudiantes e ON a.Matrícula = e.Matrícula "
                + "LEFT JOIN Usuarios ue ON e.IdUsuario = ue.IdUsuario LEFT JOIN LGAC l ON l.IdLGAC = a.IdLGAC WHERE a.título LIKE ? && (a.V°B° = 'Validado' || a.V°B° = 'Propuesto') "
                + "ORDER BY fechaFin, fechaInicio, título ASC";
        
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
                for(int i = 0; i < 3; i++){
                    research.getDirector(i).setDirectorId(resultSet.getInt("a.IdDirector"+ (i + 1)));
                
                    if(!resultSet.wasNull()){
                        research.getDirector(i).setName(resultSet.getString("nombreDirector" + (i + 1)));
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
                    research.getStudent().setName(resultSet.getString("estudianteAsignado"));
                }
                
                researchProjectList.add(research);
            }
        }catch(SQLException exception){
            exception.printStackTrace();
            throw new DataRetrievalException("Fallo al recuperar la informacion. Verifique su conexion e intentelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }
        
        return researchProjectList;
    }

    @Override
    public int modifyResearch(ResearchProject research) throws DataWritingException{
>>>>>>> b76b93d15655b9f5dfd27c9fc867dc5e2c09b660
        int result = 0;
        PreparedStatement statement;
        String query = "UPDATE Anteproyectos SET fechaFin = ?, fechaInicio = ?, IdLGAC = ?, descripción = ?, "
                + "resultadosEsperados = ?, requisitos = ?, bibliografíaRecomendada = ?, título = ?, Matrícula = ?, "
                + "IdDirector1 = ?, IdDirector2 = ?, IdDirector3 = ? WHERE IdAnteproyecto = ?";
        
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
            
            if(research.getStudent().getMatricule() != null){
                statement.setString(9, research.getStudent().getMatricule());
            }else{
                statement.setNull(9, java.sql.Types.VARCHAR);
            }
            
            for(int i = 0; i < 3; i++){
                if(research.getDirector(i).getDirectorId() != 0){
                    statement.setInt(i + 10, research.getDirector(i).getDirectorId());
                }else{
                    statement.setNull(i + 10, java.sql.Types.INTEGER);
                }
            }
            
            statement.setInt(13, research.getId());
            
            result = statement.executeUpdate();
        }catch(SQLException exception){
            throw new DataInsertionException("Error de conexión. Verifique su conexion e intentelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }
        
        return result;
    }

    @Override
    public void validateResearch(ResearchProject researchProject) throws DataWritingException{
        PreparedStatement statement;
        String query = "UPDATE Anteproyectos SET V°B° = ? WHERE IdAnteproyecto = ?";
        
        try{
            statement = dataBaseManager.getConnection().prepareStatement(query);
            
            statement.setString(1, ResearchProjectStatus.VALIDATED.getValue());
            statement.setInt(2, researchProject.getId());
            statement.executeUpdate();
        }catch(SQLException exception){
            exception.printStackTrace();
            throw new DataWritingException("Error de conexión. Verifique su conexion e intentelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }
    }

    @Override
    public boolean assertResearch(ResearchProject research){
        return !isBlank(research) && isValidDate(research);
    }

    public boolean isBlank(ResearchProject research){
        return research.getTitle().isBlank();
    }

    public boolean isValidDate(ResearchProject research){
        return research.getStartDate().compareTo(research.getDueDate()) <= 0;
    }

}