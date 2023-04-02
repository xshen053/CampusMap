package graph.junitTests;

import graph.Graph;
import org.junit.Before;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.Timeout;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
/**
 * GraphTests is a glassbox test of the Graph class.
 */
public final class GraphTests {
    @Rule public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max per method tested

    private static Graph<String, String> g1;
    private static Graph<String, String>.Node<String> n1;
    private static Graph<String, String>.Node<String> n2;
    private static Graph<String, String>.Node<String> n3;
    private static Graph<String, String>.Node<String> n4;
    private static Graph<String, String>.Edge<String> e1;
    private static Graph<String, String>.Edge<String> e2;
    private static Graph<String, String>.Edge<String> e3;
    private static Graph<String, String>.Edge<String> e4;

    @Before
    public void setUp(){
        g1 = new Graph<>();
        n1  = g1.new Node<String>("a");
        n2  = g1.new Node<String>("b");
        n3  = g1.new Node<String>("c");
        n4  = g1.new Node<String>("d");
        e1 = g1.new Edge<String>("e1", n2);
        e2 = g1.new Edge<String>("e2", n2);
        e3 = g1.new Edge<String>("e1", n2);
    }

    /**
     * Test constructor
     */
    @Test
    public void testConstructor(){
        assertEquals(0,g1.listNodes().size());
    }

    /**
     * Test node equals
     */
    @Test
    public void testNodeEquals(){
        assertTrue("Checks to see if node is equal",n1.equals(n1));
        assertFalse("Checks that the two nodes are not equal", n1.equals(n2));
    }

    /**
     * Test edge equals
     */
    @Test
    public void testEdgeEquals(){
        assertFalse("Checks that two edges are not equal", e1.equals(e2));
        assertTrue("Checks that a edge is equal to itself", e1.equals(e1));
        assertTrue("Checks that two edge between the same node and having the same label is equal", e1.equals(e3));
    }

    /**
     * Test adding duplicate edges
     */
    @Test
    public void testAddEdges(){
        g1.addEdge(n1, n2, "AB");
        g1.addEdge(n1, n2, "AB");
        List<Graph<String, String>.Edge<String>> e = new ArrayList<>();
        e.add(g1.new Edge<String>("AB", n2));
        assertEquals(e, g1.listEdges(n1));
    }

    /**
     * Test adding duplicate nodes
     */
    @Test
    public void testAddNode(){
        g1.addNode(g1.new Node<String>("a"));
        g1.addNode(g1.new Node<String>("a"));
        List<Graph<String, String>.Node<String>> test = new ArrayList<>();
        test.add(n1);
        assertEquals(test, g1.listNodes());
    }

    /**
     * Test size() of nodes in graph
     */
    @Test
    public void testNodeNumber(){
        assertEquals(0,g1.size());
        g1.addNode(n1);
        assertEquals(1,g1.size());
        g1.addNode(n2);
        assertEquals(2,g1.size());
    }

}