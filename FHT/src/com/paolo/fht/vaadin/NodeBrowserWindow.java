package com.paolo.fht.vaadin;

import java.io.File;
import java.io.FileNotFoundException;

import com.paolo.fht.vaadin.quick.QuickButton;
import com.paolo.fht.vaadin.quick.QuickHorizontalLayout;
import com.paolo.fht.vaadin.quick.QuickNotification;
import com.paolo.fht.vaadin.quick.QuickPopupWindow;
import com.paolo.fht.vaadin.quick.QuickVerticalLayout;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;

public class NodeBrowserWindow
	extends QuickVerticalLayout {

    private static final long serialVersionUID = 1L;
    private final String windowCaption;
    private final String buttonCaption;
    private final QuickVerticalLayout nodeListnerLayout;
    private final QuickHorizontalLayout currentNodePathLayout;
    private String currentPath;
    private QuickPopupWindow window;
    private final BrowserFilter filter;
    private final OS os;

    public enum BrowserFilter {
	all, folder_only, folder_only_no_hidden;
    }

    private enum OS {
	windows, osx, linux;
    }

    public NodeBrowserWindow(String windowCaption, String buttonCaption, String path, BrowserFilter filter) {
	super();
	this.windowCaption = windowCaption;
	this.buttonCaption = buttonCaption;
	nodeListnerLayout = new QuickVerticalLayout(false);
	currentNodePathLayout = new QuickHorizontalLayout(false);
	this.filter = filter;
	this.os = getOS();
	this.currentPath = path.equals("") ? os == OS.windows ? "C:\\" : "/" : path;
	init();
    }

    private OS getOS() {
	String system = System.getProperty("os.name").toLowerCase();
	if (system.startsWith("mac"))
	    return OS.osx;
	else if (system.startsWith("win"))
	    return OS.windows;
	else
	    return OS.linux;
    }

    private void init() {
	removeAllComponents();
	setSizeUndefined();
	currentNodePathLayout.setStyleName("fht-buttonsPathBrowser");
	currentNodePathLayout.setWidth("700");
	currentNodePathLayout.setHeight("37");
	addComponent(currentNodePathLayout);
	nodeListnerLayout.setWidth("700");
	nodeListnerLayout.setHeight("450");
	nodeListnerLayout.setStyleName("fht-grayBorder");
	addComponent(nodeListnerLayout);
	QuickButton selectNodeButton = new QuickButton(buttonCaption, FontAwesome.CHECK);
	addComponent(selectNodeButton);
	setComponentAlignment(selectNodeButton, Alignment.MIDDLE_RIGHT);
	selectNodeButton.addClickListener(new ClickListener() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void buttonClick(ClickEvent event) {
		window.close();
	    }
	});
	try {
	    showChildNodesAt(currentPath);
	} catch (FileNotFoundException e) {
	    QuickNotification.exception(e.getMessage());
	    e.printStackTrace();
	}
    }

    private void updateCurrentPath(String path) {
	setSelectedNode(path);
	HorizontalLayout buttons = new HorizontalLayout();
	buttons.setSpacing(true);
	buttons.setWidth("-1");
	currentNodePathLayout.removeAllComponents();
	currentNodePathLayout.addComponent(buttons);
	String[] folders;
	if (os == OS.windows) {
	    folders = path.split("\\\\");
	} else {
	    folders = path.equals("/") ? new String[] { "" } : path.split("/");
	}
	String currentPath = "";
	for (String folder : folders) {
	    currentPath += folder + (os == OS.windows ? "\\" : "/");
	    final String pathForButton = currentPath;
	    Button folderButton = new Button(folder);
	    buttons.addComponent(folderButton);
	    folderButton.addClickListener(new ClickListener() {

		private static final long serialVersionUID = 1L;

		@Override
		public void buttonClick(ClickEvent event) {
		    try {
			showChildNodesAt(pathForButton);
		    } catch (FileNotFoundException e) {
			QuickNotification.exception(e.getMessage());
			e.printStackTrace();
		    }
		}
	    });
	}
    }

    @SuppressWarnings("deprecation")
    private void showChildNodesAt(String path) throws FileNotFoundException {
	updateCurrentPath(path);
	File node = new File(path);
	if (!node.exists() || node.getName().startsWith("."))
	    throw new FileNotFoundException("The selected node does not exists");
	nodeListnerLayout.removeAllComponents();
	final Table nodeTable = new Table();
	nodeTable.setSizeFull();
	nodeTable.setSelectable(true);
	nodeListnerLayout.addComponent(nodeTable);
	nodeTable.addContainerProperty("", Label.class, null);
	nodeTable.addContainerProperty("Name", String.class, null);
	nodeTable.setColumnExpandRatio("Name", 1);
	nodeTable.addContainerProperty("Size", String.class, "--");
	for (int i = 0; i < node.listFiles().length; i++) {
	    final File child = node.listFiles()[i];
	    if (filter == BrowserFilter.folder_only_no_hidden && (child.isFile() || child.isHidden()))
		continue;
	    else if (filter == BrowserFilter.folder_only && child.isFile())
		continue;
	    Label iconLabel = new Label(getIcon(child).getHtml(), ContentMode.HTML);
	    nodeTable.addItem(new Object[] { iconLabel, child.getName(), child.isFile() ? child.length() + " bytes" : "--" }, child.getAbsolutePath());
	}
	nodeTable.addListener(new ItemClickListener() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void itemClick(ItemClickEvent event) {
		try {
		    showChildNodesAt((String) event.getItemId());
		} catch (FileNotFoundException e) {
		    QuickNotification.exception(e.getMessage());
		    e.printStackTrace();
		}
	    }
	});
    }

    private FontAwesome getIcon(File child) {
	if (child.isFile())
	    return FontAwesome.FILE;
	else if (child.isDirectory() && child.isHidden())
	    return FontAwesome.FOLDER_OPEN_O;
	else
	    return FontAwesome.FOLDER_OPEN;
    }

    public QuickPopupWindow show() {
	window = new QuickPopupWindow(windowCaption, this).show();
	return window;
    }

    public String getSelectedNode() {
	return currentPath;
    }

    public void setSelectedNode(String currentPath) {
	this.currentPath = currentPath;
    }
}
