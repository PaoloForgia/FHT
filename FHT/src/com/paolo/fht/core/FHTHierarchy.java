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
import com.paolo.fht.tools.FHTFileType;
import com.paolo.fht.tools.FHTLoaderConf;
import com.paolo.fht.tools.Tools;

public final class FHTHierarchy
	extends FHTNodeImpl {

    private final FHTLoader loader;
    private final String rootPath;
    private final FHTConfig config;
    private final String name;

    public FHTHierarchy(FHTLoaderConf conf) throws Exception {
	this(conf.getLoader());
    }

    public FHTHierarchy(FHTLoader loader) throws Exception {
	super(loader.getRootInfo());
	this.loader = loader;
	this.rootPath = loader.getLoaderConf().getAbsolutePath();
	config = new FHTConfig();
	this.name = getName();
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
	    childListing: for (File childFile : file.listFiles()) {
		if (config.toIgnore(childFile))
		    continue childListing;
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

    public synchronized FHTLoader getLoader() {
	return loader;
    }

    /**
     * <b>Test: </b> Print herarchy
     * 
     * @throws Exception
     */
    public void loadAndPrint() throws Exception {
	load();
	print();
    }

    public void print() {
	printHierarchy(this, 0);
    }

    private void printHierarchy(FHTNode node, int level) {
	String tab = "";
	for (int i = 0; i < level; i++) {
	    tab += "|--";
	}
	System.out.println(tab + node.getName() + (node.getDifferenceType() != null ? "[" + node.getDifferenceType() + "]" : ""));
	for (FHTNode child : node.getChildren()) {
	    if (child.getFileType() == FHTFileType.folder)
		printHierarchy(child, level + 1);
	    else
		System.out.println(tab + "|--" + child.getName() + (node.getDifferenceType() != null ? "[" + node.getDifferenceType() + "]" : ""));
	}
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (!super.equals(obj))
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	FHTHierarchy other = (FHTHierarchy) obj;
	if (name == null) {
	    if (other.name != null)
		return false;
	} else if (!name.equals(other.name))
	    return false;
	return true;
    }
}
