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
### processReturn
Although this method is part of ```Main```, it is included as its own section as it can be better understood with the context gained from the other sections.
The processReturn is called whenever ```Return``` is entered in the TextField in ```BP```. The method is included below:
```Java
        int charsToDisplay = Integer.parseInt(pieInput.getText());
        CV.getGraphicsContext2D().clearRect(0, 0, CV.getWidth(), CV.getHeight());
        if (charsToDisplay > 26) { charsToDisplay = 26; }
        alice.drawPieChart(CV.getGraphicsContext2D(), charsToDisplay);
```
As can be seen, it merely gets the int from the textField and checks it's not greater than 26--the total number of letters in the English alphabet and, thus, the greatest amount of slices that would need to be printed. ```CV``` is then cleared and ```alice```'s drawPieChart method is called, with the number just collected used as the parameter that tells how many slices to print. 
