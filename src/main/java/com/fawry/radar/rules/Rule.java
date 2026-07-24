package com.fawry.radar.rules;

import com.fawry.radar.model.Observation;
import com.fawry.radar.model.Violation;
import java.util.Optional;


public interface Rule {

   
    Optional<Violation> evaluate(Observation observation);
}
