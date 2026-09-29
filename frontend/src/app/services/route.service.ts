import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { API_ENDPOINTS } from '../core/constants/api.constants';
import { RouteSummary } from '../models/route.model';

@Injectable({
  providedIn: 'root',
})
export class RouteService {

  private readonly http = inject(HttpClient);

  getShortestRoutes(): Observable<RouteSummary> {
    return this.http.get<RouteSummary>(
      API_ENDPOINTS.routes.shortest
    );
  }
}
