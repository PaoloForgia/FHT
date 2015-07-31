package com.paolo.fht.vaadin;

import com.paolo.fht.vaadin.NodeBrowser.BrowserFilter;
import com.paolo.fht.vaadin.quick.QuickButton;
import com.paolo.fht.vaadin.quick.QuickHorizontalLayout;
import com.paolo.fht.vaadin.quick.QuickPopupWindow;
import com.paolo.fht.vaadin.quick.QuickTextField;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;

class UploadLayout
	extends QuickHorizontalLayout {

    private static final long serialVersionUID = 1L;
    private final QuickTextField pathField;
    private final QuickButton browseButton;

    protected UploadLayout(String caption) {
	super(false);
	pathField = new QuickTextField(caption, "");
	browseButton = new QuickButton("Browse");
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
		final NodeBrowser nodeBrowser = new NodeBrowser("Root browser", "Select root", BrowserFilter.folder_only_no_hidden);
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
}
