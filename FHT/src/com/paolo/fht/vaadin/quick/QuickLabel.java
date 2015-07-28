package com.paolo.fht.vaadin.quick;

import com.vaadin.ui.Label;

public class QuickLabel
	extends Label {

    private static final long serialVersionUID = 1L;

    public QuickLabel(String value, String styleName) {
	super(value);
	setStyleName(styleName);
    }

    public QuickLabel(String value) {
	this(value, null);
    }
}
