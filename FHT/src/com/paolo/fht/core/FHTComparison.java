package com.paolo.fht.core;

import com.paolo.fht.tools.FHTDifferenceType;

public final class FHTComparison {

    public FHTComparison() {
    }

    public void compare(FHTHierarchy h1, FHTHierarchy h2) {
	compareNodes(h1, h2);
    }

    private void compareNodes(FHTNode h1, FHTNode h2) {
	FHTDifferenceType difference = h1.equals(h2) ? FHTDifferenceType.none : FHTDifferenceType.different;
	h1.setDifferenceType(difference);
	h2.setDifferenceType(difference);
	int i1 = 0;
	int i2 = 0;
	comparison: for (;;) {
	    if (h1.getChildren().size() == 0 && h2.getChildren().size() == 0) {
		break comparison;
	    } else if (h1.getChildren().size() == 0 && h2.getChildren().size() != 0) {
		setAllChildrenOrphan(h2);
		break comparison;
	    } else if (h1.getChildren().size() != 0 && h2.getChildren().size() == 0) {
		setAllChildrenOrphan(h1);
		break comparison;
	    }
	    FHTNode n1 = h1.getChildren().get(i1);
	    FHTNode n2 = h2.getChildren().get(i2);
	    if (n1.getPath().compareTo(n2.getPath()) == 0) {
		compareNodes(n1, n2);
	    } else if (n1.getPath().compareTo(n2.getPath()) < 0) {
		i1 = searchForOrphan(h1, i1, n2.getPath()); // n1 before n2
	    } else {
		i2 = searchForOrphan(h2, i2, n1.getPath()); // n2 before n1
	    }
	    i1++;
	    i2++;
	    if (i1 != h1.getChildren().size() && i2 != h2.getChildren().size()) {
		continue comparison;
	    } else if (i1 == h1.getChildren().size() && i2 == h2.getChildren().size()) {
		break;
	    } else if (i1 == h1.getChildren().size() && i2 != h2.getChildren().size()) {
		i2 = setRestOrphan(h2, i2);
	    } else if (i1 != h1.getChildren().size() && i2 == h2.getChildren().size()) {
		i1 = setRestOrphan(h1, i1);
	    }
	}
    }

    private void setAllChildrenOrphan(FHTNode node) {
	for (FHTNode child : node.getChildren()) {
	    child.setDifferenceType(FHTDifferenceType.orphan);
	    if (child.getChildren().size() != 0)
		setAllChildrenOrphan(child);
	}
    }

    private int setRestOrphan(FHTNode h, int i) {
	for (; i < h.getChildren().size(); i++) {
	    FHTNode node = h.getChildren().get(i);
	    node.setDifferenceType(FHTDifferenceType.orphan);
	    if (node.getChildren().size() != 0)
		setAllChildrenOrphan(node);
	}
	return i;
    }

    private int searchForOrphan(FHTNode h, int i, String path) {
	for (; i < h.getChildren().size(); i++) {
	    FHTNode current = h.getChildren().get(i);
	    if (current.getPath().compareTo(path) == 0) {
		i--;
		break;
	    } else {
		current.setDifferenceType(FHTDifferenceType.orphan);
	    }
	}
	return i;
    }
}
