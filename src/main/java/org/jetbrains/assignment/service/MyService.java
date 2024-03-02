package org.jetbrains.assignment.service;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.assignment.dao.LocationDao;
import org.jetbrains.assignment.dao.MovementDao;
import org.jetbrains.assignment.entity.LocationEnt;
import org.jetbrains.assignment.entity.MovementEnt;
import org.jetbrains.assignment.model.Direction;
import org.jetbrains.assignment.model.Location;
import org.jetbrains.assignment.model.Movement;
import org.springframework.stereotype.Service;

@Service
public class MyService {
    private final LocationDao locationDao;
    private final MovementDao movementDao;

    public MyService(LocationDao locationDao, MovementDao movementDao) {
        this.locationDao = locationDao;
        this.movementDao = movementDao;
    }

    public List<Location> extractLocation(List<Movement> movements) {
        Location prevLoc = new Location(0, 0);

        List<Location> locations = new ArrayList<>();
        locations.add(prevLoc);

        for (Movement movement : movements) {
            int steps = movement.steps();

            Location currLoc = switch (movement.direction()) {
                case EAST -> new Location(prevLoc.x() + steps, prevLoc.y());
                case WEST -> new Location(prevLoc.x() - steps, prevLoc.y());
                case NORTH -> new Location(prevLoc.x(), prevLoc.y() + steps);
                case SOUTH -> new Location(prevLoc.x(), prevLoc.y() - steps);
            };
            prevLoc = currLoc;

            locations.add(currLoc);
        }

        locationDao.saveAll(locations.stream().map(location -> {
            LocationEnt entity = new LocationEnt();
            entity.setX(location.x());
            entity.setY(location.y());
            return entity;
        }).toList());

        return locations;
    }

    public List<Movement> extractMovements(List<Location> locations) {
        List<Movement> movements = new ArrayList<>();

        Location prevLoc = null;
        for (int i = 0; i < locations.size(); i++) {
            Location currLoc = locations.get(i);
            if (i == 0) {
                prevLoc = currLoc;
                continue;
            }

            int xMovement = currLoc.x() - prevLoc.x();
            int yMovement = currLoc.y() - prevLoc.y();

            Movement movement = null;
            if (xMovement > 0) movement = new Movement(Direction.EAST, Math.abs(xMovement));
            if (xMovement < 0) movement = new Movement(Direction.WEST, Math.abs(xMovement));
            if (yMovement > 0) movement = new Movement(Direction.NORTH, Math.abs(yMovement));
            if (yMovement < 0) movement = new Movement(Direction.SOUTH, Math.abs(yMovement));

            prevLoc = currLoc;
            movements.add(movement);
        }

        movementDao.saveAll(movements.stream().map(movement -> {
            MovementEnt entity = new MovementEnt();
            entity.setDirection(movement.direction());
            entity.setSteps(movement.steps());
            return entity;
        }).toList());

        return movements;
    }
}
