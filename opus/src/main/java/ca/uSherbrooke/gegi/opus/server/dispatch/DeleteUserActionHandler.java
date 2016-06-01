/*
 * Copyright 2015, 2016 Département de Génie Électrique et Génie Informatique (GEGI) de l'Université de Sherbrooke (UdeS).
 * Tous droits réservés / All rights reserved.
 */

package ca.uSherbrooke.gegi.opus.server.dispatch;

import ca.uSherbrooke.gegi.commons.core.shared.entity.UserData;
import ca.uSherbrooke.gegi.opus.shared.dispatch.DeleteUser;
import ca.uSherbrooke.gegi.opus.shared.dispatch.DeleteUserResult;
import ca.uSherbrooke.gegi.persist.dao.Dao;
import ca.uSherbrooke.gegi.persist.dao.Opus;
import com.google.inject.Inject;
import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;

public class DeleteUserActionHandler implements ActionHandler<DeleteUser, DeleteUserResult> {

	@Inject @Opus Dao dao;

	@Inject
	public DeleteUserActionHandler() {

	}

	@Override
	public DeleteUserResult execute(DeleteUser action, ExecutionContext context) throws ActionException {
		dao.clearEntityManager();

		if(action.getUserId() != null){
			dao.remove(action.getUserId(), UserData.class);
		}

		return new DeleteUserResult();
	}

	@Override
	public void undo(DeleteUser action, DeleteUserResult result, ExecutionContext context) throws ActionException {
	}

	@Override
	public Class<DeleteUser> getActionType() {
		return DeleteUser.class;
	}
}
