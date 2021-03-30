package Model;

import View.ViewGenerator;

public class TrafficLight extends Thread {
    private boolean isRed;
    private int duration;
    private int[] position;
    private ViewGenerator viewGenerator;

    public TrafficLight(ViewGenerator viewGenerator) {
        this.viewGenerator = viewGenerator;
        this.isRed = true;
        this.duration = 15000;
        position = new int[]{150,150};
        this.start();
    }

    public void run() {
        while (true) {
            try {
                isRed = true;
                viewGenerator.updateTrafficLight(this);
                Thread.sleep(duration);
                isRed = false;
                viewGenerator.updateTrafficLight(this);
                Thread.sleep(duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    private void changeColor() {
        isRed= !isRed;
    }

    public boolean isRed() {
        return isRed;
    }

    public int getDuration() {
        return duration;
    }

    public int[] getPosition() {
        return position;
    }
}
