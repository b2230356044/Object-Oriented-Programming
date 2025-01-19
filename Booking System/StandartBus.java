/**
 * The {@code StandartBus} class extends the {@code Bus} class to represent a standard bus configuration.
 * It provides additional functionality specific to standard buses such as handling refund cuts
 * and defining a specific seating layout.
 */
public class StandartBus extends Bus {

    private Integer refundCut;

    /**
     * Returns the percentage cut from the refund amount for tickets of this bus type.
     *
     * @return the refund cut percentage as an Integer.
     */
    public Integer getRefundCut() {
        return refundCut;
    }

    /**
     * Sets the refund cut percentage for this bus type, which affects the refund calculation
     * for sold tickets.
     *
     * @param refundCut the new refund cut percentage.
     */
    public void setRefundCut(Integer refundCut) {
        this.refundCut = refundCut;
    }

    /**
     * Returns the type of bus. This override specifies that the bus is a "Standard" type,
     * typically used for regular service without additional luxuries.
     *
     * @return the string "Standard" as the type of this bus.
     */
    @Override
    public String getBusType() {
        return "Standard";
    }

    /**
     * Provides a seating layout representation for the standard bus. Each row is represented with four seats:
     * three seats on one side of an aisle and one seat on the other side, where 'X' denotes a sold seat
     * and '*' denotes an available seat. The seats are separated by a vertical bar ('|') in the middle to represent the aisle.
     *
     * @return a string representation of the bus's seating layout, row by row.
     */
    @Override
    public String getSeatingLayout() {
        StringBuilder builder = new StringBuilder();
        int seatNumber = 1;
        for (int row = 0; row < getRows(); row++) {
            for (int seat = 0; seat < 3; seat++) { // 4 seats per row for Standard
                if (getSoldSeatNumbers().contains(seatNumber)) {
                    builder.append("X ");
                } else {
                    builder.append("* ");
                }
                seatNumber++;
                if (seat == 1) builder.append("| "); // Middle of the bus
            }
            if (getSoldSeatNumbers().contains(seatNumber)) {
                builder.append("X");
                seatNumber++;
            } else {
                builder.append("*");
                seatNumber++;
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    /**
     * Specifies the number of seats per row in a standard bus, which is consistently four.
     *
     * @return the number of seats per row, specifically 4 for a standard bus.
     */
    @Override
    public int getSeatsPerRow() {
        return 4;
    }
}
