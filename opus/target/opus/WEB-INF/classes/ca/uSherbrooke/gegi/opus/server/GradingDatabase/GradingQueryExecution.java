package ca.uSherbrooke.gegi.opus.server.GradingDatabase;

import ca.uSherbrooke.gegi.opus.shared.Grading.GradingQueryResult;

import java.sql.*;

import static ca.uSherbrooke.gegi.opus.server.GradingDatabase.ResultSetParser.parseGradingQuery;

/**
 * Created by ben_g on 2016-06-22.
 */
public class GradingQueryExecution {
    public GradingQueryResult getCoursesFromCip(String cip)
    {
        GradingQueryResult result = null;
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql:opus", "appopus", "appopus");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("Select bulletin.f_getegbystudent(\'"+ cip + "\')");
            result = ResultSetParser.parseGradingQuery(cip,resultSet);

            connection.close();
            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("jasdkjnadkj");
            return result;
        }
        return result;
    }

}
