package mx.uv.fei.logic.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.logic.daosinterfaces.IResearchProjectDAO;
import mx.uv.fei.logic.domain.ResearchProject;
import mx.uv.fei.logic.domain.statuses.ResearchProjectStatus;
import mx.uv.fei.logic.exceptions.DataInsertionException;
import mx.uv.fei.logic.exceptions.DataRetrievalException;

public class ResearchProjectDAO implements IResearchProjectDAO {
    private final DataBaseManager dataBaseManager;

    public ResearchProjectDAO() {
        dataBaseManager = new DataBaseManager();
    }

    @Override
    public int addResearchProject(ResearchProject researchProject) throws DataInsertionException {
        int generatedId = 0;
        PreparedStatement statement;
        String query = "INSERT INTO ResearchProjects(dueDate, startDate, kgalId, description, "
                + "expectedResults, requirements, suggestedBibliography, title, studentMatricle1, studentMatricle2, validationStatus, "
                + "directorStaffNumber1, directorStaffNumber2, directorStaffNumber3) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

        try {
            statement = dataBaseManager.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            statement.setDate(1, researchProject.getDueDate());
            statement.setDate(2, researchProject.getStartDate());
            statement.setInt(3, researchProject.getKgalId());
            statement.setString(4, researchProject.getDescription());
            statement.setString(5, researchProject.getExpectedResults());
            statement.setString(6, researchProject.getRequirements());
            statement.setString(7, researchProject.getSuggestedBibliography());
            statement.setString(8, researchProject.getTitle());
            statement.setString(9, researchProject.getStudentMatricle1());
            statement.setString(10, researchProject.getStudentMatricle2());
            statement.setString(11, researchProject.getValidationStatus());
            statement.setInt(12, researchProject.getDirectorStaffNumber1());
            statement.setInt(13, researchProject.getDirectorStaffNumber2());
            statement.setInt(14, researchProject.getDirectorStaffNumber3());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                generatedId = generatedKeys.getInt(1);
            }

        } catch (SQLException exception) {
            throw new DataInsertionException("Error de conexión. Inténtelo de nuevo más tarde");
        } finally {
            dataBaseManager.closeConnection();
        }

