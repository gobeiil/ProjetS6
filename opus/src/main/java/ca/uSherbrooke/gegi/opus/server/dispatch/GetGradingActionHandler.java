package ca.uSherbrooke.gegi.opus.server.dispatch;

import ca.uSherbrooke.gegi.opus.server.GradingDatabase.ResultSetParser;
import ca.uSherbrooke.gegi.opus.shared.dispatch.GetGrading;
import ca.uSherbrooke.gegi.opus.shared.dispatch.GetGradingResult;
import ca.uSherbrooke.gegi.persist.dao.Dao;
import ca.uSherbrooke.gegi.persist.dao.Opus;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ben_g on 2016-06-22.
 */
public class GetGradingActionHandler implements ActionHandler<GetGrading, GetGradingResult> {

    @Inject @Opus
    Dao dao;

    @Override
    public GetGradingResult execute(GetGrading getGrading, ExecutionContext executionContext) throws ActionException {
        dao.clearEntityManager();

        List<String> stringList = (List<String>) dao.getNamedResultList("Select bulletin.f_getegbystudent(\'"+ getGrading.getCip() + "\')");
        ArrayList<String> stringArrayList = new ArrayList<String>(stringList);


        return ResultSetParser.parseGradingQuery(stringArrayList);
    }

    @Override
    public Class<GetGrading> getActionType() {
        return GetGrading.class;
    }

    @Override
    public void undo(GetGrading getGrading, GetGradingResult getGradingResult, ExecutionContext executionContext) throws ActionException {

    }
}
