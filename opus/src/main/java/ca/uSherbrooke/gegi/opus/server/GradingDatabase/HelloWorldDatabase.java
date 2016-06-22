package ca.uSherbrooke.gegi.opus.server.GradingDatabase;

import ca.uSherbrooke.gegi.opus.shared.Grading.Course;
import ca.uSherbrooke.gegi.opus.shared.Grading.GradingQueryResult;

import java.sql.*;

/**
 * Created by ben_g on 2016-06-07.
 */
public class HelloWorldDatabase {
    /*public static void main(String[] args)
    {
        /*try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql:opus", "appopus", "appopus");
            System.out.println("lalalala");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from Users ");
            while (resultSet.next())
            {
                System.out.println(resultSet.getString("administrative_user_id"));
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        GradingQueryResult result = (new GradingQueryExecution()).getCoursesFromCip("gobb2201");
        System.out.println(result.getCip());
        for (Course course :
                result.getCourseList()) {
            System.out.println(course.getLabel() + " - " + course.getCourseName());
        }
    }*/
}
