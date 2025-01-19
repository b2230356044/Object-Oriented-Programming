import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Represents a controllable machine character in a JavaFX game application.
 * This class manages the machine's properties such as its image, position, fuel, and monetary resources.
 * It includes methods for moving in different directions and interacting with game elements like valuables and obstacles.
 */

public class Machine extends ImageView{
    private double fuel;
    private int money;
    private double haul;  // Represents the total weight of collected valuables

    private Game game;
    private Pane gamePane;
    private AnimationTimer fuelTimer;
    private long lastGravityUpdate = 0;  // To track when to apply gravity
    private Image Image;

    /**
     * Constructs a Machine with specified image and initializes its game environment.
     *
     * @param imagePath The path to the image resource that visually represents the machine.
     * @param game      The game controller this machine is part of, used for interaction.
     * @param gamePane  The pane in which the machine is displayed and moves around.
     */
    public Machine(String imagePath, Game game, Pane gamePane) {
        super(Utils.createImageView(imagePath).getImage());
        this.game = game;
        this.fuel = 300 ;
        this.money = 0;
        this.haul = 0;  // Initialize haul
        this.gamePane = gamePane;


        setPreserveRatio(true);
        setupGameTimer();

    }
    /**
     * Updates the machine's image based on the direction of movement.
     *
     * @param direction The direction the machine is moving, e.g., "up", "down", "left", "right".
     * @return The new image associated with the specified direction.
     */
    private Image updateImage(String direction) {
        String basePath = "assets/drill/drill_%s.png";  // Modify the base path according to your file structure

        String imagePath;
        switch (direction) {
            case "up":
                imagePath = String.format(basePath,"23");
                break;
            case "down":
                imagePath = String.format(basePath, "40");
                break;
            case "left":
                imagePath = String.format(basePath, "53");
                break;
            case "right":
                imagePath = String.format(basePath, "60");
                break;
            default:
                imagePath = String.format(basePath, "01");
        }

            Image image = new Image(getClass().getResourceAsStream(imagePath));  // Ensure this correctly loads the resource
            this.setImage(image);  // Set the new image for this ImageView


        return image;
    }
    /**
     * Collects a valuable and updates the machine's total haul and money.
     *
     * @param valuable The valuable object that the machine collects.
     */
    public void collectValuable(Valueable valuable) {
        this.haul += valuable.getWeight();
        this.money += valuable.getValue();
        game.updateHaulDisplay(haul);
        game.updateMoneyDisplay(money);
    }
    /**
     * Sets up and starts the fuel consumption and gravity application timer.
     */
    private void setupGameTimer() {
        fuelTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {

                consumeFuel(0.1); // Decrease fuel per second

                // Apply gravity every second
                if ((now - lastGravityUpdate) >= 1_000_000_000L) { // 1 second

                    applyGravity();
                    lastGravityUpdate = now; // Reset the gravity application timer
                }
            }
        };
        fuelTimer.start();
    }
    /**
     * Applies gravity to the machine, causing it to move downwards if possible.
     */
    private void applyGravity() {
        // Always moves down by 50 pixels
        if (canMoveDown()) {
            setTranslateY(getTranslateY() + 50); // Move down by 50 pixels
        }
    }
    /**
     * Determines if the machine can move downward without exiting the game grid or colliding with non-passable objects.
     *
     * @return true if the machine can move downward, false otherwise.
     */
    private boolean canMoveDown() {
        int nextY = (int) ((getTranslateY() + 50) / 50);
        int currentX = (int) (getTranslateX() / 50);
        if (nextY >= 12) return false; // Check if the next position is out of the grid bounds
        String cellBelow = game.getGrid(nextY, currentX);
        return cellBelow.equals("SKY"); // Check that the cell below is empty
    }
    /**
     * Consumes a specified amount of fuel and checks if the game should end due to running out of fuel.
     *
     * @param amount The amount of fuel to consume.
     */
    private void consumeFuel(double amount) {
        if (fuel > 0) {
            fuel -= amount;
        }
        if (fuel <= 0) {
            fuel = 0;
            fuelTimer.stop();
            game.handleGameOverWin((Stage) gamePane.getScene().getWindow());
        }
        game.updateFuelDisplay(fuel);  // Update the fuel display

    }
