package Model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Road {
    private ArrayList<Coordinate> coordsList = new ArrayList<>();
    private int roadWidth;
    private City start;
    private City end;

    public Road(City A, City B) {
        this.start = A;
        this.end = B;
        this.roadWidth = 10;
        coordsList.add(new Coordinate(A.getPosition().getX(), A.getPosition().getY(), null));
        coordsList.add(new Coordinate(B.getPosition().getX(), B.getPosition().getY(), null));
    }

    public void calculateCoordinates(Coordinate pointA, Coordinate pointB, int pointNum) {

        float diff_X = pointB.getX() - pointA.getX();
        float diff_Y = pointB.getY() - pointA.getY();

        float interval_X = diff_X / (pointNum + 1);
        float interval_Y = diff_Y / (pointNum + 1);


        for (int i = 1; i <= pointNum; i++)
        {
            float[] newPoint = {pointA.getX() + interval_X * i, pointA.getY() + interval_Y*i};
            // System.out.println("liste corrd point : " + newPoint[0] + "," + newPoint[1]);
            coordsList.add(new Coordinate(newPoint[0], newPoint[1], null));
        }
        System.out.println("-------");

    }

    public ArrayList<Coordinate> getCoordsList() {
        return coordsList;
    }

    public int getWidth() {
        return roadWidth;
    }
}
