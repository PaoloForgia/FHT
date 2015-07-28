package com.paolo.fht.vaadin.quick;

import com.vaadin.ui.VerticalLayout;

public class QuickVerticalLayout
	extends VerticalLayout {

    private static final long serialVersionUID = 1L;

    public QuickVerticalLayout() {
	this(true);
    }

    public QuickVerticalLayout(boolean margin) {
	this(margin, true);
    }

    public QuickVerticalLayout(boolean margin, boolean spacing) {
	super();
	setSizeFull();
	setMargin(margin);
	setSpacing(spacing);
    }
}
