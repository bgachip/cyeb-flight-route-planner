package hu.cyeb.flight_route_planner.repository;

import hu.cyeb.flight_route_planner.entity.Airline;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirlineRepository extends JpaRepository<Airline, Long> {
}