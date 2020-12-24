# Pie Chart GUI

## Overview
Pie-Chart uses a custom Java shape hierarchy and color enum (the same one from my MyShape repository, which can be found at https://github.com/danrose499/MyShape). The Main file of the Pie-Chart prorgram creates an object of the ```HistogramAlphaBet``` class which reads a text file (```"Alice.txt"``` in this example, containing the story to "Alice in Wonderland") and creates a map with of all the letters (as the values) and their respective frequencies (as the keys). The map is then sorted by keys to print a Pie Chart using the ```MyArc``` class to print the slices of the graph. Using the created JavaFX GUI, the user can select how many of the most common letters to print on the pie chart, and the chart is updated accordingly, as seen below:
![Output](https://i.imgur.com/lGzdwmw.png?1)

## Walkthrough
### Main
The ```Main``` class begins with the following declarations which are explained below:

```Java    
    TextField pieInput;
    String filename = "alice.txt";
    Canvas CV;
    HistogramAlphaBet alice;
```
```pieInput``` is where the user will input the number of slices they want to print; ```filename``` is a String with the name of the file whose characters will be counted (in this example, filename is set to ```"alice.txt"``` which is the name of a file containing the story to "Alice In Wonderland"); ```CV``` is the Canvas which will display the Pie Chart, along with the rest of the GUI; and ```alice``` is an object of the ```HistogramAlphaBet``` class which reads a text file and has a method to return a sorted LinkedHashmap that has the occrunces of each letter in the text file in order of most-common frequency.  

```Main``` continues by overriding ```start```. In the overriden version, a title is given to the ```primaryStage```, a BoderPane is declared (BP), and the ```addCanvas``` method is called, setting ```CV``` to 600x600, setting ```alice = new HistogramAlphaBet(filename)```, and calling ```alice```'s ```drawPieChart``` method. As this is still before any user input has been receieved, ```darPieChart``` is called with parameters such that an empty pie chart is drawn, as seen below:
![Output](https://i.imgur.com/c6XKphh.png)

Next in ```Main```, ```CV``` is set as the center node of ```BP```. A Text displaying ```"Displaying Letter Frequencies of: " + filename``` is set as the top node of ```BP```. For the bottom node of ```BP```, an HBox is created with a Text displaing ```"Enter # of Letter Frequencies to Display: "``` and a TextField to its right (which is where the user will input the number of slices to display). The left and right nodes of ```BP``` are left null. 

### HistogramAlphaBet
The ```getMap``` method included below defines a string ```s``` which is a string of the lowercase of each letter in the file. A HashMap ```sortedFrequency``` is then defined such that the keys of the map are the letters, a through z, and the values are the frequency that the respective letter appears in the file. ```sortedFrequency``` is ordered such in decreased frequency of values, ie, the letter that appears most frequently in the file will be the first key in the map. The method is included below:
```Java
    public void getMap() throws IOException {
        Path fileName = Path.of(filename);
        String actual = Files.readString(fileName);
        String s = actual.replaceAll("[^a-zA-Z]", "").toLowerCase();
        totalChars = s.length();

        Map<Character, Integer> frequency = new HashMap<>();

        for(int i = 0; i < totalChars; i++) {
            Character Ch = s.charAt(i);
            incrementFrequency(frequency, Ch);
        }

        this.sortedFrequency = frequency
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
    }

    public static <K> void incrementFrequency(Map<K, Integer> m, K Key) {
        m.putIfAbsent(Key, 0);
        m.put(Key, m.get(Key) + 1);
    }
```  
The ```drawPieChart``` method included below uses the custom made ```MyPoint``` and ```PieChart``` classes. More information on these classes can be found in the MyShape repository (https://github.com/danrose499/MyShape). The method creates a ```PieChart``` object with a diameter that is 50 pixels less than 75% of the smallest axes of the canvas. This is to allow room for lables for the ```PieChart``` object to be printed. The method ends by calling the ```draw``` method of the ```PieChart``` object which prints the chart on the canvas with the given number of slices.  
```Java
    public void drawPieChart(GraphicsContext GC, int SlicesToPrint) {
        int cWidth = (int) GC.getCanvas().getWidth();
        int cHeight = (int) GC.getCanvas().getHeight();
        double d = (3.0 * (Math.min(cWidth, cHeight) / 4.0))-50;
        MyPoint arcPoint = new MyPoint(cWidth / 2 - (int) d / 2, cHeight / 2 - (int) d / 2);
        PieChart charChart = new PieChart(arcPoint, d);
        charChart.draw(GC, totalChars, SlicesToPrint, sortedFrequency);
    }
```
### processReturn
Although this method is part of ```Main```, it is included as its own section as it can be better understood with the context gained from the other sections.
The processReturn is called whenever ```Return``` is entered in the TextField in ```BP```. The method is included below:
```Java
    private void processReturn(ActionEvent event) {
        int charsToDisplay = Integer.parseInt(pieInput.getText());
        CV.getGraphicsContext2D().clearRect(0, 0, CV.getWidth(), CV.getHeight());
        if (charsToDisplay > 26) { charsToDisplay = 26; }
        alice.drawPieChart(CV.getGraphicsContext2D(), charsToDisplay);
    }
```
As can be seen, it merely gets the int from the textField and checks it's not greater than 26--the total number of letters in the English alphabet and, thus, the greatest amount of slices that would need to be printed. ```CV``` is then cleared and ```alice```'s drawPieChart method is called, with the number just collected used as the parameter that tells how many slices to print. 
