/*
 * Copyright (C) 2023 Xiaxi Shen.  All rights reserved.
 */

package graph;
import java.util.*;

/**
 * <b>Graph</B> is a mutable collection of nodes and edges. It is directed and labeled.
 *
 * <p>In a directed graph, edges are one-way : an edge e = ⟨A,B⟩ indicates that B is directly reachable from A.
 * To indicate that B is directly reachable from A and A from B,
 * a directed graph would have edges ⟨A,B⟩ and ⟨B,A⟩.
 *
 * <p>Every edge has a label containing information of some sort.
 * Labels are not unique: multiple edges may have the same label.
 *
 * <p>No 2 edges with the same parent and child nodes will have the same edge label.
 * @param <T> the type for Nodes
 * @param <E> the type for Edges
 */
public class Graph<T, E> {

    /**
     * <b>Node</B> represents a node with label on it
     *
     * @param <T> the type for Nodes
     */
    public class Node<T> {
        private final T label;

        // Abstraction Function:
        // a node is a label

        // Representation invariant for every Node n:
        // label != null

        /**
         * Constructor that creates a Node with label
         *
         * @param label be used to create node
         * @spec.effects construct a new Node with label
         */
        public Node(T label) {
            this.label = label;
            checkRep();
        }

        /**
         * Get label of the Node
         *
         * @return label of the Node
         */
        public T getLabel() {
            checkRep();
            return label;
        }

        /**
         * Throws an exception if the representation invariant is violated.
         */
        private void checkRep() {
            if (label == null)
                throw new RuntimeException("label cannot be null!");
        }

        /**
         * Standard hashCode function.
         *
         * @return an int that all objects equal to this will also return.
         */
        @Override
        public int hashCode() {
            return label.hashCode();
        }


        /**
         * Returns the label of Node
         *
         * @return label of this as a string
         */
        @Override
        public String toString() {
            checkRep();
            return this.label.toString();
        }

        /**
         * Standard equality operation.
         *
         * @param obj the object to be compared for equality
         * @return true if and only if 'obj' is an instance of a Node and 'this' and 'obj' represent the
         * same Node.
         */
        @Override
        public boolean equals(Object obj) {
            checkRep();
            if (!(obj instanceof Node)) {
                return false;
            }
            Node n = (Node) obj;
            checkRep();
            return this.label.equals(n.label);
        }
    }


    /**
     *
     * @param <E> E is generic type of Edge
     */
    public class Edge<E> {

        private final E label;
        private final Node<T> childNode;
        private static final boolean DEBUG = false;

        // Abstraction Function:
        // an edge include two elements {x, y} where
        // x is the label, y is the node the edge pointing to

        // Representation invariant for every edge e:
        // label != null
        // childNode != null

        /**
         * Constructor that creates an Edge with the label and pointing to childNode
         *
         *  @param label Label on the edge
         *  @param childNode The node this edge is pointing to
         *  @spec.requires label != null AND childNode != null
         *  @spec.effects construct a new Edge with label and pointing to childNode
         */
        public Edge(E label, Node<T> childNode) {
            this.label = label;
            this.childNode = new Node<T>(childNode.getLabel());
            checkRep();
        }

        /**
         * Get label of the edge
         *
         * @return label of the edge
         */
        public E getLabel() {
            return label;
        }

        /**
         * Return Node edge points to
         *
         * @return Node edge points to
         */
        public Node<T> getChildNode() {
            return new Node<T>(childNode.getLabel());
        }

        /**
         * Throws an exception if the representation invariant is violated.
         */
        private void checkRep() {
            if (label == null) {
                throw new RuntimeException("label cannot be null");
            }
            if (childNode == null) {
                throw new RuntimeException("child node cannot be null");
            }
        }

        /**
         * Standard hashCode function.
         *
         * @return an int that all objects equal to this will also return.
         */
        @Override
        public int hashCode() {
            return childNode.hashCode() + label.hashCode();
        }

        /**
         * Standard equality operation.
         *
         * @param obj the object to be compared for equality
         * @return true if and only if 'obj' is an instance of an Edge and 'this' and 'obj' represent the
         * same Node.
         */
        @Override
        public boolean equals(Object obj) {
            checkRep();
            if (!(obj instanceof Edge)) {
                return false;
            }
            Edge labelEdge = (Edge) obj;
            checkRep();
            return childNode.equals(labelEdge.childNode) && label.equals(labelEdge.label);
        }

        /**
         * Returns the edge label as a string
         *
         * @return Edge label as a string
         */
        public String toString() {
            return this.label.toString();
        }
    }


    private final HashMap<Node<T>, List<Edge<E>>> graph;
    private static final boolean DEBUG = false;
    // Abstraction Function:
    // A graph g = the set {p1, p2, p3, p4...pi} where each pi is a <key, value> pair
    // where key is a node and value is set of all outgoing edges of that node
    // The graph is empty if there is no node in graph

    // Representation invariant for every graph g:
    // g != null
    // No duplicate Nodes
    // forall pi in g, g.key != null && g.value != null
    // for all edges in g.value
    // No 2 edges with the same parent and child nodes will have the same edge label.

    /**
     * Constructor which creates a new Graph with an empty Map
     *
     * @spec.effects create an empty graph
     */
    public Graph() {
        graph = new HashMap<>();
        checkRep();
    }

