package com.paolo.fht.core;

import java.util.List;

import com.paolo.fht.tools.FHTDifferenceType;
import com.paolo.fht.tools.FHTFileType;

public interface FHTNode {

    String getName();

    String getPath();

    long getSize();

    FHTFileType getFileType();

    FHTNode getParent();

    List<FHTNode> getChildren();

    void addChild(FHTNode child);

    boolean hasChildren();

    FHTDifferenceType getDifferenceType();

    void setDifferenceType(FHTDifferenceType difference);
}
