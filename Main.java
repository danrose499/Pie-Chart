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

public class Main extends Application  {
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
        }
        catch(Exception e){ System.out.println(e.getMessage()); }
    }
    private Canvas addCanvas(int cWidth, int cHeight) throws IOException {
        Canvas CV = new Canvas(cWidth, cHeight);
        GraphicsContext GC = CV.getGraphicsContext2D();

        //MyOval BackgroundChart = new MyOval(new MyPoint(2*cWidth/3,cHeight/2), 4*(Math.min(cWidth,cHeight)/2)/3,4*(Math.min(cWidth,cHeight)/2)/3, MyColor.CLEAR_GREY);
        //BackgroundChart.setCenter(new MyPoint(2*cWidth/3,cHeight/2));
        //BackgroundChart.draw(GC);

        MyCircle emptyChart = new MyCircle(new MyPoint(2*cWidth/3,cHeight/2), 2.0*(Math.min(cWidth,cHeight)/2.0)/3.0, MyColor.CLEAR_BLACK);
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

        MyColor[] col = {
                MyColor.BLUE, MyColor.GREEN, MyColor.PURPLE, MyColor.ORANGE, MyColor.RED, MyColor.CYAN,
                MyColor.MAROON, MyColor.SKY_BLUE, MyColor.LIME, MyColor.BLUE, MyColor.GREEN, MyColor.PURPLE,
                MyColor.ORANGE, MyColor.RED, MyColor.CYAN, MyColor.MAROON, MyColor.SKY_BLUE, MyColor.LIME,
                MyColor.BLUE, MyColor.GREEN, MyColor.PURPLE, MyColor.ORANGE, MyColor.RED, MyColor.CYAN,
                MyColor.MAROON, MyColor.SKY_BLUE
        };

        /////
        int CharsToPrint = 10;
        /////

        int totalChars = s.length();
        double startAngle = 0.0, angleExtent;
        double totalSoFar = 0.0;

        double d = 4.0*(Math.min(cWidth,cHeight)/2.0)/3.0;
        double r = d/2.0;
        double rOffset = r+40;

        MyPoint arcPoint = new MyPoint(2*cWidth/3 - (int)d/2,cHeight/2 - (int)d/2);

        for (Map.Entry<Character, Integer> mapElement : sortedFrequency.entrySet()) {
            if(CharsToPrint==0){ break; }
            CharsToPrint--;
            Character key = mapElement.getKey();
            Integer value = mapElement.getValue();

            angleExtent = (360.0 * value)/totalChars;
            double textAngle = Math.toRadians(startAngle + (angleExtent/2));

            MyArc temp = new MyArc(arcPoint, d, d, startAngle, angleExtent, col[CharsToPrint]);
            temp.draw(GC);

            //GC.setFill(MyColor.BLACK.getColor());
            double thisFrequency = (Math.round(1000.00*value/(double)totalChars)/1000.00);
            GC.fillText(key + ":\n" + thisFrequency,
                    (arcPoint.getX()+r) + rOffset*Math.cos(textAngle), (arcPoint.getY()+r) - rOffset*Math.sin(textAngle));
            totalSoFar+=thisFrequency;

            startAngle+=angleExtent;
        }
        GC.setFill(MyColor.BLACK.getColor());
        double remainder = Math.round(1000.00*(1.00-totalSoFar)/1000.00);
        GC.fillText("All other letters:\n" + remainder, (arcPoint.getX()+r) + rOffset*Math.cos(startAngle/2.0 + 180),
                (arcPoint.getY()+r) - rOffset*Math.sin(startAngle/2.0 + 180));

        return CV;
    }
    public static void main(String[] args) {
        launch(args);
    }
    public static<K> void incrementFrequency(Map<K, Integer> m, K Key){
        m.putIfAbsent(Key, 0);
        m.put(Key, m.get(Key) + 1);
    }


}