package io.sarl.template.javafx.agents;

import com.google.common.base.Objects;
import io.sarl.core.AgentTask;
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
import io.sarl.template.javafx.event.Kill;
import io.sarl.template.javafx.event.Perception;
import io.sarl.template.javafx.event.SetupApplication;
import java.io.ObjectStreamException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import javax.inject.Inject;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
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
  
  private CopyOnWriteArrayList<Influence> listInfluences = new CopyOnWriteArrayList<Influence>();
  
  private AtomicInteger count = new AtomicInteger();
  
  private AtomicInteger deadCount = new AtomicInteger();
  
  private void $behaviorUnit$Initialize$0(final Initialize occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER();
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("The agent was started.");
    this.controller = Controller.getInstance();
    Schedules _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER();
    final AgentTask task = _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER.task("begin");
    Schedules _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER();
    final Procedure1<Agent> _function = (Agent it) -> {
      boolean _startAgent = this.controller.getStartAgent();
      if ((_startAgent == true)) {
        ArrayList<Road> roads = this.controller.getConfiguration().getRoads();
        ArrayList<Car> cars = this.controller.getConfiguration().getCars();
        cars = this.controller.getConfiguration().addCar();
        SetupApplication setupApplication = new SetupApplication();
        DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER();
        _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(setupApplication);
        Schedules _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER_2 = this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER();
        _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER_2.cancel(task);
      }
    };
    _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER_1.every(task, 100, _function);
  }
  
  private void $behaviorUnit$SetupApplication$1(final SetupApplication occurrence) {
    try {
      ArrayList<Car> cars = this.controller.getConfiguration().getCars();
      for (final Car car : cars) {
        Lifecycle _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER();
        DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER();
        _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER.spawnInContextWithID(CarAgent.class, car.getUUID(), _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.getDefaultContext(), car.getRoadOn());
      }
      Thread.sleep(50);
      this.startSimulationStep();
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  private void $behaviorUnit$Influence$2(final Influence occurrence) {
    try {
      ArrayList<Car> cars = this.controller.getConfiguration().getCars();
      boolean move = true;
      boolean allMoved = false;
      CopyOnWriteArrayList<Integer> movingRange = new CopyOnWriteArrayList<Integer>();
      CopyOnWriteArrayList<Car> carOccurence = new CopyOnWriteArrayList<Car>();
      int maxValue = 0;
      CopyOnWriteArrayList<Integer> initialStepCar = new CopyOnWriteArrayList<Integer>();
      CopyOnWriteArrayList<Integer> stepCar = new CopyOnWriteArrayList<Integer>();
      this.listInfluences.add(occurrence);
      int _size = cars.size();
      int _size_1 = this.listInfluences.size();
      if ((_size == _size_1)) {
        for (final Influence occurence : this.listInfluences) {
          for (final Car car : cars) {
            UUID _uUID = car.getUUID();
            boolean _equals = Objects.equal(_uUID, occurence.id);
            if (_equals) {
              carOccurence.add(car);
              movingRange.add(Integer.valueOf(occurence.numberOfFreeCoord));
            }
          }
        }
        maxValue = ((Collections.<Integer>max(movingRange, null)) == null ? 0 : (Collections.<Integer>max(movingRange, null)).intValue());
        for (int i = 0; (i < movingRange.size()); i++) {
          Integer _get = movingRange.get(i);
          if ((_get != null && (_get.intValue() == 0))) {
            stepCar.add(Integer.valueOf((-1)));
          } else {
            Integer _get_1 = movingRange.get(i);
            stepCar.add(Integer.valueOf((maxValue / ((_get_1) == null ? 0 : (_get_1).intValue()))));
          }
        }
        for (final Integer ele : stepCar) {
          {
            Integer temp = ele;
            initialStepCar.add(temp);
          }
        }
        for (int j = 0; (j < maxValue); j++) {
          {
            for (int i = 0; (i < movingRange.size()); i++) {
              Integer _get = movingRange.get(i);
              if ((_get.intValue() > 0)) {
                Integer _get_1 = stepCar.get(i);
                if ((_get_1 != null && (_get_1.intValue() == 1))) {
                  carOccurence.get(i).getRoadOn().moveCarPosition(carOccurence.get(i));
                  Integer _get_2 = movingRange.get(i);
                  movingRange.set(i, Integer.valueOf((((_get_2) == null ? 0 : (_get_2).intValue()) - 1)));
                }
                Integer _get_3 = stepCar.get(i);
                stepCar.set(i, Integer.valueOf((((_get_3) == null ? 0 : (_get_3).intValue()) - 1)));
                Integer _get_4 = stepCar.get(i);
                if ((_get_4 != null && (_get_4.intValue() == 0))) {
                  stepCar.set(i, initialStepCar.get(i));
                }
              }
            }
            Thread.sleep(10);
          }
        }
        this.endSimulationStep();
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  protected synchronized void startSimulationStep() {
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
  
  protected synchronized void endSimulationStep() {
    try {
      ArrayList<Car> cars = this.controller.getConfiguration().getCars();
      ArrayList<Car> deletedCars = this.controller.getConfiguration().getDeletedCars();
      for (final Car car : cars) {
        int _size = car.getToBeDelete().size();
        if ((_size != 0)) {
          car.getToBeDelete().get(0).removeCar(car);
        }
      }
      int addCarCounter = this.count.incrementAndGet();
      int removeCarCounter = this.deadCount.incrementAndGet();
      if (((addCarCounter % 10) == 1)) {
        InputOutput.<String>println("A new car has been created");
        cars = this.controller.getConfiguration().addCar();
        Lifecycle _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER();
        int _size_1 = cars.size();
        DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER();
        int _size_2 = cars.size();
        _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER.spawnInContextWithID(CarAgent.class, cars.get((_size_1 - 1)).getUUID(), _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.getDefaultContext(), cars.get((_size_2 - 1)).getRoadOn());
        Thread.sleep(50);
      }
      ArrayList<Car> newCarsList = new ArrayList<Car>();
      for (final Car car_1 : cars) {
        float _energy = car_1.getEnergy();
        if ((_energy <= 0)) {
          Car _get = cars.get(0);
          Kill killCar = new Kill(_get);
          DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1 = this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER();
          _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1.emit(killCar);
          newCarsList.add(car_1);
          Thread.sleep(50);
        }
      }
      int _size_3 = newCarsList.size();
      if ((_size_3 != 0)) {
        for (final Car c : newCarsList) {
          cars = this.controller.getConfiguration().removeCar(c, false);
        }
      }
      this.listInfluences.clear();
      if (((!this.controller.getConfiguration().getPause()) && (!this.controller.getConfiguration().getStopSimulation()))) {
        this.startSimulationStep();
      } else {
        while ((this.controller.getConfiguration().getPause() && (!this.controller.getConfiguration().getStopSimulation()))) {
          Thread.sleep(10);
        }
        boolean _stopSimulation = this.controller.getConfiguration().getStopSimulation();
        if ((!_stopSimulation)) {
          this.startSimulationStep();
        }
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
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
