package com.paolo.fht.core;

import com.paolo.fht.tools.FHTLoaderConf;

abstract class FHTLoaderBase
	implements FHTLoader {

    protected final FHTLoaderConf conf;
    protected FHTNodeInfo infoRoot;

    public FHTLoaderBase(FHTLoaderConf loader) {
	this.conf = loader;
    }

    @Override
    public FHTNodeInfo load() throws Exception {
	return conf.load();
    }

    @Override
    public abstract FHTNodeInfo getRootInfo() throws Exception;

    @Override
    public FHTLoaderConf getLoaderConf() {
	return conf;
    }
}
