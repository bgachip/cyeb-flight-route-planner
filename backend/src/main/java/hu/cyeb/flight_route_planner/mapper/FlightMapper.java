package hu.cyeb.flight_route_planner.mapper;

import hu.cyeb.flight_route_planner.dto.FlightDto;
import hu.cyeb.flight_route_planner.entity.Flight;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FlightMapper {

    @Mapping(source = "airline.id", target = "airlineId")
    @Mapping(source = "airline.name", target = "airlineName")
    @Mapping(source = "departureCity.id", target = "departureCityId")
    @Mapping(source = "departureCity.name", target = "departureCityName")
    @Mapping(source = "arrivalCity.id", target = "arrivalCityId")
    @Mapping(source = "arrivalCity.name", target = "arrivalCityName")
    FlightDto toDto(Flight flight);
}