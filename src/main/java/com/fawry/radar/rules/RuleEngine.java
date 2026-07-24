package com.fawry.radar.rules;

import com.fawry.radar.model.Observation;
import com.fawry.radar.model.Violation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class RuleEngine {

    private final List<Rule> rules;

    public RuleEngine(List<Rule> rules) {
        this.rules = new ArrayList<>(Objects.requireNonNull(rules, "rules is required"));
    }


    public void addRule(Rule rule) {
        rules.add(Objects.requireNonNull(rule, "rule is required"));
    }

    public List<Violation> evaluate(Observation observation) {
        List<Violation> violations = new ArrayList<>();
        for (Rule rule : rules) {
            rule.evaluate(observation).ifPresent(violations::add);
        }
        return Collections.unmodifiableList(violations);
    }
}
