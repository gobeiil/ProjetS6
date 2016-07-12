package ca.uSherbrooke.gegi.opus.server.dispatch;

import ca.uSherbrooke.gegi.opus.shared.Grading.AP;
import ca.uSherbrooke.gegi.opus.shared.Grading.BoxScore;
import ca.uSherbrooke.gegi.opus.shared.Grading.SessionGrading;
import ca.uSherbrooke.gegi.opus.shared.Grading.Travail;
import ca.uSherbrooke.gegi.opus.shared.dispatch.GetSessionGrading;
import ca.uSherbrooke.gegi.opus.shared.dispatch.GetSessionGradingResult;
import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import java.util.ArrayList;

/**
 * Created by ben_g on 2016-07-11.
 */
public class GetSessionGradingActionHandler implements ActionHandler<GetSessionGrading, GetSessionGradingResult> {

    @Override
    public GetSessionGradingResult execute(GetSessionGrading getSessionGrading, ExecutionContext executionContext) throws ActionException {
        GetSessionGradingResult result = new GetSessionGradingResult();
        SessionGrading sessionGrading = new SessionGrading();
        sessionGrading.setCip("aubm2009");
        sessionGrading.setSession("S6");

        AP ap1 = new AP();
        ap1.setCourseName("Introduction aux ordinateur");
        ap1.setLabel("GIF 560");

        AP ap2 = new AP();
        ap2.setCourseName("Introduction aux transistors");
        ap2.setLabel("GIF 540");

        Travail travail11 = new Travail();
        travail11.setName("Rapport");

        BoxScore boxScore1 = new BoxScore(1, 30, 50, 25, 3);
        BoxScore boxScore2 = new BoxScore(2, 25, 50, 30, 2);
        BoxScore boxScore3 = new BoxScore(3, 45, 50, 40, 6);

        ArrayList<BoxScore> boxScores = new ArrayList<BoxScore>();

        boxScores.add(boxScore1);
        boxScores.add(boxScore2);
        boxScores.add(boxScore3);

        travail11.setBoxScoreArrayList(boxScores);


        Travail travail21 = new Travail();
        travail21.setBoxScoreArrayList(boxScores);
        travail21.setName("Rapport 1");

        Travail travail22 = new Travail();
        travail22.setBoxScoreArrayList(boxScores);
        travail22.setName("Examen Sommatif");

        ArrayList<Travail> travails1 = new ArrayList<Travail>();
        travails1.add(travail11);
        ap1.setTravails(travails1);

        ArrayList<Travail> travails2 = new ArrayList<Travail>();
        travails2.add(travail21);
        travails2.add(travail22);
        ap2.setTravails(travails2);

        ArrayList<AP> apList = new ArrayList<>();
        apList.add(ap1);
        apList.add(ap2);
        sessionGrading.setAPList(apList);
        result.setSessionGrading(sessionGrading);


        return result;
    }

    @Override
    public Class<GetSessionGrading> getActionType() {
        return GetSessionGrading.class;
    }

    @Override
    public void undo(GetSessionGrading getSessionGrading, GetSessionGradingResult getSessionGradingResult, ExecutionContext executionContext) throws ActionException {

    }
}
