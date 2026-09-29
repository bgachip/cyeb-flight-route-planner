package hu.cyeb.flight_route_planner.dto;

public record RouteSegmentDto(
        String airlineName,
        String departureCity,
        String arrivalCity,
        Integer distanceKm,
        Integer durationMinutes,
        Integer waitingMinutes
) {
}