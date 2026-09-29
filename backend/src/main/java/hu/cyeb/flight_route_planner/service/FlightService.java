package hu.cyeb.flight_route_planner.service;

import hu.cyeb.flight_route_planner.dto.FlightDto;
import hu.cyeb.flight_route_planner.entity.Flight;
import hu.cyeb.flight_route_planner.mapper.FlightMapper;
import hu.cyeb.flight_route_planner.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FlightService {

    private final FlightRepository flightRepository;
    private final FlightMapper flightMapper;

    public List<FlightDto> getAll() {
        return flightRepository.findAll()
                .stream()
                .map(flightMapper::toDto)
                .toList();
    }

    public List<FlightDto> getByAirline(Long airlineId) {
        return flightRepository.findByAirlineId(airlineId)
                .stream()
                .map(flightMapper::toDto)
                .toList();
    }

    public List<FlightDto> getFlights(Long from, Long to) {

        List<Flight> flights;

        if (from == null && to == null) {
            flights = flightRepository.findAll();
        } else if (from == null) {
            flights = flightRepository.findByArrivalCityId(to);
        } else if (to == null) {
            flights = flightRepository.findByDepartureCityId(from);
        } else {
            flights =
                    flightRepository.findByDepartureCityIdAndArrivalCityId(
                            from,
                            to
                    );
        }

        return flights.stream()
                .map(flightMapper::toDto)
                .toList();
    }
}