# Quantum Radar

A traffic radar system built for the Fawry Quantum Internship Challenge.
It ingests observations from a physical radar, evaluates them against a
configurable set of rules, and issues fines for any violations — designed
to be extended with new rules without modifying existing code.

## Requirements covered

- Handles radar info: plate number, date, car type (Private / Truck / Bus), speed, seatbelt status
- Configurable rule set, e.g.:
  - Truck speed shouldn't exceed 60
  - Private car speed shouldn't exceed 80
  - Seatbelt should be fastened
- Each observation can generate zero or more violations
- A fine is issued when there are violations, printed in the exact required format:
  ```
  Traffic fine for car ABC1234
  Total amount: 400 EGP
  Violations:
  - Seatbelt not fastned : 100 EGP
  - speed of 94 exceeded max allowed 80 : 300 EGP
  ```
- Reporting:
  - Get all fines: plate number with total amount
  - Get all violated rules with count for each
- Extensible: new rules can be added without modifying the Radar system

## Project structure

```
src/main/java/com/fawry/radar/
├── model/
│   ├── Observation.java     # immutable radar reading
│   ├── CarType.java         # PRIVATE, TRUCK, BUS
│   ├── Violation.java       # a single broken rule
│   └── Fine.java            # violations grouped for one plate
├── rules/
│   ├── Rule.java            # extension point (Strategy pattern)
│   ├── SpeedLimitRule.java  # configurable per car type
│   ├── SeatbeltRule.java
│   └── RuleEngine.java      # runs all registered rules
├── service/
│   ├── FineService.java     # builds a Fine from violations
│   ├── FinePrinter.java     # formats a Fine as required text
│   └── ViolationStatistics.java # counts violations per rule
├── repository/
│   ├── FineRepository.java  # storage abstraction
│   └── InMemoryFineRepository.java
├── RadarSystem.java         # orchestrating facade
└── Main.java                # composition root / demo

src/test/java/com/fawry/radar/
├── TestKit.java              # tiny dependency-free assertion helper
└── RadarSystemTest.java      # requirement-driven test suite
```


## Requirements

- JDK 17+

## Sample output

```
Traffic fine for car ABC1234
Total amount: 400 EGP
Violations:
- speed of 94 exceeded max allowed 80 : 300 EGP
- Seatbelt not fastned : 100 EGP

No violations for car XYZ777

Traffic fine for car TRK555
Total amount: 300 EGP
Violations:
- speed of 72 exceeded max allowed 60 : 300 EGP

Traffic fine for car BUS900
Total amount: 100 EGP
Violations:
- Seatbelt not fastned : 100 EGP

=== All fines (plate -> total amount) ===
ABC1234 -> 400.0 EGP
TRK555 -> 300.0 EGP
BUS900 -> 100.0 EGP

=== Violated rules with count ===
PRIVATE_SPEED_LIMIT -> 1
SEATBELT_RULE -> 2
TRUCK_SPEED_LIMIT -> 1
```
