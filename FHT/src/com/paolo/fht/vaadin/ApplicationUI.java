package com.paolo.fht.vaadin;

import com.paolo.fht.vaadin.quick.QuickHorizontalLayout;
import com.paolo.fht.vaadin.quick.QuickLabel;
import com.vaadin.annotations.Theme;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
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
	layout.setSpacing(true);
	setContent(layout);
	layout.addComponent(new QuickLabel("Files Hierarchy Tool", "fht-title"));
	QuickHorizontalLayout formContent = new QuickHorizontalLayout(false);
	layout.addComponent(formContent);
	ConfigurationForm formLeft = new ConfigurationForm();
	formLeft.setStyleName("fht-formLeft");
	formContent.addComponent(formLeft);
	ConfigurationForm formRight = new ConfigurationForm();
	formRight.setStyleName("fht-formRight");
	formContent.addComponent(formRight);
	Button compareButton = new Button("Compare", FontAwesome.ARROWS_H);
	layout.addComponent(compareButton);
	layout.setComponentAlignment(compareButton, Alignment.MIDDLE_CENTER);
	compareButton.addClickListener(new ClickListener() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void buttonClick(ClickEvent event) {
		// TODO
	    }
	});
    }
}