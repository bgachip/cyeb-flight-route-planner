package hu.cyeb.flight_route_planner.controller;


import hu.cyeb.flight_route_planner.dto.CityDto;
import hu.cyeb.flight_route_planner.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cities")
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;

    @GetMapping("/smallest")
    public CityDto getSmallestCity() {
        return cityService.getSmallestCity();
    }

    @GetMapping("/largest")
    public CityDto getLargestCity() {
        return cityService.getLargestCity();
    }
}