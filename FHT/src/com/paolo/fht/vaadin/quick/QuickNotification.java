package com.paolo.fht.vaadin.quick;

import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

public class QuickNotification {

    public QuickNotification() {
	super();
    }

    public static void exception(String message) {
	Notification.show(message, Type.ERROR_MESSAGE);
    }

    public static void tray(String message) {
	Notification.show(message, Type.TRAY_NOTIFICATION);
    }

    public static void humanized(String message) {
	Notification.show(message, Type.HUMANIZED_MESSAGE);
    }

    public static void warning(String message) {
	Notification.show(message, Type.WARNING_MESSAGE);
    }
}
