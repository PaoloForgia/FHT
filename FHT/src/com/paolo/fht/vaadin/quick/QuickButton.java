package com.paolo.fht.vaadin.quick;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;

public class QuickButton
	extends Button {

    private static final long serialVersionUID = 1L;

    public QuickButton(String text) {
	super(text);
	setWidth("200");
    }

    public QuickButton(String text, boolean enabled) {
	this(text);
	setEnabled(enabled);
    }

    public QuickButton(String text, ClickListener clickListener) {
	this(text);
	addClickListener(clickListener);
    }

    public QuickButton(String text, FontAwesome icon) {
	this(text);
	setIcon(icon);
    }
}
