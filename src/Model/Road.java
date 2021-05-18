package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Road {
    private ArrayList<Coordinate> coordsList = new ArrayList<>();
    private int roadWidth;
    private int pointNum;
    private int roadLength;
    Crossroad crossroadStart;
    Crossroad crossroadEnd;
    private Coordinate coordStart;
    private Coordinate coordEnd;
    private City start;
    private City end;

    public Road(City A, City B) {
        this.roadLength = 0;
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
        boolean isStartACity = false;
        boolean isEndACity = false;
        crossroadStart = A;
        crossroadEnd = B;
        //if the starting or ending crossroad is a city, we keep track of that
        if(A.isCity){
            this.start = A.getCity();
            isStartACity = true;
        }
        if(B.isCity){
            this.end = B.getCity();
            isEndACity = true;
        }
        //adding the connection between cities
        //adding the connected road only there because of current car behavior
        if(isStartACity && isEndACity){
            this.start.addConnectedCity(this.end);
            this.end.addConnectedCity(this.start);
        }
        this.roadLength = 0;
        this.roadWidth = 15;
        this.pointNum = -1;
        coordStart = A.getCoords();
        coordEnd = B.getCoords();
        coordsList.add(A.getCoords());
        coordsList.add(B.getCoords());
    }

    public void calculateCoordinates(Coordinate pointA, Coordinate pointB, boolean isHippodamian) {

        // We need to know how many point there is between two city, the longer the road is -> more point
        double distance = Math.sqrt(Math.pow(pointB.getX() - pointA.getX(),2) + Math.pow(pointB.getY() - pointA.getY(),2));
        this.roadLength += distance;
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

        float randomValue = (float)random.nextInt(2);

        if(isHippodamian){
            randomValue = 0;
        }

        // to know if we want the arc to go up or down
        int isUpDown = random.nextInt(2);
        if (isUpDown == 1) randomValue = -randomValue;

        Coordinate pointC = new Coordinate((pointA.getX() + pointB.getX())/2, (pointA.getY() + pointB.getY())/2 + randomValue );

        for (int i = 1; i <= this.pointNum; i++)
        {
            // float[] newPoint = {pointA.getX() + interval_X * i   , pointA.getY() + interval_Y*i };
            float x = pointA.getX() + interval_X * i;
            // float y =  pointA.getY() + interval_Y*i;
            float y;
            if(isHippodamian){
                y = pointA.getY() + interval_Y * i;
            } else{
                y = calculateNewton(pointA, pointB, pointC, x);
            }

            float[] newPoint = { x  , y };
            coordsList.add(new Coordinate(newPoint[0], newPoint[1]));
        }

        // add back the second town
        coordsList.add(pointB);

    }

    public float calculateNewton(Coordinate a, Coordinate b, Coordinate c, float X) {
        float xA = a.getX();
        float xB = b.getX();
        float xC = c.getX();
        float yA = a.getY();
        float yB = b.getY();
        float yC = c.getY();
        float a0 = yA;
        float a1 = (yB - yA) / (xB - xA);
        float subA1 = (yC - yB) / (xC - xB);
        float a2 = a1 - subA1;
        // p(x) = a0 + a1 * (X - xA) + a2(X - xB)
        return a0*1 + a1 * (X - xA) + a2*(X-xA) * (X - xB);
    }

    // fromcity is the city where the car comes from, not the destination
    public boolean moveCarPosition(Car car){
        for(Coordinate coord : coordsList) {
            if (coord.getCar().contains(car)) {
                // verify if there is a next coordinate
                if ((coordsList.indexOf(coord) < coordsList.size() - 1) && car.getDirection() == 1) {
                    // get the next element
                    car.changeCarPosition(coordsList.get(coordsList.indexOf(coord) + 1));
                    return true;
                } else if (((coordsList.indexOf(coord)) > 0) && car.getDirection() == -1) {
                    // get the previous element
                    car.changeCarPosition(coordsList.get(coordsList.indexOf(coord) - 1));
                    return true;
                }
                else {
                    // re-route
                    Crossroad currentCrossroad = null;
                    // if the car is at the end of the road, we must re-route it
                    if (car.getPosition() == this.crossroadEnd.getCoords()){
                        currentCrossroad = this.crossroadEnd;
                    }else if( car.getPosition() == this.crossroadStart.getCoords()) {
                        currentCrossroad = this.crossroadStart;
                    }
                    if(currentCrossroad.isCity && currentCrossroad.getCity().equals(car.getDestination())){
                        //generating a new road plan according to a random destination
                        car.getNewRoadPlan(currentCrossroad.getCity());
                        Road firstRoad = car.getRoadPlan().get(0);
                        car.changeRoad(firstRoad, currentCrossroad, true);
                    }else{
                        Road nextRoad = car.getNextRoad(this);
                        car.changeRoad(nextRoad, currentCrossroad,false);
                    }

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

    public int getRoadLength() {
        return roadLength;
    }

    public void setRoadLength(int roadLength) {
        this.roadLength = roadLength;
    }
}
