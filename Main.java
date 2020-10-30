package sample;

import java.io.IOException;
import java.lang.Math;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;

import static java.util.stream.Collectors.toMap;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) /*throws Exception*/ {
        try {
            primaryStage.setTitle("My PieChart");
            Pane P = new Pane();
            Canvas CV = addCanvas(1000, 500);
            P.getChildren().add(CV);
            Scene SC = new Scene(P, 1000, 500);
            primaryStage.setScene(SC);
            primaryStage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private Canvas addCanvas(int cWidth, int cHeight) throws IOException {
        Canvas CV = new Canvas(cWidth, cHeight);
        GraphicsContext GC = CV.getGraphicsContext2D();

        //MyOval BackgroundChart = new MyOval(new MyPoint(2*cWidth/3,cHeight/2), 4*(Math.min(cWidth,cHeight)/2)/3,4*(Math.min(cWidth,cHeight)/2)/3, MyColor.CLEAR_GREY);
        //BackgroundChart.setCenter(new MyPoint(2*cWidth/3,cHeight/2));
        //BackgroundChart.draw(GC);

        double d = 4.0 * (Math.min(cWidth, cHeight) / 2.0) / 3.0;
        MyPoint arcPoint = new MyPoint(2 * cWidth / 3 - (int) d / 2, cHeight / 2 - (int) d / 2);

        MyArc emptyChart = new MyArc(arcPoint, d, d, 0, 360, MyColor.CLEAR_GREY);
        emptyChart.draw(GC);

        //////FILE

        Path fileName = Path.of("alice.txt");
        String actual = Files.readString(fileName);
        String s = actual.replaceAll("[^a-zA-Z]", "").toLowerCase();

        Map<Character, Integer> frequency = new HashMap<>();

        for(int i = 0; i <s.length(); i++) {
            Character Ch = s.charAt(i);
            incrementFrequency(frequency, Ch);
        }

        Map<Character, Integer> sortedFrequency = frequency
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));

//        Map<Character, Integer> sortedFrequency = fileToMap("Alice.txt");

        MyColor[] col = {
                MyColor.BLUE, MyColor.GREEN, MyColor.PURPLE, MyColor.ORANGE, MyColor.RED, MyColor.CYAN,
                MyColor.MAROON, MyColor.SKY_BLUE, MyColor.LIME, MyColor.BLUE, MyColor.GREEN, MyColor.PURPLE,
                MyColor.ORANGE, MyColor.RED, MyColor.CYAN, MyColor.MAROON, MyColor.SKY_BLUE, MyColor.LIME,
                MyColor.BLUE, MyColor.GREEN, MyColor.PURPLE, MyColor.ORANGE, MyColor.RED, MyColor.CYAN,
                MyColor.MAROON, MyColor.SKY_BLUE
        };

        /////
        int CharsToPrint = 25;
        /////

        int totalChars = s.length();
        double startAngle = 0.0, angleExtent;
        double totalSoFar = 0.0;

        double r = d / 2.0;
        double rOffsetLeft = r + 55;
        double rOffsetRight = r + 35;

        for (Map.Entry<Character, Integer> mapElement : sortedFrequency.entrySet()) {
            Character key = mapElement.getKey();
            Integer value = mapElement.getValue();
            angleExtent = (360.0 * value) / totalChars;

            if(CharsToPrint > 0){
                CharsToPrint--;

                MyArc temp = new MyArc(arcPoint, d, d, startAngle, angleExtent, col[CharsToPrint]);
                temp.draw(GC);

                double thisFrequency = (Math.round(1000.00 * value / (double) totalChars) / 1000.00);
                double textAngle = Math.toRadians(startAngle + (angleExtent / 2));
                if(textAngle > (1.5708) && textAngle < (4.71239)) {
                    GC.fillText(key + ": " + thisFrequency,
                            (arcPoint.getX() + r) + (rOffsetLeft * Math.cos(textAngle)),
                            (arcPoint.getY() + r) - (rOffsetLeft * Math.sin(textAngle)));
                }
                else{
                    GC.fillText(key + ": " + thisFrequency,
                            (arcPoint.getX() + r) + (rOffsetRight * Math.cos(textAngle)),
                            (arcPoint.getY() + r) - (rOffsetRight * Math.sin(textAngle)));
                }
                totalSoFar += thisFrequency;
                startAngle += angleExtent;
            }
            else {

                MyArc remainingArc = new MyArc(arcPoint, d, d, startAngle, 360-startAngle, MyColor.LIGHT_GREY);
                remainingArc.draw(GC);

                double remainder = Math.round(1000.00 * (1.00 - totalSoFar)) / 1000.00;
                double remainingAngle = Math.toRadians(startAngle/2.0 + 180);
                GC.setFill(MyColor.GREY.getColor());
                if(remainingAngle > (1.5708) && remainingAngle < (4.71239)){
                    GC.fillText("All other letters:\n" + remainder, (arcPoint.getX() + r) + (rOffsetLeft * Math.cos(remainingAngle)),
                            (arcPoint.getY() + r) - (rOffsetLeft * Math.sin(remainingAngle)));
                }
                else{
                    GC.fillText("All other letters:\n" + remainder, (arcPoint.getX() + r) + (rOffsetRight * Math.cos(remainingAngle)),
                            (arcPoint.getY() + r) - (rOffsetRight * Math.sin(remainingAngle)));
                }
                break;
            }
        }

        return CV;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static <K> void incrementFrequency(Map<K, Integer> m, K Key) {
        m.putIfAbsent(Key, 0);
        m.put(Key, m.get(Key) + 1);
    }

//    public Map<Character, Integer> fileToMap(String textFile){
//        Path fileName = Path.of(textFile);
//        String actual = null;
//        try {
//            actual = Files.readString(fileName);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        String s = actual.replaceAll("[^a-zA-Z]", "").toLowerCase();
//
//        Map<Character, Integer> frequency = new HashMap<>();
//
//        for(int i = 0; i<s.length();i++) {
//            Character Ch = s.charAt(i);
//            incrementFrequency(frequency, Ch);
//        }
//
//        Map<Character, Integer> sortedFrequency = frequency
//                .entrySet()
//                .stream()
//                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
//                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
//        return frequency;
//    }

}
