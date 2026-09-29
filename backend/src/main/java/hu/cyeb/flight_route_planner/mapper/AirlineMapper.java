package hu.cyeb.flight_route_planner.mapper;

import hu.cyeb.flight_route_planner.dto.AirlineDto;
import hu.cyeb.flight_route_planner.entity.Airline;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AirlineMapper {

    AirlineDto toDto(Airline airline);
}