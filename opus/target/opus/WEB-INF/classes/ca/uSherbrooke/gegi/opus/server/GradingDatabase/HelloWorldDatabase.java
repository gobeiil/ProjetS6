package ca.uSherbrooke.gegi.opus.server.GradingDatabase;

import java.sql.*;

/**
 * Created by ben_g on 2016-06-07.
 */
public class HelloWorldDatabase {
    public static void main(String[] args)
    {
        try {
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
    }
}
