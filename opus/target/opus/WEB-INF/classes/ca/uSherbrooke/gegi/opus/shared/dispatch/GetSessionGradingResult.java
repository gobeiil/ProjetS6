package ca.uSherbrooke.gegi.opus.shared.dispatch;

import ca.uSherbrooke.gegi.opus.shared.Grading.SessionGrading;
import com.gwtplatform.dispatch.rpc.shared.Result;

/**
 * Created by ben_g on 2016-07-11.
 */
public class GetSessionGradingResult implements Result {

    private SessionGrading sessionGrading;

    public GetSessionGradingResult() {
    }

    public GetSessionGradingResult(SessionGrading sessionGrading) {
        this.sessionGrading = sessionGrading;
    }

    public SessionGrading getSessionGrading() {
        return sessionGrading;
    }

    public void setSessionGrading(SessionGrading sessionGrading) {
        this.sessionGrading = sessionGrading;
    }
}
