package Model;

import java.util.ArrayList;

public class Road {
    private ArrayList<Coordinate> coordsList = new ArrayList<>();
    private int roadWidth;
    private Coordinate coordStart;
    private Coordinate coordEnd;
    private City start;
    private City end;

    public Road(City A, City B) {
        this.start = A;
        this.end = B;
        this.roadWidth = 10;
        coordStart = A.getPosition();
        coordEnd = B.getPosition();
        coordsList.add(new Coordinate(A.getPosition().getX(), A.getPosition().getY()));
        coordsList.add(new Coordinate(B.getPosition().getX(), B.getPosition().getY()));
    }

    // creating a road that doesn't necessarily join two cities (meant to replace the first constructor in the future)
    public Road(Coordinate A, Coordinate B){
        this.roadWidth = 10;
        this.coordStart = A;
        this.coordEnd = B;
        coordsList.add(A);
        coordsList.add(B);
    }

    public void calculateCoordinates(Coordinate pointA, Coordinate pointB, int pointNum) {

        coordsList.clear();
        coordsList.add(pointA);
        float diff_X = pointB.getX() - pointA.getX();
        float diff_Y = pointB.getY() - pointA.getY();

        float interval_X = diff_X / (pointNum + 1);
        float interval_Y = diff_Y / (pointNum + 1);

        for (int i = 1; i <= pointNum; i++)
        {
            float[] newPoint = {pointA.getX() + interval_X * i, pointA.getY() + interval_Y*i};
            coordsList.add(new Coordinate(newPoint[0], newPoint[1]));
        }
        coordsList.add(pointB);

    }

    public boolean moveCarPosition(Car car){
        for(Coordinate coord : coordsList) {

            if (coord.getCar() == (car)) {
                // verify if there is a next coordinate
                // the car is rolling to its destination
                if (coordsList.indexOf(coord) < coordsList.size() - 1) {
                    // get the next element
                    car.changeCarPosition(coordsList.get(coordsList.indexOf(coord) + 1));
                    return true;
                } else {
                    // re-route later
                    Road nextRoad;
                    City currentCity;
                    // if the car is at the end of the road, we must re-route it
                    if (coord.getCar().getPosition() == this.end.getPosition()) {
                        currentCity = this.end;
                        // we are in the end city, so we watch every connected road, and we redirect to a random one
                        int r = (int) (Math.random() * currentCity.getConnectedCities().size()) ;
                        City randomCity = currentCity.getConnectedCities().get(r);
                        // get road from where I'm to where I go
                        nextRoad = currentCity.getConnectedRoad().get(currentCity.getConnectedCities().indexOf(randomCity));
                    } else {
                        currentCity = this.start;
                        // else we are in the start city, so we watch every connected road, and we redirect to a random one
                        int r = (int) (Math.random() * currentCity.getConnectedCities().size()) ;
                        City randomCity = currentCity.getConnectedCities().get(r);
                        nextRoad = currentCity.getConnectedRoad().get(currentCity.getConnectedCities().indexOf(randomCity));
                    }
                    car.changeRoad(nextRoad);
                    return true;

                }
            }
        }
        System.out.println("Not working, probably nahtan's fault");
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
}
