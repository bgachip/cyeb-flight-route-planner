package hu.cyeb.flight_route_planner.controller;

import hu.cyeb.flight_route_planner.dto.RouteSummaryDto;
import hu.cyeb.flight_route_planner.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/routes")
@RequiredArgsConstructor
public class RouteController {

    private final RouteService routeService;

    @GetMapping("/shortest")
    public RouteSummaryDto getShortestRoutes() {
        return routeService.calculateRoutes();
    }
}