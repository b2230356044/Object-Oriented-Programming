import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
/**
 * This class serves as the entry point for the JavaFX application, setting up the primary stage and initial scene.
 *
     */
public class Main extends Application {
    /** @param args the command line arguments passed to the application.
     *
     */
    public static void main(String[] args) {
        launch(args);
    }
    /**
     * Starts and sets up the initial scene and stage of the JavaFX application.
     * This method initializes the primary GUI components and configures the game environment.
     * @param primaryStage the primary stage for this application, onto which the application scene is set.
     */
    @Override
    public void start(Stage primaryStage) {
        Pane layout = new Pane();
        // Create a scene with the layout pane as the root, with specified width and height
        Scene scene = new Scene(layout, 800, 600);
        // Set the background color of the layout pane
        layout.setStyle("-fx-background-color: #4999b1;");
        // Set the title of the window (stage)
        primaryStage.setTitle("HU-Load");
        // Set the scene on the primary stage and display it
        primaryStage.setScene(scene);
        primaryStage.show();
        // Create and start the game
        Game game = new Game(layout);
        game.start(scene);
    }
}
