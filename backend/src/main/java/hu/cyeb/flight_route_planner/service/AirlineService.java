package hu.cyeb.flight_route_planner.service;

import hu.cyeb.flight_route_planner.dto.AirlineDto;
import hu.cyeb.flight_route_planner.mapper.AirlineMapper;
import hu.cyeb.flight_route_planner.repository.AirlineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AirlineService {

    private final AirlineRepository airlineRepository;
    private final AirlineMapper airlineMapper;

    public List<AirlineDto> getAll() {
        return airlineRepository.findAll()
                .stream()
                .map(airlineMapper::toDto)
                .toList();
    }
}