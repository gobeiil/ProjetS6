package ca.uSherbrooke.gegi.opus.shared.dispatch;

import ca.uSherbrooke.gegi.commons.core.server.utils.UserSession;
import ca.uSherbrooke.gegi.commons.core.server.utils.UserSessionImpl;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.rpc.shared.ActionImpl;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by ben_g on 2016-07-07.
 */
public class GetSessionGrading extends ActionImpl<GetSessionGradingResult> {

    private String session;
    private String cip;

    public GetSessionGrading()
    {

    }

    public GetSessionGrading(String session)
    {
        this.session = session;
        //this.cip = cip;
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
