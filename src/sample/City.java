package sample;

public class City {
    private int[] position;
    private int size;
    private String name;

    public City(int[] position, int size, String name) {
        this.position = position;
        this.size = size;
        this.name = name;
    }

    public int[] getPosition() {
        return position;
    }

    public int getSize() {
        return size;
    }

    public String getName() {
        return name;
    }
}
