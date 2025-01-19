import javafx.scene.image.ImageView;
/**
 * Represents a valuable object within the game, characterized by a specific type, value, and weight.
 * This class extends {@link ImageView}, enabling it to display an image representing the valuable.
 */
public class Valueable extends ImageView {
    private int value;
    private double weight;
    private Mineral.ValuableType type;

    public Valueable(Mineral.ValuableType type, int value, double weight) {
        /**
         * Constructs a new valuable with specified type, value, and weight.
         * The image associated with the valuable type is set as the image of this {@link ImageView}.
         *
         * @param type The type of valuable, which determines the image used.
         * @param value The economic value of the valuable.
         * @param weight The physical weight of the valuable.
         */
        super(Utils.createImageView(type.getImagePath()).getImage());
        this.type = type;
        this.value = value;
        this.weight = weight;

    }
    /**
     * Returns the value of the valuable.
     *
     * @return the economic value of the valuable
     */
    public int getValue() {
        return value;
    }
    /**
     * Returns the weight of the valuable.
     *
     * @return the physical weight of the valuable
     */
    public double getWeight() {
        return weight;
    }
    /**
     * Returns the type of the valuable.
     *
     * @return the type of the valuable, which includes its categorized image path
     */
    public Mineral.ValuableType getType() {
        return type;
    }
}
