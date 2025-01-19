import java.io.File;
import java.util.Locale;
import java.util.Scanner;
/**
 * Main class that initializes the process of analyzing a map through the provided data.
 * It uses the MapAnalyzer class to compute outputs based on the road network data.
 */
public class MapAnalyzer {
    /**
     * Entry point of the program. Reads the input file specified as the first command line argument
     * to get start and end points and road data, then uses MapAnalyzer to calculate and output the result
     * to the file specified as the second command line argument.
     *
     * @param args Command line arguments where args[0] is the path to the input file and
     *             args[1] is the path to the output file.
     */
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        try {
            MapAnalyzerAlgorithm mapAnalyzer = new MapAnalyzerAlgorithm();
            Scanner scanner = new Scanner(new File(args[0]));
            String[] points = scanner.nextLine().split("\t");
            String start = points[0];
            String end = points[1];
            while (scanner.hasNext()) {
                String[] parts=(scanner.nextLine().split("\t"));

                mapAnalyzer.addRoad(parts[0], parts[1], Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
            }

            scanner.close();
            String output = String.valueOf(mapAnalyzer.printOutput(start, end));
            FileOutput.writeToFile(args[1], output,false,false);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}