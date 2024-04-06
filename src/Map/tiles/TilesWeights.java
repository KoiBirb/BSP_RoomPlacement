package Map.tiles;

import java.util.ArrayList;
import java.util.HashMap;

public class TilesWeights {

    public static final int[] weightNull = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
    private final ArrayList<int[]>options;

    int cellGrassCharacter = 0;

    int cellPath1Character = 1;
    int cellPath1DownCharacter = 2;
    int cellPath1InvertedCharacter = 3;
    int cellPath1Left1Character = 4;
    int cellPath1Left2Character = 5;
    int cellPath1LeftDownCharacter = 6;
    int cellPath1LeftRightCharacter = 7;
    int cellPath1RightCharacter = 8;
    int cellPath1SurroundCharacter = 9;
    int cellPath1UpCharacter = 10;
    int cellPath1UpDownCharacter = 11;

    int cellPath2Character = 12;
    int cellPath2DownCharacter = 13;
    int cellPath2LeftUpCharacter = 14;
    int cellPath2LeftUpDownCharacter = 15;
    int cellPath2Right1Character = 16;
    int cellPath2Right2Character = 17;
    int cellPath2UpCharacter = 18;
    int cellPath2UpDownCharacter = 19;

    int cellPath3Character = 20;
    int cellPath3DownCharacter = 21;
    int cellPath3LeftCharacter = 22;
    int cellPath3LeftRightCharacter = 23;
    int cellPath3LeftRightUpCharacter = 24;
    int cellPath3RightCharacter = 25;
    int cellPath3RightDownCharacter = 26;

    int cellPath4Character = 27;
    int cellPath4LeftCharacter = 28;
    int cellPath4RightUpCharacter = 29;
    int cellPath4RightUpDownCharacter = 30;
    int cellPath4UpCharacter = 31;
    int cellPath4UpDownCharacter = 32;

    int cellNullCharacter = 999;

    // Weights in percents Order from option index 0 - 32
    int[] weightGrass = {25, 0, 2, 0, 2, 2, 2, 2, 2, 2, 2, 2, 0, 2, 2, 2, 2, 2, 2, 2, 0, 2, 2, 2, 2, 2, 2, 0, 2, 2, 2, 2, 2};

    int[] weightPath = {0, 3, 3, 0, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3};
    int[] weightPathWGrass = {25, 2, 2, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2};

    public TilesWeights(ArrayList<int[]>options){
        this.options = options;
    }

    public static HashMap<Integer, int[]> weightMap = new HashMap<>();

    HashMap<int[], Boolean> grassCompatibilityMap = new HashMap<>();

    HashMap<int[], Boolean> path1CompatibilityMap = new HashMap<>();
    HashMap<int[], Boolean> path1DownCompatibilityMap = new HashMap<>();
    HashMap<int[], Boolean> Path1InvertedCompatibilityMap = new HashMap<>();
    HashMap<int[], Boolean> path1Left1CompatibilityMap = new HashMap<>();
    HashMap<int[], Boolean> path1Left2CompatibilityMap = new HashMap<>();
    HashMap<int[], Boolean> path1LeftDownCompatibilityMap = new HashMap<>();
    HashMap<int[], Boolean> path1LeftRightCompatibilityMap = new HashMap<>();
    HashMap<int[], Boolean> path1RightCompatibilityMap = new HashMap<>();
    HashMap<int[], Boolean> path1SurroundCompatibilityMap = new HashMap<>();
    HashMap<int[], Boolean> path1UpCompatibilityMap = new HashMap<>();
    HashMap<int[], Boolean> path1UpDownCompatibilityMap = new HashMap<>();

    HashMap<int[], Boolean> path2CompatibilityMap = new HashMap<>();
    HashMap<int[], Boolean> path2DownCompatibilityMap = new HashMap<>();
    HashMap<int[], Boolean> path2LeftUpCompatibilityMap = new HashMap<>();
    HashMap<int[], Boolean> path2LeftUpDownCompatibilityMap = new HashMap<>();
    HashMap<int[], Boolean> path2Right1CompatibilityMap = new HashMap<>();
    HashMap<int[], Boolean> path2Right2CompatibilityMap = new HashMap<>();
    HashMap<int[], Boolean> path2UpCompatibilityMap = new HashMap<>();
    HashMap<int[], Boolean> path2UpDownCompatibilityMap = new HashMap<>();

    HashMap<int[], Boolean> path3CompatibilityMap = new HashMap<>();
    HashMap<int[], Boolean> path3DownCompatibilityMap = new HashMap<>();
    HashMap<int[], Boolean> path3LeftCompatibilityMap = new HashMap<>();
    HashMap<int[], Boolean> path3LeftRightCompatibilityMap = new HashMap<>();
    HashMap<int[], Boolean> path3LeftRightUpCompatibilityMap = new HashMap<>();
    HashMap<int[], Boolean> path3RightCompatibilityMap = new HashMap<>();
    HashMap<int[], Boolean> path3RightDownCompatibilityMap = new HashMap<>();

