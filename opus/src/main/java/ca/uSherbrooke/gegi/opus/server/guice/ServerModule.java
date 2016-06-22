/*
 * Copyright 2015, 2016 Département de Génie Électrique et Génie Informatique (GEGI) de l'Université de Sherbrooke (UdeS).
 * Tous droits réservés / All rights reserved.
 */

package ca.uSherbrooke.gegi.opus.server.guice;

import ca.uSherbrooke.gegi.opus.server.dispatch.DeleteUserActionHandler;
import ca.uSherbrooke.gegi.opus.server.dispatch.GetGradingActionHandler;
import ca.uSherbrooke.gegi.opus.shared.dispatch.DeleteUser;
import ca.uSherbrooke.gegi.opus.shared.dispatch.GetGrading;
import com.gwtplatform.dispatch.rpc.server.guice.HandlerModule;

public class ServerModule extends HandlerModule {
    @Override
    protected void configureHandlers() {
        bindHandler(DeleteUser.class, DeleteUserActionHandler.class);
        bindHandler(GetGrading.class, GetGradingActionHandler.class);
    }
}