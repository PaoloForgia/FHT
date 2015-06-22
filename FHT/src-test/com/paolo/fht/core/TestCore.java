package com.paolo.fht.core;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import com.paolo.fht.tools.FHTConfig;
import com.paolo.fht.tools.FHTLoaderConfFS;

public class TestCore {

    @Test
    public void testReadFileAndFolder() throws Exception {
	new FHTHierarchy(new FHTLoaderConfFS("/Users/paoloforgia/Documents/Developer/Workspace/Git/FHT/FHT")).print();
    }

    @Test
    public void testProperties() throws Exception {
	FHTConfig config = new FHTConfig();
	config.load();
	Assert.assertEquals(1, config.getIgnoreKeysBegin().length);
	Assert.assertEquals("/.", config.getIgnoreKeysBegin()[0]);
	Assert.assertEquals(0, config.getIgnoreKeysEnd().length);
	Assert.assertEquals(1, config.getIgnoreKeysLocation().length);
	Assert.assertEquals("/Library", config.getIgnoreKeysLocation()[0]);
	Assert.assertEquals(2, config.getIgnoreKeysExtension().length);
    }

    @Test
    public void testIgnore() throws Exception {
	// Ignore?
	FHTConfig config = new FHTConfig();
	config.load();
	Assert.assertEquals(true, config.toIgnore(new File("/Library/Audio")));
	Assert.assertEquals(true, config.toIgnore(new File("/Applications/FaceTime.app")));
	Assert.assertEquals(false, config.toIgnore(new File("/Applications")));
    }
}
