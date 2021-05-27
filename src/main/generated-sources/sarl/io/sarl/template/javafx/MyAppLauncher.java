package io.sarl.template.javafx;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.template.javafx.ui.MyAppFxApplication;
import javafx.application.Application;

/**
 * Launcher for the application.
 */
@SarlSpecification("0.11")
@SarlElementType(10)
@SuppressWarnings("all")
public class MyAppLauncher {
  public static void main(final String... args) {
    Application.launch(MyAppFxApplication.class);
  }
  
  @SyntheticMember
  public MyAppLauncher() {
    super();
  }
}
