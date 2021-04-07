package Model;

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

    public void moveCarPosition(Car car){
        for(Coordinate coord : coordsList) {
//            if(coord.getCar() != null) System.out.println("tro for");
//            if(coord.getCar() != (car)) System.out.println("tro foooooooooooooooor");

            if (coord.getCar() == (car)) {
                // verify if there is a next coordinate
                if (coordsList.indexOf(coordsList.get(coordsList.indexOf(coord)  )) < coordsList.size()-1) {
                    coord.getCar().changeCarPosition(coordsList.get(coordsList.indexOf(coord) + 1));
                    System.out.println("moving car : " + coord.getCar());
                } else {
                    // re-route later
                    System.out.println("Not working, probably nahtan's fault");
                }
            }
        }
    }

    public ArrayList<Coordinate> getCoordsList() {
        return coordsList;
    }

    public int getWidth() {
        return roadWidth;
    }

    public void setCoordsList(ArrayList<Coordinate> coordsList) {
        this.coordsList = coordsList;
    }

    public int getRoadWidth() {
        return roadWidth;
    }

    public void setRoadWidth(int roadWidth) {
        this.roadWidth = roadWidth;
    }

    public City getStart() {
        return start;
    }

    public void setStart(City start) {
        this.start = start;
    }

    public City getEnd() {
        return end;
    }

    public void setEnd(City end) {
        this.end = end;
    }
}
