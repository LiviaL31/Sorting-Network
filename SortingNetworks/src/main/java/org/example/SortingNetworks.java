package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SortingNetworks {

    private double yOffset = 30;
    private double yInterval = 30;
    private double xPosition = 50;
 int y;
    private static List<Comparator> comparators;
    private int inputSize;
    private NumberInputValidator inputValidator;

    public SortingNetworks(int numElements){
        this.inputSize = numElements;
        comparators = new ArrayList<>();
        generateBitonicComparators(numElements);
    }
    public  List<Comparator> getComparators() {

        return this.comparators;
    }


    public void bitonicComparatorSequence(int low,int count, boolean ascending){
        if (count > 1) {
            int k1 = count / 2;
            int rest1 = count-k1;
                bitonicComparator(low, k1, true, 1);
                bitonicComparator(low + k1, rest1, false, 1);

            bitonicMergeComparators(low, count, ascending,0);
        }
    }
    public void bitonicComparator(int low,int count, boolean ascending,int marge){
        if (count > 1) {
            int k1 = count / 2;
            int rest1 = count-k1;
            if(k1>1){
            bitonicComparator(low, k1, ascending, 1);}
            if(rest1>1){
            bitonicComparator(low + k1, rest1, ascending, 1);}

            bitonicMergeComparators(low, count, ascending,1);
        }
    } //20,19,18,17,16,15,14,13,12,11,10,9,8
  private void bitonicMergeComparators(int low, int count, boolean ascending,int call){
     //   double XPosition = xPosition;
        if (count > 1) {
            int k = count / 2;
            int rest = count - k;
            for (int i = low; i < low + k; i++) {
                if (i + k < low + count) {
                    comparators.add(new Comparator(i, i + k, yOffset, yInterval, xPosition, ascending ? 1 : 0,false));
                    xPosition = xPosition + 16;
                }
            }
            if (count % 2 != 0) {
                if (low + k < count && count > 3) {
                    // Comparator suplimentar pentru a compara ultimul element din prima jumătate cu primul element din a doua jumătate
                    comparators.add(new Comparator(low + k, count - 1, yOffset, yInterval, xPosition, ascending ? 1 : 0,false));
                    xPosition += 16;
                    if (call == 0) {
                        comparators.add(new Comparator((low+k)/2, low + k, yOffset, yInterval, xPosition, ascending ? 1 : 0,false));
                        xPosition +=16;
                    }
                    int x = 2;
                    while (x != 0) {

                        comparators.add(new Comparator(low + k - 2, low + k - 1, yOffset, yInterval, xPosition, ascending ? 1 : 0,false));
                        xPosition += 10;
                        comparators.add(new Comparator(low + k - 1, low + k, yOffset, yInterval, xPosition, ascending ? 1 : 0,false));
                        xPosition += 10;
                        x--;
                    }
                    xPosition+=16;
                    comparators.add(new Comparator((low+k)/2-1, low + k, yOffset, yInterval, xPosition, ascending ? 1 : 0,false));
                    xPosition +=16;

                } else {
                    if (count > 3) {
                        comparators.add(new Comparator(low + k, low + count - 1, yOffset, yInterval, xPosition, ascending ? 1 : 0,false));
                        xPosition += 16;
                        comparators.add(new Comparator(low + k - 1, low + k, yOffset, yInterval, xPosition, ascending ? 1 : 0,false));
                        xPosition += 16;
                        if(k>3) {
                            comparators.add(new Comparator(low + (k / 2), low + k, yOffset, yInterval, xPosition, ascending ? 1 : 0, false));
                            xPosition += 16;
                            comparators.add(new Comparator(low + k, low + k + (k / 2), yOffset, yInterval, xPosition, ascending ? 1 : 0, false));
                            xPosition += 16;
                            comparators.add(new Comparator(low + (k / 2) + 1, low + k + 1, yOffset, yInterval, xPosition, ascending ? 1 : 0, false));
                        }
                    }
                    xPosition+=10;
                }
            }
            if (k > 1 && count % 2 == 0) {
               // xPosition += 10;
               // comparators.add(new Comparator(low + k - 2, low + k-1, yOffset, yInterval, xPosition, ascending ? 1 : 0,false));
                xPosition += 10;
                comparators.add(new Comparator(low + k - 1, low + k, yOffset, yInterval, xPosition, ascending ? 1 : 0,false));
                xPosition += 10;
                comparators.add(new Comparator(low + k - 2, low + k-1, yOffset, yInterval, xPosition, ascending ? 1 : 0,false));
                int z=k/2;
                int a= low+k-1;
                while(z!=0){
                    xPosition+=10;
                comparators.add(new Comparator(a, low + k+y, yOffset, yInterval, xPosition, ascending ? 1 : 0,false));
                z--;
                a++;}
                int t = low+k+(k/2);
              int v= low+(k/2)-1;
              while(t>low+k){
                  xPosition += 16;
                  comparators.add(new Comparator(v, t, yOffset, yInterval, xPosition, ascending ? 1 : 0,false));
                  t--;
                  v++;
              }
                xPosition += 16;
                comparators.add(new Comparator(low+(k/2), low + k, yOffset, yInterval, xPosition, ascending ? 1 : 0,false));
                xPosition +=10;
            }
            xPosition += 10;

            if (k > 1 || rest > 1) {
                if(k>1){
                bitonicMergeComparators(low, k, ascending, 1);}

                if(rest>1){
                bitonicMergeComparators(low + k, rest, ascending, 1);}
                if(call==0) {
                    y=1;
                    while ((low+k)/2+y < low+k -1 ){
                    comparators.add(new Comparator((low + k) / 2 +y, low + k, yOffset, yInterval, xPosition, ascending ? 1 : 0,false));
                    xPosition += 16;
                    y++;}
                }
                xPosition+=30;
                if(low+k>1 ){
                comparators.add(new Comparator(low + k - 1, low + k, yOffset, yInterval, xPosition-30, ascending ? 1 : 0,false));}
                if(k>1){
                comparators.add(new Comparator(low + k - 2, low + k-1, yOffset, yInterval, xPosition-15, ascending ? 1 : 0,false));
                }
                comparators.add(new Comparator(low + k , low + k+1, yOffset, yInterval, xPosition-5, ascending ? 1 : 0,false));
            xPosition+=10;
            }
          //  if(low+k+1<=count){
            //comparators.add(new Comparator(low + k , low + k+1, yOffset, yInterval, xPosition, ascending ? 1 : 0));
            //xPosition += 5;}
            }

       /*if (count == 3 && ascending) { //nu ar mai fi nevoie
           if(low>0) {
               comparators.add(new Comparator(low - 1, low, yOffset, yInterval, xPosition, ascending ? 1 : 0));
               xPosition += 10;
           }
           comparators.add(new Comparator(low , low +1, yOffset, yInterval, xPosition, ascending ? 1 : 0));
           xPosition += 10;
       }*/
      }
    public void generateBitonicComparators(int numElements) {
        if(numElements==2){
            comparators.add(new Comparator(0 , 1, yOffset, yInterval,50, true ? 1: 0,false));
        }else
        {if(numElements==3) {
            comparators.add(new Comparator(1 , 2, yOffset, yInterval,50, true ? 1: 0,false));
            comparators.add(new Comparator(0 , 1, yOffset, yInterval,80, true ? 1: 0,false));
            comparators.add(new Comparator(1,  2, yOffset, yInterval,120, true ? 1: 0,false));

        }
        else{
        bitonicComparatorSequence(0, numElements, true);
    }}}

}
