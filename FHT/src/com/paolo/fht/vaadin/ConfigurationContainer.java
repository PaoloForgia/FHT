package com.paolo.fht.vaadin;

import com.paolo.fht.core.FHTLoader;
import com.paolo.fht.vaadin.quick.QuickVerticalLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TabSheet;

public class ConfigurationContainer
	extends QuickVerticalLayout {

    private static final long serialVersionUID = 1L;
    private final TabSheet tabSheet;

    public ConfigurationContainer() {
	super(false);
	tabSheet = new TabSheet();
	init();
    }

    private void init() {
	removeAllComponents();
	addComponent(tabSheet);
	tabSheet.removeAllComponents();
	tabSheet.addTab(getFileSystemTab(), "Filesystem");
    }

    private FormLayout getFileSystemTab() {
	ConfigurationFormFS form = new ConfigurationFormFS();
	return form;
    }

    public FHTLoader getLoader() throws Exception {
	return ((ConfigurationForm) tabSheet.getSelectedTab()).getLoader();
    }
}
