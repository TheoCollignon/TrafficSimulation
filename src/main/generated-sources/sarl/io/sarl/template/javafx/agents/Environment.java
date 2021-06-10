package io.sarl.template.javafx.agents;

import com.google.common.base.Objects;
import io.sarl.core.DefaultContextInteractions;
import io.sarl.core.Initialize;
import io.sarl.core.Lifecycle;
import io.sarl.core.Logging;
import io.sarl.core.Schedules;
import io.sarl.lang.annotation.ImportedCapacityFeature;
import io.sarl.lang.annotation.PerceptGuardEvaluator;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Address;
import io.sarl.lang.core.Agent;
import io.sarl.lang.core.AtomicSkillReference;
import io.sarl.lang.core.BuiltinCapacitiesProvider;
import io.sarl.lang.core.DynamicSkillProvider;
import io.sarl.lang.core.Scope;
import io.sarl.lang.util.SerializableProxy;
import io.sarl.template.javafx.Controller.Controller;
import io.sarl.template.javafx.Model.Car;
import io.sarl.template.javafx.Model.Configuration;
import io.sarl.template.javafx.Model.Road;
import io.sarl.template.javafx.agents.CarAgent;
import io.sarl.template.javafx.event.Influence;
import io.sarl.template.javafx.event.Perception;
import io.sarl.template.javafx.event.SetupApplication;
import java.io.ObjectStreamException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import javax.inject.Inject;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * @author Luka Lambalot
 */
@SarlSpecification("0.11")
@SarlElementType(19)
@SuppressWarnings("all")
public class Environment extends Agent {
  private Controller controller;
  
  private Configuration configuration;
  
  private List<Influence> listInfluences = Collections.<Influence>synchronizedList(new ArrayList<Influence>());
  
  private void $behaviorUnit$Initialize$0(final Initialize occurrence) {
    try {
      this.controller = Controller.getInstance();
      while ((this.controller.getStartAgent() == false)) {
        Thread.sleep(50);
      }
      Thread.sleep(50);
      ArrayList<Road> roads = this.controller.getConfiguration().getRoads();
      ArrayList<Car> cars = this.controller.getConfiguration().getCars();
      SetupApplication setupApplication = new SetupApplication();
      DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER();
      _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(setupApplication);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  private void $behaviorUnit$SetupApplication$1(final SetupApplication occurrence) {
    ArrayList<Car> cars = this.controller.getConfiguration().getCars();
    for (final Car car : cars) {
      Lifecycle _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER();
      DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER();
      _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER.spawnInContextWithID(CarAgent.class, car.getUUID(), _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.getDefaultContext(), car.getRoadOn());
    }
    this.startSimulationStep();
  }
  
  private void $behaviorUnit$Influence$2(final Influence occurrence) {
    boolean move = true;
    if ((occurrence.numberOfFreeCoord == 0)) {
      move = false;
    }
    ArrayList<Car> cars = this.controller.getConfiguration().getCars();
    this.listInfluences.add(occurrence);
    int _size = cars.size();
    int _size_1 = this.listInfluences.size();
    if ((_size == _size_1)) {
      this.endSimulationStep();
    }
  }
  
  protected void startSimulationStep() {
    List<Road> roads = Collections.<Road>synchronizedList(this.controller.getConfiguration().getRoads());
    ArrayList<Car> cars = this.controller.getConfiguration().getCars();
    for (final Car car : cars) {
      {
        Road _roadOn = car.getRoadOn();
        Perception perception = new Perception(_roadOn);
        DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER();
        class $SerializableClosureProxy implements Scope<Address> {
          
          private final UUID $_uUID;
          
          public $SerializableClosureProxy(final UUID $_uUID) {
            this.$_uUID = $_uUID;
          }
          
          @Override
          public boolean matches(final Address it) {
            UUID _uUID = it.getUUID();
            return Objects.equal(_uUID, $_uUID);
          }
        }
        final Scope<Address> _function = new Scope<Address>() {
          @Override
          public boolean matches(final Address it) {
            UUID _uUID = it.getUUID();
            UUID _uUID_1 = car.getUUID();
            return Objects.equal(_uUID, _uUID_1);
          }
          private Object writeReplace() throws ObjectStreamException {
            return new SerializableProxy($SerializableClosureProxy.class, car.getUUID());
          }
        };
        _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(perception, _function);
      }
    }
  }
  
  protected void endSimulationStep() {
    this.listInfluences.clear();
    this.startSimulationStep();
  }
  
  @Extension
  @ImportedCapacityFeature(Logging.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$IO_SARL_CORE_LOGGING;
  
  @SyntheticMember
  @Pure
  private Logging $CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER() {
    if (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) {
      this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = $getSkill(Logging.class);
    }
    return $castSkill(Logging.class, this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
  }
  
  @Extension
  @ImportedCapacityFeature(Lifecycle.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$IO_SARL_CORE_LIFECYCLE;
  
  @SyntheticMember
  @Pure
  private Lifecycle $CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER() {
    if (this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE == null || this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE.get() == null) {
      this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE = $getSkill(Lifecycle.class);
    }
    return $castSkill(Lifecycle.class, this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE);
  }
  
  @Extension
  @ImportedCapacityFeature(DefaultContextInteractions.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS;
  
  @SyntheticMember
  @Pure
  private DefaultContextInteractions $CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER() {
    if (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) {
      this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = $getSkill(DefaultContextInteractions.class);
    }
    return $castSkill(DefaultContextInteractions.class, this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
  }
  
  @Extension
  @ImportedCapacityFeature(Schedules.class)
  @SyntheticMember
  private transient AtomicSkillReference $CAPACITY_USE$IO_SARL_CORE_SCHEDULES;
  
  @SyntheticMember
  @Pure
  private Schedules $CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER() {
    if (this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES == null || this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES.get() == null) {
      this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES = $getSkill(Schedules.class);
    }
    return $castSkill(Schedules.class, this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES);
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$Initialize(final Initialize occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$Initialize$0(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$Influence(final Influence occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$Influence$2(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$SetupApplication(final SetupApplication occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$SetupApplication$1(occurrence));
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
  
  @SyntheticMember
  public Environment(final UUID parentID, final UUID agentID) {
    super(parentID, agentID);
  }
  
  @SyntheticMember
  @Inject
  @Deprecated
  public Environment(final BuiltinCapacitiesProvider provider, final UUID parentID, final UUID agentID) {
    super(provider, parentID, agentID);
  }
  
  @SyntheticMember
  @Inject
  public Environment(final UUID parentID, final UUID agentID, final DynamicSkillProvider skillProvider) {
    super(parentID, agentID, skillProvider);
  }
}
