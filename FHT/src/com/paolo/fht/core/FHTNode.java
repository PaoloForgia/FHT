package com.paolo.fht.core;

import java.util.List;

import com.paolo.fht.tools.FHTType;

public interface FHTNode {

    String getName();

    String getPath();

    long getSize();

    FHTType getType();

    FHTNode getParent();

    List<FHTNode> getChildren();

    void addChild(FHTNode child);
}
