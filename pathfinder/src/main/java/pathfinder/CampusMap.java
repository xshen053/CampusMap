/*
 * Copyright (C) 2023 Xiaxi Shen.  All rights reserved.
 */

package pathfinder;

import graph.Graph;
import pathfinder.datastructures.Path;
import pathfinder.datastructures.Point;
import pathfinder.parser.CampusBuilding;
import pathfinder.parser.CampusPath;
import pathfinder.parser.CampusPathsParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class CampusMap to process data to find shortest path
 */
public class CampusMap implements ModelAPI {

    //RI: cb !=null and cp != null
    //AF: represents all building on campus and routes on campus
    private static final List<CampusBuilding> cb = CampusPathsParser.parseCampusBuildings("campus_buildings.csv");
    private static final List<CampusPath> cp = CampusPathsParser.parseCampusPaths("campus_paths.csv");

    @Override
    public boolean shortNameExists(String shortName) {
        checkRep();
        boolean exists = false;
        for(CampusBuilding b: cb){
            if (b.getShortName().equals(shortName)) {
                exists = true;
                break;
            }
        }
        checkRep();
        return exists;
    }

    @Override
    public String longNameForShort(String shortName) {
        checkRep();
        for(CampusBuilding b: cb){
            if (b.getShortName().equals(shortName)) {
                return b.getLongName();
            }
        }
        checkRep();
        throw new IllegalArgumentException("Short name does not exist.");
    }

    @Override
    public Map<String, String> buildingNames() {
        checkRep();
        Map<String, String> result = new HashMap<>();
        for(CampusBuilding b: cb){
            result.put(b.getShortName(), b.getLongName());
        }
        checkRep();
        return result;
    }

    @Override
    public Path<Point> findShortestPath(String startShortName, String endShortName) {
        checkRep();
        if(startShortName == null || endShortName == null){
            throw new IllegalArgumentException("Start and end buildings cannot be null");
        }
        if(!shortNameExists(startShortName) || !shortNameExists(endShortName)){
            throw new IllegalArgumentException("Both buildings must be on campus");
        }
        Graph<Point, Double> campus = new Graph<>();
        for(CampusPath p : cp){
            Point s = new Point(p.getX1(),p.getY1());
            Point e = new Point(p.getX2(),p.getY2());
            campus.addEdge(campus.new Node<Point>(s), campus.new Node<Point>(e), p.getDistance());
        }
        CampusBuilding begin = getBuildingFromShortName(startShortName);
        CampusBuilding end = getBuildingFromShortName(endShortName);
        Dijkstra<Point> di = new Dijkstra<>();
        checkRep();
        return di.shortestPath(campus, new Point(begin.getX(), begin.getY()), new Point(end.getX(), end.getY()));
    }

    /**
     * Returns a Campus Building with the short name
     * @param shortName The short name of the building we want to find CampusBuildin of
     * @return CampusBuilding with the short name
     */
    public CampusBuilding getBuildingFromShortName(String shortName){
        checkRep();
        for(CampusBuilding b: cb){
            if(b.getShortName().equals(shortName)) {
                return b;
            }
        }
        checkRep();
        throw new IllegalArgumentException("No such building with shortname.");
    }

    // Checks RI hold
    private void checkRep(){
        if(cb == null){
            throw new IllegalArgumentException("Building cannot be null");
        }
        if(cp == null){
            throw new IllegalArgumentException("Paths cannot be null");
        }
    }
}
