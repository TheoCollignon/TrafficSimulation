import Controller.Controller;
import View.ViewGenerator;

public class Main {
    public static void main(String[] args) {
        Controller controller = Controller.getInstance();
        ViewGenerator.launch(ViewGenerator.class, args);
    }
}
