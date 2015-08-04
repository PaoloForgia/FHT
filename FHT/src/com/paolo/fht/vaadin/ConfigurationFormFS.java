package com.paolo.fht.vaadin;

import com.paolo.fht.core.FHTLoader;
import com.paolo.fht.tools.FHTLoaderConf;
import com.paolo.fht.tools.FHTLoaderConfFS;
import com.vaadin.ui.FormLayout;

public class ConfigurationFormFS
	extends FormLayout
	implements ConfigurationForm {

    private static final long serialVersionUID = 1L;
    private final NodeBrowserLayout upload;

    public ConfigurationFormFS() {
	super();
	upload = new NodeBrowserLayout("Root select");
	init();
    }

    private void init() {
	removeAllComponents();
	addComponent(upload);
    }

    @Override
    public FHTLoader getLoader() throws Exception {
	String path = upload.getPath();
	if (path.equals(""))
	    throw new NullPointerException("Empty field, please select a root folder");
	FHTLoaderConf conf = new FHTLoaderConfFS(path);
	conf.load();
	return conf.getLoader();
    }
}
