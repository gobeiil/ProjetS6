/*
 * Copyright 2015, 2016 Département de Génie Électrique et Génie Informatique (GEGI) de l'Université de Sherbrooke (UdeS).
 * Tous droits réservés / All rights reserved.
 */

package ca.uSherbrooke.gegi.opus.client.application.editUser;

import ca.uSherbrooke.gegi.commons.core.shared.entity.UserData;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.Form;
import org.gwtbootstrap3.client.ui.TextBox;

public class EditUserView extends ViewWithUiHandlers<EditUserUiHandlers> implements EditUserPresenter.MyView {

    private final Widget widget;

    @UiField Form      formUserInfo;
    @UiField TextBox   textBoxFirstName;
    @UiField TextBox   textBoxLastName;
    @UiField TextBox   textBoxAdministrativeUserId;
    @UiField TextBox   textBoxEmail;
    @UiField Button    buttonSave;

    public interface Binder extends UiBinder<Widget, EditUserView> {}

    @Inject
    public EditUserView(final Binder binder) {
        widget = binder.createAndBindUi(this);
    }

    @Override
    public Widget asWidget() {
        return widget;
    }

    public void resetView() {
        formUserInfo.reset();
    }

    @Override
    public void setUser(UserData userData) {
        textBoxFirstName.setText(userData.getFirstName());
        textBoxLastName.setText(userData.getLastName());
        textBoxAdministrativeUserId.setText(userData.getAdministrativeUserId());
        textBoxEmail.setText(userData.getEmailAddress());
    }

    @Override
    public String getFirstName() {
        return textBoxFirstName.getText();
    }

    @Override
    public String getLastName() {
        return textBoxLastName.getText();
    }

    @Override
    public String getEmailAddress() {
        return textBoxEmail.getText();
    }

    @Override
    public void setSaveButtonsEnabled(boolean enabled) {

    }

    @UiHandler({"textBoxFirstName", "textBoxLastName", "textBoxEmail"})
    public void actionOccuredInFormClickHandler(ClickEvent event) {
        getUiHandlers().actionOccurredInForm();
    }

    @UiHandler("buttonCancel")
    public void cancelClickHandler(ClickEvent event) {
        getUiHandlers().cancel();
    }

    @UiHandler("buttonDelete")
    public void deleteClickHandler(ClickEvent event) {
        getUiHandlers().delete();
    }

    @UiHandler("buttonSave")
    public void saveUserClickHandler(ClickEvent event) {
        if(formUserInfo.validate()){
            getUiHandlers().save();
        }
    }
}