        return generatedId;
    }

    @Override
    public ArrayList<ResearchProject> getResearchProjectsList() throws DataRetrievalException {
        ArrayList<ResearchProject> researchProjectList = new ArrayList<>();
        PreparedStatement statement;
        String query = "SELECT researchProjectId, dueDate, startDate, kgalId, description, title, validationStatus, "
                + " directorStaffNumber1, directorStaffNumber2, directorStaffNumber3, expectedResults, requirements, "
                + " suggestedBibliography, studentMatricle1, studentMatricle2 FROM ResearchProjects";
        try {
            statement = dataBaseManager.getConnection().prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                ResearchProject researchProject = new ResearchProject();
                researchProject.setResearchProjectId(resultSet.getInt("researchProjectId"));
                researchProject.setDueDate(resultSet.getDate("dueDate"));
                researchProject.setStartDate(resultSet.getDate("startDate"));
                researchProject.setKgalId(resultSet.getInt("kgalId"));
                researchProject.setDescription(resultSet.getString("description"));
                researchProject.setTitle(resultSet.getString("title"));
                researchProject.setValidationStatus(resultSet.getString("validationStatus"));
                researchProject.setDirectorStaffNumber1(resultSet.getInt("directorStaffNumber1"));
                researchProject.setDirectorStaffNumber2(resultSet.getInt("directorStaffNumber2"));
                researchProject.setDirectorStaffNumber3(resultSet.getInt("directorStaffNumber3"));
                researchProject.setExpectedResults(resultSet.getString("expectedResults"));
                researchProject.setRequirements(resultSet.getString("requirements"));
                researchProject.setSuggestedBibliography(resultSet.getString("suggestedBibliography"));
                researchProject.setStudentMatricle1(resultSet.getString("studentMatricle1"));
                researchProject.setStudentMatricle2(resultSet.getString("studentMatricle2"));
                researchProjectList.add(researchProject);
            }
        } catch (SQLException exception) {
            throw new DataRetrievalException("Fallo al recuperar la informacion. Inténtelo de nuevo más tarde");
        } finally {
            dataBaseManager.closeConnection();
        }

        return researchProjectList;
    }

    public ArrayList<ResearchProject> getDirectorResearchProjects(int staffNumber) throws DataRetrievalException {
        ArrayList<ResearchProject> researchList = new ArrayList<>();
        PreparedStatement statement;
        String query = "SELECT researchProjectId, title, studentMatricle1, studentMatricle2, FROM ResearchProjects"
                + " WHERE (directorStaffNumber1 = ? OR directorStaffNumber2 = ? OR directorStaffNumber3 = ?)";

        try {
            statement = dataBaseManager.getConnection().prepareStatement(query);
            statement.setInt(1, staffNumber);
            statement.setInt(2, staffNumber);
            statement.setInt(3, staffNumber);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ResearchProject researchProject = new ResearchProject();
                researchProject.setResearchProjectId(resultSet.getInt("researchProjectId"));
                researchProject.setTitle(resultSet.getString("title"));
                researchProject.setStudentMatricle1(resultSet.getString("studentMatricle1"));
                researchProject.setStudentMatricle2(resultSet.getString("studentMatricle2"));
                researchList.add(researchProject);
            }
        } catch (SQLException exception) {
            throw new DataRetrievalException("No se pudo establecer conexión con la base de datos, inténtelo de nuevo");
        } finally {
            dataBaseManager.closeConnection();
        }

        return researchList;
    }

    public ResearchProject getStudentResearchProject(String matricle) throws DataRetrievalException {
        ResearchProject researchProject = new ResearchProject();
        PreparedStatement statement;
        String query = "SELECT researchProjectId, title FROM ResearchProjects WHERE studentMatricle1 IN(?) OR studentMatricle2 IN(?)";

        try {
            statement = dataBaseManager.getConnection().prepareStatement(query);
            statement.setString(1, matricle);
            statement.setString(2, matricle);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                researchProject.setResearchProjectId(resultSet.getInt("researchProjectId"));
                researchProject.setTitle(resultSet.getString("title"));
            }

            resultSet.close();
            statement.close();
        } catch (SQLException exception) {
            throw new DataRetrievalException("No se pudo establecer conexión con la base de datos, inténtelo de nuevo");
        } finally {
            dataBaseManager.closeConnection();
        }

        return researchProject;
    }

    public ArrayList<ResearchProject> getCourseResearchProjects(int nrc) throws DataRetrievalException {
        ArrayList<ResearchProject> researchList = new ArrayList<>();
        PreparedStatement statement;
        String query = "SELECT DISTINCT rp.researchProjectId, rp.title, rp.studentMatricle1, rp.studentMatricle2 FROM ResearchProjects rp "
                + "LEFT JOIN Students s1 ON rp.studentMatricle1 = s1.matricle INNER JOIN StudentsCourses sc1 ON s1.matricle = sc1.matricle "
                + "LEFT JOIN Students s2 ON rp.studentMatricle2 = s2.matricle INNER JOIN StudentsCourses sc2 ON s2.matricle = sc2.matricle "
                + "LEFT JOIN Courses c1 ON sc1.nrc = c1.nrc INNER JOIN Users u1 ON s1.userId = u1.userId "
                + "LEFT JOIN Courses c2 ON sc1.nrc = c2.nrc INNER JOIN Users u2 ON s2.userId = u2.userId "
                + "WHERE c1.nrc IN(?) OR c2.nrc IN(?);";

        try {
            statement = dataBaseManager.getConnection().prepareStatement(query);
            statement.setInt(1, nrc);
            statement.setInt(2, nrc);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ResearchProject researchProject = new ResearchProject();
                researchProject.setResearchProjectId(resultSet.getInt("rp.researchProjectId"));
                researchProject.setTitle(resultSet.getString("rp.title"));
                researchProject.setStudentMatricle1(resultSet.getString("rp.studentMatricle1"));
                researchProject.setStudentMatricle2(resultSet.getString("rp.studentMatricle2"));
                researchList.add(researchProject);
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
            throw new DataRetrievalException("No se pudo establecer conexión con la base de datos, inténtelo de nuevo");
        } finally {
            dataBaseManager.closeConnection();
        }

        return researchList;
    }

    public ArrayList<ResearchProject> getSpecifiedResearchProjectList(String researchName)
            throws DataRetrievalException {
        ArrayList<ResearchProject> researchProjectList = new ArrayList<>();
        PreparedStatement statement;
        String query = "SELECT researchProjectId, dueDate, startDate, kgalId, description, title, validationStatus, " +
                "directorStaffNumber1, directorStaffNumber2, directorStaffNumber3, expectedResults, requirements, " +
                "suggestedBibliography, studentMatricle1, studentMatricle2 FROM ResearchProjects WHERE title LIKE ?";

        try {
            statement = dataBaseManager.getConnection().prepareStatement(query);
            statement.setString(1, researchName + '%');

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ResearchProject researchProject = new ResearchProject();
                researchProject.setResearchProjectId(resultSet.getInt("researchProjectId"));
                researchProject.setDueDate(resultSet.getDate("dueDate"));
                researchProject.setStartDate(resultSet.getDate("startDate"));
                researchProject.setKgalId(resultSet.getInt("kgalId"));
                researchProject.setDescription(resultSet.getString("description"));
                researchProject.setTitle(resultSet.getString("title"));
                researchProject.setValidationStatus(resultSet.getString("validationStatus"));
                researchProject.setDirectorStaffNumber1(resultSet.getInt("directorStaffNumber1"));
                researchProject.setDirectorStaffNumber2(resultSet.getInt("directorStaffNumber2"));
                researchProject.setDirectorStaffNumber3(resultSet.getInt("directorStaffNumber3"));
                researchProject.setExpectedResults(resultSet.getString("expectedResults"));
                researchProject.setRequirements(resultSet.getString("requirements"));
                researchProject.setSuggestedBibliography(resultSet.getString("suggestedBibliography"));
                researchProject.setStudentMatricle1(resultSet.getString("studentMatricle1"));
                researchProject.setStudentMatricle2(resultSet.getString("studentMatricle2"));
                researchProjectList.add(researchProject);
            }
        } catch (SQLException exception) {
            throw new DataRetrievalException("Fallo al recuperar la informacion. Inténtelo de nuevo más tarde");
        } finally {
            dataBaseManager.closeConnection();
        }

        return researchProjectList;
    }

    public ArrayList<ResearchProject> getSpecifiedValidatedResearchProjectList(String researchName)
            throws DataRetrievalException {
        ArrayList<ResearchProject> researchProjectList = new ArrayList<>();
        PreparedStatement statement;

        String query = "SELECT researchProjectId, dueDate, startDate, kgalId, description, title, validationStatus, " +
                "directorStaffNumber1, directorStaffNumber2, directorStaffNumber3, expectedResults, requirements, " +
                "suggestedBibliography, studentMatricle1, studentMatricle2 FROM ResearchProjects WHERE title LIKE ? && validationStatus = ?";

        try {
            statement = dataBaseManager.getConnection().prepareStatement(query);
            statement.setString(1, researchName + '%');
            statement.setString(2, ResearchProjectStatus.VALIDATED.getValue());

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ResearchProject researchProject = new ResearchProject();
                researchProject.setResearchProjectId(resultSet.getInt("researchProjectId"));
                researchProject.setDueDate(resultSet.getDate("dueDate"));
                researchProject.setStartDate(resultSet.getDate("startDate"));
                researchProject.setKgalId(resultSet.getInt("kgalId"));
                researchProject.setDescription(resultSet.getString("description"));
                researchProject.setTitle(resultSet.getString("title"));
                researchProject.setValidationStatus(resultSet.getString("validationStatus"));
                researchProject.setDirectorStaffNumber1(resultSet.getInt("directorStaffNumber1"));
                researchProject.setDirectorStaffNumber2(resultSet.getInt("directorStaffNumber2"));
                researchProject.setDirectorStaffNumber3(resultSet.getInt("directorStaffNumber3"));
                researchProject.setExpectedResults(resultSet.getString("expectedResults"));
                researchProject.setRequirements(resultSet.getString("requirements"));
                researchProject.setSuggestedBibliography(resultSet.getString("suggestedBibliography"));
                researchProject.setStudentMatricle1(resultSet.getString("studentMatricle1"));
                researchProject.setStudentMatricle2(resultSet.getString("studentMatricle2"));
                researchProjectList.add(researchProject);
            }
        } catch (SQLException exception) {
            throw new DataRetrievalException("Fallo al recuperar la informacion. Inténtelo de nuevo más tarde");
        } finally {
            dataBaseManager.closeConnection();
        }

        return researchProjectList;
    }

    public ArrayList<ResearchProject> getSpecifiedNotValidatedResearchProjectList(String researchName)
            throws DataRetrievalException {
        ArrayList<ResearchProject> researchProjectList = new ArrayList<>();
        PreparedStatement statement;

        String query = "SELECT researchProjectId, dueDate, startDate, kgalId, description, title, validationStatus, " +
                "directorStaffNumber1, directorStaffNumber2, directorStaffNumber3, expectedResults, requirements, " +
                "suggestedBibliography, studentMatricle1, studentMatricle2 FROM ResearchProjects WHERE title LIKE ? && validationStatus = ?";

        try {
            statement = dataBaseManager.getConnection().prepareStatement(query);
            statement.setString(1, researchName + '%');
            statement.setString(2, ResearchProjectStatus.PROPOSED.getValue());

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ResearchProject researchProject = new ResearchProject();
                researchProject.setResearchProjectId(resultSet.getInt("researchProjectId"));
                researchProject.setDueDate(resultSet.getDate("dueDate"));
                researchProject.setStartDate(resultSet.getDate("startDate"));
                researchProject.setKgalId(resultSet.getInt("kgalId"));
                researchProject.setDescription(resultSet.getString("description"));
                researchProject.setTitle(resultSet.getString("title"));
                researchProject.setValidationStatus(resultSet.getString("validationStatus"));
                researchProject.setDirectorStaffNumber1(resultSet.getInt("directorStaffNumber1"));
                researchProject.setDirectorStaffNumber2(resultSet.getInt("directorStaffNumber2"));
                researchProject.setDirectorStaffNumber3(resultSet.getInt("directorStaffNumber3"));
                researchProject.setExpectedResults(resultSet.getString("expectedResults"));
                researchProject.setRequirements(resultSet.getString("requirements"));
                researchProject.setSuggestedBibliography(resultSet.getString("suggestedBibliography"));
                researchProject.setStudentMatricle1(resultSet.getString("studentMatricle1"));
                researchProject.setStudentMatricle2(resultSet.getString("studentMatricle2"));
                researchProjectList.add(researchProject);
            }
        } catch (SQLException exception) {
            throw new DataRetrievalException("Fallo al recuperar la informacion. Inténtelo de nuevo más tarde");
        } finally {
            dataBaseManager.closeConnection();
        }

        return researchProjectList;
    }

    public ArrayList<ResearchProject> getSpecifiedValidatedAndNotValidatedResearchProjectList(String researchName)
            throws DataRetrievalException {
        ArrayList<ResearchProject> researchProjectList = new ArrayList<>();
        PreparedStatement statement;

        String query = "SELECT researchProjectId, dueDate, startDate, kgalId, description, title, validationStatus, " +
                "directorStaffNumber1, directorStaffNumber2, directorStaffNumber3, expectedResults, requirements, " +
                "suggestedBibliography, studentMatricle1, studentMatricle2 FROM ResearchProjects WHERE title LIKE ? && "
                +
                "(validationStatus = ? || validationStatus = ?)";

        try {
            statement = dataBaseManager.getConnection().prepareStatement(query);
            statement.setString(1, researchName + '%');
            statement.setString(2, ResearchProjectStatus.VALIDATED.getValue());
            statement.setString(3, ResearchProjectStatus.PROPOSED.getValue());

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ResearchProject researchProject = new ResearchProject();
                researchProject.setResearchProjectId(resultSet.getInt("researchProjectId"));
                researchProject.setDueDate(resultSet.getDate("dueDate"));
                researchProject.setStartDate(resultSet.getDate("startDate"));
                researchProject.setKgalId(resultSet.getInt("kgalId"));
                researchProject.setDescription(resultSet.getString("description"));
                researchProject.setTitle(resultSet.getString("title"));
                researchProject.setValidationStatus(resultSet.getString("validationStatus"));
                researchProject.setDirectorStaffNumber1(resultSet.getInt("directorStaffNumber1"));
                researchProject.setDirectorStaffNumber2(resultSet.getInt("directorStaffNumber2"));
                researchProject.setDirectorStaffNumber3(resultSet.getInt("directorStaffNumber3"));
                researchProject.setExpectedResults(resultSet.getString("expectedResults"));
                researchProject.setRequirements(resultSet.getString("requirements"));
                researchProject.setSuggestedBibliography(resultSet.getString("suggestedBibliography"));
                researchProject.setStudentMatricle1(resultSet.getString("studentMatricle1"));
                researchProject.setStudentMatricle2(resultSet.getString("studentMatricle2"));
                researchProjectList.add(researchProject);
            }
        } catch (SQLException exception) {
            throw new DataRetrievalException("Fallo al recuperar la informacion. Inténtelo de nuevo más tarde");
        } finally {
            dataBaseManager.closeConnection();
        }

        return researchProjectList;
    }

    @Override
    public int modifyResearchProject(ResearchProject researchProject) throws DataInsertionException {
        int result = 0;
        PreparedStatement statement;
        String query = "UPDATE ResearchProjects SET dueDate = ?, startDate = ?, kgalId = ?, description = ?, expectedResults = ?, "
                + " requirements = ?, suggestedBibliography = ?, title = ?, studentMatricle1 = ?, studentMatricle2 = ?, directorStaffNumber1 = ?, directorStaffNumber2 = ?, directorStaffNumber3 = ? "
                + " WHERE researchProjectId = ?";

        try {
            statement = dataBaseManager.getConnection().prepareStatement(query);

            statement.setDate(1, researchProject.getDueDate());
            statement.setDate(2, researchProject.getStartDate());
            statement.setInt(3, researchProject.getKgalId());
            statement.setString(4, researchProject.getDescription());
            statement.setString(5, researchProject.getExpectedResults());
            statement.setString(6, researchProject.getRequirements());
            statement.setString(7, researchProject.getSuggestedBibliography());
            statement.setString(8, researchProject.getTitle());
            statement.setString(9, researchProject.getStudentMatricle1());
            statement.setString(10, researchProject.getStudentMatricle2());
            statement.setInt(11, researchProject.getDirectorStaffNumber1());
            statement.setInt(12, researchProject.getDirectorStaffNumber2());
            statement.setInt(13, researchProject.getDirectorStaffNumber3());
            statement.setInt(14, researchProject.getResearchProjectId());

            result = statement.executeUpdate();
        } catch (SQLException exception) {
            throw new DataInsertionException("Error de conexión. Inténtelo de nuevo más tarde");
        } finally {
            dataBaseManager.closeConnection();
        }

        return result;
    }

    @Override
    public void validateResearchProject(ResearchProject researchProject) throws DataInsertionException {
        PreparedStatement statement;
        String query = "UPDATE ResearchProjects SET validationStatus = ? WHERE researchProjectId = ?";

        try {
            statement = dataBaseManager.getConnection().prepareStatement(query);

            statement.setString(1, ResearchProjectStatus.VALIDATED.getValue());
            statement.setInt(2, researchProject.getResearchProjectId());
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new DataInsertionException("Error de conexión. Inténtelo de nuevo más tarde");
        } finally {
            dataBaseManager.closeConnection();
        }
    }

    public boolean assertResearchProject(ResearchProject researchProject) {
        return !isBlank(researchProject) && isValidDate(researchProject);
    }

    public boolean isBlank(ResearchProject researchProject) {
        return researchProject.getTitle().isBlank();
    }

    public boolean isValidDate(ResearchProject researchProject) {
        return researchProject.getStartDate().compareTo(researchProject.getDueDate()) <= 0;
    }
}