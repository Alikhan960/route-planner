# Route Planner: Strategy Pattern in Java

Assignment #5 for Software Design Patterns (Astana IT University).
A `Navigator` plans a route between two locations using interchangeable travel strategies
(car, walking, cycling, public transport). The strategy can be switched at runtime and the
`Navigator` contains no `if`/`switch` on the travel mode.

## Structure

```
src/main/java/kz/aitu/navigation/
├── Main.java                         Client: builds strategies, switches them at runtime
├── Navigator.java                    Context: holds a RouteStrategy and delegates to it
├── model/
│   ├── Location.java                 Named point on the map
│   └── Route.java                    Planning result (distance, duration, steps)
└── strategy/
    ├── RouteStrategy.java            Strategy interface
    ├── CarStrategy.java              Concrete strategy
    ├── WalkStrategy.java             Concrete strategy
    ├── CycleStrategy.java            Concrete strategy
    └── PublicTransportStrategy.java  Concrete strategy (added without touching existing code)
src/test/java/kz/aitu/navigation/NavigatorTest.java
docs/Report.md                        Report with UML diagram and Clean Code section
```

## Build and run (JDK 17+)

```bash
mkdir out
javac -d out $(find src -name '*.java')

java -cp out kz.aitu.navigation.Main              # all modes
java -cp out kz.aitu.navigation.Main car walk     # chosen modes, in this order
java -cp out kz.aitu.navigation.NavigatorTest     # tests
```

In IntelliJ IDEA: mark `src/main/java` and `src/test/java` as source roots and run
`Main` or `NavigatorTest`.

## Adding a new strategy

Create a class that implements `RouteStrategy` and register it in `Main`. `Navigator` and the
other strategies are not modified.
