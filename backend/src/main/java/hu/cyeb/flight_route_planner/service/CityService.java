package hu.cyeb.flight_route_planner.service;

import hu.cyeb.flight_route_planner.dto.CityDto;
import hu.cyeb.flight_route_planner.mapper.CityMapper;
import hu.cyeb.flight_route_planner.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CityService {

    private final CityRepository cityRepository;
    private final CityMapper cityMapper;

    public CityDto getSmallestCity() {
        return cityRepository.findFirstByOrderByPopulationAsc()
                .map(cityMapper::toDto)
                .orElseThrow(() -> new IllegalStateException("No cities found"));
    }

    public CityDto getLargestCity() {
        return cityRepository.findFirstByOrderByPopulationDesc()
                .map(cityMapper::toDto)
                .orElseThrow(() -> new IllegalStateException("No cities found"));
    }
}