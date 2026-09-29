package hu.cyeb.flight_route_planner.config;

import hu.cyeb.flight_route_planner.entity.Airline;
import hu.cyeb.flight_route_planner.entity.City;
import hu.cyeb.flight_route_planner.entity.Flight;
import hu.cyeb.flight_route_planner.repository.AirlineRepository;
import hu.cyeb.flight_route_planner.repository.CityRepository;
import hu.cyeb.flight_route_planner.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CityRepository cityRepository;
    private final AirlineRepository airlineRepository;
    private final FlightRepository flightRepository;

    @Override
    public void run(String... args) {

        // --------------------
        // Cities
        // --------------------

        City barra = cityRepository.save(
                City.builder()
                        .name("Barra")
                        .population(2_000L)
                        .build()
        );

        City dublin = cityRepository.save(
                City.builder()
                        .name("Dublin")
                        .population(600_000L)
                        .build()
        );

        City washington = cityRepository.save(
                City.builder()
                        .name("Washington")
                        .population(680_000L)
                        .build()
        );

        City amsterdam = cityRepository.save(
                City.builder()
                        .name("Amsterdam")
                        .population(920_000L)
                        .build()
        );

        City london = cityRepository.save(
                City.builder()
                        .name("London")
                        .population(8_900_000L)
                        .build()
        );

        City losAngeles = cityRepository.save(
                City.builder()
                        .name("Los Angeles")
                        .population(12_000_000L)
                        .build()
        );


        // --------------------
        // Airlines
        // --------------------

        Airline lufthansa = airlineRepository.save(
                Airline.builder()
                        .name("Lufthansa")
                        .build()
        );

        Airline klm = airlineRepository.save(
                Airline.builder()
                        .name("KLM")
                        .build()
        );

        Airline wizzAir = airlineRepository.save(
                Airline.builder()
                        .name("Wizz Air")
                        .build()
        );

        Airline ryanair = airlineRepository.save(
                Airline.builder()
                        .name("Ryanair")
                        .build()
        );


        // --------------------
        // Lufthansa
        // Barra -> London -> Washington -> Los Angeles
        // --------------------

        flightRepository.save(
                Flight.builder()
                        .airline(lufthansa)
                        .departureCity(barra)
                        .arrivalCity(london)
                        .distanceKm(1_000)
                        .durationMinutes(80)
                        .build()
        );

        flightRepository.save(
                Flight.builder()
                        .airline(lufthansa)
                        .departureCity(london)
                        .arrivalCity(washington)
                        .distanceKm(5_000)
                        .durationMinutes(290)
                        .build()
        );

        flightRepository.save(
                Flight.builder()
                        .airline(lufthansa)
                        .departureCity(washington)
                        .arrivalCity(losAngeles)
                        .distanceKm(3_700)
                        .durationMinutes(200)
                        .build()
        );


        // --------------------
        // KLM
        // Nincs teljes út Barra -> Los Angeles között
        // --------------------

        flightRepository.save(
                Flight.builder()
                        .airline(klm)
                        .departureCity(barra)
                        .arrivalCity(amsterdam)
                        .distanceKm(1_500)
                        .durationMinutes(110)
                        .build()
        );

        flightRepository.save(
                Flight.builder()
                        .airline(klm)
                        .departureCity(amsterdam)
                        .arrivalCity(london)
                        .distanceKm(500)
                        .durationMinutes(60)
                        .build()
        );


        // --------------------
        // Wizz Air
        // Alternatív teljes út
        // Barra -> Dublin -> Los Angeles
        // --------------------

        flightRepository.save(
                Flight.builder()
                        .airline(wizzAir)
                        .departureCity(barra)
                        .arrivalCity(dublin)
                        .distanceKm(1_200)
                        .durationMinutes(95)
                        .build()
        );

        flightRepository.save(
                Flight.builder()
                        .airline(wizzAir)
                        .departureCity(dublin)
                        .arrivalCity(losAngeles)
                        .distanceKm(8_000)
                        .durationMinutes(500)
                        .build()
        );

        flightRepository.save(
                Flight.builder()
                        .airline(ryanair)
                        .departureCity(barra)
                        .arrivalCity(dublin)
                        .distanceKm(1_100)
                        .durationMinutes(90)
                        .build()
        );


        // --------------------
        // Extra connection
        // Combined route számára
        // --------------------

        flightRepository.save(
                Flight.builder()
                        .airline(klm)
                        .departureCity(london)
                        .arrivalCity(losAngeles)
                        .distanceKm(7_000)
                        .durationMinutes(600)
                        .build()
        );
    }
}