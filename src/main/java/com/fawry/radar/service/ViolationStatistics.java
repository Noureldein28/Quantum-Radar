package com.fawry.radar.service;

import com.fawry.radar.model.Violation;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Collections;

/**
 * Tracks how many times each rule has been violated across all
 * observations. Single responsibility: counting, nothing else.
 */
public class ViolationStatistics {

    private final Map<String, Integer> countsByRuleName = new LinkedHashMap<>();

    public void record(List<Violation> violations) {
        for (Violation v : violations) {
            countsByRuleName.merge(v.getRuleName(), 1, Integer::sum);
        }
    }

    public Map<String, Integer> getCounts() {
        return Collections.unmodifiableMap(countsByRuleName);
    }
}
