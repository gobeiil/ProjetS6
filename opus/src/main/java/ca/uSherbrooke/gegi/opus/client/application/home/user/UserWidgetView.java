
/*
 * Copyright 2015, 2016 Département de Génie Électrique et Génie Informatique (GEGI) de l'Université de Sherbrooke (UdeS).
 * Tous droits réservés / All rights reserved.
 */

package ca.uSherbrooke.gegi.opus.client.application.home.user;

import ca.uSherbrooke.gegi.commons.core.shared.entity.UserData;
import ca.uSherbrooke.gegi.opus.client.place.NameTokens;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import org.gwtbootstrap3.client.ui.Anchor;

public class UserWidgetView extends ViewImpl implements UserWidgetPresenter.MyView {
    interface Binder extends UiBinder<HTMLPanel, UserWidgetView> {
    }

    @UiField Anchor anchorUserInfo;

    @Inject
    UserWidgetView(Binder binder) {
        initWidget(binder.createAndBindUi(this));
    }

    public void setUserData(UserData userData, boolean directMemberOfGroup){
        anchorUserInfo.setText(userData.getLabel());

        if (directMemberOfGroup) {
            anchorUserInfo.getElement().getStyle().setBackgroundColor("#99ff99");
        }
        else {
            anchorUserInfo.getElement().getStyle().setBackgroundColor("#ccffff");
        }

        anchorUserInfo.setTargetHistoryToken(NameTokens.editUser.replace("{userId}", userData.getId().toString()));
    }
}
