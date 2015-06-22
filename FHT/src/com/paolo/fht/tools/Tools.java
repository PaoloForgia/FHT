package com.paolo.fht.tools;

import java.io.File;

public class Tools {

    public Tools() {
    }

    /**
     * @param rootPath
     *            path from the root
     * @param path
     *            path from the current file
     * @return the path without the rootPath part
     */
    public static String relativePath(String rootPath, String path) {
	return new File(rootPath).toURI().relativize(new File(path).toURI()).getPath();
    }
}
