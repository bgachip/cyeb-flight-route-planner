export interface City {
  id: number;
  name: string;
  population: number;
}

export interface RouteSegment {
  airlineName: string;
  departureCity: string;
  arrivalCity: string;
  distanceKm: number;
  durationMinutes: number;
  waitingMinutes: number;
}

export interface AirlineRoute {
  airlineId: number;
  airlineName: string;
  available: boolean;
  totalDistanceKm: number;
  totalDurationMinutes: number;
  segments: RouteSegment[];
}

export interface Route {
  available: boolean;
  totalDistanceKm: number;
  totalDurationMinutes: number;
  segments: RouteSegment[];
}

export interface RouteSummary {
  smallestCity: City;
  largestCity: City;
  airlineRoutes: AirlineRoute[];
  combinedRoute: Route;
}
