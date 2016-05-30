/*
 * Copyright 2015, 2016 Département de Génie Électrique et Génie Informatique (GEGI) de l'Université de Sherbrooke (UdeS).
 * Tous droits réservés / All rights reserved.
 */

package ca.uSherbrooke.gegi.opus.client.application.home.sideMenu;

import ca.uSherbrooke.gegi.commons.core.client.utils.AsyncCallbackFailed;
import ca.uSherbrooke.gegi.commons.core.shared.entity.Data;
import ca.uSherbrooke.gegi.commons.core.shared.utils.Sort;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class SideMenuPresenter extends PresenterWidget<SideMenuPresenter.MyView> implements SideMenuUiHandlers {

	@Inject DispatchAsync dispatchAsync;
	
	public interface MyView extends View, HasUiHandlers<SideMenuUiHandlers> {
		public void setList(List<Data> listData);
		public Sort getSort();
		public String getFilterText();
		public void addToApplicationPresenter();
    }

    @Inject
    public SideMenuPresenter(EventBus eventBus, MyView view) {
        super(eventBus, view);

        getView().setUiHandlers(this);
    }

	@Override
	public void refreshList(){ // TODO : uncomment and edit with existing action and result or delete if unnecessary
		/*GetData action = new GetData();
		action.setSort(getView().getSort());
		action.setFilterText(getView().getFilterText());
		dispatchAsync.execute(action, new AsyncCallback<GetData>() {
			@Override
			public void onSuccess(DataResult result) {
				getView().setList(new ArrayList<Data>());
			}

			@Override
			public void onFailure(Throwable caught) {
				AsyncCallbackFailed.asyncCallbackFailed(caught, "La liste des data est inaccessible.", true);
			}
		});*/
	}
}