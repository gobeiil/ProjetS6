/*
 * Copyright 2015, 2016 Département de Génie Électrique et Génie Informatique (GEGI) de l'Université de Sherbrooke (UdeS).
 * Tous droits réservés / All rights reserved.
 */

package ca.uSherbrooke.gegi.opus.client.application.home.sideMenu;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class SideMenuModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindSingletonPresenterWidget(SideMenuPresenter.class, SideMenuPresenter.MyView.class, SideMenuView.class);
    }
}