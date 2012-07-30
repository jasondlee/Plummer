package com.steeplesoft.plummer.kernel;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author jdlee
 */
public class NavigationNode implements Serializable {

    private String id;
    private String label;
    private String link;
    private String icon;
    private List<NavigationNode> children;

    public NavigationNode(String id, String label) {
        this.id = id;
        this.label = label;
    }

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setChildren(List<NavigationNode> nodes) {
        children = nodes;
    }

    public List<NavigationNode> getChildren() {
        return (children == null) ? null : Collections.unmodifiableList(children);
    }

    public static NavigationNode createNode(String id, String label, String icon, String link, List<NavigationNode> children) {
        NavigationNode node = new NavigationNode(id, label);
        node.setIcon(icon);
        node.setLink(link);
        node.setChildren(children);

        return node;
    }
}
