package com.paolo.fht.vaadin.quick;

import com.vaadin.ui.Field;

public class QuickBuildAndBind {

    public static Field<?> quickFormat(Field<?> field) {
	field.setWidth("300");
	field.setRequired(true);
	return field;
    }
}
