/**
 * Represents the game logic and interface for a JavaFX application. This class manages the game's state,
 * including the graphical representation of game elements, player interactions, and game mechanics.
 */
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.util.Random;

public class Game {
    /**
     * Initializes the game with the specified pane which will contain all game elements.
     *
     * @param pane The pane where game elements are displayed.
     */
    private Pane gamePane;
    private Machine machine;
    private String[][] grid;

    private ImageView[][] gridElements;
    private Label fuelLabel;
    private Label haulLabel;
    private Label moneyLabel;

    public Game(Pane pane) {
        this.gamePane = pane;
        this.grid = new String[12][16];
        this.gridElements = new ImageView[12][16];  // Initialize based on rows and cols
        initializeUI();
    }
    /**
     * Sets up the user interface for the game, including labels for fuel, haul, and money.
     */
    private void initializeUI() {
        fuelLabel = new Label();
        fuelLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");
        fuelLabel.setLayoutX(0);  // Adjust as necessary
        fuelLabel.setLayoutY(0);  // Adjust as necessary
        gamePane.getChildren().add(fuelLabel);

        haulLabel = new Label("Haul: 0");
        haulLabel.setLayoutX(0);
        haulLabel.setLayoutY(20);
        haulLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");
        gamePane.getChildren().add(haulLabel);

        moneyLabel = new Label("Money: 0");
        moneyLabel.setLayoutX(0);
        moneyLabel.setLayoutY(40);
        moneyLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");
        gamePane.getChildren().add(moneyLabel);

    }
    /**
     * Updates the fuel display with the current fuel amount.
     *
     * @param fuel The current fuel level to display.
     */

    public void updateFuelDisplay(double fuel) {
        fuelLabel.setText("Fuel: " + String.format("%.2f", fuel));
    }
    /**
     * Updates the haul display with the current haul amount.
     *
     * @param haul The current haul amount to display.
     */
    public void updateHaulDisplay(double haul) {
        haulLabel.setText("Haul: " + String.format("%.2f", haul));
    }
    /**
     * Updates the money display with the current money amount.
     *
     * @param money The current amount of money to display.
     */
    public void updateMoneyDisplay(double money) {
        moneyLabel.setText("Money: " + String.format("%.2f", money));
    }
    /**
     * Starts the game by setting up key event handlers and initializing game elements.
     *
     * @param scene The scene to which key event listeners are added.
     */
    public void start(Scene scene) {
        handleKeyEvents(scene);
        setupGameElements();

    }

