package ca.uSherbrooke.gegi.opus.shared.Grading;

import java.io.Serializable;

/**
 * Created by ben_g on 2016-07-07.
 */
public class BoxScore implements Serializable {

    private int competence;
    private double grade;
    private double ponderation;
    private double average;
    private double standardDeviation;

    public BoxScore() {
    }

    public BoxScore(int competence, double grade, double ponderation, double average, double standardDeviation) {
        this.competence = competence;
        this.grade = grade;
        this.ponderation = ponderation;
        this.average = average;
        this.standardDeviation = standardDeviation;
    }

    public int getCompetence() {
        return competence;
    }

    public void setCompetence(int competence) {
        this.competence = competence;
    }


    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public double getPonderation() {
        return ponderation;
    }

    public void setPonderation(double ponderation) {
        this.ponderation = ponderation;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public double getStandardDeviation() {
        return standardDeviation;
    }

    public void setStandardDeviation(double standardDeviation) {
        this.standardDeviation = standardDeviation;
    }
}
