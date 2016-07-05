package com.zte.tl.nm4.domain;

import java.util.ArrayList;

import java.util.List;

public class Node {
    private Node parent;
    private String name;
    private Edge edge;
    private List<Node> children = new ArrayList<Node>();


    public Node(String name) {
        this.parent = null;
        this.name = name;
        this.edge = null;
    }

    public Node(Node parent,Edge edge) {
        this.parent = parent;
        this.name = edge.getTo();
        this.edge = edge;
    }
    public Node getParent(){
        return this.parent;
    }

    public Edge getEdge(){
        return this.edge;
    }

    public String getName() {
        return name;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void addChild(Node node) {
        this.children.add(node);
    }

    @Override
    public String toString() {
        String parent = this.parent==null?"NULL":this.parent.getName();
        return "Node{" +
                "parent='" + parent + '\'' +
                ", name='" + name + '\'' +
                ", edge=" + edge +
                ", children=" + children +
                '}';
    }
}
