package Map.tiles;

import Main.GamePanel;
import Map.Cell;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;

public class TileController {
    public TileGrid tileGrid;

    public final int gridSize = GamePanel.maxWorldSize;
    public final int tilesAcross = gridSize;
    public final int tilesDown = gridSize;
    public final int tileAmount = gridSize * gridSize;
    public boolean loadingObjects = false;
    private boolean allowUpdate = false;
    private int loadingBarWidth = 0;
    public int prevLoadingBarWidth = 0;
    int x,y;

    ArrayList<String> cellChoice = new ArrayList<>();

    // Top Right Bottom Left


    public ArrayList<int[]> options = new ArrayList<>(Arrays.asList());

    ArrayList<Integer> similarAdjacentTileRequirements = new ArrayList<>(Arrays.asList());

    HashMap<Integer, HashMap<Integer, Boolean>> optionCompatibilityMap = new HashMap<>();
    HashMap<Integer, Boolean> optionCompatibilityMapKey0 = new HashMap<>();
    HashMap<Integer, Boolean> optionCompatibilityMapKey1 = new HashMap<>();
    HashMap<Integer, Boolean> optionCompatibilityMapKey2 = new HashMap<>();
    public HashMap<int[], Integer> tileOptionMap = new HashMap<>();

    TilesWeights weight  = new TilesWeights(options);

    public void initialize() {
        optionCompatibilityMap.put(0, optionCompatibilityMapKey0);
        optionCompatibilityMap.put(1, optionCompatibilityMapKey1);
        optionCompatibilityMap.put(2, optionCompatibilityMapKey2);

        optionCompatibilityMapKey0.put(0, true);
        optionCompatibilityMapKey0.put(1, false);
        optionCompatibilityMapKey0.put(2, false);

        optionCompatibilityMapKey1.put(0, false);
        optionCompatibilityMapKey1.put(1, true);
        optionCompatibilityMapKey1.put(2, true);

        optionCompatibilityMapKey2.put(0, false);
        optionCompatibilityMapKey2.put(1, true);
        optionCompatibilityMapKey2.put(2, false);

       for (int i = 0; i < options.size(); i++) {
           tileOptionMap.put(options.get(i), i);
       }
        weight.initialize();
        ArrayList<Cell> finalTileGrid = setupFile();
        loadingObjects = true;
        objectWFCC.initialize(finalTileGrid, options, tileOptionMap);
        updateFile();
    }

    private ArrayList<Cell> setupFile(){
        try{
            File directory = new File ("src/Assets/Maps");
            File newMap = new File(directory, "tileMap.txt");
            newMap.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        tileGrid = new TileGrid(tilesAcross, tilesDown, options, tileOptionMap, optionCompatibilityMap);
        waveFunctionCollapse();
        cleanUp();
        return tileGrid.getGrid();
    }

    private void updateFile() {
        for (int i = 0; i < tileAmount; i++) {

            ArrayList<int[]> cellOptions = tileGrid.getGrid().get(i).getOptions();

            if (cellOptions.size() == 1) {
                cellChoice.add(i, String.valueOf(tileOptionMap.get(cellOptions.get(0))));
            }
        }
        try {
            FileWriter mapWriter = new FileWriter("src/Assets/Maps/tileMap.txt");

            for (int i = 0; i < tileAmount; i++){
                if (i % gridSize != 0 || i == 0) {
                    mapWriter.write(cellChoice.get(i) + " ");
                } else {
                    mapWriter.write("\n" + cellChoice.get(i) + " ");
                }
            }
            mapWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void waveFunctionCollapse(){
        boolean keepGoing = true;
        while (keepGoing){
            keepGoing = tileGrid.collapse();
            allowUpdate = true;
        }
    }

    public void draw (Graphics g2) {
        if (tileGrid != null) {
            g2.drawRect(175, 384, 700, 50);
            g2.fillRect(180, 389, loadingBarWidth, 40);
            if (!loadingObjects && allowUpdate) {
                x = (int) (345 * ((tileGrid.tilesCollapsed / tileGrid.tilePercentDivider) / 100.0));
                if (prevLoadingBarWidth <= x) {
                    loadingBarWidth = x;
                    allowUpdate = false;
                }
            } else if (objectWFCC.grid != null) {
                y = 345 + (int) (345 * ((objectWFCC.grid.tilesCollapsed / objectWFCC.grid.tilePercentDivider) / 100.0));
                if (prevLoadingBarWidth <= y) {
                    loadingBarWidth = y;
                    allowUpdate = false;
                }
            }
            prevLoadingBarWidth = loadingBarWidth;
        }
    }

    private void cleanUp(){
        boolean keepGoing = true;
        while (keepGoing){
            keepGoing = tileGrid.cleanUp(similarAdjacentTileRequirements);
            waveFunctionCollapse();
        }
    }

}

