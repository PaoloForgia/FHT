package com.paolo.fht.vaadin.quick;

import com.vaadin.ui.TextField;

public class QuickTextField
	extends TextField {

    private static final long serialVersionUID = 1L;

    public QuickTextField(String caption, String value, boolean required) {
	super(caption, value);
	setWidth("300");
	setRequired(required);
	setImmediate(true);
    }

    public QuickTextField(String caption, String value) {
	this(caption, value, false);
    }
}
