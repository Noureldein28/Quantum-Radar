package com.fawry.radar.repository;

import com.fawry.radar.model.Fine;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InMemoryFineRepository implements FineRepository {

    private final List<Fine> fines = new ArrayList<>();

    @Override
    public void save(Fine fine) {
        fines.add(fine);
    }

    @Override
    public List<Fine> findAll() {
        return Collections.unmodifiableList(fines);
    }
}
