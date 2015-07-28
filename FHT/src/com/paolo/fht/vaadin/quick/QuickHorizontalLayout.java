package com.paolo.fht.vaadin.quick;

import com.vaadin.ui.HorizontalLayout;

public class QuickHorizontalLayout
	extends HorizontalLayout {

    private static final long serialVersionUID = 1L;

    public QuickHorizontalLayout() {
	this(true);
    }

    public QuickHorizontalLayout(boolean margin) {
	this(margin, true);
    }

    public QuickHorizontalLayout(boolean margin, boolean spacing) {
	super();
	setSizeFull();
	setMargin(margin);
	setSpacing(spacing);
    }
}
