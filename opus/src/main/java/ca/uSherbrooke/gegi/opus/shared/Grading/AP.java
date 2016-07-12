package ca.uSherbrooke.gegi.opus.shared.Grading;

import java.util.ArrayList;

/**
 * Created by ben_g on 2016-07-11.
 */
public class AP extends Course {
    private ArrayList<Travail> travails;

    public AP()
    {
        super();
    }

    public AP(ArrayList<Travail> travails) {
        this.travails = travails;
    }

    public AP(String label, String courseName, ArrayList<Travail> travails) {
        super(label, courseName);
        this.travails = travails;
    }

    public ArrayList<Travail> getTravails() {
        return travails;
    }

    public void setTravails(ArrayList<Travail> travails) {
        this.travails = travails;
    }
}
