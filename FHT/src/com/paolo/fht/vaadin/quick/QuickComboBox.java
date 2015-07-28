package com.paolo.fht.vaadin.quick;

import java.util.List;

import com.vaadin.ui.ComboBox;

public class QuickComboBox
	extends ComboBox {

    private static final long serialVersionUID = 1L;

    public QuickComboBox(String caption, List<String> items, boolean nullSelectionAllowed, String width) {
	super(caption, items);
	setNullSelectionAllowed(nullSelectionAllowed);
	if (!nullSelectionAllowed)
	    select(items.get(0));
	setWidth(width);
    }

    public QuickComboBox(String caption, List<String> items, String width) {
	this(caption, items, true, width);
    }

    public QuickComboBox(String caption, List<String> items, boolean nullSelectionAllowed) {
	this(caption, items, nullSelectionAllowed, "250");
    }

    public QuickComboBox(String caption, List<String> items) {
	this(caption, items, true);
    }
}
