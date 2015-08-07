package com.paolo.fht.vaadin;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinServlet;

@WebServlet(value = "/comparator/*", asyncSupported = true)
@VaadinServletConfiguration(productionMode = false, ui = ComparatorUI.class, widgetset = "com.paolo.fht.widgetset.FhtWidgetset")
public class ComparatorServlet
	extends VaadinServlet {

    private static final long serialVersionUID = 1L;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
	super.init(servletConfig);
    }
}
