package ca.uSherbrooke.gegi.opus.shared.Grading;


/**
 * Created by ben_g on 2016-06-22.
 */
public class Course {
    private String label;
    private String courseName;

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
