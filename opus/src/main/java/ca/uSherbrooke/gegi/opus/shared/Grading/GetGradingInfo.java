package ca.uSherbrooke.gegi.opus.shared.Grading;

import ca.uSherbrooke.gegi.opus.server.GradingDatabase.GradingQueryExecution;

/**
 * Created by ben_g on 2016-06-22.
 */
public class GetGradingInfo {
    public GradingQueryResult GetGradingInfoFromCip(String cip)
    {
        return new GradingQueryExecution().getCoursesFromCip(cip);
    }
}
