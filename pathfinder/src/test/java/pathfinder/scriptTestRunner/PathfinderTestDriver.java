/*
 * Copyright (C) 2023 Xiaxi Shen.  All rights reserved.
 */

package pathfinder.scriptTestRunner;

import java.io.Reader;
import java.io.Writer;

import graph.Graph;
import pathfinder.Dijkstra;
import pathfinder.datastructures.Path;

import java.io.*;
import java.util.*;
/**
 * This class implements a test driver that uses a script file format
 * to test an implementation of Dijkstra's algorithm on a graph.
 */
public class PathfinderTestDriver {

    /**
     * String -> Graph: maps the names of graphs to the actual graph
     **/
    private final Map<String, Graph<String, Double>> graphs = new HashMap<>();
    private final PrintWriter output;
    private final BufferedReader input;

    /**
     * @spec.requires r != null && w != null
     * @spec.effects Creates a new PathfinderTestDriver which reads command from
     * {@code r} and writes results to {@code w}
     **/
    public PathfinderTestDriver(Reader r, Writer w) {
        this.output = new PrintWriter(w);
        this.input = new BufferedReader(r);

    }


    /**
     * @throws IOException if the input or output sources encounter an IOException
     * @spec.effects Executes the commands read from the input and writes results to the output
     **/
    // Leave this method public
    public void runTests() throws IOException {
        String inputLine;
        while ((inputLine = input.readLine()) != null) {
            if ((inputLine.trim().length() == 0) ||
                    (inputLine.charAt(0) == '#')) {
                // echo blank and comment lines
                output.println(inputLine);
            } else {
                // separate the input line on white space
                StringTokenizer st = new StringTokenizer(inputLine);
                if (st.hasMoreTokens()) {
                    String command = st.nextToken();

                    List<String> arguments = new ArrayList<>();
                    while (st.hasMoreTokens()) {
                        arguments.add(st.nextToken());
                    }

                    executeCommand(command, arguments);
                }
            }
            output.flush();
        }
    }

    private void executeCommand(String command, List<String> arguments) {
        try {
            switch (command) {
                case "CreateGraph":
                    createGraph(arguments);
                    break;
                case "AddNode":
                    addNode(arguments);
                    break;
                case "AddEdge":
                    addEdge(arguments);
                    break;
                case "ListNodes":
                    listNodes(arguments);
                    break;
                case "ListChildren":
                    listChildren(arguments);
                    break;
                case "FindPath":
                    findPath(arguments);
                    break;
                default:
                    output.println("Unrecognized command: " + command);
                    break;
            }
        } catch (Exception e) {
            String formattedCommand = command;
            formattedCommand += arguments.stream().reduce("", (a, b) -> a + " " + b);
            output.println("Exception while running command: " + formattedCommand);
            e.printStackTrace(output);
        }
    }

    private void createGraph(List<String> arguments) {
        if (arguments.size() != 1) {
            throw new PathfinderTestDriver.CommandException("Bad arguments to CreateGraph: " + arguments);
        }

        String graphName = arguments.get(0);
        createGraph(graphName);
    }

    private void createGraph(String graphName) {
        Graph<String, Double> g1 = new Graph<>();
        graphs.put(graphName, g1);
        output.println("created graph " + graphName);
    }

    private void addNode(List<String> arguments) {
        if (arguments.size() != 2) {
            throw new PathfinderTestDriver.CommandException("Bad arguments to AddNode: " + arguments);
        }

        String graphName = arguments.get(0);
        String nodeName = arguments.get(1);

        addNode(graphName, nodeName);
    }

    private void addNode(String graphName, String nodeName) {
        Graph<String, Double> g1 = graphs.get(graphName);
        g1.addNode(g1.new Node<String>(nodeName));
        output.println("added node " + nodeName + " to " + graphName);
    }

