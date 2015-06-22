package com.paolo.fht.vaadin;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinServlet;

@WebServlet(value = "/*", asyncSupported = true)
@VaadinServletConfiguration(productionMode = false, ui = ApplicationUI.class, widgetset = "com.paolo.fht.widgetset.FhtWidgetset")
public class ApplicationServlet
	extends VaadinServlet {

    private static final long serialVersionUID = 1L;
}