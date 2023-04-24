// from https://bugs.openjdk.org/browse/JDK-8185569

import java.util.Arrays;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
 
public class HelloWorldJavaFX extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        
        for (String fontName : Font.getFontNames())
        {
            System.out.println(fontName);
        }

        VBox vBox = new VBox();
        
        List<String> fonts = Arrays.asList(
                "System",
                "System Bold",
                "System Italic",
                "System Regular",
                "Serif Bold",
                "Serif Bold Italic",
                "Serif Italic",
                "Serif Regular",
                "SansSerif Bold",
                "SansSerif Bold Italic",
                "SansSerif Italic",
                "SansSerif Regular",
                "Monospaced Bold",
                "Monospaced Bold Italic",
                "Monospaced Italic",
                "Monospaced Regular"
                );
        
        for (String fontName : fonts)
        {
            final Label label = new Label(fontName);
            label.setFont(new Font(fontName, 16));
            vBox.getChildren().add(label);
        }
        primaryStage.setTitle("JavaFX");
        StackPane root = new StackPane();
        root.getChildren().add(vBox);
        primaryStage.setScene(new Scene(root, 300, 500));
        primaryStage.show();
    }
}
