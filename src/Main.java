import Controller.Controller;
import View.ViewGenerator;
import View.ViewMenu;

public class Main {
    public static void main(String[] args) {
        Controller controller = Controller.getInstance();
        ViewMenu.launch(ViewMenu.class, args);
        //ViewGenerator.launch(ViewGenerator.class, args);
    }
}