    private void addEdge(List<String> arguments) {
        if (arguments.size() != 4) {
            throw new PathfinderTestDriver.CommandException("Bad arguments to AddEdge: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        String childName = arguments.get(2);
        String edgeLabel = arguments.get(3);

        addEdge(graphName, parentName, childName, Double.valueOf(edgeLabel));
    }

    private void addEdge(String graphName, String parentName, String childName,
                         Double edgeLabel) {

        Graph<String, Double> g1 = graphs.get(graphName);

        g1.addEdge(g1.new Node<String>(parentName), g1.new Node<String>(childName), edgeLabel);
        String e = String.format("%.3f", edgeLabel);
        output.println("added edge " + e + " from " + parentName + " to " + childName + " in " + graphName);
    }

    private void listNodes(List<String> arguments) {
        if (arguments.size() != 1) {
            throw new PathfinderTestDriver.CommandException("Bad arguments to ListNodes: " + arguments);
        }

        String graphName = arguments.get(0);
        listNodes(graphName);
    }

    private void listNodes(String graphName) {

        Graph<String, Double> g1 = graphs.get(graphName);
        List<Graph<String, Double>.Node<String>> n = g1.listNodes();
        output.print(graphName + " contains:");
        for (Graph<String, Double>.Node<String> stringNode : n) {
            output.print(" " + stringNode.getLabel());
        }
        output.println();
    }

    private void listChildren(List<String> arguments) {
        if (arguments.size() != 2) {
            throw new PathfinderTestDriver.CommandException("Bad arguments to ListChildren: " + arguments);
        }
        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        listChildren(graphName, parentName);
    }

    private void listChildren(String graphName, String parentName) {

        Graph<String, Double> g1 = graphs.get(graphName);
        Graph<String, Double>.Node<String> n1 = g1.new Node<String>(parentName);
        List<String[]> n = g1.listChildren(n1);
        n.sort((o1, o2) -> o2[1].compareTo(o1[1]));
        output.print("the children of " + parentName + " in " + graphName + " are:");
        for (int i = n.size() - 1; i > -1; i--) {
            String format = String.format("%.3s", n.get(i)[1]);
            output.print(" " + n.get(i)[0] + "(" + format + ")");
        }
        output.println();
    }

    public void findPath(List<String> arguments) {
        if (arguments.size() != 3) {
            throw new PathfinderTestDriver.CommandException("Bad arguments to findPath: " + arguments);
        }
        String name = arguments.get(0);
        String start = arguments.get(1);
        String end = arguments.get(2);
        findPath(name, start, end);
    }

    private void findPath(String name, String start, String end) {
        Graph<String, Double> g1 = graphs.get(name);
        Dijkstra<String> di = new Dijkstra<>();
        Graph<String, Double>.Node<String> n1 = g1.new Node<String>(start);
        Graph<String, Double>.Node<String> n2 = g1.new Node<String>(end);
        if(!g1.listNodes().contains(n1) && !g1.listNodes().contains(n2)){
            output.println("unknown: " + n1);
            output.println("unknown: " + n2);
            return;
        }
        if(!g1.listNodes().contains(n1)){
            output.println("unknown: " + n1);
            return;
        }
        if(!g1.listNodes().contains(n2)){
            output.println("unknown: " + n2);
            return;
        }
        output.println("path from " + n1 + " to " + n2 + ":");
        if(n1.equals(n2)){
            output.println("total cost: 0.000");
            return;
        }
        try  {
            Path<String> minPath = di.shortestPath(g1, n1.getLabel(), n2.getLabel());
            for (Path<String>.Segment s : minPath) {
                if(s.getStart().equals(s.getEnd())){
                    continue;
                }
                String temp = String.format("%.3f",s.getCost());
                output.println(s.getStart() + " to " + s.getEnd() + " with weight " + temp);
            }
            String cost = String.format("%.3f",minPath.getCost());
            output.println("total cost: " + cost);
        } catch (RuntimeException e) {
            output.println("no path found");
        }
    }

    /**
     * This exception results when the input file cannot be parsed properly
     **/
    static class CommandException extends RuntimeException {

        public CommandException(String s) {
            super(s);
        }

        public static final long serialVersionUID = 3495;
    }
}
