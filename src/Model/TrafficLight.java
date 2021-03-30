package Model;

public class TrafficLight extends Thread {
    private boolean isRed;
    private int duration;

    public TrafficLight() {
        this.isRed = true;
        this.duration = 15000;
    }

    public void run() {
        while (true) {
            try {
                isRed = true;
                System.out.println("Feu rouge");
                Thread.sleep(duration);
                isRed = false;
                System.out.println("Feu vert");
                Thread.sleep(duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
