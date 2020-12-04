import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = new MainWorkspace();
        root.getStylesheets().add(DrawingWorkspace.class.getResource("style.css").toExternalForm());

        Scene scene = new Scene(root, 800, 600);

        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.requestFocus();
        primaryStage.toFront();

    }

    public static void main(String[] args) {
	// write your code here
    }
}
