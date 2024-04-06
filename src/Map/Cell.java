package Map;

import java.security.SecureRandom;
import java.util.*;


public class Cell implements Comparable<Cell>{

    private ArrayList<int[]> options;
    private int[] weight = {1};
    private boolean collapsed;
    private Map<Double, Double> logCache;
    private final SecureRandom random = new SecureRandom();
    private final HashMap<int[], Integer> optionMap;

    public Cell(ArrayList<int[]> options, HashMap<int[], Integer> optionMap){
        this.options = options;
        this.optionMap = optionMap;
        collapsed = false;
        logCache = new HashMap<>();
    }

    public double entropy() {
        double entropy = 0;
        for (double probability : weight) {
            if (probability > 0) {
                entropy -= probability * log2(probability);
            }
        }

        if (entropy == 0) {
            entropy = 999;
        }

        return entropy;
    }

    private double log2(double x) {
        if (logCache.containsKey(x)) {
            return logCache.get(x);
        }
        double result = Math.log(x) / Math.log(2);
        logCache.put(x, result);
        return result;
    }

    private int minWeightSize(int[] weight){
        int min = weight[0];
        for (int v : weight) {
            if (v < min) {
                min = v;
            }
        }
        if (min > 200000000) {
            System.out.println("djhsbfjkds");
        }
        return min;
    }

    public boolean collapse(){
             if(!options.isEmpty()){
                if (weight[0] != 1) {
                    int[] tileChoice = null;
                    int[] collapsedTileWeight = new int[options.size()];
                    collapsedTileWeight[0] = 1;
                    for (int i = 0; i < collapsedTileWeight.length; i++) {
                        int index = i;
                        if (index != 0) {
                            index--;
                        }
                        collapsedTileWeight[i] = collapsedTileWeight[index] - weight[optionMap.get(options.get(i))];
                    }
                    try {
                        int randomInt = random.nextInt(minWeightSize(collapsedTileWeight), 1);
                        for (int i = 0; i < collapsedTileWeight.length; i++) {
                            if (randomInt >= collapsedTileWeight[i]) {
                                tileChoice = options.get(i);
                                break;
                            }
                        }
                    } catch (IllegalArgumentException e) {
                        return collapsed;
                    }
                    options = new ArrayList<>(Collections.singletonList(tileChoice));
                } else {
                    options = new ArrayList<>(Collections.singletonList(options.get(random.nextInt(options.size()))));
                }
            }else{
             return collapsed;
            }
            collapsed = true;
            return true;
    }

    public boolean isCollapsed(){
        return collapsed;
    }

    public void setOptions(ArrayList<int[]> options){
        this.options = options;
    }

    public void setWeight(int[] weight){
        this.weight = weight;
    }

    public void setState(){
        this.collapsed = false;
    }

    public ArrayList<int[]> getOptions() {
        return options;
    }

    @Override
    public int compareTo(Cell o) {
        return Double.compare(entropy(), o.entropy());
    }


}
