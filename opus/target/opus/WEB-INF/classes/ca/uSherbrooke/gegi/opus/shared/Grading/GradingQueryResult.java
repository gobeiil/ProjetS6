package ca.uSherbrooke.gegi.opus.shared.Grading;

import java.util.ArrayList;

/**
 * Created by ben_g on 2016-06-22.
 */
public class GradingQueryResult {
    private String cip;
    private ArrayList<Course> courseList;

    public GradingQueryResult(String cip, ArrayList<Course> courseList)
    {
        this.cip = cip;
        this.courseList = courseList;
    }

    public String getCip()
    {
        return this.cip;
    }

    public ArrayList<Course> getCourseList()
    {
        return this.courseList;
    }
}
