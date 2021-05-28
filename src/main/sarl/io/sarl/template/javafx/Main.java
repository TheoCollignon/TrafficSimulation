package io.sarl.template.javafx;

import io.sarl.bootstrap.SRE;
import io.sarl.bootstrap.SREBootstrap;
import io.sarl.template.javafx.Controller.Controller;
import io.sarl.template.javafx.View.ViewMenu;
import io.sarl.template.javafx.agents.Environment;

public class Main {
    public static void main(String[] args) {
    	
    	// Implementing SARL : 
    	SREBootstrap bootstrap = SRE.getBootstrap();
        Controller controller = Controller.getInstance();
        // calling agent
        try {
			bootstrap.startAgent(Environment.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        // View
        ViewMenu.launch(ViewMenu.class, args);
        
    }
}
