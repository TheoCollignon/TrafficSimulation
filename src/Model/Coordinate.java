package Model;

public class Coordinate {
    private float x;
    private float y;
    private Car car;

    public Coordinate(float x, float y, Car car) {
        this.x = x;
        this.y = y;
        this.car = car;
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
}
