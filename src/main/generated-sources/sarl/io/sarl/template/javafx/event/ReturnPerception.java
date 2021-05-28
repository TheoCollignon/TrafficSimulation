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
public class ReturnPerception extends Event {
  public int i;
  
  public UUID id;
  
  public ReturnPerception(final int i, final UUID id) {
    this.i = i;
    this.id = id;
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
    ReturnPerception other = (ReturnPerception) obj;
    if (other.i != this.i)
      return false;
    if (!Objects.equals(this.id, other.id))
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Integer.hashCode(this.i);
    result = prime * result + Objects.hashCode(this.id);
    return result;
  }
  
  /**
   * Returns a String representation of the ReturnPerception event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected void toString(final ToStringBuilder builder) {
    super.toString(builder);
    builder.add("i", this.i);
    builder.add("id", this.id);
  }
  
  @SyntheticMember
  private static final long serialVersionUID = -1491898299L;
}
