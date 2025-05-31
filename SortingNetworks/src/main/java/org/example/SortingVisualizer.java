package org.example;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.scene.control.ScrollPane;
import javafx.util.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;
import org.example.Comparator;

public class SortingVisualizer {
    private int k = 0;
    private int[] array;
    private List<Comparator> comparators;

    private Color[] currentColors;
    private SortingNetworks sortingNetwork;
    private Pane pane;
    private Label[] labels;
    private Semaphore swapSemaphore = new Semaphore(1);
    private Line[] horizontalLines;
    private List<Line> dottedLine;
    private Line LastHorizontalLine = null;
    double newWidth = 0;
   private int semaphore = 0;

    private  double[] lastCoordonate;
   private Map<Integer,Integer> firstComparator = new HashMap<>();

    private final Color[] dottedLineColors = {Color.RED, Color.GREEN, Color.BLUE, Color.ORANGE, Color.PURPLE, Color.YELLOW, Color.PINK, Color.AQUA,
            Color.BROWN, Color.CORAL, Color.DARKGREY, Color.FUCHSIA, Color.GOLD, Color.HOTPINK, Color.INDIANRED, Color.KHAKI, Color.LIGHTBLUE, Color.MAGENTA, Color.YELLOWGREEN, Color.VIOLET}; // Adăugați mai multe culori dacă aveți mai multe linii

    //private final double dottedLineYOffset = 5;

    public SortingVisualizer(int[] array) {
        this.array = array;
        double yOffset = 30;
        double yInterval = 30;
        double xPosition = 200; // Initial x position for comparators
        this.sortingNetwork = new SortingNetworks(array.length);
        horizontalLines = new Line[array.length];
        this.dottedLine = new ArrayList<>();
        this.pane = new Pane();
        this.pane.setPadding(new Insets(10));
        lastCoordonate = new double[array.length];
        //Map<Integer,Integer> firstComparator = new HashMap<>();

    }

