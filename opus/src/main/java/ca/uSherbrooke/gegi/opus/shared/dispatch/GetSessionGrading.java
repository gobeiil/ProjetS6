package ca.uSherbrooke.gegi.opus.shared.dispatch;

import com.gwtplatform.dispatch.rpc.shared.ActionImpl;

/**
 * Created by ben_g on 2016-07-07.
 */
public class GetSessionGrading extends ActionImpl<GetSessionGradingResult> {

    private String session;
    private String cip;

    public GetSessionGrading()
    {

    }

    public GetSessionGrading(String session, String cip)
    {
        this.session = session;
        this.cip = cip;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getCip() {
        return cip;
    }

    public void setCip(String cip) {
        this.cip = cip;
    }

    @Override
    public boolean isSecured() {
        return false;
    }
}
