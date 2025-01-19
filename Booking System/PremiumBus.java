/**
 * The {@code PremiumBus} class extends the {@code Bus} class to represent a more luxurious bus type
 * with specific attributes such as refund cuts and premium fees for certain seats.
 * This class also provides a specific seating layout and bus type identifier for premium buses.
 */
public class PremiumBus extends Bus {
    private Integer refundCut;
    private Integer premiumFee;

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
     * Returns the additional fee percentage that is applied to the price of premium seats on this bus.
     *
     * @return the premium fee as an Integer.
     */
    @Override
    public Integer getPremiumFee() {
        return premiumFee;
    }

    /**
     * Sets the premium fee percentage for premium seats on this bus.
     *
     * @param premiumFee the new premium fee percentage.
     */
    public void setPremiumFee(Integer premiumFee) {
        this.premiumFee = premiumFee;
    }

    /**
     * Returns the type of bus. This override specifies that the bus is a "Premium" type,
     * indicating a higher level of service and comfort.
     *
     * @return the string "Premium" as the type of this bus.
     */
    @Override
    public String getBusType() {
        return "Premium";
    }

    /**
     * Provides a seating layout representation for the premium bus. Each row is represented with three seats:
     * one premium seat followed by two regular seats, where 'X' denotes a sold seat and '*' denotes an available seat.
     * Premium and regular seats are separated by a vertical bar ('|').
     *
     * @return a string representation of the bus's seating layout, row by row.
     */
    @Override    public String getSeatingLayout() {
        StringBuilder builder = new StringBuilder();
        int seatNumber = 1;
        for (int row = 0; row < getRows(); row++) {
            // Print premium seat first
            if (getSoldSeatNumbers().contains(seatNumber)) {
                builder.append("X ");
            } else {
                builder.append("* ");
            }
            seatNumber++;

            builder.append("| "); // Separator for premium and standard seats

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
     * Specifies the number of seats per row in a premium bus, which is consistently three.
     *
     * @return the number of seats per row, specifically 3 for a premium bus.
     */
    @Override
    public int getSeatsPerRow() {
        return 3;
    }
}
