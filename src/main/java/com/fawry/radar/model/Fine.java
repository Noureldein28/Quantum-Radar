package com.fawry.radar.model;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class Fine {

    private final String plateNumber;
    private final List<Violation> violations;

    public Fine(String plateNumber, List<Violation> violations) {
        if (violations == null || violations.isEmpty()) {
            throw new IllegalArgumentException("A fine must have at least one violation");
        }
        this.plateNumber = Objects.requireNonNull(plateNumber, "plateNumber is required");
        this.violations = Collections.unmodifiableList(violations);
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public List<Violation> getViolations() {
        return violations;
    }

    public double getTotalAmount() {
        return violations.stream().mapToDouble(Violation::getFee).sum();
    }
}
