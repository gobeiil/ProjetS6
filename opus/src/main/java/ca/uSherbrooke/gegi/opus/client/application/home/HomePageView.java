/*
 * Copyright 2015, 2016 Département de Génie Électrique et Génie Informatique (GEGI) de l'Université de Sherbrooke (UdeS).
 * Tous droits réservés / All rights reserved.
 */

package ca.uSherbrooke.gegi.opus.client.application.home;

import ca.uSherbrooke.gegi.commons.core.shared.entity.GroupData;
import ca.uSherbrooke.gegi.opus.shared.Grading.*;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.Tooltip;
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
    @UiField HTMLPanel sessionDropdown;

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

    public void setEducationalGoalPanel(SessionGrading educationalGoalArray){



        for (AP item: educationalGoalArray.getAPList()) {
            int nbCompEducaGoal = item.getNumberOfCompetencies();
            //int nbEvaluationEducaGoal = 3;

            Div educGoalDiv = new Div();
            educGoalDiv.getElement().getStyle().setBackgroundColor("#FFFFFF");
            //educGoalDiv.getElement().getStyle().setWidth(465, PX);
            educGoalDiv.getElement().getStyle().setMarginTop(10, PX);
            //educGoalDiv.getElement().getStyle().setBorderStyle(Style.BorderStyle.SOLID);
            //educGoalDiv.getElement().getStyle().setBorderWidth(2, PX);

            Label educGoalName = new Label(item.getLabel() + "-" + item.getCourseName());
            educGoalName.getElement().getStyle().setPaddingTop(4, PX);
            educGoalName.getElement().getStyle().setBackgroundColor("#2D9D5F");
            educGoalName.getElement().getStyle().setFontSize(16, PX);
            educGoalName.getElement().getStyle().setFontWeight(Style.FontWeight.BOLD);
            educGoalName.getElement().getStyle().setTextIndent(5, PX);
            educGoalName.getElement().getStyle().setHeight(30, PX);

            DisclosurePanel educGoalEvaluationTable = new DisclosurePanel();
            educGoalEvaluationTable.getElement().getStyle().setWidth(575, PX);
            educGoalEvaluationTable.getElement().getStyle().setBorderStyle(Style.BorderStyle.SOLID);
            educGoalEvaluationTable.getElement().getStyle().setBorderWidth(0.5, PX);
            educGoalEvaluationTable.getElement().getStyle().setBorderColor("#000000");

            ArrayList<Travail> travailList = item.getTravails();


            Grid evaluationGrid = new Grid(travailList.size() + 1, nbCompEducaGoal + 2);
            evaluationGrid.setBorderWidth(1);

            ArrayList<String> descriptionList = new ArrayList<>();
            descriptionList.add(0,"Travail");
            descriptionList.add(1,"Total");
            descriptionList.add(2,"Compétence 1");
            descriptionList.add(3,"Compétence 2");
            descriptionList.add(4,"Compétence 3");

            for (int i = 0; i <= nbCompEducaGoal + 1; i++) {
                evaluationGrid.getColumnFormatter().setWidth(i, "112");
                evaluationGrid.getCellFormatter().getElement(0, i).getStyle().setTextAlign(Style.TextAlign.CENTER);
                evaluationGrid.setText(0,i,descriptionList.get(i));
            }

            //Grille de note
            for (int j = 1; j < travailList.size() + 1; j++) {

                //Temporaire
                int totalTravail = 0;
                int totalTravailPond = 0;

                Travail travail = travailList.get(j-1);
                ArrayList<BoxScore> boxScores = travail.getBoxScoreArrayList();
                evaluationGrid.setText(j, 0, travail.getName());

                for (int k = 2; k <= boxScores.size() + 1; k++) {
                    BoxScore boxScore = boxScores.get(k-2);
                    evaluationGrid.getCellFormatter().getElement(j, k).getStyle().setTextAlign(Style.TextAlign.CENTER);

                    if(boxScore.getAverage() == -1) {
                        evaluationGrid.getCellFormatter().getElement(j, k).getStyle().setColor("#a6a6a6");
                        evaluationGrid.setText(j, k, " " + boxScore.getPonderation());
                    }
                    else {
                        String tooltipString = new String();
                        tooltipString = "Moyenne: " + boxScore.getAverage() + "| Ecart-Type: " + boxScore.getStandardDeviation();

                        Tooltip tooltip = new Tooltip();

                        tooltip.setTitle(tooltipString);
                        tooltip.setWidget(new Label(boxScore.getGrade() + "/" + boxScore.getPonderation()));

                        //Temporaire
                        totalTravail += boxScore.getGrade();

                        evaluationGrid.setWidget(j, k, tooltip);
                    }

                    //Temporaire
                    totalTravailPond += boxScore.getPonderation();
                }

                //TOTAL TRAVAIL
                evaluationGrid.getCellFormatter().getElement(j, 1).getStyle().setTextAlign(Style.TextAlign.CENTER);

                String tooltipStringTotal = new String();
                tooltipStringTotal = "Moyenne: 95 | Ecart-Type: 11";

                Tooltip tooltipTotal = new Tooltip();

                tooltipTotal.setTitle(tooltipStringTotal);

                //Temporaire
                if(totalTravail == 0) {
                    Label totalLabel = new Label();
                    totalLabel.getElement().getStyle().setColor("#a6a6a6");
                    totalLabel.setText("" + totalTravailPond);
                    tooltipTotal.setWidget(totalLabel);
                }
                else{
                    tooltipTotal.setWidget(new Label(totalTravail + "/" + totalTravailPond));
                }

                evaluationGrid.setWidget(j,1,tooltipTotal);

                //END TOTAL TRAVAIL
            }

            Grid totalGrid = new Grid(1, nbCompEducaGoal);
            totalGrid.setBorderWidth(1);

            //Grille de competence
            for (int i = 0; i < nbCompEducaGoal; i++) {
                totalGrid.getColumnFormatter().setWidth(i, "149");
                totalGrid.getCellFormatter().getElement(0, i).getStyle().setTextAlign(Style.TextAlign.CENTER);
                totalGrid.getCellFormatter().getElement(0, i).getStyle().setFontSize(14, PX);
                totalGrid.getCellFormatter().getElement(0, i).getStyle().setFontWeight(Style.FontWeight.BOLD);

                String tooltipString = new String();
                tooltipString = "Moyenne: 250 | Ecart-Type: 25";

                Tooltip tooltip = new Tooltip();

                tooltip.setTitle(tooltipString);
                tooltip.setWidget(new Label("C" + (i + 1) + ": " + " 300/300"));

                totalGrid.setWidget(0,i,tooltip);
            }

            totalGrid.getElement().getStyle().setMarginLeft(23, PX);
            totalGrid.getElement().getStyle().setHeight(35, PX);

            HorizontalPanel totalFinalDiv = new HorizontalPanel();

            Label totalLabel = new Label("900/900");
            totalLabel.getElement().getStyle().setMarginLeft(33, PX);
            totalLabel.getElement().getStyle().setMarginTop(6, PX);
            totalLabel.getElement().getStyle().setFontSize(16, PX);
            totalLabel.getElement().getStyle().setFontWeight(Style.FontWeight.BOLD);
            totalLabel.getElement().getStyle().setHeight(35, PX);

            String tooltipStringTotal = new String();
            tooltipStringTotal = "Moyenne: 850 | Ecart-Type: 25";

            Tooltip tooltipTotal = new Tooltip();

            tooltipTotal.setTitle(tooltipStringTotal);
            tooltipTotal.setWidget(totalLabel);

            totalFinalDiv.add(tooltipTotal);
            totalFinalDiv.add(totalGrid);
            totalFinalDiv.getElement().getStyle().setMarginTop(10, PX);
            totalFinalDiv.getElement().getStyle().setMarginBottom(3, PX);
            totalFinalDiv.getElement().getStyle().setHeight(40, PX);

            Div headerDiv = new Div();
            headerDiv.add(educGoalName);
            headerDiv.add(totalFinalDiv);
            headerDiv.getElement().getStyle().setColor("white");

            educGoalEvaluationTable.setHeader(headerDiv);
            educGoalEvaluationTable.setContent(evaluationGrid);

            educGoalEvaluationTable.getContent().getElement().getStyle().setBorderWidth(0, PX);
            educGoalEvaluationTable.getContent().getElement().getStyle().setBorderColor("black");

            //educGoalDiv.add(educGoalName);
            //educGoalDiv.add(totalGrid);
            educGoalDiv.add(educGoalEvaluationTable);

            educationalGoalPanel.add(educGoalDiv);
        }

    }
    public void setSessionDropdown(ArrayList<String> sessionArray) {

        // Make a new list box, adding a few items to it.
        ListBox listBox1 = new ListBox();
        listBox1.addItem("First");
        listBox1.addItem("Second");
        listBox1.addItem("Third");
        listBox1.addItem("Fourth");
        listBox1.addItem("Fifth");


        // Make enough room for all five items
        listBox1.setVisibleItemCount(1);

        // Add listboxes to the root panel.
        VerticalPanel panel = new VerticalPanel();
        panel.setSpacing(10);
        panel.add(listBox1);

        sessionDropdown.add(panel);
    }
}