package com.paolo.fht.vaadin;

import com.paolo.fht.vaadin.quick.QuickHorizontalLayout;
import com.paolo.fht.vaadin.quick.QuickLabel;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Theme("fht")
public class ApplicationUI
	extends UI {

    private static final long serialVersionUID = 1L;

    @Override
    protected void init(VaadinRequest request) {
	final VerticalLayout layout = new VerticalLayout();
	layout.setMargin(true);
	setContent(layout);
	layout.addComponent(new QuickLabel("Files Hierarchy Tool", "fht-title"));
	QuickHorizontalLayout formContent = new QuickHorizontalLayout(false);
	layout.addComponent(formContent);
	ConfigurationForm formLeft = new ConfigurationForm();
	formContent.addComponent(formLeft);
	ConfigurationForm formRight = new ConfigurationForm();
	formContent.addComponent(formRight);
    }
}