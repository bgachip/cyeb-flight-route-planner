import {Component, inject, signal} from '@angular/core';

import {RouteService} from './services/route.service';
import {RouteSummary} from './models/route.model';
import {DecimalPipe} from "@angular/common";
import {MatCard, MatCardContent, MatCardHeader, MatCardSubtitle, MatCardTitle} from "@angular/material/card";
import {MatIcon} from "@angular/material/icon";
import {MatDivider} from "@angular/material/list";
import {MatChip} from "@angular/material/chips";
import {MatProgressSpinner} from "@angular/material/progress-spinner";
import {finalize} from "rxjs";

@Component({
  selector: 'app-root',
  imports: [DecimalPipe, MatCard, MatCardHeader, MatIcon, MatCardTitle, MatCardSubtitle, MatCardContent, MatDivider, MatChip, MatProgressSpinner],
  styleUrl: './app.scss',
  templateUrl: './app.html',
})
export class App {

  private readonly routeService = inject(RouteService);

  readonly routeSummary = signal<RouteSummary | undefined>(undefined);
  readonly loading = signal(true);
  readonly error = signal(false);

  constructor() {
    this.loadRoutes();
  }

  private loadRoutes(): void {
    this.routeService.getShortestRoutes()
      .pipe(
        finalize(() => this.loading.set(false))
      )
      .subscribe({
        next: (result) => {
          this.routeSummary.set(result);
        },
        error: (error) => {
          console.error('Failed to load routes', error);
          this.error.set(true);
        },
      });
  }

  formatDuration(minutes: number): string {
    const hours = Math.floor(minutes / 60);
    const remainingMinutes = minutes % 60;

    if (hours === 0) {
      return `${remainingMinutes} min`;
    }

    if (remainingMinutes === 0) {
      return `${hours}h`;
    }

    return `${hours}h ${remainingMinutes}m`;
  }
}
