package com.paolo.fht.tools;

import java.io.File;
import java.io.FileNotFoundException;

import com.paolo.fht.core.FHTLoader;
import com.paolo.fht.core.FHTLoaderFS;
import com.paolo.fht.core.FHTNodeInfo;

public class FHTLoaderConfFS
	implements FHTLoaderConf {

    private final File file;

    public FHTLoaderConfFS(String path) {
	this.file = new File(path.endsWith("/") ? path : path + "/");
    }

    @Override
    public FHTNodeInfo load() throws Exception {
	boolean exists = file.exists();
	boolean isFolder = file.isDirectory();
	if (exists && isFolder) {
	    return getRootInfo();
	} else if (!exists) {
	    throw new FileNotFoundException("The selected folder does not exists.");
	} else {
	    throw new Exception("Current path is not linking on a folder");
	}
    }

    private FHTNodeInfo getRootInfo() {
	return new FHTNodeInfo(file.getName(), "", file.length(), FHTType.folder);
    }

    @Override
    public FHTLoader getLoader() {
	return new FHTLoaderFS(this);
    }

    @Override
    public String getAbsolutePath() {
	return file.getAbsolutePath();
    }
}
