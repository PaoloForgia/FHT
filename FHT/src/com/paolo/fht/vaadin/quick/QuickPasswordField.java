package com.paolo.fht.vaadin.quick;

import com.vaadin.ui.PasswordField;

public class QuickPasswordField
	extends PasswordField {

    private static final long serialVersionUID = 1L;

    public QuickPasswordField(String caption, String value, boolean required) {
	super(caption, value);
	setWidth("300");
	setRequired(required);
    }

    public QuickPasswordField(String caption, String value) {
	this(caption, value, true);
    }
}