    /**
     * Initializes game elements, setting up the grid and placing all necessary components.
     */
    private void setupGameElements() {
        // Setup logic for initializing game elements, including grid setup and machine initialization.
        int rows = 12;
        int cols = 16;
        double elementSize = 50;

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = "SKY";
            }
        }

        for (int i = 2; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                double x = j * elementSize;
                double y = (i-2) * elementSize + 100;
                ImageView element = createElement(i, j);
                element.setLayoutX(x);
                element.setLayoutY(y);
                gamePane.getChildren().add(element);
                gridElements[i][j] = element;  // Store the reference

            }
        }

        machine = new Machine("/assets/drill/drill_02.png",this,gamePane);
        machine.setTranslateX(0);  // Initial X position
        machine.setTranslateY(0);  // Initial Y position // Assuming Machine is also an ImageView or similar
        gamePane.getChildren().add(machine);

    }




    private ImageView createElement( int row, int col) {
        Random rand = new Random();
        double chance=rand.nextDouble();

        if (row == 2) {
            grid[row][col] = "Top";
            return new Mineral.Top();
        }
        if (col == 0 || col == 15 || row == 11) {
            grid[row][col] = "Boulder";
            return new Mineral.Boulder();
        } else if (chance <0.01) {
            Valueable valueable1 = new Valueable(Mineral.ValuableType.AMAZONITE, 500000, 120);
            grid[row][col] = "Valuable";
            return valueable1;
        } else if (chance < 0.02) {
            Valueable valueable2 = new Valueable(Mineral.ValuableType.EMERALD, 5000, 60);
            grid[row][col] = "Valuable";
            return valueable2;
        } else if (chance <= 0.03) {
            Valueable valueable3 = new Valueable(Mineral.ValuableType.DIAMOND, 100000, 100);
            grid[row][col] = "Valuable";
            return valueable3;
        } else if (chance <= 0.08) {
            Mineral.ValuableType type = Mineral.ValuableType.values()[rand.nextInt(Mineral.ValuableType.values().length)];
            int value = rand.nextInt(100) + 100;  // Assign value
            double weight = 5 + (rand.nextDouble() * 15);  // Assign weight
            grid[row][col] = "Valuable";
            return new Valueable(type, value, weight);  // Note the corrected class name here if it should be `Valuable
        } else if (chance<=0.20) {
            grid[row][col] = "Lava";
            return new Mineral.Lava();
        } else {
            grid[row][col] = "Soil";
            return new Mineral.Soil();
        }

    }
    /**
     * Clears a grid element at the specified row and column, effectively resetting it to a default state.
     *
     * @param row The row index of the element to clear.
     * @param col The column index of the element to clear.
     * @return The ImageView of the cleared element, or null if there was no element to clear.
     */
    public ImageView clearGridElement(int row, int col) {

        if(row<0 || row>=12 || col<0 || col>=16) {
            return null;
        }
        if (grid[row][col] != null && !grid[row][col].equals("SKY")) {
            grid[row][col] = "SKY";  // Clear the logical representation
            ImageView element = gridElements[row][col];
            return element;
        }
        return null;
    }


    private void handleKeyEvents(Scene scene) {
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case LEFT: machine.moveLeft(); break;
                case RIGHT: machine.moveRight(); break;
                case UP: machine.moveUp(); break;
                case DOWN: machine.moveDown(); break;
            }
        });

    }
    /**
     * Handles game over by displaying a losing screen on the primary stage.
     *
     * @param primaryStage The primary stage where the game over screen is displayed.
     */
    public void handleGameOverLose(Stage primaryStage) {
        // Create a new pane for the game over screen
        VBox gameOverPane = new VBox(10); // Using VBox for vertical alignment
        gameOverPane.setStyle("-fx-background-color: #ff0000; -fx-alignment: center;");
        gameOverPane.setPrefSize(gamePane.getWidth(), gamePane.getHeight());

        // Create a label to show game over message
        Label gameOverLabel = new Label("Game Over!");
        gameOverLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: white;");

        // Add components to the pane
        gameOverPane.getChildren().addAll(gameOverLabel);

        // Create a scene with the game over pane
        Scene gameOverScene = new Scene(gameOverPane);

        // Set the scene to the primary stage
        primaryStage.setScene(gameOverScene);
    }
    /**
     * Handles game over by displaying a winning screen on the primary stage, including the total money earned.
     *
     * @param primaryStage The primary stage where the game over screen is displayed.
     */
    public void handleGameOverWin(Stage primaryStage) {
        // Create a new pane for the game over screen
        VBox gameOverPane = new VBox(10); // Using VBox for vertical alignment
        gameOverPane.setStyle("-fx-background-color: #00ff00; -fx-alignment: center;");
        gameOverPane.setPrefSize(gamePane.getWidth(), gamePane.getHeight());

        // Create a label to show game over message
        Label gameOverLabel = new Label("Game Over!");
        gameOverLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: white;");

        Label moneyLabel = new Label("Money: $");
        moneyLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: white;");
        moneyLabel.setText("Money: $"+machine.getMoney());
        // Add components to the pane
        gameOverPane.getChildren().addAll(gameOverLabel, moneyLabel);

        // Create a scene with the game over pane
        Scene gameOverScene = new Scene(gameOverPane);

        // Set the scene to the primary stage
        primaryStage.setScene(gameOverScene);
    }

    /**
     * Retrieves the value from the game grid at the specified row and column.
     *
     * @param row The row index of the element to retrieve.
     * @param col The column index of the element to retrieve.
     * @return The string value representing the grid element at the specified position.
     */
    public String getGrid(int row, int col) {
        return grid[row][col];
    }
    public ImageView getGridElements(int row, int col) {
        return gridElements[row][col];
    }
}





