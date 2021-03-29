package sample;

public class Main {
    public static void main(String[] args) {
        ViewGenerator.launch(ViewGenerator.class, args);
        Controller controller = Controller.getInstance();
        controller.init();
    }
}
