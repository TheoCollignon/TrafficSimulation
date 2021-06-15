package io.sarl.template.javafx.event;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Event;
import io.sarl.template.javafx.Model.Car;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

@SarlSpecification("0.11")
@SarlElementType(15)
@SuppressWarnings("all")
public class Kill extends Event {
  public Car car;
  
  public Kill(final Car car) {
    this.car = car;
  }
  
  @Override
  @Pure
  @SyntheticMember
  public boolean equals(final Object obj) {
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    return result;
  }
  
  /**
   * Returns a String representation of the Kill event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected void toString(final ToStringBuilder builder) {
    super.toString(builder);
    builder.add("car", this.car);
  }
  
  @SyntheticMember
  private static final long serialVersionUID = -61748263L;
}
