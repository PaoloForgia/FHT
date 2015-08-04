package com.paolo.fht.vaadin;

import com.paolo.fht.vaadin.NodeBrowserWindow.BrowserFilter;
import com.paolo.fht.vaadin.quick.QuickButton;
import com.paolo.fht.vaadin.quick.QuickHorizontalLayout;
import com.paolo.fht.vaadin.quick.QuickPopupWindow;
import com.paolo.fht.vaadin.quick.QuickTextField;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;

public class NodeBrowserLayout
	extends QuickHorizontalLayout {

    private static final long serialVersionUID = 1L;
    private final QuickTextField pathField;
    private final QuickButton browseButton;

    public NodeBrowserLayout(String caption) {
	super(false);
	pathField = new QuickTextField(caption, "");
	browseButton = new QuickButton("Browse", FontAwesome.FOLDER_OPEN);
	init();
    }

    private void init() {
	removeAllComponents();
	addComponent(pathField);
	addComponent(browseButton);
	setComponentAlignment(browseButton, Alignment.BOTTOM_LEFT);
	browseButton.addClickListener(new ClickListener() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void buttonClick(ClickEvent event) {
		final NodeBrowserWindow nodeBrowser = new NodeBrowserWindow("Root browser", "Select root", pathField.getValue(), BrowserFilter.folder_only_no_hidden);
		QuickPopupWindow window = nodeBrowser.show();
		window.addCloseListener(new CloseListener() {

		    private static final long serialVersionUID = 1L;

		    @Override
		    public void windowClose(CloseEvent e) {
			pathField.setValue(nodeBrowser.getSelectedNode());
		    }
		});
		nodeBrowser.getSelectedNode();
	    }
	});
    }

    public String getPath() {
	return pathField.getValue();
    }
}
