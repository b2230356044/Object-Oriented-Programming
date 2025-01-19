import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * The {@code Main} class serves as the entry point for the bus voyage management application.
 * It processes a list of commands from an input file and executes them to manage voyages,
 * including initializing voyages, selling and refunding tickets, and generating reports.
 * The results and any errors during the processing are logged to an output file.
 */
public class BookingSystem {

    /**
     * The main method reads and processes commands from a specified file.
     * It initializes the {@link Voyage} management system and executes commands
     * until the end of the file. If the last command isn't a "Z_REPORT", it triggers
     * the generation of a summary report.
     *
     * @param args Array of command-line arguments where args[0] is the path to the input file
     *             containing commands, and args[1] is the path to the output file where results
     *             and logs are written.
     */
    public static void main(String[] args) {
        try {
            String output_path = args[1]; // Path to the output file for logging

            // Reads all lines from the input file; expecting proper handling for file reading
            String[] content = FileInput.readFile(args[0], true, true);

            // Initialize voyage management system
            Voyage voyage = new Voyage();
            String lastLine = ""; // Track the last processed command

            // Process each line as a command
            for (String line : content) {
                voyage.input(line, output_path);
                lastLine = line; // Update last processed command
            }

            // If the last command is not "Z_REPORT", manually generate a report
            if (!lastLine.equals("Z_REPORT")) {
                voyage.printZReport(voyage.busMap, output_path);
            }

            removeLastNewline(output_path);

        } catch (Exception ex) {
            // Print stack trace to standard error stream in case of exceptions
            ex.printStackTrace();
        }
    }

    /**
     * Removes the last newline character from the specified file if it exists.
     *
     * @param filePath The path to the file from which the newline should be removed.
     * @throws IOException If an I/O error occurs reading or writing to the file.
     */
    public static void removeLastNewline(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        String content = new String(Files.readAllBytes(path));
        if (content.endsWith("\n")) {
            // Remove the last newline character
            content = content.substring(0, content.length() - 1);
            // Write back to the file without the newline
            Files.write(path, content.getBytes(), StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
        }
    }

}
