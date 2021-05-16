package bearmaps.proj2c;

import bearmaps.hw4.streetmap.Node;
import bearmaps.hw4.streetmap.StreetMapGraph;
import bearmaps.lab9.MyTrieSet;
import bearmaps.proj2ab.KDTree;
import bearmaps.proj2ab.Point;

import java.util.*;

/**
 * An augmented graph that is more powerful that a standard StreetMapGraph.
 * Specifically, it supports the following additional operations:
 *
 *
 * @author Alan Yao, Josh Hug, ________
 */
public class AugmentedStreetMapGraph extends StreetMapGraph {

    public AugmentedStreetMapGraph(String dbPath) {
        super(dbPath);
        // You might find it helpful to uncomment the line below:
        List<Node> nodes = this.getNodes();
    }


    /**
     * For Project Part II
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    public long closest(double lon, double lat) {
        List<Node> nodes = this.getNodes();
        List<Point> points = new ArrayList<>();
        Map<Point, Node> point_node = new HashMap<>();
        for(Node cur_Node: nodes) {
            if(!this.neighbors(cur_Node.id()).isEmpty()) {
                Point cur_Point = new Point(cur_Node.lon(),cur_Node.lat());
                point_node.put(cur_Point, cur_Node);
                points.add(cur_Point);
            }
        }
        KDTree vertices = new KDTree(points);
        Point tar_vertex = vertices.nearest(lon,lat);
        long id = point_node.get(tar_vertex).id();
        return id;
    }


    /**
     * For Project Part III (gold points)
     * In linear time, collect all the names of OSM locations that prefix-match the query string.
     * @param prefix Prefix string to be searched for. Could be any case, with our without
     *               punctuation.
     * @return A <code>List</code> of the full names of locations whose cleaned name matches the
     * cleaned <code>prefix</code>.
     */
    public List<String> getLocationsByPrefix(String prefix) {
        List<Node> nodes = this.getNodes();
        MyTrieSet allLocations = new MyTrieSet();
        Map<String, String> cleanToRaw = new HashMap<>();
        for(Node singleNode: nodes) {
            if(singleNode.name() != null) {
                String cleanName = cleanString(singleNode.name());
                cleanToRaw.put(cleanName, singleNode.name());
                allLocations.add(cleanName);
            }
        }
        String cleanPrefix = cleanString(prefix);
        List<String> matchedCleanName = allLocations.keysWithPrefix(cleanPrefix);
        List<String> matchedFullName = new LinkedList<>();
        for(String cleanName: matchedCleanName) {
            String fullName = cleanToRaw.get(cleanName);
            matchedFullName.add(fullName);
        }
        return matchedFullName;
    }


    /**
     * For Project Part III (gold points)
     * Collect all locations that match a cleaned <code>locationName</code>, and return
     * information about each node that matches.
     * @param locationName A full name of a location searched for.
     * @return A list of locations whose cleaned name matches the
     * cleaned <code>locationName</code>, and each location is a map of parameters for the Json
     * response as specified: <br>
     * "lat" -> Number, The latitude of the node. <br>
     * "lon" -> Number, The longitude of the node. <br>
     * "name" -> String, The actual name of the node. <br>
     * "id" -> Number, The id of the node. <br>
     */
    public List<Map<String, Object>> getLocations(String locationName) {
        List<Node> nodes = this.getNodes();
        String cleanName = cleanString(locationName);
        List<Map<String, Object>> locations = new LinkedList<>();
        for(Node singleNode: nodes) {
            if(singleNode.name() != null) {
                String cleanCurNode = cleanString(singleNode.name());
                if(cleanCurNode.equals(cleanName)) {
                    Map<String, Object> location = new HashMap<>();
                    double lat = singleNode.lat();
                    double lon = singleNode.lon();
                    String name = singleNode.name();
                    long id = singleNode.id();
                    location.put("lat",lat);
                    location.put("lon",lon);
                    location.put("name",name);
                    location.put("id",id);
                    locations.add(location);
                }
            }
        }
        return locations;
    }


    /**
     * Useful for Part III. Do not modify.
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    private static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

}
