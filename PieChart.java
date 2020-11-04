package sample;

import javafx.scene.canvas.GraphicsContext;
import java.util.Map;

public class PieChart {
    MyColor[] col = {
//            MyColor.BLUE, MyColor.GREEN, MyColor.PURPLE, MyColor.ORANGE, MyColor.RED, MyColor.CYAN,
//            MyColor.MAROON, MyColor.SKY_BLUE, MyColor.LIME, MyColor.BLUE, MyColor.GREEN, MyColor.PURPLE,
//            MyColor.ORANGE, MyColor.RED, MyColor.CYAN, MyColor.MAROON, MyColor.SKY_BLUE, MyColor.LIME,
//            MyColor.BLUE, MyColor.GREEN, MyColor.PURPLE, MyColor.ORANGE, MyColor.RED, MyColor.CYAN,
//            MyColor.MAROON, MyColor.SKY_BLUE
            MyColor.BLUE, MyColor.GREEN, MyColor.PURPLE, MyColor.ORANGE, MyColor.RED, MyColor.CYAN,
            MyColor.MAROON, MyColor.SKY_BLUE, MyColor.LIME
    };

    double d, r, rOffsetLeft, rOffsetRight;
    MyPoint arcPoint;

    PieChart(MyPoint arcPoint, double d){
        this.arcPoint = arcPoint;
        this.d = d;
        r = d / 2.0;
        rOffsetLeft = r + 55;
        rOffsetRight = r + 35;
    }
    public void draw(GraphicsContext GC, int totalFrequency, int SlicesToPrint, Map<Character, Integer> sortedFrequency){
        double startAngle = 0.0, angleExtent;
        double totalSoFar = 0.0;

        for (Map.Entry<Character, Integer> mapElement : sortedFrequency.entrySet()) {
            Character key = mapElement.getKey();
            Integer value = mapElement.getValue();
            angleExtent = (360.0 * value) / totalFrequency;

            if(SlicesToPrint > 0){
                SlicesToPrint--;

                MyArc temp = new MyArc(arcPoint, d, d, startAngle, angleExtent, col[SlicesToPrint%9]);
                temp.draw(GC);

                double thisFrequency = (Math.round(1000.00 * value / (double) totalFrequency) / 1000.00);
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
                    GC.fillText("Others:\n" + remainder, (arcPoint.getX() + r) + (rOffsetLeft * Math.cos(remainingAngle)),
                            (arcPoint.getY() + r) - (rOffsetLeft * Math.sin(remainingAngle)));
                }
                else{
                    GC.fillText("Others:\n" + remainder, (arcPoint.getX() + r) + (rOffsetRight * Math.cos(remainingAngle)),
                            (arcPoint.getY() + r) - (rOffsetRight * Math.sin(remainingAngle)));
                }
                break;
            }
        }
    }
}
