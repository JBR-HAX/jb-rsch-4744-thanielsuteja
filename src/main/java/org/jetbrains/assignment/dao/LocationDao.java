package org.jetbrains.assignment.dao;

import org.jetbrains.assignment.entity.LocationEnt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationDao extends JpaRepository<LocationEnt, Long> {
}