    /**
     * Throws an exception if the representation invariant is violated.
     */
    private void checkRep() {
        if (graph == null) {
            throw new RuntimeException("The graph cannot be null.");
        }
        if (DEBUG) {
            for (Node n : graph.keySet()) {
                if (n == null) {
                    throw new RuntimeException("Node cannot be null.");
                }
                if (graph.get(n) == null) {
                    throw new RuntimeException("EdgeList cannot be null.");
                }
                for (Edge e : graph.get(n)) {
                    if (e == null) {
                        throw new RuntimeException("Edge cannot be null.");
                    }
                }
            }
            // no 2 duplicates edge
            if (graph.keySet() != null) {
                for (Node n : graph.keySet()) {
                    Set<Edge> temp = new HashSet<>(graph.get(n));
                    if (!(graph.get(n).size() == temp.size())) {
                        throw new RuntimeException("Duplicate edges exist for a node.");
                    }
                }
            }
        }

    }

    /**
     * Add a node to current graph
     * Return true if and only if node is added to graph, otherwise, false
     *
     * @param n node to be added to the graph
     * @return true if and only if node is added to graph, otherwise, false
     * @throws IllegalArgumentException if n is null
     * @spec.modifies this
     * @spec.effects Adds node to current graph
     */
    public boolean addNode(Node<T> n) {
        checkRep();
        if (n == null) {
            throw new IllegalArgumentException("invalid input: node is null!");
        }
        if (!graph.containsKey(n)) {
            graph.put(n, new ArrayList<Edge<E>>());
            checkRep();
            return true;
        }
        checkRep();
        return false;
    }

    /**
     * Add an edge that starting from s in current graph
     * If the node edge pointing to doesn't exist, create that node to the graph
     * Return true if and only if edge is added to graph, otherwise, false
     *
     * @param s node the edge starting from
     * @param e node the edge pointing to
     * @param label edge label
     * @return true if and only if edge is added from graph, otherwise, false
     * @throws IllegalArgumentException if s is null or e is null or label is null
     * @spec.requires e != null AND s != null AND label != null
     * @spec.modifies this
     * @spec.effects Adds edge from current graph
     */
    public boolean addEdge(Node<T> s, Node<T> e, E label) {
        checkRep();
        if (label == null) {
            throw new IllegalArgumentException("label cannot be null");
        }
        if (s == null || e == null) {
            throw new IllegalArgumentException("Node cannot be null.");
        }
        addNode(s);
        Edge<E> newEdge = new Edge<E>(label, e);
        List<Edge<E>> edgeList = graph.get(s);
        if (!edgeList.contains(newEdge)) {
            edgeList.add(newEdge);
            checkRep();
            return true;
        }
        checkRep();
        return false;
    }

    /**
     * Return all children of Node n
     *
     * @param n node whose children we want to get
     * @return List containing all the children of node n and their edges
     * @throws IllegalArgumentException if n is null
     * @spec.requires n != null
     */
    public List<String[]> listChildren(Node<T> n) {
        checkRep();
        if (n == null) {
            throw new IllegalArgumentException("Node cannot be null");
        }
        List<String[]> stringList = new ArrayList<>();
        List<Edge<E>> edgeList = graph.get(n);
        for (Edge<E> e : edgeList) {
            String[] temp = {e.getChildNode().getLabel().toString(), e.toString()};
            stringList.add(temp);
        }
        checkRep();
        return stringList;
    }

    /**
     * Return all the nodes of current graph
     *
     * @return NodeList containing all the nodes of current graph
     */
    public List<Node<T>> listNodes() {
        checkRep();
        List<Node<T>> allNodes = new ArrayList<>();
        for (Node<T> n : graph.keySet()) {
            allNodes.add(new Node<T>(n.getLabel()));
        }
        checkRep();
        return allNodes;
    }

    /**
     * Return true if n exists in current graph
     *
     * @param n node whose children we want to get
     * @return true if n exists in current graph
     * @throws IllegalArgumentException if n is null
     * @spec.requires n != null
     */
    public boolean containsNode(Node<T> n) {
        checkRep();
        if (n == null) {
            throw new IllegalArgumentException("Node cannot be null");
        }
        if (!graph.containsKey(n)) {
            checkRep();
            return false;
        } else {
            checkRep();
            return true;
        }
    }

    /**
     * Return number of nodes in the graph
     *
     * @return number of nodes in the graph
     */
    public int size() {
        checkRep();
        return graph.keySet().size();
    }

    /**
     * Return true if graph is empty
     *
     * @return true if graph is empty
     */
    public boolean isEmpty() {
        checkRep();
        return graph.keySet().size() == 0;
    }
    /**
     * Return all the edges of current node
     *
     * @param n node whose edge we want to get
     * @throws IllegalArgumentException if n is null or n doesn't exist in graph
     * @spec.requires n != null
     * @return EdgeList containing all the edges of current node
     */
    public List<Edge<E>> listEdges(Node<T> n) {
        checkRep();
        if (n == null) {
            throw new IllegalArgumentException("Node cannot be null");
        }
        if (!graph.containsKey(n)) {
            throw new IllegalArgumentException("Node is not in the graph");
        }
        List<Edge<E>> allEdges = new ArrayList<>();
        for (Edge<E> e : graph.get(n)) {
            allEdges.add(e);
        }
        checkRep();
        return allEdges;
    }

}
