package ca.uSherbrooke.gegi.opus.shared.Grading;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ben_g on 2016-07-11.
 */
public class Travail implements Serializable{
    private String name;
    private ArrayList<BoxScore> boxScoreArrayList;

    public Travail() {
    }

    public Travail(String name, ArrayList<BoxScore> boxScoreArrayList) {

        this.name = name;
        this.boxScoreArrayList = boxScoreArrayList;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<BoxScore> getBoxScoreArrayList() {
        return boxScoreArrayList;
    }

    public void setBoxScoreArrayList(ArrayList<BoxScore> boxScoreArrayList) {
        this.boxScoreArrayList = boxScoreArrayList;
    }
}
