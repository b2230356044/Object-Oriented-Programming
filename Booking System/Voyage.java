import java.util.*;

/**
 * The {@code Voyage} class manages the operations related to bus voyages,
 * including initializing voyages, processing ticket sales and refunds,
 * and generating reports. It maintains a map of bus objects, each representing
 * a unique voyage.
 */
public class Voyage {

    /**
     * A map that stores the bus voyages with their ID as the key and the corresponding
     * {@link Bus} object as the value.
     */
    Map<Integer, Bus> busMap = new HashMap<>();

    /**
     * Processes commands related to bus voyage management. This method interprets
     * the command type from the input line and executes the appropriate action,
     * such as initializing a voyage, selling tickets, handling refunds, or generating reports.
     * It logs all operations and errors to the specified output path.
     *
     * @param line        The command line extracted from the input file, containing instructions
     *                    for voyage management.
     * @param output_path The path to the file where outputs and errors are logged.
     */
    public void input(String line, String output_path) {

        String[] splited = line.split("\t");
        String commandType = splited[0];

        // Log the command to the output file
        FileOutput.writeToFile(output_path, "COMMAND: " + line, true, true);

        /**
         * Handles the initialization of a voyage by parsing command details and validating inputs.
         * It creates bus objects based on the type specified and stores them in a map.
         *
         * @param commandType The type of command being processed, e.g., "INIT_VOYAGE".
         * @param splited     An array containing parts of the command string split by spaces.
         * @param busMap      A map where bus objects are stored with their IDs as keys.
         * @param output_path The file path where output messages are written.
         */

        if (commandType.equals("INIT_VOYAGE")) {
            String busType = splited[1];

            int id = Integer.parseInt(splited[2]);
            if (id < 0) {
                String error = String.format("ERROR: %d is not a positive integer, ID of a voyage must be a positive integer!", id);
                FileOutput.writeToFile(output_path, error, true, true);
                return;
            }

            if (busMap.containsKey(id)) {
                String error = "ERROR: There is already a voyage with ID of " + id + "!";
                FileOutput.writeToFile(output_path, error, true, true);
                return;  // Stop processing this command
            }

            String fromCity = splited[3];
            String toCity = splited[4];
            int rows = Integer.parseInt(splited[5]);
            if (rows < 0) {
                String error = String.format("ERROR: %d is not a positive integer, number of seat rows of a voyage must be a positive integer!", rows);
                FileOutput.writeToFile(output_path, error, true, true);
                return;
            }

            double price = Double.parseDouble(splited[6]);
            if (price < 0) {
                String error = String.format("ERROR: %d is not a positive number, price must be a positive number!", (int) price);
                FileOutput.writeToFile(output_path, error, true, true);
                return;
            }


            switch (busType) {
                case "Minibus": {

                    MiniBus miniBus = new MiniBus();
                    miniBus.setId(id);
                    miniBus.setFromCity(fromCity);
                    miniBus.setToCity(toCity);
                    miniBus.setRows(rows);
                    miniBus.setPrice(price);

                    busMap.put(miniBus.getId(), miniBus);

                    String output = ("Voyage " + miniBus.getId() + " was initialized as a minibus (2) " +
                            "voyage from " + miniBus.getFromCity() + " to " + miniBus.getToCity() + " " +
                            "with " + String.format("%.2f", miniBus.getPrice()) + " TL priced " + (2 * miniBus.getRows()) +
                            " regular seats. Note that minibus tickets are not refundable.");
                    FileOutput.writeToFile(output_path, output, true, true);
                    break;
                }
                case "Standard": {
                    int refundCut = Integer.parseInt(splited[7]);

                    if (refundCut < 0) {
                        String error = String.format("ERROR: %d is not an integer that is in range of [0, 100], " +
                                "refund cut must be an integer that is in range of [0, 100]!", refundCut);
                        FileOutput.writeToFile(output_path, error, true, true);
                        return;
                    }


                    StandartBus standartBus = new StandartBus();
                    standartBus.setId(id);
                    standartBus.setFromCity(fromCity);
                    standartBus.setToCity(toCity);
                    standartBus.setRows(rows);
                    standartBus.setPrice(price);
                    standartBus.setRefundCut(refundCut);

                    busMap.put(standartBus.getId(), standartBus);


                    String output = ("Voyage " + standartBus.getId() + " was initialized as a standard (2+2)" +
                            " voyage from " + standartBus.getFromCity() + " to " + standartBus.getToCity()
                            + " with " + String.format("%.2f", standartBus.getPrice()) + " TL priced " + (4 * standartBus.getRows())
                            + " regular seats. Note that refunds will be " + standartBus.getRefundCut()
                            + "% less than the paid amount.");
                    FileOutput.writeToFile(output_path, output, true, true);
                    break;
                }
                case "Premium": {
                    int refundCut = Integer.parseInt(splited[7]);

                    if (refundCut < 0) {
                        String error = String.format("ERROR: %d is not an integer that is in range of [0, 100], " +
                                "refund cut must be an integer that is in range of [0, 100]!", refundCut);
                        FileOutput.writeToFile(output_path, error, true, true);
                        return;
                    }

                    int premiumFree = Integer.parseInt(splited[8]);

                    if (premiumFree < 0) {
                        String error = String.format("ERROR: %d is not a non-negative integer, premium fee must be a non-negative integer!", premiumFree);
                        FileOutput.writeToFile(output_path, error, true, true);
                        return;
                    }


                    PremiumBus premiumBus = new PremiumBus();
                    premiumBus.setId(id);
                    premiumBus.setFromCity(fromCity);
                    premiumBus.setToCity(toCity);
                    premiumBus.setRows(rows);
                    premiumBus.setPrice(price);
                    premiumBus.setRefundCut(refundCut);
                    premiumBus.setPremiumFee(premiumFree);

                    busMap.put(premiumBus.getId(), premiumBus);

                    String output = ("Voyage " + premiumBus.getId() + " was initialized as a premium (1+2)" +
                            " voyage from " + premiumBus.getFromCity() + " to " + premiumBus.getToCity()
                            + " with " + String.format("%.2f", premiumBus.getPrice()) + " TL priced "
                            + (2 * premiumBus.getRows()) + " regular seats and "
                            + String.format("%.2f", (premiumBus.getPrice()
                            + (premiumBus.getPrice() * premiumBus.getPremiumFee() / 100))) + " TL priced "
                            + premiumBus.getRows() + " premium seats. Note that refunds will be "
                            + premiumBus.getRefundCut() + "% less than the paid amount.");
                    FileOutput.writeToFile(output_path, output, true, true);
                    break;
                }
                default:
                    String error = "ERROR: Erroneous usage of \"INIT_VOYAGE\" command!";
                    FileOutput.writeToFile(output_path, error, true, true);
                    break;
            }
        }

        /**
         * Handles ticket sales for a specific voyage. Validates the availability of seats and processes
         * the sale if the seats are available, then updates the bus object in the bus map accordingly.
         *
         * @param commandType The type of command, e.g., "SELL_TICKET".
         * @param splited     An array containing parts of the command string split by spaces.
         * @param busMap      A map storing bus objects, with voyage IDs as keys.
         * @param output_path The file path where output and error messages are written.
         */
        else if (commandType.equals("SELL_TICKET")) {
            if (splited.length != 3) {
                String error = "ERROR: Erroneous usage of \"SELL_TICKET\" command!";
                FileOutput.writeToFile(output_path, error, true, true);
                return;
            }

            int id = Integer.parseInt(splited[1]);
            if (!busMap.containsKey(id)) {
                String error = "ERROR: There is no voyage with ID of " + id + "!";
                FileOutput.writeToFile(output_path, error, true, true);
                return;
            }

            Bus bus = busMap.get(id);
            String[] seatNumbers = splited[2].split("_");
            boolean anySeatSold = false;
            StringBuilder alreadySoldSeats = new StringBuilder();
            double totalSalePrice = 0.0;

            // First pass to check for already sold seats
            for (String seatStr : seatNumbers) {

                int seatNum = Integer.parseInt(seatStr);

                if (seatNum > bus.getRows() * bus.getSeatsPerRow()) {
                    String error = "ERROR: There is no such a seat!";
                    FileOutput.writeToFile(output_path, error, true, true);
                    return;
                }

                if (seatNum <= 0) {
                    String error = "ERROR: " + seatNum + " is not a positive integer, seat number must be a positive integer!";
                    FileOutput.writeToFile(output_path, error, true, true);
                    return;
                }
                if (bus.getSoldSeatNumbers().contains(seatNum)) {
                    alreadySoldSeats.append(seatNum).append(", ");
                    anySeatSold = true;
                }
            }

            if (anySeatSold) {
                if (alreadySoldSeats.length() > 2) {
                    alreadySoldSeats.setLength(alreadySoldSeats.length() - 2); // Remove last comma and space
                }
                String error = "ERROR: One or more seats already sold!";
                FileOutput.writeToFile(output_path, error, true, true);
                return;
            }

            // Second pass to sell the seats if none were already sold
            for (String seatStr : seatNumbers) {
                int seatNum = Integer.parseInt(seatStr);
                bus.getSoldSeatNumbers().add(seatNum);
                double seatPrice = bus.getPrice();
                if (bus instanceof PremiumBus && (seatNum % 3 == 1)) { // Assuming premium seats are every third seat
                    seatPrice += seatPrice * bus.getPremiumFee() / 100;
                }
                totalSalePrice += seatPrice;
            }
            bus.setRevenue(bus.getRevenue() + totalSalePrice);

            String seatsFormatted = String.join("-", seatNumbers);
            String output = "Seat " + seatsFormatted + " of the Voyage " + id + " from " +
                    bus.getFromCity() + " to " + bus.getToCity() + " was successfully sold for "
                    + String.format("%.2f", totalSalePrice) + " TL.";
            FileOutput.writeToFile(output_path, output, true, true);
        }


        /**
         * Processes ticket refunds based on the command details. Validates seat availability for refund,
         * calculates refund amounts according to bus type and conditions, and updates the bus object.
         *
         * @param commandType The type of command, e.g., "REFUND_TICKET".
         * @param splited     An array containing parts of the command string split by spaces.
         * @param busMap      A map storing bus objects, with voyage IDs as keys.
         * @param output_path The file path where output and error messages are written.
         */
        else if (commandType.equals("REFUND_TICKET")) {

            if (splited.length != 3) {

                String error = "ERROR: Erroneous usage of \"REFUND_TICKET\" command!";
                FileOutput.writeToFile(output_path, error, true, true);
                return;

            }


            String[] parts = line.split("\t");
            int voyageId = Integer.parseInt(parts[1]);

            if (!busMap.containsKey(voyageId)) {
                String error = "ERROR: There is no voyage with ID of " + voyageId + "!";
                FileOutput.writeToFile(output_path, error, true, true);
                return;
            }

            String[] seatsToRefund = parts[2].split("_");
            Bus bus = busMap.get(voyageId);

            if (bus.getBusType().equals("Minibus")) {
                String error = "ERROR: Minibus tickets are not refundable!";
                FileOutput.writeToFile(output_path, error, true, true);


            } else {
                double totalRefundAmount = 0.0;

                StringBuilder refundedSeats = new StringBuilder();

                boolean anySeatEmpty = false;
                StringBuilder alreadyEmptySeats = new StringBuilder();

                for (String seat : seatsToRefund) {
                    int seatNumber = Integer.parseInt(seat);

                    if (seatNumber > bus.getRows() * bus.getSeatsPerRow()) {
                        String error = "ERROR: There is no such a seat!";
                        FileOutput.writeToFile(output_path, error, true, true);
                        return;
                    }

                    if (seatNumber <= 0) {
                        String error = "ERROR: " + seatNumber + " is not a positive integer, seat number must be a positive integer!";
                        FileOutput.writeToFile(output_path, error, true, true);
                        return;
                    }

                    if (!bus.getSoldSeatNumbers().contains(seatNumber)) {
                        alreadyEmptySeats.append(seatNumber).append(", ");
                        anySeatEmpty = true;

                    }
                }
                if (anySeatEmpty) {
                    if (alreadyEmptySeats.length() > 2) {
                        alreadyEmptySeats.setLength(alreadyEmptySeats.length() - 2); // Remove last comma and space
                    }
                    String error = "ERROR: One or more seats are already empty!";
                    FileOutput.writeToFile(output_path, error, true, true);
                    return;
                }



                for (String seat : seatsToRefund) {
                    int seatNumber = Integer.parseInt(seat);
                    if (bus.getSoldSeatNumbers().contains(seatNumber)) {

                        double refundAmount = 0;

                        if (bus.getBusType().equals("Premium")) {
                            Double price;
                            if ((Integer.parseInt(seat) % 3 == 1)) {
                                price = bus.getPrice() + (bus.getPrice() * bus.getPremiumFee() / 100);
                            } else {
                                price = bus.getPrice();
                            }
                            refundAmount = price * (1 - ((PremiumBus) bus).getRefundCut() / 100.0);

                        } else if (bus.getBusType().equals("Standard")) {
                            Double price = bus.getPrice();
                            refundAmount = price * (1 - ((StandartBus) bus).getRefundCut() / 100.0);
                        } else {
                            System.out.println("MINIBUS NO REFUND");
                        }

                        totalRefundAmount += refundAmount;


                        bus.getSoldSeatNumbers().remove(Integer.valueOf(seatNumber));
                        bus.setRevenue(bus.getRevenue() - refundAmount);
                        refundedSeats.append(seatNumber).append("-");
                    }
                }


                if (refundedSeats.length() > 0) {
                    refundedSeats.setLength(refundedSeats.length() - 1); // Remove last "-"
                    String output = String.format("Seat %s of the Voyage %d from %s to %s was successfully refunded " +
                                    "for %.2f TL.\n",
                            refundedSeats, voyageId, bus.getFromCity(), bus.getToCity(), totalRefundAmount);
                    FileOutput.writeToFile(output_path, output, true, false);
                }
            }
        }

        /**
         * Generates a Z-report summarizing all voyages and their revenue details.
         * It prints the report to the specified output file path.
         *
         * @param commandType The type of command, e.g., "Z_REPORT".
         * @param splited     An array containing parts of the command string split by spaces.
         * @param busMap      A map containing all bus objects, used to generate the report.
         * @param output_path The file path where the Z-report is written.
         */
        else if (commandType.equals("Z_REPORT")) {

            if (splited.length != 1) {

                String error = "ERROR: Erroneous usage of \"Z_REPORT\" command!";
                FileOutput.writeToFile(output_path, error, true, true);
                return;

            }


            printZReport(busMap, output_path);
        }

        /**
         * Prints the detailed information of a specific voyage. This includes voyage ID, cities,
         * seating layout, and revenue, which are all written to the specified output file path.
         *
         * @param commandType The type of command, e.g., "PRINT_VOYAGE".
         * @param splited     An array containing parts of the command string split by spaces.
         * @param busMap      A map storing bus objects, with voyage IDs as keys.
         * @param output_path The file path where output messages are written.
         */
        else if (commandType.equals("PRINT_VOYAGE")) {

                if (splited.length != 2) {

                    String error = "ERROR: Erroneous usage of \"PRINT_VOYAGE\" command!";
                    FileOutput.writeToFile(output_path, error, true, true);
                    return;

                }


                int id = Integer.parseInt(splited[1]);

                if (id < 0) {
                    String error = String.format("ERROR: %d is not a positive integer, ID of a voyage must be a positive integer!", id);
                    FileOutput.writeToFile(output_path, error, true, true);
                    return;
                }

                if (!busMap.containsKey(id)) {
                    String error = "ERROR: There is no voyage with ID of " + id + "!";
                    FileOutput.writeToFile(output_path, error, true, true);
                    return;
                }

                Bus bus = busMap.get(id);

                String output = ("Voyage " + bus.getId() + "\n" + bus.getFromCity() + "-" + bus.getToCity() + "\n" +
                        bus.getSeatingLayout() + "Revenue: " + String.format("%.2f", bus.getRevenue()));

                FileOutput.writeToFile(output_path, output, true, true);

            }

        /**
         * Cancels a voyage by its ID, processes full refunds for any sold tickets, and removes the voyage
         * from the bus map. Detailed information about the cancelled voyage and adjustments in revenue are
         * written to the output file path.
         *
         * @param commandType The type of command, e.g., "CANCEL_VOYAGE".
         * @param splited     An array containing parts of the command string split by spaces.
         * @param busMap      A map storing bus objects, with voyage IDs as keys.
         * @param output_path The file path where output messages and changes are documented.
         */
        else if (commandType.equals("CANCEL_VOYAGE")) {

                if (splited.length != 2) {

                    String error = "ERROR: Erroneous usage of \"CANCEL_VOYAGE\" command!";
                    FileOutput.writeToFile(output_path, error, true, true);
                    return;
                }


                String[] parts = line.split("\t");
                int voyageId = Integer.parseInt(parts[1]);

                if (voyageId < 0) {
                    String error = String.format("ERROR: %d is not a positive integer, ID of a voyage must be a positive integer!", voyageId);
                    FileOutput.writeToFile(output_path, error, true, true);
                    return;
                }

                if (!busMap.containsKey(voyageId)) {
                    String error = "ERROR: There is no voyage with ID of " + voyageId + "!";
                    FileOutput.writeToFile(output_path, error, true, true);
                    return;
                }

                Bus bus = busMap.get(voyageId);

                if (bus != null) {
                    // Print current state before cancellation
                    String output = ("Voyage " + voyageId + " was successfully cancelled!\n" +
                            "Voyage details can be found below:\n" + "Voyage " + bus.getId() + "\n" +
                            bus.getFromCity() + "-" + bus.getToCity() + "\n" + bus.getSeatingLayout());
                    FileOutput.writeToFile(output_path, output, true, false);

                    // Refund all sold tickets without any cut, and adjust revenue
                    List<Integer> soldSeats = new ArrayList<>(bus.getSoldSeatNumbers()); // Copy to avoid concurrent modification
                    for (Integer seat : soldSeats) {
                        double price;

                        if (bus.getBusType().equals("Premium") && (seat % 3 == 1)) {
                            price = bus.getPrice() + (bus.getPrice() * bus.getPremiumFee() / 100);
                        } else {
                            price = bus.getPrice();
                        }

                        bus.setRevenue(bus.getRevenue() - price); // Subtract full ticket price from revenue
                        bus.getSoldSeatNumbers().remove(seat); // Clear the seat as refunded
                    }
                    String output2 = ("Revenue: " + String.format("%.2f", bus.getRevenue()));
                    FileOutput.writeToFile(output_path, output2, true, true);
                    // Remove voyage from the map
                    busMap.remove(voyageId);


                }

            }

        /**
         * Handles unrecognized commands by logging an error message to the specified output file path.
         * It informs the user about the incorrect or unsupported usage of a command.
         *
         * @param commandType The type of command received, used for error reporting.
         * @param output_path The file path where the error message is written.
         */
        else {
                String error = "ERROR: There is no command namely " + commandType + "!";
                FileOutput.writeToFile(output_path, error, true, true);
            }

        }

    /**
     * Generates and logs a Z report that summarizes the state of all voyages.
     * The report includes details such as voyage ID, source and destination cities,
     * seating layout, and total revenue.
     *
     * @param busMap      A map of all bus voyages currently managed by the system.
     * @param output_path The path to the output file where the report will be written.
     */
        public static void printZReport (Map < Integer, Bus > busMap, String output_path){
            List<Integer> sortedKeys = new ArrayList<>(busMap.keySet());
            Collections.sort(sortedKeys);
            StringBuilder output = new StringBuilder();
            output.append("Z Report:\n");
            if (busMap.isEmpty()) {
                output.append("----------------\n" + "No Voyages Available!\n" + "----------------");
            } else {
                for (Integer id : sortedKeys) {
                    Bus bus = busMap.get(id);
                    output.append("----------------\n" + "Voyage ").append(bus.getId()).append("\n").append(bus.getFromCity()).append("-").append(bus.getToCity()).append("\n").append(bus.getSeatingLayout()).append("Revenue: ").append(String.format("%.2f", bus.getRevenue())).append("\n");
                }
                output.append("----------------");
            }
            FileOutput.writeToFile(output_path, output.toString(), true, true);
        }

}


