
/*
 * Copyright 2015, 2016 Département de Génie Électrique et Génie Informatique (GEGI) de l'Université de Sherbrooke (UdeS).
 * Tous droits réservés / All rights reserved.
 */

package ca.uSherbrooke.gegi.opus.client.application.home.user;

import ca.uSherbrooke.gegi.commons.core.shared.entity.UserData;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

public class UserWidgetPresenter extends PresenterWidget<UserWidgetPresenter.MyView> {

    private UserData userData;
    private boolean directMemberOfGroup;

    interface MyView extends View {
        public void setUserData(UserData userData, boolean directMemberOfGroup);
    }

    @Inject
    UserWidgetPresenter(EventBus eventBus, MyView view) {
        super(eventBus, view);
    }

    public void setUserData(UserData userData, boolean directMemberOfGroup){
        this.userData = userData;
        this.directMemberOfGroup = directMemberOfGroup;

        getView().setUserData(userData, directMemberOfGroup);
    }
}
