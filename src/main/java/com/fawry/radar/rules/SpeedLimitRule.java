package com.fawry.radar.rules;

import com.fawry.radar.model.CarType;
import com.fawry.radar.model.Observation;
import com.fawry.radar.model.Violation;
import java.util.Objects;
import java.util.Optional;


public class SpeedLimitRule implements Rule {

    private final CarType carType;
    private final int maxSpeed;
    private final double fee;

    public SpeedLimitRule(CarType carType, int maxSpeed, double fee) {
        this.carType = Objects.requireNonNull(carType, "carType is required");
        this.maxSpeed = maxSpeed;
        this.fee = fee;
    }

    @Override
    public Optional<Violation> evaluate(Observation observation) {
        if (observation.getCarType() != carType) {
            return Optional.empty();
        }
        if (observation.getSpeed() <= maxSpeed) {
            return Optional.empty();
        }
        String description = String.format(
                "speed of %d exceeded max allowed %d", observation.getSpeed(), maxSpeed);
        String ruleName = carType + "_SPEED_LIMIT";
        return Optional.of(new Violation(ruleName, description, fee));
    }
}
