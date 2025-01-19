import java.util.*;
 /**
  * This class handles the analysis and manipulation of a road map.
  * It maintains a list of roads and provides methods to add roads,
  * find the minimum branching for the road network, and calculate the shortest path between two points.
  */
    public class MapAnalyzerAlgorithm {
    private final List<Road> roads = new ArrayList<>();

    private final Map<String, List<Road>> adjList = new HashMap<>();
    private final Map<String, List<Road>> orjAdjList = new HashMap<>();

    /**
    * Adds a road to the map and updates the adjacency lists for both directed and undirected graphs.
    *
    * @param pointB   the starting point of the road
    * @param pointF   the ending point of the road
    * @param distance the distance of the road
    * @param id       the identifier for the road
    */

    public void addRoad(String pointB, String pointF, int distance, int id) {
     roads.add(new Road(pointB, pointF, distance, id));
     adjList.computeIfAbsent(pointB, k -> new ArrayList<>()).add(new Road(pointB, pointF, distance, id));
     adjList.computeIfAbsent(pointF, k -> new ArrayList<>()).add(new Road(pointF, pointB, distance, id));
     orjAdjList.computeIfAbsent(pointB, k -> new ArrayList<>()).add(new Road(pointB, pointF, distance, id));
    }
    /**
    * Computes the minimum branching tree.
    *
    * @return a list of roads that form the barely connected map.
    */
    public List<Road> minimumBranch() {
     Collections.sort(roads);
     Union uf = new Union();
     List<Road> result = new ArrayList<>();

     for (Road road : roads) {
         uf.makeSet(road.getPointB());
         uf.makeSet(road.getPointF());
     }

     for (Road road : roads) {
         if (!Objects.equals(uf.find(road.getPointB()), uf.find(road.getPointF()))) {
             result.add(road);
             uf.union(road.getPointB(), road.getPointF());
         }
     }

     return result;
    }
    /**
    * Calculates the shortest path between two points.
    * Optionally uses a list of roads to consider.
    *
    * @param start starting point of the path
    * @param end   ending point of the path
    * @param mb   list of roads to use for calculating the path; if null, uses the original adjacency list
    * @return a list of roads representing the shortest path from start to end
    */
    public List<Road> ShortestPath(String start, String end, List<Road> mb) {
     Map<String, List<Road>> newAdjList = mb == null ? adjList : new HashMap<>();
     if (mb != null) {
         for (Road road : mb) {
             newAdjList.computeIfAbsent(road.getPointB(), k -> new ArrayList<>()).add(road);
             newAdjList.computeIfAbsent(road.getPointF(), k -> new ArrayList<>()).add(new Road(road.getPointF(), road.getPointB(), road.getDistance(), road.getId()));
         }
     }


     Map<String, Integer> distances = new HashMap<>();
     Map<String, Road> path = new HashMap<>();
     PriorityQueue<Road> pq = new PriorityQueue<>((r1, r2) -> {if(r1.getDistance() != r2.getDistance()) {
         return r1.getDistance() - r2.getDistance();
     }
         return r1.getId() - r2.getId();
    });
     Set<String> visited = new HashSet<>();

     for (String vertex : newAdjList.keySet()) {
         distances.put(vertex, Integer.MAX_VALUE);
     }
     distances.put(start, 0);
     pq.add(new Road(start, start, 0, -1));

     while (!pq.isEmpty()) {
         Road current = pq.poll();
         if (!visited.add(current.getPointF())) continue;
         for (Road edge : newAdjList.get(current.getPointF())) {
             if (visited.contains(edge.getPointF())) continue;
             int newDist = distances.get(current.getPointF()) + edge.getDistance();
             if (newDist < distances.get(edge.getPointF())) {
                 distances.put(edge.getPointF(), newDist);
                 path.put(edge.getPointF(), edge);
                 pq.add(new Road(current.getPointB(), edge.getPointF(), newDist, edge.getId()));
             }
         }
     }

     List<Road> route = new ArrayList<>();
     String current = end;
     while (!current.equals(start)) {
         Road step = path.get(current);
         if (step == null) {
             break;  // No path found
         }

         for(List<Road> adj : orjAdjList.values()) {
             for (Road road : adj) {
                 if (road.getId() == (step.getId())) {
                     route.add(road);
                 }
             }
         }

         current = step.getPointB();
     }
     Collections.reverse(route);
     return route;
    }

    /**
    * Generates a detailed output of the analysis including the fastest route in the original map,
    * and the fastest route in the Barely Connected Map.
    *
    * @param start starting point of the path
    * @param end   ending point of the path
    * @return a StringBuilder containing the detailed analysis
    */
    public StringBuilder printOutput(String start, String end) {
     List<Road> fastestRoute = ShortestPath(start, end, null);
     List<Road> BCM = minimumBranch();
     List<Road> fastestRouteInMB = ShortestPath(start, end, BCM);


     // Calculate total distances
     int originalMapDistance = fastestRoute.stream().mapToInt(Road::getDistance).sum();
     int barelyConnectedMapDistance = fastestRouteInMB.stream().mapToInt(Road::getDistance).sum();

     // Calculate material usage
     double originalMaterialUsage = roads.stream().mapToInt(Road::getDistance).sum();
     double bcmMaterialUsage = BCM.stream().mapToInt(Road::getDistance).sum();

     // Output the results
     StringBuilder sb = new StringBuilder();

     sb.append("Fastest Route from ").append(start).append(" to ").append(end).append(" (").append(originalMapDistance).append(" KM):\n");

     fastestRoute.forEach(edge -> sb.append(edge.getPointB()).append("\t").append(edge.getPointF()).append("\t").append(edge.getDistance()).append("\t").append(edge.getId()).append("\n"));

     sb.append("Roads of Barely Connected Map is:\n");
     BCM.forEach(edge -> sb.append(edge.getPointB()).append("\t").append(edge.getPointF()).append("\t").append(edge.getDistance()).append("\t").append(edge.getId()).append("\n"));

     sb.append("Fastest Route from ").append(start).append(" to ").append(end).append(" on Barely Connected Map (").append(barelyConnectedMapDistance).append(" KM):\n");
     fastestRouteInMB.forEach(edge -> sb.append(edge.getPointB()).append("\t").append(edge.getPointF()).append("\t").append(edge.getDistance()).append("\t").append(edge.getId()).append("\n"));

     sb.append("Analysis:\n");
     sb.append(String.format("Ratio of Construction Material Usage Between Barely Connected and Original Map: %.2f\n", bcmMaterialUsage / originalMaterialUsage));
     sb.append(String.format("Ratio of Fastest Route Between Barely Connected and Original Map: %.2f", (double) barelyConnectedMapDistance / originalMapDistance));

     return sb;
    }


    }
