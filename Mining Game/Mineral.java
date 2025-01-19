import javafx.scene.image.ImageView;
/**
 * Represents a generic mineral element in the game, characterized by its value and weight.
 * This class is extended by specific types of minerals and obstacles within the game environment.
 */


public class Mineral extends ImageView {

    private int value;
    private double weight;

    /**
     * Constructs a new mineral with specified value and weight.
     *
     * @param value the economic value of the mineral
     * @param weight the physical weight of the mineral
     */
    public Mineral(int value, double weight) {
        this.value = value;
        this.weight = weight;

    }
    /**
     * Returns the value of the mineral.
     *
     * @return the value of the mineral
     */
    public int getValue() {
        return value;
    }
    /**
     * Returns the weight of the mineral.
     *
     * @return the weight of the mineral
     */
    public double getWeight() {
        return weight;

    }
    /**
     * Represents the topmost layer in the game's environment, typically non-valuable and indestructible.
     */
    public static class Top extends ImageView{
        /**
         * Constructs the top layer with a predefined image.
         */
          public Top()  {
              super(Utils.createImageView("/assets/underground/top_concrete_01.png").getImage());

          }
    }

    /**
     * Represents soil, a common type of ground in the game that can be dug through.
     */
    public static class Soil extends ImageView {
        /**
         * Constructs a soil element with a predefined image.
         */
        public Soil() {
            super(Utils.createImageView("/assets/underground/soil_02.png").getImage());


        }
    }
    /**
     * Represents a boulder, an obstacle that blocks the player's path in the game.
     */
    public static class Boulder extends ImageView {
        /**
         * Constructs a boulder with a predefined image, representing an obstacle.
         */
        public Boulder() {
            super(Utils.createImageView("/assets/underground/obstacle_02.png").getImage());

        }
    }
    /**
     * Represents lava, a hazardous environmental element that can cause the player to lose the game.
     */
    public static class Lava extends ImageView {

        public Lava() {
             /** Constructs a lava element with a predefined image, representing a hazard.
         */
            super(Utils.createImageView("/assets/underground/lava_03.png").getImage());

        }
    }
    public enum ValuableType {
        AMAZONITE("/assets/underground/valuable_amazonite.png"),
        EMERALD("/assets/underground/valuable_emerald.png"),
        DIAMOND("/assets/underground/valuable_diamond.png");
        private String imagePath;
        /**
         * Constructs a valuable type with the specified image path.
         *
         * @param imagePath the path to the image representing the valuable
         */
        /**
         * Returns the image path associated with the valuable type.
         *
         * @return the image path
         */
        ValuableType(String imagePath) {
          this.imagePath = imagePath;
        }
        public String getImagePath() {
            return imagePath;
        }
    }

    }




