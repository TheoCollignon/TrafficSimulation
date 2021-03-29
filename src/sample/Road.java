package sample;

import java.util.ArrayList;

public class Road {
    private ArrayList<int[]> coordsList = new ArrayList<>();
    private int roadWidth;

    public Road(City A, City B) {
        this.roadWidth = 10;
        coordsList.add(new int[]{A.getPosition()[0], A.getPosition()[1]});
        coordsList.add(new int[]{B.getPosition()[0], B.getPosition()[1]});
    }

    public void addCoordinates(int[] point) {
        // TO EDIT
        coordsList.add(1,point);
    }

    public ArrayList<int[]> getCoordsList() {
        return coordsList;
    }

    public int getWidth() {
        return roadWidth;
    }
}
