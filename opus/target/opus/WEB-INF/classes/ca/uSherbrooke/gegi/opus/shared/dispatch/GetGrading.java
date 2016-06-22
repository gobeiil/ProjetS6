package ca.uSherbrooke.gegi.opus.shared.dispatch;

import ca.uSherbrooke.gegi.opus.shared.Grading.Course;
import com.gwtplatform.dispatch.rpc.shared.ActionImpl;

import java.util.ArrayList;

/**
 * Created by ben_g on 2016-06-22.
 */
public class GetGrading extends ActionImpl<GetGradingResult> {
    private String cip = null;

    public GetGrading()
    {

    }

    public GetGrading(String cip)
    {
        this.cip = cip;
    }

    public String getCip()
    {
        return this.cip;
    }

    @Override
    public boolean isSecured() {
        return false;
    }
}
