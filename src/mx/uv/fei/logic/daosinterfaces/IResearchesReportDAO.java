package mx.uv.fei.logic.daosinterfaces;

import java.util.ArrayList;

import mx.uv.fei.logic.domain.Research;

public interface IResearchesReportDAO {
    public abstract ArrayList<Research> getResearchesFromDatabase (String title, String query);
    public abstract ArrayList<Research> getValidatedResearchesFromDatabase (String title);
    public abstract ArrayList<Research> getNotValidatedResearchesFromDatabase (String title);
    public abstract ArrayList<Research> getValidatedAndNotValidatedResearchesFromDatabase (String title);
    public abstract ArrayList<Research> getSelectedResearchesFromDatabase (ArrayList<String> selectedResearchesTitles);
}
