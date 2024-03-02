package org.jetbrains.assignment.controller;

import java.util.List;

import org.jetbrains.assignment.model.Location;
import org.jetbrains.assignment.model.Movement;
import org.jetbrains.assignment.service.MyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    private final MyService myService;

    public Controller(MyService myservice) {
        this.myService = myservice;
    }

    @PostMapping("locations")
    public ResponseEntity<List<Location>> getLocations(@RequestBody List<Movement> movements) {
        List<Location> locations = myService.extractLocation(movements);

        return ResponseEntity.ok(locations);
    }

    @PostMapping("moves")
    public ResponseEntity<List<Movement>> getMovements(@RequestBody List<Location> locations) {
        List<Movement> movements = myService.extractMovements(locations);

        return ResponseEntity.ok(movements);
    }
}
