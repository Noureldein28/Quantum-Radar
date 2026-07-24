package com.fawry.radar.service;

import com.fawry.radar.model.Fine;
import com.fawry.radar.model.Violation;
import java.util.List;
import java.util.Optional;

public class FineService {

    public Optional<Fine> createFine(String plateNumber, List<Violation> violations) {
        if (violations == null || violations.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new Fine(plateNumber, violations));
    }
}
