package hu.cyeb.flight_route_planner.dto;

public record FlightDto(
        Long id,
        Long airlineId,
        String airlineName,
        Long departureCityId,
        String departureCityName,
        Long arrivalCityId,
        String arrivalCityName,
        Integer distanceKm,
        Integer durationMinutes
) {
}