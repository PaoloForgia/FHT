package com.paolo.fht.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.paolo.fht.tools.FHTDifferenceType;
import com.paolo.fht.tools.FHTFileType;

public class FHTNodeImpl
	implements FHTNode {

    protected final FHTNodeInfo info;
    protected final FHTNode parent;
    protected List<FHTNode> children;
    private FHTDifferenceType difference;

    protected FHTNodeImpl(FHTNodeInfo info) {
	this(info, null);
    }

    public FHTNodeImpl(FHTNodeInfo info, FHTNode parent) {
	this.info = info;
	this.parent = parent;
	children = Collections.synchronizedList(new ArrayList<FHTNode>());
    }

    @Override
    public synchronized String getName() {
	return info.getName();
    }

    @Override
    public synchronized String getPath() {
	return info.getPath();
    }

    @Override
    public synchronized long getSize() {
	return info.getSize();
    }

    @Override
    public synchronized FHTFileType getFileType() {
	return info.getType();
    }

    @Override
    public synchronized FHTNode getParent() {
	return parent;
    }

    @Override
    public synchronized List<FHTNode> getChildren() {
	return Collections.unmodifiableList(children);
    }

    @Override
    public void addChild(final FHTNode child) {
	synchronized (children) {
	    children.add(child);
	}
    }

    @Override
    public synchronized FHTDifferenceType getDifferenceType() {
	return difference;
    }

    @Override
    public synchronized void setDifferenceType(FHTDifferenceType difference) {
	this.difference = difference;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((info == null) ? 0 : info.hashCode());
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
	FHTNodeImpl other = (FHTNodeImpl) obj;
	if (info == null) {
	    if (other.info != null)
		return false;
	} else if (!info.equals(other.info))
	    return false;
	return true;
    }
}
