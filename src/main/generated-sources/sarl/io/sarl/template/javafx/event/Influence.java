package io.sarl.template.javafx.event;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Event;
import java.util.Objects;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

@SarlSpecification("0.11")
@SarlElementType(15)
@SuppressWarnings("all")
public class Influence extends Event {
  public boolean move;
  
  public int i;
  
  public UUID id;
  
  public int numberOfFreeCoord;
  
  public Influence(final int i, final UUID id, final int numberOfFreeCoord) {
    this.i = i;
    this.id = id;
    this.numberOfFreeCoord = numberOfFreeCoord;
  }
  
  @Override
  @Pure
  @SyntheticMember
  public boolean equals(final Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Influence other = (Influence) obj;
    if (other.move != this.move)
      return false;
    if (other.i != this.i)
      return false;
    if (!Objects.equals(this.id, other.id))
      return false;
    if (other.numberOfFreeCoord != this.numberOfFreeCoord)
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Boolean.hashCode(this.move);
    result = prime * result + Integer.hashCode(this.i);
    result = prime * result + Objects.hashCode(this.id);
    result = prime * result + Integer.hashCode(this.numberOfFreeCoord);
    return result;
  }
  
  /**
   * Returns a String representation of the Influence event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected void toString(final ToStringBuilder builder) {
    super.toString(builder);
    builder.add("move", this.move);
    builder.add("i", this.i);
    builder.add("id", this.id);
    builder.add("numberOfFreeCoord", this.numberOfFreeCoord);
  }
  
  @SyntheticMember
  private static final long serialVersionUID = -2921440223L;
}
