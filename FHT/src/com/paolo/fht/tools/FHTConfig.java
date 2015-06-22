package com.paolo.fht.tools;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

public class FHTConfig {

    private final Properties properties;
    private final static String NAME = "config.properties";
    private final static String BEGIN = "IGNORE_BEGINS_WITH";
    private final static String END = "IGNORE_ENDS_WITH";
    private final static String LOCATION = "IGNORE_LOCATION";
    private final static String EXTENSION = "IGNORE_EXTENSION";
    // ----------------------------------------
    private String[] beginList;
    private String[] endList;
    private String[] locationList;
    private String[] extensionList;

    public FHTConfig() {
	properties = new Properties();
    }

    public void load() throws Exception {
	InputStream inputStream = getClass().getClassLoader().getResourceAsStream(NAME);
	if (inputStream != null) {
	    properties.load(inputStream);
	    beginList = getArrayFromProperties(BEGIN);
	    endList = getArrayFromProperties(END);
	    locationList = getArrayFromProperties(LOCATION);
	    extensionList = getArrayFromProperties(EXTENSION);
	} else {
	    throw new Exception("Configuration properties file not found");
	}
    }

    private String[] getArrayFromProperties(String key) {
	List<String> list = new ArrayList<String>();
	for (String value : properties.getProperty(key).split(";")) {
	    if (value != null && !value.equals(""))
		list.add(value);
	}
	String[] array = new String[list.size()];
	array = list.toArray(array);
	return array;
    }

    public boolean toIgnore(File file) {
	if (StringUtils.startsWithAny(file.getName(), getIgnoreKeysBegin()))
	    return true;
	if (StringUtils.endsWithAny(file.getName(), getIgnoreKeysEnd()))
	    return true;
	if (StringUtils.startsWithAny(file.getAbsolutePath(), getIgnoreKeysLocation()))
	    return true;
	if (Arrays.asList(getIgnoreKeysExtension()).contains(FilenameUtils.getExtension(file.getAbsolutePath())))
	    return true;
	return false;
    }

    public synchronized String[] getIgnoreKeysBegin() {
	return beginList;
    }

    public synchronized String[] getIgnoreKeysEnd() {
	return endList;
    }

    public synchronized String[] getIgnoreKeysLocation() {
	return locationList;
    }

    public synchronized String[] getIgnoreKeysExtension() {
	return extensionList;
    }
}
