/**
 * This class represents a road with a distance, starting point, ending point, and an identifier.
 * It implements the Comparable interface to allow for sorting based on distance and then by ID.
 */
public class Road implements Comparable<Road> {
    private final int distance;
    private final String pointB;
    private final String pointF;

    private final int id;

    /**
     * Constructs a Road object with specified starting point, ending point, distance, and identifier.
     *
     * @param pointB   the starting point of the road
     * @param pointF   the ending point of the road
     * @param distance the distance of the road
     * @param id       the identifier for the road
     */
    public Road(String pointB, String pointF, int distance, int id) {
        this.distance = distance;
        this.pointB = pointB;
        this.pointF = pointF;
        this.id = id;
    }

    /**
     * Compares this road with another road for order.
     * Roads are ordered first by distance and then by ID if distances are equal.
     *
     * @param other the other road to be compared
     * @return a negative integer, zero, or a positive integer as this road is less than, equal to, or greater than the specified road
     */
    @Override
    public int compareTo(Road other) {
        if(this.distance!=other.distance){
            return this.distance - other.distance;
        }
        return this.id - other.id;
    }

    /**
     * Returns the starting point of the road.
     *
     * @return the starting point of the road
     */
    public String getPointB() {
        return pointB;
    }

    /**
     * Returns the ending point of the road.
     *
     * @return the ending point of the road
     */
    public String getPointF() {
        return pointF;
    }
    /**
     * Returns the distance of the road.
     *
     * @return the distance of the road
     */
    public int getDistance() {
        return distance;
    }
    /**
     * Returns the identifier of the road.
     *
     * @return the identifier of the road
     */
    public int getId() {
        return id;
    }
}



