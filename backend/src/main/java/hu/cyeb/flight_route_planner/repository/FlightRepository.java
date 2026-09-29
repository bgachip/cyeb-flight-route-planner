package hu.cyeb.flight_route_planner.repository;

import hu.cyeb.flight_route_planner.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {

    List<Flight> findByAirlineId(Long airlineId);

    List<Flight> findByDepartureCityId(Long departureCityId);

    List<Flight> findByArrivalCityId(Long arrivalCityId);

    List<Flight> findByDepartureCityIdAndArrivalCityId(
            Long departureCityId,
            Long arrivalCityId
    );
}