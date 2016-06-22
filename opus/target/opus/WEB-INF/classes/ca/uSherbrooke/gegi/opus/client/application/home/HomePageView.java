/*
 * Copyright 2015, 2016 Département de Génie Électrique et Génie Informatique (GEGI) de l'Université de Sherbrooke (UdeS).
 * Tous droits réservés / All rights reserved.
 */

package ca.uSherbrooke.gegi.opus.client.application.home;

import ca.uSherbrooke.gegi.commons.core.shared.entity.GroupData;
import ca.uSherbrooke.gegi.opus.shared.Grading.Course;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.constants.ButtonType;
import org.gwtbootstrap3.client.ui.html.Div;

import java.util.ArrayList;
import java.util.List;

import static com.google.gwt.dom.client.Style.Unit.PX;

public class HomePageView extends ViewWithUiHandlers<HomePageUiHandlers> implements HomePagePresenter.MyView {

    private final Widget widget;

    @UiField Page        panelGroups;
    @UiField HTMLPanel   panelUsers;
    @UiField SimplePager pager;
    @UiField HTMLPanel educationalGoalPanel;

    public interface Binder extends UiBinder<Widget, HomePageView> {}

    @Inject
    public HomePageView(final Binder binder) {
        widget = binder.createAndBindUi(this);
        bindSlot(HomePagePresenter.SLOT_USERS, panelUsers);

        panelGroups.setPager(pager);
    }

    @Override
    public Widget asWidget() {
        return widget;
    }

    public void resetView() {
        panelGroups.getListDataProvider().getList().clear();
        panelUsers.clear();
    }

    public void setListGroup(List<GroupData> listGroup) {
        panelGroups.getListDataProvider().getList().clear();

        for (final GroupData gd : listGroup) {
            Button b = new Button(gd.getLabel());
            b.setType(ButtonType.LINK);
            b.setTitle(gd.getDescription());
            b.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent clickEvent) {
                    getUiHandlers().displayUserMembers(gd.getId());
                }
            });

            panelGroups.getListDataProvider().getList().add(b);
        }
    }

    public void clearUsers(){ panelUsers.clear(); }

    public void setEducationalGoalPanel(ArrayList<String> educationalGoalArray){
        GWT.log(educationalGoalArray.get(0));
        for (String item: educationalGoalArray) {
            GWT.log(item);
            int nbCompEducaGoal = 3;
            int nbEvaluationEducaGoal = 3;

            Div educGoalDiv = new Div();
            educGoalDiv.getElement().getStyle().setBackgroundColor("#FFFFFF");
            //educGoalDiv.getElement().getStyle().setWidth(465, PX);
            educGoalDiv.getElement().getStyle().setMarginTop(10, PX);
            //educGoalDiv.getElement().getStyle().setBorderStyle(Style.BorderStyle.SOLID);
            //educGoalDiv.getElement().getStyle().setBorderWidth(2, PX);

            Label educGoalName = new Label(item);
            educGoalName.getElement().getStyle().setMarginBottom(0,PX);
            educGoalName.getElement().getStyle().setBackgroundColor("#339933");
            educGoalName.getElement().getStyle().setFontSize(16,PX);
            educGoalName.getElement().getStyle().setFontWeight(Style.FontWeight.BOLD);
            educGoalName.getElement().getStyle().setTextIndent(5,PX);
            educGoalName.getElement().getStyle().setHeight(30,PX);

            DisclosurePanel educGoalEvaluationTable = new DisclosurePanel();
            educGoalEvaluationTable.getElement().getStyle().setWidth(500, PX);
            educGoalEvaluationTable.getElement().getStyle().setBorderStyle(Style.BorderStyle.SOLID);
            educGoalEvaluationTable.getElement().getStyle().setBorderWidth(2, PX);

            //educGoalEvaluationTable.setHeader(new Label("\u25B8" + " " + item));

            Grid evaluationGrid = new Grid(nbEvaluationEducaGoal,nbCompEducaGoal+1);
            evaluationGrid.setBorderWidth(3);

            for(int i = 0; i<=nbCompEducaGoal; i++)
            {
                evaluationGrid.getColumnFormatter().setWidth(i,"112");
            }

            for(int j = 0; j<nbEvaluationEducaGoal; j++)
            {
                evaluationGrid.setText(j,0,"Travail " + (j+1));

                for(int k = 1; k<=nbCompEducaGoal; k++)
                {
                    //if(k == nbCompEducaGoal)
                    //    evaluationGrid.setText(j,k,"300/300");
                    //else
                        evaluationGrid.setText(j,k,"100/100");

                    evaluationGrid.getCellFormatter().getElement(j,k).getStyle().setTextAlign(Style.TextAlign.CENTER);
                }
            }

            Grid totalGrid = new Grid(1,nbCompEducaGoal);
            totalGrid.setBorderWidth(3);

            for(int i = 0; i<nbCompEducaGoal; i++)
            {
                //if(i == nbCompEducaGoal-1)
                //    totalGrid.setText(0,i,"Total: " + " 900/900");
                //else
                    totalGrid.setText(0,i,"Comp " + (i+1) + ": " + " 300/300");

                totalGrid.getColumnFormatter().setWidth(i,"112");
            }

            totalGrid.getElement().getStyle().setMarginLeft(30, PX);


            HorizontalPanel totalFinalDiv = new HorizontalPanel();

            Label totalLabel = new Label("900/900");
            totalLabel.getElement().getStyle().setMarginLeft(20,PX);
            totalLabel.getElement().getStyle().setMarginTop(6,PX);
            totalLabel.getElement().getStyle().setFontSize(16,PX);
            totalLabel.getElement().getStyle().setFontWeight(Style.FontWeight.BOLD);

            totalFinalDiv.add(totalLabel);
            totalFinalDiv.add(totalGrid);

            Div headerDiv = new Div();
            headerDiv.add(educGoalName);
            headerDiv.add(totalFinalDiv);

            educGoalEvaluationTable.setHeader(headerDiv);
            educGoalEvaluationTable.setContent(evaluationGrid);

            //educGoalDiv.add(educGoalName);
            //educGoalDiv.add(totalGrid);
            educGoalDiv.add(educGoalEvaluationTable);

            educationalGoalPanel.add(educGoalDiv);
        }




    }
}