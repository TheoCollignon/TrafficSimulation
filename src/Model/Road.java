package Model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Road {
    private ArrayList<float[]> coordsList = new ArrayList<>();
    private int roadWidth;

    public Road(City A, City B) {
        this.roadWidth = 10;
        coordsList.add(new float[]{A.getPosition()[0], A.getPosition()[1]});
        coordsList.add(new float[]{B.getPosition()[0], B.getPosition()[1]});
    }

    public void calculateCoordinates(float[] pointA, float[] pointB, int pointNum) {

        float diff_X = pointB[0] - pointA[0];
        float diff_Y = pointB[1] - pointA[1];

        float interval_X = diff_X / (pointNum + 1);
        float interval_Y = diff_Y / (pointNum + 1);


        for (int i = 1; i <= pointNum; i++)
        {
            float[] newPoint = {pointA[0] + interval_X * i, pointA[1] + interval_Y*i};
            // System.out.println("liste corrd point : " + newPoint[0] + "," + newPoint[1]);
            coordsList.add(newPoint);
        }
        System.out.println("-------");

    }

    public ArrayList<float[]> getCoordsList() {
        return coordsList;
    }

    public int getWidth() {
        return roadWidth;
    }
}
