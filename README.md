# Pie Chart GUI

## Overview
Pie-Chart uses a custom Java shape hierarchy and color enum (the same one from my MyShape repository, which can be found at https://github.com/danrose499/MyShape). The Main file of the Pie-Chart prorgram creates an object of the ```HistogramAlphaBet``` class which reads a text file (```"Alice.txt"``` in this example, containing the story to "Alice in Wonderland") and creates a map with of all the letters (as the values) and their respective frequencies (as the keys). The map is then sorted by keys to print a Pie Chart using the ```MyArc``` class to print the slices of the graph. Using the created JavaFX GUI, the user can select how many of the most common letters to print on the pie chart, and the chart is updated accordingly, as seen below:
![Output](https://i.imgur.com/lGzdwmw.png?1)

## Walkthrough
The ```Main``` class begins with the following declarations:
```Java    TextField pieInput;
    String filename = "alice.txt";
    Canvas CV;
    HistogramAlphaBet alice;```
