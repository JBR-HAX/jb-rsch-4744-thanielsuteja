package org.jetbrains.assignment.dao;

import org.jetbrains.assignment.entity.MovementEnt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovementDao extends JpaRepository<MovementEnt, Long> {
}
