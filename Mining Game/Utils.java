/**
 * Utility class containing helper methods for image processing within a JavaFX application.
 */
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Utils {
    /**
     * Creates an {@link ImageView} object from an image file located at the specified path.
     * This method assumes the image file is located within the resources directory of the application.
     *
     * @param imagePath the relative path to the image file within the resources directory.
     *                  The path should begin with a slash (e.g., "/images/myImage.png") if it is
     *                  located at the root of the compiled output directory (e.g., "bin/" or "target/classes/").
     * @return an {@link ImageView} object containing the loaded image.
     * @throws IllegalArgumentException if the specified image resource cannot be loaded, which
     *         may occur if the resource does not exist or the path is incorrect.
     */
        public static ImageView createImageView(String imagePath) {
            Image image = new Image(Utils.class.getResourceAsStream(imagePath));
            return new ImageView(image);
        }

}
