package io.sarl.template.javafx;

import io.sarl.template.javafx.Controller.Controller;
import io.sarl.template.javafx.View.ViewMenu;

public class Main {
    public static void main(String[] args) {
        Controller controller = Controller.getInstance();
        ViewMenu.launch(ViewMenu.class, args);
    }
}