    public void SortingNetworks(int[] array, List<Comparator> comparators) {
        this.array = array;
        this.comparators = comparators;
    }
    public void drawDottedLine(double startX, double yOffset, int i, double yInterval, double firstComparatorX) {
        // Determină coordonata y pe baza indexului și intervalului
        double y = yOffset + (i * yInterval);

        // Creează o linie punctată
        Line line = new Line(startX, y, firstComparatorX, y);

        // Setează stilul pentru a crea un efect punctat
        line.setStroke(Color.BLACK);
        line.setStrokeWidth(2);
        line.getStrokeDashArray().addAll(5d, 5d); // Setează lățimea și spațiul punctelor

        // Adaugă linia în containerul grafic (ex: pane sau canvas)
       // someContainer.getChildren().add(line); // Asigură-te că someContainer este containerul tău
    }
    public void show(Stage stage) {
        pane = new Pane();
        pane.setPadding(new Insets(10));
        if (array == null || array.length == 0) {
            System.out.println("Error: array is null or empty");
            return;
        }

        currentColors = new Color[array.length];
        for (int i = 0; i < array.length; i++){
            currentColors[i] = dottedLineColors[i%dottedLineColors.length];
       }

        labels = new Label[array.length];
        horizontalLines = new Line[array.length]; // Adaugăm inițializarea pentru linii orizontale
        int numElements = array.length;

        double yOffset = 30;
        double yInterval = 30;
        double width = 150;
        double startX = 40;
        int x;

        for (int i = 0; i < array.length; i++) {
            double numComparators = numElements * Math.log(numElements) / Math.log(2);
            if(numElements==2){
                newWidth =100;
            }/*else if(numElements<10){
                    newWidth=150+(50*(numComparators-2));
                }else if (numElements<14){
                newWidth  =150+(50*(numComparators))+30;
            }else if(numElements>17 && numElements<20){
                newWidth  =430+(50*(numComparators+5));
            }else if(numElements >=14 && numElements<=17){
                newWidth  =width+(50*(numComparators+7));
            }else if(numElements>=20&& numElements<22){
                newWidth = 560+(50*(numComparators+7));
            }*/else //if(numElements>=22){
            { int y=2;
                int copyNrElements= numElements;
                while (copyNrElements>10){
                    y++;
                    copyNrElements-=10;
                }
                //if(copyNrElements!=0||)
                x= (numElements/y)+7;
                newWidth = 200+(x*5*numComparators);
            }

            Line line = new Line(startX, yOffset + (i * yInterval), newWidth, yOffset + (i * yInterval));
            line.setStroke(Color.BLACK);
            pane.getChildren().add(line);

            //afisare din stanga
            labels[i] = new Label(Integer.toString(array[i]));
            labels[i].setLayoutX(startX -40); // Poziționare în partea dreaptă a scenei
            labels[i].setLayoutY(yOffset + (i * yInterval) - 10); // Ajustare verticală pentru a fi aliniat cu linia
            labels[i].setPadding(new Insets(0, 10, 0, 10));
            pane.getChildren().add(labels[i]);

            //afisare din dreapta
            labels[i] = new Label(Integer.toString(array[i]));
            labels[i].setLayoutX(newWidth + 5);
            labels[i].setLayoutY(yOffset + (i * yInterval) - 10);
            labels[i].setPadding(new Insets(0, 10, 0, 10));
            pane.getChildren().add(labels[i]);


        }
        if (sortingNetwork == null) {
            System.out.println("Error: sortingNetwork or its comparators are null");
            return;
        }
        List<Comparator> comparators = sortingNetwork.getComparators();
        if (comparators == null) {
            System.out.println("Error: comparators list is null");
            return;
        }
        for (Comparator comparator : comparators) {
            if (comparator != null && comparator.getLine() != null) {
                pane.getChildren().add(comparator.getLine());
            } else {
                System.out.println("aici");
            }
        }
        // Adăugarea unui ScrollPane pentru a permite scroll
        ScrollPane scrollPane = new ScrollPane(pane);
        scrollPane.setFitToWidth(false);  // Asigură-te că lățimea se ajustează
        scrollPane.setFitToHeight(false); // Asigură-te că înălțimea se ajustează
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setMaxHeight(Region.USE_PREF_SIZE);
        scrollPane.setMaxWidth(Region.USE_PREF_SIZE);



        Scene scene = new Scene(scrollPane, 400, array.length * yInterval + 40);
        stage.setScene(scene);
        stage.show();

        new Thread(()->runVisualization(0)).start();
        }
    private void addDottedHorizontalLine(int index, double startX, double endX) {
        double yOffset = 30;
        double yInterval = 30;
        double yPosition = yOffset + (index * yInterval) + 5;  // Poziția verticală a liniei

        // Crearea liniei punctate
        Line dottedLine = new Line(startX, yPosition, endX, yPosition);
        dottedLine.setStroke(dottedLineColors[index % dottedLineColors.length]);  // Setarea culorii
        dottedLine.getStrokeDashArray().addAll(5d, 5d);  // Definirea liniei punctate
        pane.getChildren().add(dottedLine);  // Adăugarea liniei pe panou
    }
    private Comparator findNextComparator(int currentIndex,int lineIndex) {
        List<Comparator> comparators = sortingNetwork.getComparators();
        for (int i =currentIndex+1;i<comparators.size();i++) {
            Comparator comparator = comparators.get(i);
            if (comparator!= null&& !comparator.isExecute() &&(comparator.getFrom()==lineIndex||comparator.getTo() ==lineIndex )) {
                return comparators.get(i);
            }
        }
        return null; // Dacă nu găsește, returnează null
    }

    private void addDiagonalDottedLine(int fromIndex, int toIndex, double fromX, double toX,Color color) {
        double yOffset = 30;
        double yInterval = 30;

        // Coordonatele de start și final
        double fromY = yOffset + (fromIndex * yInterval) + 5;
        double toY = yOffset + (toIndex * yInterval) + 5;

        // Crearea liniei diagonale punctate
        Line dottedLine = new Line(fromX, fromY, toX, toY);
        dottedLine.setStroke(color);  // Setarea culorii
        dottedLine.getStrokeDashArray().addAll(5d, 5d);  // Definirea liniei punctate
        pane.getChildren().add(dottedLine);  // Adăugarea liniei pe panou
    }

