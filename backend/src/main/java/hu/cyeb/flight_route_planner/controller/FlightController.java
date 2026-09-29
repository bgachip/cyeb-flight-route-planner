package hu.cyeb.flight_route_planner.controller;

import hu.cyeb.flight_route_planner.dto.FlightDto;
import hu.cyeb.flight_route_planner.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flights")
@RequiredArgsConstructor
public class FlightController {

    private final FlightService flightService;

    @GetMapping
    public List<FlightDto> getFlights(
            @RequestParam(required = false) Long from,
            @RequestParam(required = false) Long to
    ) {
        return flightService.getFlights(from, to);
    }
}