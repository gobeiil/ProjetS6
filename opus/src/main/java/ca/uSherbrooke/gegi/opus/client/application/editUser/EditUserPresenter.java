/*
 * Copyright 2015, 2016 Département de Génie Électrique et Génie Informatique (GEGI) de l'Université de Sherbrooke (UdeS).
 * Tous droits réservés / All rights reserved.
 */

package ca.uSherbrooke.gegi.opus.client.application.editUser;

import ca.uSherbrooke.gegi.commons.core.client.accessRestriction.AuthenticationGatekeeper;
import ca.uSherbrooke.gegi.commons.core.client.presenter.application.ApplicationPresenter;
import ca.uSherbrooke.gegi.commons.core.client.utils.AsyncCallbackFailed;
import ca.uSherbrooke.gegi.commons.core.client.widget.confirmation.ConfirmationChoice;
import ca.uSherbrooke.gegi.commons.core.client.widget.confirmation.ConfirmationView;
import ca.uSherbrooke.gegi.commons.core.client.widget.notify.NotifyWrapper;
import ca.uSherbrooke.gegi.commons.core.shared.dispatch.DMLData;
import ca.uSherbrooke.gegi.commons.core.shared.dispatch.DMLData.DMLAction;
import ca.uSherbrooke.gegi.commons.core.shared.dispatch.DMLDataResult;
import ca.uSherbrooke.gegi.commons.core.shared.dispatch.GetUsers;
import ca.uSherbrooke.gegi.commons.core.shared.dispatch.GetUsersResult;
import ca.uSherbrooke.gegi.commons.core.shared.entity.Data;
import ca.uSherbrooke.gegi.commons.core.shared.entity.UserData;
import ca.uSherbrooke.gegi.opus.client.place.NameTokens;
import ca.uSherbrooke.gegi.opus.shared.dispatch.DeleteUser;
import ca.uSherbrooke.gegi.opus.shared.dispatch.DeleteUserResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class EditUserPresenter extends Presenter<EditUserPresenter.MyView, EditUserPresenter.MyProxy> implements EditUserUiHandlers {

    private UserData userData;

    @Inject PlaceManager      placeManager;
    @Inject DispatchAsync     dispatchAsync;
    @Inject ConfirmationView confirmationView;
    @Inject AuthenticationGatekeeper authenticationGatekeeper;

    public interface MyView extends View, HasUiHandlers<EditUserUiHandlers> {
        public void resetView();
        public void setUser(UserData userData);
        public String getFirstName();
        public String getLastName();
        public String getEmailAddress();
        public void setSaveButtonsEnabled(boolean enabled);
    }

    @ProxyStandard
    @NameToken(NameTokens.editUser)
	//@UseGatekeeper(AuthenticationGatekeeper.class)
    public interface MyProxy extends ProxyPlace<EditUserPresenter> {}

    @Inject
    public EditUserPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
        super(eventBus, view, proxy, ApplicationPresenter.SLOT_CONTENT);

        getView().setUiHandlers(this);
    }

    @Override
    protected void onReset() {
        super.onReset();

        userData = null;
        getView().resetView();

        getUserData();
    }

    private void getUserData(){
        List<Integer> listUserId = new ArrayList<>();
        String userIdParameter = placeManager.getCurrentPlaceRequest().getParameter("userId", "0");
        Integer userId = null;

        if(userIdParameter != null && !userIdParameter.isEmpty()){
            if(userIdParameter.matches("-?\\d+")){ // est integer ?
                userId = Integer.parseInt(userIdParameter);
                listUserId.add(userId);
            }
        }

        if(!listUserId.isEmpty()){
            GetUsers action = new GetUsers();
            action.setListUserId(listUserId);
            dispatchAsync.execute(action, getUsersAsyncCallback);
        }
        else{
            NotifyWrapper.notifyDanger("Url mal formaté : userId invalide.");
        }
    }

    @Override
    public void actionOccurredInForm() {
        boolean edited = !userData.getFirstName().equals(getView().getFirstName()) || userData.getLastName().equals(getView().getLastName()) || !userData.getEmailAddress().equals(getView().getEmailAddress());

        if(edited){
            placeManager.setOnLeaveConfirmation(
                    "Les informations non sauvegardées seront perdues. Voulez-vous continuer ?");
        }
        else{
            placeManager.setOnLeaveConfirmation(null);
        }
    }

    @Override
    public void cancel() {
        exit();
    }

    @Override
    public void delete() {
        if(authenticationGatekeeper.canReveal()){
            if(userData != null) {
                confirmationView.yesnoConfirm("Confirmation de suppression", "Désirez-vous supprimer cet usager ?", new ConfirmationChoice("Oui") {
                            @Override
                            public void onAction() {
                                DeleteUser action = new DeleteUser(userData.getId());
                                dispatchAsync.execute(action, deleteUserAsyncCallback);
                            }
                        }, new ConfirmationChoice("Non") {
                            @Override
                            public void onAction() {
                                // DO NOTHING
                            }
                        });
            }
        }
        else{
            NotifyWrapper.notifyDanger("Vous n'êtes pas autorisé à supprimer un usager.");
        }
    }

    @Override
    public void save() {
        List<DMLAction> listAction = new ArrayList<DMLAction>();
        List<Data> listData = new ArrayList<Data>();

        if(authenticationGatekeeper.canReveal()){
            if(userData != null){

            }
            getView().setSaveButtonsEnabled(false);

            userData.setFirstName(getView().getFirstName());
            userData.setLastName(getView().getLastName());
            userData.setEmailAddress(getView().getEmailAddress());

            listAction.add(DMLAction.UPDATE);
            listData.add(userData);

            DMLData action = new DMLData(listAction, listData);
            dispatchAsync.execute(action, dmlDataAsyncCallback);
        }
        else{
            NotifyWrapper.notifyDanger("Vous n'êtes pas autorisé à modifier les informations d'un usager.");
        }
    }

    private void exit(){
        placeManager.navigateBack();
    }

    private AsyncCallback<GetUsersResult> getUsersAsyncCallback = new AsyncCallback<GetUsersResult>() {
        @Override
        public void onSuccess(GetUsersResult result) {
            if (!result.getListResult().isEmpty()) {
                userData = result.getListResult().get(0);
                getView().setUser(userData);
            }
            else {
                NotifyWrapper.notifyDanger("L'usager n'existe pas.");
        }
        }

        @Override
        public void onFailure(Throwable caught) {
            AsyncCallbackFailed.asyncCallbackFailed(caught, "L'usager est inaccessible.", true);
        }
    };

    private AsyncCallback<DeleteUserResult> deleteUserAsyncCallback = new AsyncCallback<DeleteUserResult>() {
        @Override
        public void onSuccess(DeleteUserResult result) {
            NotifyWrapper.notifySuccess("La suppression de l'usager a réussi.");
            getView().setSaveButtonsEnabled(true);
            exit();
        }

        @Override
        public void onFailure(Throwable caught) {
            NotifyWrapper.notifyDanger("La suppression de l'usager a échoué.");
            getView().setSaveButtonsEnabled(true);
        }
    };

    private AsyncCallback<DMLDataResult> dmlDataAsyncCallback = new AsyncCallback<DMLDataResult>() {
        @Override
        public void onSuccess(DMLDataResult result) {
            NotifyWrapper.notifySuccess("La sauvegarde des modifications a réussi.");
            getView().setSaveButtonsEnabled(true);
            exit();
        }

        @Override
        public void onFailure(Throwable caught) {
            NotifyWrapper.notifyDanger("La sauvegarde des modifications a échoué.");
            getView().setSaveButtonsEnabled(true);
        }
    };
}