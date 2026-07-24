package com.fawry.radar.rules;

import com.fawry.radar.model.Observation;
import com.fawry.radar.model.Violation;
import java.util.Optional;

public class SeatbeltRule implements Rule {

    private static final String RULE_NAME = "SEATBELT_RULE";

    private static final String DESCRIPTION = "Seatbelt not fastned";

    private final double fee;

    public SeatbeltRule(double fee) {
        this.fee = fee;
    }

    @Override
    public Optional<Violation> evaluate(Observation observation) {
        if (observation.isSeatbeltFastened()) {
            return Optional.empty();
        }
        return Optional.of(new Violation(RULE_NAME, DESCRIPTION, fee));
    }
}
