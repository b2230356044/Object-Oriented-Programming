import java.util.ArrayList;
import java.util.List;

/**
 * The {@code Bus} class represents a bus involved in a voyage from one city to another.
 * It stores the bus's identification details, route information, seating capacity, price per seat,
 * list of sold seats, total revenue generated, and a premium fee applicable for certain seats.
 */
public class Bus {

    private Integer id;
    private String fromCity;
    private String toCity;
    private Integer rows;
    private Double price;
    private List<Integer> soldSeatNumbers;
    private Double revenue;
    private Integer premiumFee;


    /**
     * Returns the premium fee added to the base price for premium seats.
     *
     * @return the premium fee as an Integer.
     */
    public Integer getPremiumFee() {
        return premiumFee;
    }

    /**
     * Constructs a new {@code Bus} instance with default values.
     * Initializes the list of sold seat numbers and sets initial revenue to zero.
     */
    public Bus() {
        soldSeatNumbers = new ArrayList<>();
        revenue = 0.0;
    }

    /**
     * Returns the bus's unique identifier.
     *
     * @return the ID of the bus as an Integer.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the bus's unique identifier.
     *
     * @param id the new ID of the bus.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Returns the departure city of the bus voyage.
     *
     * @return the departure city as a String.
     */
    public String getFromCity() {
        return fromCity;
    }

    /**
     * Sets the departure city for the bus voyage.
     *
     * @param fromCity the city from where the bus will depart.
     */
    public void setFromCity(String fromCity) {
        this.fromCity = fromCity;
    }
    /**
     * Returns the destination city of the bus voyage.
     *
     * @return the destination city as a String.
     */
    public String getToCity() {
        return toCity;
    }

    /**
     * Sets the destination city for the bus voyage.
     *
     * @param toCity the city where the bus will arrive.
     */
    public void setToCity(String toCity) {
        this.toCity = toCity;
    }

    /**
     * Returns the number of rows of seats in the bus.
     *
     * @return the number of seat rows as an Integer.
     */
    public Integer getRows() {
        return rows;
    }

    /**
     * Sets the number of rows of seats in the bus.
     *
     * @param rows the new number of seat rows in the bus.
     */
    public void setRows(Integer rows) {
        this.rows = rows;
    }
    /**
     * Returns the price of a regular seat on the bus.
     *
     * @return the price of a seat as a Double.
     */
    public Double getPrice() {
        return price;
    }

    /**
     * Sets the price of a regular seat on the bus.
     *
     * @param price the new price of a seat.
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * Returns the list of seat numbers that have been sold.
     *
     * @return a list of sold seat numbers as List<Integer>.
     */
    public List<Integer> getSoldSeatNumbers() {
        return soldSeatNumbers;
    }

    /**
     * Sets the list of seat numbers that have been sold.
     *
     * @param soldSeatNumbers the new list of sold seat numbers.
     */
    public void setSoldSeatNumbers(List<Integer> soldSeatNumbers) {
        this.soldSeatNumbers = soldSeatNumbers;
    }
    /**
     * Returns the total revenue generated from ticket sales.
     *
     * @return the total revenue as a Double.
     */
    public Double getRevenue() {
        return revenue;
    }

    /**
     * Sets the total revenue generated from ticket sales.
     *
     * @param revenue the new total revenue amount.
     */
    public void setRevenue(Double revenue) {
        this.revenue = revenue;
    }

    /**
     * Returns a string representation of the sold seat numbers, separated by dashes.
     *
     * @return a formatted string of sold seat numbers.
     */
    public String getSeatNumbersAsString() {
        String seatNumbersToPrint = "";
        for (int i = 0; i < soldSeatNumbers.size(); i++) {
            if (i == soldSeatNumbers.size() - 1) {
                seatNumbersToPrint += soldSeatNumbers.get(i);
            } else {
                seatNumbersToPrint += soldSeatNumbers.get(i) + "-";
            }
        }
        return seatNumbersToPrint;
    }

    /**
     * Returns a string representation of the seating layout of the bus.
     * The specific layout implementation is not detailed in this class.
     *
     * @return a string detailing the seating layout.
     */
    public String getSeatingLayout() {
        return ""; // Default implementation, should be overridden in subclasses
    }

    /**
     * Returns the type of the bus. Should be overridden in subclasses to return specific bus type.
     *
     * @return a string representing the type of the bus.
     */
    public String getBusType() {
        return ""; // Default implementation, should be overridden in subclasses
    }

    /**
     * Returns the number of seats per row. This method is meant to be overridden in subclasses
     * to provide the specific number of seats per row for each type of bus.
     *
     * @return the number of seats per row as an integer.
     */
    public int getSeatsPerRow() {
        return 0; // Default implementation, should never be used directly
    }
}