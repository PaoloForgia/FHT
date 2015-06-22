package com.paolo.fht.core;

import com.paolo.fht.tools.FHTLoaderConfFS;

public final class FHTLoaderFS
	extends FHTLoaderBase {

    public FHTLoaderFS(FHTLoaderConfFS loader) {
	super(loader);
    }

    @Override
    public FHTNodeInfo getRootInfo() throws Exception {
	if (infoRoot != null)
	    infoRoot = load();
	return infoRoot;
    }
}
