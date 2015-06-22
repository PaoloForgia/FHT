package com.paolo.fht.core;

import org.junit.Test;

import com.paolo.fht.tools.FHTLoaderConfFS;

public class TestCore {

    @Test
    public void testReadFileAndFolder() throws Exception {
	new FHTHierarchy(new FHTLoaderConfFS("/Users/paoloforgia/Documents/Developer/Server/jboss-eap-6.3/")).print();
    }
}
