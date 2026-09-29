package hu.cyeb.flight_route_planner.dto;

import java.util.List;

public record RouteDto(
        boolean available,
        Integer totalDistanceKm,
        Integer totalDurationMinutes,
        List<RouteSegmentDto> segments
) {
}