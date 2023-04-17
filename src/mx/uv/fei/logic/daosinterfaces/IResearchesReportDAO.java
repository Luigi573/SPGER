package mx.uv.fei.logic.daosinterfaces;

import java.util.ArrayList;

import mx.uv.fei.logic.domain.Research;

public interface IResearchesReportDAO {
    public abstract ArrayList<Research> getResearchesFromDatabase (String title);
}
