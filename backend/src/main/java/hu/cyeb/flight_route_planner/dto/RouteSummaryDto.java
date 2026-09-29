package hu.cyeb.flight_route_planner.dto;

import java.util.List;

public record RouteSummaryDto(
        CityDto smallestCity,
        CityDto largestCity,
        List<AirlineRouteDto> airlineRoutes,
        RouteDto combinedRoute
) {
}