package ca.uSherbrooke.gegi.opus.shared.Grading;

import java.util.ArrayList;

import static java.lang.Math.sqrt;

/**
 * Created by ben_g on 2016-07-11.
 */
public class AP extends Course {

    private int numberOfCompetencies;
    private ArrayList<Travail> travails;
    private ArrayList<BoxScore> totalCompetencyBoxScore;
    private BoxScore grandTotal;

    public AP()
    {
        super();
        this.travails = new ArrayList<Travail>();
    }

    public AP(ArrayList<Travail> travails, int nbComp) {
        this.travails = travails;
        this.numberOfCompetencies = nbComp;
    }

    public AP(String label, String courseName, ArrayList<Travail> travails, int nbComp) {
        super(label, courseName);
        this.travails = travails;
        this.numberOfCompetencies = nbComp;
    }

    public ArrayList<Travail> getTravails() {
        return travails;
    }

    public void setTravails(ArrayList<Travail> travails) {
        this.travails = travails;
    }

    public int getNumberOfCompetencies() {
        return numberOfCompetencies;
    }

    public void setNumberOfCompetencies(int numberOfCompetencies) {
        this.numberOfCompetencies = numberOfCompetencies;
    }

    public ArrayList<BoxScore> getTotalCompetencyBoxScore() {
        return totalCompetencyBoxScore;
    }

    public void setTotalCompetencyBoxScore(ArrayList<BoxScore> totalCompetencyBoxScore) {
        this.totalCompetencyBoxScore = totalCompetencyBoxScore;
    }

    public BoxScore getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(BoxScore grandTotal) {
        this.grandTotal = grandTotal;
    }

    public void setGrandTotal()
    {
        this.grandTotal = new BoxScore();
        int totalGrade = 0;
        int totalPond = 0;
        int totalAverage = 0;
        int totalStandard = 0;

        for(BoxScore boxScore : this.getTotalCompetencyBoxScore())
        {
            totalGrade += boxScore.getGrade();
            totalPond += boxScore.getPonderation();
            totalAverage += boxScore.getAverage();
            totalStandard = (int)boxScore.getStandardDeviation() ^ 2;
        }

        this.grandTotal.setGrade(totalGrade);
        this.grandTotal.setPonderation(totalPond);
        this.grandTotal.setAverage(totalAverage);
        totalStandard /= this.getNumberOfCompetencies();
        this.grandTotal.setStandardDeviation(sqrt(totalStandard));
    }
}
