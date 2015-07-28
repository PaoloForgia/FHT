package com.paolo.fht.vaadin.quick;

import com.vaadin.ui.Component;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.Runo;

public class QuickPopupWindow
	extends Window {

    private static final long serialVersionUID = 1L;

    public QuickPopupWindow(String caption, Component content) {
	super(caption);
	center();
	setModal(true);
	setResizable(false);
	setWidth("-1");
	setHeight("-1");
	addStyleName(Runo.PANEL_LIGHT);
	if (content != null)
	    setContent(content);
    }

    public final QuickPopupWindow show() {
	UI.getCurrent().addWindow(this);
	return this;
    }
}
