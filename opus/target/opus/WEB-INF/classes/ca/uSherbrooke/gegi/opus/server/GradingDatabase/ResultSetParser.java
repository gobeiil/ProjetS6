package ca.uSherbrooke.gegi.opus.server.GradingDatabase;

import ca.uSherbrooke.gegi.opus.shared.Grading.Course;
import ca.uSherbrooke.gegi.opus.shared.Grading.GradingQueryResult;
import ca.uSherbrooke.gegi.opus.shared.dispatch.GetGradingResult;

import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by ben_g on 2016-06-22.
 */
public class ResultSetParser {
    public static GetGradingResult parseGradingQuery(ResultSet resultSet)
    {
        return buildQueryResult(getStringFromResultSet(resultSet));
    }

    public static GetGradingResult parseGradingQuery(ArrayList<String> stringArrayList)
    {
        return buildQueryResult(stringArrayList);
    }

    private static ArrayList<String> getStringFromResultSet(ResultSet resultSet)
    {
        ArrayList<String> arrayList = new ArrayList<String>();
        try {
            while (resultSet.next())
            {
                arrayList.add(resultSet.getString(1));
            }
        } finally {

            return arrayList;
        }
    }

   private static Course parseCourseFromString(String queryResult)
    {
        System.out.println(queryResult);
        queryResult = queryResult.replace("\"","").replace("(", "").replace(")", "");
        String stringArray[] = queryResult.split(",", 2);
        return new Course(stringArray[0], stringArray[1]);
    }

    private static GetGradingResult buildQueryResult(ArrayList<String> stringArrayList)
    {
        ArrayList<Course> courseArrayList = new ArrayList<Course>();
        for(String queryString : stringArrayList)
        {
            courseArrayList.add(parseCourseFromString(queryString));
        }
        return new GetGradingResult(courseArrayList);
    }
}
