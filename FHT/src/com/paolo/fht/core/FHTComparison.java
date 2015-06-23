package com.paolo.fht.core;

import com.paolo.fht.tools.FHTDifferenceType;

public final class FHTComparison {

    public FHTComparison() {
    }

    public void compare(FHTHierarchy h1, FHTHierarchy h2) {
	compareNodes(h1, h2);
    }

    private void compareNodes(FHTNode h1, FHTNode h2) {
	FHTDifferenceType difference = h1.equals(h2) ? FHTDifferenceType.equals : FHTDifferenceType.different;
	h1.setDifferenceType(difference);
	h2.setDifferenceType(difference);
	if (h1.hasChildren() && h2.hasChildren()) {
	    compareChildren(h1, h2);
	} else if (h1.hasChildren() && !h2.hasChildren()) {
	    setAllOrphan(h1);
	} else if (!h1.hasChildren() && h2.hasChildren()) {
	    setAllOrphan(h2);
	}
    }

    private void compareChildren(FHTNode h1, FHTNode h2) {
	int size1 = h1.getChildren().size();
	int size2 = h2.getChildren().size();
	analizeChildren: for (int i1 = 0, i2 = 0;; i1++, i2++) {
	    if (i1 < size1 && i2 < size2) {
		FHTNode n1 = h1.getChildren().get(i1);
		FHTNode n2 = h2.getChildren().get(i2);
		if (n1.getPath().compareTo(n2.getPath()) == 0) {
		    compareNodes(n1, n2);
		} else if (n1.getPath().compareTo(n2.getPath()) < 0) {
		    for (; i1 < size1; i1++) {
			FHTNode current = h1.getChildren().get(i1);
			if (current.getPath().compareTo(n2.getPath()) < 0) {
			    current.setDifferenceType(FHTDifferenceType.orphan);
			} else {
			    break;
			}
		    }
		} else {
		    for (; i2 < size2; i2++) {
			FHTNode current = h2.getChildren().get(i2);
			if (n1.getPath().compareTo(current.getPath()) > 0) {
			    current.setDifferenceType(FHTDifferenceType.orphan);
			} else {
			    break;
			}
		    }
		}
	    } else if (i1 < size1 && i2 == size2) {
		for (; i1 < size1; i1++) {
		    setAllOrphan(h1.getChildren().get(i1));
		}
		break analizeChildren;
	    } else if (i1 == size1 && i2 < size2) {
		for (; i2 < size2; i2++) {
		    setAllOrphan(h2.getChildren().get(i2));
		}
		break analizeChildren;
	    } else {
		break analizeChildren;
	    }
	}
    }

    private void setAllOrphan(FHTNode node) {
	node.setDifferenceType(FHTDifferenceType.orphan);
	for (FHTNode child : node.getChildren()) {
	    setAllOrphan(child);
	}
    }
}
