/*
 * Copyright 2015, 2016 Département de Génie Électrique et Génie Informatique (GEGI) de l'Université de Sherbrooke (UdeS).
 * Tous droits réservés / All rights reserved.
 */

package ca.uSherbrooke.gegi.opus.client.application.home.user;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class UserModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenterWidget(UserWidgetPresenter.class, UserWidgetPresenter.MyView.class, UserWidgetView.class);
    }
}