package com.paolo.fht.core;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.paolo.fht.tools.FHTConfig;
import com.paolo.fht.tools.FHTLoaderConf;
import com.paolo.fht.tools.FHTType;
import com.paolo.fht.tools.Tools;

public final class FHTHierarchy
	extends FHTNodeImpl {

    private final FHTLoader loader;
    private final String rootPath;
    private final FHTConfig config;

    public FHTHierarchy(FHTLoaderConf conf) throws Exception {
	this(conf.getLoader());
    }

    private FHTHierarchy(FHTLoader loader) throws Exception {
	super(loader.getRootInfo());
	this.loader = loader;
	this.rootPath = loader.getLoaderConf().getAbsolutePath();
	config = new FHTConfig();
    }

    public void load() throws Exception {
	config.load();
	loadRootChildren();
    }

    private void loadRootChildren() throws Exception {
	loader.load();
	ExecutorService executor = Executors.newCachedThreadPool();
	List<Future<Void>> futureList = new ArrayList<Future<Void>>();
	childListing: for (final File childFile : new File(getAbsoluteRootPath()).listFiles()) {
	    if (config.toIgnore(childFile))
		continue childListing;
	    boolean isDirectory = childFile.isDirectory();
	    final FHTNode childNode = new FHTNodeImpl(new FHTNodeInfo(childFile.getName(), Tools.relativePath(rootPath, childFile.getAbsolutePath()),
		    childFile.length(), isDirectory), this);
	    addChild(childNode);
	    if (isDirectory) {
		Callable<Void> task = new Callable<Void>() {

		    @Override
		    public Void call() throws Exception {
			loadChildren(childFile, childNode);
			return null;
		    }
		};
		Future<Void> future = executor.submit(task);
		futureList.add(future);
	    }
	}
	// ---------------------------------------------------
	for (Future<Void> future : futureList) {
	    try {
		future.get();
	    } catch (InterruptedException | ExecutionException e) {
		e.printStackTrace();
	    }
	}
	executor.shutdown();
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
	    System.out.println("Ignored folder: " + file.getAbsolutePath());
	}
    }

    public synchronized String getAbsoluteRootPath() {
	return rootPath;
    }

    /**
     * <b>Test: </b> Print herarchy
     * 
     * @throws Exception
     */
    public void print() throws Exception {
	load();
	printHierarchy(this, 0);
    }

    private void printHierarchy(FHTNode node, int level) {
	String tab = "";
	for (int i = 0; i < level; i++) {
	    tab += "|--";
	}
	System.out.println(tab + node.getName());
	for (FHTNode child : node.getChildren()) {
	    if (child.getType() == FHTType.folder)
		printHierarchy(child, level + 1);
	    else
		System.out.println(tab + "|--" + child.getName());
	}
    }
}
