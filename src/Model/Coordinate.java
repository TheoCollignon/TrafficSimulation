package Model;

public class Coordinate {
    private float x;
    private float y;
    private Car car;
    private City city;

    public Coordinate(float x, float y) {
        this.x = x;
        this.y = y;
        this.car = null;
    }

    public City getCity() {
        if(city != null){
            return city;
        } else {
            return null;
        }
    }

    public void setCity(City city) {
        this.city = city;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Car getCar() {
        return car;
    }

    public boolean isCar() { return car != null; }

    public void setCar(Car car) {
        this.car = car;
    }

    //Compares ONLY the coordinates
    public boolean equals(Coordinate coordToCompare){
        return (this.x == coordToCompare.getX() && this.y == coordToCompare.getY());
    }
}
