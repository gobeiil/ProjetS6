/*
 * Copyright 2015, 2016 Département de Génie Électrique et Génie Informatique (GEGI) de l'Université de Sherbrooke (UdeS).
 * Tous droits réservés / All rights reserved.
 */

package ca.uSherbrooke.gegi.opus.client.application.home.sideMenu;

import ca.uSherbrooke.gegi.commons.core.client.presenter.application.ApplicationPresenter;
import ca.uSherbrooke.gegi.commons.core.shared.entity.Data;
import ca.uSherbrooke.gegi.commons.core.shared.utils.Sort;
import ca.uSherbrooke.gegi.opus.client.place.NameTokens;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import org.gwtbootstrap3.client.ui.AnchorListItem;
import org.gwtbootstrap3.client.ui.ListBox;
import org.gwtbootstrap3.client.ui.TextBox;
import org.gwtbootstrap3.client.ui.html.UnorderedList;

import javax.inject.Inject;
import java.util.List;

public class SideMenuView extends ViewWithUiHandlers<SideMenuUiHandlers> implements SideMenuPresenter.MyView {

	private final Widget widget;

	@UiField HTMLPanel panelMenu;
	@UiField UnorderedList ulMenuContent;
	@UiField ListBox listBoxSort;
	@UiField TextBox textBoxFilter;
	@UiField UnorderedList ulSidebar;

	@Inject ApplicationPresenter applicationPresenter;
	
	public interface Binder extends UiBinder<Widget, SideMenuView> {
    }

    @Inject
    public SideMenuView(Binder uiBinder) {
		widget = uiBinder.createAndBindUi(this);

		listBoxSort.addItem(Sort.LATEST_FIRST.toString(), Sort.LATEST_FIRST.toString());
		listBoxSort.addItem(Sort.ALPHABETICAL.toString(), Sort.ALPHABETICAL.toString());
		//listBoxSort.addItem(Sort.MOST_LIKED.toString(), Sort.MOST_LIKED.toString());
		listBoxSort.addItem(Sort.OLDEST_FIRST.toString(), Sort.OLDEST_FIRST.toString());

		listBoxSort.setSelectedIndex(1);
    }

	public void addToApplicationPresenter(){
		applicationPresenter.getView().setInSlot(ApplicationPresenter.SLOT_SIDE_MENU, panelMenu);
		applicationPresenter.getView().setInSlot(ApplicationPresenter.SLOT_SIDEBAR, ulSidebar);
	}
	
	public void setList(List<Data> listData){
		AnchorListItem a;

		ulMenuContent.clear();

		if(listData != null){
			for(Data d : listData){
				a = new AnchorListItem();
				a.setText(d.getLabel());
				a.setTargetHistoryToken(NameTokens.userGroups.replace("{dataId}", d.getId().toString())); // TODO : replace with right nameToken and path parameter or delete if unnecessary

				ulMenuContent.add(a);
			}
		}
	}

	public Sort getSort(){
		return Sort.valueOf(listBoxSort.getSelectedValue());
	}
	
	public String getFilterText(){
		return textBoxFilter.getText();
	}

	@UiHandler("listBoxSort")
	public void sortChangeHandler(ChangeEvent event) {
		getUiHandlers().refreshList();
	}

	@UiHandler("buttonFilter")
	public void filterClickHandler(ClickEvent event) {
		getUiHandlers().refreshList();
	}
}