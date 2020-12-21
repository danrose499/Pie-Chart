# Pie Chart GUI

## Overview
Pie-Chart uses a custom Java shape hierarchy and color enum (the same one from my MyShape repository, which can be found at https://github.com/danrose499/MyShape). The Main file of the Pie-Chart prorgram creates an object of the ```HistogramAlphaBet``` class which reads a text file (```"Alice.txt"``` in this example, containing the story to "Alice in Wonderland") and creates a map with of all the letters (as the values) and their respective frequencies (as the keys). The map is then sorted by keys to print a Pie Chart using the ```MyArc``` class to print the slices of the graph. Using the created JavaFX GUI, the user can select how many of the most common letters to print on the pie chart, and the chart is updated accordingly, as seen below:
![Output](https://i.imgur.com/lGzdwmw.png?1)

## Walkthrough
The ```Main``` class begins with the following declarations which are explained below:

```Java    
    TextField pieInput;
    String filename = "alice.txt";
    Canvas CV;
    HistogramAlphaBet alice;
```
```pieInput``` is where the user will input the number of slices they want to print; ```filename``` is a String with the name of the file whose characters will be counted (in this example, filename is set to ```"alice.txt"``` which is the name of a file containing the story to "Alice In Wonderland"); ```CV``` is the Canvas which will display the Pie Chart, along with the rest of the GUI; and ```alice``` is an object of the ```HistogramAlphaBet``` class which reads a text file and has a method to return a sorted LinkedHashmap that has the occrunces of each letter in the text file in order of most-common frequency.  

```Main``` continues by overriding ```start```. In the overriden version, a title is given to the ```primaryStage```, a BoderPane is declared (BP), and the ```addCanvas``` method is called, setting ```CV``` to 600x600, setting ```alice``` to ```Java new HistogramAlphaBet(filename)```, and calling ```alice```'s ```drawPieChart``` method. 
