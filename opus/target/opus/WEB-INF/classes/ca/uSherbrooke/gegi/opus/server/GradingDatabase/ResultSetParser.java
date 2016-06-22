package ca.uSherbrooke.gegi.opus.server.GradingDatabase;

import ca.uSherbrooke.gegi.opus.shared.Grading.Course;
import ca.uSherbrooke.gegi.opus.shared.Grading.GradingQueryResult;

import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by ben_g on 2016-06-22.
 */
public class ResultSetParser {
    public static GradingQueryResult parseGradingQuery(String cip, ResultSet resultSet)
    {
        return buildQueryResult(cip, getStringListFromResultSet(resultSet));
    }

    private static ArrayList<String> getStringListFromResultSet(ResultSet resultSet)
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
        queryResult = queryResult.replace("\"","").replace("(", "").replace(")", "");
        String stringArray[] = queryResult.split(",", 2);
        return new Course(stringArray[0], stringArray[1]);
    }

    private static GradingQueryResult buildQueryResult(String cip, ArrayList<String> stringArrayList)
    {
        ArrayList<Course> courseArrayList = new ArrayList<Course>();
        for(String queryString : stringArrayList)
        {
            courseArrayList.add(parseCourseFromString(queryString));
        }
        return new GradingQueryResult(cip, courseArrayList);
    }
}
