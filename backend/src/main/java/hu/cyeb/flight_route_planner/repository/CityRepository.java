package hu.cyeb.flight_route_planner.repository;

import hu.cyeb.flight_route_planner.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Long> {

    Optional<City> findFirstByOrderByPopulationAsc();

    Optional<City> findFirstByOrderByPopulationDesc();
}