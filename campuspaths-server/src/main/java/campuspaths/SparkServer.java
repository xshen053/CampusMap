/*
 * Copyright (C) 2023 Xiaxi Shen.  All rights reserved.
 */

package campuspaths;

import campuspaths.utils.CORSFilter;
import com.google.gson.Gson;
import pathfinder.CampusMap;
import pathfinder.datastructures.Path;
import pathfinder.datastructures.Point;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

public class SparkServer {

    public static void main(String[] args) {
        // create new map
        CampusMap map = new CampusMap();
        CORSFilter corsFilter = new CORSFilter();
        corsFilter.apply();
        // The above two lines help set up some settings that allow the
        // React application to make requests to the Spark server, even though it
        // comes from a different server.
        // You should leave these two lines at the very beginning of main().

        Spark.get("/find_path", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                // In spark, you can get a value of a query string by calling
                // request.queryParams with the key of the item you're looking for.
                // For this example, requests should look like:
                //          "/hello-someone?person=Andrew"
                // for example.
                // If there is no "person=" in the request, queryParams returns null.
                String start = request.queryParams("start");
                String end = request.queryParams("end");
                Path<Point> path = map.findShortestPath(start, end);
                Gson gson = new Gson();
                return gson.toJson(path);
            }
        });
    }

}