    private void continueDottedLine(int index, double startX, double endX,Color color) {
        double yOffset = 30;
        double yInterval = 30;
        // Coordonata Y a noului element după swap
        double startY = yOffset + (index * yInterval) +5;

        // Crearea liniei orizontale punctate de la noua poziție
        Line dottedLine = new Line(startX, startY, endX, startY);
        dottedLine.setStroke(color);  // Setăm culoarea liniei punctate
        dottedLine.getStrokeDashArray().addAll(5d, 5d);  // Definim linia punctată
        pane.getChildren().add(dottedLine);  // Adăugăm linia pe panou
    }
    private void runVisualization(int comparatorIndex) {
        if(comparatorIndex >= sortingNetwork.getComparators().size()) {
            return;
        }

        Line[] lastHorizontalLine = {null};
        Comparator comparator = sortingNetwork.getComparators().get(comparatorIndex);
        int index1 = comparator.getFrom();
        int index2 = comparator.getTo();

        // Coordonatele pozițiilor inițiale ale numerelor
        double initialX = 40;
        double yInterval = 30;
        double comparatorX = comparator.getLine().getStartX();
        double initialY1 = 30 + (index1 * yInterval) + 5;
        double initialY2 = 30 + (index2 * yInterval) + 5;
        //Sincronizare executiei vizuale

        Platform.runLater(() -> {
            comparator.getLine().setStroke(Color.GREEN);

        });
        k = comparator.compareAndSwap(array);
        PauseTransition pause = new PauseTransition(Duration.seconds(1)); // întârziere de 1 secundă
        pause.setOnFinished(event -> {
            Platform.runLater(() -> {
                comparator.getLine().setStroke(Color.RED);

            });
        });
        pause.play();

        Platform.runLater(() -> {
            double startX1BeforeSwap=lastCoordonate[index1];
            double startX2BeforeSwap = lastCoordonate[index2];
            //fac sa se schimbe valorile in caz de swap
            labels[index1].setText(Integer.toString(array[index1]));
            labels[index2].setText(Integer.toString(array[index2]));


            double finalX = comparator.getLine().getEndX() + 10;
            double finalY1 = 30 + (index1 * 30) + 5;
            double finalY2 = 30 + (index2 * 30) + 5;
            Comparator nextComparator1 = findNextComparator(comparatorIndex,index1);
            Comparator nextComparator2 = findNextComparator(comparatorIndex,index2);
            if(startX1BeforeSwap<40){
                if(k!=1){
                    startX1BeforeSwap=40;
                    double endX1BeforeSwap = nextComparator1 != null ? nextComparator1.getLine().getStartX() : finalX; // folosește coordonata X a următorului comparator
                    continueDottedLine(index1, startX1BeforeSwap, endX1BeforeSwap, currentColors[index1]);  // Linia continuă pentru elementul de la index1
                    lastCoordonate[index1] = endX1BeforeSwap;

                }else {  startX1BeforeSwap=40;
                    double endX1BeforeSwap = nextComparator1 != null ? nextComparator1.getLine().getStartX() : finalX; // folosește coordonata X a următorului comparator
                    continueDottedLine(index1, startX1BeforeSwap, finalX, currentColors[index1]);  // Linia continuă pentru elementul de la index1
                    lastCoordonate[index1] = endX1BeforeSwap;
                }}
            if(startX2BeforeSwap<40){
                if(k!=1){  startX2BeforeSwap=40;
                    double endX2BeforeSwap = nextComparator2 != null ? nextComparator2.getLine().getStartX() : finalX;
                    continueDottedLine(index2, startX2BeforeSwap,endX2BeforeSwap, currentColors[index2]);
                    lastCoordonate[index2] = endX2BeforeSwap;}
                else{
                    startX2BeforeSwap=40;
                    double endX2BeforeSwap = nextComparator2 != null ? nextComparator2.getLine().getStartX() : finalX;
                    continueDottedLine(index2, startX2BeforeSwap, finalX, currentColors[index2]);
                    lastCoordonate[index2] = endX2BeforeSwap;
                }}


            if (k == 1) {
                Color colorFor1 = currentColors[index2];
                Color colorFor2 = currentColors[index1];
                      /*  if(startX2BeforeSwap>finalX){
                            continueDottedLine(index2,finalX,startX2BeforeSwap,currentColors[index2]);
                        }*/


                addDiagonalDottedLine(index1, index2, comparator.getLine().getStartX(), finalX, colorFor2);
                addDiagonalDottedLine(index2, index1, comparator.getLine().getStartX(), finalX, colorFor1);

                double endX1 = nextComparator1 != null ? nextComparator1.getLine().getStartX() : finalX ;
                double endX2 = nextComparator2 != null ? nextComparator2.getLine().getStartX() : finalX ;

                // nextComparator1 = findNextComparator(comparatorIndex,index1);
                // nextComparator2 = findNextComparator(comparatorIndex,index2);
                if(nextComparator1 == null){
                    continueDottedLine(index1, finalX, newWidth, currentColors[index2]);  // Linia continuă pentru elementul de la index1
                }else{
                    continueDottedLine(index1 ,finalX, endX1-5,currentColors[index2]); } // Linia continuă pentru elementul de la index1
                if(nextComparator2 == null){
                    continueDottedLine(index2,finalX,newWidth,currentColors[index1]);
                }else{
                    continueDottedLine(index2, finalX, endX2, currentColors[index1]);}
                startX1BeforeSwap=endX1;
                startX2BeforeSwap = endX2;


                // Actualizăm culorile curente după swap
                Color tempColor = currentColors[index1];
                currentColors[index1] = currentColors[index2];
                currentColors[index2] = tempColor;

                lastCoordonate[index1]= endX1;
                lastCoordonate[index2]=endX2;

            } else  {
                // logica pentru cazul fără swap: asigură-te că liniile orizontale punctate merg spre comparatorii corespunzători
                nextComparator1 = findNextComparator(comparatorIndex, index1);
                nextComparator2 = findNextComparator(comparatorIndex, index2);

                double endX1 = nextComparator1 != null ? nextComparator1.getLine().getStartX() : finalX;
                double endX2 = nextComparator2 != null ? nextComparator2.getLine().getStartX() : finalX;

              //  continueDottedLine(index1, finalX, endX1, currentColors[index1]);
               //   continueDottedLine(index2, finalX, endX2, currentColors[index2]);
                if(nextComparator1 == null){
                    continueDottedLine(index1, finalX-10, newWidth, currentColors[index1]);  // Linia continuă pentru elementul de la index1
                }else{
                    continueDottedLine(index1,finalX-10, endX1, currentColors[index1]); } // Linia continuă pentru elementul de la index1
                if(nextComparator2 == null){
                    continueDottedLine(index2,finalX-10,newWidth,currentColors[index2]);
                }else{
                    continueDottedLine(index2, finalX-10, endX2, currentColors[index2]);}
            }
            //comparator.getLine().setStrokenew(color.Red);
            if (lastHorizontalLine[0] != null) { // Verifica daca exista o linie orizontala anterioara
                lastHorizontalLine[0].setEndX(finalX); // Ajusteaza finalul liniei
            }
            //   addHorizontalLines(index1,index2);
           /* Comparator nextComparator = findNextComparator(comparatorIndex,index1);
            if (nextComparator != null) {
                double nextX = nextComparator.getLine().getStartX(); // X-ul de început al următorului comparator
                // Creăm linia orizontală de la finalul comparatorului curent la începutul următorului
                continueDottedLine(index1, finalX, nextX, currentColors[index1]);
                continueDottedLine(index2, finalX, nextX, currentColors[index2]);
            }*/

            // Thread.sleep(2000); // Delay for visualization


            new Timeline(new KeyFrame(Duration.millis(2000), e -> {
                runVisualization(comparatorIndex + 1);  // Apelăm recursiv pentru următorul comparator
            })).play();

        });}
    private void updateLines(int from, int to, Color color1, Color color2) {
        for (Comparator comparator : sortingNetwork.getComparators()) {
            if ((comparator.getFrom() == from && comparator.getTo() == to) ||
                    (comparator.getFrom() == to && comparator.getTo() == from)) {
                Platform.runLater(() -> {
                    comparator.getLine().setStroke(color1.interpolate(color2, 0.5));
                });
                break;
            }
        }
    }
    private void addVerticalLines(int index1, int index2) {
        double yOffset = 30;
        double yInterval = 30;
        double startX = 40;
        double endX = sortingNetwork.getComparators().get(0).getLine().getStartX();
        double offset = 5;

        Line verticalLine1 = new Line(startX, yOffset + (index1 * yInterval) + offset, endX, yOffset + (index1 * yInterval) + offset);
        verticalLine1.setStroke(Color.BLACK);
        verticalLine1.getStrokeDashArray().addAll(5d, 5d);
        pane.getChildren().add(verticalLine1);
    }

