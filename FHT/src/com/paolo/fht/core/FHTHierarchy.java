package com.paolo.fht.core;

import com.paolo.fht.tools.FHTLoaderConf;

public final class FHTHierarchy
	extends FHTNodeImpl {

    private final FHTLoader loader;
    private final String absolutePath;

    public FHTHierarchy(FHTLoaderConf conf) throws Exception {
	this(conf.getLoader());
    }

    private FHTHierarchy(FHTLoader loader) throws Exception {
	super(loader.getRootInfo());
	this.loader = loader;
	this.absolutePath = loader.getLoaderConf().getAbsolutePath();
    }

    public synchronized String getAbsolutePath() {
	return absolutePath;
    }

    /**
     * <b>Test: </b> Print herarchy
     */
    public void print() {
	// TODO Auto-generated method stub
    }
}
