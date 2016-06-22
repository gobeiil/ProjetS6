package ca.uSherbrooke.gegi.opus.shared.dispatch;

import ca.uSherbrooke.gegi.opus.shared.Grading.Course;
import com.gwtplatform.dispatch.rpc.shared.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ben_g on 2016-06-22.
 */

@SuppressWarnings("serial")
public class GetGradingResult implements Result {

    private ArrayList<Course> courseArrayList;
    public GetGradingResult()
    {

    }

    public GetGradingResult(ArrayList<Course> courseArrayList)
    {
        this.courseArrayList = courseArrayList;
    }

    public ArrayList<Course> getCourseArrayList() {
        return courseArrayList;
    }

    public void setCourseArrayList(ArrayList<Course> courseArrayList) {
        this.courseArrayList = courseArrayList;
    }
}