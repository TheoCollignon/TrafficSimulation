package sample;

import java.util.ArrayList;

public class Road {
    private ArrayList<int[]> coordsList = new ArrayList<>();
    private int roadWidth;

    public Road(int[] start, int[] end) {
        this.roadWidth = 10;
        coordsList.add(start);
        coordsList.add(end);
    }

    public void addCoordinates(int[] point) {
        coordsList.add(point);
    }

    public ArrayList<int[]> getCoordsList() {
        return coordsList;
    }
}
