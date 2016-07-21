package ca.uSherbrooke.gegi.opus.server.dispatch;

import ca.uSherbrooke.gegi.commons.core.server.utils.UserSession;
import ca.uSherbrooke.gegi.opus.shared.Grading.AP;
import ca.uSherbrooke.gegi.opus.shared.Grading.BoxScore;
import ca.uSherbrooke.gegi.opus.shared.Grading.SessionGrading;
import ca.uSherbrooke.gegi.opus.shared.Grading.Travail;
import ca.uSherbrooke.gegi.opus.shared.dispatch.GetSessionGrading;
import ca.uSherbrooke.gegi.opus.shared.dispatch.GetSessionGradingResult;
import ca.uSherbrooke.gegi.persist.dao.Dao;
import ca.uSherbrooke.gegi.persist.dao.Opus;
import com.google.gwt.core.client.GWT;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ben_g on 2016-07-11.
 */
public class GetSessionGradingActionHandler implements ActionHandler<GetSessionGrading, GetSessionGradingResult> {


    @javax.inject.Inject UserSession userSession;
    @Inject @Opus
    Dao dao;

    @Override
    public GetSessionGradingResult execute(GetSessionGrading getSessionGrading, ExecutionContext executionContext) throws ActionException {
        /*GetSessionGradingResult result = new GetSessionGradingResult();
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

        Travail travail12 = new Travail();
        travail12.setName("Examen");

        BoxScore boxScore1 = new BoxScore(1, 30, 50, 25, 3);
        BoxScore boxScore2 = new BoxScore(2, 25, 50, 30, 2);
        BoxScore boxScore3 = new BoxScore(3, 45, 50, 40, 6);

        BoxScore boxScore4 = new BoxScore(1, -1, 50, -1, -1);
        BoxScore boxScore5 = new BoxScore(2, -1, 50, -1, -1);
        BoxScore boxScore6 = new BoxScore(3, -1, 50, -1, -1);


        ArrayList<BoxScore> boxScores = new ArrayList<BoxScore>();

        boxScores.add(boxScore1);
        boxScores.add(boxScore2);
        boxScores.add(boxScore3);

        ArrayList<BoxScore> boxScores2 = new ArrayList<BoxScore>();

        boxScores2.add(boxScore4);
        boxScores2.add(boxScore5);
        boxScores2.add(boxScore6);

        travail11.setBoxScoreArrayList(boxScores);
        travail12.setBoxScoreArrayList(boxScores2);

        Travail travail21 = new Travail();
        travail21.setBoxScoreArrayList(boxScores);
        travail21.setName("Rapport 1");

        Travail travail22 = new Travail();
        travail22.setBoxScoreArrayList(boxScores);
        travail22.setName("Examen Sommatif");

        ArrayList<Travail> travails1 = new ArrayList<Travail>();
        travails1.add(travail11);
        travails1.add(travail12);
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


        return result;*/
        String userCip = userSession.getAdministrativeUserId();
        userCip = "elsf2301";

        dao.clearEntityManager();
        SessionGrading sessionGrading = new SessionGrading();

        sessionGrading.setSession("S1");
        List<Map<String, Object>> query = dao.getMap("SELECT label, description FROM bulletin.v_students_eg where student_id = \'" + userCip + "\';");
        for (Map<String, Object> queryItem : query) {
            AP newAP = new AP();
            newAP.setCourseName((String) queryItem.get("description"));
            newAP.setLabel((String) queryItem.get("label"));
            sessionGrading.getAPList().add(newAP);
        }
        for (AP ap : sessionGrading.getAPList())
        {
            List<Map<String, Object>> queryResult = dao.getMap("SELECT label_comp FROM bulletin.v_competence_students_eg as compAP inner join bulletin.educational_goal_mix egmix on compAP.label_eg = egmix.label where student_id = \'"+ userCip +"\' and label_eg = \'"+ ap.getLabel() +"\' order by label_comp desc;");
            ap.setNumberOfCompetencies(queryResult.size());
            List<Map<String, Object>> apQueryResult = dao.getMap("SELECT label_eg, label_eval FROM bulletin.v_travaux_students_eg where student_id = \'" + userCip + "\' and label_eg = \'" + ap.getLabel() + "\';");
            for (Map<String, Object> apMap : apQueryResult) {
                ap.getTravails().add(new Travail((String) apMap.get("label_eval")));
            }
            for (Travail travail : ap.getTravails()) {
                for (int i = 1; i <= ap.getNumberOfCompetencies(); i++) {
                    List<Map<String, Object>> travailQueryResult = dao.getMap("SELECT note, maxpoints, average, ecarttype FROM bulletin.v_resultats_eg_ev_comp resultats Inner join bulletin.v_avg_ecarttype_comp_ap avg_ect on (avg_ect.label_eg = resultats.ap AND avg_ect.label_eval = resultats.travail and avg_ect.label_comp = resultats.competence) where student_id = \'" + userCip + "\' and travail = \'" + travail.getName() + "\' and competence = " + i + ";");
                    if (travailQueryResult.size() == 0) {
                        continue;
                    }
                    Map<String, Object> gradesMap = travailQueryResult.get(0);
                    if (gradesMap == null) {
                        continue;
                    }

                    BoxScore boxScore = new BoxScore();
                    boxScore.setCompetence(i);
                    if (gradesMap.get("note") == null) {
                        boxScore.setGrade(-1);
                        boxScore.setPonderation((int) gradesMap.get("maxpoints"));
                        boxScore.setAverage(-1);
                        boxScore.setStandardDeviation(-1);
                    } else {
                        boxScore.setGrade((int) gradesMap.get("note"));
                        boxScore.setPonderation((int) gradesMap.get("maxpoints"));
                        boxScore.setAverage((int) gradesMap.get("average"));
                        boxScore.setStandardDeviation(gradesMap.get("ecarttype") == null ? -1 : (int) (gradesMap.get("ecarttype")));
                    }
                    travail.getBoxScoreArrayList().add(boxScore);
                }
                List<Map<String, Object>> travailTotalQuery = dao.getMap("SELECT total, maxpoints, groupAvg.moyenne_groupe, groupEcrt.ecarttype\n" +
                        "  FROM bulletin.v_totals_ap apTotals\n" +
                        "  inner join bulletin.v_totals_groupavg_ap groupAvg on (groupAvg.session = apTotals.session and apTotals.ap = groupAvg.ap and apTotals.travail = groupAvg.travail)\n" +
                        "  inner join bulletin.v_totals_groupecrt_ap groupEcrt on (groupEcrt.session = apTotals.session and apTotals.ap = groupEcrt.ap and apTotals.travail = groupEcrt.travail)\n" +
                        "  where student_id = \'"+ userCip +"\'\n" +
                        "  and groupAvg.ap = \'"+ ap.getLabel() +"\'\n" +
                        "  and groupAvg.travail = \'"+ travail.getName() +"\'\n");
                Map<String, Object> totalMap = travailTotalQuery.get(0);
                if (totalMap == null)
                {
                    continue;
                }
                BoxScore tempBoxScore = new BoxScore();
                if (totalMap.get("total") == null) {
                    tempBoxScore.setGrade(-1);
                    tempBoxScore.setPonderation((int) totalMap.get("maxpoints"));
                    tempBoxScore.setAverage(-1);
                    tempBoxScore.setStandardDeviation(-1);
                } else {
                    tempBoxScore.setGrade((int) totalMap.get("total"));
                    tempBoxScore.setPonderation((int) totalMap.get("maxpoints"));
                    tempBoxScore.setAverage((int) totalMap.get("moyenne_groupe"));
                    tempBoxScore.setStandardDeviation(totalMap.get("ecarttype") == null ? -1 : (int) (totalMap.get("ecarttype")));
                }
                travail.setTotalBoxScore(tempBoxScore);

            }
            ArrayList<BoxScore> list = new ArrayList<>();
            for (int i = 1; i <= ap.getNumberOfCompetencies(); i++)
            {
                List<Map<String, Object>> travailTotalQuery = dao.getMap("SELECT total, maxpoints, groupAvg.moyenne_groupe, groupEcrt.ecarttype\n" +
                        "  FROM bulletin.v_totals_comp compTotals\n" +
                        "  inner join bulletin.v_totals_groupavg_comp groupAvg on (groupAvg.session = compTotals.session and compTotals.ap = groupAvg.ap and compTotals.competence = groupAvg.competence)\n" +
                        "  inner join bulletin.v_totals_groupecrt_comp groupEcrt on (groupEcrt.session = compTotals.session and compTotals.ap = groupEcrt.ap and compTotals.competence = groupEcrt.competence)\n" +
                        "  where student_id = \'"+ userCip +"\'\n" +
                        "  and groupAvg.ap = \'"+ ap.getLabel() +"\'\n" +
                        "  and groupAvg.competence = "+ i +"\n");
                Map<String, Object> totalMap = travailTotalQuery.get(0);
                if (totalMap == null)
                {
                    continue;
                }
                BoxScore tempBoxScore = new BoxScore();
                if (totalMap.get("total") == null) {
                    tempBoxScore.setGrade(-1);
                    tempBoxScore.setPonderation((int) totalMap.get("maxpoints"));
                    tempBoxScore.setAverage(-1);
                    tempBoxScore.setStandardDeviation(-1);
                } else {
                    tempBoxScore.setGrade((int) totalMap.get("total"));
                    tempBoxScore.setPonderation((int) totalMap.get("maxpoints"));
                    tempBoxScore.setAverage((int) totalMap.get("moyenne_groupe"));
                    tempBoxScore.setStandardDeviation(totalMap.get("ecarttype") == null ? -1 : (int) (totalMap.get("ecarttype")));
                }
                list.add(tempBoxScore);
            }
            ap.setTotalCompetencyBoxScore(list);
            ap.setGrandTotal();
        }
        return new GetSessionGradingResult(sessionGrading);
    }

    @Override
    public Class<GetSessionGrading> getActionType() {
        return GetSessionGrading.class;
    }

    @Override
    public void undo(GetSessionGrading getSessionGrading, GetSessionGradingResult getSessionGradingResult, ExecutionContext executionContext) throws ActionException {

    }
}
