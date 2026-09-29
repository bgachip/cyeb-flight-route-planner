package hu.cyeb.flight_route_planner.service;

import hu.cyeb.flight_route_planner.dto.AirlineRouteDto;
import hu.cyeb.flight_route_planner.dto.RouteDto;
import hu.cyeb.flight_route_planner.dto.RouteSegmentDto;
import hu.cyeb.flight_route_planner.dto.RouteSummaryDto;
import hu.cyeb.flight_route_planner.entity.Airline;
import hu.cyeb.flight_route_planner.entity.City;
import hu.cyeb.flight_route_planner.entity.Flight;
import hu.cyeb.flight_route_planner.mapper.CityMapper;
import hu.cyeb.flight_route_planner.repository.AirlineRepository;
import hu.cyeb.flight_route_planner.repository.CityRepository;
import hu.cyeb.flight_route_planner.repository.FlightRepository;
import hu.cyeb.flight_route_planner.service.model.CityDistance;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RouteService {

    private final CityRepository cityRepository;
    private final AirlineRepository airlineRepository;
    private final FlightRepository flightRepository;

    private final CityMapper cityMapper;

    public RouteSummaryDto calculateRoutes() {

        City smallestCity = cityRepository
                .findFirstByOrderByPopulationAsc()
                .orElseThrow(() ->
                        new IllegalStateException("No cities found"));

        City largestCity = cityRepository
                .findFirstByOrderByPopulationDesc()
                .orElseThrow(() ->
                        new IllegalStateException("No cities found"));

        List<Airline> airlines = airlineRepository.findAll();

        List<AirlineRouteDto> airlineRoutes = new ArrayList<>();

        for (Airline airline : airlines) {

            List<Flight> airlineFlights =
                    flightRepository.findByAirlineId(airline.getId());

            List<Flight> path =
                    findShortestPath(
                            smallestCity,
                            largestCity,
                            airlineFlights
                    );

            AirlineRouteDto route =
                    createAirlineRoute(airline, path);

            airlineRoutes.add(route);
        }

        List<Flight> allFlights = flightRepository.findAll();

        List<Flight> combinedPath =
                findShortestPath(
                        smallestCity,
                        largestCity,
                        allFlights
                );

        RouteDto combinedRoute =
                createRoute(combinedPath);

        return new RouteSummaryDto(
                cityMapper.toDto(smallestCity),
                cityMapper.toDto(largestCity),
                airlineRoutes,
                combinedRoute
        );
    }

    private List<Flight> findShortestPath(
            City start,
            City destination,
            List<Flight> availableFlights
    ) {

        // Járatok csoportosítása indulási város szerint
        Map<City, List<Flight>> flightsByDepartureCity =
                availableFlights.stream()
                        .collect(Collectors.groupingBy(
                                Flight::getDepartureCity
                        ));

        // Eddigi legrövidebb távolságok
        Map<City, Integer> distances = new HashMap<>();

        // Az útvonal visszaépítéséhez
        Map<City, Flight> previousFlights = new HashMap<>();

        PriorityQueue<CityDistance> queue =
                new PriorityQueue<>(
                        Comparator.comparingInt(CityDistance::distance)
                );

        distances.put(start, 0);
        queue.add(new CityDistance(start, 0));

        while (!queue.isEmpty()) {

            CityDistance current = queue.poll();
            City currentCity = current.city();

            // Lehet régebbi, hosszabb útvonal is a queue-ban
            if (current.distance() >
                    distances.getOrDefault(
                            currentCity,
                            Integer.MAX_VALUE
                    )) {
                continue;
            }

            if (currentCity.equals(destination)) {
                break;
            }

            List<Flight> outgoingFlights =
                    flightsByDepartureCity.getOrDefault(
                            currentCity,
                            Collections.emptyList()
                    );

            for (Flight flight : outgoingFlights) {

                City nextCity = flight.getArrivalCity();

                int newDistance =
                        current.distance()
                                + flight.getDistanceKm();

                int knownDistance =
                        distances.getOrDefault(
                                nextCity,
                                Integer.MAX_VALUE
                        );

                if (newDistance < knownDistance) {

                    distances.put(nextCity, newDistance);

                    previousFlights.put(nextCity, flight);

                    queue.add(
                            new CityDistance(
                                    nextCity,
                                    newDistance
                            )
                    );
                }
            }
        }

        if (!distances.containsKey(destination)) {
            return List.of();
        }

        List<Flight> path = new ArrayList<>();

        // Útvonal visszaépítése a célállomástól
        City current = destination;

        while (!current.equals(start)) {

            Flight flight = previousFlights.get(current);

            if (flight == null) {
                return List.of();
            }

            path.add(flight);

            current = flight.getDepartureCity();
        }

        Collections.reverse(path);

        return path;
    }

    private AirlineRouteDto createAirlineRoute(
            Airline airline,
            List<Flight> path
    ) {

        if (path.isEmpty()) {
            return new AirlineRouteDto(
                    airline.getId(),
                    airline.getName(),
                    false,
                    0,
                    0,
                    List.of()
            );
        }

        int totalDistance = path.stream()
                .mapToInt(Flight::getDistanceKm)
                .sum();

        List<RouteSegmentDto> segments = createSegments(path);

        int totalDuration = calculateTotalDuration(segments);

        return new AirlineRouteDto(
                airline.getId(),
                airline.getName(),
                true,
                totalDistance,
                totalDuration,
                segments
        );
    }

    private RouteDto createRoute(List<Flight> path) {

        if (path.isEmpty()) {
            return new RouteDto(
                    false,
                    0,
                    0,
                    List.of()
            );
        }

        int totalDistance = path.stream()
                .mapToInt(Flight::getDistanceKm)
                .sum();

        List<RouteSegmentDto> segments = createSegments(path);

        int totalDuration = calculateTotalDuration(segments);

        return new RouteDto(
                true,
                totalDistance,
                totalDuration,
                segments
        );
    }

    private List<RouteSegmentDto> createSegments(List<Flight> path) {

        List<RouteSegmentDto> segments = new ArrayList<>();

        int currentTime = 0;

        for (int i = 0; i < path.size(); i++) {

            Flight flight = path.get(i);

            int waitingMinutes = 0;

            if (i > 0) {
                waitingMinutes = calculateWaitingMinutes(currentTime);
                currentTime += waitingMinutes;
            }

            segments.add(
                    new RouteSegmentDto(
                            flight.getAirline().getName(),
                            flight.getDepartureCity().getName(),
                            flight.getArrivalCity().getName(),
                            flight.getDistanceKm(),
                            flight.getDurationMinutes(),
                            waitingMinutes
                    )
            );

            currentTime += flight.getDurationMinutes();
        }

        return segments;
    }

    // A járatok minden egész órakor indulnak
    private int calculateWaitingMinutes(int currentTimeMinutes) {

        int remainder = currentTimeMinutes % 60;

        if (remainder == 0) {
            return 0;
        }

        return 60 - remainder;
    }

    private int calculateTotalDuration(List<RouteSegmentDto> segments) {

        return segments.stream()
                .mapToInt(segment ->
                        segment.durationMinutes()
                                + segment.waitingMinutes()
                )
                .sum();
    }
}