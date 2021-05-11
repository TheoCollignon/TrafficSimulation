package Model;

import java.util.ArrayList;
import java.util.Random;

public class Road {
    private ArrayList<Coordinate> coordsList = new ArrayList<>();
    private int roadWidth;
    private int pointNum;
    Crossroad crossroadStart;
    Crossroad crossroadEnd;
    private Coordinate coordStart;
    private Coordinate coordEnd;
    private City start;
    private City end;

    public Road(City A, City B) {
        this.start = A;
        this.end = B;
        this.roadWidth = 15;
        this.pointNum = -1;
        coordStart = A.getPosition();
        coordEnd = B.getPosition();
        coordsList.add(coordStart);
        coordsList.add(coordEnd);
    }

    // creating a road that doesn't necessarily join two cities (meant to replace the first constructor in the future)
    public Road(Crossroad A, Crossroad B){
        this.roadWidth = 15;
        coordsList.add(A.getCoords());
        coordsList.add(B.getCoords());
    }

    public void calculateCoordinates(Coordinate pointA, Coordinate pointB) {

        // We need to know how many point there is between two city, the longer the road is -> more point
        double distance = Math.sqrt(Math.pow(pointB.getX() - pointA.getX(),2) + Math.pow(pointB.getY() - pointA.getY(),2));
        this.pointNum = (int)(distance / 10);
        // we clear once
        coordsList.clear();
        // we had the first city
        coordsList.add(pointA);
        Random random = new Random();


        float diff_X = pointB.getX() - pointA.getX();
        float diff_Y = pointB.getY() - pointA.getY();

        float interval_X = diff_X / (this.pointNum + 1);
        float interval_Y = diff_Y / (this.pointNum + 1);

        float randomValue = (float)random.nextInt(5);
        int isUpDown = random.nextInt(2);
        if (isUpDown == 1) randomValue = -randomValue;
        for (int i = 1; i <= this.pointNum; i++)
        {
            int angle = i;
            if (i > (this.pointNum /2)) angle = (this.pointNum - i);
            float[] newPoint = {pointA.getX() + interval_X * i +   randomValue * (angle-1)  , pointA.getY() + interval_Y*i + randomValue * (angle-1)};
            coordsList.add(new Coordinate(newPoint[0], newPoint[1]));
        }

        // add back the second town
        coordsList.add(pointB);

    }

    // fromcity is the city where the car come from, not the destination
    public boolean moveCarPosition(Car car, City fromCity){
        for(Coordinate coord : coordsList) {
            if (coord.getCar().contains(car)) {
                // verify if there is a next coordinate
                // the car is rolling to its destination
                if ((coordsList.indexOf(coord) < coordsList.size() - 1) && fromCity == start) {
                    // get the next element
                    car.changeCarPosition(coordsList.get(coordsList.indexOf(coord) + 1));
                    return true;
                } else if (((coordsList.indexOf(coord)) > 0) && fromCity == end) {
                    // get the previous element
                    car.changeCarPosition(coordsList.get(coordsList.indexOf(coord) - 1));
                    return true;
                }
                else {
                    // re-route
                    Road nextRoad;
                    City currentCity = null;
                    // if the car is at the end of the road, we must re-route it
                    if (car.getPosition() == this.end.getPosition()) {
                        currentCity = this.end;
                    } else if(car.getPosition() == this.start.getPosition()) {
                        // else we are in the start city, so we watch every connected road, and we redirect to a random one
                        currentCity = this.start;
                    } else {
                        System.out.println("not on a city, which should result an error");
                    }
                    // we are in the end city, so we watch every connected road, and we redirect to a random one
                    int r = (int) (Math.random() * currentCity.getConnectedCities().size()) ;
                    City randomCity = currentCity.getConnectedCities().get(r);
                    // get road from where I'm to where I go
                    nextRoad = currentCity.getConnectedRoad().get(currentCity.getConnectedCities().indexOf(randomCity));
                    car.changeRoad(nextRoad, currentCity);
                    return true;
                }
            }

        }
        // no car has been found on the road
        return false;
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

    public Coordinate getCoordStart() {
        return coordStart;
    }

    public Coordinate getCoordEnd() {
        return coordEnd;
    }

    public int getPointNum() {
        return pointNum;
    }

    public void setPointNum(int pointNum) {
        this.pointNum = pointNum;
    }

    public void setCoordStart(Coordinate coordStart) {
        this.coordStart = coordStart;
    }

    public void setCoordEnd(Coordinate coordEnd) {
        this.coordEnd = coordEnd;
    }

    public Crossroad getCrossroadStart() {
        return crossroadStart;
    }

    public void setCrossroadStart(Crossroad crossroadStart) {
        this.crossroadStart = crossroadStart;
    }

    public Crossroad getCrossroadEnd() {
        return crossroadEnd;
    }

    public void setCrossroadEnd(Crossroad crossroadEnd) {
        this.crossroadEnd = crossroadEnd;
    }
}
