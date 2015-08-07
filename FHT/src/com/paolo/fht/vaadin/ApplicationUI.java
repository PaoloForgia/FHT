package com.paolo.fht.vaadin;

import com.paolo.fht.core.FHTComparison;
import com.paolo.fht.core.FHTHierarchy;
import com.paolo.fht.vaadin.quick.QuickHorizontalLayout;
import com.paolo.fht.vaadin.quick.QuickLabel;
import com.paolo.fht.vaadin.quick.QuickNotification;
import com.vaadin.annotations.Theme;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Theme("fht")
public class ApplicationUI
	extends UI {

    private static final long serialVersionUID = 1L;

    public ApplicationUI() {
	super();
    }

    @Override
    protected void init(VaadinRequest request) {
	final VerticalLayout layout = new VerticalLayout();
	layout.setMargin(true);
	layout.setSpacing(true);
	setContent(layout);
	layout.addComponent(new QuickLabel("Files Hierarchy Tool", "fht-title"));
	QuickHorizontalLayout formContent = new QuickHorizontalLayout(false);
	layout.addComponent(formContent);
	final ConfigurationContainer formLeft = new ConfigurationContainer();
	formLeft.setStyleName("fht-rightBorderContainer");
	formContent.addComponent(formLeft);
	final ConfigurationContainer formRight = new ConfigurationContainer();
	formRight.setStyleName("fht-leftBorderContainer");
	formContent.addComponent(formRight);
	Button compareButton = new Button("Compare", FontAwesome.ARROWS_H);
	layout.addComponent(compareButton);
	layout.setComponentAlignment(compareButton, Alignment.MIDDLE_CENTER);
	compareButton.addClickListener(new ClickListener() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void buttonClick(ClickEvent event) {
		try {
		    FHTHierarchy h1 = new FHTHierarchy(formLeft.getLoader());
		    FHTHierarchy h2 = new FHTHierarchy(formRight.getLoader());
		    h1.load();
		    QuickNotification.tray("Loaded left configuration");
		    System.out.println("Loaded left configuration");
		    h2.load();
		    QuickNotification.tray("Loaded right configuration");
		    System.out.println("Loaded right configuration");
		    new FHTComparison().compare(h1, h2);
		    System.out.println("Comparative successfully");
		    ComparatorUI.compareByLoaders(h1, h2);
		    getPage().setLocation("/FHT/comparator");
		} catch (Exception e) {
		    QuickNotification.exception(e.getMessage());
		    e.printStackTrace();
		}
	    }
	});
	layout.addComponent(new Label("/Users/paoloforgia/Documents/Developer/Server/jboss-eap-6.3"));
    }
}