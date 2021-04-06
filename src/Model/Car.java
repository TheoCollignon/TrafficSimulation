package Model;

public class Car {
    private int[] position;
    private int enegy;

    public Car(int[] position, int enegy) {
        this.position = position;
        this.enegy = enegy;
    }

    public int getEnegy() {
        return enegy;
    }

    public void setEnegy(int enegy) {
        this.enegy = enegy;
    }

    public int[] getPosition() {
        return position;
    }

    public void setPosition(int[] position) {
        this.position = position;
    }
}
