/*
 * Copyright 2015, 2016 Département de Génie Électrique et Génie Informatique (GEGI) de l'Université de Sherbrooke (UdeS).
 * Tous droits réservés / All rights reserved.
 */

package ca.uSherbrooke.gegi.opus.client.application.home;

import ca.uSherbrooke.gegi.commons.core.client.accessRestriction.Parameters;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maip2202 on 2016-05-31.
 */
public class Page extends HTMLPanel implements HasRows, HasData<Widget> {

    private static final Integer DEFAULT_MAX_NUMBER_OF_GROUPS_DISPLAYED = 2;

    private ListDataProvider<Widget> dataProvider = new ListDataProvider<Widget>();
    private SimplePager pager;
    private Range       range;
    private HTMLPanel panel = new HTMLPanel("");

    public Page(String html) {
        super(html);
        init();
    }

    public Page(SafeHtml safehtml) {
        super(safehtml);
        init();
    }

    public Page(String tag, String html) {
        super(tag, html);
        init();
    }

    private void init() {
        Integer pageSize = null;
        String strPageSize = Parameters.getInstance().getParameterValue("MAX_NUMBER_OF_GROUPS_DISPLAYED");

        if (strPageSize != null) {
            try {
                pageSize = Integer.parseInt(strPageSize);
            }
            catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        if (pageSize == null) {
            pageSize = DEFAULT_MAX_NUMBER_OF_GROUPS_DISPLAYED;
        }

        range = new Range(0, pageSize);

        dataProvider.addDataDisplay(this);

        add(panel);
    }

    public void setPager(SimplePager pager){
        this.pager = pager;
    }

    public ListDataProvider getListDataProvider() {
        return dataProvider;
    }

    @Override
    public SelectionModel<? super Widget> getSelectionModel() {
        return null; // UNSUPPORTED
    }

    @Override
    public Widget getVisibleItem(int indexOnPage) {
        return panel.getWidget(indexOnPage);
    }

    @Override
    public int getVisibleItemCount() {
        return panel.getWidgetCount();
    }

    @Override
    public Iterable<Widget> getVisibleItems() {
        List<Widget> list = new ArrayList<Widget>();

        for (int i = range.getStart() ; i < range.getStart() + range.getLength() && dataProvider.getList().size() > i ; i++){
            list.add(dataProvider.getList().get(i));
        }

        return list;
    }

    @Override
    public void setRowData(int start, List<? extends Widget> values) {
        panel.clear();

        for(int i = 0 ; i < values.size() ; i++){
            panel.add(values.get(i));
        }

        pager.setDisplay(this);
    }

    @Override
    public void setSelectionModel(SelectionModel<? super Widget> selectionModel) {
        // UNSUPPORTED
    }

    @Override
    public void setVisibleRangeAndClearData(Range range, boolean forceRangeChangeEvent) {
        this.range = range;

        panel.clear();

        for(int i = range.getStart() ; i < range.getStart() + range.getLength() ; i++){
            panel.add(dataProvider.getList().get(i));
        }
    }

    @Override
    public HandlerRegistration addCellPreviewHandler(CellPreviewEvent.Handler<Widget> handler) {
        return addHandler(handler, CellPreviewEvent.getType());
    }

    @Override
    public HandlerRegistration addRangeChangeHandler(RangeChangeEvent.Handler handler) {
        return addHandler(handler, RangeChangeEvent.getType());
    }

    @Override
    public HandlerRegistration addRowCountChangeHandler(RowCountChangeEvent.Handler handler) {
        return addHandler(handler, RowCountChangeEvent.getType());
    }

    @Override
    public int getRowCount() {
        return dataProvider.getList().size();
    }

    @Override
    public Range getVisibleRange() {
        return range;
    }

    @Override
    public boolean isRowCountExact() {
        return true;
    }

    @Override
    public void setRowCount(int count) {

    }

    @Override
    public void setRowCount(int count, boolean isExact) {

    }

    @Override
    public void setVisibleRange(int start, int length) {
        range = new Range(start, length);
        dataProvider.refresh();
    }

    @Override
    public void setVisibleRange(Range range) {
        this.range = range;
        dataProvider.refresh();
    }

    @Override
    public void fireEvent(GwtEvent<?> event) {
        super.fireEvent(event);
    }
}
