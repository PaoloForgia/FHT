package com.paolo.fht.tools;

import com.paolo.fht.core.FHTLoader;
import com.paolo.fht.core.FHTNodeInfo;

public interface FHTLoaderConf {

    FHTNodeInfo load() throws Exception;

    FHTLoader getLoader();

    String getAbsolutePath();
}
