package com.paolo.fht.vaadin;

import com.paolo.fht.vaadin.quick.QuickVerticalLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TabSheet;

class ConfigurationForm
	extends QuickVerticalLayout {

    private static final long serialVersionUID = 1L;
    private final TabSheet tabSheet;

    protected ConfigurationForm() {
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
	FormLayout form = new FormLayout();
	UploadLayout upload = new UploadLayout("Root select");
	form.addComponent(upload);
	return form;
    }
}