    HashMap<int[], Boolean> path4CompatibilityMap = new HashMap<>();
    HashMap<int[], Boolean> path4LeftCompatibilityMap = new HashMap<>();
    HashMap<int[], Boolean> path4RightUpCompatibilityMap = new HashMap<>();
    HashMap<int[], Boolean> path4RightUpDownCompatibilityMap = new HashMap<>();
    HashMap<int[], Boolean> path4UpCompatibilityMap = new HashMap<>();
    HashMap<int[], Boolean> path4UpDownCompatibilityMap = new HashMap<>();

    HashMap<int[], Boolean> nullCompatibilityMap = new HashMap<>();

    public static HashMap<Integer, HashMap<int[], Boolean>> tileCompatibilityHashMapFinder = new HashMap<>();

    public void initialize(){

        for (int i = 0; i < options.size(); i++) {

            if (i != 1 || i != 12 || i != 20 || i != 27) {
                grassCompatibilityMap.put(options.get(i), true);
            } else {
                grassCompatibilityMap.put(options.get(i), false);
            }

            if (i == 0){
                path1CompatibilityMap.put(options.get(i), false);
                path2CompatibilityMap.put(options.get(i), false);
                path3CompatibilityMap.put(options.get(i), false);
                path4CompatibilityMap.put(options.get(i), false);
            } else {
                path1CompatibilityMap.put(options.get(i), true);
                path2CompatibilityMap.put(options.get(i), true);
                path3CompatibilityMap.put(options.get(i), true);
                path4CompatibilityMap.put(options.get(i), true);
            }


            path1DownCompatibilityMap.put(options.get(i), true);
            Path1InvertedCompatibilityMap.put(options.get(i), true);
            Path1InvertedCompatibilityMap.put(options.get(i), true);
            path1Left1CompatibilityMap.put(options.get(i), true);
            path1Left2CompatibilityMap.put(options.get(i), true);
            path1LeftDownCompatibilityMap.put(options.get(i), true);
            path1LeftRightCompatibilityMap.put(options.get(i), true);
            path1LeftRightCompatibilityMap.put(options.get(i), true);
            path1RightCompatibilityMap.put(options.get(i), true);
            path1SurroundCompatibilityMap.put(options.get(i), true);
            path1UpCompatibilityMap.put(options.get(i), true);
            path1UpCompatibilityMap.put(options.get(i), true);
            path1UpDownCompatibilityMap.put(options.get(i), true);

            path2DownCompatibilityMap.put(options.get(i), true);
            path2LeftUpCompatibilityMap.put(options.get(i), true);
            path2LeftUpDownCompatibilityMap.put(options.get(i), true);
            path2Right1CompatibilityMap.put(options.get(i), true);
            path2Right2CompatibilityMap.put(options.get(i), true);
            path2UpCompatibilityMap.put(options.get(i), true);
            path2UpDownCompatibilityMap.put(options.get(i), true);

            path3DownCompatibilityMap.put(options.get(i), true);
            path3LeftCompatibilityMap.put(options.get(i), true);
            path3LeftRightCompatibilityMap.put(options.get(i), true);
            path3LeftRightUpCompatibilityMap.put(options.get(i), true);
            path3RightCompatibilityMap.put(options.get(i), true);
            path3RightDownCompatibilityMap.put(options.get(i), true);

            path4LeftCompatibilityMap.put(options.get(i), true);
            path4RightUpCompatibilityMap.put(options.get(i), true);
            path4RightUpDownCompatibilityMap.put(options.get(i), true);
            path4UpCompatibilityMap.put(options.get(i), true);
            path4UpDownCompatibilityMap.put(options.get(i), true);

            nullCompatibilityMap.put(options.get(i), false);
        }

        tileCompatibilityHashMapFinder.put(cellGrassCharacter,grassCompatibilityMap);

        tileCompatibilityHashMapFinder.put(cellPath1Character,path1CompatibilityMap);
        tileCompatibilityHashMapFinder.put(cellPath1DownCharacter,path1DownCompatibilityMap);
        tileCompatibilityHashMapFinder.put(cellPath1InvertedCharacter,Path1InvertedCompatibilityMap);
        tileCompatibilityHashMapFinder.put(cellPath1Left1Character,path1Left1CompatibilityMap);
        tileCompatibilityHashMapFinder.put(cellPath1Left2Character,path1Left2CompatibilityMap);
        tileCompatibilityHashMapFinder.put(cellPath1LeftDownCharacter,path1LeftDownCompatibilityMap);
        tileCompatibilityHashMapFinder.put(cellPath1LeftRightCharacter,path1LeftRightCompatibilityMap);
        tileCompatibilityHashMapFinder.put(cellPath1RightCharacter,path1RightCompatibilityMap);
        tileCompatibilityHashMapFinder.put(cellPath1SurroundCharacter,path1SurroundCompatibilityMap);
        tileCompatibilityHashMapFinder.put(cellPath1UpCharacter,path1UpCompatibilityMap);
        tileCompatibilityHashMapFinder.put(cellPath1UpDownCharacter,path1UpDownCompatibilityMap);

        tileCompatibilityHashMapFinder.put(cellPath2Character,path2CompatibilityMap);
        tileCompatibilityHashMapFinder.put(cellPath2DownCharacter,path2DownCompatibilityMap);
        tileCompatibilityHashMapFinder.put(cellPath2LeftUpCharacter,path2LeftUpCompatibilityMap);
        tileCompatibilityHashMapFinder.put(cellPath2LeftUpDownCharacter,path2LeftUpDownCompatibilityMap);
        tileCompatibilityHashMapFinder.put(cellPath2Right1Character,path2Right1CompatibilityMap);
        tileCompatibilityHashMapFinder.put(cellPath2Right2Character,path2Right2CompatibilityMap);
        tileCompatibilityHashMapFinder.put(cellPath2UpCharacter,path2UpCompatibilityMap);
        tileCompatibilityHashMapFinder.put(cellPath2UpDownCharacter,path2UpDownCompatibilityMap);

        tileCompatibilityHashMapFinder.put(cellPath3Character,path3CompatibilityMap);
        tileCompatibilityHashMapFinder.put(cellPath3DownCharacter,path3DownCompatibilityMap);
        tileCompatibilityHashMapFinder.put(cellPath3LeftCharacter,path3LeftCompatibilityMap);
        tileCompatibilityHashMapFinder.put(cellPath3LeftRightCharacter,path3LeftRightCompatibilityMap);
        tileCompatibilityHashMapFinder.put(cellPath3LeftRightUpCharacter,path3LeftRightUpCompatibilityMap);
        tileCompatibilityHashMapFinder.put(cellPath3RightCharacter,path3RightCompatibilityMap);
        tileCompatibilityHashMapFinder.put(cellPath3RightDownCharacter,path3RightDownCompatibilityMap);

        tileCompatibilityHashMapFinder.put(cellPath4Character,path4CompatibilityMap);
        tileCompatibilityHashMapFinder.put(cellPath4LeftCharacter,path4LeftCompatibilityMap);
        tileCompatibilityHashMapFinder.put(cellPath4RightUpCharacter,path4RightUpCompatibilityMap);
        tileCompatibilityHashMapFinder.put(cellPath4RightUpDownCharacter,path4RightUpDownCompatibilityMap);
        tileCompatibilityHashMapFinder.put(cellPath4UpCharacter,path4UpCompatibilityMap);
        tileCompatibilityHashMapFinder.put(cellPath4UpDownCharacter,path4UpDownCompatibilityMap);

        tileCompatibilityHashMapFinder.put(cellNullCharacter,nullCompatibilityMap);

        weightMap.put(cellGrassCharacter, weightGrass);

        weightMap.put(cellPath1Character, weightPath);
        weightMap.put(cellPath1DownCharacter, weightPathWGrass);
        weightMap.put(cellPath1InvertedCharacter, weightPathWGrass);
        weightMap.put(cellPath1Left1Character, weightPathWGrass);
        weightMap.put(cellPath1Left2Character, weightPathWGrass);
        weightMap.put(cellPath1LeftDownCharacter, weightPathWGrass);
        weightMap.put(cellPath1LeftRightCharacter, weightPathWGrass);
        weightMap.put(cellPath1RightCharacter, weightPathWGrass);
        weightMap.put(cellPath1SurroundCharacter, weightPathWGrass);
        weightMap.put(cellPath1UpCharacter, weightPathWGrass);
        weightMap.put(cellPath1UpDownCharacter, weightPathWGrass);

        weightMap.put(cellPath2Character, weightPath);
        weightMap.put(cellPath2DownCharacter, weightPathWGrass);
        weightMap.put(cellPath2LeftUpCharacter, weightPathWGrass);
        weightMap.put(cellPath2LeftUpDownCharacter, weightPathWGrass);
        weightMap.put(cellPath2Right1Character, weightPathWGrass);
        weightMap.put(cellPath2Right2Character, weightPathWGrass);
        weightMap.put(cellPath2UpCharacter, weightPathWGrass);
        weightMap.put(cellPath2UpDownCharacter, weightPathWGrass);

        weightMap.put(cellPath3Character, weightPath);
        weightMap.put(cellPath3DownCharacter, weightPathWGrass);
        weightMap.put(cellPath3LeftCharacter, weightPathWGrass);
        weightMap.put(cellPath3LeftRightCharacter, weightPathWGrass);
        weightMap.put(cellPath3LeftRightUpCharacter, weightPathWGrass);
        weightMap.put(cellPath3RightCharacter, weightPathWGrass);
        weightMap.put(cellPath3RightDownCharacter, weightPathWGrass);

        weightMap.put(cellPath4Character, weightPath);
        weightMap.put(cellPath4LeftCharacter, weightPathWGrass);
        weightMap.put(cellPath4RightUpCharacter, weightPathWGrass);
        weightMap.put(cellPath4RightUpDownCharacter, weightPathWGrass);
        weightMap.put(cellPath4UpCharacter, weightPathWGrass);
        weightMap.put(cellPath4UpDownCharacter, weightPathWGrass);

        weightMap.put(cellNullCharacter, weightNull);
    }
}
