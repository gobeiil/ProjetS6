/*
 * Copyright 2015, 2016 Département de Génie Électrique et Génie Informatique (GEGI) de l'Université de Sherbrooke (UdeS).
 * Tous droits réservés / All rights reserved.
 */

package ca.uSherbrooke.gegi.opus.client.application.editUser;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class EditUserModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(EditUserPresenter.class, EditUserPresenter.MyView.class, EditUserView.class,
                EditUserPresenter.MyProxy.class);
    }
}