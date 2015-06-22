package com.paolo.fht.core;

import java.io.File;

import com.paolo.fht.tools.FHTLoaderConf;
import com.paolo.fht.tools.Tools;

public final class FHTHierarchy
	extends FHTNodeImpl {

    private final FHTLoader loader;
    private final String rootPath;

    public FHTHierarchy(FHTLoaderConf conf) throws Exception {
	this(conf.getLoader());
    }

    private FHTHierarchy(FHTLoader loader) throws Exception {
	super(loader.getRootInfo());
	this.loader = loader;
	this.rootPath = loader.getLoaderConf().getAbsolutePath();
	loadRootChildren();
    }

    private void loadRootChildren() {
	for (File childFile : new File(getAbsoluteRootPath()).listFiles()) {
	    // multi thread
	    boolean isDirectory = childFile.isDirectory();
	    FHTNode childNode = new FHTNodeImpl(new FHTNodeInfo(childFile.getName(), Tools.relativePath(rootPath, childFile.getAbsolutePath()), childFile.length(),
		    isDirectory), this);
	    addChild(childNode);
	    if (isDirectory)
		loadChildren(childFile, childNode);
	}
    }

    private void loadChildren(File file, FHTNode node) {
	try {
	    for (File childFile : file.listFiles()) {
		boolean isDirectory = childFile.isDirectory();
		FHTNode childNode = new FHTNodeImpl(new FHTNodeInfo(childFile.getName(), Tools.relativePath(rootPath, file.getAbsolutePath()), childFile.length(),
			isDirectory), node);
		node.addChild(childNode);
		if (isDirectory)
		    loadChildren(childFile, childNode);
	    }
	} catch (Exception e) {
	    System.out.println("Ignored file on folder: " + file.getAbsolutePath());
	}
    }

    public synchronized String getAbsoluteRootPath() {
	return rootPath;
    }

    /**
     * <b>Test: </b> Print herarchy
     */
    public void print() {
	// TODO Auto-generated method stub
    }
}
