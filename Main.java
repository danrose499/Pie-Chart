package sample;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;

public class Main extends Application {
    TextField pieInput;
    String filename = "alice.txt";
    Canvas CV;
    HistogramAlphaBet alice;

    @Override
    public void start(Stage primaryStage) /*throws Exception*/ {
        try {
            primaryStage.setTitle("My PieChart");

            BorderPane BP = new BorderPane();

            CV = addCanvas(500, 500, filename);
            BP.setCenter(CV);

            Text topText = new Text("Displaying Letter Frequencies of: " + filename);
            BorderPane.setAlignment(topText, Pos.TOP_CENTER);

            Text inputText = new Text("Enter # of Letter Frequencies to Display: ");
            pieInput = new TextField();
            pieInput.setPrefWidth(50);
            pieInput.setAlignment(Pos.CENTER);
            pieInput.setOnAction(event -> {
                try {
                    processReturn(event);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            HBox hbox = new HBox(inputText, pieInput);
            hbox.setAlignment(Pos.BASELINE_CENTER);
            
            BP.setStyle("-fx-padding: 10;");
            BP.setTop(topText);
            BP.setBottom(hbox);

            Scene SC = new Scene(BP, 700, 700);
            primaryStage.setScene(SC);
            primaryStage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    private Canvas addCanvas(int cWidth, int cHeight, String filename) throws IOException {
        Canvas CV = new Canvas(cWidth, cHeight);
        alice = new HistogramAlphaBet(filename);
        alice.drawPieChart(CV.getGraphicsContext2D(), 0);
        return CV;
    }
    private void processReturn(ActionEvent event) throws IOException {
        int charsToDisplay = Integer.parseInt(pieInput.getText());
        CV.getGraphicsContext2D().clearRect(0, 0, CV.getWidth(), CV.getHeight());
        if (charsToDisplay > 26) { charsToDisplay = 26; }
        alice.drawPieChart(CV.getGraphicsContext2D(), charsToDisplay);
    }
    public static void main(String[] args) {
        launch(args);
    }
}
