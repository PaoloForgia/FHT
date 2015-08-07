package com.paolo.fht.vaadin;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;

import com.paolo.fht.core.FHTHierarchy;
import com.paolo.fht.core.FHTLoader;
import com.paolo.fht.core.FHTLoaderFS;
import com.paolo.fht.core.FHTNode;
import com.paolo.fht.tools.FHTDifferenceType;
import com.paolo.fht.vaadin.quick.QuickHorizontalLayout;
import com.paolo.fht.vaadin.quick.QuickLabel;
import com.paolo.fht.vaadin.quick.QuickVerticalLayout;
import com.vaadin.annotations.Theme;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.Tree;
import com.vaadin.ui.Tree.ItemStyleGenerator;
import com.vaadin.ui.UI;

@Theme("fht")
public class ComparatorUI
	extends UI {

    private static final long serialVersionUID = 1L;
    private static FHTHierarchy h1;
    private static FHTHierarchy h2;
    private final List<FHTNode> ignoredList;
    private final Tree treeLeft;
    private final Tree treeRight;
    private final QuickVerticalLayout itemDetailsLayoutLeft;
    private final QuickVerticalLayout itemDetailsLayoutRight;

    public ComparatorUI() {
	super();
	treeLeft = new Tree();
	treeRight = new Tree();
	itemDetailsLayoutLeft = new QuickVerticalLayout(false);
	itemDetailsLayoutRight = new QuickVerticalLayout(false);
	ignoredList = new ArrayList<FHTNode>();
    }

    @Override
    protected void init(VaadinRequest request) {
	if (h1 == null || h2 == null) {
	    getPage().setLocation("/FHT");
	    return;
	}
	final QuickVerticalLayout layout = new QuickVerticalLayout();
	setContent(layout);
	layout.addComponent(new QuickLabel("Files Hierarchy Tool", "fht-title"));
	QuickHorizontalLayout container = new QuickHorizontalLayout(false);
	container.addComponent(getPreferences());
	QuickVerticalLayout comparatorTreeLeft = getComparatorTree(h1, treeLeft, itemDetailsLayoutLeft);
	container.addComponent(comparatorTreeLeft);
	QuickVerticalLayout comparatorTreeRight = getComparatorTree(h2, treeRight, itemDetailsLayoutRight);
	container.addComponent(comparatorTreeRight);
	container.setExpandRatio(comparatorTreeLeft, 1);
	container.setExpandRatio(comparatorTreeRight, 1);
	layout.addComponent(container);
	layout.setExpandRatio(container, 1);
    }

    private QuickVerticalLayout getComparatorTree(FHTHierarchy hierarchy, Tree tree, QuickVerticalLayout itemDetailsLayout) {
	QuickVerticalLayout comparatorLayout = new QuickVerticalLayout(false);
	// -------------------------------------------
	QuickVerticalLayout confLayout = new QuickVerticalLayout(false);
	confLayout.addStyleName("fht-borderContainer");
	confLayout.setHeight("-1");
	FHTLoader loader = hierarchy.getLoader();
	if (loader instanceof FHTLoaderFS)
	    confLayout.addComponent(new QuickLabel("Filesystem", "fht-comparator-title"));
	else
	    confLayout.addComponent(new QuickLabel("Other", "fht-comparator-title"));
	confLayout.addComponent(new QuickLabel(hierarchy.getAbsoluteRootPath()));
	comparatorLayout.addComponent(confLayout);
	// -------------------------------------------
	QuickVerticalLayout treeLayout = new QuickVerticalLayout(false);
	treeLayout.addStyleName("fht-borderContainer fht-treeLayout");
	treeLayout.addComponent(getTree(hierarchy, tree));
	comparatorLayout.addComponent(treeLayout);
	comparatorLayout.setExpandRatio(treeLayout, 1);
	// -------------------------------------------
	itemDetailsLayout.setStyleName("fht-borderContainer");
	itemDetailsLayout.setHeight("-1");
	itemDetailsLayout.addComponent(new QuickLabel("Select item"));
	comparatorLayout.addComponent(itemDetailsLayout);
	return comparatorLayout;
    }

    private Tree getTree(FHTHierarchy hierarchy, Tree tree) {
	tree.addContainerProperty("caption", String.class, "");
	tree.setItemCaptionPropertyId("caption");
	tree.setItemStyleGenerator(new ItemStyleGenerator() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getStyle(Tree source, Object itemId) {
		FHTNode node = (FHTNode) itemId;
		if (node.getDifferenceType() == FHTDifferenceType.different)
		    return "fht-tree-different";
		else if (node.getDifferenceType() == FHTDifferenceType.orphan)
		    return "fht-tree-orphan";
		else
		    return null;
	    }
	});
	tree.addItemClickListener(new ItemClickListener() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void itemClick(ItemClickEvent event) {
		showDetails((FHTNode) event.getItemId());
	    }
	});
	addChildrenToTree(hierarchy, tree);
	tree.expandItemsRecursively(hierarchy);
	return tree;
    }

    protected void showDetails(FHTNode node) {
	treeLeft.select(node);
	expandParent(treeLeft, node);
	treeRight.select(node);
	expandParent(treeRight, node);
	updateItemDetails((FHTNode) treeLeft.getValue(), itemDetailsLayoutLeft);
	updateItemDetails((FHTNode) treeRight.getValue(), itemDetailsLayoutRight);
    }

    private void expandParent(Tree tree, FHTNode node) {
	tree.expandItem(node);
	if (node.getParent() != null)
	    expandParent(tree, node.getParent());
    }

    private void updateItemDetails(FHTNode nodeLeft, QuickVerticalLayout itemDetailsLayout) {
	itemDetailsLayout.removeAllComponents();
	itemDetailsLayout.addComponent(new Label("<b>Name:</b> " + nodeLeft.getName(), ContentMode.HTML));
	itemDetailsLayout.addComponent(new Label("<b>Path:</b> " + nodeLeft.getPath(), ContentMode.HTML));
	itemDetailsLayout.addComponent(new Label("<b>Size:</b> " + nodeLeft.getSize() + " byte", ContentMode.HTML));
	itemDetailsLayout.addComponent(new Label("<b>Type</b>: " + nodeLeft.getFileType().toString(), ContentMode.HTML));
    }

    @SuppressWarnings("unchecked")
    private void addChildrenToTree(final FHTNode node, Tree tree) {
	try {
	    tree.addItem(node).getItemProperty("caption").setValue(FilenameUtils.removeExtension(node.getName()));
	} catch (Exception e) {
	    ignoredList.add(node);
	}
	tree.setParent(node, node.getParent());
	tree.setChildrenAllowed(node, node.getChildren().size() > 0);
	for (FHTNode child : node.getChildren()) {
	    addChildrenToTree(child, tree);
	}
    }

    private QuickVerticalLayout getPreferences() {
	QuickVerticalLayout preferencesLayout = new QuickVerticalLayout(false);
	preferencesLayout.setStyleName("fht-borderContainer");
	preferencesLayout.setWidth("300");
	preferencesLayout.addComponent(new QuickLabel("Preferences", "fht-comparator-title"));
	return preferencesLayout;
    }

    public static void compareByLoaders(FHTHierarchy h1, FHTHierarchy h2) {
	ComparatorUI.h1 = h1;
	ComparatorUI.h2 = h2;
    }
}
