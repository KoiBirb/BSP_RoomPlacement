package Map.tiles;

import Map.Cell;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;

public class TileGrid {

    private final int width;
    private final int height;
    public final double tilePercentDivider;
    public int tilesCollapsed = 0;
    private final ArrayList<Cell> grid = new ArrayList<>();
    private final ArrayList<int[]> options;
    HashMap<Integer, HashMap<Integer, Boolean>> optionCompatibilityMap;
    HashMap<int[], Integer> optionMap;
    HashMap<Integer, int[]> weightMap = TilesWeights.weightMap;
    HashMap<Integer, HashMap<int[], Boolean>> tileCompatibilityHashMapFinder = TilesWeights.tileCompatibilityHashMapFinder;

    public TileGrid(int width, int height, ArrayList<int[]>options, HashMap<int[], Integer> optionMap, HashMap<Integer, HashMap<Integer, Boolean>> optionCompatibilityMap) {
        this.width = width;
        this.height = height;
        this.options = options;
        this.optionMap = optionMap;
        this.optionCompatibilityMap = optionCompatibilityMap;
        this.tilePercentDivider = (width * height) / 100.0;

        setupGrid();
    }
    private void setupGrid() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                grid.add(new Cell(options, optionMap));
            }
        }
    }

    private Cell getCell() {
        ArrayList<Cell> mapOrdered = (ArrayList<Cell>) grid.clone();
        mapOrdered.removeIf(Cell::isCollapsed);
        if (mapOrdered.isEmpty()) {
            return null;
        }
        mapOrdered.sort(Cell::compareTo);
        Cell leastEntropyCell = mapOrdered.get(0);
        mapOrdered.removeIf(cell -> cell.entropy() != leastEntropyCell.entropy());
        return mapOrdered.get(new SecureRandom().nextInt(mapOrdered.size()));
    }

    public boolean collapse() {
        ArrayList<Integer> cellsToCheck = new ArrayList<>();
        ArrayList<Integer> cellsChecked = new ArrayList<>();
        Cell cell = getCell();
        int collapsedCellIndex = grid.indexOf(cell);

        int centerIndexUP = collapsedCellIndex - width;
        if (centerIndexUP >= 0) {
            cellsToCheck.add(centerIndexUP);
        }

        int centerIndexDOWN = collapsedCellIndex + width;
        if (centerIndexDOWN < grid.size()) {
            cellsToCheck.add(centerIndexDOWN);
        }

        int centerIndexLEFT = collapsedCellIndex - 1;
        if (collapsedCellIndex % width != 0) {
            cellsToCheck.add(centerIndexLEFT);
        }

        int centerIndexRIGHT = collapsedCellIndex + 1;
        if ((collapsedCellIndex + 1) % width != 0) {
            cellsToCheck.add(centerIndexRIGHT);
        }

        if (cell != null) {
            if (!cell.collapse()) {
                resetAdjacentTiles(cell);
            }
            tilesCollapsed = 0;
            for (int i = 0; i < (width * height); i++) {
                if (grid.get(i).isCollapsed()) {
                    tilesCollapsed++;
                }
            }
        } else {
            return false;
        }

        while (cellsToCheck.size() != 0) {
            for (int i = 0; i < cellsToCheck.size(); i++) {
                int index = cellsToCheck.get(i);
                Cell currentCell = grid.get(index);
                if (!currentCell.isCollapsed()) {
                    ArrayList<Integer> adjacentTiles =  findAdjacentTiles(index);
                    ArrayList<int[]> cumulativeValidOptions = currentCell.getOptions();
                    ArrayList<int[]> originalOptions = currentCell.getOptions();

                    int indexUP = index - width;
                    if (indexUP >= 0) {
                        grid.get(indexUP).getOptions();
                        if (adjacentTiles.get(0) != 999) {
                            cumulativeValidOptions =
                                    findAllowedOptions(cumulativeValidOptions, grid.get(indexUP).getOptions(), 0, adjacentTiles.get(0));
                        }
                    }

                    int indexDOWN = index + width;
                    if (indexDOWN < grid.size()) {
                        if (adjacentTiles.get(1) != 999) {
                            cumulativeValidOptions =
                                    findAllowedOptions(cumulativeValidOptions, grid.get(indexDOWN).getOptions(), 2, adjacentTiles.get(1));
                        }
                    }

                    int indexLEFT = index - 1;
                    if (index % width != 0) {
                        if (adjacentTiles.get(2) != 999) {
                            cumulativeValidOptions =
                                    findAllowedOptions(cumulativeValidOptions, grid.get(indexLEFT).getOptions(), 3, adjacentTiles.get(2));
                        }
                    }
                    int indexRIGHT = index + 1;
                    if ((index + 1) % width != 0) {
                        if (adjacentTiles.get(3) != 999) {
                            cumulativeValidOptions =
                                    findAllowedOptions(cumulativeValidOptions, grid.get(indexRIGHT).getOptions(), 1, adjacentTiles.get(3));
                        }
                    }
                    currentCell.setOptions(cumulativeValidOptions);
                    if (!checkArrayList(originalOptions, cumulativeValidOptions)){

                        if (indexUP >= 0) {
                            if (!(cellsToCheck.contains(indexUP) && cellsChecked.contains(indexUP))) {
                                cellsToCheck.add(indexUP);
                            }
                        }

                        if (indexDOWN < grid.size()) {
                            if (!(cellsToCheck.contains(indexDOWN) && cellsChecked.contains(indexDOWN))) {
                                cellsToCheck.add(indexDOWN);
                            }
                        }

                        if (index % width != 0) {
                            if (!(cellsToCheck.contains(indexLEFT) && cellsChecked.contains(indexLEFT))) {
                                cellsToCheck.add(indexLEFT);
                            }
                        }

                        if ((index + 1) % width != 0) {
                            if (!(cellsToCheck.contains(indexRIGHT) && cellsChecked.contains(indexRIGHT))) {
                                cellsToCheck.add(indexRIGHT);
                            }
                        }
                    } else {
                        cellsToCheck.remove((Integer) index);
                        cellsChecked.add(index);
                        cellsToCheck.remove((Integer) collapsedCellIndex);
                        cellsChecked.add(collapsedCellIndex);
                    }

                    int[] weightTileLeft = weightMap.get(adjacentTiles.get(2));
                    int[] weightTileRight = weightMap.get(adjacentTiles.get(3));
                    int[] weightTileBottom = weightMap.get(adjacentTiles.get(1));
                    int[] weightTileTop = weightMap.get(adjacentTiles.get(0));

                    int[] newWeightTile = new int[33];

                    for (int y = 0; y < newWeightTile.length; y++) {
                        newWeightTile[y] = weightTileLeft[y] * weightTileRight[y] * weightTileBottom[y] * weightTileTop[y];
                    }
                    currentCell.setWeight(newWeightTile);
                } else {
                    cellsToCheck.remove((Integer) index);
                    cellsChecked.add(index);
                }
            }
        }
        return true;
    }

    protected ArrayList<int[]> findAllowedOptions(ArrayList<int[]> cumulativeValidOptions, ArrayList<int[]> comparativeCellOptions, int position, int tile) {
        ArrayList<int[]> newCumulativeValidOptions = new ArrayList<>();
        HashMap tileMap = tileCompatibilityHashMapFinder.get(tile);
        cumulativeValidOptions.forEach(option -> {
            if (tileMap.get(option) == Boolean.TRUE) {
                for (int[] comparativeCellOption : comparativeCellOptions) {
                    HashMap<Integer, Boolean> correctOptionCompatibilityMap = optionCompatibilityMap.get(option[position]);
                    if (correctOptionCompatibilityMap.get(comparativeCellOption[(position + 2) % 4]) == Boolean.TRUE) {
                        newCumulativeValidOptions.add(option);
                        break;
                    }
                }
            }
        });
        return newCumulativeValidOptions;
    }

    public ArrayList<Integer> findAdjacentTiles (int index){
        ArrayList<Integer> adjacentTiles = new ArrayList<>();

        int indexUP = index - width;
        try {
            if (grid.get(indexUP).isCollapsed()) {
                adjacentTiles.add(0, optionMap.get(grid.get(indexUP).getOptions().get(0)));
            } else {
                adjacentTiles.add(0, 999);
            }
        } catch (IndexOutOfBoundsException e) {
            adjacentTiles.add(0, 999);
        }

        int indexDOWN = index + width;
        try {
            if (grid.get(indexDOWN).isCollapsed()) {
                adjacentTiles.add(1, optionMap.get(grid.get(indexDOWN).getOptions().get(0)));
            } else {
                adjacentTiles.add(1, 999);
            }
        } catch (IndexOutOfBoundsException e) {
            adjacentTiles.add(1, 999);
        }

        int indexLEFT = index - 1;
        try {
            if (grid.get(indexLEFT).isCollapsed()) {
                adjacentTiles.add(2, optionMap.get(grid.get(indexLEFT).getOptions().get(0)));
            } else {
                adjacentTiles.add(2, 999);
            }
        } catch (IndexOutOfBoundsException e) {
            adjacentTiles.add(2, 999);
        }

        int indexRIGHT = index + 1;
        try {
            if (grid.get(indexRIGHT).isCollapsed()) {
                adjacentTiles.add(3, optionMap.get(grid.get(indexRIGHT).getOptions().get(0)));
            } else {
                adjacentTiles.add(3, 999);
            }
        } catch (IndexOutOfBoundsException e) {
            adjacentTiles.add(3, 999);
        }
        return adjacentTiles;
    }

    public boolean checkArrayList (ArrayList<int[]> originalList, ArrayList<int[]> modifiedList){
        if (originalList.size() == modifiedList.size()){
            int correctOptions = 0;
            for (int i = 0; i < originalList.size(); i++){
                if (modifiedList.contains(originalList.get(i))){
                    correctOptions++;
                }
            }
            if (correctOptions == originalList.size()){
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean cleanUp(ArrayList<Integer> similarAdjacentTileRequirements){
        boolean tilesReset = false;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                int index = y * width + x;
                Cell currentCell = grid.get(index);

                if (currentCell.isCollapsed()) {

                    int[] tileOption = currentCell.getOptions().get(0);
                    int tileType = optionMap.get(tileOption);
                    int similarAdjacentTileRequirement = similarAdjacentTileRequirements.get(tileType);
                    int similarTileTypeOccurrences = 0;

                    ArrayList<Integer> adjacentTiles = new ArrayList<>();

                    int indexUP = index - width;
                    try {
                        adjacentTiles.add(0, optionMap.get(grid.get(indexUP).getOptions().get(0)));
                    } catch (IndexOutOfBoundsException e) {
                        adjacentTiles.add(0, 999);
                    }

                    int indexDOWN = index + width;
                    try {
                        adjacentTiles.add(1, optionMap.get(grid.get(indexDOWN).getOptions().get(0)));
                    } catch (IndexOutOfBoundsException e) {
                        adjacentTiles.add(1, 999);
                    }

                    int indexLEFT = index - 1;
                    try {
                        adjacentTiles.add(2, optionMap.get(grid.get(indexLEFT).getOptions().get(0)));
                    } catch (IndexOutOfBoundsException e) {
                        adjacentTiles.add(2, 999);
                    }

                    int indexRIGHT = index + 1;
                    try {
                        adjacentTiles.add(3, optionMap.get(grid.get(indexRIGHT).getOptions().get(0)));
                    } catch (IndexOutOfBoundsException e) {
                        adjacentTiles.add(3, 999);
                    }

                    for (int i = 0; i < adjacentTiles.size(); i++) {
                        if (adjacentTiles.get(i) == tileType) {
                            similarTileTypeOccurrences++;
                        }
                    }
                    if (similarTileTypeOccurrences < similarAdjacentTileRequirement) {
                        resetAdjacentTiles(currentCell);
                        tilesReset = true;
                    }
                }
            }
        }
        return tilesReset;
    }

    public void resetAdjacentTiles(Cell cell){

        int index = grid.indexOf(cell);
        resetCell(index);
        try {
            int indexUP = index - width;
            resetCell(indexUP);
        } catch (IndexOutOfBoundsException e){}
        try {
            int indexDOWN = index + width;
            resetCell(indexDOWN);
        } catch (IndexOutOfBoundsException e){}
        try {
            int indexLEFT = index - 1;
            resetCell(indexLEFT);
        } catch (IndexOutOfBoundsException e){}
        try {
            int indexRIGHT = index + 1;
            resetCell(indexRIGHT);
        } catch (IndexOutOfBoundsException e){}
    }

    public void resetCell (int index) {
        Cell currentCell = grid.get(index);
        currentCell.setOptions(options);
        currentCell.setState();
        currentCell.setWeight(new int[]{1});
    }

    public ArrayList<Cell> getGrid() {
        return grid;
    }

}