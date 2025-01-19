/**
 * The {@code MiniBus} class extends the {@code Bus} class to represent a specific type of bus
 * with a distinct seating arrangement and bus type identifier. This class overrides several methods
 * to provide the specifics for a minibus, including the seating layout and the number of seats per row.
 */
public class MiniBus extends Bus {

    /**
     * Returns the type of bus. This override specifies that the bus is a "Minibus".
     *
     * @return the string "Minibus" as the type of this bus.
     */
    @Override
    public String getBusType() {
        return "Minibus";
    }

    /**
     * Provides a seating layout representation for the minibus. Each row in the minibus is represented
     * by two seats, where 'X' denotes a sold seat and '*' denotes an available seat. This layout
     * is presented row by row.
     *
     * @return a string representation of the minibus's seating layout, row by row.
     */
    @Override
    public String getSeatingLayout() {
        StringBuilder builder = new StringBuilder();
        int seatNumber = 1;
        for (int row = 0; row < getRows(); row++) {
            if (getSoldSeatNumbers().contains(seatNumber)) {
                builder.append("X ");
            } else {
                builder.append("* ");
            }
            seatNumber++;
            if (getSoldSeatNumbers().contains(seatNumber)) {
                builder.append("X");
            } else {
                builder.append("*");
            }
            seatNumber++;

            builder.append("\n");
        }
        return builder.toString();
    }

    /**
     * Specifies the number of seats per row in a minibus, which is consistently two.
     *
     * @return the number of seats per row, specifically 2 for a minibus.
     */
    @Override
    public int getSeatsPerRow() {
        return 2; // Minibuses have 2 seats per row
    }

}
