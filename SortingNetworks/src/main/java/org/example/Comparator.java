package org.example;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
public class Comparator {
    private  int from;
    private  int to;
    private Line line;
    private int direction;
    private double yOffset;
    private double yInterval;
    private double xPosition;
    private int index;
    private boolean execute;


    public Comparator(int from, int to, double yOffset, double yInterval, double xPosition,int direction,boolean execute) {
        this.from = from;
        this.to = to;
       // this.line = new Line(xPosition, yOffset + (from * yInterval),  xPosition, yOffset + (to * yInterval));
        //this.line.setStroke(Color.RED);
        this.yOffset = yOffset;
        this.yInterval = yInterval;
        this.xPosition = xPosition;
        this.direction = direction;
        // Initialize the visual representation of the comparator
        this.line = new Line(xPosition, yOffset + (from * yInterval), xPosition, yOffset + (to * yInterval));
       // this.line.setStroke(Color.RED);
        this.index = index;
        this.execute=false;

    }

    public int getIndex() {
        return index;
    }
    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }
    public boolean isExecute(){
        return execute;
    }
    public void setExecute(boolean execute){
        this.execute=execute;
    }

    public Line getLine() {
        return line;
    }
    public double getYInterval() {
        return yInterval;
    }

    public int  compareAndSwap(int[] array) {
        if ((direction == 1 && array[from] > array[to]) || (direction == 0 && array[from] < array[to])) {
            int temp = array[from];
            array[from] = array[to];
            array[to] = temp;
            this.execute=true;
            return 1;
        }
        return 0;
    }

}
