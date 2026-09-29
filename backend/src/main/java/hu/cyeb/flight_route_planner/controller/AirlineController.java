package hu.cyeb.flight_route_planner.controller;

import hu.cyeb.flight_route_planner.dto.AirlineDto;
import hu.cyeb.flight_route_planner.dto.FlightDto;
import hu.cyeb.flight_route_planner.service.AirlineService;
import hu.cyeb.flight_route_planner.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/airlines")
@RequiredArgsConstructor
public class AirlineController {

    private final AirlineService airlineService;
    private final FlightService flightService;

    @GetMapping
    public List<AirlineDto> getAll() {
        return airlineService.getAll();
    }

    @GetMapping("/{airlineId}/flights")
    public List<FlightDto> getFlights(
            @PathVariable Long airlineId
    ) {
        return flightService.getByAirline(airlineId);
    }
}