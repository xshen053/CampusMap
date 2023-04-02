/*
 * Copyright (C) 2023 Xiaxi Shen.  All rights reserved.
 */

package pathfinder;

import graph.Graph;
import pathfinder.datastructures.Path;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * This is a class that represents Dijkstra's algorithm for determining the shortest path between
 * two nodes in a graph.
 *
 * @param <T> The type of data that we return Path of
 */
public class Dijkstra<T> {

    //This is not an ADT so there are no RI and AF.
    /**
     * Returns shortestPath between two nodes of a graph
     *
     * @param graph input graph
     * @param start start node
     * @param end end node
     * @throws RuntimeException if there does not exist a path from start to end.
     * @return shortest path from start node to end node
     */
    public Path<T> shortestPath(Graph<T, Double> graph, T start, T end) {
        //Comparator for priority queue
        Comparator<Path<T>> combPath = Comparator.comparingDouble(Path::getCost);
        PriorityQueue<Path<T>> active = new PriorityQueue<>(combPath);
        Set<T> finished = new HashSet<>();
        Path<T> path = new Path<>(start);
        active.add(path.extend(start, 0));
        // Continue while shortest path not found
        while (!active.isEmpty()) {
            Path<T> minPath = active.remove();
            T minDest = minPath.getEnd();
            // If the path's endpoint if our dest. then return
            if (minDest.equals(end)) {
                return minPath;
            }
            // If we already know the shortest path to this node then skip it
            if (finished.contains(minDest)) {
                continue;
            }
            for (Graph<T, Double>.Edge<Double> e : graph.listEdges(graph.new Node<T>(minDest))) {
                    if (!finished.contains(e.getChildNode())) {
                        active.add(minPath.extend(e.getChildNode().getLabel(), e.getLabel()));
                    }
            }
            // Ensures that we know the shortest path to this node
            finished.add(minDest);
        }
        throw new RuntimeException("There does not exist a path from start to end.");
    }

}