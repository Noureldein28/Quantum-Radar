package com.fawry.radar;

import com.fawry.radar.model.CarType;
import com.fawry.radar.model.Fine;
import com.fawry.radar.model.Observation;
import com.fawry.radar.repository.FineRepository;
import com.fawry.radar.repository.InMemoryFineRepository;
import com.fawry.radar.rules.Rule;
import com.fawry.radar.rules.RuleEngine;
import com.fawry.radar.rules.SeatbeltRule;
import com.fawry.radar.rules.SpeedLimitRule;
import com.fawry.radar.service.FinePrinter;
import com.fawry.radar.service.FineService;
import com.fawry.radar.service.ViolationStatistics;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class Main {

    public static void main(String[] args) {

        List<Rule> rules = List.of(
                new SpeedLimitRule(CarType.TRUCK, 60, 300),
                new SpeedLimitRule(CarType.PRIVATE, 80, 300),
                new SeatbeltRule(100)
        );


        RuleEngine ruleEngine = new RuleEngine(rules);
        FineService fineService = new FineService();
        FineRepository fineRepository = new InMemoryFineRepository();
        ViolationStatistics violationStatistics = new ViolationStatistics();
        FinePrinter finePrinter = new FinePrinter();

        RadarSystem radarSystem = new RadarSystem(ruleEngine, fineService, fineRepository, violationStatistics);


        List<Observation> observations = List.of(
                Observation.builder()
                        .plateNumber("ABC1234")
                        .date(LocalDate.of(2026, 7, 23))
                        .carType(CarType.PRIVATE)
                        .speed(94)
                        .seatbeltFastened(false)
                        .build(),
                Observation.builder()
                        .plateNumber("XYZ777")
                        .date(LocalDate.of(2026, 7, 23))
                        .carType(CarType.TRUCK)
                        .speed(55)
                        .seatbeltFastened(true)
                        .build(),
                Observation.builder()
                        .plateNumber("TRK555")
                        .date(LocalDate.of(2026, 7, 23))
                        .carType(CarType.TRUCK)
                        .speed(72)
                        .seatbeltFastened(true)
                        .build(),
                Observation.builder()
                        .plateNumber("BUS900")
                        .date(LocalDate.of(2026, 7, 23))
                        .carType(CarType.BUS)
                        .speed(65)
                        .seatbeltFastened(false)
                        .build()
        );

        for (Observation observation : observations) {
            Optional<Fine> fine = radarSystem.processObservation(observation);
            if (fine.isPresent()) {
                finePrinter.print(fine.get());
                System.out.println();
            } else {
                System.out.println("No violations for car " + observation.getPlateNumber());
                System.out.println();
            }
        }


        System.out.println("=== All fines (plate -> total amount) ===");
        for (Map.Entry<String, Double> entry : radarSystem.getAllFines().entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue() + " EGP");
        }

        System.out.println();
        System.out.println("=== Violated rules with count ===");
        for (Map.Entry<String, Integer> entry : radarSystem.getViolatedRulesWithCount().entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}
