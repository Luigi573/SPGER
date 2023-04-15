package mx.uv.fei.logic.daosinterfaces;

import java.util.ArrayList;

import mx.uv.fei.logic.domain.Research;

public interface IResearchDAO {
    public abstract ArrayList<Research> getResearchFromDatabase ();
    public abstract void setResearchToDatabase (Research research);
}