// Additional methods for moving left, right, up, down, and clearing cells omitted for brevity.
    /**
     * Moves the machine left on the game grid if not at the leftmost edge.
     * It updates the machine's image to reflect the leftward movement, checks for collisions with obstacles,
     * collects valuables if present, and consumes fuel.
     */
    public void moveLeft() {

        int currentY = (int)(getTranslateY()/50);
        int currentX = (int)(getTranslateX()/50);
        if (getTranslateX() > 0) {  // Ensure we're not on the leftmost column
            updateImage("left");
            String gridContent = game.getGrid((int)(getTranslateY()/50), (int)((getTranslateX()-50)/50));
            if ("Lava".equals(gridContent)) {
                game.handleGameOverLose((Stage) gamePane.getScene().getWindow());
            }
            if (gridContent.equals("Valuable")) {
                Valueable element = (Valueable) game.getGridElements(currentY,currentX-1);
                collectValuable(element);

            }
            if (!"Boulder".equals(gridContent)) {
                clearCell(currentY, currentX-1);
                setTranslateX(getTranslateX() - 50);
                consumeFuel(10); // Consume fuel per movement

            }
        }
    }
    /**
     * Moves the machine right on the game grid if not at the rightmost edge.
     * It updates the machine's image to reflect the rightward movement, checks for collisions with obstacles,
     * collects valuables if present, and consumes fuel.
     */
    public void moveRight() {

        int currentY = (int)(getTranslateY()/50);
        int currentX = (int)(getTranslateX()/50);
        if (getTranslateX() < 50 * 15) {  // Assuming the grid has 16 columns (indexed 0 to 15)
            updateImage("right");
            String gridContent = game.getGrid((int)(getTranslateY()/50), (int)((getTranslateX()+50)/50));
            if ("Lava".equals(gridContent)) {
                game.handleGameOverLose((Stage) gamePane.getScene().getWindow());
            }

            if (gridContent.equals("Valuable")) {
                Valueable element = (Valueable) game.getGridElements(currentY,currentX+1);
                collectValuable(element);

            }

            if (!"Boulder".equals(gridContent)) {
                clearCell(currentY, currentX+1);
                setTranslateX(getTranslateX() + 50);
                consumeFuel(10); // Consume fuel per movement

            }
        }
    }
    /**
     * Moves the machine up on the game grid if not at the topmost row.
     * It updates the machine's image to reflect the upward movement, checks for collisions with obstacles,
     * and consumes fuel.
     */
    public void moveUp() {
        int currentY = (int)(getTranslateY()/50);
        int currentX = (int)(getTranslateX()/50);
        if (getTranslateY() > 0) {// Ensure we're not on the topmost row

            String gridContent = game.getGrid((int)((getTranslateY()-50)/50), (int)(getTranslateX()/50));
            if ("Lava".equals(gridContent)) {
                game.handleGameOverLose((Stage) gamePane.getScene().getWindow());
            }

            if ("SKY".equals(gridContent)) {
                updateImage("up");
                clearCell(currentY-1, currentX);
                setTranslateY(getTranslateY() - 50);
                consumeFuel(10); // Consume fuel per movement

            }
        }
    }
    /**
     * Moves the machine down on the game grid if not at the bottom row.
     * It updates the machine's image to reflect the downward movement, checks for collisions with obstacles,
     * collects valuables if present, and consumes fuel.
     */
    public void moveDown() {
        int currentY = (int)(getTranslateY()/50);
        int currentX = (int)(getTranslateX()/50);
        if (getTranslateY() < 50 * 10) {// Assuming the grid has 10 rows (indexed 0 to 9)
            updateImage("down");
            String gridContent = game.getGrid((int)((getTranslateY()+50)/50), (int)(getTranslateX()/50));
            if ("Lava".equals(gridContent)) {
                game.handleGameOverLose((Stage) gamePane.getScene().getWindow());
            }

            if (gridContent.equals("Valuable")) {
                Valueable element = (Valueable) game.getGridElements(currentY+1,currentX);
                collectValuable(element);

            }
            if (!"Boulder".equals(gridContent)) {
                clearCell(currentY+1, currentX);

                setTranslateY(getTranslateY() + 50);
                consumeFuel(10); // Consume fuel per movement

            }
        }
    }

    private void clearCell(int row, int col) {
        ImageView element = game.clearGridElement(row, col);
        if (element != null) {
            element.setVisible(false);  // Or element.setOpacity(0) to make it transparent
        }
    }
    /**
     * Retrieves the current fuel level of the machine.
     *
     * @return The current fuel level.
     */
    public double getFuel() {
        return fuel;
    }
    /**
     * Retrieves the current money quantity that machine collected.
     *
     * @return The current money quantity.
     */
    public int getMoney() {
        return money;
    }


}

