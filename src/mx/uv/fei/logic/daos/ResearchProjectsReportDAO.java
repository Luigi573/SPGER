package mx.uv.fei.logic.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.logic.daosinterfaces.IResearchProjectsReportDAO;
import mx.uv.fei.logic.domain.ResearchProject;
import mx.uv.fei.logic.domain.statuses.ResearchProjectStatus;
import mx.uv.fei.logic.exceptions.DataRetrievalException;

public class ResearchProjectsReportDAO implements IResearchProjectsReportDAO {
    private final DataBaseManager dataBaseManager;

    public ResearchProjectsReportDAO() {
        dataBaseManager = new DataBaseManager();
    }

    @Override
    public ArrayList<ResearchProject> getResearchProjects(String title) throws DataRetrievalException {
        ArrayList<ResearchProject> researchesProjects = new ArrayList<>();

        try {
            String query = "SELECT title FROM ResearchProjects WHERE title LIKE ?";

            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setString(1, title + "%");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ResearchProject researchProject = new ResearchProject();
                researchProject.setTitle(resultSet.getString("title"));
                researchesProjects.add(researchProject);
            }
            resultSet.close();
            dataBaseManager.closeConnection();

        } catch (SQLException e) {
            throw new DataRetrievalException("Fallo al recuperar la informacion. Inténtelo de nuevo más tarde");
        } finally {
            dataBaseManager.closeConnection();
        }

        return researchesProjects;
    }

    @Override
    public ArrayList<ResearchProject> getValidatedResearchProjects(String title) throws DataRetrievalException {
        ArrayList<ResearchProject> validatedResearchProjects = new ArrayList<>();

        try {
            String query = "SELECT title FROM ResearchProjects WHERE title LIKE ? && validationStatus = ?";

            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setString(1, title + "%");
            preparedStatement.setString(2, ResearchProjectStatus.VALIDATED.getValue());

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ResearchProject researchProject = new ResearchProject();
                researchProject.setTitle(resultSet.getString("title"));
                validatedResearchProjects.add(researchProject);
            }
            resultSet.close();
            dataBaseManager.closeConnection();

        } catch (SQLException e) {
            throw new DataRetrievalException("Fallo al recuperar la informacion. Inténtelo de nuevo más tarde");
        } finally {
            dataBaseManager.closeConnection();
        }

        return validatedResearchProjects;
    }

    @Override
    public ArrayList<ResearchProject> getNotValidatedResearchProjects(String title) throws DataRetrievalException {
        ArrayList<ResearchProject> notValidatedResearchProjects = new ArrayList<>();

        try {
            String query = "SELECT title FROM ResearchProjects WHERE title LIKE ? && validationStatus = ?";

            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setString(1, title + "%");
            preparedStatement.setString(2, ResearchProjectStatus.NOT_VALIDATED.getValue());

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ResearchProject researchProject = new ResearchProject();
                researchProject.setTitle(resultSet.getString("title"));
                notValidatedResearchProjects.add(researchProject);
            }
            resultSet.close();
            dataBaseManager.closeConnection();

        } catch (SQLException e) {
            throw new DataRetrievalException("Fallo al recuperar la informacion. Inténtelo de nuevo más tarde");
        } finally {
            dataBaseManager.closeConnection();
        }

        return notValidatedResearchProjects;
    }

    @Override
    public ArrayList<ResearchProject> getValidatedAndNotValidatedResearchProjects(String title)
            throws DataRetrievalException {
        ArrayList<ResearchProject> validatedAndNotValidatedResearchProjects = new ArrayList<>();

        try {
            String query = "SELECT title FROM ResearchProjects WHERE title LIKE ? && ( validationStatus = ? || validationStatus = ? )";

            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setString(1, title + "%");
            preparedStatement.setString(2, ResearchProjectStatus.VALIDATED.getValue());
            preparedStatement.setString(3, ResearchProjectStatus.NOT_VALIDATED.getValue());

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ResearchProject researchProject = new ResearchProject();
                researchProject.setTitle(resultSet.getString("title"));
                validatedAndNotValidatedResearchProjects.add(researchProject);
            }
            resultSet.close();
            dataBaseManager.closeConnection();

        } catch (SQLException e) {
            throw new DataRetrievalException("Fallo al recuperar la informacion. Inténtelo de nuevo más tarde");
        } finally {
            dataBaseManager.closeConnection();
        }

        return validatedAndNotValidatedResearchProjects;
    }

    @Override
    public ArrayList<ResearchProject> getSelectedResearchProjects(ArrayList<String> selectedResearchesTitles)
            throws DataRetrievalException {
        DataBaseManager dataBaseManager = new DataBaseManager();
        ArrayList<ResearchProject> selectedResearches = new ArrayList<>();

        String fullQuery = "SELECT * FROM ResearchProjects WHERE title = ";
        for (int i = 1; i <= selectedResearchesTitles.size(); i++) {
            fullQuery = fullQuery + "?";
            if (i < selectedResearchesTitles.size()) {
                fullQuery = fullQuery + " || title = ";
            }
        }

        try {
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(fullQuery);
            int researchesListForQueryCounter = 1;
            for (String researchProject : selectedResearchesTitles) {
                preparedStatement.setString(researchesListForQueryCounter, researchProject);
                researchesListForQueryCounter++;
            }

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ResearchProject researchProject = new ResearchProject();
                researchProject.setStudentMatricle1(resultSet.getString("studentMatricle1"));
                researchProject.setStudentMatricle2(resultSet.getString("studentMatricle2"));
                researchProject.setDirectorStaffNumber1(resultSet.getInt("directorStaffNumber1"));
                researchProject.setDirectorStaffNumber2(resultSet.getInt("directorStaffNumber2"));
                researchProject.setDirectorStaffNumber3(resultSet.getInt("directorStaffNumber3"));
                researchProject.setKgalId(resultSet.getInt("kgalId"));
                researchProject.setDescription(resultSet.getString("description"));
                researchProject.setDueDate(resultSet.getDate("dueDate"));
                researchProject.setExpectedResults(resultSet.getString("expectedResults"));
                researchProject.setRequirements(resultSet.getString("requirements"));
                researchProject.setStartDate(resultSet.getDate("startDate"));
                researchProject.setSuggestedBibliography(resultSet.getString("suggestedBibliography"));
                researchProject.setTitle(resultSet.getString("title"));
                researchProject.setValidationStatus(resultSet.getString("validationStatus"));
                selectedResearches.add(researchProject);
            }
            resultSet.close();
            dataBaseManager.closeConnection();
        } catch (SQLException e) {
            throw new DataRetrievalException("Fallo al recuperar la informacion. Inténtelo de nuevo más tarde");
        } finally {
            dataBaseManager.closeConnection();
        }

        return selectedResearches;
    }

}
