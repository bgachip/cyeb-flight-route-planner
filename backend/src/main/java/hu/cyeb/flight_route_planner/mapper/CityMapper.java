package hu.cyeb.flight_route_planner.mapper;

import hu.cyeb.flight_route_planner.dto.CityDto;
import hu.cyeb.flight_route_planner.entity.City;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CityMapper {

    CityDto toDto(City city);
}