package com.paolo.fht.core;

import com.paolo.fht.tools.FHTLoaderConf;

public interface FHTLoader {

    FHTNodeInfo load() throws Exception;

    FHTNodeInfo getRootInfo() throws Exception;

    FHTLoaderConf getLoaderConf();
}
