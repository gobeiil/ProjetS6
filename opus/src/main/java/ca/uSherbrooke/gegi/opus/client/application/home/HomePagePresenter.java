/*
 * Copyright 2015, 2016 Département de Génie Électrique et Génie Informatique (GEGI) de l'Université de Sherbrooke (UdeS).
 * Tous droits réservés / All rights reserved.
 */

package ca.uSherbrooke.gegi.opus.client.application.home;

import javax.inject.Inject;

import ca.uSherbrooke.gegi.commons.core.client.accessRestriction.AuthenticationGatekeeper;
import ca.uSherbrooke.gegi.commons.core.client.presenter.application.ApplicationPresenter;
import ca.uSherbrooke.gegi.commons.core.client.utils.AsyncCallbackFailed;
import ca.uSherbrooke.gegi.commons.core.shared.dispatch.GetGroups;
import ca.uSherbrooke.gegi.commons.core.shared.dispatch.GetGroupsResult;
import ca.uSherbrooke.gegi.commons.core.shared.dispatch.GetUsers;
import ca.uSherbrooke.gegi.commons.core.shared.dispatch.GetUsersResult;
import ca.uSherbrooke.gegi.commons.core.shared.entity.GroupData;
import ca.uSherbrooke.gegi.commons.core.shared.entity.UserData;
import ca.uSherbrooke.gegi.opus.client.application.home.sideMenu.SideMenuPresenter;
import ca.uSherbrooke.gegi.opus.client.application.home.user.UserWidgetPresenter;
import ca.uSherbrooke.gegi.opus.client.place.NameTokens;

import ca.uSherbrooke.gegi.opus.shared.dispatch.GetGradingResult;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Provider;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.common.client.IndirectProvider;
import com.gwtplatform.common.client.StandardProvider;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.presenter.slots.Slot;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

import java.util.ArrayList;
import java.util.List;

public class HomePagePresenter extends Presenter<HomePagePresenter.MyView, HomePagePresenter.MyProxy> implements HomePageUiHandlers {

    public static final Slot SLOT_USERS = new Slot();
    public final IndirectProvider<UserWidgetPresenter> provider;
    private List<UserData> listUser;

    @Inject DispatchAsync dispatchAsync;
    @Inject SideMenuPresenter sideMenuPresenter;

    public interface MyView extends View, HasUiHandlers<HomePageUiHandlers> {
        public void setListGroup(List<GroupData> listGroup);
        public void clearUsers();
        public void resetView();
    }

    @ProxyStandard
    @NameToken(NameTokens.userGroups)
	//@UseGatekeeper(AuthenticationGatekeeper.class)
    public interface MyProxy extends ProxyPlace<HomePagePresenter> {
    }

    @Inject
    public HomePagePresenter(EventBus eventBus, MyView view, MyProxy proxy, Provider<UserWidgetPresenter> provider) {
        super(eventBus, view, proxy, ApplicationPresenter.SLOT_CONTENT);

        this.provider = new StandardProvider<UserWidgetPresenter>(provider);

        getView().setUiHandlers(this);

        listUser = new ArrayList<UserData>();
    }

    @Override
    protected void onReset() {
        super.onReset();

        sideMenuPresenter.getView().addToApplicationPresenter();
        sideMenuPresenter.refreshList();

        listUser.clear();
        getView().resetView();

        GetGroups action = new GetGroups();
        action.setRetrieveAdministratedGroups(true);
        action.setRetrieveMemberGroups(true);
        dispatchAsync.execute(action, getGroupsAsyncCallback);
    }

    @Override
    public void displayUserMembers(final Integer groupId) {
        getView().clearUsers();
        listUser.clear();

        GetUsers action = new GetUsers();
        action.setGroupIdToRetrieveUsersFrom(groupId);
        action.setRetrieveDirectUserMembersOfGroup(true);
        dispatchAsync.execute(action, new AsyncCallback<GetUsersResult>() {
            @Override
            public void onSuccess(GetUsersResult result) {
                for (final UserData ud : result.getListResult()) {
                    provider.get(new AsyncCallback<UserWidgetPresenter>() {
                        @Override
                        public void onSuccess(UserWidgetPresenter userWidgetPresenter) {
                            userWidgetPresenter.setUserData(ud, true);
                            listUser.add(ud);
                            addToSlot(SLOT_USERS, userWidgetPresenter);
                        }

                        @Override
                        public void onFailure(Throwable throwable) {

                        }
                    });
                }

                GetUsers action = new GetUsers();
                action.setGroupIdToRetrieveUsersFrom(groupId);
                action.setRetrieveIndirectUserMembersOfGroup(true);
                dispatchAsync.execute(action, getIndirectUsersResultAsyncCallback);
            }

            @Override
            public void onFailure(Throwable throwable) {
                AsyncCallbackFailed.asyncCallbackFailed(throwable, "La liste des usagers membres du groupe est inaccessible.");
            }
        });
    }

    private AsyncCallback<GetGroupsResult> getGroupsAsyncCallback = new AsyncCallback<GetGroupsResult>() {
        @Override
        public void onSuccess(GetGroupsResult result) {
            getView().setListGroup(result.getListResult());
        }

        @Override
        public void onFailure(Throwable throwable) {
            AsyncCallbackFailed.asyncCallbackFailed(throwable, "La liste des groupes est inaccessible.");
        }
    };

    private AsyncCallback<GetGradingResult> getGradingAsyncCallback = new AsyncCallback<GetGradingResult>() {
        @Override
        public void onSuccess(GetGradingResult result) {
            ///TODO
        }

        @Override
        public void onFailure(Throwable throwable) {
            AsyncCallbackFailed.asyncCallbackFailed(throwable, "failed to get grades for student.");
        }
    };

    private AsyncCallback<GetUsersResult> getIndirectUsersResultAsyncCallback = new AsyncCallback<GetUsersResult>() {
        @Override
        public void onSuccess (GetUsersResult result){
            for (final UserData ud : result.getListResult()) {
                provider.get(new AsyncCallback<UserWidgetPresenter>() {
                    @Override
                    public void onSuccess(UserWidgetPresenter userWidgetPresenter) {
                        if(!listUser.contains(ud)){
                            userWidgetPresenter.setUserData(ud, false);
                            listUser.add(ud);
                            addToSlot(SLOT_USERS, userWidgetPresenter);
                        }
                    }

                    @Override
                    public void onFailure(Throwable throwable) {

                    }
                });
            }
        }

        @Override
        public void onFailure (Throwable throwable){
            AsyncCallbackFailed.asyncCallbackFailed(throwable, "La liste des usagers membres du groupe est inaccessible.");
        }
    };
}