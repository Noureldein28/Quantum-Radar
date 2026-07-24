package com.fawry.radar.repository;

import com.fawry.radar.model.Fine;
import java.util.List;

public interface FineRepository {

    void save(Fine fine);

    List<Fine> findAll();
}
