package mx.uv.fei.logic.daosinterfaces;

import java.util.ArrayList;

import mx.uv.fei.logic.domain.Research;

public interface IReceptionalWorkReportDAO {
    public abstract ArrayList<Research> getResearchsFromDatabase (String title);
}
