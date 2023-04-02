package pathfinder.junitTests.textInterface;

import org.junit.Test;
import pathfinder.CampusMap;
import pathfinder.parser.CampusBuilding;

import static org.junit.Assert.assertEquals;

public class testCampusMap {

    private static final CampusMap map = new CampusMap();
    private static final CampusBuilding bag =
            new CampusBuilding("EEB","Electrical Engineering Building (North Entrance)",2159.9587,1694.8192);
    
    /**
     * Test that we get correct building from short name
     */
    @Test
    public void testBuildingFromShortname(){
        CampusBuilding build = map.getBuildingFromShortName("EEB");
        assertEquals("Check that we get correct building.", build, bag);
    }

    /**
     * Test that exception is thrown for invalid shortname
     */
    @Test(expected = IllegalArgumentException.class)
    public void testException(){
        map.findShortestPath("sav","SAV");
    }
}