    private void addHorizontalLines(int index1, int index2) {
        double yOffset = 30;
        double yInterval = 30;
        double startX = 40;
        double endX = 200; // End X position for the horizontal dotted lines
        double offset = 10;
        Line horizontalLine1 = new Line(startX, yOffset + (index1 * yInterval) + offset, 140, yOffset + (index1 * yInterval) + offset);
        horizontalLine1.setStroke(dottedLineColors[index1 % dottedLineColors.length]);
        horizontalLine1.getStrokeDashArray().addAll(5d, 5d);
        pane.getChildren().add(horizontalLine1);
        for (Comparator comparator : sortingNetwork.getComparators()) {
            if (comparator.getFrom() == index1 || comparator.getTo() == index1) {
                double comparatorX = comparator.getLine().getStartX();
                Line horizontalLine2 = new Line(startX, yOffset + (index2 * yInterval) + offset, comparatorX, yOffset + (index2 * yInterval) + offset);
                horizontalLine2.setStroke(dottedLineColors[index1 % dottedLineColors.length]);
                horizontalLine2.getStrokeDashArray().addAll(5d, 5d);
                pane.getChildren().add(horizontalLine2);
                break;
            }
        }
    }

    private Line lastHorizontalLine = null;
    private Color lastHorizontalLineColor= Color.BLACK;

    private Line createHorizontalDottedLine(double startX, double startY, double endX, double endY, Color color) {

        Line dottedLine = new Line(startX, startY, endX, endY);
        dottedLine.setStroke(color);
        dottedLine.getStrokeDashArray().addAll(5d, 5d); // Facem linia punctată
        pane.getChildren().add(dottedLine);
        return dottedLine;
    }

}