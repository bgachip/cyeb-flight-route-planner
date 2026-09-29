package hu.cyeb.flight_route_planner.service.model;

import hu.cyeb.flight_route_planner.entity.City;

public record CityDistance(
        City city,
        int distance
) {
}