package ca.uSherbrooke.gegi.opus.shared.Grading;


import java.io.Serializable;

/**
 * Created by ben_g on 2016-06-22.
 */
public class Course implements Serializable{
    protected String label;
    protected String courseName;

    public Course()
    {

    }

    public Course(String label, String courseName)
    {
        this.label = label;
        this.courseName = courseName;
    }

    public String getLabel()
    {
        return this.label;
    }

    public String getCourseName()
    {
        return this.courseName;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
