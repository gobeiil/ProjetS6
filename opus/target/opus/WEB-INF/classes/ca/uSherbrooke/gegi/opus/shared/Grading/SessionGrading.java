package ca.uSherbrooke.gegi.opus.shared.Grading;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ben_g on 2016-07-11.
 */
public class SessionGrading implements Serializable{

    private ArrayList<AP> APList;
    private String cip;
    private String session;

    public SessionGrading() {
        this.APList = new ArrayList<AP>();
    }

    public SessionGrading(ArrayList<AP> APList, String cip, String session) {
        this.APList = APList;
        this.cip = cip;
        this.session = session;
    }

    public ArrayList<AP> getAPList() {
        return APList;
    }

    public void setAPList(ArrayList<AP> APList) {
        this.APList = APList;
    }

    public String getCip() {
        return cip;
    }

    public void setCip(String cip) {
        this.cip = cip;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }
}
