package com.paolo.fht.core;

import com.paolo.fht.tools.FHTType;

public final class FHTNodeInfo {

    private final String name;
    private final String path;
    private final long size;
    private final FHTType type;

    public FHTNodeInfo(String name, String path, long size, FHTType type) {
	super();
	this.name = name;
	this.path = path;
	this.size = size;
	this.type = type;
    }

    protected String getName() {
	return name;
    }

    protected String getPath() {
	return path;
    }

    protected long getSize() {
	return size;
    }

    protected FHTType getType() {
	return type;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	result = prime * result + ((path == null) ? 0 : path.hashCode());
	result = prime * result + ((type == null) ? 0 : type.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	FHTNodeInfo other = (FHTNodeInfo) obj;
	if (name == null) {
	    if (other.name != null)
		return false;
	} else if (!name.equals(other.name))
	    return false;
	if (path == null) {
	    if (other.path != null)
		return false;
	} else if (!path.equals(other.path))
	    return false;
	if (type == null) {
	    if (other.type != null)
		return false;
	} else if (!type.equals(other.type))
	    return false;
	return true;
    }
}
