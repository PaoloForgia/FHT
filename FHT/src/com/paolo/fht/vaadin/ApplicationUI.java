package com.paolo.fht.vaadin;

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
    }
}