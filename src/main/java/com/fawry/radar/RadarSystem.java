package com.fawry.radar;

import com.fawry.radar.model.Fine;
import com.fawry.radar.model.Observation;
import com.fawry.radar.model.Violation;
import com.fawry.radar.repository.FineRepository;
import com.fawry.radar.rules.RuleEngine;
import com.fawry.radar.service.FineService;
import com.fawry.radar.service.ViolationStatistics;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;


public class RadarSystem {

    private final RuleEngine ruleEngine;
    private final FineService fineService;
    private final FineRepository fineRepository;
    private final ViolationStatistics violationStatistics;

    public RadarSystem(RuleEngine ruleEngine,
                        FineService fineService,
                        FineRepository fineRepository,
                        ViolationStatistics violationStatistics) {
        this.ruleEngine = Objects.requireNonNull(ruleEngine);
        this.fineService = Objects.requireNonNull(fineService);
        this.fineRepository = Objects.requireNonNull(fineRepository);
        this.violationStatistics = Objects.requireNonNull(violationStatistics);
    }

    public Optional<Fine> processObservation(Observation observation) {
        List<Violation> violations = ruleEngine.evaluate(observation);
        Optional<Fine> fine = fineService.createFine(observation.getPlateNumber(), violations);
        fine.ifPresent(f -> {
            fineRepository.save(f);
            violationStatistics.record(violations);
        });
        return fine;
    }


    public Map<String, Double> getAllFines() {
        Map<String, Double> result = new java.util.LinkedHashMap<>();
        for (Fine fine : fineRepository.findAll()) {
            result.merge(fine.getPlateNumber(), fine.getTotalAmount(), Double::sum);
        }
        return result;
    }

    /** get all violated rules with count for each */
    public Map<String, Integer> getViolatedRulesWithCount() {
        return violationStatistics.getCounts();
    }

    public List<Fine> getFineHistory() {
        return fineRepository.findAll();
    }
}
